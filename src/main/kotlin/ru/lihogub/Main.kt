package ru.lihogub

import ru.lihogub.common.generateSystemOfLinearEquations
import ru.lihogub.common.generateSystemOfLinearEquationsWithStrongDiagonalDominant
import ru.lihogub.task02methodgauss.calcVectorDiff
import ru.lihogub.task02methodgauss.solveSystemOfLinearEquationsGaussWithRowReplace
import ru.lihogub.task03jacobyseidelmethod.solveSystemOfLinearEquationsJacobi
import ru.lihogub.task03jacobyseidelmethod.solveSystemOfLinearEquationsSeidel

fun main() {
    runSeidelMethod()
}

fun runJacobiMethod() {
    val generatedAXB = generateSystemOfLinearEquationsWithStrongDiagonalDominant(50, 0.0, 10.0, 10.0)

//    println("Matrix A: \n${generatedAXB.first}")
    println("Vector X: \n${generatedAXB.second}")
//    println("Vector B: \n${generatedAXB.third}")
    val computedX = solveSystemOfLinearEquationsJacobi(generatedAXB.first, generatedAXB.third, 10e-5)
    println("Computed X: \n$computedX")
    println("Inaccurance: \n${calcVectorDiff(generatedAXB.second, computedX)}")
}

fun runSeidelMethod() {
    val generatedAXB = generateSystemOfLinearEquationsWithStrongDiagonalDominant(50, 0.0, 10.0, 10.0)

//    println("Matrix A: \n${generatedAXB.first}")
    println("Vector X: \n${generatedAXB.second}")
//    println("Vector B: \n${generatedAXB.third}")
    val computedX = solveSystemOfLinearEquationsSeidel(generatedAXB.first, generatedAXB.third, 10e-5)
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
