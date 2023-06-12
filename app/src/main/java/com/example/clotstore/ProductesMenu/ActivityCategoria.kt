package com.example.clotstore.ProductesMenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clotstore.API.*
import com.example.clotstore.Adaptadors.AdaptadorProductesMenu
import com.example.clotstore.databinding.ActivityCategoriaBinding
import kotlinx.coroutines.*
import okhttp3.logging.HttpLoggingInterceptor
import kotlin.coroutines.CoroutineContext

class ActivityCategoria : AppCompatActivity() , CoroutineScope {
    private lateinit var binding: ActivityCategoriaBinding
    private lateinit var adapterSamarretes: AdaptadorProductesMenu
    private lateinit var adapterPantalons: AdaptadorProductesMenu
    private lateinit var adapterJaquetes: AdaptadorProductesMenu
    private lateinit var adapterCalcat: AdaptadorProductesMenu
    private lateinit var adapterVestits: AdaptadorProductesMenu

    private var job: Job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoriaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()

        val idCategoria = intent.getIntExtra("idCategoria",0)
        val nomCategoria = intent.getStringExtra("nom")



        binding.nomCategoria.setText(nomCategoria)

        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        runBlocking {
            val crudApi = CRUDApi()
            val corrutina = launch {
                llistaJaquetes = crudApi.getProductesCategoria(idCategoria,1)
                llistaPantalo = crudApi.getProductesCategoria(idCategoria,2)
                llistaSamarretes = crudApi.getProductesCategoria(idCategoria,3)
                llistaCalcat = crudApi.getProductesCategoria(idCategoria,4)
                llistaVestits = crudApi.getProductesCategoria(idCategoria,5)
            }
            corrutina.join()
        }





        adapterSamarretes = AdaptadorProductesMenu(llistaSamarretes)
        adapterPantalons = AdaptadorProductesMenu(llistaPantalo)
        adapterJaquetes = AdaptadorProductesMenu(llistaJaquetes)
        adapterCalcat = AdaptadorProductesMenu(llistaCalcat)
        adapterVestits = AdaptadorProductesMenu(llistaVestits)

        binding.rvSamarretesCategories.adapter = adapterSamarretes
        binding.rvSamarretesCategories.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL,false)

        binding.rvPantalonsCategories.adapter = adapterPantalons
        binding.rvPantalonsCategories.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL,false)

        binding.rvJaquetesCategories.adapter = adapterJaquetes
        binding.rvJaquetesCategories.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL,false)

        binding.rvCalcatCategories.adapter = adapterCalcat
        binding.rvCalcatCategories.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL,false)



        if (llistaVestits.count()!=0){
            binding.rvVestitsCategories.adapter = adapterVestits
            binding.rvVestitsCategories.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL,false)
            binding.rvVestitsCategories.setVisibility(View.VISIBLE)
            binding.textViewVEstits.setVisibility(View.VISIBLE)

        }
        if (llistaSamarretes.count()!=0){
            binding.rvSamarretesCategories.adapter = adapterSamarretes
            binding.rvSamarretesCategories.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL,false)
            binding.rvSamarretesCategories.setVisibility(View.VISIBLE)
            binding.tvSamarretes.setVisibility(View.VISIBLE)

        }
        if (llistaJaquetes.count()!=0){
            binding.rvJaquetesCategories.adapter = adapterJaquetes
            binding.rvJaquetesCategories.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL,false)
            binding.rvJaquetesCategories.setVisibility(View.VISIBLE)
            binding.tvJaquetes.setVisibility(View.VISIBLE)

        }
        if (llistaCalcat.count()!=0){
            binding.rvCalcatCategories.adapter = adapterCalcat
            binding.rvCalcatCategories.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL,false)
            binding.rvCalcatCategories.setVisibility(View.VISIBLE)
            binding.tvCalcat.setVisibility(View.VISIBLE)

        }
        if (llistaPantalo.count()!=0){
            binding.rvPantalonsCategories.adapter = adapterPantalons
            binding.rvPantalonsCategories.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL,false)
            binding.rvPantalonsCategories.setVisibility(View.VISIBLE)
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


