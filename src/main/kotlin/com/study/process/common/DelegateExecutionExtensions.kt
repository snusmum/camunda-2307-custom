package com.study.process.common

import org.camunda.bpm.engine.delegate.DelegateExecution

fun DelegateExecution.readString(variableName: String): String {
    return getVariable(variableName) as String
}

fun DelegateExecution.readInt(variableName: String): Int {
    return getVariable(variableName) as Int
}

inline fun <reified T : Enum<T>> DelegateExecution.readEnumFromString(variableName: String): T {
    return enumValueOf(readString(variableName))
}
