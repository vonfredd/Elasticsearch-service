package org.k8s.searchservice.controller;

import org.k8s.searchservice.config.Properties;
import org.k8s.searchservice.entity.UserDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin
public class SearchController {

    private final RestClient restClient;
    private final ElasticsearchOperations elasticsearchOperations;
    private final Properties properties;

    public SearchController(RestClient restClient, ElasticsearchOperations elasticsearchOperations, Properties properties) {
        this.restClient = restClient;
        this.elasticsearchOperations = elasticsearchOperations;
        this.properties = properties;
    }

    @GetMapping("/newuser")
    public ResponseEntity<String> updatedb(){
        try {
            var usersList = Objects.requireNonNull(restClient
                    .get()
                    .uri(properties.getUri())
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<UserDTO>>() {
                    }));
            elasticsearchOperations.save(usersList);
            return ResponseEntity.ok().build();
        }catch (NullPointerException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public List<UserDTO> searchAsYouType(@RequestParam String query) {
        Criteria criteria = new Criteria("name")
                .startsWith(query);
        Query criteriaQuery = new CriteriaQuery(criteria);
        SearchHits<UserDTO> searchHits = elasticsearchOperations.search(criteriaQuery, UserDTO.class);

        return searchHits.getSearchHits()
                .stream()
                .map(SearchHit::getContent)
                .toList();
    }

    @GetMapping("/search/contains")
    public List<UserDTO> searchContainsAsYouType(@RequestParam String query) {
        Criteria criteria = new Criteria("name")
                .contains(query);
        Query criteriaQuery = new CriteriaQuery(criteria);
        SearchHits<UserDTO> searchHits = elasticsearchOperations.search(criteriaQuery, UserDTO.class);

        return searchHits.getSearchHits()
                .stream()
                .map(SearchHit::getContent)
                .toList();
    }
}
