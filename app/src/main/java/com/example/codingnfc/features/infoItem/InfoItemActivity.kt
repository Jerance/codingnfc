package com.example.codingnfc.features.infoItem

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.codingnfc.databinding.ActivityInfoItemBinding
import java.lang.Exception

class InfoItemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInfoItemBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoItemBinding.inflate(layoutInflater)
        setContentView(binding.root) // binding.root

        val image = intent.getStringExtra("image")
        val name = intent.getStringExtra("name")
        val confidence = intent.getFloatExtra("confidence", -1f)
        val x = intent.getIntExtra("x", -1)
        val y = intent.getIntExtra("y", -1)
        val h = intent.getIntExtra("h", -1)
        val w = intent.getIntExtra("w", -1)

        val originalBitmap = BitmapFactory.decodeFile(image)
        val mutableBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true)

        val percentageConfidence = confidence * 100
        binding.objectImageView.setImageBitmap(mutableBitmap)

        try {
            val canvas = Canvas(mutableBitmap)

            val paint = Paint()
            paint.color = Color.BLUE
            paint.setStrokeWidth(30F)
            paint.setStyle(Paint.Style.STROKE)
            val left = x
            var top = y
            val right = left + w
            val bottom = top + h
            canvas.drawRect(left.toFloat(), y.toFloat(), right.toFloat(), bottom.toFloat(), paint)
        } catch (e: Exception) {
            println(e)
        }
            // Set paint properties (color, style, etc.)

        // Specify the rectangle coordinates and dimensions


        binding.objectNameTextView.text = "Object: $name"
        binding.confidenceTextView.text = "Confidence: $percentageConfidence%"

    }
}