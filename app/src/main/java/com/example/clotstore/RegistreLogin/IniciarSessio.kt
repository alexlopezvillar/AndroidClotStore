package com.example.clotstore.RegistreLogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.clotstore.API.*
import com.example.clotstore.Administrador.Adminitrador
import com.example.clotstore.FragmentsMenu.Menu
import com.example.clotstore.databinding.IniciarSessioBinding
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class IniciarSessio : AppCompatActivity() {
    private lateinit var binding: IniciarSessioBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val crudApi = CRUDApi()
        var tePreferencies: Boolean = false
        binding = IniciarSessioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()
        binding.etNom.setText(intent.getStringExtra("nomusu"))
        binding.etContrasenya.setText(intent.getStringExtra("contra"))

        binding.btInicia.setOnClickListener() {
            binding.btInicia.setAlpha(0.25f);
            binding.btInicia.animate().alpha(1f).setDuration(1500);
            val nomusu = binding.etNom.text.toString()
            val passw = Sha.calculateSHA(binding.etContrasenya.text.toString())
            var usu: Usuari? = null
            runBlocking {
                val corrutina = launch {

                    usu = crudApi.autenticaUsuari(nomusu, passw)


                }
                corrutina.join()
            }

            if (usu != null && usu!!.tipus.equals("Cliente")) {
                val intent = Intent(this, Menu::class.java)
                ContextCompat.startActivity(this, intent, null)
                usuariLogejat.idUsuari = usu!!.idUsuari
                usuariLogejat.nom = usu!!.nom
                usuariLogejat.contra = usu!!.contra
                usuariLogejat.tipus = usu!!.tipus


                runBlocking {
                    val corrutina = launch {
                        var idCarrito: Int = crudApi.getIdCarret(usuariLogejat.idUsuari)

                        carret.idCarret = idCarrito

                    }
                    corrutina.join()
                }


            } else if (usu != null && usu!!.tipus.equals("Admin")) {
                val intent = Intent(this, Adminitrador::class.java)
                ContextCompat.startActivity(this, intent, null)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Error: nom d'usuari i/o PW erronis", Toast.LENGTH_LONG).show()

            }

        }

        binding.btRegistre.setOnClickListener() {
            val intent = Intent(this, Registre::class.java)
            ContextCompat.startActivity(this, intent, null)
            binding.btRegistre.setAlpha(0.25f);
            binding.btRegistre.animate().alpha(1f).setDuration(1500);
        }

    }

    override fun onBackPressed() {

    }

}