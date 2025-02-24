locals {
  source_dir    = "${path.module}/../src"
  source_object = "${local.source_dir}/index.mjs"
  source_hash = filemd5(local.source_object)
}

data "archive_file" "source" {
  type        = "zip"
  source_file = local.source_object
  output_path = "${local.source_dir}/build/dist.zip"
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
  source = data.archive_file.source.output_path
}