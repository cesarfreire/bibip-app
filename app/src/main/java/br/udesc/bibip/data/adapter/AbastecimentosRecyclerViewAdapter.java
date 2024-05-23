package br.udesc.bibip.data.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.List;

import br.udesc.bibip.R;
import br.udesc.bibip.data.model.AbastecimentoModel;
import br.udesc.bibip.data.model.AnotacaoModel;
import br.udesc.bibip.data.repository.AbastecimentoRepository;
import br.udesc.bibip.data.repository.AnotacaoRepository;

public class AbastecimentosRecyclerViewAdapter extends RecyclerView.Adapter<AbastecimentosRecyclerViewAdapter.ViewHolder>{

    private List<AbastecimentoModel> abastecimentos;

    public AbastecimentosRecyclerViewAdapter(List<AbastecimentoModel> abastecimentos) {
        this.abastecimentos = abastecimentos;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuilometragem;
        TextView tvLitros;
        TextView tvValor;
        TextView tvValorTotal;
        TextView tvData;
        ImageView ivDetalhesAbastecimento;
//        ImageView ivEditarAbastecimento;
        ImageView ivDeletarAbastecimento;
        public ViewHolder(View itemView){
            super(itemView);
            tvQuilometragem = itemView.findViewById(R.id.tvQuilometragem);
            tvLitros = itemView.findViewById(R.id.tvQuantidadeLitros);
            tvValor = itemView.findViewById(R.id.tvValor);
            tvValorTotal = itemView.findViewById(R.id.tvValorTotal);
            tvData = itemView.findViewById(R.id.tvDataAbastecimento);
            ivDetalhesAbastecimento = itemView.findViewById(R.id.ivDetalhesAbastecimento);
//            ivEditarAbastecimento = itemView.findViewById(R.id.ivEditarAbastecimento);
            ivDeletarAbastecimento = itemView.findViewById(R.id.ivDeletarAbastecimento);
        }
    }

    @NonNull
    @Override
    public AbastecimentosRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_abastecimentos, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AbastecimentosRecyclerViewAdapter.ViewHolder holder, int position) {
        AbastecimentoModel abastecimento = abastecimentos.get(position);
        Context context = holder.itemView.getContext();

        // seta quilometragem
        String quilometragem = String.valueOf(abastecimento.getQuilometragem());
        holder.tvQuilometragem.setText(holder.itemView.getContext().getString(R.string.abastecimento_quilometragem,quilometragem));

        // seta quantidade litros
        holder.tvLitros.setText(holder.itemView.getContext().getString(R.string.abastecimentos_qtade_litros,abastecimento.getLitros()));

        // seta valor por litro
        holder.tvValor.setText(holder.itemView.getContext().getString(R.string.abastecimento_valor_por_litro,abastecimento.getValor()));

        // seta valor total
        Float valorTotalAbastecimento = abastecimento.getValor() * abastecimento.getLitros();
        holder.tvValorTotal.setText(holder.itemView.getContext().getString(R.string.abastecimento_valor_total, valorTotalAbastecimento));

        // seta data do abastecimento
        holder.tvData.setText(new SimpleDateFormat("dd'/'M'/'yyyy ' - ' K:s").format(abastecimento.getData()));

//        holder.itemView.setOnLongClickListener(v -> {
//            Toast.makeText(holder.itemView.getContext(), "Você pressionou e segurou o item", Toast.LENGTH_SHORT).show();
//            return true;
//        });

//        holder.itemView.setOnClickListener(v -> {
//            Toast.makeText(holder.itemView.getContext(), "Você clicou no item", Toast.LENGTH_SHORT).show();
//        });

        holder.ivDetalhesAbastecimento.setOnClickListener(v -> {
            View view = View.inflate(context, R.layout.detalhes_abastecimento, null);
            AlertDialog.Builder builder
                    = new AlertDialog
                    .Builder(holder.itemView.getContext());
            TextView detalhesQtdadeLitros = view.findViewById(R.id.tvDetalhesQuantidadeLitrosValor);
            TextView detalhesValor = view.findViewById(R.id.tvDetalhesValorPagoValor);
            TextView detalhesQuilometragem = view.findViewById(R.id.tvDetalhesQuilometragemValor);
            detalhesQtdadeLitros.setText(String.valueOf(abastecimento.getLitros()));
            detalhesValor.setText(String.valueOf(abastecimento.getValor()));
            detalhesQuilometragem.setText(String.valueOf(abastecimento.getQuilometragem()));
            builder.setMessage("Detalhes do abastecimento");
            builder.setTitle("Detalhes");
            builder.setView(view);
            builder.setCancelable(true);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

//        holder.ivEditarAbastecimento.setOnClickListener(v -> {
//            Toast.makeText(holder.itemView.getContext(), "Você clicou no botão de editar", Toast.LENGTH_SHORT).show();
//        });

        holder.ivDeletarAbastecimento.setOnClickListener(v -> {
            AlertDialog.Builder builder
                    = new AlertDialog
                    .Builder(holder.itemView.getContext());
            builder.setMessage("Você deseja excluir o abastecimento?");
            builder.setTitle("Excluir abastecimento");
            builder.setPositiveButton("Sim",  (d, w) -> {
                excluirAbastecimento(abastecimento, context);
                abastecimentos.remove(abastecimento);
                notifyDataSetChanged();
            });
            builder.setNegativeButton("No", (d, w) -> d.cancel());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return abastecimentos.size();
    }

    public void setList(List<AbastecimentoModel> abastecimentos){
        this.abastecimentos = abastecimentos;
        notifyDataSetChanged();
    }

    public void excluirAbastecimento(AbastecimentoModel abastecimento, Context context){
        new AbastecimentoRepository(context).deletarAbastecimento(abastecimento);
    }
}
