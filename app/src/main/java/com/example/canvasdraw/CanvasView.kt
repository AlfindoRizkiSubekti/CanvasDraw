package com.example.canvasdraw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View

class CanvasView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint()
    private val dbHelper = DatabaseHelper(context)

    private var square = Square()

    init {
        paint.color = Color.BLACK
        paint.strokeWidth = 5f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)


        // Gambar kotak pertama (garis luar saja)
        paint.color = Color.BLUE
        paint.style = Paint.Style.STROKE
        canvas.drawRect(square.positionX, square.positionY, square.positionX + square.squareSize, square.positionY + square.squareSize, paint)

        // Gambar titik-titik acak di dalam kotak pertama
        val points = dbHelper.getAllPoints()
        paint.style = Paint.Style.FILL
        for (point in points) {
            Log.d("CanvasView", "Point: ${point.id}, ${point.x}, ${point.y}")
            canvas.drawPoint(point.x, point.y, paint)
        }
    }

    fun moveSquares(newPositionX: Float,newPositionY: Float,newFirstSquareSize: Float) {
        square.positionX= newPositionX
        square.positionY= newPositionY
        square.squareSize= newFirstSquareSize

        invalidate()
    }

}