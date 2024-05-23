package br.udesc.bibip.data.repository;

import android.content.Context;

import androidx.room.Room;

import br.udesc.bibip.data.database.AppDatabase;
import br.udesc.bibip.data.model.UsuarioModel;

public class UsuarioRepository {
    private Context context;
    private AppDatabase db;

    public UsuarioRepository(Context context) {
        this.context = context;
        db = Room.databaseBuilder(context,
                AppDatabase.class, "bibip.sqlite").allowMainThreadQueries().build();
    }

    public void salvarUsuario(UsuarioModel usuario) {
        db.usuarioDao().insertUsuario(usuario);
    }

    public UsuarioModel getUsuarioById(String id) {
        return db.usuarioDao().getUsuarioById(id);
    }

    public void atualizarUsuario(UsuarioModel usuario) {
        db.usuarioDao().updateUsuario(usuario);
    }

    public void deletarUsuario(UsuarioModel usuario) {
        db.usuarioDao().deleteUsuario(usuario);
    }

}
