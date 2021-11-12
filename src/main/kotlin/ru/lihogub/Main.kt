package ru.lihogub

import jetbrains.datalore.base.observable.collections.Collections
import jetbrains.letsPlot.geom.geomLine
import jetbrains.letsPlot.ggsize
import jetbrains.letsPlot.letsPlot
import ru.lihogub.common.generateInterpolationNodes
import ru.lihogub.task04lagrangenewtoninterpolation.interpolateLagrangeMethod
import ru.lihogub.task04lagrangenewtoninterpolation.interpolateNewtonMethod
import kotlin.math.*


fun main() {
    val func: (Double) -> Double = { x -> sign(sin(x)) }
    val nodesCount = 10
    val lowerBound = -5.0
    val upperBound = 5.0
    val nodeList = generateInterpolationNodes(nodesCount, lowerBound, upperBound) { _x -> func(_x) }

    println(nodeList)

    val resultPointCount = 100

    val originalX = List(resultPointCount) { i -> lowerBound + i * (upperBound - lowerBound) / resultPointCount }
    val originalY = originalX.map { x -> func(x) }
    val lagrangeY = originalX.map { x -> interpolateLagrangeMethod(x, nodeList) }
    val newtonY = originalX.map { x -> interpolateNewtonMethod(x, nodeList) }

    val data = mapOf(
        "originalX" to originalX,
        "originalY" to originalY,
        "lagrangeY" to lagrangeY,
        "newtonY" to newtonY
    )

    val p = letsPlot(data) +
            geomLine(color = "black") { x = "originalX"; y = "originalY"; } +
            geomLine(color = "blue") { x = "originalX"; y = "newtonY"; } +
            geomLine(color = "red") { x = "originalX"; y = "lagrangeY"; } +
            ggsize(500, 250)

    p.show()

    val maxDeltaNewton = originalY.zip(newtonY).maxOf { pair -> abs(pair.first - pair.second) }
    val maxDeltaLagrange = originalY.zip(lagrangeY).maxOf { pair -> abs(pair.first - pair.second) }

    println("Max inaccurance:")
    println("Lagrange: $maxDeltaLagrange")
    println("Newton: $maxDeltaNewton")
}