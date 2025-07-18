package com.sinppr.snippets;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sinppr.snippets.model.Snippet;
import com.sinppr.snippets.service.SnippetService;
import java.io.InputStream;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class SnippetLoader implements CommandLineRunner {

    @Autowired
    private SnippetService snippetService;

    @Override
    public void run(String... args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Snippet>> typeReference = new TypeReference<>() {};
        InputStream inputStream = new ClassPathResource("seedData.json").getInputStream();

        try {
            List<Snippet> snippets = mapper.readValue(inputStream, typeReference);
            for (Snippet snippet : snippets) {
                snippetService.addSnippet(snippet);
            }
            System.out.println("✅ Snippet data loaded successfully.");
        } catch (Exception e) {
            System.out.println("❌ Failed to load snippet data: " + e.getMessage());
        }
    }
}


