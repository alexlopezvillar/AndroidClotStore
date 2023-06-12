package com.example.clotstore.Preferences

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.clotstore.API.preferencesUsuari
import com.example.clotstore.R
import com.example.clotstore.databinding.FragmentPreferencesEstilBinding


class PreferencesEstil : Fragment() {
    private lateinit var binding: FragmentPreferencesEstilBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPreferencesEstilBinding.inflate(inflater, container, false)


            when (preferencesUsuari!!.estil) {
                1 -> binding.rgEstil.check(binding.rbtFormal.id)
                2 -> binding.rgEstil.check(binding.rbtInformal.id)
                3 -> binding.rgEstil.check(binding.rbtEsportiu.id)
                5 -> binding.rgEstil.check(binding.rbtRetro.id)

            }

        binding.rgEstil.setOnCheckedChangeListener { rgEstil, i ->
            when (i){
                R.id.rbtFormal -> preferencesUsuari!!.estil=1
                R.id.rbtInformal -> preferencesUsuari!!.estil=2
                R.id.rbtEsportiu -> preferencesUsuari!!.estil=3
                R.id.rbtRetro -> preferencesUsuari!!.estil=5
            }
        }
        return binding.root
    }
}