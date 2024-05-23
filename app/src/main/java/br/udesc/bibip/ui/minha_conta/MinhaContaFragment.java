package br.udesc.bibip.ui.minha_conta;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.udesc.bibip.data.repository.UsuarioRepository;
import br.udesc.bibip.databinding.FragmentMinhaContaBinding;
import br.udesc.bibip.databinding.FragmentNotificationsBinding;
import br.udesc.bibip.ui.login.LoginActivity;

public class MinhaContaFragment extends Fragment {
    private FragmentMinhaContaBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        UsuarioRepository userRepository = new UsuarioRepository(getContext());
        MinhaContaViewModel minhaContaViewModel = new ViewModelProvider(this, new MinhaContaViewModelFactory(userRepository)).get(MinhaContaViewModel.class);

        binding = FragmentMinhaContaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final TextView tvEmail = binding.tvEmail;
        final TextView tvNome = binding.tvNome;
        final TextView tvId = binding.tvId;
        minhaContaViewModel.getCurrenttUser().observe(getViewLifecycleOwner(), user -> {
            tvEmail.setText(user.getEmail());
            tvNome.setText(user.getNome());
            tvId.setText(user.getId());
        });

        binding.btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            goToLogin();
        });

        SwitchMaterial swToggleTheme = binding.swToggleTheme;

        swToggleTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });
        return root;


    }

    private void goToLogin(){
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();

    }

}


//UserRepository userRepository = new UserDAO();
//        userViewModel = new ViewModelProvider(this, new UserViewModelFactory(userRepository)).get(UserViewModel.class);