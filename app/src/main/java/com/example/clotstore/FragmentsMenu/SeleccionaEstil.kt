package com.example.clotstore.FragmentsMenu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clotstore.API.CRUDApi
import com.example.clotstore.API.llistaEstils
import com.example.clotstore.Adaptadors.AdaptadorEstils
import com.example.clotstore.databinding.FragmentSeleccionaEstilBinding
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class SeleccionaEstil : Fragment() {

    private lateinit var binding:FragmentSeleccionaEstilBinding
    private lateinit var adapter: AdaptadorEstils

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSeleccionaEstilBinding.inflate(inflater, container, false)

        runBlocking {
            val crudApi = CRUDApi()
            val corrutina = launch {
                llistaEstils = crudApi.getAllEstils()
            }
            corrutina.join()
        }

        binding.rvEstil.layoutManager = LinearLayoutManager(container?.context)


        binding.rvEstil.layoutManager = LinearLayoutManager(container?.context)
        adapter = AdaptadorEstils(llistaEstils)
        binding.rvEstil.adapter = adapter
        binding.rvEstil.layoutManager = GridLayoutManager(binding.rvEstil.context,2)
        return binding.root

        return binding.root
    }






}