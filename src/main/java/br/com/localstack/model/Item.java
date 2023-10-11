package br.com.localstack.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDbBean
public class Item {

    private String id;
    private String name;

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }

    // Mapear o campo 'name' como um atributo na tabela DynamoDB
    @DynamoDbAttribute("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
