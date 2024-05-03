package org.k8s.searchservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {

    @GetMapping("/search")
    public String searchText() {
        return "hello";
    }
}
