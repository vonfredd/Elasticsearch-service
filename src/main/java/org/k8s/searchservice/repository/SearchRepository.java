package org.k8s.searchservice.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SearchRepository extends ElasticsearchRepository<String, String> {
}
