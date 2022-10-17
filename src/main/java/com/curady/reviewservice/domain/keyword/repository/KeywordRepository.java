package com.curady.reviewservice.domain.keyword.repository;

import com.curady.reviewservice.domain.keyword.model.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    List<Keyword> findByIdIn(List<Long> idList);
}
