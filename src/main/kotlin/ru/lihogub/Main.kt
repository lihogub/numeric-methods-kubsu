package ru.lihogub

import org.jetbrains.kotlinx.multik.ndarray.data.D1Array
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import ru.lihogub.common.generateSystemOfLinearEquations
import ru.lihogub.task03jacobymethod.isStrictlyDiagonallyDominant
import ru.lihogub.task03jacobymethod.solveSystemOfLinearEquationsJacobi

fun main() {
    var generatedAXB: Triple<D2Array<Double>, D1Array<Double>, D1Array<Double>>
    do {
        generatedAXB = generateSystemOfLinearEquations(3)
    } while (!isStrictlyDiagonallyDominant(generatedAXB.first))

    println("Matrix A:")
    println(generatedAXB.first)

    println("Vector X:")
    println(generatedAXB.second)

    println("Vector B:")
    println(generatedAXB.third)

    val computedX = solveSystemOfLinearEquationsJacobi(generatedAXB.first, generatedAXB.third)
    println("Computed X: $computedX")
}