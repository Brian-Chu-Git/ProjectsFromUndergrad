terraform {
  required_providers {
    aws = {
      version = ">= 4.0.0"
      source = "hashicorp/aws"
    }
  }
}

# specify the provider region
provider "aws" {
  region = "ca-central-1"
}

locals {
  save_note_function   = "save-note-30150088"
  get_notes_function   = "get-notes-30150088"
  delete_note_function = "delete-note-30150088"
  save_note_handler    = "main.lambda_handler"
  delete_note_handler  = "main.lambda_handler"
  get_notes_handler    = "main.lambda_handler"
  save_note_artifact   = "../functions/save-note/artifact.zip"
  get_notes_artifact   = "../functions/get-notes/artifact.zip"
  delete_note_artifact = "../functions/delete-note/artifact.zip"
}

#Create an IAM role

#Create an IAM role
resource "aws_iam_role" "IAM-role-all" {
  name               = "IAM-role-lambda"
  assume_role_policy = <<EOF
  {
  "Version": "2012-10-17",
  "Statement": [
    {
      "Action": "sts:AssumeRole",
      "Principal": {
        "Service": "lambda.amazonaws.com"
      },
      "Effect": "Allow",
      "Sid": ""
    }
  ]
}
EOF
}

resource "aws_iam_policy" "logs_all" {
  name        = "lambda-log"
  description = "IAM policy for logging from a lambda"

  policy = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Action": [
        "logs:CreateLogGroup",
        "logs:CreateLogStream",
        "logs:PutLogEvents",
        "dynamodb:PutItem",
        "dynamodb:Query",
        "dynamodb:DeleteItem",
        "dynamodb:Get"
      ],
      "Resource": ["arn:aws:logs:*:*:*", "${aws_dynamodb_table.lotion-30152819.arn}"],
      "Effect": "Allow"
    }
  ]
}
EOF
}


#Attaching above policies
resource "aws_iam_role_policy_attachment" "lambda_logs" {
  role       = aws_iam_role.IAM-role-all.name
  policy_arn = aws_iam_policy.logs_all.arn
}

#Creating the archive files
data "archive_file" "archive_save_note" {
  type        = "zip"
  source_file = "../functions/save-note/main.py"
  output_path = local.save_note_artifact
}

data "archive_file" "archive_get_notes" {
  type        = "zip"
  source_file = "../functions/get-notes/main.py"
  output_path = local.get_notes_artifact
}

data "archive_file" "archive_delete_note" {
  type        = "zip"
  source_file = "../functions/delete-note/main.py"
  output_path = local.delete_note_artifact
}

#Creating the 3 lambda functions
resource "aws_lambda_function" "lambda-save-note" {
  role             = aws_iam_role.IAM-role-all.arn
  function_name    = local.save_note_function
  handler          = local.save_note_handler
  filename         = local.save_note_artifact
  source_code_hash = data.archive_file.archive_save_note.output_base64sha256
  runtime          = "python3.9"
}

resource "aws_lambda_function" "lambda-get-notes" {
  role             = aws_iam_role.IAM-role-all.arn
  function_name    = local.get_notes_function
  handler          = local.get_notes_handler
  filename         = local.get_notes_artifact
  source_code_hash = data.archive_file.archive_get_notes.output_base64sha256
  runtime          = "python3.9"
}

resource "aws_lambda_function" "lambda-delete-note" {
  role             = aws_iam_role.IAM-role-all.arn
  function_name    = local.delete_note_function
  handler          = local.delete_note_handler
  filename         = local.delete_note_artifact
  source_code_hash = data.archive_file.archive_delete_note.output_base64sha256
  runtime          = "python3.9"
}

#Creating lambda function URLs
resource "aws_lambda_function_url" "save_note_url" {
  function_name      = aws_lambda_function.lambda-save-note.function_name
  authorization_type = "NONE"
  cors {
    allow_credentials = true
    allow_origins     = ["*"]
    allow_methods     = ["POST"]
    allow_headers     = ["*"]
    expose_headers    = ["keep-alive", "date"]
  }
}

resource "aws_lambda_function_url" "get_note_url" {
  function_name      = aws_lambda_function.lambda-get-notes.function_name
  authorization_type = "NONE"

  cors {
    allow_credentials = true
    allow_origins     = ["*"]
    allow_methods     = ["GET"]
    allow_headers     = ["*"]
    expose_headers    = ["keep-alive", "date"]
  }
}

resource "aws_lambda_function_url" "delete_note_url" {
  function_name      = aws_lambda_function.lambda-delete-note.function_name
  authorization_type = "NONE"

  cors {
    allow_credentials = true
    allow_origins     = ["*"]
    allow_methods     = ["DELETE"]
    allow_headers     = ["*"]
    expose_headers    = ["keep-alive", "date"]
  }
}

# show the Function URL after creation
output "save_note_url" {
  value = aws_lambda_function_url.save_note_url.function_url
}

# show the Function URL after creation
output "get_notes_url" {
  value = aws_lambda_function_url.get_note_url.function_url
}

# show the Function URL after creation
output "delete_note_url" {
  value = aws_lambda_function_url.delete_note_url.function_url
}

# Dynamodb
resource "aws_dynamodb_table" "lotion-30152819" {
  name         = "lotion-30152819"
  billing_mode = "PROVISIONED"

  # up to 8KB read per second (eventually consistent)
  read_capacity = 1

  # up to 1KB per second
  write_capacity = 1

  hash_key = "email"
  range_key = "id"

  attribute {
    name = "email"
    type = "S"
  }

  attribute {
    name = "id"
    type = "S"
  }  

}