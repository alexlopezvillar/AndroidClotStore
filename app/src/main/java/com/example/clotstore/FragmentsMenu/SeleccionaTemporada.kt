package com.example.clotstore.FragmentsMenu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clotstore.API.CRUDApi
import com.example.clotstore.API.llistaTemporades
import com.example.clotstore.Adaptadors.AdaptadorTemporades
import com.example.clotstore.databinding.FragmentSeleccionaTemporadaBinding
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class SeleccionaTemporada : Fragment() {

    private lateinit var binding:FragmentSeleccionaTemporadaBinding
    private lateinit var adapter: AdaptadorTemporades

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSeleccionaTemporadaBinding.inflate(inflater, container, false)

        runBlocking {
            val crudApi = CRUDApi()
            val corrutina = launch {
                llistaTemporades = crudApi.getAllTeporades()
            }
            corrutina.join()
        }

        binding.rvTemporades.layoutManager = LinearLayoutManager(container?.context)


        binding.rvTemporades.layoutManager = LinearLayoutManager(container?.context)
        adapter = AdaptadorTemporades(llistaTemporades)
        binding.rvTemporades.adapter = adapter
        binding.rvTemporades.layoutManager = GridLayoutManager(binding.rvTemporades.context, 2)
        return binding.root


    }
}