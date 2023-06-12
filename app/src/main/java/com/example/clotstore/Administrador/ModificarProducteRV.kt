package com.example.clotstore.Administrador

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import com.example.clotstore.API.CRUDApi
import com.example.clotstore.API.Producte
import com.example.clotstore.API.llistaProductes
import com.example.clotstore.Adaptadors.AdaptadorModificarProducte
import com.example.clotstore.databinding.ActivityModificarProducteRvBinding
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ModificarProducteRV : AppCompatActivity() {
    private lateinit var binding: ActivityModificarProducteRvBinding
    private lateinit var adapter: AdaptadorModificarProducte
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModificarProducteRvBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()

        binding.btBack.setOnClickListener() {
            val intent = Intent(this, Adminitrador::class.java)
            ContextCompat.startActivity(this, intent, null)
        }

        binding.etCerca.addTextChangedListener { filtre ->
            val novallista = llistaProductes.filter { producte ->
                producte.nom.lowercase().contains(filtre.toString().lowercase())
            } as ArrayList<Producte>
            adapter.updateLlista(novallista)
        }

        runBlocking {
            val corrutina = launch {
                val crudApi = CRUDApi()
                llistaProductes = crudApi.getAllProductes()
            }
            corrutina.join()
        }

        if (llistaProductes.isNotEmpty()) {
            adapter = AdaptadorModificarProducte(llistaProductes)
            binding.rvproductes.adapter = adapter
            binding.rvproductes.layoutManager = GridLayoutManager(this, 2)

        }
    }
}