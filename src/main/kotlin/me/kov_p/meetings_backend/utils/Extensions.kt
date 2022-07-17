package me.kov_p.meetings_backend.utils

fun Boolean?.orFalse() = this ?: false

fun Long?.orZero() = this ?: 0

fun Int?.orZero() = this ?: 0