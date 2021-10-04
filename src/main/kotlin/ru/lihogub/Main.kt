import ru.lihogub.task02methodgauss.calcVectorDiff
import ru.lihogub.common.generateSystemOfLinearEquations
import ru.lihogub.task02methodgauss.solveSystemOfLinearEquationsGauss

fun main() {
    val generatedAXB = generateSystemOfLinearEquations(10000)
    val calculatedX = solveSystemOfLinearEquationsGauss(generatedAXB.first, generatedAXB.third)
    println("Max inaccuracy:")
    println(calcVectorDiff(calculatedX, generatedAXB.second))
}