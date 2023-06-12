package com.example.clotstore.RegistreLogin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.clotstore.API.CRUDApi
import com.example.clotstore.API.Usuari
import com.example.clotstore.databinding.ActivityRegistreBinding
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.regex.Matcher
import java.util.regex.Pattern

class Registre : AppCompatActivity() {
    private lateinit var binding: ActivityRegistreBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()

        binding.btRegistre.setOnClickListener() {
            binding.btRegistre.setAlpha(0.25f);
            binding.btRegistre.animate().alpha(1f).setDuration(1500);

            val nomusuari = binding.etNom.text.toString()
            val pass1 = binding.etContrasenya.text.toString()
            val pass2 = binding.etConfirma.text.toString()
            val patternMayuscula = Pattern.compile("[A-Z]")
            val patternNumero = Pattern.compile("\\d")
            val matcherMayuscula = patternMayuscula.matcher(pass1)
            val matcherNumero = patternNumero.matcher(pass1)
            val patron = Pattern.compile("[a-zA-Z0-9]+")
            val matcher: Matcher = patron.matcher(pass1)
            if (pass1 != pass2) {
                binding.etConfirma.text.clear()
                Toast.makeText(
                    this,
                    "El password i la seva confirmació no coincideixen",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                if (matcherMayuscula.find() && matcherNumero.find() && pass1.length >= 8 && pass1.length <= 16 && matcher.matches() && nomusuari.length >= 4 && nomusuari.length <= 16) {
                    runBlocking {
                        val corrutina = launch {
                            val crudApi = CRUDApi()
                            if (crudApi.existeixUsuari(nomusuari)) {
                                runOnUiThread {
                                    Toast.makeText(
                                        applicationContext,
                                        "L'usuari " + nomusuari + " ja existeix",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            } else {
                                var usuari =
                                    Usuari(null, nomusuari, Sha.calculateSHA(pass1), "Cliente")
                                if (crudApi.afegeixUsuari(usuari)) {
                                    usuari.idUsuari = crudApi.getIdUsuari(usuari.nom)!!
                                    crudApi.creaCarret(usuari.idUsuari)
                                    runOnUiThread {
                                        Toast.makeText(
                                            applicationContext,
                                            "Usuari afegit",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        val intent =
                                            Intent(this@Registre, IniciarSessio::class.java)
                                        intent.putExtra("nomusu", binding.etNom.text.toString())
                                        intent.putExtra(
                                            "contra",
                                            binding.etContrasenya.text.toString()
                                        )
                                        ContextCompat.startActivity(this@Registre, intent, null)
                                    }
                                } else {
                                    runOnUiThread {
                                        Toast.makeText(
                                            applicationContext,
                                            "Hi ha algun problema afegint l'usuari",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                            }
                        }
                    }
                } else {
                    Toast.makeText(
                        applicationContext,
                        "El teu nom i la teva contrasenya han de tenir una longitud de 8 a 16 caràcters, la contrasenya ha de contindre una majúscula i un nombre i no pot tenir caràcters especials",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        binding.btBack.setOnClickListener() {
            val intent = Intent(this, IniciarSessio::class.java)
            ContextCompat.startActivity(this, intent, null)
        }
    }
}