package com.navio.first_arcgis_map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.arcgismaps.ApiKey
import com.arcgismaps.ArcGISEnvironment
import com.navio.first_arcgis_map.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Hide top bar or modify it --> Not visible
        supportActionBar?.let { actionBar ->
//          actionBar.hide()
            actionBar.title = "ArcGIS Map"
            actionBar.subtitle = "Jose Luis Navío Mendoza"
        }

        //Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Set ArcGIS API key
        setArcGISApiKey()

        attachFragments()
    }

    private fun attachFragments(){

        supportFragmentManager.commit {

            //Let commit operations decide better operation's order
            setReorderingAllowed(true)

            replace(
                binding.fragmentContainerMap.id,
                FragmentMap.newInstance()
            )

            replace(
                binding.fragmentContainerButtons.id,
                FragmentButtonsMap.newInstance()
            )
        }
    }

    //Set API key
    private fun setArcGISApiKey() {
        //Use your own api key here...
        ArcGISEnvironment.apiKey = ApiKey.create(Utils.ARCGIS_API_KEY)
    }
}