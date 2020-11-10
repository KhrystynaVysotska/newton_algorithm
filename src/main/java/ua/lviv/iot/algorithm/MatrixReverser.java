package main.java.ua.lviv.iot.algorithm;

public class MatrixReverser {

	public static double[][] reverseMatrix(double[][] matrixToReverse, int[][] identityMatrix) {
		int size = matrixToReverse.length;
		double[][] reversedMatrix = new double[size][size];
		double[] solution;
		for (int equationSystemCounter = 0; equationSystemCounter < size; equationSystemCounter++) {
			solution = findRootsForLinearEquationSystem(equationSystemCounter, matrixToReverse, identityMatrix);
			for (int i = 0; i < solution.length; i++) {
				reversedMatrix[i][equationSystemCounter] = solution[i];
			}
		}
		return reversedMatrix;
	}

	private static double[] findRootsForLinearEquationSystem(int equationSystemCounter, double[][] matrixToReverse,
			int[][] identityMatrix) {
		int size = matrixToReverse.length;
		double[][] coefficientMatrix = new double[size][size];
		double[] freeMembersArray = new double[size];
		int[] argumentPositions = new int[size];
		double[][] resultCoefficientMatrix = new double[size][size];
		double[] resultFreeMembersArray = new double[size];
		initializeSystem(freeMembersArray, identityMatrix, equationSystemCounter, argumentPositions, coefficientMatrix,
				matrixToReverse);
		directWay(resultFreeMembersArray, freeMembersArray, coefficientMatrix, resultCoefficientMatrix,
				argumentPositions, size);
		double[] solution = reverseWay(resultFreeMembersArray, resultCoefficientMatrix);
		putInOrder(solution, argumentPositions);
		return solution;
	}

	private static void putInOrder(double[] solution, int[] argumentPositions) {
		for (int i = 0; i < solution.length; i++) {
			if (argumentPositions[i] != i) {
				int z = argumentPositions[i];
				double value = solution[i];
				solution[i] = solution[z];
				solution[z] = value;
				argumentPositions[i] = argumentPositions[z];
				argumentPositions[z] = z;
			}
		}
	}

	private static void directWay(double[] resultFreeMembersArray, double[] freeMembersArray,
			double[][] coefficientMatrix, double[][] resultCoefficientMatrix, int[] argumentPositions, int size) {
		for (int row = 0; row < size; row++) {
			organizeMatrix(row, coefficientMatrix, freeMembersArray, argumentPositions, resultCoefficientMatrix, size);
			resultFreeMembersArray[row] = freeMembersArray[row] / coefficientMatrix[row][row];
			for (int nextRow = row + 1; nextRow < size; nextRow++) {
				freeMembersArray[nextRow] = freeMembersArray[nextRow]
						- coefficientMatrix[nextRow][row] * resultFreeMembersArray[row];
				for (int nextColumn = row + 1; nextColumn < size; nextColumn++) {
					resultCoefficientMatrix[row][nextColumn] = coefficientMatrix[row][nextColumn]
							/ coefficientMatrix[row][row];
					coefficientMatrix[nextRow][nextColumn] = coefficientMatrix[nextRow][nextColumn]
							- coefficientMatrix[nextRow][row] * resultCoefficientMatrix[row][nextColumn];
				}
			}
		}
	}

	private static double[] reverseWay(double[] resultFreeMembersArray, double[][] resultCoefficientMatrix) {
		int size = resultFreeMembersArray.length;
		double[] solution = new double[size];
		solution[size - 1] = resultFreeMembersArray[size - 1];
		for (int i = size - 2; i >= 0; i--) {
			double sum = 0.0;
			for (int j = i + 1; j < size; j++) {
				sum += resultCoefficientMatrix[i][j] * solution[j];
			}
			solution[i] = resultFreeMembersArray[i] - sum;
		}
		return solution;
	}

	private static void initializeSystem(double[] freeMembersArray, int[][] identityMatrix, int equationSystemCounter,
			int[] argumentPositions, double[][] coefficientMatrix, double[][] matrixToReverse) {
		int size = matrixToReverse.length;
		for (int row = 0; row < size; row++) {
			freeMembersArray[row] = identityMatrix[row][equationSystemCounter];
			argumentPositions[row] = row;
			for (int column = 0; column < size; column++) {
				coefficientMatrix[row][column] = matrixToReverse[row][column];
			}
		}
	}

	private static void organizeMatrix(int row, double[][] coefficientMatrix, double[] freeMembersArray,
			int[] argumentPositions, double[][] resultCoefficientMatrix, int size) {
		double maxCoefficient = coefficientMatrix[row][row];
		int maxRowIndex = row;
		int maxColumnIndex = row;
		for (int i = row; i < size; i++) {
			for (int j = row; j < size; j++) {
				if (maxCoefficient < Math.abs(coefficientMatrix[i][j])) {
					maxCoefficient = Math.abs(coefficientMatrix[i][j]);
					maxRowIndex = i;
					maxColumnIndex = j;
				}
			}
		}
		swapValuesInArray(freeMembersArray, row, maxRowIndex);
		for (int column = 0; column < size; column++) {
			swapValuesInMatrixByRow(coefficientMatrix, row, maxRowIndex, column);
		}
		swapArgumentPositions(argumentPositions, row, maxColumnIndex);
		for (int d = 0; d < size; d++) {
			if (d < row) {
				swapValuesInMatrixByColumn(resultCoefficientMatrix, d, row, maxColumnIndex);
			} else {
				swapValuesInMatrixByColumn(coefficientMatrix, d, row, maxColumnIndex);
			}
		}
	}

	private static void swapValuesInMatrixByColumn(double[][] matrix, int row, int firstColumn, int secondColumn) {
		double tempValue = matrix[row][firstColumn];
		matrix[row][firstColumn] = matrix[row][secondColumn];
		matrix[row][secondColumn] = tempValue;
	}

	private static void swapArgumentPositions(int[] argumentPositions, int row, int maxColumnIndex) {
		int tempValue = argumentPositions[row];
		argumentPositions[row] = argumentPositions[maxColumnIndex];
		argumentPositions[maxColumnIndex] = tempValue;
	}

	private static void swapValuesInArray(double[] array, int firstIndex, int secondIndex) {
		double tempValue = array[firstIndex];
		array[firstIndex] = array[secondIndex];
		array[secondIndex] = tempValue;

	}

	private static void swapValuesInMatrixByRow(double[][] matrix, int firstRow, int secondRow, int column) {
		double tempValue = matrix[firstRow][column];
		matrix[firstRow][column] = matrix[secondRow][column];
		matrix[secondRow][column] = tempValue;

	}
}
