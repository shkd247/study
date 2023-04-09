package com.example.webflux_assignment.controller;

import javax.lang.model.type.NullType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sync/board")
public class SyncBoardController {

    @GetMapping
    public ResponseEntity<NullType> getBoard(){
        return ResponseEntity.ok(null);
    }

    @PostMapping
    public ResponseEntity<NullType> postBoard(){
        return ResponseEntity.ok(null);
    }

    @PatchMapping
    public ResponseEntity<NullType> patchBoard(){
        return ResponseEntity.ok(null);
    }

    @DeleteMapping
    public ResponseEntity<NullType> deleteBoard(){
        return ResponseEntity.ok(null);
    }
}
