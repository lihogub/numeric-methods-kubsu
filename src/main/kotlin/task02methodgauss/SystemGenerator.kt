package task02methodgauss

import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.ndarray
import org.jetbrains.kotlinx.multik.ndarray.data.D1Array
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import kotlin.random.Random

fun generateSystemOfLinearEquations(size: Int): Triple<D2Array<Double>, D1Array<Double>, D1Array<Double>> {
    val matrixARaw = Array(size * size) { Random.nextDouble(0.0, 1.0) }
        .toDoubleArray()

    val matrixA = mk.ndarray(matrixARaw, size, size)

    val vectorXRaw = Array(size) { Random.nextDouble(0.0, 1.0) }
        .toDoubleArray()

    val vectorX = mk.ndarray(vectorXRaw)

    val vectorB = mk.linalg.dot(matrixA, vectorX)

    println("Matrix A:")
    println(matrixA)

    println("Vector X:")
    println(vectorX)

    println("Vector B:")
    println(vectorB)

    return Triple(matrixA, vectorX, vectorB)
}