package org.k8s.searchservice.repository;
import org.k8s.searchservice.entity.UserDTO;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface SearchRepository extends ElasticsearchRepository<UserDTO, String> {
    @Query("{\"bool\": {\"must\": {\"multi_match\": {\"query\": \"?0\",\"type\": \"bool_prefix\",\"fields\": [\"name\", \"name._2gram\", \"name._3gram\"]}}}}")
    List<UserDTO> searchByName(String query);
}
