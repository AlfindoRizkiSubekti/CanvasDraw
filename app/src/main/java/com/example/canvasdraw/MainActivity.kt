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
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val canvasView = binding.canvasView

        // Ambil posisi dan ukuran persegi dari input (Anda dapat menggunakan EditText atau yang lainnya)
        binding.edPositionX.setText(square.positionX.toInt().toString())
        binding.edPositionY.setText(square.positionY.toInt().toString())
        binding.edWideSquare.setText(square.squareSize.toInt().toString())

        // Isi database dengan titik acak sesuai luas canvas
        addRandomPoints()
            binding.btnSubmit.setOnClickListener {
                val positionXText = binding.edPositionX.text.toString().trim()
                val positionYText = binding.edPositionY.text.toString().trim()
                val wideSquareText = binding.edWideSquare.text.toString().trim()

                if (positionXText.toString().isEmpty() || positionYText.toString().isEmpty() || wideSquareText.toString().isEmpty()) {
                    // Tampilkan pesan kesalahan jika salah satu EditText kosong
                    Toast.makeText(this, "Harap isi semua kolom", Toast.LENGTH_LONG).show()
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
        repeat(square.squareSize.toInt()) { // luas.toInt() Ganti 100 dengan jumlah titik acak yang diinginkan
            val x = square.positionX + random.nextFloat() * square.squareSize
            val y = square.positionY + random.nextFloat() * square.squareSize
            dbHelper.addPoint(x, y)
        }
    }


}