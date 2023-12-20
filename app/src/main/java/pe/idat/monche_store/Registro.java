package pe.idat.monche_store;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import pe.idat.monche_store.databinding.ActivityRegistroBinding;

public class Registro extends AppCompatActivity {

    private ActivityRegistroBinding binding;

    FirebaseAuth firebaseAuth;
    String correo;
    String contrasena;
    String confir_contrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validar_datos();

            }
        });
        binding.linkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registro.this, LoginActivity.class);
                startActivity(intent);

            }
        });

                firebaseAuth = FirebaseAuth.getInstance();
    }
    private void Registrar_Usuario(String Correo, String Contrasena){
        firebaseAuth.createUserWithEmailAndPassword(Correo, Contrasena).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()){
                startActivity(new Intent(Registro.this, LoginActivity.class));
                Toast.makeText(Registro.this, "usuario registrado correctamente", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(Registro.this, "El usuario no se pudo registrar", Toast.LENGTH_SHORT).show();
            }
            }
        });

    }

    private void validar_datos(){
         correo = binding.correo.getText().toString().trim();
         contrasena = binding.contrasexa.getText().toString().trim();
         confir_contrasena = binding.confirmarContrasexa.getText().toString().trim();
         if (correo.isEmpty()||contrasena.isEmpty()||confir_contrasena.isEmpty()){
             Toast.makeText(this, "Casillas Vacias", Toast.LENGTH_SHORT).show();
         }else {
             if(contrasena.equals(confir_contrasena)){

             Registrar_Usuario(correo, contrasena);
         }}
    }

}