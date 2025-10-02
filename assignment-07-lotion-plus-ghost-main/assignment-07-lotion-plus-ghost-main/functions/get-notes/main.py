import boto3
from boto3.dynamodb.conditions import Key
import json

dynamodb_resource = boto3.resource("dynamodb")
table = dynamodb_resource.Table("lotion-30152819")

def lambda_handler(event, context):
    email = event["queryStringParameters"]["email"]
    print(email)
    res = table.query(KeyConditionExpression=Key("email").eq(email))
    try:
        return {
            "statusCode": 200,
            "body" : json.dumps(res["Items"])
        }
    except Exception as exp:
        print(exp)
        return {
            "statusCode": 500,
            "body" : json.dumps(res["Items"])
        }