package com.curady.reviewservice.domain.keyword.service;

import com.curady.reviewservice.domain.keyword.dto.ResponseKeywords;
import com.curady.reviewservice.domain.keyword.repository.KeywordRepository;
import com.curady.reviewservice.global.mapper.KeywordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KeywordService {
    private final KeywordRepository keywordRepository;

    @Transactional
    public List<ResponseKeywords> getKeywords() {
        return KeywordMapper.INSTANCE.keywordsToResponseList(keywordRepository.findAll());
    }
}
