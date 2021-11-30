package ru.lihogub.task06integration

fun integrateSimpson(
    lowerBound: Double,
    upperBound: Double,
    intervalCount: Int,
    func: (Double) -> Double
): Double {
    val segments = intervalCount + (intervalCount % 2) // even count of segments needed

    val dx = (upperBound - lowerBound) / segments

    var s = func(lowerBound) + 4 * func(lowerBound + dx) + func(upperBound)
    for (i in 1 until segments / 2) {
        s += 2 * func(lowerBound + (2 * i) * dx) + 4 * func(lowerBound + (2 * i + 1) * dx)
    }
    return s * dx / 3
}