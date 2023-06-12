package com.example.clotstore.CarretFactura

import android.content.Intent
import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clotstore.API.*
import com.example.clotstore.Adaptadors.AdaptadorCarret
import com.example.clotstore.FragmentsMenu.Menu
import com.example.clotstore.databinding.ActivityCarretBinding

import kotlinx.coroutines.*
import okhttp3.logging.HttpLoggingInterceptor
import kotlin.coroutines.CoroutineContext

class CarretActivity : AppCompatActivity(), CoroutineScope {
    val crudApi = CRUDApi()
    private lateinit var adapter: AdaptadorCarret
    private var job: Job = Job()
    private lateinit var binding: ActivityCarretBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCarretBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()

        binding.btBack.setOnClickListener() {
            val intent = Intent(this, Menu::class.java)
            ContextCompat.startActivity(this, intent, null)

        }

        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        runBlocking {

            val corrutina = launch {
                llistaProductesCarret = crudApi.getProductesCarret(carret.idCarret)
            }
            corrutina.join()
        }

        binding.rvProductesCarret.layoutManager = LinearLayoutManager(this)


        binding.rvProductesCarret.layoutManager = LinearLayoutManager(this)
        adapter = AdaptadorCarret(llistaProductesCarret, binding.preuProducteCaret, binding.btComprar, binding.carretBuit)
        binding.rvProductesCarret.adapter = adapter
        binding.rvProductesCarret.layoutManager =
            GridLayoutManager(binding.rvProductesCarret.context, 1)

        runBlocking {
            val corrutina = launch {
                carret = crudApi.getCarret(carret.idCarret)
                carret.PreuTotal = crudApi.getCarretPreu(carret.idCarret)


                if (llistaProductesCarret.count()==0){
                    binding.preuProducteCaret.setText("El teu carret es buit!")
                    binding.carretBuit.visibility = VISIBLE
                }else{
                    binding.carretBuit.visibility = INVISIBLE
                }

            }
            corrutina.join()
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

}