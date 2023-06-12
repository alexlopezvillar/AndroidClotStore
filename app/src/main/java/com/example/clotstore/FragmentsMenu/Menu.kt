package com.example.clotstore.FragmentsMenu

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.clotstore.API.CRUDApi
import com.example.clotstore.API.preferencesUsuari
import com.example.clotstore.API.usuariLogejat
import com.example.clotstore.OutfitsRecomanats.ActivityPreferencesOutfits
import com.example.clotstore.CarretFactura.CarretActivity
import com.example.clotstore.OutfitsRecomanats.OutfitsRecomanats
import com.example.clotstore.R
import com.example.clotstore.databinding.ActivityMenuBinding
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Menu : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.appBarMenu.fab.setOnClickListener { view ->
            val intent = Intent(this, CarretActivity::class.java)

            ContextCompat.startActivity(this, intent, null)
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_menu)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.fragmentInici, R.id.seleccionarMarca, R.id.seleccionarPrenda,
                R.id.seleccionarTempo, R.id.seleccionarEStil, R.id.seleccionarCategoria
            ), drawerLayout
        )

        navView.setupWithNavController(navController)

        binding.appBarMenu.btMenu.setOnClickListener() {
            drawerLayout.openDrawer(Gravity.LEFT)
        }
        binding.appBarMenu.btPreferences.setOnClickListener() {

            runBlocking {
                val corrutina = launch {
                    val crudApi = CRUDApi()
                    preferencesUsuari = crudApi.getPreferences(usuariLogejat.idUsuari)!!

                }
                corrutina.join()
            }


            if (preferencesUsuari!!.idPreferences != 0 &&
                preferencesUsuari!!.idUsuari != 0 &&
                preferencesUsuari!!.temporada != 0 &&
                preferencesUsuari!!.estil != 0 &&
                preferencesUsuari!!.idColor != 0 &&
                preferencesUsuari!!.categoria != 0
            ) {
                val intent = Intent(this, OutfitsRecomanats::class.java)
                ContextCompat.startActivity(this, intent, null)
            } else {
                val intent = Intent(this, ActivityPreferencesOutfits::class.java)
                ContextCompat.startActivity(this, intent, null)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_menu)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
    public override fun onBackPressed() {

    }
}