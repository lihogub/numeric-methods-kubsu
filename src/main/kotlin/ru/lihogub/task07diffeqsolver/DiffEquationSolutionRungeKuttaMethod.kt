package ru.lihogub.task07diffeqsolver

fun solveDiffEquationRungeKuttaMethod(
    x0: Double,
    y0: Double,
    upperBound: Double,
    segments: Int,
    func: (Double, Double) -> Double
): List<Pair<Double, Double>> {
    val pointList = mutableListOf<Pair<Double, Double>>()
    pointList.add(Pair(x0, y0))

    val h = (upperBound - x0) / segments

    for (i in 1 until segments) {
        val (prevX, prevY) = pointList.last()
        val k1 = func(prevX, prevY)
        val k2 = func(prevX + 0.5 * h, prevY + 0.5 * h * k1)
        val k3 = func(prevX + 0.5 * h, prevY + 0.5 * h * k2)
        val k4 = func(prevX + h, prevY + h * k3)
        val newX = prevX + h
        val newY = prevY + h * (k1 + 2 * k2 + 2 * k3 + k4) / 6
        pointList.add(newX to newY)
    }
    return pointList
}