package com.example.clotstore.ProductesMenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clotstore.API.*
import com.example.clotstore.Adaptadors.AdaptadorProductesMenu
import com.example.clotstore.databinding.ActivityTemporadesBinding
import kotlinx.coroutines.*
import okhttp3.logging.HttpLoggingInterceptor
import kotlin.coroutines.CoroutineContext

class ActivityTemporades : AppCompatActivity(), CoroutineScope {
    private lateinit var binding: ActivityTemporadesBinding
    private lateinit var adapterSamarretes: AdaptadorProductesMenu
    private lateinit var adapterPantalons: AdaptadorProductesMenu
    private lateinit var adapterJaquetes: AdaptadorProductesMenu
    private lateinit var adapterCalcat: AdaptadorProductesMenu
    private lateinit var adapterVestits: AdaptadorProductesMenu

    private var job: Job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTemporadesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()

        val idTemporada = intent.getIntExtra("idTemporada", 0)
        val nomTemporada = intent.getStringExtra("nom")


        binding.nomTemporada.setText(nomTemporada)

        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        runBlocking {
            val crudApi = CRUDApi()
            val corrutina = launch {
                llistaJaquetes = crudApi.getProductesTemporada(idTemporada, 1)
                llistaPantalo = crudApi.getProductesTemporada(idTemporada, 2)
                llistaSamarretes = crudApi.getProductesTemporada(idTemporada, 3)
                llistaCalcat = crudApi.getProductesTemporada(idTemporada, 4)
                llistaVestits = crudApi.getProductesTemporada(idTemporada, 5)
            }
            corrutina.join()
        }





        adapterSamarretes = AdaptadorProductesMenu(llistaSamarretes)
        adapterPantalons = AdaptadorProductesMenu(llistaPantalo)
        adapterJaquetes = AdaptadorProductesMenu(llistaJaquetes)
        adapterCalcat = AdaptadorProductesMenu(llistaCalcat)
        adapterVestits = AdaptadorProductesMenu(llistaVestits)

        binding.rvSamarretesTemporades.adapter = adapterSamarretes
        binding.rvSamarretesTemporades.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL, false
        )

        binding.rvPantalonsTemporades.adapter = adapterPantalons
        binding.rvPantalonsTemporades.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL, false
        )

        binding.rvJaquetesTemporades.adapter = adapterJaquetes
        binding.rvJaquetesTemporades.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL, false
        )

        binding.rvCalcatTemporades.adapter = adapterCalcat
        binding.rvCalcatTemporades.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL, false
        )

        binding.rvVestitsTemporades.adapter = adapterVestits
        binding.rvVestitsTemporades.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL, false
        )


        if (llistaVestits.count() != 0) {
            binding.rvVestitsTemporades.adapter = adapterVestits
            binding.rvVestitsTemporades.layoutManager = LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL, false
            )
            binding.rvVestitsTemporades.setVisibility(View.VISIBLE)
            binding.textViewVEstits.setVisibility(View.VISIBLE)

        }
        if (llistaSamarretes.count() != 0) {
            binding.rvSamarretesTemporades.adapter = adapterSamarretes
            binding.rvSamarretesTemporades.layoutManager = LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL, false
            )
            binding.rvSamarretesTemporades.setVisibility(View.VISIBLE)
            binding.tvSamarretes.setVisibility(View.VISIBLE)

        }
        if (llistaJaquetes.count() != 0) {
            binding.rvJaquetesTemporades.adapter = adapterJaquetes
            binding.rvJaquetesTemporades.layoutManager = LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL, false
            )
            binding.rvJaquetesTemporades.setVisibility(View.VISIBLE)
            binding.tvJaquetes.setVisibility(View.VISIBLE)

        }

        if (llistaCalcat.count() != 0) {
            binding.rvCalcatTemporades.adapter = adapterCalcat
            binding.rvCalcatTemporades.layoutManager = LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL, false
            )
            binding.rvCalcatTemporades.setVisibility(View.VISIBLE)
            binding.tvCalcat.setVisibility(View.VISIBLE)

        }
        if (llistaPantalo.count() != 0) {
            binding.rvPantalonsTemporades.adapter = adapterPantalons
            binding.rvPantalonsTemporades.layoutManager = LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL, false
            )
            binding.rvPantalonsTemporades.setVisibility(View.VISIBLE)
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