package com.example.noisemachine

import android.content.Context
import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Half.toFloat
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() , SensorEventListener {

    private var mSensorManager: SensorManager? = null
    private var mAccelerometer: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //get resources
        val nameArray = resources.getStringArray(R.array.hw_names)
        val infoArray = resources.getStringArray(R.array.hw_values)
        val imageArray = arrayOf<Int>(R.drawable.accel_icon, R.drawable.mic_icon)

        // get reference of the service
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        // focus in accelerometer
        mAccelerometer = mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        // setup the window
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        //define custom adapter
        val adapter = CustomListAdapter(this, nameArray, infoArray, imageArray)

        //apply custom adapter
        data_list_view.adapter = adapter

        //on-click functionality
        data_list_view.setOnItemClickListener { adapterView, view, position, id ->
            val itemAtPos = adapterView.getItemAtPosition(position)
            val itemIdAtPos = adapterView.getItemIdAtPosition(position)
            Toast.makeText(this, "Click on item at $itemAtPos : item id $itemIdAtPos", Toast.LENGTH_LONG).show()
        }

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {

            val accelval1: String = event.values[0].toString()
            val accelval2: String = event.values[1].toString()
            val accelval3: String = event.values[2].toString()

            val nameArray = resources.getStringArray(R.array.hw_names)
            val infoArray = arrayOf<String>( accelval1, accelval2, accelval3 )
            val imageArray = arrayOf<Int>(R.drawable.accel_icon, R.drawable.accel_icon, R.drawable.accel_icon)

            val adapter = CustomListAdapter(this, nameArray, infoArray, imageArray)

            //apply custom adapter
            data_list_view.adapter = adapter
        }
    }

    override fun onResume() {
        super.onResume()
        mSensorManager!!.registerListener(this,mAccelerometer,
            SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onPause() {
        super.onPause()
        mSensorManager!!.unregisterListener(this)
    }

}
