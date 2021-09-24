import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.ndarray
import org.jetbrains.kotlinx.multik.ndarray.data.D1Array
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import kotlin.random.Random

fun generateSystemOfLinearEquations(size: Int): Triple<D2Array<Double>, D1Array<Double>, D1Array<Double>> {
    val matrixARaw = Array(size * size) { Random.nextDouble(0.0, 10000.0) }
        .toDoubleArray()

    val matrixA = mk.ndarray(matrixARaw, size, size)

    val vectorXRaw = Array(size) { Random.nextDouble(0.0, 10000.0) }
        .toDoubleArray()

    val vectorX = mk.ndarray(vectorXRaw)

    val vectorB = mk.linalg.dot(matrixA, vectorX)

    return Triple(matrixA, vectorX, vectorB)
}