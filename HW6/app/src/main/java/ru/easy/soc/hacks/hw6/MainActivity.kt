package ru.easy.soc.hacks.hw6

import android.animation.AnimatorSet
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import kotlinx.android.synthetic.main.activity_main.*
import ru.easy.soc.hacks.hw6.myviewanim.AnimatedView
import ru.easy.soc.hacks.hw6.propertyanim.PropertyAnimation

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        PropertyAnimation(animatedCircle_1, animatedCircle_2, animatedCircle_3).startAnimation()

    }
}