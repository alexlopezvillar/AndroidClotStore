package com.example.clotstore.FragmentsMenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clotstore.API.CRUDApi
import com.example.clotstore.API.llistaMarques
import com.example.clotstore.Adaptadors.AdaptadorMarques

import com.example.clotstore.databinding.FragmentSeleccionaMarcaBinding
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class SeleccionaMarca : Fragment() {

    private lateinit var binding:FragmentSeleccionaMarcaBinding
    private lateinit var adapter: AdaptadorMarques

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSeleccionaMarcaBinding.inflate(inflater, container, false)

        runBlocking {
            val crudApi = CRUDApi()
            val corrutina = launch {
                llistaMarques = crudApi.getAllMarques()
            }
            corrutina.join()
        }



        binding.rvMarcas.layoutManager = LinearLayoutManager(container?.context)
        adapter = AdaptadorMarques(llistaMarques)
        binding.rvMarcas.adapter = adapter
        binding.rvMarcas.layoutManager = GridLayoutManager(binding.rvMarcas.context,2)
        return binding.root


    }




}