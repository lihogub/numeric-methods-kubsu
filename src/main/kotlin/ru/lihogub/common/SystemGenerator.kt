package ru.lihogub.common

import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.ndarray
import org.jetbrains.kotlinx.multik.ndarray.data.D1Array
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import kotlin.math.abs
import kotlin.random.Random

fun generateSystemOfLinearEquations(
    size: Int,
    lowerBound: Double,
    upperBound: Double
): Triple<D2Array<Double>, D1Array<Double>, D1Array<Double>> {
    val matrixARaw = Array(size * size) { Random.nextDouble(lowerBound, upperBound) }
        .toDoubleArray()

    val matrixA = mk.ndarray(matrixARaw, size, size)

    val vectorXRaw = Array(size) { Random.nextDouble(lowerBound, upperBound) }
        .toDoubleArray()

    val vectorX = mk.ndarray(vectorXRaw)

    val vectorB = mk.linalg.dot(matrixA, vectorX)

    return Triple(matrixA, vectorX, vectorB)
}

fun generateSystemOfLinearEquationsWithStrongDiagonalDominant(
    size: Int,
    lowerBound: Double,
    upperBound: Double,
    dominantFactor: Double
): Triple<D2Array<Double>, D1Array<Double>, D1Array<Double>> {
    val matrixARaw = Array(size * size) { Random.nextDouble(lowerBound, upperBound) }
        .toDoubleArray()

    for (i in 0 until size) {
        var sumOfLine = 0.0
        for (j in 0 until size)
            sumOfLine += abs(matrixARaw[i*size + j])
        sumOfLine -= abs(matrixARaw[i*size + i])
        matrixARaw[i*size + i] = sumOfLine*dominantFactor
    }

    val matrixA = mk.ndarray(matrixARaw, size, size)

    val vectorXRaw = Array(size) { Random.nextDouble(lowerBound, upperBound) }
        .toDoubleArray()

    val vectorX = mk.ndarray(vectorXRaw)

    val vectorB = mk.linalg.dot(matrixA, vectorX)

    return Triple(matrixA, vectorX, vectorB)
}
