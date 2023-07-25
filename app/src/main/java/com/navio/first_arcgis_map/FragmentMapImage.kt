package com.navio.first_arcgis_map

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.navio.first_arcgis_map.databinding.FragmentMapImageBinding

class FragmentMapImage(private val image: BitmapDrawable) : Fragment() {

    private lateinit var binding: FragmentMapImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Binding
        binding = FragmentMapImageBinding.inflate(layoutInflater)

        showImage()

        return binding.root
    }

    private fun showImage() {
        binding.imageViewMap.setImageDrawable(image)
    }

    companion object {

        @JvmStatic
        fun newInstance(image: BitmapDrawable) = FragmentMapImage(image)
    }
}