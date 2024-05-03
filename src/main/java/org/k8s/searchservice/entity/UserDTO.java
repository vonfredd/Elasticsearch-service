package org.k8s.searchservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import static org.springframework.data.elasticsearch.annotations.FieldType.Text;

@Document(indexName = "user_index")
public record UserDTO(@Field(type = Text) String name, String imageLink, @Id String userID) {
}
//Using same data as UserService-repo in chatgut