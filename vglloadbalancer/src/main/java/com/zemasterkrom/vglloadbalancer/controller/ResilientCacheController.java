package com.zemasterkrom.vglloadbalancer.controller;

import com.zemasterkrom.vglloadbalancer.configuration.CacheInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@CrossOrigin
public class ResilientCacheController {
    /**
     * Caffeine cache
     */
    private final CacheInstance cache;

    /**
     * Constructor of the resilient cache manager
     *
     * @param c Cache
     */
    @Autowired
    public ResilientCacheController(CacheInstance c) {
        this.cache = c;
    }

    @GetMapping(value = "/vgl-cache")
    public ResponseEntity<String> getVGLCache(HttpServletRequest request) {
        return ResponseEntity.ok(
                this.cache.get(request.getContextPath()) != null ?
                        this.cache.get(request.getContextPath()).toString() :
                        "[]"
        );
    }
}
