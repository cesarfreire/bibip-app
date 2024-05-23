package br.udesc.bibip.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import java.util.List;

import br.udesc.bibip.data.database.AppDatabase;
import br.udesc.bibip.data.model.VeiculoModel;

public class VeiculoRepository {
    private Context context;
    private AppDatabase db;

    public VeiculoRepository(Context context) {
        this.context = context;
        db = Room.databaseBuilder(context,
                AppDatabase.class, "bibip.sqlite").allowMainThreadQueries().build();
    }

    public void salvarVeiculo(VeiculoModel veiculo) {
        Log.d("VeiculoRepository", "salvarVeiculo: " + veiculo.toString());
        db.veiculoDao().insertVeiculo(veiculo);
    }

    public VeiculoModel getVeiculoByPlaca(String placa) {
        return db.veiculoDao().getVeiculoByPlaca(placa);
    }

    public List<VeiculoModel> getVeiculosByUsuarioId(String usuario_id) {
        return db.veiculoDao().getVeiculosByUsuarioId(usuario_id);
    }

    public void atualizarVeiculo(VeiculoModel veiculo) {
        db.veiculoDao().updateVeiculo(veiculo);
    }

    public void deletarVeiculo(VeiculoModel veiculo) {
        db.veiculoDao().deleteVeiculo(veiculo);
    }
}
