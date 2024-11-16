package com.example.myapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.NotificationHelper
import com.example.myapplication.databinding.ActivityMainBinding
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.myapplication.Feed
import com.example.myapplication.Feteh
import com.example.myapplication.R
import com.example.myapplication.fetchCallBackData
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar


class MainActivity : AppCompatActivity(), fetchCallBackData {
    lateinit var notificationHelper:NotificationHelper
    lateinit var mainActivityBinding: ActivityMainBinding
    private val handle= Handler()
    var start = false
    var apiCallStatus = ""
    lateinit var fetch : Feteh

    private val requestNotificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                // Permission granted, you can now send notifications
                Toast.makeText(this, "Notification Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                // Permission denied, handle accordingly
                Toast.makeText(this, "Notification Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(mainActivityBinding.root)
            mainActivityBinding.textChangeLog.movementMethod = ScrollingMovementMethod()
        askNotificationPermision()
        mainActivityBinding.btnStartStop.setOnClickListener{
            if(start == false){
//                ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
                mainActivityBinding.textStartStop.text = getString(R.string.cancel_text)
                mainActivityBinding.btnStartStop.text = getString(R.string.cancel)
                startApiCall()
                start = true
            }else{
                //when start is true
                mainActivityBinding.textStartStop.text = getString(R.string.start_text)
                mainActivityBinding.btnStartStop.text = getString(R.string.start)
                mainActivityBinding.textChangeStatus.text = getString(R.string.no_change_detected)
                mainActivityBinding.textChangeLog.text = getString(R.string.log)
                mainActivityBinding.textChangeStatus.background = getDrawable(R.drawable.bg_green)
                handle.removeCallbacksAndMessages(null)
                start = false
            }
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED == false){
                mainActivityBinding.btnNotification.visibility = View.VISIBLE
            }else{
                mainActivityBinding.btnNotification.visibility = View.GONE
            }


        }

    }



    private fun startApiCall(){
        handle.removeCallbacksAndMessages(null)
        handle.postDelayed(object : Runnable {
            override fun run() {
                fetch = Feteh()
                fetch.fetch(this@MainActivity)
                // Repeat this runnable code again after 1000ms (1 second)
                handle.postDelayed(this, 1000)
            }
        }, 0)

    }
    override fun onSucess(feedList : List<Feed>) {
        for (i in 0 until feedList.size - 1) {
            if (feedList[i].field1 != feedList[i + 1].field1) {
                notificationHelper = NotificationHelper(this)
                notificationHelper.sendNotification("Alert","The senser detect something.")
                var text = mainActivityBinding.textChangeLog.text.toString()
                text = text + "\nChange deteceted at " + getCurrentDateTimeWithCalendar()
                mainActivityBinding.textChangeLog.text = text

                mainActivityBinding.textChangeStatus.text = getString(R.string.no_change_detected)
                mainActivityBinding.textChangeStatus.background = getDrawable(R.drawable.bg_red)
            }else{
                mainActivityBinding.textChangeStatus.text = getString(R.string.change_detected)
                mainActivityBinding.textChangeStatus.background = getDrawable(R.drawable.bg_green)
            }

        }
    }
    fun getCurrentDateTimeWithCalendar(): String {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1 // Months are zero-based
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)
        return "$year-$month-$day $hour:$minute:$second"
    }
    private fun askNotificationPermision(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            // If we don't have permission yet, request it
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                requestNotificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        }

    }


}
