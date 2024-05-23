package br.udesc.bibip.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

import br.udesc.bibip.data.model.AbastecimentoModel;

@Dao
public interface AbastecimentoDao {
    @Query("SELECT * FROM abastecimento WHERE veiculo_placa = :placa ORDER BY data DESC")
    List<AbastecimentoModel> getAbastecimentosByVeiculoPlaca(String placa);

    @Query("SELECT * FROM abastecimento WHERE veiculo_placa = :placa ORDER BY quilometragem DESC LIMIT 1")
    AbastecimentoModel getUltimoAbastecimentoByVeiculoPlaca(String placa);

    @Insert
    void insertAbastecimento(AbastecimentoModel abastecimento);

    @Update
    void updateAbastecimento(AbastecimentoModel abastecimento);

    @Delete
    void deleteAbastecimento(AbastecimentoModel abastecimento);

}
