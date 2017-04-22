package benchmarks;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.*;

import fourier.Complex;
import fourier.DFT;
import fourier.FFT;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(value=2)
public class Test {
	
	public static double generateRandomDouble(double min, double max) {
		Random random = new Random();
		
		return min + (random.nextDouble() * (max - min));
	}
	
	/* Generate a random complex number */
	public static Complex generateRandomComplex(double min, double max) {
		double real = generateRandomDouble(min, max);
		double imag = generateRandomDouble(min, max);
		
		return new Complex(real, imag);
	}
	
	/* Parameter of each benchmark iteration */
	@Param({"1"})
	public int arg;
	
	public Complex[] cs;
	
	/* Setup new list of complex numbers at the beginning of each iteration */
	@Setup(Level.Iteration)
	public void prepare() {
		cs = new Complex[arg];
		
		for(int j = 0; j < arg; j++) {
			cs[j] = generateRandomComplex(-Double.MIN_VALUE, Double.MAX_VALUE);
		}
	}
	
	@Benchmark
	public Complex[] dft() {
		return DFT.dft(cs);
	}
	
	@Benchmark
	public Complex[] fftSmallOptimisation() {
		return FFT.fftSimpleOptimisation(cs);
	}
	
	@Benchmark
	public Complex[] fft() {
		return FFT.fft(cs);
	}
	
	@Benchmark
	public Complex[] fftInplace() {
		FFT.fftInplace(cs);
		return cs;
	}
}
