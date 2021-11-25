package ru.lihogub.task05rootfinding

import kotlin.math.abs

fun findRootsBisectionMethod(
    lowerBound: Double,
    upperBound: Double,
    epsilon: Double,
    func: (Double) -> Double
): List<Double> {
    var a = lowerBound
    var b = upperBound
    var center = (a + b) / 2

    while (abs(func(center)) > epsilon) {
        if (func(a) * func(center) <= 0) {
            b = center
        } else if (func(center) * func(b) <= 0) {
            a = center
        } else {
            return emptyList()
        }
        center = (a + b) / 2
    }
    return listOf(center)
}

fun findRootsBisectionMethodRecursive(
    lowerBound: Double,
    upperBound: Double,
    epsilon: Double,
    func: (Double) -> Double
): List<Double> {
    val roots = mutableListOf<Double>()
    _bisectionMethod(lowerBound, upperBound, epsilon, roots, func)
    return roots
}

fun _bisectionMethod(
    leftBound: Double,
    rightBound: Double,
    epsilon: Double,
    roots: MutableList<Double>,
    func: (Double) -> Double
) {
    val center = (rightBound + leftBound) / 2
//    println("$leftBound <-> $rightBound")
    if (abs(func(center)) < epsilon) {
        roots.add(center)
        return
    }
    if (rightBound - leftBound > epsilon * 10e-2) {
        _bisectionMethod(leftBound, center, epsilon, roots, func)
        _bisectionMethod(center, rightBound, epsilon, roots, func)
    }
}