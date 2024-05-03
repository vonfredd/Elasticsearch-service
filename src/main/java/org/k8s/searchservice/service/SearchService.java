package org.k8s.searchservice.service;

import org.k8s.searchservice.entity.UserDTO;
import org.k8s.searchservice.repository.SearchRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {

    SearchRepository repository;

    public SearchService(SearchRepository repository) {
        this.repository = repository;
    }

    public List<UserDTO> searchByName(String name) {
        return new ArrayList<>();
    }

    public void saveUser(UserDTO user) {
        try {
            repository.save(user);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException();
        }
    }
}
