package ru.easy.soc.hacks.hw6.myviewanim

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.view.postDelayed
import ru.easy.soc.hacks.hw6.R
import kotlin.math.min
import kotlin.math.roundToLong

class AnimatedView(context: Context?, attrs: AttributeSet?) : View(context, attrs)  {
    private val paint = Paint()
    private var time = 5000f
    private val fps = 45f
    private val step : Float

    private var currentStepStart = 0
    private var currentStepEnd = 0

    private var circleColor = Color.MAGENTA

    init {
        val typedArray = context?.obtainStyledAttributes(attrs, R.styleable.AnimatedView)

        try {
            time = typedArray?.getFloat(R.styleable.AnimatedView_durationMillis, 5000f) ?: 5000f
            circleColor = typedArray?.getColor(R.styleable.AnimatedView_circleColor, Color.MAGENTA) ?: Color.MAGENTA
        } finally {
            typedArray?.recycle()
        }

        step = fps * time / 1000f / 4.0f
        paint.color = circleColor
    }

    override fun onDraw(canvas: Canvas?) {
        val minAreaSize = min(measuredWidth, measuredHeight).toFloat()

        when {
            currentStepStart < step -> currentStepStart++
            currentStepEnd < step -> currentStepEnd++
            else -> {
                currentStepStart = 0
                currentStepEnd = 0
            }
        }

        val minCircleRadius = minAreaSize / 5.0f
        val maxCircleRadius = minAreaSize / 2.0f

        var circleRadius = minCircleRadius

        if (currentStepStart >= step) {
            circleRadius = if (currentStepEnd < step / 2) {
                minCircleRadius + currentStepEnd * (maxCircleRadius - minCircleRadius) / (step / 2)
            } else {
                minCircleRadius + (step - currentStepEnd) * (maxCircleRadius - minCircleRadius) / (step / 2)
            }
        }

        val maxTranslateCanvas = (measuredWidth - circleRadius) / 2.0f + 2.0f * circleRadius

        when {
            currentStepStart < step / 2 -> canvas?.translate(currentStepStart * maxTranslateCanvas / (step / 2),0.0f)
            currentStepStart < step -> canvas?.translate((currentStepStart - step) * maxTranslateCanvas / (step / 2),0.0f)
        }

        canvas?.drawCircle(measuredWidth / 2.0f, measuredHeight / 2.0f, circleRadius, paint)

        postDelayed(this::invalidate, (1000f / fps).toLong())
    }

    private fun calculateSize(desiredSize : Int, measureSpec : Int): Int {
        val mode = MeasureSpec.getMode(measureSpec)
        val size = MeasureSpec.getSize(measureSpec)

        return when (mode) {
            MeasureSpec.EXACTLY -> size
            MeasureSpec.AT_MOST -> min(desiredSize, size)
            else -> desiredSize
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(
            calculateSize(suggestedMinimumWidth + paddingStart + paddingEnd, widthMeasureSpec),
            calculateSize(suggestedMinimumHeight + paddingBottom + paddingTop, heightMeasureSpec))
    }
}