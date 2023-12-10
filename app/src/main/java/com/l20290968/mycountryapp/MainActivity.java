package com.l20290968.mycountryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.l20290968.mycountryapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding viewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //definir la vista a traes del objeto de el enlace con la vista
        viewBinding = ActivityMainBinding.inflate( getLayoutInflater() );


        setContentView(viewBinding.getRoot());

        //enlazer los elementos
        BottomNavigationView navView = viewBinding.mainBnvNavView;

        //pasar los ids a un configurador de barra para habilitar la navegacion
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.mainNavMiContinente,
                R.id.mainNavMiBuscar,
                R.id.mainNavMiPerfil
        ).build();

        // CREAR CONTROLADOR PARA VINCULAR LA NAVEGACION CON UN ELEMENTO(HOST)
        NavController navController = Navigation.findNavController(this, R.id.mainFragmentNavHost);

// enlazar los controles para que la interfaz cambie de vista conforme se seleccionan los iconos
        NavigationUI.setupWithNavController(navView, navController);

    }
}