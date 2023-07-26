package com.navio.first_arcgis_map.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.navio.first_arcgis_map.OnButtonsClicked
import com.navio.first_arcgis_map.databinding.FragmentExpandableListBinding

class FragmentExpandableList : Fragment() {

    private lateinit var binding: FragmentExpandableListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Binding
        binding = FragmentExpandableListBinding.inflate(layoutInflater)

        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() = FragmentExpandableList()
    }
}