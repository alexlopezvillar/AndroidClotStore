package com.example.clotstore.Administrador

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.core.content.ContextCompat
import com.example.clotstore.API.CRUDApi
import com.example.clotstore.API.llistaTallas
import com.example.clotstore.R
import com.example.clotstore.databinding.ActivityAfegirExistenciesBinding
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AfegirExistencies : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivityAfegirExistenciesBinding
    private var tallas: ArrayList<String> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        var isNumber: Boolean = false
        super.onCreate(savedInstanceState)
        binding = ActivityAfegirExistenciesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()
        val idProducte = intent.getIntExtra("idProducte", 0)
        val prenda = intent.getIntExtra("prenda", 0)
        val adapterSpinnerTalla = ArrayAdapter(this, R.layout.spinner_layout, tallas)
        binding.spinnerTalla.adapter = adapterSpinnerTalla
        binding.spinnerTalla.onItemSelectedListener = this

        binding.btBack.setOnClickListener() {
            val intent = Intent(this, ModificarProducteRV::class.java)
            ContextCompat.startActivity(this, intent, null)
        }

        runBlocking {
            val corrutina = launch {
                val crudApi = CRUDApi()

                if (prenda == 4) {
                    isNumber = true
                    binding.spinnerTalla.setAdapter(null)
                    tallas.clear()
                } else {
                    isNumber = false
                    binding.spinnerTalla.setAdapter(null)
                    tallas.clear()
                }

                llistaTallas = crudApi.getTallas(isNumber)

                for (t in llistaTallas) {
                    tallas.add(t.nom)
                }

                binding.spinnerTalla.adapter =
                    ArrayAdapter(this@AfegirExistencies, R.layout.spinner_layout, tallas)
                binding.spinnerTalla.onItemSelectedListener = this@AfegirExistencies
            }
            corrutina.join()
        }


        binding.btAfegirExt.setOnClickListener() {

            if (binding.etQuant.text.toString().isEmpty() || binding.etQuant.text.toString()
                    .isBlank()
            ) {

                Toast.makeText(
                    this@AfegirExistencies,
                    "No pots afegir 0 existències",
                    LENGTH_LONG
                )
                    .show()

            } else if (binding.etQuant.text.toString().toInt() <= 0) {
                Toast.makeText(
                    this@AfegirExistencies,
                    "No pots afegir 0 existències",
                    LENGTH_LONG
                )
                    .show()


            } else {
                runBlocking {
                    val corrutina = launch {
                        val crudApi = CRUDApi()
                        crudApi.postTallaPorducte(
                            idProducte,
                            binding.spinnerTalla.selectedItem.toString(),
                            binding.etQuant.text.toString().toInt()
                        )


                    }
                    Toast.makeText(
                        this@AfegirExistencies,
                        "Existències afegides correctament",
                        LENGTH_LONG
                    ).show()


                    corrutina.join()

                }
            }

        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {


    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}
