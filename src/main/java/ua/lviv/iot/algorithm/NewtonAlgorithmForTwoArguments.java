package main.java.ua.lviv.iot.algorithm;

public class NewtonAlgorithmForTwoArguments {
	private static final double FAULT_IN_PERCENTS = 0.00001;
	private static final int NUMBER_OF_ARGUMENTS = 2;
	private static final int NUMBER_OF_STEPS_FOR_FINITE_DIFFERECE_APPROXIMATION = 1;
	private double[] argumentGuessVector;
	private double[] previousArgumentGuessVector = new double[NUMBER_OF_ARGUMENTS];

	public NewtonAlgorithmForTwoArguments(double[] argumentGuessVector) {
		this.argumentGuessVector = argumentGuessVector;
	}

	public double[] findRoots() {
		int[][] identityMatrix = buildIdentityMatrix();
		double[] functionVector;
		double[][] jacobian;
		double[][] reversedJacobian;
		do {
			functionVector = getFunctionsVector(argumentGuessVector);
			jacobian = buildJacobian(functionVector);
			reversedJacobian = MatrixReverser.reverseMatrix(jacobian, identityMatrix);
			for (int i = 0; i < NUMBER_OF_ARGUMENTS; i++) {
				double oldGuess = argumentGuessVector[i];
				previousArgumentGuessVector[i] = oldGuess;
				double increase = 0.0;
				for (int j = 0; j < NUMBER_OF_ARGUMENTS; j++) {
					increase += reversedJacobian[i][j] * functionVector[i];
				}
				argumentGuessVector[i] = previousArgumentGuessVector[i] - increase;
			}
		} while (!isTrueConditionOfConverhence());
		return argumentGuessVector;
	}

	private boolean isTrueConditionOfConverhence() {
		for (int i = 0; i < NUMBER_OF_ARGUMENTS; i++) {
			double condition = Math
					.abs((argumentGuessVector[i] - previousArgumentGuessVector[i]) / argumentGuessVector[i]) * 100;
			if (condition > FAULT_IN_PERCENTS) {
				return false;
			}
		}
		return true;
	}

	private double[][] buildJacobian(double[] functionVector) {
		double[] argumentsForApproximation = new double[NUMBER_OF_ARGUMENTS];
		double[][] jacobian = new double[NUMBER_OF_ARGUMENTS][NUMBER_OF_ARGUMENTS];
		for (int row = 0; row < NUMBER_OF_ARGUMENTS; row++) {
			for (int column = 0; column < NUMBER_OF_ARGUMENTS; column++) {
				for (int argumentCounter = 0; argumentCounter < NUMBER_OF_ARGUMENTS; argumentCounter++) {
					argumentsForApproximation[argumentCounter] = argumentGuessVector[argumentCounter];
				}
				argumentsForApproximation[column] = argumentGuessVector[column]
						+ NUMBER_OF_STEPS_FOR_FINITE_DIFFERECE_APPROXIMATION;
				double[] approximatedFunctionVector = getFunctionsVector(argumentsForApproximation);
				jacobian[row][column] = (approximatedFunctionVector[row] - functionVector[row])
						/ NUMBER_OF_STEPS_FOR_FINITE_DIFFERECE_APPROXIMATION;
			}
		}
		return jacobian;
	}

	public double[] getFunctionsVector(double[] argumentGuessVector) {
		double[] functionsVector = new double[NUMBER_OF_ARGUMENTS];
		double firstArgument = argumentGuessVector[0];
		double secondArgument = argumentGuessVector[1];
		double firstFunctionResult = firstArgument * firstArgument - secondArgument * secondArgument - 0.1
				- firstArgument;
		double secondFunctionResult = 2 * firstArgument * secondArgument + 0.1 - secondArgument;
		functionsVector[0] = firstFunctionResult;
		functionsVector[1] = secondFunctionResult;
		return functionsVector;
	}

	private int[][] buildIdentityMatrix() {
		int[][] identityMatrix = new int[NUMBER_OF_ARGUMENTS][NUMBER_OF_ARGUMENTS];
		for (int row = 0; row < NUMBER_OF_ARGUMENTS; row++) {
			for (int column = 0; column < NUMBER_OF_ARGUMENTS; column++) {
				if (row == column) {
					identityMatrix[row][column] = 1;
				} else {
					identityMatrix[row][column] = 0;
				}
			}
		}
		return identityMatrix;
	}
}
