locals {
  source_object = "${path.module}/../../../target/plain-lambda-1.0-SNAPSHOT.jar"
  source_hash = filemd5(local.source_object)
}

module "s3-bucket" {
  source  = "terraform-aws-modules/s3-bucket/aws"
  version = "4.5.0"

  bucket_prefix = local.full_name
  force_destroy = true
}

resource "aws_s3_object" "code" {
  bucket = module.s3-bucket.s3_bucket_id
  key    = "${local.source_hash}.zip"
  source = local.source_object
}