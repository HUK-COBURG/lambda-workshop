package de.huk.web.schaden.springbootlambda;

import com.amazonaws.serverless.proxy.model.ApiGatewayRequestIdentity;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyRequestContext;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.model.Headers;
import com.amazonaws.services.lambda.runtime.Context;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

//@SpringBootTest
class SpringBootLambdaApplicationTests {

    @Disabled
    @Test
    void contextLoads() {
    }

    @Disabled
    @Test
    void whenTheUsersPathIsInvokedViaLambda_thenShouldReturnAList() throws IOException {
        Context context = null;
        Handler lambdaHandler = new Handler();
        AwsProxyRequest req = new AwsProxyRequest();
        req.setPath("/pet/1234");
        req.setHttpMethod("GET");
        Headers headers = new Headers();
        headers.put("Content-Type", List.of("application/json"));
//        req.setMultiValueHeaders(headers);
        AwsProxyRequestContext requestContext = new AwsProxyRequestContext();
        requestContext.setIdentity(new ApiGatewayRequestIdentity());

        req.setRequestContext(requestContext);

        AwsProxyResponse resp = lambdaHandler.handleRequest(req, null);
        Assertions.assertNotNull(resp.getBody());
        Assertions.assertEquals(200, resp.getStatusCode());
    }

}
