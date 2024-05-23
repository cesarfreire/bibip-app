package br.udesc.bibip.data.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.udesc.bibip.R;
import br.udesc.bibip.data.model.VeiculoModel;
import br.udesc.bibip.data.repository.VeiculoRepository;
import br.udesc.bibip.ui.detalhes_veiculo.DetalhesVeiculoActivity;

public class VeiculoRecyclerViewAdapter extends RecyclerView.Adapter<VeiculoRecyclerViewAdapter.ViewHolder>{

    private List<VeiculoModel> veiculos;

    public VeiculoRecyclerViewAdapter(List<VeiculoModel> veiculos){
        this.veiculos = veiculos;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvApelido;
        TextView tvModelo;
        TextView tvPlaca;
        TextView tvTipo;
        ImageView ivApagarVeiculo;
        ImageView ivEditarVeiculo;
        ImageView ivDetalhesVeiculo;
        ImageView ivFotoVeiculo;
        public ViewHolder(View itemView){
            super(itemView);
            tvApelido = itemView.findViewById(R.id.tvApelido);
            tvPlaca = itemView.findViewById(R.id.tvPlaca);
            tvModelo = itemView.findViewById(R.id.tvModelo);
            tvTipo = itemView.findViewById(R.id.tvTipo);
            ivApagarVeiculo = itemView.findViewById(R.id.ivDelete);
            ivEditarVeiculo = itemView.findViewById(R.id.ivEdit);
            ivDetalhesVeiculo = itemView.findViewById(R.id.ivViewDetails);
            ivFotoVeiculo = itemView.findViewById(R.id.ivFotoVeiculo);
        }
    }
    @NonNull
    @Override
    public VeiculoRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_veiculos, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VeiculoRecyclerViewAdapter.ViewHolder holder, int position) {
        VeiculoModel veiculo = veiculos.get(position);
        holder.tvApelido.setText(veiculo.getApelido());
        holder.tvPlaca.setText(veiculo.getPlaca());
        holder.tvModelo.setText(veiculo.getModelo());
        holder.tvTipo.setText(veiculo.getTipo());
        Context context = holder.itemView.getContext();

        // exlcusão do veiculo
        holder.ivApagarVeiculo.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Excluir veículo");
            builder.setMessage("Deseja realmente excluir o veículo " + veiculo.getPlaca() + "?");
            builder.setPositiveButton("Sim", (dialog, which) -> {
                excluirVeiculo(veiculo, context);
                veiculos.remove(veiculo);
                notifyDataSetChanged();
            });
            builder.setNegativeButton("Não", (dialog, which) -> {
                dialog.dismiss();
            });
            builder.show();
        });

        holder.ivEditarVeiculo.setOnClickListener(v -> {
            Toast.makeText(context, "Edição do veíclo " + veiculo.getApelido(), Toast.LENGTH_SHORT).show();
        });

        // Tela de detalhes do veículo
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetalhesVeiculoActivity.class);
            intent.putExtra("placa", veiculo.getPlaca());
            context.startActivity(intent);
        });

        // Tela de detalhes do veículo clicando no ícone de detalhes
        holder.ivDetalhesVeiculo.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetalhesVeiculoActivity.class);
            intent.putExtra("placa", veiculo.getPlaca());
            context.startActivity(intent);
        });

        // foto do veiculo
//        holder.ivFotoVeiculo.setOnClickListener(v -> {
//            Toast.makeText(context, "Foto do veículo " + veiculo.getApelido(), Toast.LENGTH_SHORT).show();
//
//        });
        if (veiculo.getTipo().equals("Carro")){
            holder.ivFotoVeiculo.setImageResource(R.drawable.car_icon);
        } else if (veiculo.getTipo().equals("Moto")){
            holder.ivFotoVeiculo.setImageResource(R.drawable.moto_icon);
        } else if (veiculo.getTipo().equals("Caminhão")){
            holder.ivFotoVeiculo.setImageResource(R.drawable.truck_icon);
        } else {
            holder.ivFotoVeiculo.setImageResource(R.drawable.car_icon);
        }
    }

    @Override
    public int getItemCount() {
        return veiculos.size();
    }

    public void excluirVeiculo(VeiculoModel veiculo, Context context){
        new VeiculoRepository(context).deletarVeiculo(veiculo);
    }

    public void setList(List<VeiculoModel> veiculos){
        this.veiculos = veiculos;
        notifyDataSetChanged();
    }

}
