package com.alexey.skoblin.test_task_irbis.mapper;


import com.alexey.skoblin.test_task_irbis.dto.NewsDto;
import com.alexey.skoblin.test_task_irbis.dto.RubricDto;
import com.alexey.skoblin.test_task_irbis.entity.News;
import com.alexey.skoblin.test_task_irbis.entity.Rubric;
import org.mapstruct.*;

@Named("NewsMapper")
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface NewsSlimMapper extends BaseMapper<NewsDto, News> {

    @Named("toSlimNews")
    @Mapping(target = "rubric", ignore = true)
    News toSlimEntry(NewsDto dto);

    @Named("toSlimNewsDto")
    @Mapping(target = "rubric", ignore = true)
    NewsDto toSlimDto(News news);


}
