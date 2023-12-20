package pe.idat.monche_store;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import pe.idat.monche_store.databinding.ActivityLoginBinding;
import pe.idat.monche_store.databinding.ActivityMainBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    FirebaseAuth firebaseAuth;
    private String email,contra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();
        binding.correo01.getText().toString().trim();
        binding.contraseA01.getText().toString().trim();


        binding.registrar01.setOnClickListener(v -> validar_datos());
        binding.linkRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, Registro.class);
                startActivity(intent);

            }
        });

    }
    private void validar_datos(){
        email=binding.correo01.getText().toString().trim();
        contra= binding.contraseA01.getText().toString().trim();
        if (email.isEmpty()||contra.isEmpty()){
            Toast.makeText(this, "Casillas vacias", Toast.LENGTH_SHORT).show();
        }else {
            Login_Usuario(email,contra);
        }
    }
    private void Login_Usuario(String Correo, String Contrasena){
        firebaseAuth.signInWithEmailAndPassword(Correo, Contrasena).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(LoginActivity.this, PrincipalActivity.class));

                }else {
                    Toast.makeText(LoginActivity.this, "Correo o contraseña invalidos", Toast.LENGTH_SHORT).show();
                    Toast.makeText(LoginActivity.this, "Verifique los datos ", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}