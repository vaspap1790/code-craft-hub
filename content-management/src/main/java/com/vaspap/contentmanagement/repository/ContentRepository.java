package com.vaspap.contentmanagement.repository;

import com.vaspap.contentmanagement.model.Content;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentRepository extends MongoRepository<Content, String> {
}
