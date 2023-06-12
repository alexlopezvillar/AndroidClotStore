package com.example.clotstore.FragmentsMenu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clotstore.API.CRUDApi
import com.example.clotstore.API.llistaCategories
import com.example.clotstore.Adaptadors.AdaptadorCategories
import com.example.clotstore.databinding.FragmentSeleccionaCategoriaBinding
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class SeleccionaCategoria : Fragment() {

    private lateinit var binding:FragmentSeleccionaCategoriaBinding
    private lateinit var adapter: AdaptadorCategories

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSeleccionaCategoriaBinding.inflate(inflater, container, false)

        runBlocking {
            val crudApi = CRUDApi()
            val corrutina = launch {
                llistaCategories = crudApi.getAllCategories()
            }
            corrutina.join()
        }

        binding.rvCategories.layoutManager = LinearLayoutManager(container?.context)


        binding.rvCategories.layoutManager = LinearLayoutManager(container?.context)
        adapter = AdaptadorCategories(llistaCategories)
        binding.rvCategories.adapter = adapter
        binding.rvCategories.layoutManager = GridLayoutManager(binding.rvCategories.context,2)
        return binding.root

        return binding.root
    }






}