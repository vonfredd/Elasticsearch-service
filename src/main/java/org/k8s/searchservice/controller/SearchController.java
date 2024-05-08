package org.k8s.searchservice.controller;

import org.k8s.searchservice.config.Properties;
import org.k8s.searchservice.entity.UserDTO;
import org.k8s.searchservice.repository.SearchRepository;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
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
    private final SearchRepository searchRepository;
    private final Properties properties;

    public SearchController(RestClient restClient, ElasticsearchOperations elasticsearchOperations, SearchRepository searchRepository, Properties properties) {
        this.restClient = restClient;
        this.elasticsearchOperations = elasticsearchOperations;
        this.searchRepository = searchRepository;
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
            return ResponseEntity.ok("Successfully updated");
        }catch (NullPointerException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public List<UserDTO> searchAsYouType(@RequestParam String query) {
        return searchRepository.searchByName(query);
    }
}
