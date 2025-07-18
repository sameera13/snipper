package com.sinppr.snippets.controller;

import com.sinppr.snippets.model.Snippet;
import com.sinppr.snippets.service.SnippetService;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/snippets")
public class SnippetController {

    private final SnippetService snippetService;

    public SnippetController(SnippetService snippetService) {
        this.snippetService = snippetService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Snippet createSnippet(@RequestBody Snippet snippet) {
        return snippetService.addSnippet(snippet);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Snippet> getAllSnippets(@RequestParam(required = false) String lang) {
        if (lang != null) {
            return snippetService.getSnippetsByLanguage(lang);
        }
        return snippetService.getAllSnippets();
    }

    
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Snippet getSnippetById(@PathVariable int id) {
        return snippetService.getSnippetById(id);
    }
}
