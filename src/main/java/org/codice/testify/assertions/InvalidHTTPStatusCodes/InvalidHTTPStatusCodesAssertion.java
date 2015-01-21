package org.codice.testify.assertions.InvalidHTTPStatusCodes;

import org.codice.testify.assertions.Assertion;
import org.codice.testify.objects.AssertionStatus;
import org.codice.testify.objects.TestifyLogger;
import org.codice.testify.objects.Response;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * The InvalidHTTPStatusCodesAssertion class is a Testify Assertion service that checks that the response code is not one a set of comma separated status codes
 * @author Yakov Salzberg
 */
public class InvalidHTTPStatusCodesAssertion implements BundleActivator, Assertion {

    @Override
    public AssertionStatus evaluateAssertion(String assertionInfo, Response response) {

        TestifyLogger.debug("Running InvalidHTTPStatusCodesAssertion", this.getClass().getSimpleName());

        //Get the processor response code
        int responseCode = response.getResponseCode();

        //If no assertion info is provided, return a failure
        if (assertionInfo == null) {
            return new AssertionStatus("No HTTP status codes provided in test file");

        //If the response code from the processor is not set, return a failure
        } else if (responseCode == -1) {
            return new AssertionStatus("No response code set by processor");

        } else {

            //Split the assertionInfo by comma into an array of codes
            String[] codeArray = assertionInfo.split(",");

            //Loop through each code in the list of valid codes
            for (String code : codeArray) {

                //If the response code matches a invalid code, return a failure
                if (responseCode == Integer.parseInt(code.trim())) {
                    return new AssertionStatus("Response code " + responseCode + " is listed as an invalid code");
                }
            }

            //If the response code is not in the list of invalid codes, return failure details of null meaning a successful assertion
            return new AssertionStatus(null);
        }
    }

    @Override
    public void start(BundleContext bundleContext) throws Exception {

        //Register the InvalidHTTPStatusCodes service
        bundleContext.registerService(Assertion.class.getName(), new InvalidHTTPStatusCodesAssertion(), null);

    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {

    }
}