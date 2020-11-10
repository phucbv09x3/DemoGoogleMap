package com.mon.demogooglemap

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.compat.GeoDataClient
import com.google.android.libraries.places.compat.PlaceDetectionClient
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private var checkMapType = false
    private var mGeoDataClient: GeoDataClient? = null
    private var mPlaceDetectionClient: PlaceDetectionClient? = null
    private var mFusedLocationProvider: FusedLocationProviderClient? = null
    private var locationPermissionGranted = false
    val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val frg = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        frg?.getMapAsync(this)
        btn_changeMap.setOnClickListener {
            if (checkMapType) {
                checkMapType = false
                mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            } else {
                checkMapType = true
                mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
            }
        }
        setupPermissions()
        init()
    }
    private fun init() {
        mGeoDataClient = com.google.android.libraries.places.compat.Places.getGeoDataClient(this)
        mPlaceDetectionClient = com.google.android.libraries.places.compat.Places.getPlaceDetectionClient(this)
        mFusedLocationProvider = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onMapReady(p0: GoogleMap?) {
        mMap = p0!!
        val pos = LatLng(20.9788, 105.7973)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 16f))
        mMap.addMarker(MarkerOptions()
                .position(pos)
                .title("201 Đường Chiến Thắng-Tân Triều-Thanh Trì")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pushpin)))

        mMap.isMyLocationEnabled = false
        mMap.uiSettings.isMyLocationButtonEnabled = false
        if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
    }

    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    19
            )
        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        locationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                if (grantResults.isNotEmpty() &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true
                }
            }
        }
        //updateUI()
    }

    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.applicationContext,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
        }
    }

    fun updateUI() {
        try {
            if (locationPermissionGranted) {
                mMap?.isMyLocationEnabled = true
                mMap?.uiSettings?.isMyLocationButtonEnabled = true
            } else {
                mMap?.isMyLocationEnabled = false
                mMap?.uiSettings?.isMyLocationButtonEnabled = false
                // lastKnownLocation = null
                getLocationPermission()
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    private fun getDeviceLocation() {
//        try {
//            if (locationPermissionGranted) {
//                val locationResult = mFusedLocationProvider?.lastLocation
//                locationResult?.addOnCompleteListener(this) { task ->
//                    if (task.isSuccessful) {
//                        lastKnownLocation = task.result
//                        if (lastKnownLocation != null) {
//                            map?.moveCamera(CameraUpdateFactory.newLatLngZoom(
//                                    LatLng(lastKnownLocation!!.latitude,
//                                            lastKnownLocation!!.longitude), DEFAULT_ZOOM.toFloat()))
//                        }
//                    } else {
//                        Log.d(TAG, "Current location is null. Using defaults.")
//                        Log.e(TAG, "Exception: %s", task.exception)
//                        map?.moveCamera(CameraUpdateFactory
//                                .newLatLngZoom(defaultLocation, DEFAULT_ZOOM.toFloat()))
//                        map?.uiSettings?.isMyLocationButtonEnabled = false
//                    }
//                }
//            }
//        } catch (e: SecurityException) {
//            Log.e("Exception: %s", e.message, e)
//        }
//    }

    }

}