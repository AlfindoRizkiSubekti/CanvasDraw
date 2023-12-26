package com.example.canvasdraw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.canvasdraw.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var square = Square()
    private val dbHelper = DatabaseHelper(this)
    private val random = Random()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val canvasView = binding.canvasView
        binding.edPositionX.setText(square.positionX.toInt().toString())
        binding.edPositionY.setText(square.positionY.toInt().toString())
        binding.edWideSquare.setText(square.squareSize.toInt().toString())

        addRandomPoints()
            binding.btnSubmit.setOnClickListener {
                val positionXText = binding.edPositionX.text.toString().trim()
                val positionYText = binding.edPositionY.text.toString().trim()
                val wideSquareText = binding.edWideSquare.text.toString().trim()

                if (positionXText.isEmpty() || positionYText.isEmpty() || wideSquareText.isEmpty()) {
                    Toast.makeText(this, "Please fill in all input", Toast.LENGTH_LONG).show()
                } else {
                    dbHelper.deleteTable()
                    square.positionX = positionXText.toFloat()
                    square.positionY = positionYText.toFloat()
                    square.squareSize = wideSquareText.toFloat()
                    canvasView.moveSquares(positionXText.toFloat(), positionYText.toFloat(), wideSquareText.toFloat())
                    addRandomPoints()
                }
            }
    }

    private fun addRandomPoints() {
        dbHelper.deleteTable()
        repeat(square.squareSize.toInt()) {
            val x = square.positionX + random.nextFloat() * square.squareSize
            val y = square.positionY + random.nextFloat() * square.squareSize
            dbHelper.addPoint(x, y)
        }
    }


}