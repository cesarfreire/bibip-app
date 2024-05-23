package br.udesc.bibip.data.dto;

import br.udesc.bibip.data.model.VeiculoServiceModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface VeiculoService {
    @GET("veiculos/{placa}")
    Call<VeiculoServiceModel> select(@Path("placa") String placa);
}
