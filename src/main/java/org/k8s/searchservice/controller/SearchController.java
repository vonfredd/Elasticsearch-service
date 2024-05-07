package org.k8s.searchservice.controller;


import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.k8s.searchservice.entity.UserDTO;
import org.k8s.searchservice.repository.SearchRepository;
import org.k8s.searchservice.service.SearchService;
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

    private final SearchService searchService;
    private final RestClient restClient;

    //Use this to persist and make operations on entities. Acts as / is a db.
    private final ElasticsearchOperations elasticsearchOperations;
    private final SearchRepository searchRepository;

    public SearchController(SearchService searchService, RestClient restClient, ElasticsearchOperations elasticsearchOperations, SearchRepository searchRepository, JacksonJsonpMapper jacksonJsonpMapper, ObjectMapper jacksonObjectMapper) {
        this.searchService = searchService;
        this.restClient = restClient;
        this.elasticsearchOperations = elasticsearchOperations;
        this.searchRepository = searchRepository;
    }

    @GetMapping("/newuser")
    public ResponseEntity<String> updatedb(){
       var usersList = Objects.requireNonNull(restClient
               .get()
               .uri("http://usersapp:8002/users/all")
               .retrieve()
               .body(new ParameterizedTypeReference<List<UserDTO>>(){}));

        elasticsearchOperations.save(usersList);
        return ResponseEntity.ok("Successfully updated");
    }

    @GetMapping("/person/{id}")
    public UserDTO findById(@PathVariable("id")  Long id) {
        UserDTO user = elasticsearchOperations.get(id.toString(), UserDTO.class);
        return user;
    }

    @GetMapping("/search")
    public List<UserDTO> searchAsYouType(@RequestParam String query) {
        return searchRepository.searchByName(query);
    }
}
