import { DynamoDBClient } from "@aws-sdk/client-dynamodb";
import { GetCommand, PutCommand , DynamoDBDocumentClient } from "@aws-sdk/lib-dynamodb";

const client = new DynamoDBClient({});
const docClient = DynamoDBDocumentClient.from(client);
const tableName = process.env.DYNAMODB_TABLE_ID

export const handler = async (event, context) => {
    console.log("EVENT: \n" + JSON.stringify(event, null, 2));
    switch (event.method) {
        case "GET":
            console.log("getting with event: " + JSON.stringify(event))
            return getPet(event.pathParameters["petId"])
        case "POST":
            return createPet(event.body)
        default:
            return {
                statusCode: 405,
                body: JSON.stringify({ message: "Method not allowed" }),
            };
    }
};

async function getPet(id) {
    const command = new GetCommand({
        TableName: tableName,
        Key: {
            id: id,
        },
    })

   let result = await docClient.send(command);

    if(result && result.Item) {
        return {
            statusCode: 200,
            body: JSON.stringify(result.Item),
        }
    } else {
        return {
            statusCode: 404,
            body: JSON.stringify({ message: "Pet not found" }),
        }
    }
}

async function createPet(pet) {
    const params = {
        TableName: tableName,
        Item: pet,
    };

    await docClient.send(new PutCommand(params));

    return {
        statusCode: 201,
        body: JSON.stringify(pet),
    }
}
