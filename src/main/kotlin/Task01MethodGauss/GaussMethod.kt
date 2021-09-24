import org.jetbrains.kotlinx.multik.ndarray.data.D1Array
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.set

fun solveSystemOfLinearEquationsGauss(inputMatrix: D2Array<Double>, inputVector: D1Array<Double>): D1Array<Double> {
    val size = inputMatrix.shape[0]
    val matrix = inputMatrix.deepCopy()
    val vector = inputVector.deepCopy()
    for (selectedRowIndex in 0 until size) {
        if (matrix[selectedRowIndex, selectedRowIndex] == 0.0)
            continue
        for (targetRowIndex in selectedRowIndex + 1 until size) {
            val targetRowMultiplier: Double =
                matrix[targetRowIndex, selectedRowIndex] / matrix[selectedRowIndex, selectedRowIndex]

            vector[targetRowIndex] -= targetRowMultiplier * vector[selectedRowIndex]

            for (targetColumnIndex in selectedRowIndex until size) {
                matrix[targetRowIndex, targetColumnIndex] -= targetRowMultiplier * matrix[selectedRowIndex, targetColumnIndex]
            }
        }
    }

    for (selectedRowIndex in size - 1 downTo 0) {
        if (matrix[selectedRowIndex, selectedRowIndex] == 0.0)
            continue
        for (targetRowIndex in selectedRowIndex - 1 downTo 0) {
            val targetRowMultiplier =
                matrix[targetRowIndex, selectedRowIndex] / matrix[selectedRowIndex, selectedRowIndex]
            vector[targetRowIndex] -= targetRowMultiplier * vector[selectedRowIndex]
            matrix[targetRowIndex, selectedRowIndex] -= targetRowMultiplier * matrix[selectedRowIndex, selectedRowIndex]
        }
    }

    for (selectedRowIndex in 0 until size) {
        vector[selectedRowIndex] /= matrix[selectedRowIndex, selectedRowIndex]
    }

    return vector
}


