package com.example.clotstore.FragmentsMenu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clotstore.API.CRUDApi
import com.example.clotstore.API.llistaPrendes
import com.example.clotstore.Adaptadors.AdaptadorPrendas
import com.example.clotstore.databinding.FragmentSeleccionaPrendaBinding
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SeleccionaPrenda : Fragment() {

    private lateinit var binding: FragmentSeleccionaPrendaBinding
    private lateinit var adapter: AdaptadorPrendas

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSeleccionaPrendaBinding.inflate(inflater, container, false)

        runBlocking {
            val crudApi = CRUDApi()
            val corrutina = launch {
                llistaPrendes = crudApi.getAllPrendes()
            }
            corrutina.join()
        }

        binding.rvPrendes.layoutManager = LinearLayoutManager(container?.context)


        binding.rvPrendes.layoutManager = LinearLayoutManager(container?.context)
        adapter = AdaptadorPrendas(llistaPrendes)
        binding.rvPrendes.adapter = adapter
        binding.rvPrendes.layoutManager = GridLayoutManager(binding.rvPrendes.context, 2)
        return binding.root

        return binding.root
    }
}