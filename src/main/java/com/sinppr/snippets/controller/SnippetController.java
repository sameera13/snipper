package com.sinppr.snippets.controller;

import com.sinppr.snippets.model.Snippet;
import com.sinppr.snippets.service.SnippetService;
import java.util.List;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/snippet")
public class SnippetController {
    private final SnippetService service;

    public SnippetController(SnippetService service) {
        this.service = service;
    }

    @PostMapping
    public Snippet createSnippet(@RequestBody Snippet snippet) throws Exception {
        return service.addSnippet(snippet);
    }

    @GetMapping
    public List<Snippet> getAll() throws Exception {
        return service.getAllSnippets();
    }
}
