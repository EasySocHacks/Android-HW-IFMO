package ru.easy.soc.hacks.hw1

import android.util.Log
import android.widget.HorizontalScrollView
import java.math.BigDecimal
import java.math.RoundingMode

class EventHandler  {
    private var mainActivity : MainActivity

    constructor(mainActivity: MainActivity) {
        this.mainActivity = mainActivity

        setButtonListeners()
    }

    private fun setButtonListeners() {
        mainActivity.clearAllButton.setOnClickListener { onClearAllButtonClickEvent() }
        mainActivity.backspaceButton.setOnClickListener { onBackspaceButtonClickEvent() }

        mainActivity.plusButton.setOnClickListener { onOperationButtonClickEvent(OperationType.PLUS) }
        mainActivity.minusButton.setOnClickListener { onOperationButtonClickEvent(OperationType.MINUS) }
        mainActivity.multiplyButton.setOnClickListener {
            onOperationButtonClickEvent(
                OperationType.MULTIPLY
            )
        }
        mainActivity.divideButton.setOnClickListener { onOperationButtonClickEvent(OperationType.DIVIDE) }
        mainActivity.changeNegationButton.setOnClickListener {
            onOperationButtonClickEvent(
                OperationType.CHANGE_NEGATION
            )
        }
        mainActivity.dotButton.setOnClickListener { onDotButtonClickEvent() }
        mainActivity.equalsButton.setOnClickListener { onOperationButtonClickEvent(OperationType.EQUALS) }

        mainActivity.zeroButton.setOnClickListener { onDigitButtonClickEvent(DigitType.ZERO) }
        mainActivity.oneButton.setOnClickListener { onDigitButtonClickEvent(DigitType.ONE) }
        mainActivity.twoButton.setOnClickListener { onDigitButtonClickEvent(DigitType.TWO) }
        mainActivity.threeButton.setOnClickListener { onDigitButtonClickEvent(DigitType.THREE) }
        mainActivity.fourButton.setOnClickListener { onDigitButtonClickEvent(DigitType.FOUR) }
        mainActivity.fiveButton.setOnClickListener { onDigitButtonClickEvent(DigitType.FIVE) }
        mainActivity.sixButton.setOnClickListener { onDigitButtonClickEvent(DigitType.SIX) }
        mainActivity.sevenButton.setOnClickListener { onDigitButtonClickEvent(DigitType.SEVEN) }
        mainActivity.eightButton.setOnClickListener { onDigitButtonClickEvent(DigitType.EIGHT) }
        mainActivity.nineButton.setOnClickListener { onDigitButtonClickEvent(DigitType.NINE) }
    }

    private fun onClearAllButtonClickEvent() {
        mainActivity.inputField.text = "0"
        mainActivity.lastOperationType = OperationType.NOTHING

        mainActivity.calculatorResult = BigDecimal.ZERO

        Log.i(MainActivity.TAG, "Clear all info")
    }

    private fun onBackspaceButtonClickEvent() {
        if (mainActivity.inputField.text.length != 1 &&
            !(mainActivity.inputField.text.length == 2 && mainActivity.inputField.text.first() == '-')) {
            mainActivity.inputField.text = mainActivity.inputField.text.substring(0, mainActivity.inputField.text.length - 1)
        }

        moveHorizontalScrollBarToRightWithDelay(35)

        Log.i(MainActivity.TAG, "Backspace character")
    }

    private fun onOperationButtonClickEvent(operationType: OperationType) {
        if (operationType == OperationType.NOTHING) {
            Log.wtf(
                MainActivity.TAG,
                "Unexpected operation $operationType occurred while processing button click event"
            )
            return
        }

        if (operationType == OperationType.CHANGE_NEGATION) {
            if (isNumber(mainActivity.inputField.text)) {
                if (mainActivity.inputField.text.first() == '-') {
                    mainActivity.inputField.text = mainActivity.inputField.text.substring(1)
                } else {
                    mainActivity.inputField.text = "-".plus(mainActivity.inputField.text)
                }
            }

            return
        }

        if (mainActivity.lastOperationType == OperationType.NOTHING) {
            mainActivity.calculatorResult = BigDecimal(mainActivity.inputField.text.toString())
        } else {
            if (isNumber(mainActivity.inputField.text)) {
                mainActivity.calculatorResult = doCalculate(
                    mainActivity.calculatorResult, BigDecimal(
                        mainActivity.inputField.text.toString()), mainActivity.lastOperationType
                )
            }
        }

        if (operationType == OperationType.EQUALS) {
            mainActivity.lastOperationType = OperationType.NOTHING

            mainActivity.inputField.text = mainActivity.calculatorResult.stripTrailingZeros().toPlainString()

            mainActivity.calculatorResult = BigDecimal.ZERO

            moveHorizontalScrollBarToRightWithDelay()
        } else {
            mainActivity.lastOperationType = operationType

            mainActivity.inputField.text = operationType.stringValue
        }

        Log.i(MainActivity.TAG, "Pressed $operationType operation button")
    }

    private fun onDotButtonClickEvent() {
        for (inputFieldTextChar in mainActivity.inputField.text) {
            if (inputFieldTextChar == '.') {
                return
            }
        }

        if (!isNumber(mainActivity.inputField.text)) {
            return
        }

        mainActivity.inputField.text = mainActivity.inputField.text.toString().plus(".")

        moveHorizontalScrollBarToRightWithDelay(0)
    }

    private fun onDigitButtonClickEvent(digitType: DigitType) {
        if (mainActivity.inputField.text.toString() == "0" || !isNumber(mainActivity.inputField.text)) {
            mainActivity.inputField.text = digitType.stringValue
        } else {
            mainActivity.inputField.text = mainActivity.inputField.text.toString().plus(digitType.stringValue)
        }

        moveHorizontalScrollBarToRightWithDelay()
    }

    private fun doCalculate(
        firstNumber: BigDecimal,
        secondNumber: BigDecimal,
        operationType: OperationType
    ): BigDecimal {
        return when (operationType) {
            OperationType.PLUS -> (firstNumber + secondNumber).stripTrailingZeros()
            OperationType.MINUS -> (firstNumber - secondNumber).stripTrailingZeros()
            OperationType.MULTIPLY -> (firstNumber * secondNumber).stripTrailingZeros()
            OperationType.DIVIDE -> {
                if (secondNumber.stripTrailingZeros() == BigDecimal.ZERO) {
                    BigDecimal.ZERO
                } else {
                    (1.0.toBigDecimal() * firstNumber.divide(
                        secondNumber,
                        10,
                        RoundingMode.HALF_UP
                    )).stripTrailingZeros()
                }
            }
            else -> {
                Log.wtf(
                    MainActivity.TAG,
                    "Unexpected operation type $operationType occurred while computing result"
                )

                return BigDecimal.ZERO
            }
        }
    }

    private fun isNumber(stringForCheck : CharSequence) : Boolean {
        return stringForCheck.last() in '0' .. '9' || stringForCheck.last() == '.'
    }

    private fun moveHorizontalScrollBarToRightWithDelay(delay : Long = 0L) {
        mainActivity.inputFieldScroll.postDelayed ({
            mainActivity.inputFieldScroll.fullScroll(HorizontalScrollView.FOCUS_RIGHT)
        },
        delay)
    }
}