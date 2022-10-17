package com.curady.reviewservice.global.mapper;

import com.curady.reviewservice.domain.keyword.dto.ResponseKeywords;
import com.curady.reviewservice.domain.keyword.model.Keyword;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface KeywordMapper {
    KeywordMapper INSTANCE = Mappers.getMapper(KeywordMapper.class);

    @Named("E2R")
    ResponseKeywords keywordToResponse(Keyword keyword);

    @IterableMapping(qualifiedByName = "E2R")
    List<ResponseKeywords> keywordsToResponseList(List<Keyword> keywords);
}
