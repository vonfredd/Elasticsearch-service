package org.k8s.searchservice.controller;


import org.k8s.searchservice.entity.UserDTO;
import org.k8s.searchservice.repository.SearchRepository;
import org.k8s.searchservice.service.SearchService;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class SearchController {

    private final SearchService searchService;

    //Use this to persist and make operations on entities. Acts as / is a db.
    private final ElasticsearchOperations elasticsearchOperations;
    private final SearchRepository searchRepository;

    public SearchController(SearchService searchService, ElasticsearchOperations elasticsearchOperations, SearchRepository searchRepository) {
        this.searchService = searchService;
        this.elasticsearchOperations = elasticsearchOperations;
        this.searchRepository = searchRepository;
    }

    @PostMapping("/newuser")
    public ResponseEntity<String> updatedb(@RequestBody UserDTO user) {
        elasticsearchOperations.save(user);
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
