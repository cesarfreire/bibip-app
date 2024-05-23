package br.udesc.bibip.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.udesc.bibip.data.model.AnotacaoModel;

@Dao
public interface AnotacaoDao {
    @Query("SELECT * FROM anotacao WHERE veiculoPlaca = :veiculo_placa ORDER BY titulo ASC")
    List<AnotacaoModel> getAnotacoesByVeiculoPlaca(String veiculo_placa);

    @Query("SELECT * FROM anotacao WHERE id = :id LIMIT 1")
    AnotacaoModel getAnotacaoById(int id);

    @Insert
    void insertAnotacao(AnotacaoModel anotacao);

    @Update
    void updateAnotacao(AnotacaoModel anotacao);

    @Delete
    void deleteAnotacao(AnotacaoModel anotacao);
}
