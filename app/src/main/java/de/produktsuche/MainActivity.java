package de.produktsuche;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import javax.inject.Inject;

import dagger.Component;
import de.produktsuche.backend.commons.HTTPRequest;
import de.produktsuche.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        AppBarConfiguration appBarConfiguration1 = new AppBarConfiguration.Builder(R.id.mobile_navigation).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration1);


        HTTPRequest httpRequest = new HTTPRequest();
        httpRequest.request("https://jsonplaceholder.typicode.com/todos/1", this);
    }

}