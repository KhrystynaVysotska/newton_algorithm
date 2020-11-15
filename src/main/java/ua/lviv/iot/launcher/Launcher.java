package main.java.ua.lviv.iot.launcher;

import java.util.Scanner;

import main.java.ua.lviv.iot.algorithm.NewtonAlgorithmForTwoArguments;

public class Launcher {

	public static void main(String[] args) {
		double[] primaryArgumentsGuess = new double[2];
		Scanner input = new Scanner(System.in);
		System.out.println("Enter first argument primary guess: ");
		primaryArgumentsGuess[0] = input.nextDouble();
		System.out.println("Enter second argument primary guess: ");
		primaryArgumentsGuess[1] = input.nextDouble();
		input.close();
		NewtonAlgorithmForTwoArguments algo = new NewtonAlgorithmForTwoArguments(primaryArgumentsGuess);
		double[] result = algo.findRoots();
		for (double x : result) {
			System.out.println(x);
		}
		double[] functionValues = algo.getFunctionsVector(result);
		System.out.println("\nFunctions' values:\n");
		for (double value : functionValues) {
			System.out.println(value);
		}
	}
}
