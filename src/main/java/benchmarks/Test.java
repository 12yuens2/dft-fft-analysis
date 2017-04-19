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

	@Param({"1", "2"})
	public int arg;
	
	Complex[] cs = new Complex[0];
	
	@Setup(Level.Iteration)
	public void prepare() {
		Random random = new Random();
		
		cs = new Complex[arg];
		for(int j = 0; j < arg; j++) {
			double real = -Double.MIN_VALUE + (random.nextDouble() * (Double.MAX_VALUE - Double.MAX_VALUE));
			double im = -Double.MIN_VALUE + (random.nextDouble() * (Double.MAX_VALUE - Double.MAX_VALUE));
			cs[j] = new Complex(real, im);
		}
		
	}
	
	@Benchmark
	public Complex[] dft() {
		return DFT.dft(cs);
	}
	
	@Benchmark
	public Complex[] fft() {
		return FFT.fft(cs);
	}
}
