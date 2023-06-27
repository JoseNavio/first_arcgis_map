package com.navio.first_arcgis_map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.arcgismaps.ApiKey
import com.arcgismaps.ArcGISEnvironment
import com.arcgismaps.Color
import com.arcgismaps.geometry.Point
import com.arcgismaps.geometry.PolygonBuilder
import com.arcgismaps.geometry.PolylineBuilder
import com.arcgismaps.geometry.SpatialReference
import com.arcgismaps.mapping.ArcGISMap
import com.arcgismaps.mapping.BasemapStyle
import com.arcgismaps.mapping.Viewpoint
import com.arcgismaps.mapping.symbology.*
import com.arcgismaps.mapping.view.Graphic
import com.arcgismaps.mapping.view.GraphicsOverlay
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
        addGraphics()
    }

    //Creates an ArcGIS map
    private fun setupMap() {
        val createdMap = ArcGISMap(BasemapStyle.ArcGISTopographic)

        //Display map on the layout view
        mapView.map = createdMap
        //Set the position and zoom of the map
        //mapView.setViewpoint(Viewpoint(36.7278, -4.4697, 22000.0))
        mapView.setViewpoint(Viewpoint(34.0005, -118.8065, 72000.0))
    }

    private fun setApiKey() {
        //Use your own api key here...
        ArcGISEnvironment.apiKey = ApiKey.create(Utils.ARCGIS_API_KEY)
    }

    private fun addGraphics() {
        val createdGraphicsOverlay = GraphicsOverlay()
        mapView.graphicsOverlays.add(createdGraphicsOverlay)

        //You have to pass the ellipsoid reference (wgs84)
        //-> Point
        val point = Point(-4.4697, 36.7278, SpatialReference.wgs84())
        //Marker options
        val simpleMarkerSymbol = SimpleMarkerSymbol(SimpleMarkerSymbolStyle.Circle, Color.red, 10f)
        val blueOutlineSymbol =
            SimpleLineSymbol(SimpleLineSymbolStyle.Solid, Color.fromRgba(0, 0, 255), 2f)
        //Add outline to marker
        simpleMarkerSymbol.outline = blueOutlineSymbol
        //Display marker on map
        val pointGraphic = Graphic(point, simpleMarkerSymbol)
        createdGraphicsOverlay.graphics.add(pointGraphic)

        //-> Polyline
        val polylineSymbol =
            SimpleLineSymbol(SimpleLineSymbolStyle.Solid, Color.fromRgba(0, 0, 255), 3f)
        val polylineBuilder = PolylineBuilder(SpatialReference.wgs84()) {
            addPoint(-4.46, 36.73)
            addPoint(-4.47, 36.75)
            addPoint(-4.48, 36.74)
        }
        val polyline = polylineBuilder.toGeometry()
        //Display polyline on map
        val polylineGraphic = Graphic(polyline, polylineSymbol)
        createdGraphicsOverlay.graphics.add(polylineGraphic)

        //-> Polygon
        val polygonBuilder = PolygonBuilder(SpatialReference.wgs84()) {
            addPoint(-118.8189, 34.0137)
            addPoint(-118.8067, 34.0215)
            addPoint(-118.7914, 34.0163)
            addPoint(-118.7959, 34.0085)
            addPoint(-118.8085, 34.0035)
        }
        val polygon = polygonBuilder.toGeometry()
        val redOutlineSymbol =
            SimpleLineSymbol(SimpleLineSymbolStyle.Solid, Color.fromRgba(255, 0, 25), 2f)
        val polygonFillSymbol =
            SimpleFillSymbol(
                SimpleFillSymbolStyle.Solid,
                Color.fromRgba(255, 0, 0, 128),
                redOutlineSymbol
            )
        //Display polygon on map
        val polygonGraphic = Graphic(polygon, polygonFillSymbol)
        createdGraphicsOverlay.graphics.add(polygonGraphic)
    }

//    private fun showError(message: String) {
//        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
//        Log.e(localClassName, message)
//    }
}