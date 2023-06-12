package com.example.clotstore.Preferences

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.clotstore.API.CRUDApi
import com.example.clotstore.API.preferencesUsuari
import com.example.clotstore.API.usuariLogejat
import com.example.clotstore.FragmentsMenu.Menu
import com.example.clotstore.OutfitsRecomanats.OutfitsRecomanats
import com.example.clotstore.R
import com.example.clotstore.databinding.ActivityPreferencesBinding
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Preferences : AppCompatActivity() {
    private var npregunta = 0
    lateinit var fragment: Fragment
    private lateinit var binding: ActivityPreferencesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreferencesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        fragment = PreferencesEstil()
        transaction.replace(R.id.fc1, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

        binding.btContinua2.setOnClickListener() {
            binding.btContinua2.setAlpha(0.25f);
            binding.btContinua2.animate().alpha(1f).setDuration(1500);
            if (npregunta != 3) {
                carregarFragment()
            } else {
                if (preferencesUsuari!!.temporada != 0 &&
                    preferencesUsuari!!.estil != 0 &&
                    preferencesUsuari!!.idColor != 0 &&
                    preferencesUsuari!!.categoria != 0
                ) {

                    runBlocking {
                        val corrutina = launch {
                            preferencesUsuari!!.idUsuari = usuariLogejat.idUsuari
                            val crudApi = CRUDApi()
                            crudApi.postPreferences(preferencesUsuari!!)
                            Toast.makeText(
                                this@Preferences,
                                "Les teves preferençes s'han guardat correctament",
                                Toast.LENGTH_SHORT
                            ).show()
                            preferencesUsuari = crudApi.getPreferences(preferencesUsuari!!.idUsuari)
                        }
                        corrutina.join()
                    }
                      val intent = Intent(this, OutfitsRecomanats::class.java)
                      ContextCompat.startActivity(this, intent, null)
                }
                else{
                    Toast.makeText(
                        this@Preferences,
                        "Has de respondre totes les preguntes",
                        Toast.LENGTH_LONG
                    ).show()

                }
            }
        }




        binding.btBack.setOnClickListener() {
            val intent = Intent(this, Menu::class.java)
            ContextCompat.startActivity(this, intent, null)
        }
        binding.btEnrrere.setOnClickListener() {
            binding.btEnrrere.setAlpha(0.25f);
            binding.btEnrrere.animate().alpha(1f).setDuration(1500);
            if (npregunta > 0) {
                tornaEnrrere()
            }
        }
    }

    public override fun onBackPressed() {

    }

    fun carregarFragment() {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        if (npregunta == 0) {
            fragment = PreferencesTemporada()
        } else if (npregunta == 1) {
            fragment = PreferencesCategoria()
        } else if (npregunta == 2) {
            fragment = PreferencesColor()
        }
        npregunta++
        transaction.replace(R.id.fc1, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

    }

    fun tornaEnrrere() {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        if (npregunta == 1) {
            fragment = PreferencesEstil()
        } else if (npregunta == 2) {
            fragment = PreferencesTemporada()
        } else if (npregunta == 3) {
            fragment = PreferencesCategoria()
        } else if (npregunta == 4) {
            fragment = PreferencesColor()
        }
        npregunta--
        transaction.replace(R.id.fc1, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

    }
}
