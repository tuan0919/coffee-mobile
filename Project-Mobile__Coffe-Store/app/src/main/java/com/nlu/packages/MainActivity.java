package com.nlu.packages;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.nlu.packages.databinding.ActivityMainBinding;
import com.nlu.packages.ui.cart.CartActivity;
import com.nlu.packages.ui.home.HomeFragment;
import com.nlu.packages.ui.order.OrderFragment;
import com.nlu.packages.ui.store.StoreFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Runnable goTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_order,
                R.id.navigation_cart, R.id.navigation_store)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(navView, navController);

        navView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    if(item.getItemId() == R.id.navigation_home) {
                        loadFragment(new HomeFragment());
                        return true;
                    }else if(item.getItemId() == R.id.navigation_order){
                        loadFragment(new OrderFragment());
                        return true;
                    }else if(item.getItemId() == R.id.navigation_cart){
                        loadActivity(new CartActivity().getClass());
                        return true;
                    }else if(item.getItemId() == R.id.navigation_store){
                        loadFragment(new StoreFragment());
                        return true;
                    }
                return true;
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    private void loadActivity(Class<? extends AppCompatActivity> activity){
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}