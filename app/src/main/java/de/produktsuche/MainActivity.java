package de.produktsuche;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import de.produktsuche.ui.search.SearchFragment;

public class MainActivity extends AppCompatActivity {
    private NavController navController;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Log.d("NOTIFICATION FCM ID", sharedPreferences.getString("fcm_token", "error"));

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_search, R.id.navigation_watchlist, R.id.navigation_reserved_list)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        AppBarConfiguration appBarConfiguration1 = new AppBarConfiguration.Builder(R.id.mobile_navigation).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration1);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.action_login);
        String account = sharedPreferences.getString("AccountUUID", null);
        if (account != null) {
            item.setTitle("Logout");
        }


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_login:
                String account = sharedPreferences.getString("AccountUUID", null);
                if (account != null) {
                    sharedPreferences.edit().putString("AccountUUID", null).apply();
                    item.setTitle("Login");
                    navController.navigate(R.id.navigation_search);
                } else {
                    navController.navigate(R.id.navigation_login);
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}