package ru.lihogub.common

import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.ndarray
import org.jetbrains.kotlinx.multik.ndarray.data.D1Array
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import kotlin.random.Random

fun generateSystemOfLinearEquations(size: Int, lowerBound: Double, upperBound: Double): Triple<D2Array<Double>, D1Array<Double>, D1Array<Double>> {
    val matrixARaw = Array(size * size) { Random.nextDouble(lowerBound, upperBound) }
        .toDoubleArray()

    val matrixA = mk.ndarray(matrixARaw, size, size)

    val vectorXRaw = Array(size) { Random.nextDouble(lowerBound, upperBound) }
        .toDoubleArray()

    val vectorX = mk.ndarray(vectorXRaw)

    val vectorB = mk.linalg.dot(matrixA, vectorX)

    return Triple(matrixA, vectorX, vectorB)
}