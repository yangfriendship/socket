package com.byultudy.socketserver.lineup.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/lineup")
@RestController
public class LineupController {

    @PostMapping
    public ResponseEntity<?> lineup() {
        return ResponseEntity.ok(null);
    }
}
