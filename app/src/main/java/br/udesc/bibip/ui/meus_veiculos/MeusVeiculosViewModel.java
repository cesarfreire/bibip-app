package br.udesc.bibip.ui.meus_veiculos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import br.udesc.bibip.data.model.UsuarioModel;
import br.udesc.bibip.data.model.VeiculoModel;
import br.udesc.bibip.data.repository.UsuarioRepository;
import br.udesc.bibip.data.repository.VeiculoRepository;

public class MeusVeiculosViewModel extends ViewModel {
    private MutableLiveData<UsuarioModel> usuarioAtualLiveData;

    private VeiculoRepository veiculoRepository;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    private MutableLiveData<List<VeiculoModel>> veiculosLiveData;

    public MeusVeiculosViewModel(VeiculoRepository veiculoRepository) {
        this.veiculoRepository = veiculoRepository;
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        veiculosLiveData = new MutableLiveData<>();
        updateLista();
    }

    public LiveData<List<VeiculoModel>> getVeiculosLiveData() {
        if (veiculosLiveData == null) {
            veiculosLiveData = new MutableLiveData<>();
            veiculosLiveData.setValue(veiculoRepository.getVeiculosByUsuarioId(currentUser.getUid()));
        }
        return veiculosLiveData;
    }

    public void setLista(){
        veiculosLiveData.setValue(veiculoRepository.getVeiculosByUsuarioId(currentUser.getUid()));
    }

    public void updateLista(){
        veiculosLiveData.setValue(veiculoRepository.getVeiculosByUsuarioId(currentUser.getUid()));
    }

}
