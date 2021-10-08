package ru.lihogub

import org.jetbrains.kotlinx.multik.ndarray.data.D1Array
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import ru.lihogub.common.generateSystemOfLinearEquations
import ru.lihogub.task02methodgauss.calcVectorDiff
import ru.lihogub.task02methodgauss.solveSystemOfLinearEquationsGaussWithRowReplace
import ru.lihogub.task03jacobymethod.isStrictlyDiagonallyDominant
import ru.lihogub.task03jacobymethod.solveSystemOfLinearEquationsJacobi

fun main() {
    runJacobiMethod()
}

fun runJacobiMethod() {
    var generatedAXB: Triple<D2Array<Double>, D1Array<Double>, D1Array<Double>>
    do {
        generatedAXB = generateSystemOfLinearEquations(3, 0.0, 1000.0)
    } while (!isStrictlyDiagonallyDominant(generatedAXB.first))


    println("Matrix A: \n${generatedAXB.first}")
    println("Vector X: \n${generatedAXB.second}")
    println("Vector B: \n${generatedAXB.third}")
    val computedX = solveSystemOfLinearEquationsJacobi(generatedAXB.first, generatedAXB.third)
    println("Computed X: \n$computedX")
    println("Inaccurance: \n${calcVectorDiff(generatedAXB.second, computedX)}")
}

fun runGaussMethod() {
    val generatedAXB = generateSystemOfLinearEquations(3, 0.0, 1000.0)
    val computedX = solveSystemOfLinearEquationsGaussWithRowReplace(generatedAXB.first, generatedAXB.third)

    println("Matrix A: \n${generatedAXB.first}")
    println("Vector X: \n${generatedAXB.second}")
    println("Vector B: \n${generatedAXB.third}")
    println("Computed X: \n$computedX")
    println("Inaccurance: \n${calcVectorDiff(generatedAXB.second, computedX)}")
}
