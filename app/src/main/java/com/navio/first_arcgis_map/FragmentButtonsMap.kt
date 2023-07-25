package com.navio.first_arcgis_map

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arcgismaps.ApiKey
import com.arcgismaps.ArcGISEnvironment
import com.navio.first_arcgis_map.databinding.FragmentButtonsMapBinding

class FragmentButtonsMap : Fragment() {

    private lateinit var binding: FragmentButtonsMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        //Binding
        binding = FragmentButtonsMapBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentButtonsMap()
    }
}