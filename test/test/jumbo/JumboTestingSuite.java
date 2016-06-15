package test.jumbo;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import test.jumbo.components.JumboComponentsSuite;
import test.jumbo.core.JumboCoreSuite;
import test.jumbo.tools.calculations.JumboCalculationsSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		JumboComponentsSuite.class, JumboCalculationsSuite.class, JumboCoreSuite.class
})
public class JumboTestingSuite {

}
