package br.udesc.bibip.data.repository;

import android.content.Context;

import androidx.room.Room;

import java.util.List;

import br.udesc.bibip.data.database.AppDatabase;
import br.udesc.bibip.data.model.AnotacaoModel;

public class AnotacaoRepository {
    private Context context;
    private AppDatabase db;

    public AnotacaoRepository(Context context) {
        this.context = context;
        db = Room.databaseBuilder(context,
                AppDatabase.class, "bibip.sqlite").allowMainThreadQueries().build();
    }

    public void salvarAnotacao(AnotacaoModel anotacao) {
        db.anotacaoDao().insertAnotacao(anotacao);
    }

    public AnotacaoModel getAnotacaoById(int id) {
        return db.anotacaoDao().getAnotacaoById(id);
    }

    public void atualizarAnotacao(AnotacaoModel anotacao) {
        db.anotacaoDao().updateAnotacao(anotacao);
    }

    public void deletarAnotacao(AnotacaoModel anotacao) {
        db.anotacaoDao().deleteAnotacao(anotacao);
    }

    public List<AnotacaoModel> getAnotacoesByVeiculoPlaca(String placa) {
        return db.anotacaoDao().getAnotacoesByVeiculoPlaca(placa);
    }
}
