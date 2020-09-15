package ru.easy.soc.hacks.hw1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.HorizontalScrollView
import android.widget.TextView
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "HW1_Calculator"

        lateinit var inputFieldScroll: HorizontalScrollView
        lateinit var inputField: TextView

        lateinit var clearAllButton: Button
        lateinit var backspaceButton: Button
        lateinit var plusButton: Button
        lateinit var minusButton: Button
        lateinit var multiplyButton: Button
        lateinit var divideButton: Button
        lateinit var changeNegationButton: Button
        lateinit var dotButton: Button
        lateinit var equalsButton: Button

        lateinit var zeroButton: Button
        lateinit var oneButton: Button
        lateinit var twoButton: Button
        lateinit var threeButton: Button
        lateinit var fourButton: Button
        lateinit var fiveButton: Button
        lateinit var sixButton: Button
        lateinit var sevenButton: Button
        lateinit var eightButton: Button
        lateinit var nineButton: Button

        var calculatorResult = BigDecimal.ZERO

        var lastOperationType = OperationType.NOTHING

        private const val CALCULATOR_RESULT_SAVING_INSTANCE_KEY = "CalculatorResult"
        private const val LAST_OPERATION_TYPE_SAVING_INSTANCE_KEY = "LastOperationType"
        private const val INPUT_FIELD_STRING_SAVING_INSTANCE_KEY = "InputFieldString"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "Called 'onCreate' method : processing")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)

        initViews()
        EventHandler.setButtonListeners()

        Log.i(TAG, "Called 'onCreate' method : done")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.i(TAG, "Saving instance state : processing")

        outState.putString(CALCULATOR_RESULT_SAVING_INSTANCE_KEY, calculatorResult.toPlainString())
        outState.putSerializable(LAST_OPERATION_TYPE_SAVING_INSTANCE_KEY, lastOperationType)
        outState.putCharSequence(INPUT_FIELD_STRING_SAVING_INSTANCE_KEY, inputField.text)

        super.onSaveInstanceState(outState)

        Log.i(TAG, "Saving instance state : done")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        Log.i(TAG, "Restoring instance state : processing")

        super.onRestoreInstanceState(savedInstanceState)

        calculatorResult = BigDecimal(savedInstanceState.getString(
            CALCULATOR_RESULT_SAVING_INSTANCE_KEY
        ))
        lastOperationType = savedInstanceState.getSerializable(
            LAST_OPERATION_TYPE_SAVING_INSTANCE_KEY
        ) as OperationType
        inputField.text = savedInstanceState.getCharSequence(INPUT_FIELD_STRING_SAVING_INSTANCE_KEY)

        Log.i(TAG, "Restoring instance state : done")
    }

    private fun initViews() {
        Log.i(TAG, "Initialised views : processing")

        inputFieldScroll = findViewById(R.id.inputFieldScroll)
        inputField = findViewById(R.id.inputField)

        clearAllButton = findViewById(R.id.clearAllButton)
        backspaceButton = findViewById(R.id.backspaceButton)
        plusButton = findViewById(R.id.plusButton)
        minusButton = findViewById(R.id.minusButton)
        multiplyButton = findViewById(R.id.multiplyButton)
        divideButton = findViewById(R.id.divideButton)
        changeNegationButton = findViewById(R.id.changeNegationButton)
        dotButton = findViewById(R.id.dotButton)
        equalsButton = findViewById(R.id.equalsButton)

        zeroButton = findViewById(R.id.zeroButton)
        oneButton = findViewById(R.id.oneButton)
        twoButton = findViewById(R.id.twoButton)
        threeButton = findViewById(R.id.threeButton)
        fourButton = findViewById(R.id.fourButton)
        fiveButton = findViewById(R.id.fiveButton)
        sixButton = findViewById(R.id.sixButton)
        sevenButton = findViewById(R.id.sevenButton)
        eightButton = findViewById(R.id.eightButton)
        nineButton = findViewById(R.id.nineButton)

        Log.i(TAG, "Initialised views : done")
    }
}