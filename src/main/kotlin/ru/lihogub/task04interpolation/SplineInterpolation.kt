package ru.lihogub.task04interpolation

import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.zeros
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.set
import ru.lihogub.task03jacobyseidelmethod.solveSystemOfLinearEquationsJacobi
import kotlin.math.pow

fun interpolateSplineMethod(x: Double, interpolationNodes: List<Pair<Double, Double>>): Double {
    val size = interpolationNodes.size

    val vectorH = mk.zeros<Double>(size)
    for (i in 1 until size) {
        vectorH[i] = interpolationNodes[i].first - interpolationNodes[i - 1].first
    }

    val vectorA = mk.zeros<Double>(size)
    for (i in 0 until size) {
        vectorA[i] = interpolationNodes[i].second
    }

    val matrixT = mk.zeros<Double>(size, size)
    matrixT[0, 0] = 1.0
    matrixT[size - 1, size - 1] = 1.0
    for (i in 1 until size - 1) {
        matrixT[i, i - 1] = vectorH[i]
        matrixT[i, i] = 2 * (vectorH[i] + vectorH[i + 1])
        matrixT[i, i + 1] = vectorH[i + 1]
    }

    val vectorF = mk.zeros<Double>(size)
    for (i in 1 until size - 1) {
        vectorF[i] = 6 * ((vectorA[i + 1] - vectorA[i]) / vectorH[i + 1] - (vectorA[i] - vectorA[i - 1]) / vectorH[i])
    }

    val vectorC = solveSystemOfLinearEquationsJacobi(matrixT, vectorF, 10e-5)


    val vectorD = mk.zeros<Double>(size)
    for (i in 1 until size) {
        vectorD[i] = (vectorC[i] - vectorC[i - 1]) / vectorH[i]
    }

    val vectorB = mk.zeros<Double>(size)
    for (i in 1 until size) {
        vectorB[i] = vectorH[i] * vectorC[i] / 2 - vectorD[i] * vectorH[i].pow(2) / 6 + (vectorA[i] - vectorA[i - 1]) / vectorH[i]
    }

//    println(vectorA)
//    println(vectorB)
//    println(vectorC)
//    println(vectorD)

    val idx = interpolationNodes.indexOfFirst { pair -> x < pair.first }
    val offsetX = x - interpolationNodes[idx].first
    var result = vectorA[idx]
    result += offsetX *  vectorB[idx]
    result += offsetX.pow(2) *  vectorC[idx] / 2
    result += offsetX.pow(3) *  vectorD[idx] / 6

    return result
}