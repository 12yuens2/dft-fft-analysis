import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import benchmarks.Test;

public class App {
	
	public static void main(String[] args) throws RunnerException {

		/* Create list of inputs for the benchmarks */
		int max = 11;
		String[] inputs = new String[max];
		for (int i = 0; i < max; i++) {
			inputs[i] = String.valueOf((int)Math.pow(2, i));
		}
		
		/* Options for the benchmarks */
		Options opts = new OptionsBuilder()
				.include(Test.class.getSimpleName())
				.warmupIterations(15)
				.warmupTime(TimeValue.seconds(1))
				.measurementIterations(20)
				.measurementTime(TimeValue.seconds(1))
				.forks(10)
				.param("arg", inputs)
				.result("results.csv")
				.build();
		
		new Runner(opts).run();
	}
}
