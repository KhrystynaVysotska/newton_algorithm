package main.java.ua.lviv.iot.launcher;

import main.java.ua.lviv.iot.algorithm.NewtonAlgorithmForTwoArguments;

public class Launcher {

	public static void main(String[] args) {
		double[] input = {1.0,1.0};
		NewtonAlgorithmForTwoArguments algo = new NewtonAlgorithmForTwoArguments(input);
		double[] result = algo.findRoots();
		for(double x:result) {
			System.out.println(x);
		}
		double[] functionValues = algo.getFunctionsVector(result);
		for(double value:functionValues) {
			System.out.println(value);
		}
	}
}
