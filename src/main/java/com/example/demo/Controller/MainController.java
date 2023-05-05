package com.example.demo.Controller;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class MainController {
    @GetMapping("/")
    public String getAllItems() {
           // set the access key ID and secret access key
        String accessKeyId = "";
        String secretAccessKey = "";
           
        // create a DynamoDB client with the access key ID and secret access key
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKeyId, secretAccessKey);
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
        .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("https://dynamodb.us-east-1.amazonaws.com", "us-east-1"))
        .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
        .build();

        // create a DynamoDB object and specify the table name
        DynamoDB dynamoDB = new DynamoDB(client);
        String tableName = "company";
        Table table = dynamoDB.getTable(tableName);

        // scan the table to read all items
        ArrayList<String> items_display = new ArrayList<>();
        
        for (Item item : table.scan()) {
            System.out.println(item.toJSON());
            items_display.add(item.toJSON());
        }
        //System.out.println(items_display.toString());
        return items_display.toString();
    }
}
