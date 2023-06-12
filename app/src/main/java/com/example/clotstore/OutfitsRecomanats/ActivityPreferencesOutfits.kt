package com.example.clotstore.OutfitsRecomanats

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.clotstore.Preferences.Preferences
import com.example.clotstore.databinding.ActivityPreferencesOutfitsBinding
import kotlinx.coroutines.Job

class ActivityPreferencesOutfits : AppCompatActivity() {
    private lateinit var binding:ActivityPreferencesOutfitsBinding

    private var job: Job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreferencesOutfitsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()

        binding.btContinua.setOnClickListener(){
            val intent = Intent(this, Preferences::class.java)
            ContextCompat.startActivity(this, intent, null)
        }
    }
}