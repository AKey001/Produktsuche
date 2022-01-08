package de.produktsuche.ui.login;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;

import de.produktsuche.R;
import de.produktsuche.backend.products.RequestController;

public class LoginFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        Button registerBtn = (Button) root.findViewById(R.id.buttonSignUp);
        registerBtn.setOnClickListener(v -> {
            NavHostFragment.findNavController(LoginFragment.this)
                    .navigate(R.id.action_navigation_login_to_navigation_register);
        });


        TextInputLayout textInputLayoutUsername = root.findViewById(R.id.loginTextFieldUsername);
        TextInputLayout textInputLayoutPassword = root.findViewById(R.id.loginTextFieldPassword);

        Button btnLogin = root.findViewById(R.id.buttonLogin);
        btnLogin.setOnClickListener(v -> {
            RequestController requestController = new RequestController();

            HashMap<String, String> params = new HashMap<>();
            params.put("email", textInputLayoutUsername.getEditText().getText().toString());
            params.put("password", textInputLayoutPassword.getEditText().getText().toString());
            requestController.login(params, getActivity(), getContext());
        });

        return root;
    }


}
