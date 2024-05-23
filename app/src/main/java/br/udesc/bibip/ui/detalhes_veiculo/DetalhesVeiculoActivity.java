package br.udesc.bibip.ui.detalhes_veiculo;

import static java.util.Collections.emptyList;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import br.udesc.bibip.R;
import br.udesc.bibip.data.adapter.AbastecimentosRecyclerViewAdapter;
import br.udesc.bibip.data.adapter.AnotacoesRecyclerViewAdapter;
import br.udesc.bibip.data.model.AbastecimentoModel;
import br.udesc.bibip.data.model.AnotacaoModel;
import br.udesc.bibip.data.model.VeiculoModel;
import br.udesc.bibip.data.repository.AbastecimentoRepository;
import br.udesc.bibip.data.repository.AnotacaoRepository;
import br.udesc.bibip.data.repository.VeiculoRepository;
import br.udesc.bibip.databinding.ActivityDetalhesVeiculoBinding;

public class DetalhesVeiculoActivity extends AppCompatActivity {
    private ActivityDetalhesVeiculoBinding binding;
    private VeiculoRepository veiculoRepository;
    private AbastecimentoRepository abastecimentoRepository;
    private AnotacaoRepository anotacaoRepository;
    private VeiculoModel veiculo;
    private RecyclerView rvAbastecimentos;
    private RecyclerView rvAnotacoes;
    private DetalhesVeiculoViewModel detalhesVeiculoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        veiculoRepository = new VeiculoRepository(this);
        abastecimentoRepository = new AbastecimentoRepository(this);
        anotacaoRepository = new AnotacaoRepository(this);
        binding = ActivityDetalhesVeiculoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        loadVeiculo();
        binding.tvVeiculoApelido.setText(veiculo.getApelido());


        // viewmodel
        detalhesVeiculoViewModel = new ViewModelProvider(this, new DetalhesVeiculoViewModelFactory(veiculoRepository, abastecimentoRepository, anotacaoRepository, veiculo.getPlaca())).get(DetalhesVeiculoViewModel.class);

        // recycler view abastecimentos
        rvAbastecimentos = binding.rvAbatecimentosList;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvAbastecimentos.setLayoutManager(layoutManager);
        RecyclerView.Adapter adapter = new AbastecimentosRecyclerViewAdapter(emptyList());
        rvAbastecimentos.setAdapter(adapter);

        // recycler view anotacoes
        rvAnotacoes = binding.rvAnotacoesList;
        RecyclerView.Adapter adapter_anotacoes = new AnotacoesRecyclerViewAdapter(emptyList());
        RecyclerView.LayoutManager layoutManager_anotacoes = new LinearLayoutManager(this);
        rvAnotacoes.setLayoutManager(layoutManager_anotacoes);
        rvAnotacoes.setAdapter(adapter_anotacoes);

        // atualiza os dados das listas
        detalhesVeiculoViewModel.updateList();
        detalhesVeiculoViewModel.updateDadosPainel();

        detalhesVeiculoViewModel.getAnotacoesLiveData().observe(this, anotacoes -> {
            ((AnotacoesRecyclerViewAdapter) adapter_anotacoes).setList(anotacoes);
        });

        detalhesVeiculoViewModel.getAbastecimentosLiveData().observe(this, abastecimentos -> {
            ((AbastecimentosRecyclerViewAdapter) adapter).setList(abastecimentos);
        });


        binding.btnAdicionarAbastecimento.setOnClickListener(v -> showBottomSheetAbastecimento());

        binding.btnAdicionarAnotacao.setOnClickListener(v -> showBottomSheetAnotacao());

        detalhesVeiculoViewModel.getUltimaMedia().observe(this, media -> {
            binding.valorUltimaMedia.setText(getString(R.string.km_por_litro, media));
        });

        detalhesVeiculoViewModel.getUltimoPreco().observe(this, preco -> {
            binding.valorUltimoPreco.setText(getString(R.string.ultimo_preco, preco));
        });

        detalhesVeiculoViewModel.getQuantidadeAbastecimentos().observe(this, quantidade -> {
            binding.tvQtdadeAbastecimentos.setText(getString(R.string.abastecimentos_registrados, String.valueOf(quantidade)));
        });

