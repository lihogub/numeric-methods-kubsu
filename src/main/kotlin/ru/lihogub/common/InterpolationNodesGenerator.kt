package ru.lihogub.common

fun generateInterpolationNodes(
    nodesCount: Int,
    lowerBound: Double,
    upperBound: Double,
    function: (Double) -> Double
): List<Pair<Double, Double>> = List(nodesCount) { nodeIndex ->
    val intervalLength = (upperBound - lowerBound) / (nodesCount - 1)
    val x = lowerBound + nodeIndex * intervalLength
    x to function(x)
}