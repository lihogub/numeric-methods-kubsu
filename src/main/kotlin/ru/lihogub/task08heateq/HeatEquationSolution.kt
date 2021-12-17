package ru.lihogub.task08heateq

import jetbrains.letsPlot.geom.geomLine
import jetbrains.letsPlot.ggsize
import jetbrains.letsPlot.letsPlot
import org.jetbrains.kotlinx.multik.api.*
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.set
import org.jetbrains.kotlinx.multik.ndarray.operations.toList
import kotlin.math.*

fun solveHeatEquation() {
    val T = 2.0

    val NT = 1000

    val drawLineCount = 10

    val deltaT = T / (NT - 1)

    val NX = floor(PI / 2 / sqrt(2 * deltaT) + 1).toInt()

    val deltaX = PI / 2 / (NX - 1)

    println("Intervals T: $NT")
    println("Delta T: $deltaT")
    println("Intervals X: $NX")
    println("Delta X: $deltaX")

    val matrix = mk.zeros<Double>(NT, NX)

    for (xIdx in 0 until NX) {
        val x = xIdx * deltaX
        matrix[0, xIdx] = sin(x)
    }

    for (tIdx in 0 until NT) {
        val t = tIdx * deltaT
        matrix[tIdx, 0] = 0.0
        matrix[tIdx, NX - 1] = exp(-t)
    }

    val courantValue = deltaT / deltaX / deltaX

    for (tIdx in 1 until NT) {
        for (xIdx in 1 until NX - 1) {
            var value = courantValue * matrix[tIdx - 1, xIdx + 1]
            value += (1 - 2 * courantValue) * matrix[tIdx - 1, xIdx]
            value += courantValue * matrix[tIdx - 1, xIdx - 1]
            matrix[tIdx, xIdx] = value
        }
    }

    val xValues = mk.linspace<Double>(0.0, PI / 2, NX).toList()

    val answerU = mk.d2arrayIndices(NT, NX) { tIdx, xIdx -> exp(-tIdx*deltaT)*sin(xIdx*deltaX) }

    val data = mutableMapOf(
        "originalX" to xValues,
    )

    for (tIdx in 0 until NT step NT / drawLineCount) {
        data["$tIdx"] = matrix[tIdx].toList()
        data["ans$tIdx"] = answerU[tIdx].toList()
    }

    var p = letsPlot(data)

    for (tIdx in 0 until NT step NT / drawLineCount) {
        p += geomLine(color = "black", linetype = "dotted", size = 2) { x = "originalX"; y = "ans$tIdx"; }

        p += geomLine(color = getColor(tIdx, NT)) { x = "originalX"; y = "$tIdx"; }
    }

    p += ggsize(500, 250)

    p.show()

    var maxError = 0.0
    var maxErrorT = 0.0
    for (tIdx in 0 until NT) {
        val curError: Double = matrix[tIdx].toList()
            .zip(answerU[tIdx].toList())
            .map{ pair -> abs(pair.first - pair.second) }
            .maxOf { it }
        if (maxError < curError) {
            maxError = curError
            maxErrorT = tIdx*deltaT
        }
    }

    println("Max inaccurance: $maxError")
    println("Max inaccurance time: $maxErrorT")

}

fun getColor(tIdx: Int, tMax: Int): String {
    var r = Integer.toHexString((1.0 * tIdx / tMax * 256).toInt() )
    var g = Integer.toHexString((255.0 - 1.0 * tIdx / tMax * 256).toInt() )
    if (r.length == 1)
        r = "0$r"
    if (g.length == 1)
        g = "0$g"
    return "#${r}${g}00"
}