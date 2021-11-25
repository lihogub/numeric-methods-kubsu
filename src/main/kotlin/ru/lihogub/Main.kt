package ru.lihogub

import jetbrains.letsPlot.geom.geomLine
import jetbrains.letsPlot.ggsize
import jetbrains.letsPlot.letsPlot
import ru.lihogub.common.generateInterpolationNodes
import ru.lihogub.task04interpolation.interpolateLagrangeMethod
import ru.lihogub.task04interpolation.interpolateNewtonMethod
import ru.lihogub.task04interpolation.interpolateSplineMethod
import kotlin.math.*


fun main() {
//    val func: (Double) -> Double = { x -> 1 + 1 * x + 1 * x * x + 1 * x * x * x }
    val func: (Double) -> Double = { x -> abs(x) }
    val nodesCount = 5
    val lowerBound = -10.0
    val upperBound = 10.0
    val nodeList = generateInterpolationNodes(nodesCount, lowerBound, upperBound) { _x -> func(_x) }

    println(nodeList)

    val resultPointCount = 1000

    val originalX = List(resultPointCount) { i -> lowerBound + i * ((upperBound - lowerBound) / (resultPointCount - 1)) }
    val originalY = originalX.map { x -> func(x) }
    val lagrangeY = originalX.map { x -> interpolateLagrangeMethod(x, nodeList) }
    val newtonY = originalX.map { x -> interpolateNewtonMethod(x, nodeList) }
    val splineY = originalX.map { x -> interpolateSplineMethod(x, nodeList) }

    val data = mapOf(
        "originalX" to originalX,
        "originalY" to originalY,
        "lagrangeY" to lagrangeY,
        "newtonY" to newtonY,
        "splineY" to splineY
    )

    val p = letsPlot(data) +
            geomLine(color = "black") { x = "originalX"; y = "originalY"; } +
            geomLine(color = "blue") { x = "originalX"; y = "newtonY"; } +
            geomLine(color = "red") { x = "originalX"; y = "lagrangeY"; } +
            geomLine(color = "green") { x = "originalX"; y = "splineY"; } +
            ggsize(500, 250)

    p.show()

    val maxDeltaNewton = originalY.zip(newtonY).maxOf { pair -> abs(pair.first - pair.second) }
    val maxDeltaLagrange = originalY.zip(lagrangeY).maxOf { pair -> abs(pair.first - pair.second) }
    val maxDeltaSpline = originalY.zip(splineY).maxOf { pair -> abs(pair.first - pair.second) }

    println("Max inaccurance:")
    println("Lagrange: $maxDeltaLagrange")
    println("Newton: $maxDeltaNewton")
    println("Spline: $maxDeltaSpline")
}