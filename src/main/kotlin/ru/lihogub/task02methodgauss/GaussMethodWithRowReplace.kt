package ru.lihogub.task02methodgauss

import org.jetbrains.kotlinx.multik.ndarray.data.D1Array
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.set

fun solveSystemOfLinearEquationsGaussWithRowReplace(inputMatrix: D2Array<Double>, inputVector: D1Array<Double>): D1Array<Double> {
    val size = inputMatrix.shape[0]
    val matrix = inputMatrix.deepCopy()
    val vector = inputVector.deepCopy()
    for (selectedRowIndex in 0 until size) {

        var maxValue = matrix[selectedRowIndex, selectedRowIndex]
        var maxValueRowIndex = selectedRowIndex
        for (selectedSecondRowIndex in selectedRowIndex + 1 until size) {
            if (maxValue < matrix[selectedSecondRowIndex, selectedRowIndex]) {
                maxValue = matrix[selectedSecondRowIndex, selectedRowIndex]
                maxValueRowIndex = selectedSecondRowIndex
            }
        }

        swapRows(matrix, vector, selectedRowIndex, maxValueRowIndex)

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

fun swapRows(inputMatrix: D2Array<Double>, inputVector: D1Array<Double>, iRow: Int, jRow: Int) {
    val tmpRow = inputMatrix[iRow].deepCopy()
    inputMatrix[iRow] = inputMatrix[jRow]
    inputMatrix[jRow] = tmpRow
    val tmpItem = inputVector[iRow]
    inputVector[iRow] = inputVector[jRow]
    inputVector[jRow] = tmpItem
}