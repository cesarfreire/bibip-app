package br.udesc.bibip.ui.registro;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.udesc.bibip.MainActivity;
import br.udesc.bibip.data.model.UsuarioModel;
import br.udesc.bibip.data.repository.UsuarioRepository;
import br.udesc.bibip.databinding.ActivityLoginBinding;
import br.udesc.bibip.databinding.ActivityRegistroBinding;
import br.udesc.bibip.ui.login.LoginActivity;


public class RegistroActivity extends AppCompatActivity {

    private ActivityRegistroBinding binding;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegistroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressBar = binding.progressBar;
        progressBar.setVisibility(View.GONE);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            goToHome();
        }

        setupBtnRegistrarAction();
        setupGoToLogin();
    }

    private void setupBtnRegistrarAction() {
        binding.btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String etEmail = binding.txtInputEmail.getText().toString();
                String etSenha = binding.txtInputSenha.getText().toString();
                String etNome = binding.txtInputNome.getText().toString();
                if (etEmail.isEmpty() || etSenha.isEmpty() || etNome.isEmpty()) {
                    binding.txtInputEmail.setError("Campo obrigatório");
                    binding.txtInputSenha.setError("Campo obrigatório");
                    binding.txtInputNome.setError("Campo obrigatório");
                    progressBar.setVisibility(View.GONE);
                    return;
                }
                mAuth.createUserWithEmailAndPassword(etEmail, etSenha)
                        .addOnCompleteListener(RegistroActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    progressBar.setVisibility(View.GONE);
                                    Log.d("RegistroActivity", "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    criaUsuarioLocal(user.getUid(), etNome, etEmail);
                                    goToHome();
                                } else {
                                    progressBar.setVisibility(View.GONE);
                                    // If sign in fails, display a message to the user.
                                    Log.w("RegistroActivity", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(RegistroActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    private void goToHome() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private void setupGoToLogin(){
        binding.txtJaTenhoConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegistroActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void criaUsuarioLocal(String id, String nome, String email){
        // Cria um usuário local
        UsuarioRepository usuarioRepository = new UsuarioRepository(this);
        usuarioRepository.salvarUsuario(
                new UsuarioModel(
                    id, nome, email
                )
        );
    }
}