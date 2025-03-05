package com.alexey.skoblin.test_task_irbis.mapper;

import com.alexey.skoblin.test_task_irbis.dto.ResourceDto;
import com.alexey.skoblin.test_task_irbis.dto.RubricDto;
import com.alexey.skoblin.test_task_irbis.dto.request.ResourceWebDto;
import com.alexey.skoblin.test_task_irbis.entity.Resource;

import java.util.*;

import com.alexey.skoblin.test_task_irbis.entity.Rubric;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;

@Named("ResourceMapper")
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {RubricSlimMapper.class})
public interface ResourceMapper extends BaseMapper<ResourceDto, Resource> {

    @Override
    @Mapping(target = "rubrics", qualifiedByName = "toSlimRubric", defaultExpression = "java(new java.util.ArrayList<>())")
    Resource toEntity(ResourceDto dto);

    @Override
    @Mapping(target = "rubrics", qualifiedByName = "toSlimRubricDto", defaultExpression = "java(new java.util.ArrayList<>())")
    ResourceDto toDto(Resource entity);

    Resource toEntity(ResourceWebDto resourceWebDto);

    @Mapping(target = "rubricIds", expression = "java(rubricsToRubricIds(resource.getRubrics()))")
    @InheritInverseConfiguration(name = "toEntity")
    ResourceWebDto toResourceWebDto(Resource resource);

    default List<UUID> rubricsToRubricIds(List<Rubric> rubrics) {
        return rubrics.stream().map(Rubric::getId).toList();
    }
}