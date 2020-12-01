package ru.easy.soc.hacks.hw6.propertyanim

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.view.View
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class PropertyAnimation(
    private val animatedCircle_1 : View,
    private val animatedCircle_2 : View,
    private val animatedCircle_3 : View) {

    private val propertyAnimationDuration = 2000L
    private val fadeOutTimePercent = 0.3f
    private val fadeInTimePercent = 0.3f
    private val fadeOutRadius = 70
    private val rotatingAngle = 2.0f * PI.toFloat()

    fun startAnimation() {
        getCircleAnimator(animatedCircle_1, 0.0f).start()
        getCircleAnimator(animatedCircle_2, 2.0f * PI.toFloat() / 3.0f).start()
        getCircleAnimator(animatedCircle_3, 4.0f * PI.toFloat() / 3.0f).start()
    }

    private fun getCircleAnimator(animatedCircle : View, startAngle : Float): AnimatorSet {
        var radius = 0.0f
        var angle = startAngle
        var lastDeltaAngle = 0.0f
        var lastDeltaX = 0.0f
        var lastDeltaY = 0.0f

        val animatorSet = AnimatorSet()

        val fadeOutAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
            repeatMode = ValueAnimator.RESTART
            repeatCount = ValueAnimator.INFINITE

            addUpdateListener {
                if ((it.animatedValue as Float) <= fadeOutTimePercent) {
                    radius = (it.animatedValue as Float) * fadeOutRadius / fadeOutTimePercent
                }
            }
        }

        val fadeInAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
            repeatMode = ValueAnimator.RESTART
            repeatCount = ValueAnimator.INFINITE

            addUpdateListener {
                if ((it.animatedValue as Float) >= 1f - fadeInTimePercent) {
                    radius = (1.0f - (it.animatedValue as Float)) * fadeOutRadius / fadeInTimePercent
                }
            }
        }

        val rotatingAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
            repeatMode = ValueAnimator.RESTART
            repeatCount = ValueAnimator.INFINITE

            addUpdateListener {
                val deltaAngle = (it.animatedValue as Float) * rotatingAngle

                angle += (lastDeltaAngle - deltaAngle)

                if (angle >= 2.0f * PI.toFloat()) {
                    angle -= 2.0f * PI.toFloat()
                }

                lastDeltaAngle = deltaAngle

                val deltaX = radius * cos(angle)
                val deltaY = radius * sin(angle)

                animatedCircle.x += (deltaX - lastDeltaX)
                animatedCircle.y += (deltaY - lastDeltaY)

                lastDeltaX = deltaX
                lastDeltaY = deltaY
            }
        }

        animatorSet.apply {
            duration = propertyAnimationDuration

            play(rotatingAnimator).with(fadeOutAnimator)
            play(rotatingAnimator).with(fadeInAnimator)

        }

        return animatorSet
    }
}