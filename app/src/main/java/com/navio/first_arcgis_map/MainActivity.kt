package com.navio.first_arcgis_map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.arcgismaps.ApiKey
import com.arcgismaps.ArcGISEnvironment
import com.navio.first_arcgis_map.databinding.ActivityMainBinding
import com.navio.first_arcgis_map.fragments.FragmentButtonsBlock
import com.navio.first_arcgis_map.fragments.FragmentExpandableList
import com.navio.first_arcgis_map.fragments.FragmentMap

private lateinit var binding: ActivityMainBinding
private lateinit var currentTopFragment: Fragment

class MainActivity : AppCompatActivity(), OnButtonsClicked {
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

        attachInitialFragments()
    }

    private fun attachInitialFragments() {

            //Add a listener to the buttons fragment
            val frgButtonsBlock = FragmentButtonsBlock.newInstance()
            frgButtonsBlock.setOnButtonsClickedListener(this@MainActivity)

            //Add fragments
            replaceTopFragment(FragmentExpandableList.newInstance())
            replaceBottomFragment(frgButtonsBlock)
    }

    private fun replaceTopFragment(fragment: Fragment) {
        //Stores the value of the current fragment
        currentTopFragment = fragment

        //Replaces the current fragment
        supportFragmentManager.commit {

            //Let commit operations decide better operation's order
            setReorderingAllowed(true)

            replace(
                binding.fragmentContainerTop.id,
                fragment
            )
        }
    }

    private fun replaceBottomFragment(fragment: Fragment) {

        supportFragmentManager.commit {

            //Let commit operations decide better operation's order
            setReorderingAllowed(true)

            replace(
                binding.fragmentContainerButtons.id,
                fragment
            )
        }
    }

    //Set API key
    private fun setArcGISApiKey() {
        //Use your own api key here...
        ArcGISEnvironment.apiKey = ApiKey.create(Utils.ARCGIS_API_KEY)
    }

    override fun onOpenProject() {}

    override fun onMapOpen() {
        replaceTopFragment(FragmentMap.newInstance())
    }

    override fun onTakeSnapshot() {

        if(currentTopFragment is FragmentMap){
            val fragmentMap = currentTopFragment as FragmentMap
            fragmentMap.startSnapshotCoroutine()
        }
    }
}

public interface OnButtonsClicked {
    public fun onOpenProject()
    public fun onMapOpen()
    public fun onTakeSnapshot()
}