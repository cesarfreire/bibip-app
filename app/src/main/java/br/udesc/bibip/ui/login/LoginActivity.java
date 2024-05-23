package br.udesc.bibip.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.udesc.bibip.MainActivity;
import br.udesc.bibip.R;
import br.udesc.bibip.data.model.UsuarioModel;
import br.udesc.bibip.data.repository.UsuarioRepository;
import br.udesc.bibip.databinding.ActivityLoginBinding;
import br.udesc.bibip.ui.registro.RegistroActivity;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;

    private String etEmail;
    private String etSenha;
    private Button btnLogin;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressBar = binding.barraProgressoLogin;
        progressBar.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            goToHome();
        }
        setupBotoes();
    }


    private void setupBotoes() {
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etEmail = binding.txtInputEmail.getText().toString();
                etSenha = binding.txtInputSenha.getText().toString();
                progressBar.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(etEmail, etSenha)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("LoginActivity", "signInWithEmail:success");
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(LoginActivity.this, "Bem vindo!!!",
                                            Toast.LENGTH_SHORT).show();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    setupUser(user.getUid(), etEmail);
                                    goToHome();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("LoginActivity", "signInWithEmail:failure", task.getException());
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        binding.txtCriarNovaConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegistroActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void setupUser(String id, String email){
        UsuarioRepository usuarioRepository = new UsuarioRepository(this);
        UsuarioModel user = usuarioRepository.getUsuarioById(id);
        if (user == null){
            usuarioRepository.salvarUsuario(
                    new UsuarioModel(
                            id, "", email
                    )
            );
        }
    }

    private void goToHome() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}