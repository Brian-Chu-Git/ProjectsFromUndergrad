import boto3
import json

dynamodb_resource = boto3.resource("dynamodb")
table = dynamodb_resource.Table("lotion-30152819")

def lambda_handler(event, context):
    body = json.loads(event["body"])
    try:
        table.delete_item(
            Key={
                "email": body['email'],
                "id": body['id']
            }
        )
        return {
            "statusCode": 201, 
            "body": "success"

        }
    except Exception as exp:
        print(exp)
        return {
            "statusCode": 500, 
            "body": json.dumps(
                {
                    "message": str(exp)
                }
            )
        }