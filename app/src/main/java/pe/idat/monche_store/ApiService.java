package pe.idat.monche_store;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;


public interface ApiService {
    @GET("products")
    Call<List<TiendaResponse.Producto>> ProductosGet();
}
