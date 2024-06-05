package com.vaspap.contentmanagement.controller;

import com.vaspap.contentmanagement.dto.ContentDto;
import com.vaspap.contentmanagement.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/content")
public class ContentController {

    private final ContentService contentService;

    @Autowired
    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    @GetMapping
    public ResponseEntity<List<ContentDto>> getAllContent() {
        List<ContentDto> contents = contentService.getAllContent();
        return ResponseEntity.ok(contents);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContentDto> getContentById(@PathVariable String id) {
        ContentDto content = contentService.getContentById(id);
        return ResponseEntity.ok(content);
    }

    @PostMapping
    public ResponseEntity<ContentDto> createContent(@RequestBody ContentDto contentDto) {
        ContentDto createdContent = contentService.createContent(contentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdContent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContentDto> updateContent(@PathVariable String id, @RequestBody ContentDto contentDto) {
        ContentDto updatedContent = contentService.updateContent(id, contentDto);
        return ResponseEntity.ok(updatedContent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ContentDto> deleteContent(@PathVariable String id) {
        ContentDto deletedContent =contentService.deleteContent(id);
        return ResponseEntity.ok(deletedContent);
    }
}
