import task02methodgauss.calcVectorDiff
import task02methodgauss.generateSystemOfLinearEquations
import task02methodgauss.solveSystemOfLinearEquationsGauss

fun main() {
    val generatedAXB = generateSystemOfLinearEquations(10000)
    val calculatedX = solveSystemOfLinearEquationsGauss(generatedAXB.first, generatedAXB.third)
    println("Max inaccuracy:")
    println(calcVectorDiff(calculatedX, generatedAXB.second))
}