//Chaitanya Malshikare
//Codsoft internship
package com.example.notificationdemoapp

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {
    private val CHANNEL_ID = "Hello world"
    private val CHANNEL_NAME = "hello world channel"
    private val CHANNEL_DESCRIPTION = "hello world notificaion"
    private lateinit var et1o: EditText
    private lateinit var et2o: EditText
    private lateinit var b1o: Button
    private val link1: String = "https://en.wikipedia.org/wiki/World"
    private val link2: String = "https://en.wikipedia.org/wiki/%22Hello,_World!%22_program"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        et1o = findViewById(R.id.et1)
        et2o = findViewById(R.id.et2)
        b1o = findViewById(R.id.b1)

        b1o.setOnClickListener {
            val imgBitmap = BitmapFactory.decodeResource(resources, R.drawable.world2)

            val intent1 = linkOpener(link1)
            val intent2 = linkOpener(link2)

            val pendingintent1 = PendingIntent.getActivity(this, 5, intent1,
                PendingIntent.FLAG_UPDATE_CURRENT)
            val pendingintent2 = PendingIntent.getActivity(this, 6, intent2,
                PendingIntent.FLAG_UPDATE_CURRENT)

            notificationChannel1()

            val notification1 = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(et1o.text.toString())
                .setContentText(et2o.text.toString())
                .setSmallIcon(R.drawable.baseline_add_alert_24)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .addAction(0, "HelloWorld Program", pendingintent2)
                .setContentIntent(pendingintent1)
                .setLargeIcon(imgBitmap)

                .setStyle(
                    NotificationCompat.BigPictureStyle()
                        .bigPicture(imgBitmap)
                        .bigLargeIcon(null as Icon?)
                )
                .build()

            val nManagerC = NotificationManagerCompat.from(this)
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) !=
                PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this,"No Permission",Toast.LENGTH_LONG).show()
            }
            else {
                nManagerC.notify(1, notification1)
            }
        }
    }

    private fun notificationChannel1() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel1 = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
                .apply {
                description = CHANNEL_DESCRIPTION
            }
            val notificationmanager1 = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationmanager1.createNotificationChannel(channel1)
        }
    }

    private fun linkOpener(link: String): Intent {
        val intento = Intent()
        intento.action = Intent.ACTION_VIEW
        intento.data = Uri.parse(link)
        return intento
    }
}