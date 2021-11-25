package ru.lihogub.task05rootfinding

import kotlin.math.abs

fun findRootsSecantMethod(
    lowerBound: Double,
    upperBound: Double,
    epsilon: Double,
    func: (Double) -> Double
): List<Double> {
    var a = lowerBound
    var b = upperBound
    var c = b

    while (abs(func(c)) > epsilon) {
        c = b - func(b) * (b - a) / (func(b) - func(a))
        a = b
        b = c
    }
    return listOf(c)
}