package com.example.clotstore.OutfitsRecomanats

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clotstore.API.*
import com.example.clotstore.Adaptadors.AdaptadorOutfits
import com.example.clotstore.FragmentsMenu.Menu
import com.example.clotstore.Preferences.Preferences
import com.example.clotstore.RegistreLogin.IniciarSessio
import com.example.clotstore.databinding.ActivityOutfitsRecomnatsBinding

import kotlinx.coroutines.*
import okhttp3.logging.HttpLoggingInterceptor
import kotlin.coroutines.CoroutineContext


class OutfitsRecomanats : AppCompatActivity(), CoroutineScope {
    private lateinit var binding: ActivityOutfitsRecomnatsBinding
    private lateinit var adapterOutfit1: AdaptadorOutfits

    private var job: Job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOutfitsRecomnatsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()
        carrega()

        binding.btRefresh.setOnClickListener() {
            val anim = ObjectAnimator.ofFloat(binding.btRefresh, "rotation", 0f, -360f)
            anim.duration = 1000
            anim.start()
            carrega()

        }

        binding.btBack.setOnClickListener() {
            val intent = Intent(this, Menu::class.java)
            ContextCompat.startActivity(this, intent, null)

        }
        binding.btPref.setOnClickListener() {
            binding.btPref.setAlpha(0.25f);
            binding.btPref.animate().alpha(1f).setDuration(1500);
            val intent = Intent(this, Preferences::class.java)
            ContextCompat.startActivity(this, intent, null)
        }


    }

    public fun carrega() {


            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            runBlocking {
                val crudApi = CRUDApi()
                val corrutina = launch {
                    outfit1 = crudApi.getOutfits(preferencesUsuari!!.idPreferences)
                }
                corrutina.join()
            }


        if (outfit1!!.count() >= 3) {

            adapterOutfit1 = AdaptadorOutfits(outfit1)



            binding.rvOutfit1.adapter = adapterOutfit1
            binding.rvOutfit1.layoutManager = LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL, false
            )





            binding.btBack.setOnClickListener() {
                val intent = Intent(this, Menu::class.java)
                ContextCompat.startActivity(this, intent, null)

            }
        } else {
            val intent = Intent(this, Preferences::class.java)
            ContextCompat.startActivity(this, intent, null)
            Toast.makeText(
                this,
                "No hi ha outfits adequats per les teves preferences, prova a cambiar-les",
                LENGTH_LONG
            ).show()
        }

    }

    override fun onBackPressed() {
        val intent = Intent(this, Menu::class.java)
        ContextCompat.startActivity(this, intent, null)
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

}


