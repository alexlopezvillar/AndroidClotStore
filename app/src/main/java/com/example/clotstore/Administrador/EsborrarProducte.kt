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
import com.example.clotstore.Adaptadors.AdaptadorEsborrarProducte
import com.example.clotstore.databinding.ActivityEsborrarProducteBinding
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking



class EsborrarProducte : AppCompatActivity() {
    private lateinit var binding: ActivityEsborrarProducteBinding
    private lateinit var adapter: AdaptadorEsborrarProducte
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEsborrarProducteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()

        binding.btBack.setOnClickListener() {
            val intent = Intent(this, Adminitrador::class.java)
            ContextCompat.startActivity(this, intent, null)
        }



        runBlocking {
            val corrutina = launch {
                val crudApi = CRUDApi()
                llistaProductes = crudApi.getAllProductes()
            }
            corrutina.join()
        }

        if (llistaProductes.isNotEmpty()) {
            adapter = AdaptadorEsborrarProducte(llistaProductes, binding.etCerca)
            binding.rvproductes.adapter = adapter
            binding.rvproductes.layoutManager = GridLayoutManager(this, 2)

        }
    }
}