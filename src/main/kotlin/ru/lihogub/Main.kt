package ru.lihogub

import jetbrains.letsPlot.geom.geomLine
import jetbrains.letsPlot.ggsize
import jetbrains.letsPlot.letsPlot
import org.jetbrains.kotlinx.multik.api.linspace
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.ndarray.operations.toDoubleArray
import ru.lihogub.task06integration.integrateRiemann
import ru.lihogub.task06integration.integrateSimpson
import ru.lihogub.task06integration.integrateTrapezoid
import ru.lihogub.task07diffeqsolver.solveDiffEquationRungeKuttaMethod
import kotlin.math.*

fun main() {
//    testMethods()
    testRungeKutta()
}

fun testMethods() {
    val func: (Double) -> Double = { x -> sin(x) }
    val epsilon = 10e-5
    val lowerBound = 0.0
    val upperBound = 10.0

    println("Riemann: " + computeSegments(epsilon, lowerBound, upperBound, func) {
            a, b, n, f -> integrateRiemann(a, b, n, f)
    })
    println("Trapezoid: " + computeSegments(epsilon, lowerBound, upperBound, func) {
            a, b, n, f -> integrateTrapezoid(a, b, n, f)
    })
    println("Simpson: " + computeSegments(epsilon, lowerBound, upperBound, func) {
            a, b, n, f -> integrateSimpson(a, b, n, f)
    })

    val n = 1000
    val originalX = mk.linspace<Double>(lowerBound, upperBound, 1000).toDoubleArray()
    val riemannY = originalX.map { x -> integrateRiemann(lowerBound, x, n, func) }
    val trapezoidY = originalX.map { x -> integrateTrapezoid(lowerBound, x, n, func) }
    val simpsonY = originalX.map { x -> integrateSimpson(lowerBound, x, n, func) }


    val data = mapOf(
        "originalX" to originalX,
        "riemannY" to riemannY,
        "trapezoidY" to trapezoidY,
        "simpsonY" to simpsonY
    )

    val p = letsPlot(data) +
            geomLine(color = "black") { x = "originalX"; y = "riemannY"; } +
            geomLine(color = "blue") { x = "originalX"; y = "trapezoidY"; } +
            geomLine(color = "red") { x = "originalX"; y = "simpsonY"; } +
            ggsize(500, 250)

    p.show()

    val maxDeltaRiemannTrapezoid = riemannY.zip(trapezoidY).maxOf { pair -> abs(pair.first - pair.second) }
    val maxDeltaRiemannSimpson = riemannY.zip(simpsonY).maxOf { pair -> abs(pair.first - pair.second) }
    val maxDeltaTrapezoidSimpson = trapezoidY.zip(riemannY).maxOf { pair -> abs(pair.first - pair.second) }

    println("Max inaccurance:")
    println("Riemann Trapezoid: $maxDeltaRiemannTrapezoid")
    println("Riemann Simpson: $maxDeltaRiemannSimpson")
    println("Trapezoid Simpson: $maxDeltaTrapezoidSimpson")
}

fun computeSegments(
    epsilon: Double,
    lowerBound: Double,
    upperBound: Double,
    func: (Double) -> Double,
    method: (Double, Double, Int, (Double) -> Double) -> Double
): Int {
    var segments = 4
    var prevResult = method(lowerBound, upperBound, 2, func)
    var currentResult = method(lowerBound, upperBound, 4, func)
    while (abs(currentResult - prevResult) > epsilon) {
        segments *= 2
        prevResult = currentResult
        currentResult = method(lowerBound, upperBound, segments, func)
    }
    println(currentResult)
    return segments
}

fun testRungeKutta() {
    val func: (Double, Double) -> Double = { x, y -> x }

    val pointList = solveDiffEquationRungeKuttaMethod(0.0, 1.0, 10.0, 50, func)
    val xList = pointList.map { it.first }
    val yList = pointList.map { it.second }


    val data = mapOf(
        "xList" to xList,
        "yList" to yList,
    )

    val p = letsPlot(data) +
            geomLine(color = "black") { x = "xList"; y = "yList"; } +
            ggsize(500, 250)

    p.show()
}