        detalhesVeiculoViewModel.getQuantidadeAnotacoes().observe(this, quantidade -> {
            binding.tvQtdadeAnotacoes.setText(getString(R.string.anotacoes_registradas, String.valueOf(quantidade)));
        });
    }

    private void loadVeiculo() {
        Bundle b = getIntent().getExtras();
        String parametro_placa = b.getString("placa");
        veiculo = veiculoRepository.getVeiculoByPlaca(parametro_placa);
    }

    private void showBottomSheetAnotacao(){
        final Dialog dialog = new BottomSheetDialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_adicionar_anotacao);

        Button btnSalvarNovaAnotacao = dialog.findViewById(R.id.btnSalvarNovaAnotacao);

        btnSalvarNovaAnotacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etTitulo = dialog.findViewById(R.id.etTituloAnotacao);
                EditText etDescricao = dialog.findViewById(R.id.etDescricaoAnotacao);
                if (etTitulo.getText().toString().trim().isEmpty() || etDescricao.getText().toString().trim().isEmpty()) {
                    Log.d("Anotacao", "Campos obrigatórios não preenchidos");
                    Toast.makeText(getApplicationContext(), "Preencha todos os campos!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.d("Anotacao", "Salvando anotação");
                Log.d("Anotacao", "Título: " + etTitulo.getText().toString().trim());
                Log.d("Anotacao", "Descrição: " + etDescricao.getText().toString().trim());
                AnotacaoModel anotacao = new AnotacaoModel(
                        etTitulo.getText().toString().trim(),
                        etDescricao.getText().toString().trim(),
                        veiculo.getPlaca()
                );
                Log.d("Anotacao", "Anotação: " + anotacao);
                anotacaoRepository.salvarAnotacao(anotacao);
                detalhesVeiculoViewModel.updateList();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showBottomSheetAbastecimento(){
        final Dialog dialog = new BottomSheetDialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_adicionar_abastecimento);

        Button btnSalvarNovoAbastecimento = dialog.findViewById(R.id.btnSalvarNovoAbastecimento);

        btnSalvarNovoAbastecimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etQuilometragem = dialog.findViewById(R.id.etQuilometragemNoAbastecimento);
                EditText etLitros = dialog.findViewById(R.id.etLitrosAbastecidos);
                EditText etValor = dialog.findViewById(R.id.etValorPago);
                if (etQuilometragem.getText().toString().trim().isEmpty() || etLitros.getText().toString().trim().isEmpty() || etValor.getText().toString().trim().isEmpty()) {
                    Log.d("Abastecimento", "Campos obrigatórios não preenchidos");
                    Toast.makeText(getApplicationContext(), "Preencha todos os campos!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Date now = new Date(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toInstant().toEpochMilli());
                Log.d("Abastecimento", "Salvando abastecimento");
                Log.d("Abastecimento", "Quilometragem: " + etQuilometragem.getText().toString().trim());
                Log.d("Abastecimento", "Litros: " + etLitros.getText().toString().trim());
                Log.d("Abastecimento", "Valor: " + etValor.getText().toString().trim());
                Log.d("Abastecimento", "Data: " + now);
                AbastecimentoModel abastecimento = new AbastecimentoModel(
                        Long.parseLong(etQuilometragem.getText().toString().trim()),
                        Float.parseFloat(etLitros.getText().toString().trim()),
                        Float.parseFloat(etValor.getText().toString().trim()),
                        now,
                        veiculo.getPlaca()
                );
                Log.d("Abastecimento", "Quilometragem abastecimento: " + abastecimento.getQuilometragem());
                Log.d("Abastecimento", "Quilometragem veículo: " + veiculo.getQuilometragem());
                abastecimento.setKm_rodado(abastecimento.getQuilometragem() - veiculo.getQuilometragem());
                Log.d("Abastecimento", "Km rodado: " + abastecimento.getKm_rodado());
                Log.d("Abastecimento", "Abastecimento: " + abastecimento);
                abastecimentoRepository.salvarAbastecimento(abastecimento);
                veiculo.setQuilometragem(abastecimento.getQuilometragem());
                veiculoRepository.atualizarVeiculo(veiculo);
                Log.d("Abastecimento", "Veículo: " + veiculo);
                detalhesVeiculoViewModel.updateList();
                detalhesVeiculoViewModel.updateDadosPainel();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}