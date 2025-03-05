package com.alexey.skoblin.test_task_irbis.mapper;

import com.alexey.skoblin.test_task_irbis.dto.RubricDto;
import com.alexey.skoblin.test_task_irbis.entity.Rubric;

import org.mapstruct.*;
import org.mapstruct.MappingConstants.ComponentModel;

@Named("RubricMapper")
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = ComponentModel.SPRING,
        uses = {ResourceSlimMapper.class, ResourceMapper.class, NewsMapper.class}
)
public interface RubricMapper extends BaseMapper<RubricDto, Rubric> {

    @Override
    @Mapping(target = "resource", qualifiedByName = "toSlimResource")
    @Mapping(target = "news", defaultExpression = "java(new java.util.ArrayList<>())")
    Rubric toEntity(RubricDto dto);

    @Override
    @Mapping(target = "resource", qualifiedByName = "toSlimResourceDto")
    @Mapping(target = "news", defaultExpression = "java(new java.util.ArrayList<>())")
    RubricDto toDto(Rubric entity);

}
