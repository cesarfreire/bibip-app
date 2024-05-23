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

import java.util.List;

import br.udesc.bibip.R;
import br.udesc.bibip.data.model.AnotacaoModel;
import br.udesc.bibip.data.model.VeiculoModel;
import br.udesc.bibip.data.repository.AnotacaoRepository;
import br.udesc.bibip.data.repository.VeiculoRepository;

public class AnotacoesRecyclerViewAdapter extends RecyclerView.Adapter<AnotacoesRecyclerViewAdapter.ViewHolder>{

    private List<AnotacaoModel> anotacoes;

    public AnotacoesRecyclerViewAdapter(List<AnotacaoModel> anotacoes){
        this.anotacoes = anotacoes;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTituloAnotacao;
        TextView tvDescricaoAnotacao;
//        ImageView ivDetalhesAnotacao;
//        ImageView ivEditarAnotacao;
        ImageView ivDeletarAnotacao;
        public ViewHolder(View itemView){
            super(itemView);
            tvTituloAnotacao = itemView.findViewById(R.id.tvTituloAnotacao);
            tvDescricaoAnotacao = itemView.findViewById(R.id.tvDescricaoAnotacao);
//            ivDetalhesAnotacao = itemView.findViewById(R.id.ivDetalhesAnotacao);
//            ivEditarAnotacao = itemView.findViewById(R.id.ivEditarAnotacao);
            ivDeletarAnotacao = itemView.findViewById(R.id.ivDeletarAnotacao);

        }
    }

    @NonNull
    @Override
    public AnotacoesRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_anotacoes, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AnotacoesRecyclerViewAdapter.ViewHolder holder, int position) {
        AnotacaoModel anotacao = anotacoes.get(position);
        holder.tvTituloAnotacao.setText(anotacao.getTitulo());
        holder.tvDescricaoAnotacao.setText(anotacao.getDescricao());
        Context context = holder.itemView.getContext();

//        holder.ivDetalhesAnotacao.setOnClickListener(v -> {
//            Toast.makeText(v.getContext(), "Detalhes da anotação", Toast.LENGTH_SHORT).show();
//        });

        holder.ivDeletarAnotacao.setOnClickListener(v -> {
            AlertDialog.Builder builder
                    = new AlertDialog
                    .Builder(holder.itemView.getContext());
            builder.setMessage("Você deseja excluir a anotação?");
            builder.setTitle("Excluir anotação");
            builder.setPositiveButton("Sim",  (d, w) -> {
                excluirAnotacao(anotacao, context);
                anotacoes.remove(anotacao);
                notifyDataSetChanged();
            });
            builder.setNegativeButton("No", (d, w) -> d.cancel());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });
    }


    @Override
    public int getItemCount() {
        return anotacoes.size();
    }

    public void setList(List<AnotacaoModel> anotacoes){
        this.anotacoes = anotacoes;
        notifyDataSetChanged();
    }

    public void excluirAnotacao(AnotacaoModel anotacao, Context context){
        new AnotacaoRepository(context).deletarAnotacao(anotacao);
    }


}
