import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.openjdk.jmh.Main;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import benchmarks.Test;

public class App {
	
	public static void main(String[] args) throws RunnerException, UnknownHostException {

		int max = 10;
		String[] inputs = new String[max];
		for (int i = 0; i < max; i++) {
			inputs[i] = String.valueOf((int)Math.pow(2, i));
		}
		
		Options opts = new OptionsBuilder()
				.include(Test.class.getSimpleName())
				.warmupIterations(10)
				.warmupTime(TimeValue.seconds(1))
				.measurementIterations(10)
				.measurementTime(TimeValue.seconds(1))
				.forks(2)
				.param("arg", inputs)
				.result("results-" + InetAddress.getLocalHost().getHostName() + "3.csv")
				.build();
		
		new Runner(opts).run();
	}
}
