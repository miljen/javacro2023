package com.myorg;

import software.amazon.awscdk.App;
import software.amazon.awscdk.assertions.Template;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class DemoTest {

    @Test
    public void testStack() throws IOException {
        App app = new App();
        DemoStack stack = new DemoStack(app, "test");

        Template template = Template.fromStack(stack);

        template.resourceCountIs("AWS::DynamoDB::Table", 1);
        template.resourceCountIs("AWS::Lambda::Function", 1);
        template.resourceCountIs("AWS::ApiGateway::RestApi", 1);
    }
}
