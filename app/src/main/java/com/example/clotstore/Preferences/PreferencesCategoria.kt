package com.example.clotstore.Preferences

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.clotstore.API.preferencesUsuari
import com.example.clotstore.R
import com.example.clotstore.databinding.FragmentPreferencesCategoriaBinding


class PreferencesCategoria : Fragment() {
    private lateinit var binding: FragmentPreferencesCategoriaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPreferencesCategoriaBinding.inflate(inflater, container, false)

        when (preferencesUsuari!!.categoria) {
            1 -> binding.rgCategoria.check(binding.rbtDona.id)
            2 -> binding.rgCategoria.check(binding.rbtHome.id)
            3 -> binding.rgCategoria.check(binding.rbtInfantil.id)
            5 -> binding.rgCategoria.check(binding.rbtUnisex.id)

        }


        binding.rgCategoria.setOnCheckedChangeListener { rgCategoria, i ->
            when (i) {
                R.id.rbtDona -> preferencesUsuari!!.categoria = 1
                R.id.rbtHome -> preferencesUsuari!!.categoria = 2
                R.id.rbtInfantil -> preferencesUsuari!!.categoria = 3
                R.id.rbtUnisex -> preferencesUsuari!!.categoria = 5
            }
        }
        return binding.root
    }
}