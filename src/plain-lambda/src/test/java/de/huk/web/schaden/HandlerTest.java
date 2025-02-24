/*
 * 12.02.2025 ap4768
 * Copyright (c) 2025 HUK-COBURG. All Rights Reserved.
 */
package de.huk.web.schaden;

import com.amazonaws.services.lambda.runtime.ClientContext;
import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HandlerTest {

    @Disabled
    @Test
    void handleRequest() {
        // arrange
        Handler handler = new Handler();
        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();
        Context context = new Context() {
            @Override
            public String getAwsRequestId() {
                return "";
            }

            @Override
            public String getLogGroupName() {
                return "";
            }

            @Override
            public String getLogStreamName() {
                return "";
            }

            @Override
            public String getFunctionName() {
                return "";
            }

            @Override
            public String getFunctionVersion() {
                return "";
            }

            @Override
            public String getInvokedFunctionArn() {
                return "";
            }

            @Override
            public CognitoIdentity getIdentity() {
                return null;
            }

            @Override
            public ClientContext getClientContext() {
                return null;
            }

            @Override
            public int getRemainingTimeInMillis() {
                return 0;
            }

            @Override
            public int getMemoryLimitInMB() {
                return 0;
            }

            @Override
            public LambdaLogger getLogger() {
                return new LambdaLogger() {
                    @Override
                    public void log(String s) {
                        System.out.println(s);
                    }

                    @Override
                    public void log(byte[] bytes) {
                        System.out.println(Arrays.toString(bytes));
                    }
                };
            }
        };
        // act
        var result = handler.handleRequest(request, context);
        // assert
        assertEquals(204, result.getStatusCode());
    }
}