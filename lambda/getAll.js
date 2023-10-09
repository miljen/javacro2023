const AWS = require('aws-sdk');
const dynamodb = new AWS.DynamoDB.DocumentClient();

exports.handler = async (event) => {
    const tableName = process.env.DDB_TABLE_NAME;
    const httpMethod = event.httpMethod;

    if (httpMethod === 'GET') {
        return await getAllItems(tableName);
    } else {
        return {
            statusCode: 400,
            body: JSON.stringify({ message: 'Invalid HTTP method' }),
        };
    }
};

async function getAllItems(tableName) {
    const params = {
        TableName: tableName,
    };

    const data = await dynamodb.scan(params).promise();

    return {
        statusCode: 200,
        body: JSON.stringify(data.Items),
    };
}
