package com.alexey.skoblin.test_task_irbis.mapper;

import com.alexey.skoblin.test_task_irbis.dto.ResourceDto;
import com.alexey.skoblin.test_task_irbis.dto.RubricDto;
import com.alexey.skoblin.test_task_irbis.entity.Resource;
import com.alexey.skoblin.test_task_irbis.entity.Rubric;

import org.mapstruct.*;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.factory.Mappers;

@Named("RubricMapper")
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = ComponentModel.SPRING,
        uses = {ResourceSlimMapper.class}
)
public interface RubricMapper extends BaseMapper<RubricDto, Rubric> {

    @Override
    @Mapping(target = "resource", qualifiedByName = "toSlimResource")
    Rubric toEntity(RubricDto dto);

    @Override
    @Mapping(target = "resource", qualifiedByName = "toSlimResourceDto")
    RubricDto toDto(Rubric entity);

}
