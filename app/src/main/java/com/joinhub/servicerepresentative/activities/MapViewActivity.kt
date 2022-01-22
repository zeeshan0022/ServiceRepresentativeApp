package com.joinhub.servicerepresentative.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.huawei.hms.maps.HuaweiMap
import com.huawei.hms.maps.MapView
import com.huawei.hms.maps.MapsInitializer
import com.huawei.hms.maps.OnMapReadyCallback
import com.joinhub.servicerepresentative.R


class MapViewActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var  mapView:MapView
    lateinit var  himap:HuaweiMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapsInitializer.setApiKey("1D22348949657FDB2BC72FE81D92A826C264EB87C91E8DE8CC587E678CA2F26B")
        setContentView(R.layout.activity_map_view)
        mapView= findViewById(R.id.mapview_mapviewdemo)
        var bundle: Bundle? =null
        if (savedInstanceState != null) {
            bundle = savedInstanceState.getBundle("MapViewBundleKey")!!
        }
        mapView.onCreate(bundle);
        mapView.getMapAsync(this);
    }


    override fun onMapReady(p0: HuaweiMap?) {
        if (p0 != null) {
            himap= p0
        }
    }

    private val MAPVIEW_BUNDLE_KEY = "MapViewBundleKey"
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        var mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY)
        if (mapViewBundle == null) {
            mapViewBundle = Bundle()
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle)
        }
        mapView.onSaveInstanceState(mapViewBundle)
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}