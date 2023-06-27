package com.navio.first_arcgis_map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.arcgismaps.ApiKey
import com.arcgismaps.ArcGISEnvironment
import com.arcgismaps.mapping.ArcGISMap
import com.arcgismaps.mapping.BasemapStyle
import com.arcgismaps.mapping.Viewpoint
import com.arcgismaps.mapping.view.MapView
import com.navio.first_arcgis_map.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding

private lateinit var mapView: MapView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Initialize map
        mapView = binding.mapView
        //Subscribe map to lifecycle
        lifecycle.addObserver(mapView)

        setApiKey()
        setupMap()
    }
    //Creates an ArcGIS map
    private fun setupMap(){
        val createdMap = ArcGISMap(BasemapStyle.ArcGISTopographic)

        //Display map on the layout view
        mapView.map = createdMap
        //Set the position and zoom of the map
        mapView.setViewpoint(Viewpoint(36.7278, -4.4697, 7000.0))
    }

    private fun setApiKey(){
        ArcGISEnvironment.apiKey = ApiKey.create(Utils.ARCGIS_API_KEY)
    }

//    private fun showError(message: String) {
//        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
//        Log.e(localClassName, message)
//    }
}