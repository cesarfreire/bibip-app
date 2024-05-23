package br.udesc.bibip.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import br.udesc.bibip.data.model.UsuarioModel;

@Dao
public interface UsuarioDao {
    @Query("SELECT * FROM usuario WHERE id = :id")
    UsuarioModel getUsuarioById(String id);

    @Insert
    void insertUsuario(UsuarioModel usuario);

    @Insert
    void insertAllUsuarios(UsuarioModel... usuarios);

    @Update
    void updateUsuario(UsuarioModel usuario);

    @Delete
    void deleteUsuario(UsuarioModel usuario);
}
