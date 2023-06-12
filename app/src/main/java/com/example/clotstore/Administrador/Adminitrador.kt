package com.example.clotstore.Administrador

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.clotstore.RegistreLogin.IniciarSessio
import com.example.clotstore.databinding.ActivityAdminitradorBinding

class Adminitrador : AppCompatActivity() {

    private lateinit var binding: ActivityAdminitradorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        binding = ActivityAdminitradorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btCrear.setOnClickListener {
            val intent = Intent(this, CrearProducte::class.java)
            startActivity(intent)
        }

        binding.btEsborrar.setOnClickListener {
            val intent = Intent(this, EsborrarProducte::class.java)
            startActivity(intent)
        }


        binding.btModificar.setOnClickListener() {
            val intent = Intent(this, ModificarProducteRV::class.java)
            ContextCompat.startActivity(this, intent, null)
        }

        binding.btCrearMarca.setOnClickListener() {
            val intent = Intent(this, CrearMarca::class.java)
            ContextCompat.startActivity(this, intent, null)
        }

        binding.btSortir.setOnClickListener() {
            Sortir()
        }

        binding.btEsborrarMarca.setOnClickListener {
            val intent = Intent(this, EsborrarMarca::class.java)
            startActivity(intent)
        }
    }


    fun Sortir() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Segur que vols tancar la sessió?")
            .setPositiveButton("Acceptar", DialogInterface.OnClickListener { dialog, id ->
                val intent = Intent(this, IniciarSessio::class.java)
                ContextCompat.startActivity(this, intent, null)
                Toast.makeText(this,"Sessió tancada", Toast.LENGTH_SHORT).show()
            })
            .setNegativeButton("Cancel·lar", DialogInterface.OnClickListener { dialog, id ->

            })

        // Mostra el diàleg
        builder.show()
    }

    override fun onBackPressed() {

    }
}