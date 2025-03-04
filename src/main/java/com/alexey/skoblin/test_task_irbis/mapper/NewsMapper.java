package com.alexey.skoblin.test_task_irbis.mapper;


import com.alexey.skoblin.test_task_irbis.dto.NewsDto;
import com.alexey.skoblin.test_task_irbis.entity.News;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Named("NewsMapper")
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {RubricSlimMapper.class}
)
public interface NewsMapper extends BaseMapper<NewsDto, News> {

    @Override
    @Mapping(target = "rubric", qualifiedByName = "toSlimRubric")
    News toEntity(NewsDto dto);

    @Override
    @Mapping(target = "rubric", qualifiedByName = "toSlimRubricDto")
    NewsDto toDto(News entity);
}
