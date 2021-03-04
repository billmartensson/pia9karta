package se.magictechnology.pia9karta

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    lateinit var mapview : MapView

    lateinit var gmap : GoogleMap
    lateinit var locationManager: LocationManager

    var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val mapFragment = supportFragmentManager.findFragmentById(R.id.themap) as? SupportMapFragment
        //mapFragment!!.getMapAsync(this)


        mapview = findViewById<MapView>(R.id.themap)
        mapview.onCreate(savedInstanceState)
        mapview.getMapAsync(this)

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        getLocation()


        findViewById<Button>(R.id.minusButton).setOnClickListener {
            counter = counter - 1

            findViewById<TextView>(R.id.resultTextview).text = counter.toString()
        }

        findViewById<Button>(R.id.plusButton).setOnClickListener {
            counter = counter + 1

            findViewById<TextView>(R.id.resultTextview).text = counter.toString()

        }

        findViewById<Button>(R.id.addMarkerButton).setOnClickListener {

            val sydney = LatLng(-33.852, 151.211)

            var mypos = MarkerOptions()
            mypos.title("Sydney")
            mypos.position(sydney)

            gmap.addMarker(mypos)
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(checkPermission() == true)
        {
            getLocation()
        } else {
            // Användare gav inte access. Visa meddelande
        }
    }

    fun getLocation()
    {
        if(checkPermission() == false)
        {
            ActivityCompat.requestPermissions(this,arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION), 1)
            return
        }

        // HÄMTA POSITION

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 100F, object : LocationListener {
            override fun onLocationChanged(location: Location) {

                Log.d("pia9debug", "NY POSITION " + location.latitude.toString())

                val myLatLng = LatLng(location.latitude, location.longitude)

                var mypos = MarkerOptions()
                mypos.title("My position")
                mypos.position(myLatLng)

                gmap.addMarker(mypos)

            }

        })

    }


    override fun onMapReady(p0: GoogleMap?) {

        Log.d("pia9debug", "Karta laddad")

        gmap = p0!!

        /*
        mMap.apply {
            val sydney = LatLng(-33.852, 151.211)

            moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10F))

            addMarker(
                    MarkerOptions()
                            .position(sydney)
                            .title("Marker in Sydney")
            )
        }
         */

        val sydney = LatLng(-33.852, 151.211)

        val zoomLevel : Float = 10F

        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoomLevel))


        gmap.setOnMarkerClickListener(object : GoogleMap.OnMarkerClickListener {
            override fun onMarkerClick(marker: Marker): Boolean {

                Log.i("pia9debug", marker.title)

                return false
            }
        })

        mapview.onResume()
    }



    fun checkPermission() : Boolean
    {
        val fineLoc = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val coarseLoc = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)

        if(fineLoc == PackageManager.PERMISSION_GRANTED)
        {
            return true
        }
        if(coarseLoc == PackageManager.PERMISSION_GRANTED)
        {
            return true
        }

        return false
    }



}