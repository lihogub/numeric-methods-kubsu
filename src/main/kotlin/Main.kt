fun main() {
    val generatedAXB = generateSystemOfLinearEquations(3)
    val calculatedX = solveSystemOfLinearEquationsGauss(generatedAXB.first, generatedAXB.third)
    println(calcVectorDiff(calculatedX, generatedAXB.second))
}