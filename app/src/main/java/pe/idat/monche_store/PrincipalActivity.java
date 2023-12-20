package pe.idat.monche_store;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import pe.idat.monche_store.databinding.ActivityPrincipalBinding;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PrincipalActivity extends AppCompatActivity {
    private ActivityPrincipalBinding binding;
    private List<TiendaResponse.Producto> productoList = new ArrayList<>();
    private TiendaAdapter tiendaAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPrincipalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.botoncamara.setOnClickListener(v -> camarauso.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE)));

        // Asigna un valor inicial a "pro" o ajusta según tus necesidades

        // Llama a listTienda con el valor actual de "pro"
        listTienda();
    }

    ActivityResultLauncher<Intent> camarauso = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult()
            , new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    if (o.getResultCode() == RESULT_OK) {
                        Bundle extras = o.getData().getExtras();
                        Bitmap imgBipmap = (Bitmap) extras.get("data");
                        binding.imagen.setImageBitmap(imgBipmap);
                    }
                }
            });

    private Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://fakestoreapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    private void listTienda() {
        Thread thread = new Thread(() -> {
            try {
                Response<List<TiendaResponse.Producto>> response = getRetrofit().create(ApiService.class).ProductosGet().execute();

                if (response.isSuccessful()) {
                    List<TiendaResponse.Producto> productoList = response.body();
                    runOnUiThread(() -> {
                        if (productoList != null && !productoList.isEmpty()) {
                            mostrarLista(productoList);
                        } else {
                            Toast.makeText(this, "No se encontraron productos", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    // Manejar el error en caso de una respuesta no exitosa
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Error en la solicitud: " + response.code(), Toast.LENGTH_SHORT).show();
                    });
                }

            } catch (IOException ioException) {
                ioException.printStackTrace();
                // Manejar la excepción de IO
                runOnUiThread(() -> {
                    Toast.makeText(this, "Error de red", Toast.LENGTH_SHORT).show();
                });
            }
        });
        thread.start();
    }

    private void mostrarLista(List<TiendaResponse.Producto> productoList) {
        // Actualiza la lógica para mostrar la lista
        tiendaAdapter = new TiendaAdapter(productoList);
        binding.rsPRODUCTOS.setHasFixedSize(true);
        binding.rsPRODUCTOS.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        binding.rsPRODUCTOS.setAdapter(tiendaAdapter);
    }


}
