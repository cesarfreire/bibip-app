package br.udesc.bibip.data.retrofit;

import br.udesc.bibip.data.dto.VeiculoService;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitInitializer {
    private final Retrofit retrofit;

    public RetrofitInitializer() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofit = new Retrofit.Builder().baseUrl("http://api.freire.tech/")
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client)
                .build();
    }

    public VeiculoService getVeiculoData()     {
        return retrofit.create(VeiculoService.class);
    }
}
