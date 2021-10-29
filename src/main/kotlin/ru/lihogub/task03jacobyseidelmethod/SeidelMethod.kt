package ru.lihogub.task03jacobyseidelmethod

import org.jetbrains.kotlinx.multik.api.d1array
import org.jetbrains.kotlinx.multik.api.linalg.dot
import org.jetbrains.kotlinx.multik.api.linalg.inv
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.zeros
import org.jetbrains.kotlinx.multik.ndarray.data.*
import org.jetbrains.kotlinx.multik.ndarray.operations.minus
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
 * x(0) = [[1, 1]]
 *
 * x(k+1) = (L + D) ^ (-1) * (b - U*x(k)
 *
 * @return vectorX
 */
fun solveSystemOfLinearEquationsSeidel(matrixA: D2Array<Double>, vectorB: D1Array<Double>, epsilon: Double): D1Array<Double> {
    val size = vectorB.size

    val matrixU = mk.zeros<Double>(size, size)
    for (i in 0 until size)
        for (j in i + 1 until size)
            matrixU[i, j] = matrixA[i, j]

    val matrixLInv = mk.linalg.inv(matrixA - matrixU)

    var vectorX = mk.d1array(size) { 0.0 }
    var inaccuracy: Double
    var i = 1
    do {
        vectorX = mk.linalg.dot(matrixLInv, vectorB  - mk.linalg.dot(matrixU, vectorX))

        inaccuracy = calcVectorDiff(vectorB, mk.linalg.dot(matrixA, vectorX))
        i++
    } while (inaccuracy > epsilon)

    println("Iterations: \n$i")

//    println("U: \n$matrixU")
//    println("L: \n$matrixL")
//    println("D inverted:\n$matrixDInv")
//    println("T: \n$matrixT")
//    println("C: \n$vectorC")
//    println("X: \n$vectorX")

    return vectorX
}