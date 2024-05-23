package br.udesc.bibip.ui.situacao;

import static java.util.Collections.emptyList;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import br.udesc.bibip.data.adapter.SituacaoRecyclerViewAdapter;
import br.udesc.bibip.data.model.VeiculoServiceModel;
import br.udesc.bibip.data.repository.VeiculoRepository;
import br.udesc.bibip.data.utils.NotificationHelper;
import br.udesc.bibip.databinding.FragmentSituacaoBinding;

public class SituacaoFragment extends Fragment {

    private FragmentSituacaoBinding binding;
    private RecyclerView rvSituacaoVeiculos;
    private SituacaoRecyclerViewAdapter adapter;
    private ProgressBar pbLoading;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SituacaoViewModel homeViewModel =
                new ViewModelProvider(this).get(SituacaoViewModel.class);
        homeViewModel.setRepository(new VeiculoRepository(this.getContext()));

        binding = FragmentSituacaoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        pbLoading = binding.pbLoading;
        pbLoading.setVisibility(View.GONE);


        rvSituacaoVeiculos = binding.rvSituacaoVeiculos;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        rvSituacaoVeiculos.setLayoutManager(layoutManager);
        adapter = new SituacaoRecyclerViewAdapter(emptyList());
        rvSituacaoVeiculos.setAdapter(adapter);


        homeViewModel.getVeiculosService().observe(getViewLifecycleOwner(), veiculos -> {
//            ((SituacaoRecyclerViewAdapter) adapter).setList(veiculos);
            adapter = new SituacaoRecyclerViewAdapter(veiculos);
            rvSituacaoVeiculos.setAdapter(adapter);
            boolean pendencias = false;
            for (VeiculoServiceModel veiculo : veiculos) {
                if (veiculo.getMultas() > 0) {
                    pendencias = true;
                    break;
                }
            }

            if (pendencias){
                showDialog("Veículos com pendências financeiras foram encontrados!", "Atenção!");
                NotificationHelper notificationHelper = new NotificationHelper(getActivity());
                String titulo = "Atenção!";
                String mensagem = "Veículos com pendências financeiras foram encontrados!";
                notificationHelper.sendNotification(1, titulo, mensagem);
            }
        });

        binding.btBuscarInfos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pbLoading.setVisibility(View.VISIBLE);
                homeViewModel.loadData();
                pbLoading.setVisibility(View.GONE);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void showDialog(String message, String title){
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(getActivity());
        builder.setMessage(message);
        builder.setTitle(title);
        builder.setCancelable(true);
        builder.setPositiveButton("Ok",  (d, w) -> {
            d.cancel();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}