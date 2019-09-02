package com.example.noisemachine

import android.content.Context
import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import kotlin.random.Random


class MainActivity : AppCompatActivity() , SensorEventListener {

    private var mSensorManager: SensorManager? = null
    private var mAccelerometer: Sensor? = null
    private var mCompass: Sensor? = null

    //expected range of values, used for math
    private var accelmax = 10
    private var accelmin = 0
    private var magmax = 50
    private var magmin = -50

    //expected output range, used for math
    private var volmax = 200
    private var volmin = 50
    private var freqmax = 15000
    private var freqmin = 30

    //get resources
    private var nameArray: Array<String>? = null //resources.getStringArray(R.array.hw_names)
    private var infoArray: Array<String>? = null //resources.getStringArray(R.array.hw_values)
    private var imageArray: Array<Int>? = null //arrayOf<Int>(R.drawable.accel_icon, R.drawable.accel_icon, R.drawable.accel_icon, R.drawable.compass_icon)

    //def buzzer
    //defining initial params, may have to move this elsewhere
    private var tonevol: Int = 50
    private var tonefreq: Double = 110.00

    //init buzzer
    private val tonePlayer = ContinuousBuzzer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initiate a Switch
        val simpleSwitch = findViewById<Switch>(R.id.on_switch)
        //set the current state of a Switch
        simpleSwitch.isChecked = false

        // get reference of the service
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        //SENSORS INIT
        mAccelerometer = mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        mCompass = mSensorManager!!.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

        // setup the window
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        //define custom adapter and init data
        nameArray = resources.getStringArray(R.array.hw_names)
        infoArray = resources.getStringArray(R.array.hw_values)
        imageArray = arrayOf<Int>(R.drawable.accel_icon, R.drawable.accel_icon, R.drawable.accel_icon, R.drawable.compass_icon, R.drawable.compass_icon, R.drawable.compass_icon)

        val adapter = CustomListAdapter(this, nameArray as Array<String>, infoArray as Array<String>, imageArray!!)

        //apply custom adapter
        data_list_view.adapter = adapter

        //SWITCH
        // Set an checked change listener for switch button
        simpleSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // The switch is enabled/checked
                val switchText = findViewById<Switch>(R.id.on_switch)
                switchText.setText(R.string.switchon).toString()

                //THIS IS WHERE WE MAKE NOISE
                tonePlayer.setVolume(tonevol)
                tonePlayer.setToneFreqInHz(tonefreq)
                tonePlayer.play()

            } else {
                val switchText = findViewById<Switch>(R.id.on_switch)
                switchText.setText(R.string.switchoff).toString()

                //stop the player
                tonePlayer.stop()
            }
        }

        //on-click functionality, NONFUNCTIONAL: updating too quick
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
            if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                val accelval1: String? = infoArray?.get(0)
                val accelval2: String? = infoArray?.get(1)
                val accelval3: String? = infoArray?.get(2)
                val magval1: String = event.values[0].toString()
                val magval2: String = event.values[1].toString()
                val magval3: String = event.values[2].toString()

                // update values
                infoArray = arrayOf<String>(accelval1!!, accelval2!!, accelval3!!, magval1, magval2, magval3)

                //update adapter
                val adapter = CustomListAdapter(this, nameArray, infoArray, imageArray)

                //apply custom adapter
                data_list_view.adapter = adapter

                // UPDATE BUZZER
                // sensor output range magmin:magmax
                // desired range freqmin:freqmax
                // output = ((inputval - inputmin) / (inputmax - inputmin)) * (outputmax - outputmin) + outputmin

                var inputval: Double = arrayOf<Double>(magval1.toDouble(), magval2.toDouble(), magval3.toDouble()).average()

                tonefreq = ((inputval - magmin) / (magmax - magmin)) * (freqmax - freqmin) + freqmin
                tonePlayer.setToneFreqInHz(tonefreq)

            }
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                val accelval1: String = event.values[0].toString()
                val accelval2: String = event.values[1].toString()
                val accelval3: String = event.values[2].toString()
                val magval1: String? = infoArray?.get(3)
                val magval2: String? = infoArray?.get(4)
                val magval3: String? = infoArray?.get(5)

                // update values
                infoArray = arrayOf<String>(accelval1, accelval2, accelval3, magval1!!, magval2!!, magval3!!)

                //update adapter
                val adapter = CustomListAdapter(this, nameArray, infoArray, imageArray)

                //apply custom adapter
                data_list_view.adapter = adapter

                //update buzzer
                var inputval = arrayOf<Double>(accelval1.toDouble(), accelval2.toDouble(), accelval3.toDouble()).sum().toInt()

                tonevol = ((inputval - accelmin) / (accelmax - accelmin)) * (volmax - volmin) + volmin
                tonePlayer.setVolume(tonevol)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mSensorManager!!.registerListener(this,mAccelerometer,
            SensorManager.SENSOR_DELAY_GAME)
        mSensorManager!!.registerListener(this,mCompass,
            SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onPause() {
        super.onPause()
        mSensorManager!!.unregisterListener(this)
    }

}
