package com.navio.first_arcgis_map.fragments

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.arcgismaps.Color
import com.arcgismaps.geometry.Point
import com.arcgismaps.geometry.PolygonBuilder
import com.arcgismaps.geometry.PolylineBuilder
import com.arcgismaps.geometry.SpatialReference
import com.arcgismaps.mapping.ArcGISMap
import com.arcgismaps.mapping.BasemapStyle
import com.arcgismaps.mapping.Viewpoint
import com.arcgismaps.mapping.symbology.SimpleFillSymbol
import com.arcgismaps.mapping.symbology.SimpleFillSymbolStyle
import com.arcgismaps.mapping.symbology.SimpleLineSymbol
import com.arcgismaps.mapping.symbology.SimpleLineSymbolStyle
import com.arcgismaps.mapping.symbology.SimpleMarkerSymbol
import com.arcgismaps.mapping.symbology.SimpleMarkerSymbolStyle
import com.arcgismaps.mapping.view.Graphic
import com.arcgismaps.mapping.view.GraphicsOverlay
import com.arcgismaps.mapping.view.MapView
import com.navio.first_arcgis_map.R
import com.navio.first_arcgis_map.databinding.FragmentMapBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class FragmentMap : Fragment() {

    private lateinit var binding: FragmentMapBinding

    private lateinit var mapView: MapView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Binding
        binding = FragmentMapBinding.inflate(layoutInflater)

        //Initialize map
        mapView = binding.mapView

        //Subscribe map to lifecycle
        lifecycle.addObserver(mapView)

        return binding.root
    }

    //After the view is created...
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupMap()
        addGraphics()
    }

    //Creates an ArcGIS map
    private fun setupMap() {
        val createdMap = ArcGISMap(BasemapStyle.ArcGISTopographic)

        //Display map on the layout view
        mapView.map = createdMap
        //Set the position and zoom of the map
        mapView.setViewpoint(Viewpoint(36.7278, -4.4697, 22000.0))
//        mapView.setViewpoint(Viewpoint(34.0005, -118.8065, 72000.0))
    }

    //Add some graphic examples to map
    private fun addGraphics() {
        val createdGraphicsOverlay = GraphicsOverlay()
        mapView.graphicsOverlays.add(createdGraphicsOverlay)

        //You have to pass the ellipsoid reference (wgs84)
        //--> Point
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

        //--> Polyline
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

        //--> Polygon
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

    //Starts coroutine to take picture from map
    public fun startSnapshotCoroutine() {
        lifecycleScope.launch(Dispatchers.IO) {
            snapshotFromMap()
        }
    }

    private suspend fun snapshotFromMap() {

        val resultBitmapImage = mapView.exportImage()

        if (resultBitmapImage.isSuccess) {

            resultBitmapImage.getOrNull()?.let { imageDrawable ->
                //Stores images in gallery
                val imageBitmap = imageDrawable.bitmap
                val isBitmapSaved = saveBitmapToInternalStorage(requireContext(), imageBitmap)

                //todo Showing image here
                showSnapshotOnFragment(imageDrawable)

                //If couldn't be saved notifies
                if (!isBitmapSaved) {
                    //todo Log.e
                }
            }
        }
    }

    private suspend fun showSnapshotOnFragment(imageDrawable: BitmapDrawable) =

        withContext(Dispatchers.Main) {

            parentFragmentManager.commit {

                //Let commit operations decide better operation's order
                setReorderingAllowed(true)

                replace(
                    R.id.fragment_container_top,
                    FragmentMapImage.newInstance(imageDrawable)
                )
                //Save the old fragment in pile
                addToBackStack(null)
            }
        }

    private suspend fun saveBitmapToInternalStorage(context: Context, bitmap: Bitmap): Boolean =
        withContext(Dispatchers.IO) {

            //todo Replace for each project name
            val imageFileName = "my_image.jpg"

            //Get the directory for your app's internal storage
            val directory = File(context.filesDir, "thumbnails").apply {
                mkdirs() //Create the directory if it doesn't exist
            }

            //Create the file path for the image
            val imageFile = File(directory, imageFileName)

            return@withContext runCatching {
                //Write the bitmap to the file
                FileOutputStream(imageFile).use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 25, outputStream)
                }
                true //Success
            }.getOrElse {
                it.printStackTrace()
                false //Failure
            }
        }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentMap()
    }
}