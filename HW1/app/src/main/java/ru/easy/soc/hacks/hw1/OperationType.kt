package ru.easy.soc.hacks.hw1

enum class OperationType(val stringValue: String) {
    PLUS("+"),
    MINUS("-"),
    MULTIPLY("*"),
    DIVIDE("/"),
    CHANGE_NEGATION("-"),
    EQUALS("="),
    NOTHING("")
}