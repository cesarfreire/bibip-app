package br.udesc.bibip.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.udesc.bibip.data.model.VeiculoModel;
@Dao
public interface VeiculoDao {

    @Query("SELECT * FROM veiculo WHERE placa = :placa limit 1")
    VeiculoModel getVeiculoByPlaca(String placa);

    @Query("SELECT * FROM veiculo WHERE usuario_id = :usuario_id")
    List<VeiculoModel> getVeiculosByUsuarioId(String usuario_id);

    @Update
    void updateVeiculo(VeiculoModel veiculo);

    @Delete
    void deleteVeiculo(VeiculoModel veiculo);

    @Insert
    void insertVeiculo(VeiculoModel veiculo);

}
