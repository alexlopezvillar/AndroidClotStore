package com.example.clotstore.Preferences

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.clotstore.API.preferencesUsuari
import com.example.clotstore.databinding.FragmentPreferencesColorBinding
import com.example.clotstore.databinding.FragmentPreferencesTemporadaBinding

class PreferencesTemporada : Fragment() {
    private lateinit var binding: FragmentPreferencesTemporadaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPreferencesTemporadaBinding.inflate(inflater, container, false)

        when (preferencesUsuari!!.temporada) {
            1 -> binding.radioGroup.check(binding.rbtEstiu.id)
            2 -> binding.radioGroup.check(binding.rbtPrimavera.id)
            3 -> binding.radioGroup.check(binding.rbtTardor.id)
            4 -> binding.radioGroup.check(binding.rbtHivern.id)
        }
        comprovaBotons()
        binding.radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            comprovaBotons()
        }
        return binding.root
    }
    public fun comprovaBotons(){
        if (binding.rbtEstiu.isChecked) {
            binding.textViewEstacio.text = "Estiu"
            preferencesUsuari!!.temporada = 1

        } else if (binding.rbtPrimavera.isChecked) {
            binding.textViewEstacio.text = "Primavera"
            preferencesUsuari!!.temporada = 2

        } else if (binding.rbtTardor.isChecked) {
            binding.textViewEstacio.text = "Tardor"
            preferencesUsuari!!.temporada = 3

        } else if (binding.rbtHivern.isChecked) {
            binding.textViewEstacio.text = "Hivern"
            preferencesUsuari!!.temporada = 4
        }
    }
}