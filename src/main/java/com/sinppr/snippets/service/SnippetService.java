package com.sinppr.snippets.service;

import com.sinppr.snippets.model.Snippet;
import com.sinppr.snippets.util.EncryptionUtil;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Service;



@Service
public class SnippetService {
    private final List<Snippet> snippets = new ArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger();

    public Snippet addSnippet(Snippet snippet) throws Exception {
        String encrypted = EncryptionUtil.encrypt(snippet.getCode(), System.getenv("ENCRYPTION_KEY"));
        snippet.setEncryptedCode(encrypted);
        snippet.setId(idCounter.incrementAndGet());
        snippet.setCode(null); // Hide plaintext code from being saved
        snippets.add(snippet);
        return snippet;
    }

    public List<Snippet> getAllSnippets() throws Exception {
        List<Snippet> result = new ArrayList<>();
        for (Snippet s : snippets) {
            String decrypted = EncryptionUtil.decrypt(s.getEncryptedCode(), System.getenv("ENCRYPTION_KEY"));

            result.add(new Snippet(s.getId(), s.getLanguage(), decrypted, null));
        }
        return result;
    }
}
