package ru.lihogub.task04interpolation

fun interpolateLagrangeMethod(x: Double, interpolationNodes: List<Pair<Double, Double>>): Double {
    var s = 0.0
    for (k in interpolationNodes.indices) {
        var product = 1.0
        for (j in interpolationNodes.indices) {
            if (k == j) continue
            product *= (x - interpolationNodes[j].first) / (interpolationNodes[k].first - interpolationNodes[j].first)
        }
        s += product * interpolationNodes[k].second
    }
    return s
}