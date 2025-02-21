package com.huytpq.SecurityEx.controller;

import com.huytpq.SecurityEx.dto.request.ObjectRequest;
import com.huytpq.SecurityEx.dto.response.ObjectResponse;
import com.huytpq.SecurityEx.service.ObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/object")
public class ObjectController {

    @Autowired
    private ObjectService objectService;

    @GetMapping
    public ResponseEntity<List<ObjectResponse>> getAllObjects() {
        return ResponseEntity.ok(objectService.getAllObjects());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObjectResponse> getObjectById(@PathVariable Long id) {
        return ResponseEntity.ok(objectService.getObjectById(id));
    }

    @PostMapping
    public ResponseEntity<ObjectResponse> createObject(@RequestBody ObjectRequest request) {
        return ResponseEntity.ok(objectService.createObject(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ObjectResponse> updateObject(@PathVariable Long id, @RequestBody ObjectRequest request) {
        return ResponseEntity.ok(objectService.updateObject(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteObject(@PathVariable Long id) {
        objectService.deleteObject(id);
        return ResponseEntity.ok("Deleted successfully!");
    }

}
