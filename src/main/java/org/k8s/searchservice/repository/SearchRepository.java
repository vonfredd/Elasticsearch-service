package org.k8s.searchservice.repository;
import org.k8s.searchservice.entity.UserDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SearchRepository extends ElasticsearchRepository<UserDTO, String> {
}
