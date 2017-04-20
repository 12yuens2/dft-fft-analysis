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
	
	public static Complex generateRandomComplex(double min, double max) {
		double real = generateRandomDouble(min, max);
		double imag = generateRandomDouble(min, max);
		
		return new Complex(real, imag);
	}
	
	@Param({"1"})
	public int arg;
	
	public Complex[] cs;
	
	@Setup(Level.Iteration)
	public void prepare() {
		cs = new Complex[arg];
		
		for(int j = 0; j < arg; j++) {
			cs[j] = generateRandomComplex(-Double.MIN_VALUE, Double.MAX_VALUE);
		}
	}
	
//	@Benchmark
//	public Complex[] dft() {
//		return DFT.dft(cs);
//	}
	
	@Benchmark
	public Complex[] fftButterfly() {
		return FFT.fftButterfly(cs);
	}
	
	@Benchmark
	public Complex[] fft() {
		return FFT.fft(cs);
	}
}
