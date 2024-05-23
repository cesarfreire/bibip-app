package br.udesc.bibip.data.repository;

import android.content.Context;

import androidx.room.Room;

import java.util.List;

import br.udesc.bibip.data.database.AppDatabase;
import br.udesc.bibip.data.model.AbastecimentoModel;

public class AbastecimentoRepository {
    private Context context;
    private AppDatabase db;

    public AbastecimentoRepository(Context context) {
        this.context = context;
        db = Room.databaseBuilder(context,
                AppDatabase.class, "bibip.sqlite").allowMainThreadQueries().build();
    }

    public void salvarAbastecimento(AbastecimentoModel abastecimento) {
        db.abastecimentoDao().insertAbastecimento(abastecimento);
    }

    public AbastecimentoModel getUltimoAbastecimentoByVeiculoPlaca(String placa) {
        return db.abastecimentoDao().getUltimoAbastecimentoByVeiculoPlaca(placa);
    }

    public List<AbastecimentoModel> getAbastecimentosByVeiculoPlaca(String placa) {
        return db.abastecimentoDao().getAbastecimentosByVeiculoPlaca(placa);
    }

    public void atualizarAbastecimento(AbastecimentoModel abastecimento) {
        db.abastecimentoDao().updateAbastecimento(abastecimento);
    }

    public void deletarAbastecimento(AbastecimentoModel abastecimento) {
        db.abastecimentoDao().deleteAbastecimento(abastecimento);
    }
}
