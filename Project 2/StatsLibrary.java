import java.util.*;
import java.lang.Math;
import java.math.BigInteger;
/**
 *
 */
public class StatsLibrary {
	public void StatsLibrary() {
		
	}

	public BigInteger factorial(int x) {
		BigInteger result = BigInteger.valueOf(x);
		
		for (int i = 1; i < x; i++)
			result = result.multiply(BigInteger.valueOf(i));
		
		return result;
	}

	public BigInteger combination(int n, int r) {
		BigInteger result;
		
		result = (factorial(n)).divide((factorial(r).multiply(factorial(n - r))));
		
		return result;
	}
	

	public double hypergeometricDistribution(int n, int m, int r, int y) {
		double probability;

		probability = (combination(r, y).multiply(combination(n - r, m - y))).doubleValue() / (combination(n, m).doubleValue());
		
		return probability;
	}
	

	public double hypergeometricExpected(int n, int r, int m) {
		double expected;
		//Store n as a double
		double population = n;
		
		expected = (m*r) / population;
		
		return expected;
	}
	

	public double hypergeometricVariance(int n, int r, int m) {
		double variance;
		double population = n;
	
		variance = (m * (r / population) * ((population-r) / population) * ((population - m) / (population - 1)));
		
		return variance;
	}
	
	
	public double hypergeometricStandardDeviation(int n, int r, int m) {
		double standardDeviation = Math.sqrt(hypergeometricVariance(n, r, m));
		
		return standardDeviation;
	}
	

	public double poissonDistribution(int lambda, int y) {
		double probability;
		double negativeLambda = lambda * -1;
				probability = (Math.pow(lambda, y) / factorial(y).doubleValue()) * (Math.exp(negativeLambda));
		
		return probability;
	}
	

	public int poissonExpectedAndVariance(int lambda) {
		return lambda;
	}
	

	public double poissonStandardDeviation(int lamda) {
		double standardDeviation = Math.sqrt(lamda);
		
		return standardDeviation;
	}
	

	public double chebyshev(double lowerRange, double upperRange, double mean, double standardDeviation) {
		double percentage;
		double k = (upperRange - mean) / standardDeviation;
		
		percentage = 1 - (1 / (k*k));
		
		return percentage;
	}
	

	public double chebyshevGivenVariance(double lowerRange, double upperRange, double mean, double variance) {
		double percentage;
		double standardDeviation = Math.sqrt(variance);
		double k = (upperRange - mean) / standardDeviation;
				percentage = 1 - (1 / (k*k));
		
		return percentage;
	}
	

	public double uniformDistribution(int a, int b, int c, int d) {
		double probability;
		double intervalUpperBound = d;
	
		probability = (b - a) / (intervalUpperBound - c);
		
		return probability;
	}
	
	public double uniformExpectedValue(int c, int d) {
		double expected;
		double intervalUpperBound = d;
		
		expected = (intervalUpperBound + c) / 2;
		
		return expected;
	}
	

	public double uniformVariance(int c, int d) {
		double variance;
		double intervalUpperBound = d;
		
		variance = ((intervalUpperBound - c)*(intervalUpperBound - c)) / 12;
		
		return variance;
	}
	

	public double uniformStandardDeviation(int c, int d) {
		double standardDeviation = Math.sqrt(uniformVariance(c, d));
		
		return standardDeviation;
	}
}
