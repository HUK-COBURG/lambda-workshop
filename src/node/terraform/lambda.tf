module "lambda" {
  source  = "terraform-aws-modules/lambda/aws"
  version = "7.20.1"

  function_name = local.full_name
  runtime       = "nodejs22.x"

  handler = "index.handler"

  memory_size = 128

  create_package = false
  s3_existing_package = {
    bucket = module.s3-bucket.s3_bucket_id
    key    = aws_s3_object.code.key
  }

  environment_variables = {
    DYNAMODB_TABLE_ID : aws_dynamodb_table.table.id
  }

  create_current_version_allowed_triggers = false
  attach_cloudwatch_logs_policy           = true
  attach_tracing_policy                   = true
  tracing_mode                            = "Active"

  allowed_triggers = {
    APIGateway = {
      service    = "apigateway"
      source_arn = "${aws_api_gateway_deployment.deployment.execution_arn}*/*/*"
    },
  }

  attach_policies    = true
  number_of_policies = 1
  policies = [
    aws_iam_policy.allow_dynamodb_read_write.arn
  ]
}

data "aws_iam_policy_document" "allow_dynamodb_read_write" {
  statement {
    actions = ["dynamodb:GetItem", "dynamodb:PutItem", "dynamodb:UpdateItem", "dynamodb:DeleteItem"]
    resources = [aws_dynamodb_table.table.arn]
  }
}

resource "aws_iam_policy" "allow_dynamodb_read_write" {
  policy = data.aws_iam_policy_document.allow_dynamodb_read_write.json
}