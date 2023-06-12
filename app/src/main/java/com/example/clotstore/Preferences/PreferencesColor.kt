package com.example.clotstore.Preferences

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.clotstore.API.preferencesUsuari
import com.example.clotstore.databinding.FragmentIniciBinding
import com.example.clotstore.databinding.FragmentPreferencesColorBinding


class PreferencesColor : Fragment() {
    private lateinit var binding: FragmentPreferencesColorBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPreferencesColorBinding.inflate(inflater, container, false)

        when (preferencesUsuari!!.idColor) {
            2 -> binding.radioGroup.check(binding.rbtVermell.id)
            3 -> binding.radioGroup.check(binding.rbtRosa.id)
            4 -> binding.radioGroup.check(binding.rbtLila.id)
            5 -> binding.radioGroup.check(binding.rbtCian.id)
            6 -> binding.radioGroup.check(binding.rbtBlau.id)
            7 -> binding.radioGroup.check(binding.rbtVerdFosc.id)
            8 -> binding.radioGroup.check(binding.rbtVerd.id)
            9 -> binding.radioGroup.check(binding.rbtGroc.id)
            10 -> binding.radioGroup.check(binding.rbtTaronja.id)
            11 -> binding.radioGroup.check(binding.rbtMarro.id)
            12 -> binding.radioGroup.check(binding.rbtBlanc.id)
            13 -> binding.radioGroup.check(binding.rbtGris.id)
            14 -> binding.radioGroup.check(binding.rbtNegre.id)

        }
        comprovaBotons()
        binding.radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            comprovaBotons()
        }
        return binding.root
    }

    fun comprovaBotons() {
        if (binding.rbtBlanc.isChecked) {
            binding.colorSelect.background =
                binding.rbtBlanc.background
            preferencesUsuari!!.idColor = 12

        } else if (binding.rbtNegre.isChecked) {
            binding.colorSelect.background =
                binding.rbtNegre.background
            preferencesUsuari!!.idColor = 14
        } else if (binding.rbtGris.isChecked) {
            binding.colorSelect.background =
                binding.rbtGris.background
            preferencesUsuari!!.idColor = 13
        } else if (binding.rbtMarro.isChecked) {
            binding.colorSelect.background =
                binding.rbtMarro.background
            preferencesUsuari!!.idColor = 11
        } else if (binding.rbtTaronja.isChecked) {
            binding.colorSelect.background =
                binding.rbtTaronja.background
            preferencesUsuari!!.idColor = 10
        } else if (binding.rbtGroc.isChecked) {
            binding.colorSelect.background =
                binding.rbtGroc.background
            preferencesUsuari!!.idColor = 9
        } else if (binding.rbtVerd.isChecked) {
            binding.colorSelect.background =
                binding.rbtVerd.background
            preferencesUsuari!!.idColor = 8
        } else if (binding.rbtVerdFosc.isChecked) {
            binding.colorSelect.background =
                binding.rbtVerdFosc.background
            preferencesUsuari!!.idColor = 7
        } else if (binding.rbtBlau.isChecked) {
            binding.colorSelect.background =
                binding.rbtBlau.background
            preferencesUsuari!!.idColor = 6
        } else if (binding.rbtCian.isChecked) {
            binding.colorSelect.background =
                binding.rbtCian.background
            preferencesUsuari!!.idColor = 5
        } else if (binding.rbtLila.isChecked) {
            binding.colorSelect.background =
                binding.rbtLila.background
            preferencesUsuari!!.idColor = 4
        } else if (binding.rbtRosa.isChecked) {
            binding.colorSelect.background =
                binding.rbtRosa.background
            preferencesUsuari!!.idColor = 3
        } else if (binding.rbtVermell.isChecked) {
            binding.colorSelect.background =
                binding.rbtVermell.background
            preferencesUsuari!!.idColor = 2
        }
    }
}