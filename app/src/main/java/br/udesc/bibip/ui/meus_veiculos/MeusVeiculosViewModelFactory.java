package br.udesc.bibip.ui.meus_veiculos;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import br.udesc.bibip.data.repository.VeiculoRepository;
import br.udesc.bibip.ui.minha_conta.MinhaContaViewModel;

public class MeusVeiculosViewModelFactory implements ViewModelProvider.Factory{
    private final VeiculoRepository veiculoRepository;

    public MeusVeiculosViewModelFactory(VeiculoRepository veiculoRepository) {
        this.veiculoRepository = veiculoRepository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MeusVeiculosViewModel.class)) {
            return (T) new MeusVeiculosViewModel(veiculoRepository);
        }
        throw new IllegalArgumentException("Classe ViewModel desconhecida: " + modelClass.getName());
    }
}
