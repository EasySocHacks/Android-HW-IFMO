package ru.easy.soc.hacks.hw1

import android.util.Log
import android.widget.HorizontalScrollView
import java.math.BigDecimal
import java.math.RoundingMode

class EventHandler  {
    companion object {
        fun setButtonListeners() {
            MainActivity.clearAllButton.setOnClickListener { onClearAllButtonClickEvent() }
            MainActivity.backspaceButton.setOnClickListener { onBackspaceButtonClickEvent() }

            MainActivity.plusButton.setOnClickListener { onOperationButtonClickEvent(OperationType.PLUS) }
            MainActivity.minusButton.setOnClickListener { onOperationButtonClickEvent(OperationType.MINUS) }
            MainActivity.multiplyButton.setOnClickListener {
                onOperationButtonClickEvent(
                    OperationType.MULTIPLY
                )
            }
            MainActivity.divideButton.setOnClickListener { onOperationButtonClickEvent(OperationType.DIVIDE) }
            MainActivity.changeNegationButton.setOnClickListener {
                onOperationButtonClickEvent(
                    OperationType.CHANGE_NEGATION
                )
            }
            MainActivity.dotButton.setOnClickListener { onDotButtonClickEvent() }
            MainActivity.equalsButton.setOnClickListener { onOperationButtonClickEvent(OperationType.EQUALS) }

            MainActivity.zeroButton.setOnClickListener { onDigitButtonClickEvent(DigitType.ZERO) }
            MainActivity.oneButton.setOnClickListener { onDigitButtonClickEvent(DigitType.ONE) }
            MainActivity.twoButton.setOnClickListener { onDigitButtonClickEvent(DigitType.TWO) }
            MainActivity.threeButton.setOnClickListener { onDigitButtonClickEvent(DigitType.THREE) }
            MainActivity.fourButton.setOnClickListener { onDigitButtonClickEvent(DigitType.FOUR) }
            MainActivity.fiveButton.setOnClickListener { onDigitButtonClickEvent(DigitType.FIVE) }
            MainActivity.sixButton.setOnClickListener { onDigitButtonClickEvent(DigitType.SIX) }
            MainActivity.sevenButton.setOnClickListener { onDigitButtonClickEvent(DigitType.SEVEN) }
            MainActivity.eightButton.setOnClickListener { onDigitButtonClickEvent(DigitType.EIGHT) }
            MainActivity.nineButton.setOnClickListener { onDigitButtonClickEvent(DigitType.NINE) }
        }

        private fun onClearAllButtonClickEvent() {
            MainActivity.inputField.text = "0"
            MainActivity.lastOperationType = OperationType.NOTHING

            MainActivity.calculatorResult = BigDecimal.ZERO

            Log.i(MainActivity.TAG, "Clear all info")
        }

        private fun onBackspaceButtonClickEvent() {
            if (MainActivity.inputField.text.length != 1 &&
                !(MainActivity.inputField.text.length == 2 && MainActivity.inputField.text.first() == '-')) {
                MainActivity.inputField.text = MainActivity.inputField.text.substring(0, MainActivity.inputField.text.length - 1)
            }

            moveHorizontalScrollBarToRight() //TODO: why scroll to left?!?! LEAVE?

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
                if (isNumber(MainActivity.inputField.text)) {
                    if (MainActivity.inputField.text.first() == '-') {
                        MainActivity.inputField.text = MainActivity.inputField.text.substring(1)
                    } else {
                        MainActivity.inputField.text = "-".plus(MainActivity.inputField.text)
                    }
                }

                return
            }

            if (MainActivity.lastOperationType == OperationType.NOTHING) {
                MainActivity.calculatorResult = BigDecimal(MainActivity.inputField.text.toString())
            } else {
                if (isNumber(MainActivity.inputField.text)) {
                    MainActivity.calculatorResult = doCalculate(
                        MainActivity.calculatorResult, BigDecimal(
                            MainActivity.inputField.text.toString()), MainActivity.lastOperationType
                    )
                }
            }

            if (operationType == OperationType.EQUALS) {
                MainActivity.lastOperationType = OperationType.NOTHING

                MainActivity.inputField.text = MainActivity.calculatorResult.stripTrailingZeros().toPlainString()

                MainActivity.calculatorResult = BigDecimal.ZERO

                moveHorizontalScrollBarToRight() //TODO: why scroll to left?!?! LEAVE?
            } else {
                MainActivity.lastOperationType = operationType

                MainActivity.inputField.text = operationType.stringValue
            }

            Log.i(MainActivity.TAG, "Pressed $operationType operation button")
        }

        private fun onDotButtonClickEvent() {
            for (inputFieldTextChar in MainActivity.inputField.text) {
                if (inputFieldTextChar == '.') {
                    return
                }
            }

            if (!isNumber(MainActivity.inputField.text)) {
                return
            }

            MainActivity.inputField.text = MainActivity.inputField.text.toString().plus(".")

            moveHorizontalScrollBarToRight()
        }

        private fun onDigitButtonClickEvent(digitType: DigitType) {
            if (MainActivity.inputField.text.toString() == "0" || !isNumber(MainActivity.inputField.text)) {
                MainActivity.inputField.text = digitType.stringValue
            } else {
                MainActivity.inputField.text = MainActivity.inputField.text.toString().plus(digitType.stringValue)
            }

            moveHorizontalScrollBarToRight()
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
                    if (secondNumber.stripTrailingZeros() == BigDecimal.ZERO) { //TODO: write "Error" and then check everywhere for "Error" string
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

        private fun moveHorizontalScrollBarToRight() {
            MainActivity.inputFieldScroll.post {
                MainActivity.inputFieldScroll.fullScroll(HorizontalScrollView.FOCUS_RIGHT)
            }
        }
    }
}