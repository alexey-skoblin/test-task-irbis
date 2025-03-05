package com.alexey.skoblin.test_task_irbis.mapper;

import com.alexey.skoblin.test_task_irbis.dto.RubricDto;
import com.alexey.skoblin.test_task_irbis.entity.Rubric;
import org.mapstruct.*;
import org.mapstruct.MappingConstants.ComponentModel;

@Named("RubricMapper")
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = ComponentModel.SPRING,
        uses = {NewsSlimMapper.class}
)
public interface RubricSlimMapper {

    @Named("toSlimRubric")
    @Mapping(target = "resource", ignore = true)
    @Mapping(target = "news", qualifiedByName = "toSlimNews", defaultExpression = "java(new java.util.ArrayList<>())")
    Rubric toSlimEntry(RubricDto dto);

    @Named("toSlimRubricDto")
    @Mapping(target = "resource", ignore = true)
    @Mapping(target = "news", qualifiedByName = "toSlimNewsDto", defaultExpression = "java(new java.util.ArrayList<>())")
    RubricDto toSlimDto(Rubric rubric);

}
