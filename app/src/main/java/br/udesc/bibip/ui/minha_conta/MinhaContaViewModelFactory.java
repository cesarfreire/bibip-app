package br.udesc.bibip.ui.minha_conta;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import br.udesc.bibip.data.repository.UsuarioRepository;

public class MinhaContaViewModelFactory implements ViewModelProvider.Factory{
    private final UsuarioRepository usuarioRepository;

    public MinhaContaViewModelFactory(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MinhaContaViewModel.class)) {
            return (T) new MinhaContaViewModel(usuarioRepository);
        }
        throw new IllegalArgumentException("Classe ViewModel desconhecida: " + modelClass.getName());
    }
}
