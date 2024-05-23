package br.udesc.bibip.ui.situacao;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import br.udesc.bibip.data.model.VeiculoModel;
import br.udesc.bibip.data.model.VeiculoServiceModel;
import br.udesc.bibip.data.repository.VeiculoRepository;
import br.udesc.bibip.data.retrofit.RetrofitInitializer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SituacaoViewModel extends ViewModel {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private VeiculoRepository veiculoRepository;
    private List<VeiculoModel> veiculos;
    private MutableLiveData<List<VeiculoServiceModel>> _veiculosService;

    public SituacaoViewModel() {
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        _veiculosService = new MutableLiveData<List<VeiculoServiceModel>>();
    }

    public LiveData<List<VeiculoServiceModel>> getVeiculosService() {
        if (_veiculosService == null) {
            _veiculosService = new MutableLiveData<>();
            loadData();
        }
        return _veiculosService;
    }

    public void setRepository(VeiculoRepository veiculoRepository) {
        this.veiculoRepository = veiculoRepository;
    }

    public void loadData() {
        this.veiculos = veiculoRepository.getVeiculosByUsuarioId(currentUser.getUid());
        ArrayList<VeiculoServiceModel> lista_nova = new ArrayList<VeiculoServiceModel>();
        for (VeiculoModel veiculo : veiculos) {
            Call<VeiculoServiceModel> call = new RetrofitInitializer().getVeiculoData().select(veiculo.getPlaca().toLowerCase());
            try {
                call.enqueue(new Callback<VeiculoServiceModel>() {
                    @Override
                    public void onResponse(Call<VeiculoServiceModel> call, Response<VeiculoServiceModel> response) {
                        if (response.isSuccessful()) {
                            Log.d("SUCESSO", String.valueOf(response.body()));
                            VeiculoServiceModel veiculoServiceModel = response.body();
                            Log.d("VEICULO", "Adicionando veiculo na lista nova...");
                            Log.d("VEICULO", veiculoServiceModel.toString());
                            lista_nova.add(veiculoServiceModel);
                            _veiculosService.setValue(lista_nova);
                        }
                    }

                    @Override
                    public void onFailure(Call<VeiculoServiceModel> call, Throwable throwable) {
                        Log.d("ERRO", "Erro ao buscar veiculo");
                        Log.d("ERRO", throwable.getMessage());
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        _veiculosService.setValue(lista_nova);
    }
}