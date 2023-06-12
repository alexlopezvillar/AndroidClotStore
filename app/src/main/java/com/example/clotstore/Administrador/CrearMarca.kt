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
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.clotstore.API.*
import com.example.clotstore.R
import com.example.clotstore.databinding.ActivityCrearMarcaBinding
import com.example.clotstore.databinding.ActivityCrearProducteBinding
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CrearMarca : AppCompatActivity() {
    private lateinit var binding: ActivityCrearMarcaBinding
    var imageURI: Uri? = null
    var acceptat: Boolean? = null
    var permisos_request_code = 10
    var m: Marca? = Marca(0, "", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearMarcaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        activaPermisos()
        supportActionBar!!.hide()

        binding.btBack.setOnClickListener() {
            val intent = Intent(this, Adminitrador::class.java)
            ContextCompat.startActivity(this, intent, null)
        }


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


        binding.btCrearMarca.setOnClickListener {
            if (binding.etNom.text.isEmpty() || imageURI == null) {
                Toast.makeText(this, "Ha de d'omplir tots els camps.", Toast.LENGTH_LONG).show()
            } else {
                m!!.nom = binding.etNom.text.toString()

                //insertar producto en la base de datos
                runBlocking {
                    val corrutina = launch {
                        val crudApi = CRUDApi()

                        m = crudApi.postMarca(
                            m!!.nom,
                            imageURI!!.path.toString(),
                            m!!.nom + ".jpg"
                        )


                    }
                    corrutina.join()
                }
                Toast.makeText(
                    this,
                    "Marca: " + m!!.nom + " afegida",
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

    fun borrarFormulario() {
        m = Marca(0, "", "")
        Glide.with(this)
            .load("https://img.freepik.com/vector-premium/icono-marco-fotos-foto-vacia-blanco-vector-sobre-fondo-transparente-aislado-eps-10_399089-1290.jpg")
            .into(binding.img)
        binding.etNom.setText("")
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

}