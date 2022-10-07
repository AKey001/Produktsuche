package de.produktsuche.ui.account;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Objects;

import de.produktsuche.R;
import de.produktsuche.backend.products.RequestController;

public class RegistrationFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.fragment_registration, container, false);

        RequestController requestController = new RequestController();

        TextInputLayout textInputLayoutUsername = root.findViewById(R.id.registerTextFieldUsername);
        TextInputLayout textInputLayoutPassword = root.findViewById(R.id.registerTextFieldPassword);
        TextInputLayout textInputLayoutRepeat = root.findViewById(R.id.registerTextFieldPasswordRepeat);
        ProgressBar progressBar = root.findViewById(R.id.progressBarRegistration);

        Button btnRegister = root.findViewById(R.id.buttonRegister);
        btnRegister.setOnClickListener(v -> {

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
            if (Objects.requireNonNull(textInputLayoutRepeat.getEditText()).getText().toString().trim().equals("") ) {
                textInputLayoutRepeat.setErrorEnabled(true);
                textInputLayoutRepeat.setError("Bitte eingeben");
                textInputLayoutRepeat.setEndIconActivated(true);
                error = true;
            } else {
                textInputLayoutRepeat.setErrorEnabled(false);
                textInputLayoutRepeat.setEndIconActivated(false);
                textInputLayoutRepeat.setError(null);
            }

            if (!error) {
                if (Objects.requireNonNull(textInputLayoutPassword.getEditText()).getText().toString().equals(Objects.requireNonNull(textInputLayoutRepeat.getEditText()).getText().toString())) {
                    Log.d("REQUEST", "start");
                    HashMap<String, String> params = new HashMap<>();
                    params.put("email", Objects.requireNonNull(textInputLayoutUsername.getEditText()).getText().toString());
                    params.put("password", textInputLayoutPassword.getEditText().getText().toString());
                    progressBar.setVisibility(View.VISIBLE);
                    requestController.register(params, requireActivity(), requireContext(), progressBar);
                } else {
                    textInputLayoutRepeat.setErrorEnabled(true);
                    textInputLayoutRepeat.setError("Keine Übereinstimmung");
                    textInputLayoutRepeat.setEndIconActivated(true);
                }
            }


        });

        return root;
    }


}
