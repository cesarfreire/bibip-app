package br.udesc.bibip.ui.meus_veiculos;

import static java.util.Collections.emptyList;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.udesc.bibip.R;
import br.udesc.bibip.data.adapter.VeiculoRecyclerViewAdapter;
import br.udesc.bibip.data.model.VeiculoModel;
import br.udesc.bibip.data.repository.VeiculoRepository;
import br.udesc.bibip.data.utils.FirebaseUtil;
import br.udesc.bibip.databinding.FragmentMeusVeiculosBinding;


public class MeusVeiculosFragment extends Fragment {
    private FragmentMeusVeiculosBinding binding;
    private RecyclerView rvVeiculos;
    private VeiculoRepository veiculoRepository;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        veiculoRepository = new VeiculoRepository(getContext());
        MeusVeiculosViewModel meusVeiculosViewModel = new ViewModelProvider(this, new MeusVeiculosViewModelFactory(veiculoRepository)).get(MeusVeiculosViewModel.class);
        binding = FragmentMeusVeiculosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        rvVeiculos = binding.rvVeiculosList;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        rvVeiculos.setLayoutManager(layoutManager);
        RecyclerView.Adapter adapter = new VeiculoRecyclerViewAdapter(emptyList());
        meusVeiculosViewModel.updateLista();
        rvVeiculos.setAdapter(adapter);

        meusVeiculosViewModel.getVeiculosLiveData().observe(getViewLifecycleOwner(), veiculos -> {
            ((VeiculoRecyclerViewAdapter) adapter).setList(veiculos);
        });

        binding.btnAddVeiculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheet();
            }
        });
        return root;
    }

    private void showBottomSheet() {
        final Dialog dialog = new BottomSheetDialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_adicionar_veiculo);
        Button btnSalvaNovoVeiculo = dialog.findViewById(R.id.btnSalvaNovoVeiculo);

        btnSalvaNovoVeiculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_id = new FirebaseUtil().getCurrentUser(getContext()).getId();
                EditText apelido = dialog.findViewById(R.id.etApelido);
                EditText modelo = dialog.findViewById(R.id.etModelo);
                EditText placa = dialog.findViewById(R.id.etPlaca);
                EditText tipo = dialog.findViewById(R.id.etTipo);
                EditText quilometragem = dialog.findViewById(R.id.etQuilometragem);

                if (apelido.getText().toString().trim().isEmpty() || modelo.getText().toString().trim().isEmpty() || placa.getText().toString().trim().isEmpty() || tipo.getText().toString().trim().isEmpty() || quilometragem.getText().toString().trim().isEmpty()) {
                    Log.d("Veiculo", "Campos obrigatórios não preenchidos");
                    Toast.makeText(getContext(), "Preencha todos os campos!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.d("Veiculo", "Salvando veiculo");
                Log.d("Veiculo", "Apelido: " + apelido.getText().toString().trim());
                Log.d("Veiculo", "Modelo: " + modelo.getText().toString().trim());
                Log.d("Veiculo", "Placa: " + placa.getText().toString().trim());
                Log.d("Veiculo", "Tipo: " + tipo.getText().toString().trim());
                Log.d("Veiculo", "Quilometragem: " + quilometragem.getText().toString().trim());
                Log.d("Veiculo", "User_id: " + user_id);
                VeiculoModel veiculo = new VeiculoModel(
                        placa.getText().toString().trim(),
                        apelido.getText().toString().trim(),
                        modelo.getText().toString().trim(),
                        tipo.getText().toString().trim(),
                        user_id
                );
                veiculo.setQuilometragem(Long.parseLong(quilometragem.getText().toString().trim()));
                veiculoRepository.salvarVeiculo(veiculo);
                updateList();
                dialog.dismiss();
            }
        });
        dialog.show();


    }

    private void updateList() {
        List<VeiculoModel> veiculos = getVeiculos();
        rvVeiculos.setAdapter(new VeiculoRecyclerViewAdapter(veiculos));
    }

    private List<VeiculoModel> getVeiculos() {
        FirebaseUtil fb = new FirebaseUtil();
        return new VeiculoRepository(getContext()).getVeiculosByUsuarioId(fb.getCurrentUser(getContext()).getId());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
