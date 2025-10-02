import boto3
import json

dynamodb_resource = boto3.resource("dynamodb")
table = dynamodb_resource.Table("lotion-30152819")

def lambda_handler(event, context):
    #post request
    #need to check the body of the request
    body = json.loads(event["body"])
    try:
        table.put_item(Item=body)
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