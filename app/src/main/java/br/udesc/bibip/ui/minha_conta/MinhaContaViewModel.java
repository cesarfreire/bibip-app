package br.udesc.bibip.ui.minha_conta;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.udesc.bibip.data.model.UsuarioModel;
import br.udesc.bibip.data.repository.UsuarioRepository;

public class MinhaContaViewModel extends ViewModel {
    private MutableLiveData<UsuarioModel> usuarioAtualLiveData;

    private UsuarioRepository usuarioRepository;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private UsuarioModel usuarioAtual;

    public MinhaContaViewModel(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
    }

    public LiveData<UsuarioModel> getCurrenttUser() {
        if (usuarioAtualLiveData == null) {
            usuarioAtualLiveData = new MutableLiveData<>();
            loadUser(); // Pode ser uma chamada assíncrona para carregar dados do repositório
        }
        return usuarioAtualLiveData;
    }

    private void loadUser() {
        // Simulação de carregamento de dados (Poderia ser de um sharedpreference)
        this.usuarioAtual = usuarioRepository.getUsuarioById(currentUser.getUid());
        usuarioAtualLiveData.setValue(usuarioAtual);
    }


    private String getEmail() {
        return this.usuarioAtual.getEmail();
    }

    private String getNome() {
        return this.usuarioAtual.getNome();
    }

    private String getId() {
        return this.usuarioAtual.getId();
    }
}

