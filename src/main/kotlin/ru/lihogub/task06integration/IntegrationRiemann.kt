package ru.lihogub.task06integration

import org.jetbrains.kotlinx.multik.api.linspace
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.ndarray.data.get

fun integrateRiemann(
    lowerBound: Double,
    upperBound: Double,
    intervalCount: Int,
    func: (Double) -> Double
): Double {
    val dx = (upperBound - lowerBound) / intervalCount
    val xArray = mk.linspace<Double>(lowerBound, upperBound, intervalCount + 1)
    var s = 0.0
    for (i in 0 until intervalCount) {
        val xMiddle = (xArray[i + 1] + xArray[i]) / 2
        s += func(xMiddle) * dx
    }
    return s
}