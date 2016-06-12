package test.jumbo;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import test.jumbo.components.JumboComponentsSuite;

public class JumboTestingRunner {
	public static void main(String... args) {
		Result result = JUnitCore.runClasses(JumboComponentsSuite.class);
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}
		System.out.println("[" + result.getRunCount() + " @ " + result.getRunTime() + "ms] Components = "
				+ result.wasSuccessful());
	}
}
