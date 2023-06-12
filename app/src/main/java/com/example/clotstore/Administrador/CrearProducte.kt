package com.example.clotstore.Administrador

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.clotstore.API.*
import com.example.clotstore.R
import com.example.clotstore.databinding.ActivityCrearProducteBinding
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CrearProducte : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    var p: Producte? = Producte(0, "", 0.0, "", 0, 0, 0, 0, 0)
    private lateinit var binding: ActivityCrearProducteBinding
    private var marcas: ArrayList<String> = arrayListOf()
    private var categorias: ArrayList<String> = arrayListOf()
    private var prendas: ArrayList<String> = arrayListOf()
    private var tallas: ArrayList<String> = arrayListOf()
    private var quantitats: ArrayList<String> = arrayListOf()
    private var estils: ArrayList<String> = arrayListOf()
    private var temporades: ArrayList<String> = arrayListOf()
    var isNumber: Boolean = false
    var imageURI: Uri? = null
    var acceptat: Boolean? = null
    var permisos_request_code = 10


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearProducteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        activaPermisos()
        supportActionBar!!.hide()


        binding.btBack.setOnClickListener() {
            val intent = Intent(this, Adminitrador::class.java)
            ContextCompat.startActivity(this, intent, null)
        }
        ListasString()

        binding.btImg.setOnClickListener {
            val gallery =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI).also {
                    it.type = "image/*"
                    val mimeTypes = arrayOf("image/jpeg", "image/png")
                    it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)

                    // Excluir Google Photos y aplicaciones comunes de administración de archivos de Android
                    val excludedPackageNames = listOf(
                        "com.google.android.apps.photos",
                        "com.mi.android.globalFileexplorer"
                    )
                    it.putExtra(
                        Intent.EXTRA_EXCLUDE_COMPONENTS,
                        excludedPackageNames.toTypedArray()
                    )
                }

            resultLauncher.launch(gallery)
        }


        //spinner marcas
        val adapterSpinnerMarca = ArrayAdapter(this, R.layout.spinner_layout_llarg, marcas)
        binding.spnMarca.adapter = adapterSpinnerMarca
        binding.spnMarca.onItemSelectedListener = this

        //spinner categorias
        val adapterSpinnerCateg = ArrayAdapter(this, R.layout.spinner_layout_llarg, categorias)
        binding.spnCat.adapter = adapterSpinnerCateg
        binding.spnCat.onItemSelectedListener = this

        //spinner prendas
        val adapterSpinnerPrenda = ArrayAdapter(this, R.layout.spinner_layout_llarg, prendas)
        binding.spnPrenda.adapter = adapterSpinnerPrenda
        binding.spnPrenda.onItemSelectedListener = this

        //spinner estils
        val adapterSpinnerEstil = ArrayAdapter(this, R.layout.spinner_layout_llarg, estils)
        binding.spnEstil.adapter = adapterSpinnerEstil
        binding.spnEstil.onItemSelectedListener = this

        //spinner temporades
        val adapterSpinnerTempo = ArrayAdapter(this, R.layout.spinner_layout_llarg, temporades)
        binding.spnTempo.adapter = adapterSpinnerTempo
        binding.spnTempo.onItemSelectedListener = this

        //spinner talles
        val adapterSpinnerTalla = ArrayAdapter(this, R.layout.spinner_layout_llarg, tallas)
        binding.spTalla.adapter = adapterSpinnerTalla
        binding.spTalla.onItemSelectedListener = this


        binding.btCrearProducte.setOnClickListener {
            if (binding.etPreu.text.isEmpty() || binding.etNom.text.isEmpty() || binding.etQuant.text.isEmpty() || imageURI == null) {
                Toast.makeText(this, "Ha de d'omplir tots els camps.", Toast.LENGTH_LONG).show()
            } else {
                p!!.preu = binding.etPreu.text.toString().toDouble()
                p!!.nom = binding.etNom.text.toString()

                //insertar producto en la base de datos
                runBlocking {
                    val corrutina = launch {
                        val crudApi = CRUDApi()

                        p = crudApi.postProducte(
                            p!!.nom,
                            p!!.preu,
                            p!!.categoria,
                            p!!.prenda,
                            p!!.marca,
                            p!!.temporada,
                            p!!.estil,
                            imageURI!!.path.toString(),
                            p!!.nom + ".jpg"
                        )

                        var quant: Int = binding.etQuant.text.toString().toInt()
                        crudApi.postTallaPorducte(
                            p!!.idProducte,
                            binding.spTalla.selectedItem.toString(),
                            quant
                        )
//                        if(imageURI!!.path.toString() !=null) {
//                            if (crudApi.postImatge(imageURI!!.path.toString(), p!!.nom + ".jpg")) {
//
//                                llistaProductes.add(p!!)
//                            } else {
//                                Toast.makeText(
//                                    this@CrearProducte,
//                                    "S'ha produït un error al pujar la imatge",
//                                    Toast.LENGTH_LONG
//                                ).show()
//                            }
//                        }
                    }
                    corrutina.join()
                }
                Toast.makeText(
                    this,
                    "Producte: " + p!!.nom + " afegit\nEl numero total de productes es: " + llistaProductes.count(),
                    Toast.LENGTH_LONG
                ).show()
                borrarFormulario()
            }

        }
    }

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                var rutaArxiu = result.data?.data!!.path
                var novarutaArxiu = ""

                if(imageURI.toString()?.contains("storage") == true){
                    novarutaArxiu =
                        TextUtils.substring(rutaArxiu, TextUtils.indexOf(rutaArxiu, "/storage"), rutaArxiu!!.length)
                } else{
                    novarutaArxiu =
                        rutaArxiu?.replace(iniciUrlDocument, iniciUrl).toString()
                }
                imageURI = Uri.parse(novarutaArxiu)
                Glide.with(this).load(imageURI.toString()).into(binding.img)


            }
        }

    private fun activaPermisos() {
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) &&
            (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED)
        ) {

        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
                Toast.makeText(
                    this,
                    "El permís WRITE EXTERNAL STORAGE no està disponible. S'ha de canviar als ajustaments",
                    Toast.LENGTH_LONG
                ).show()
                acceptat = false
            } else
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                ) {
                    Toast.makeText(
                        this,
                        "El permís READ EXTERNAL STORAGE no està disponible. S'ha de canviar als ajustaments",
                        Toast.LENGTH_LONG
                    ).show()
                    acceptat = false
                } else {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ),
                        permisos_request_code
                    )
                }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            permisos_request_code -> {
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                            grantResults[1] == PackageManager.PERMISSION_GRANTED)
                ) {
                    acceptat = true

                } else {
                    Toast.makeText(
                        this,
                        "No s'han acceptat els permisos, per poder pujar arxius canvia-ho als ajustaments",
                        Toast.LENGTH_LONG
                    ).show()
                    acceptat = false
                }
                return
            }
        }
    }


    fun borrarFormulario() {
        p = Producte(0, "", 0.0, "", 1, 1, 1, 1, 1)
        binding.etNom.text.clear()
        binding.etPreu.text.clear()
        binding.spnPrenda.setSelection(0)
        binding.spnEstil.setSelection(0)
        binding.spnCat.setSelection(0)
        binding.spnMarca.setSelection(0)
        binding.spnTempo.setSelection(0)
        Glide.with(this)
            .load("https://img.freepik.com/vector-premium/icono-marco-fotos-foto-vacia-blanco-vector-sobre-fondo-transparente-aislado-eps-10_399089-1290.jpg")
            .into(binding.img)
        binding.spTalla.setSelection(0)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (parent != null) {
            runBlocking {
                val corrutina = launch {
                    val crudApi = CRUDApi()
                    when (parent.id) {
                        R.id.spnMarca -> {
                            p!!.marca = crudApi.getMarcaNom(parent.selectedItem.toString()).idMarca
                        }
                        R.id.spnCat -> {
                            p!!.categoria =
                                crudApi.getCategoriaNom(parent.selectedItem.toString()).idCategoria
                        }
                        R.id.spnEstil -> {
                            p!!.estil = crudApi.getEstilNom(parent.selectedItem.toString()).idEstil
                        }
                        R.id.spnPrenda -> {

                            p!!.prenda =
                                crudApi.getPrendaNom(parent.selectedItem.toString()).idPrenda
                            if (parent.selectedItem.toString().equals("Sabates")) {
                                isNumber = true
                                binding.spTalla.setAdapter(null)
                                tallas.clear()
                            } else {
                                isNumber = false
                                binding.spTalla.setAdapter(null)
                                tallas.clear()
                            }

                            llistaTallas = crudApi.getTallas(isNumber)

                            for (t in llistaTallas) {
                                tallas.add(t.nom)
                            }

                            binding.spTalla.adapter =
                                ArrayAdapter(
                                    this@CrearProducte,
                                    R.layout.spinner_layout_llarg, tallas
                                )
                            binding.spTalla.onItemSelectedListener = this@CrearProducte


                        }

                        R.id.spnTempo -> {
                            p!!.temporada =
                                crudApi.getTemporadaNom(parent.selectedItem.toString()).idTemporada
                        }
                    }
                }
                corrutina.join()
            }

        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    fun ListasString() {
        runBlocking {
            val corrutina = launch {
                val crudApi = CRUDApi()
                llistaProductes = crudApi.getAllProductes()
            }
            corrutina.join()
        }
        runBlocking {
            val corrutina = launch {
                val crudApi = CRUDApi()
                llistaMarques = crudApi.getAllMarques()
                llistaCategories = crudApi.getAllCategories()
                llistaPrendes = crudApi.getAllPrendes()
                llistaEstils = crudApi.getAllEstils()
                llistaTemporades = crudApi.getAllTeporades()


            }
            corrutina.join()
        }
        for (m in llistaMarques) {
            marcas.add(m.nom)
        }
        for (c in llistaCategories) {
            categorias.add(c.nom)
        }
        for (p in llistaPrendes) {
            prendas.add(p.nom)
        }
        for (e in llistaEstils) {
            estils.add(e.nom)
        }
        for (t in llistaTemporades) {
            temporades.add(t.nom)
        }
    }


}



