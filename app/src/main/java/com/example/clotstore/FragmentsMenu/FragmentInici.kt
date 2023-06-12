package com.example.clotstore.FragmentsMenu

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clotstore.API.*
import com.example.clotstore.Adaptadors.AdaptadorProductesInici
import com.example.clotstore.R
import com.example.clotstore.RegistreLogin.IniciarSessio
import com.example.clotstore.databinding.FragmentIniciBinding
import kotlinx.coroutines.*
import okhttp3.logging.HttpLoggingInterceptor
import kotlin.coroutines.CoroutineContext


class FragmentInici : Fragment(), CoroutineScope {
    private lateinit var binding: FragmentIniciBinding
    private lateinit var adapter: AdaptadorProductesInici
    private var job: Job = Job()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentIniciBinding.inflate(inflater, container, false)


        binding.etCerca.addTextChangedListener { filtre ->
            val novallista = llistaProductes.filter { producte ->
                producte.nom.lowercase().contains(filtre.toString().lowercase())
            } as ArrayList<Producte>
            adapter.updateLlista(novallista)
        }

        binding.btSortir.setOnClickListener() {
            Sortir()

        }



        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        runBlocking {
            val crudApi = CRUDApi()
            val corrutina = launch {
                llistaProductes = crudApi.getAllProductes()
            }
            corrutina.join()
        }
        binding.rvproductes.layoutManager = LinearLayoutManager(container?.context)
        binding.rvproductes.layoutManager = LinearLayoutManager(container?.context)
        adapter = AdaptadorProductesInici(llistaProductes)
        binding.rvproductes.adapter = adapter
        binding.rvproductes.layoutManager = GridLayoutManager(binding.rvproductes.context, 2)

        binding.btNous.setOnClickListener() {
            runBlocking {
                val crudApi = CRUDApi()
                val corrutina = launch {
                    llistaProductes = crudApi.getAllProductes()
                }
                corrutina.join()
            }

            binding.rvproductes.layoutManager = LinearLayoutManager(container?.context)
            binding.rvproductes.layoutManager = LinearLayoutManager(container?.context)
            adapter = AdaptadorProductesInici(llistaProductes)
            binding.rvproductes.adapter = adapter
            binding.rvproductes.layoutManager = GridLayoutManager(binding.rvproductes.context, 2)
            binding.btNous.setAlpha(0.25f);
            binding.btNous.animate().alpha(1f).setDuration(1500);
            binding.etCerca.setText("")
        }

        binding.btCars.setOnClickListener() {
            runBlocking {
                val crudApi = CRUDApi()
                val corrutina = launch {
                    llistaProductes = crudApi.getAllProductesCars()
                }
                corrutina.join()
            }

            binding.rvproductes.layoutManager = LinearLayoutManager(container?.context)
            binding.rvproductes.layoutManager = LinearLayoutManager(container?.context)
            adapter = AdaptadorProductesInici(llistaProductes)
            binding.rvproductes.adapter = adapter
            binding.rvproductes.layoutManager = GridLayoutManager(binding.rvproductes.context, 2)
            binding.etCerca.setText("")
            binding.btCars.setAlpha(0.25f);
            binding.btCars.animate().alpha(1f).setDuration(1500);
        }

        binding.btBarats.setOnClickListener() {
            runBlocking {
                val crudApi = CRUDApi()
                val corrutina = launch {
                    llistaProductes = crudApi.getAllProductesBarats()
                }
                corrutina.join()
            }

            binding.rvproductes.layoutManager = LinearLayoutManager(container?.context)
            binding.rvproductes.layoutManager = LinearLayoutManager(container?.context)
            adapter = AdaptadorProductesInici(llistaProductes)
            binding.rvproductes.adapter = adapter
            binding.rvproductes.layoutManager = GridLayoutManager(binding.rvproductes.context, 2)
            binding.btBarats.setAlpha(0.25f);
            binding.btBarats.animate().alpha(1f).setDuration(1500);
            binding.etCerca.setText("")
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()

    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    fun Sortir() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Segur que vols tancar la sessió?")
            .setPositiveButton("Acceptar", DialogInterface.OnClickListener { dialog, id ->
                val intent = Intent(requireContext(), IniciarSessio::class.java)
                ContextCompat.startActivity(requireContext(), intent, null)
                Toast.makeText(requireContext(), "Sessió tancada", Toast.LENGTH_SHORT).show()
            })
            .setNegativeButton("Cancel·lar", DialogInterface.OnClickListener { dialog, id ->

            })

        // Mostra el diàleg
        builder.show()
    }


}
