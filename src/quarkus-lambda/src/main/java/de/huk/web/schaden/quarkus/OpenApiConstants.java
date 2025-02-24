/*
 * 12.05.2022 ap102
 * Copyright (c) 2022 HUK-COBURG. All Rights Reserved.
 *
 */

package de.huk.web.schaden.quarkus;

public class OpenApiConstants {
    public static final String AMAZON_GATEWAY_INTEGRATION_CONTENTS = """
            {
              "uri": "${invoke_arn}",
              "responses": {
                "default": {
                  "statusCode": "200"
                }
              },
              "passthroughBehavior": "when_no_match",
              "httpMethod": "POST",
              "contentHandling": "CONVERT_TO_TEXT",
              "type": "aws_proxy"
            }
            """;
    public static final String X_AMAZON_APIGATEWAY_INTEGRATION = "x-amazon-apigateway-integration";

    private OpenApiConstants() {
        // private constructor to prevent instantiation
    }
}
