package ru.lihogub.task03jacobyseidelmethod

import org.jetbrains.kotlinx.multik.api.*
import org.jetbrains.kotlinx.multik.api.linalg.dot
import org.jetbrains.kotlinx.multik.ndarray.data.*
import org.jetbrains.kotlinx.multik.ndarray.operations.map
import org.jetbrains.kotlinx.multik.ndarray.operations.minus
import org.jetbrains.kotlinx.multik.ndarray.operations.plus
import org.jetbrains.kotlinx.multik.ndarray.operations.times
import ru.lihogub.task02methodgauss.calcVectorDiff

/**
 * System of linear equations **Ax = b**
 *
 * A = D + U + L
 *
 * D - diagonal matrix,
 * U - upper triangle matrix,
 * L - upper triangle matrix
 *
 * T = -D^(-1) * (L + U)
 *
 * C = D^(-1) * b
 *
 * x(0) = [[1, 1]]
 *
 * x(k+1) = T * x(k) + C
 *
 * @return vectorX
 */
fun solveSystemOfLinearEquationsJacobi(matrixA: D2Array<Double>, vectorB: D1Array<Double>, epsilon: Double): D1Array<Double> {
    val size = vectorB.size

    val matrixU = mk.zeros<Double>(size, size)
    for (i in 0 until size)
        for (j in i + 1 until size)
            matrixU[i, j] = matrixA[i, j]


    val matrixL = mk.zeros<Double>(size, size)
    for (i in 1 until size)
        for (j in 0 until i)
            matrixL[i, j] = matrixA[i, j]

    val matrixDInv = (matrixA - matrixU - matrixL)
        .map { if (it != 0.0) 1 / it else it }


    val matrixT = mk.linalg.dot(matrixDInv * (-1.0), (matrixL + matrixU))
    val vectorC = mk.linalg.dot(matrixDInv, vectorB)

    var vectorX = mk.d1array(size) { 0.0 }
    var inaccuracy: Double
    var i = 1
    do {
        vectorX = mk.linalg.dot(matrixT, vectorX) + vectorC
        inaccuracy = calcVectorDiff(vectorB, mk.linalg.dot(matrixA, vectorX))
        i++
    } while (inaccuracy > epsilon)

//    println("Iterations: \n$i")

//    println("U: \n$matrixU")
//    println("L: \n$matrixL")
//    println("D inverted:\n$matrixDInv")
//    println("T: \n$matrixT")
//    println("C: \n$vectorC")
//    println("X: \n$vectorX")

    return vectorX
}