package com.alexey.skoblin.test_task_irbis.mapper;


import com.alexey.skoblin.test_task_irbis.dto.NewsDto;
import com.alexey.skoblin.test_task_irbis.entity.News;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Named("NewsMapper")
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface NewsMapper extends BaseMapper<NewsDto, News> {

    @Override
    News toEntity(NewsDto dto);

    @Override
    NewsDto toDto(News entity);

    @Override
    List<NewsDto> toDtoList(List<News> entities);

    @Override
    List<News> toEntityList(List<NewsDto> dtos);
}
