
package br.com.localstack.service;

        import br.com.localstack.model.Item;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;
        import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
        import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
        import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
        import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
        import software.amazon.awssdk.services.dynamodb.model.*;

        import java.util.Map;

@Service
public class ItemService {
    private final DynamoDbTable<Item> itemTable;
    private final DynamoDbClient dynamoDbClient;

    @Autowired
    public ItemService(DynamoDbEnhancedClient enhancedClient, DynamoDbClient dynamoDbClient) {
        this.itemTable = enhancedClient.table("YourTableName", TableSchema.fromClass(Item.class));
        this.dynamoDbClient = dynamoDbClient;
    }

    // Método para criar a tabela no DynamoDB
    public void createTable() {
        CreateTableRequest createTableRequest = CreateTableRequest.builder()
                .tableName("YourTableName")
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


    public void createItem(Item item) {
        PutItemRequest request = PutItemRequest.builder()
                .tableName("tebela")
                .item(convertToDynamoDbItem(item))
                .build();
        dynamoDbClient.putItem(request);
    }

    public Item getItemById(String id) {
        GetItemRequest request = GetItemRequest.builder()
                .tableName("YourTableName")
                .key(Map.of("id", AttributeValue.builder().s(id).build()))
                .build();
        GetItemResponse response = dynamoDbClient.getItem(request);
        if (response.item() != null) {
            return convertToItem(response.item());
        }
        return null;
    }

    public void updateItem(Item item) {
        PutItemRequest request = PutItemRequest.builder()
                .tableName("YourTableName")
                .item(convertToDynamoDbItem(item))
                .build();
        dynamoDbClient.putItem(request);
    }

    public void deleteItemById(String id) {
        DeleteItemRequest request = DeleteItemRequest.builder()
                .tableName("YourTableName")
                .key(Map.of("id", AttributeValue.builder().s(id).build()))
                .build();
        dynamoDbClient.deleteItem(request);
    }

    private Item convertToItem(Map<String, AttributeValue> item) {
        Item result = new Item();
        result.setId(item.get("id").s());
        result.setName(item.get("name").s());
        // Configure outros campos conforme necessário
        return result;
    }

    private Map<String, AttributeValue> convertToDynamoDbItem(Item item) {
        return Map.of(
                "id", AttributeValue.builder().s(item.getId()).build(),
                "name", AttributeValue.builder().s(item.getName()).build()
                // Configure outros campos conforme necessário
        );
    }
}

