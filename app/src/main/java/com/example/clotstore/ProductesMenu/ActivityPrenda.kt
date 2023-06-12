package com.example.clotstore.ProductesMenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.clotstore.API.*
import com.example.clotstore.Adaptadors.AdaptadorProductesMenu
import com.example.clotstore.databinding.ActivityPrendaBinding
import kotlinx.coroutines.*
import okhttp3.logging.HttpLoggingInterceptor
import kotlin.coroutines.CoroutineContext

class ActivityPrenda : AppCompatActivity() , CoroutineScope {
    private lateinit var binding: ActivityPrendaBinding
    private lateinit var adapter: AdaptadorProductesMenu


    private var job: Job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrendaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()

        val idPrenda = intent.getIntExtra("idPrenda",0)
        val nomPrenda = intent.getStringExtra("nom")



        binding.nomPrenda.setText(nomPrenda)

        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        runBlocking {
            val crudApi = CRUDApi()
            val corrutina = launch {
                prductesPrendes = crudApi.getProductesPrenda(idPrenda)

            }
            corrutina.join()
        }





        adapter = AdaptadorProductesMenu(prductesPrendes)


        binding.rvPrenda.adapter = adapter
        binding.rvPrenda.layoutManager = GridLayoutManager(binding.rvPrenda.context,3)



    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

}
