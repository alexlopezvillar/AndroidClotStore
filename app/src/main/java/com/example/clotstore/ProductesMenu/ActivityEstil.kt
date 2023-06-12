package com.example.clotstore.ProductesMenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clotstore.API.*
import com.example.clotstore.Adaptadors.AdaptadorProductesMenu
import com.example.clotstore.databinding.ActivityEstilBinding
import kotlinx.coroutines.*
import okhttp3.logging.HttpLoggingInterceptor
import kotlin.coroutines.CoroutineContext

class ActivityEstil : AppCompatActivity() , CoroutineScope {
    private lateinit var binding: ActivityEstilBinding
    private lateinit var adapterSamarretes: AdaptadorProductesMenu
    private lateinit var adapterPantalons: AdaptadorProductesMenu
    private lateinit var adapterJaquetes: AdaptadorProductesMenu
    private lateinit var adapterCalcat: AdaptadorProductesMenu
    private lateinit var adapterVestits: AdaptadorProductesMenu

    private var job: Job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEstilBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()


        val
                idEstil = intent.getIntExtra("idEstil",0)
        val nomEstil = intent.getStringExtra("nom")

        binding.nomEStil.setText(nomEstil)

        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        runBlocking {
            val crudApi = CRUDApi()
            val corrutina = launch {
                llistaJaquetes = crudApi.getProductesEstil(idEstil,1)
                llistaPantalo = crudApi.getProductesEstil(idEstil,2)
                llistaSamarretes = crudApi.getProductesEstil(idEstil,3)
                llistaCalcat = crudApi.getProductesEstil(idEstil,4)
                llistaVestits = crudApi.getProductesEstil(idEstil,5)
            }
            corrutina.join()
        }





        adapterSamarretes = AdaptadorProductesMenu(llistaSamarretes)
        adapterPantalons = AdaptadorProductesMenu(llistaPantalo)
        adapterJaquetes = AdaptadorProductesMenu(llistaJaquetes)
        adapterCalcat = AdaptadorProductesMenu(llistaCalcat)
        adapterVestits = AdaptadorProductesMenu(llistaVestits)

        binding.rvSamarretesEstilss.adapter = adapterSamarretes
        binding.rvSamarretesEstilss.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL,false)

        binding.rvPantalonsEstilss.adapter = adapterPantalons
        binding.rvPantalonsEstilss.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL,false)

        binding.rvJaquetesEstilss.adapter = adapterJaquetes
        binding.rvJaquetesEstilss.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL,false)

        binding.rvCalcatEstilss.adapter = adapterCalcat
        binding.rvCalcatEstilss.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL,false)

        if (llistaVestits.count()!=0){
            binding.rvVestitsEstil.adapter = adapterVestits
            binding.rvVestitsEstil.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL,false)
            binding.rvVestitsEstil.setVisibility(View.VISIBLE)
            binding.textViewVEstits.setVisibility(View.VISIBLE)

        }
        if (llistaSamarretes.count()!=0){
            binding.rvSamarretesEstilss.adapter = adapterSamarretes
            binding.rvSamarretesEstilss.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL,false)
            binding.rvSamarretesEstilss.setVisibility(View.VISIBLE)
            binding.tvSamarretes.setVisibility(View.VISIBLE)

        }
        if (llistaJaquetes.count()!=0){
            binding.rvJaquetesEstilss.adapter = adapterJaquetes
            binding.rvJaquetesEstilss.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL,false)
            binding.rvJaquetesEstilss.setVisibility(View.VISIBLE)
            binding.tvJaquetes.setVisibility(View.VISIBLE)

        }
        if (llistaCalcat.count()!=0){
            binding.rvCalcatEstilss.adapter = adapterCalcat
            binding.rvCalcatEstilss.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL,false)
            binding.rvCalcatEstilss.setVisibility(View.VISIBLE)
            binding.tvCalcat.setVisibility(View.VISIBLE)

        }
        if (llistaPantalo.count()!=0){
            binding.rvPantalonsEstilss.adapter = adapterPantalons
            binding.rvPantalonsEstilss.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL,false)
            binding.rvPantalonsEstilss.setVisibility(View.VISIBLE)
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


