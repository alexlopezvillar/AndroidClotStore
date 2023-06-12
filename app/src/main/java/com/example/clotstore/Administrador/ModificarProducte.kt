package com.example.clotstore.Administrador

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.clotstore.API.CRUDApi
import com.example.clotstore.databinding.ActivityModificarProducteBinding
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ModificarProducte : AppCompatActivity() {
    private lateinit var binding: ActivityModificarProducteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModificarProducteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()

        val idProducte = intent.getIntExtra("id", 0)
        val nomProducte = intent.getStringExtra("nom")
        val preuProducte = intent.getDoubleExtra("preu", 0.0)
        val imatgeProducte = intent.getStringExtra("imatge")
        val prenda = intent.getIntExtra("prenda",0)

        Glide.with(this).load(imatgeProducte).into(binding.imatgeProducte)
        binding.etNom.setText(nomProducte)
        binding.etPreu.setText(preuProducte.toString())

        binding.btBack.setOnClickListener() {
            val intent = Intent(this, ModificarProducteRV::class.java)
            ContextCompat.startActivity(this, intent, null)
        }
        binding.btModificar.setOnClickListener() {
            val crudApi = CRUDApi()
            runBlocking {
                val corrutina = launch {

                    if (crudApi.putProducte(
                            idProducte,
                            binding.etNom.text.toString(),
                            binding.etPreu.text.toString().toDouble()
                        )
                    ){
                        Toast.makeText(this@ModificarProducte,"Producte modificat", Toast.LENGTH_LONG).show()
                    }

                }
                corrutina.join()
            }
        }
        binding.btAfegirExistencies.setOnClickListener() {
            val intent = Intent(this, AfegirExistencies::class.java)
            intent.putExtra("prenda", prenda)
            intent.putExtra("idProducte", idProducte)
            ContextCompat.startActivity(this, intent, null)
        }
    }

    public override fun onBackPressed() {
        val intent = Intent(this, ModificarProducteRV::class.java)
        ContextCompat.startActivity(this, intent, null)
    }
}