package com.alexey.skoblin.test_task_irbis.mapper;


import com.alexey.skoblin.test_task_irbis.dto.NewsDto;
import com.alexey.skoblin.test_task_irbis.entity.News;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Named("NewsMapper")
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {RubricSlimMapper.class}
)
public interface NewsMapper extends BaseMapper<NewsDto, News> {

    @Override
    @Mapping(target = "rubrics", qualifiedByName = "toSlimRubric")
    News toEntity(NewsDto dto);

    @Override
    @Mapping(target = "rubrics", qualifiedByName = "toSlimRubricDto")
    NewsDto toDto(News entity);
}
