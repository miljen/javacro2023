package com.myorg;

import software.amazon.awscdk.RemovalPolicy;
import software.amazon.awscdk.services.apigateway.LambdaRestApi;
import software.amazon.awscdk.services.dynamodb.Attribute;
import software.amazon.awscdk.services.dynamodb.AttributeType;
import software.amazon.awscdk.services.dynamodb.Table;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.Runtime;
import software.constructs.Construct;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;

import java.util.HashMap;
import java.util.Map;

public class DemoStack extends Stack {
    public DemoStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public DemoStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        final Table table = Table.Builder.create(this, "DDBTable")
                .partitionKey(Attribute.builder()
                        .name("id")
                        .type(AttributeType.NUMBER)
                        .build())
                .removalPolicy(RemovalPolicy.DESTROY)
                .build();

        final Map<String, String> environment = new HashMap<>();
        environment.put("DDB_TABLE_NAME", table.getTableName());

        final Function lambda = Function.Builder.create(this, "GetAllHandler")
                .runtime(Runtime.NODEJS_16_X)
                .code(Code.fromAsset("lambda"))
                .handler("getAll.handler")
                .environment(environment)
                .build();

        table.grantReadData(lambda);

        LambdaRestApi.Builder.create(this, "API")
                .handler(lambda)
                .build();
    }
}
