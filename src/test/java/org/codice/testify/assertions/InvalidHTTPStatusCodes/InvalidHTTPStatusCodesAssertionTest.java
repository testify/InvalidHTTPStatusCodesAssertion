package org.codice.testify.assertions.InvalidHTTPStatusCodes;

import org.codice.testify.objects.AssertionStatus;
import org.codice.testify.objects.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class InvalidHTTPStatusCodesAssertionTest {

    @Test
    public void testNoAssertionInfo() {
        Response response = new Response(null);
        response.setResponseCode(200);
        InvalidHTTPStatusCodesAssertion invalidHTTPStatusCodesAssertion = new InvalidHTTPStatusCodesAssertion();
        AssertionStatus assertionStatus = invalidHTTPStatusCodesAssertion.evaluateAssertion(null, response);
        assert ( assertionStatus.getFailureDetails().equals("No HTTP status codes provided in test file") );
    }

    @Test
    public void testNoResponseCode() {
        Response response = new Response(null);
        String invalidCodes = "500, 501, 502";
        InvalidHTTPStatusCodesAssertion invalidHTTPStatusCodesAssertion = new InvalidHTTPStatusCodesAssertion();
        AssertionStatus assertionStatus = invalidHTTPStatusCodesAssertion.evaluateAssertion(invalidCodes, response);
        assert ( assertionStatus.getFailureDetails().equals("No response code set by processor") );
    }

    @Test
    public void testValidStatusCode() {
        Response response = new Response(null);
        response.setResponseCode(200);
        String invalidCodes = "500, 501, 502";
        InvalidHTTPStatusCodesAssertion invalidHTTPStatusCodesAssertion = new InvalidHTTPStatusCodesAssertion();
        AssertionStatus assertionStatus = invalidHTTPStatusCodesAssertion.evaluateAssertion(invalidCodes, response);
        assert ( assertionStatus.isSuccess() );
    }

    @Test
    public void testInvalidStatusCode() {
        Response response = new Response(null);
        response.setResponseCode(500);
        String invalidCodes = "500, 501, 502";
        InvalidHTTPStatusCodesAssertion invalidHTTPStatusCodesAssertion = new InvalidHTTPStatusCodesAssertion();
        AssertionStatus assertionStatus = invalidHTTPStatusCodesAssertion.evaluateAssertion(invalidCodes, response);
        assert ( assertionStatus.getFailureDetails().equals("Response code 500 is listed as an invalid code") );
    }

}
