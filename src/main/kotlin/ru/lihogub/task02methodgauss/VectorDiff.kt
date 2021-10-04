package ru.lihogub.task02methodgauss

import org.jetbrains.kotlinx.multik.ndarray.data.D1Array
import org.jetbrains.kotlinx.multik.ndarray.data.get
import kotlin.math.abs
import kotlin.math.max

fun calcVectorDiff(one: D1Array<Double>, other: D1Array<Double>): Double {
    if (one.size != other.size)
        return Double.POSITIVE_INFINITY

    var maxDiff = 0.0

    for (i in one.indices)
        maxDiff = max(maxDiff, abs(one[i] - other[i]))

    return maxDiff
}
