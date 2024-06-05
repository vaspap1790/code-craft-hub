package com.vaspap.contentmanagement.util;

import com.vaspap.contentmanagement.dto.ContentDto;
import com.vaspap.contentmanagement.model.Content;

public class ContentMapper {
    public static ContentDto toDto(Content content) {
        return new ContentDto(
                content.getId(),
                content.getTitle(),
                content.getDescription(),
                content.getCategory(),
                content.getAccessLevel(),
                content.getMetadata()
        );
    }

    public static Content toEntity(ContentDto contentDto) {
        Content content = new Content();
        content.setTitle(contentDto.title());
        content.setDescription(contentDto.description());
        content.setCategory(contentDto.category());
        content.setAccessLevel(contentDto.accessLevel());
        content.setMetadata(contentDto.metadata());
        return content;
    }
}
