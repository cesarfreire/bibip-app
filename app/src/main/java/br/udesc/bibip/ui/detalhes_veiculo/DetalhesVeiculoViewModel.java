package br.udesc.bibip.ui.detalhes_veiculo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import br.udesc.bibip.data.model.AbastecimentoModel;
import br.udesc.bibip.data.model.AnotacaoModel;
import br.udesc.bibip.data.model.VeiculoModel;
import br.udesc.bibip.data.repository.AbastecimentoRepository;
import br.udesc.bibip.data.repository.AnotacaoRepository;
import br.udesc.bibip.data.repository.VeiculoRepository;
import br.udesc.bibip.data.utils.FirebaseUtil;

public class DetalhesVeiculoViewModel extends ViewModel {

    private VeiculoModel veiculo;
    private MutableLiveData<List<AbastecimentoModel>> abastecimentos;
    private MutableLiveData<List<AnotacaoModel>> anotacoes;
    private VeiculoRepository veiculoRepository;
    private AbastecimentoRepository abastecimentoRepository;
    private AnotacaoRepository anotacaoRepository;

    // dados do último abastecimento
    private AbastecimentoModel ultimoAbastecimento;
    private MutableLiveData<Float> ultimaMedia;
    private MutableLiveData<Float> ultimoPreco;

    public DetalhesVeiculoViewModel(
            VeiculoRepository veiculoRepository,
            AbastecimentoRepository abastecimentoRepository,
            AnotacaoRepository anotacaoRepository,
            String placa){
        this.veiculoRepository = veiculoRepository;
        this.abastecimentoRepository = abastecimentoRepository;
        this.anotacaoRepository = anotacaoRepository;
        this.veiculo = veiculoRepository.getVeiculoByPlaca(placa);
        this.ultimoAbastecimento = abastecimentoRepository.getUltimoAbastecimentoByVeiculoPlaca(veiculo.getPlaca());
        this.abastecimentos = new MutableLiveData<List<AbastecimentoModel>>();
        this.anotacoes = new MutableLiveData<List<AnotacaoModel>>();
        this.ultimaMedia = new MutableLiveData<Float>();
        this.ultimoPreco = new MutableLiveData<Float>();
        updateDadosPainel();
    }

    public LiveData<List<AbastecimentoModel>> getAbastecimentosLiveData() {
        return abastecimentos;
    }

    public LiveData<List<AnotacaoModel>> getAnotacoesLiveData() {
        return anotacoes;
    }

    public LiveData<Float> getUltimoPreco() {
        return ultimoPreco;
    }

    public LiveData<Float> getUltimaMedia() {
        return ultimaMedia;
    }

    public LiveData<Integer> getQuantidadeAbastecimentos() {
        return Transformations.map(abastecimentos, List::size);
    }

    public LiveData<Integer> getQuantidadeAnotacoes(){
        return Transformations.map(anotacoes, List::size);
    }


    public void updateList(){
        abastecimentos.setValue(abastecimentoRepository.getAbastecimentosByVeiculoPlaca(veiculo.getPlaca()));
        anotacoes.setValue(anotacaoRepository.getAnotacoesByVeiculoPlaca(veiculo.getPlaca()));
    }

    public void updateDadosPainel(){
        ultimoAbastecimento = abastecimentoRepository.getUltimoAbastecimentoByVeiculoPlaca(veiculo.getPlaca());

        if (ultimoAbastecimento != null) {
            float litros = (float) ultimoAbastecimento.getLitros();
            float media = ultimoAbastecimento.getKm_rodado() / litros;
            ultimaMedia.setValue(media);
        } else {
            ultimaMedia.setValue(0f);
        }

        if (ultimoAbastecimento != null) {
            ultimoPreco.setValue((float) ultimoAbastecimento.getValor());
        } else {
            ultimoPreco.setValue(0f);
        }

        abastecimentos.setValue(abastecimentoRepository.getAbastecimentosByVeiculoPlaca(veiculo.getPlaca()));
    }
}
