provider "aws" {
  region = "eu-central-1"
}

resource "random_pet" "pet" {}

locals {
  full_name = "${random_pet.pet.id}-nodejs"
}