package de.produktsuche.ui.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Objects;

import de.produktsuche.R;
import de.produktsuche.backend.products.RequestController;

public class LoginFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        Button registerBtn = root.findViewById(R.id.buttonSignUp);
        registerBtn.setOnClickListener(v -> NavHostFragment.findNavController(LoginFragment.this)
                .navigate(R.id.action_navigation_login_to_navigation_register));


        TextInputLayout textInputLayoutUsername = root.findViewById(R.id.loginTextFieldUsername);
        TextInputLayout textInputLayoutPassword = root.findViewById(R.id.loginTextFieldPassword);
        ProgressBar progressBar = root.findViewById(R.id.progressBarLogin);

        Button btnLogin = root.findViewById(R.id.buttonLogin);
        btnLogin.setOnClickListener(v -> {
            boolean error = false;
            if (Objects.requireNonNull(textInputLayoutUsername.getEditText()).getText().toString().trim().equals("") ) {
                textInputLayoutUsername.setErrorEnabled(true);
                textInputLayoutUsername.setError("Bitte eingeben");
                textInputLayoutUsername.setEndIconActivated(true);
                error = true;
            } else {
                textInputLayoutUsername.setErrorEnabled(false);
                textInputLayoutUsername.setEndIconActivated(false);
                textInputLayoutUsername.setError(null);
            }
            if (Objects.requireNonNull(textInputLayoutPassword.getEditText()).getText().toString().trim().equals("")) {
                textInputLayoutPassword.setErrorEnabled(true);
                textInputLayoutPassword.setError("Bitte eingeben");
                textInputLayoutPassword.setEndIconActivated(true);
                error = true;
            } else {
                textInputLayoutPassword.setErrorEnabled(false);
                textInputLayoutPassword.setEndIconActivated(false);
                textInputLayoutPassword.setError(null);
            }

            if (!error) {
                RequestController requestController = new RequestController();

                HashMap<String, String> params = new HashMap<>();
                params.put("email", Objects.requireNonNull(textInputLayoutUsername.getEditText()).getText().toString());
                params.put("password", Objects.requireNonNull(textInputLayoutPassword.getEditText()).getText().toString());
                progressBar.setVisibility(View.VISIBLE);
                requestController.login(params, getActivity(), getContext(), progressBar);
            }
        });

        return root;
    }


}
