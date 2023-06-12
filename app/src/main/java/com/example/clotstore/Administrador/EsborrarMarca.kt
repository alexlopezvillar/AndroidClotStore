package com.example.clotstore.Administrador

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.clotstore.API.CRUDApi
import com.example.clotstore.API.llistaMarques
import com.example.clotstore.API.llistaProductes
import com.example.clotstore.Adaptadors.AdaptadorEsborrarMarques
import com.example.clotstore.Adaptadors.AdaptadorEsborrarProducte
import com.example.clotstore.databinding.ActivityEsborrarMarcaBinding

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class EsborrarMarca : AppCompatActivity() {
    private lateinit var binding: ActivityEsborrarMarcaBinding
    private lateinit var adapter: AdaptadorEsborrarMarques
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEsborrarMarcaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()

        binding.btBack.setOnClickListener() {
            val intent = Intent(this, Adminitrador::class.java)
            ContextCompat.startActivity(this, intent, null)
        }



        runBlocking {
            val corrutina = launch {
                val crudApi = CRUDApi()
                llistaMarques = crudApi.getAllMarques()

            }
            corrutina.join()
        }

        if (llistaMarques.isNotEmpty()) {
            adapter = AdaptadorEsborrarMarques(llistaMarques, binding.etCerca)
            binding.rvMarques.adapter = adapter
            binding.rvMarques.layoutManager = GridLayoutManager(this, 2)

        }
    }
}