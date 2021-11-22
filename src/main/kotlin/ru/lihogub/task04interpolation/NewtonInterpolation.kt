package ru.lihogub.task04interpolation

fun interpolateNewtonMethod(x: Double, interpolationNodes: List<Pair<Double, Double>>): Double {
    val size = interpolationNodes.size
    val matrix = Array(size) { Array(size) { 0.0 } }
    for (i in 0 until size) {
        matrix[i][i] = interpolationNodes[i].second
    }

    for (layer in 0 until size) {
        for (startIndex in 0 until size - layer - 1) {
            val a = matrix[startIndex + 1][startIndex + layer + 1] - matrix[startIndex][startIndex + layer]
            val b = interpolationNodes[startIndex + layer + 1].first - interpolationNodes[startIndex].first
            matrix[startIndex][startIndex + layer + 1] = a / b
        }
    }

    var s = matrix[0][0]
    var m = (x - interpolationNodes[0].first)
    for (i in 1 until size) {
        s += m * matrix[0][i]
        m *= (x - interpolationNodes[i].first)
    }
    return s
}