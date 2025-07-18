package com.sinppr.snippets.service;

import com.sinppr.snippets.model.Snippet;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class SnippetService {
    private final List<Snippet> snippets = new ArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger();

    public SnippetService() {
        // Optional seed data
        addSnippet(new Snippet(0, "python", "print('Hello, world!')"));
        addSnippet(new Snippet(0, "java", "System.out.println(\"Hello\");"));
    }

    public Snippet addSnippet(Snippet snippet) {
        snippet.setId(idCounter.incrementAndGet());
        snippets.add(snippet);
        return snippet;
    }

    public List<Snippet> getAllSnippets() {
        return snippets;
    }

    public Snippet getSnippetById(int id) {
        return snippets.stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Snippet> getSnippetsByLanguage(String lang) {
        return snippets.stream()
                .filter(s -> s.getLanguage().equalsIgnoreCase(lang))
                .collect(Collectors.toList());
    }
}

