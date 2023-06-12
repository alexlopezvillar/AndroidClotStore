package com.example.clotstore.ProductesMenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clotstore.API.*
import com.example.clotstore.Adaptadors.AdaptadorProductesMenu
import com.example.clotstore.databinding.ActivityMarquesBinding
import kotlinx.coroutines.*
import okhttp3.logging.HttpLoggingInterceptor
import kotlin.coroutines.CoroutineContext

class ActivityMarques : AppCompatActivity(), CoroutineScope {
    private lateinit var binding: ActivityMarquesBinding
    private lateinit var adapterSamarretes: AdaptadorProductesMenu
    private lateinit var adapterPantalons: AdaptadorProductesMenu
    private lateinit var adapterJaquetes: AdaptadorProductesMenu
    private lateinit var adapterCalcat: AdaptadorProductesMenu
    private lateinit var adapterVestits: AdaptadorProductesMenu

    private var job: Job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMarquesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()

        val idMarca = intent.getIntExtra("idMarca", 0)
        val nomMarca = intent.getStringExtra("nom")

        binding.nomMarca.setText(nomMarca)

        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        runBlocking {
            val crudApi = CRUDApi()
            val corrutina = launch {
                llistaJaquetes = crudApi.getProductesMarca(idMarca, 1)
                llistaPantalo = crudApi.getProductesMarca(idMarca, 2)
                llistaSamarretes = crudApi.getProductesMarca(idMarca, 3)
                llistaCalcat = crudApi.getProductesMarca(idMarca, 4)
                llistaVestits = crudApi.getProductesMarca(idMarca, 5)
            }
            corrutina.join()
        }





        adapterSamarretes = AdaptadorProductesMenu(llistaSamarretes)
        adapterPantalons = AdaptadorProductesMenu(llistaPantalo)
        adapterJaquetes = AdaptadorProductesMenu(llistaJaquetes)
        adapterCalcat = AdaptadorProductesMenu(llistaCalcat)
        adapterVestits = AdaptadorProductesMenu(llistaVestits)

        binding.rvSamarretesMarques.adapter = adapterSamarretes
        binding.rvSamarretesMarques.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        binding.rvPantalonsMarques.adapter = adapterPantalons
        binding.rvPantalonsMarques.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        binding.rvJaquetesMarques.adapter = adapterJaquetes
        binding.rvJaquetesMarques.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        binding.rvCalcatMarques.adapter = adapterCalcat
        binding.rvCalcatMarques.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        binding.rvVestits.adapter = adapterVestits
        binding.rvVestits.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        if (llistaVestits.count() != 0) {
            binding.rvVestits.adapter = adapterVestits
            binding.rvVestits.layoutManager = LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL, false
            )
            binding.rvVestits.setVisibility(View.VISIBLE)
            binding.textViewVEstits.setVisibility(View.VISIBLE)

        }
        if (llistaSamarretes.count() != 0) {
            binding.rvSamarretesMarques.adapter = adapterSamarretes
            binding.rvSamarretesMarques.layoutManager = LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL, false
            )
            binding.rvSamarretesMarques.setVisibility(View.VISIBLE)
            binding.tvSamarretes.setVisibility(View.VISIBLE)

        }
        if (llistaJaquetes.count() != 0) {
            binding.rvJaquetesMarques.adapter = adapterJaquetes
            binding.rvJaquetesMarques.layoutManager = LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL, false
            )
            binding.rvJaquetesMarques.setVisibility(View.VISIBLE)
            binding.tvJaquetes.setVisibility(View.VISIBLE)

        }
        if (llistaCalcat.count() != 0) {
            binding.rvCalcatMarques.adapter = adapterCalcat
            binding.rvCalcatMarques.layoutManager = LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL, false
            )
            binding.rvCalcatMarques.setVisibility(View.VISIBLE)
            binding.tvCalcat.setVisibility(View.VISIBLE)

        }
        if (llistaPantalo.count() != 0) {
            binding.rvPantalonsMarques.adapter = adapterPantalons
            binding.rvPantalonsMarques.layoutManager = LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL, false
            )
            binding.rvPantalonsMarques.setVisibility(View.VISIBLE)
            binding.tvPantalons.setVisibility(View.VISIBLE)

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

}


