package com.navio.first_arcgis_map.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.navio.first_arcgis_map.OnButtonsClicked
import com.navio.first_arcgis_map.databinding.FragmentButtonsBlockBinding

class FragmentButtonsBlock : Fragment() {

    private lateinit var binding: FragmentButtonsBlockBinding
    private lateinit var listenerButtons: OnButtonsClicked

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        //Binding
        binding = FragmentButtonsBlockBinding.inflate(layoutInflater)
        //Init buttons
        initButtons()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return binding.root
    }

    private fun initButtons() {

        binding.buttonMap.setOnClickListener { listenerButtons.onMapOpen() }
        binding.buttonImage.setOnClickListener { listenerButtons.onTakeSnapshot() }
    }

    //Sets the button communicated with main activity
    fun setOnButtonsClickedListener(listener: OnButtonsClicked) {
        listenerButtons = listener
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentButtonsBlock()
    }
}