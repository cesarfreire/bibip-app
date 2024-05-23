package br.udesc.bibip.data.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.udesc.bibip.R;
import br.udesc.bibip.data.dto.VeiculoService;
import br.udesc.bibip.data.model.AnotacaoModel;
import br.udesc.bibip.data.model.VeiculoServiceModel;

public class SituacaoRecyclerViewAdapter extends RecyclerView.Adapter<SituacaoRecyclerViewAdapter.ViewHolder>{
    private List<VeiculoServiceModel> veiculos;

    public SituacaoRecyclerViewAdapter(List<VeiculoServiceModel> veiculos){
        this.veiculos = new ArrayList<>(veiculos);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPlacaVeiculo;
        TextView tvModeloVeiculo;
        TextView tvCorVeiculo;
        TextView tvValorVeiculo;
        TextView tvMultasVeiculo;
        TextView tvPendenciasFinanceiras;
        TextView tvStatusVeiculo;

        public ViewHolder(View itemView){
            super(itemView);
            tvPlacaVeiculo = itemView.findViewById(R.id.tvPlacaVeiculo);
            tvModeloVeiculo = itemView.findViewById(R.id.tvModeloVeiculo);
            tvCorVeiculo = itemView.findViewById(R.id.tvCorVeiculo);
            tvValorVeiculo = itemView.findViewById(R.id.tvValorVeiculo);
            tvMultasVeiculo = itemView.findViewById(R.id.tvMultasVeiculo);
            tvPendenciasFinanceiras = itemView.findViewById(R.id.tvPendenciasFinanceiras);
            tvStatusVeiculo = itemView.findViewById(R.id.tvStatusVeiculo);
        }
    }

    @NonNull
    @Override
    public SituacaoRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_situacao_veiculos, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SituacaoRecyclerViewAdapter.ViewHolder holder, int position) {
        VeiculoServiceModel veiculo = veiculos.get(position);
        holder.tvModeloVeiculo.setText(veiculo.getModelo());
        holder.tvCorVeiculo.setText(veiculo.getCor());
        holder.tvPlacaVeiculo.setText(veiculo.getPlaca());
        String valor_veiculo = holder.itemView.getContext().getString(R.string.valor_veiculo, Float.parseFloat(veiculo.getValor()));
        holder.tvValorVeiculo.setText(valor_veiculo);
        holder.tvMultasVeiculo.setText(String.valueOf(veiculo.getMultas()));
        holder.tvPendenciasFinanceiras.setText(String.valueOf(veiculo.getPendenciasFinanceiras()));
        if (veiculo.getMultas() > 0 || veiculo.getPendenciasFinanceiras() > 0) {
            holder.tvStatusVeiculo.setText("Em situação irregular");
        } else {
            holder.tvStatusVeiculo.setText("Em situação regular");
        }


//        holder.ivDetalhesAnotacao.setOnClickListener(v -> {
//            Toast.makeText(v.getContext(), "Detalhes da anotação", Toast.LENGTH_SHORT).show();
//        });
//
//        holder.ivEditarAnotacao.setOnClickListener(v -> {
//            Toast.makeText(v.getContext(), "Editar anotação", Toast.LENGTH_SHORT).show();
//        });
//
//        holder.ivDeletarAnotacao.setOnClickListener(v -> {
//            Toast.makeText(v.getContext(), "Deletar anotação", Toast.LENGTH_SHORT).show();
//        });
    }

    @Override
    public int getItemCount() {
        return veiculos.size();
    }

}
