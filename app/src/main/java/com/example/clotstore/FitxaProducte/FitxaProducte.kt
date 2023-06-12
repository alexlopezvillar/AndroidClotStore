package com.example.clotstore.FitxaProducte

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.clotstore.API.*
import com.example.clotstore.CarretFactura.CarretActivity
import com.example.clotstore.FragmentsMenu.Menu
import com.example.clotstore.R
import com.example.clotstore.databinding.ActivityFitxaProducteBinding
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class FitxaProducte : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivityFitxaProducteBinding
    var tallas: ArrayList<String> = arrayListOf()
    var spinerTalla: String = ""
    var quantitats: ArrayList<Int> = arrayListOf()
    var spinnerQuant: String = ""
    var preuProducte: Double = 0.0
    var preuFinal: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFitxaProducteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()

        var marca: Marca? = null
        var estil: Estil? = null
        var temporada: Temporada? = null
        var prenda: Prenda? = null
        var categoria: Categoria? = null
        var isNumber: Boolean = false

        val idProducte = intent.getIntExtra("id", 0)
        val nomProducte = intent.getStringExtra("nom")
        preuProducte = intent.getDoubleExtra("preu", 0.0)
        val imatgeProducte = intent.getStringExtra("imatge")
        val idmarca = intent.getIntExtra("marca", 0)
        val idestil = intent.getIntExtra("estil", 0)
        val idtemporada = intent.getIntExtra("temporada", 0)
        val idprenda = intent.getIntExtra("prenda", 0)
        val idcategoria = intent.getIntExtra("categoria", 0)

        quantitats.addAll(
            listOf(
                1, 2, 3, 4, 5
            )
        )

        runBlocking {
            val crudApi = CRUDApi()
            val corrutina = launch {
                marca = crudApi.getMarcaId(idmarca)
                estil = crudApi.getEstilId(idestil)
                temporada = crudApi.getTemporadaId(idtemporada)
                prenda = crudApi.getPrendaId(idprenda)
                categoria = crudApi.getCategoriaId(idcategoria)

                if (prenda!!.nom.equals("Sabates")) {
                    isNumber = true
                }
                llistaTallas = crudApi.getTallas(isNumber)



                for (t in llistaTallas) {
                    tallas.add(t.nom)
                }

                binding.spTalla.adapter =
                    ArrayAdapter(this@FitxaProducte, R.layout.spinner_layout, tallas)
                binding.spTalla.onItemSelectedListener = this@FitxaProducte

                binding.spQuantitat.adapter =
                    ArrayAdapter(this@FitxaProducte, R.layout.spinner_layout, quantitats)
                binding.spQuantitat.onItemSelectedListener = this@FitxaProducte
            }
            corrutina.join()
        }
        binding.btBack.setOnClickListener() {
            val intent = Intent(this, Menu::class.java)
            ContextCompat.startActivity(this, intent, null)
        }
        binding.tvNomProducte.setText(nomProducte)
        binding.preuProducte.setText(preuProducte.toString() + "€")
        binding.tvMarca.setText("Fabricant: " + marca?.nom)
        binding.tvCategoria.setText("Categoria: " + categoria?.nom)
        binding.tvEstil.setText("Estil: " + estil?.nom)
        binding.tvTipusPrenda.setText("Tipus: " + prenda?.nom)
        binding.tvTemporada.setText("Temporada: " + temporada?.nom)


        Glide.with(this).load(imatgeProducte).into(binding.imatgeProducte)

        binding.afegirCarret.setOnClickListener() {
            runBlocking {
                val corrutina = launch {
                    val crudApi = CRUDApi()
                    val existencies: Int = crudApi.getTallaProducte(
                        idProducte,
                        binding.spTalla.selectedItem.toString()
                    )
                    var quantCarret: Int = 0
                    quantCarret = crudApi.getQuantitatCarret(
                        carret.idCarret,
                        idProducte,
                        binding.spTalla.selectedItem.toString()

                    )
                    val idTallaProducte: Int = crudApi.getIdTallaProducte(
                        idProducte,
                        binding.spTalla.selectedItem.toString()
                    )
                    if (existencies >= binding.spQuantitat.selectedItem.toString()
                            .toInt() + quantCarret
                    ) {
                        crudApi.afegeixProducteCarret(
                            carret.idCarret,
                            idTallaProducte,
                            binding.spQuantitat.selectedItem.toString().toInt()
                        )

                        Toast.makeText(
                            applicationContext,
                            "Producte afegit al carret",
                            Toast.LENGTH_LONG
                        ).show()
                        val intent = Intent(this@FitxaProducte, CarretActivity::class.java)
                        ContextCompat.startActivity(this@FitxaProducte, intent, null)

                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Talla esgotada",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (parent != null) {
            when (parent.id) {
                R.id.spTalla -> {
                    spinerTalla = parent.selectedItem.toString()
                }
                R.id.spQuantitat -> {
                    spinnerQuant = parent.selectedItem.toString()
                    preuFinal = preuProducte * spinnerQuant.toDouble()
                    binding.preuProducte.setText(String.format("%.2f", preuFinal) + "€")
                }
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}