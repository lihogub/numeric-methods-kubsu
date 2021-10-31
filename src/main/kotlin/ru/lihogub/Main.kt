package ru.lihogub

import ru.lihogub.common.generateInterpolationNodes
import ru.lihogub.task04lagrangenewtoninterpolation.interpolateLagrangeMethod
import ru.lihogub.task04lagrangenewtoninterpolation.interpolateNewtonMethod
import kotlin.math.*

fun main() {
    val func: (Double) -> Double = { x -> 1.0 / 5.0}
    val nodesCount = 10
    val lowerBound = 0.0
    val upperBound = 1.0
    val nodeList = generateInterpolationNodes(nodesCount, lowerBound, upperBound) { func(it) }
    println(nodeList)
    val x = 0.1
    val y = interpolateNewtonMethod(x, nodeList)
    val y1 = interpolateLagrangeMethod(x, nodeList)
    println("x=$x, y=$y, y1=$y1, f(x)=${func(x)}, delta=${abs(func(x) - y)}")
}