package br.udesc.bibip.data.utils;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;

import br.udesc.bibip.data.model.UsuarioModel;
import br.udesc.bibip.data.repository.UsuarioRepository;

public class FirebaseUtil {

    private FirebaseAuth mAuth;

    public FirebaseUtil() {
        mAuth = FirebaseAuth.getInstance();
    }

    public FirebaseAuth getAuth() {
        return mAuth;
    }

    public void signOut() {
        mAuth.signOut();
    }

    public boolean isUserLogged() {
        return mAuth.getCurrentUser() != null;
    }

    public String getCurrentUserId() {
        return mAuth.getCurrentUser().getUid();
    }

    public String getCurrentUserEmail() {
        return mAuth.getCurrentUser().getEmail();
    }

    public UsuarioModel getCurrentUser(Context context) {
        UsuarioRepository userRepository = new UsuarioRepository(context);
        return userRepository.getUsuarioById(getCurrentUserId());
    }

}
