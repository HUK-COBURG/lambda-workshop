import {DynamoDBClient} from "@aws-sdk/client-dynamodb";
import {DynamoDBDocumentClient, GetCommand, PutCommand} from "@aws-sdk/lib-dynamodb";

const client = new DynamoDBClient({});
const docClient = DynamoDBDocumentClient.from(client);
const tableName = process.env.DYNAMODB_TABLE_ID

// tag::handler[]
export const handler = async (event, context) => {
    console.log("EVENT: \n" + JSON.stringify(event, null, 2));
    switch (event.httpMethod) {
        case "GET":
            return getPet(parseInt(event.pathParameters["petId"]))
        case "POST":
            return createPet(JSON.parse(event.body))
        default:
            return {
                statusCode: 405,
                body: JSON.stringify({ message: "Method not allowed" }),
            };
    }
};
// end::handler[]

// tag::get_pet[]
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
// end::get_pet[]

// tag::create_pet[]
async function createPet(pet) {
    const params = {
        TableName: tableName,
        Item: pet,
    };
    console.log("creating pet with " + JSON.stringify(params))

    await docClient.send(new PutCommand(params));

    return {
        statusCode: 201,
        body: JSON.stringify(pet),
    }
}
// end::create_pet[]
