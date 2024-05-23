package br.udesc.bibip.ui.detalhes_veiculo;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import br.udesc.bibip.data.repository.AbastecimentoRepository;
import br.udesc.bibip.data.repository.AnotacaoRepository;
import br.udesc.bibip.data.repository.VeiculoRepository;

public class DetalhesVeiculoViewModelFactory implements ViewModelProvider.Factory {
//    private final VeiculoRepository veiculoRepository;
//
//    public MeusVeiculosViewModelFactory(VeiculoRepository veiculoRepository) {
//        this.veiculoRepository = veiculoRepository;
//    }
//
//    @Override
//    public <T extends ViewModel> T create(Class<T> modelClass) {
//        if (modelClass.isAssignableFrom(MeusVeiculosViewModel.class)) {
//            return (T) new MeusVeiculosViewModel(veiculoRepository);
//        }
//        throw new IllegalArgumentException("Classe ViewModel desconhecida: " + modelClass.getName());
//    }
    private final VeiculoRepository veiculoRepository;
    private final AbastecimentoRepository abastecimentoRepository;
    private final AnotacaoRepository anotacaoRepository;
    private final String placa;

    public DetalhesVeiculoViewModelFactory(
            VeiculoRepository veiculoRepository,
            AbastecimentoRepository abastecimentoRepository,
            AnotacaoRepository anotacaoRepository,
            String placa) {
        this.veiculoRepository = veiculoRepository;
        this.abastecimentoRepository = abastecimentoRepository;
        this.anotacaoRepository = anotacaoRepository;
        this.placa = placa;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DetalhesVeiculoViewModel.class)) {
            return (T) new DetalhesVeiculoViewModel(veiculoRepository, abastecimentoRepository, anotacaoRepository, placa);
        }
        throw new IllegalArgumentException("Classe ViewModel desconhecida: " + modelClass.getName());
    }
}
