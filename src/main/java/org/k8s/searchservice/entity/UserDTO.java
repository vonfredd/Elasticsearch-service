package org.k8s.searchservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import static org.springframework.data.elasticsearch.annotations.FieldType.Search_As_You_Type;

@Document(indexName = "user_index")
public record UserDTO(@Field(type = Search_As_You_Type) String name, @Id String userID) {
}
//Using same data as UserService-repo in chatgut