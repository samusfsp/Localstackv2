package br.com.localstack.service;

import br.com.localstack.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.Map;

@Service
public class ItemService {
    private final DynamoDbClient dynamoDbClient;

    @Autowired
    public ItemService(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    // Método para criar a tabela no DynamoDB (opcional, se a tabela já existir)
    public void createTable(String tableName) {
        CreateTableRequest createTableRequest = CreateTableRequest.builder()
                .tableName(tableName)
                .keySchema(
                        KeySchemaElement.builder().attributeName("id").keyType(KeyType.HASH).build()
                )
                .attributeDefinitions(
                        AttributeDefinition.builder().attributeName("id").attributeType(ScalarAttributeType.S).build()
                )
                .provisionedThroughput(
                        ProvisionedThroughput.builder().readCapacityUnits(5L).writeCapacityUnits(5L).build()
                )
                .build();

        dynamoDbClient.createTable(createTableRequest);
    }

    public void createItem(String tableName, Item item) {
        PutItemRequest request = PutItemRequest.builder()
                .tableName(tableName)
                .item(convertToDynamoDbItem(item))
                .build();
        dynamoDbClient.putItem(request);
    }

    public Item getItemById(String tableName, String id) {
        GetItemRequest request = GetItemRequest.builder()
                .tableName(tableName)
                .key(Map.of("ID", AttributeValue.builder().s(id).build()))
                .build();
        GetItemResponse response = dynamoDbClient.getItem(request);
        if (response.item() != null) {
            return convertToItem(response.item());
        }
        return null;
    }

    public void updateItem(String tableName, Item item) {
        PutItemRequest request = PutItemRequest.builder()
                .tableName(tableName)
                .item(convertToDynamoDbItem(item))
                .build();
        dynamoDbClient.putItem(request);
    }

    public void deleteItemById(String tableName, String id) {
        DeleteItemRequest request = DeleteItemRequest.builder()
                .tableName(tableName)
                .key(Map.of("ID", AttributeValue.builder().s(id).build()))
                .build();
        dynamoDbClient.deleteItem(request);
    }

    private Item convertToItem(Map<String, AttributeValue> item) {
        Item result = new Item();
        result.setId(item.get("ID").s());
        result.setName(item.get("name").s());
        // Configure outros campos conforme necessário
        return result;
    }

    private Map<String, AttributeValue> convertToDynamoDbItem(Item item) {
        return Map.of(
                "ID", AttributeValue.builder().s(item.getId()).build(),
                "name", AttributeValue.builder().s(item.getName()).build()
                // Configure outros campos conforme necessário
        );
    }
}
