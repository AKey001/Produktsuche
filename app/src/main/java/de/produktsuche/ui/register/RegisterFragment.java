package de.produktsuche.ui.register;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;

import de.produktsuche.R;
import de.produktsuche.backend.products.RequestController;

public class RegisterFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.fragment_register, container, false);

        RequestController requestController = new RequestController();

        TextInputLayout textInputLayoutUsername = root.findViewById(R.id.registerTextFieldUsername);
        TextInputLayout textInputLayoutPassword = root.findViewById(R.id.registerTextFieldPassword);
        TextInputLayout textInputLayoutRepeat = root.findViewById(R.id.registerTextFieldPasswordRepeat);

        Button btnRegister = root.findViewById(R.id.buttonRegister);
        btnRegister.setOnClickListener(v -> {

            if (textInputLayoutPassword.getEditText().getText().toString().equals(textInputLayoutRepeat.getEditText().getText().toString())) {
                Log.d("REQUEST", "start");
                HashMap<String, String> params = new HashMap<>();
                params.put("email", textInputLayoutUsername.getEditText().getText().toString());
                params.put("password", textInputLayoutPassword.getEditText().getText().toString());
                requestController.register(params, getContext());
            } else {
                // TODO error pwd not equal
            }

        });

        return root;
    }


}
