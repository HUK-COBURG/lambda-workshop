resource "aws_api_gateway_rest_api" "api" {
  name = local.full_name

  body = templatefile("${path.module}/../../../target/openapi.yaml", {
    invoke_arn : module.lambda.lambda_function_invoke_arn
  })
}

resource "aws_api_gateway_deployment" "deployment" {
  rest_api_id = aws_api_gateway_rest_api.api.id

  triggers = {
    redeployment = sha1(jsonencode(aws_api_gateway_rest_api.api.body))
  }

  lifecycle {
    create_before_destroy = true
  }
}

resource "aws_api_gateway_stage" "stage" {
  deployment_id = aws_api_gateway_deployment.deployment.id
  rest_api_id   = aws_api_gateway_rest_api.api.id
  stage_name    = "default"
}