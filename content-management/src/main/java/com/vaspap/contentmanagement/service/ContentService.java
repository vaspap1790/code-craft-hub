package com.vaspap.contentmanagement.service;

import com.vaspap.contentmanagement.dto.ContentDto;

import java.util.List;

public interface ContentService {
    List<ContentDto> getAllContent();
    ContentDto getContentById(String id);
    ContentDto createContent(ContentDto contentDto);
    ContentDto updateContent(String id, ContentDto contentDto);
    ContentDto deleteContent(String id);
}
