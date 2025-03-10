package com.alexey.skoblin.test_task_irbis.mapper;

import com.alexey.skoblin.test_task_irbis.dto.ResourceDto;
import com.alexey.skoblin.test_task_irbis.entity.Resource;
import org.mapstruct.*;

@Named("ResourceMapper")
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface ResourceSlimMapper {

    @Named("toSlimResource")
    @Mapping(target = "rubrics", ignore = true, defaultExpression = "java(new java.util.ArrayList<>())")
    Resource toSlimEntity(ResourceDto dto);

    @Named("toSlimResourceDto")
    @Mapping(target = "rubrics", ignore = true, defaultExpression = "java(new java.util.ArrayList<>())")
    ResourceDto toSlimDto(Resource entity);

}