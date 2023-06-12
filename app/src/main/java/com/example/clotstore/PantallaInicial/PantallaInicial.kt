package com.example.clotstore.PantallaInicial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.target.ImageViewTarget
import com.example.clotstore.R
import com.example.clotstore.RegistreLogin.IniciarSessio
import com.example.clotstore.databinding.ActivityAdminitradorBinding
import com.example.clotstore.databinding.ActivityPantallaInicialBinding
import java.util.*
import kotlin.concurrent.schedule

class PantallaInicial : AppCompatActivity() {
    private lateinit var binding: ActivityPantallaInicialBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_inicial)
        supportActionBar!!.hide()
        binding = ActivityPantallaInicialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent(this, IniciarSessio::class.java)
        Timer().schedule(4000) {
            startActivity(intent)
        }
        Glide.with(this)
            .asGif()
            .load(R.drawable.loading)
            .into(object : ImageViewTarget<GifDrawable>(binding.loadingGif) {
                override fun setResource(resource: GifDrawable?) {
                    binding.loadingGif.setImageDrawable(resource)
                    resource?.start()
                }
            })

    }
}