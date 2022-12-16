package com.zemasterkrom.vglloadbalancer.configuration;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@CrossOrigin
public class ResiliencyCacheController {

    @GetMapping(value = "/video-games/cache")
    public ResponseEntity<?> getAllCachedVideoGames() {
        return ResponseEntity.ok("");
    }
}
