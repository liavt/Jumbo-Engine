package test.jumbo;

import java.awt.Dimension;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.lwjgl.opengl.Display;

import com.jumbo.core.Jumbo;
import com.jumbo.core.JumboLaunchConfig;

import test.jumbo.components.JumboComponentsSuite;
import test.jumbo.core.JumboCoreSuite;
import test.jumbo.tools.calculations.JumboCalculationsSuite;

public class JumboTestingRunner {
	public static void main(String... args) {
		Jumbo.start(new JumboLaunchConfig("Testing...", new Dimension(500, 500)));
		Jumbo.setLaunchAction(() -> {
			while (!Display.isCreated()) {
			}
			Result result = JUnitCore.runClasses(JumboComponentsSuite.class, JumboCalculationsSuite.class,
					JumboCoreSuite.class);
			for (Failure failure : result.getFailures()) {
				System.err.println(
						"Failed: " + failure.getTestHeader() + ": " + failure.getException().getLocalizedMessage());
			}
			if (result.getIgnoreCount() > 0) {
				System.out.println("Ignored " + result.getIgnoreCount());
			}
			System.out.println("[" + result.getRunCount() + " tests @ " + result.getRunTime()
					+ "ms] Completed with result: " + result.wasSuccessful());
			Jumbo.stop();
		});

	}
}
