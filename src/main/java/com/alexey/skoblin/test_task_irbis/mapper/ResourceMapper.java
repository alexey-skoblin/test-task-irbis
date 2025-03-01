package com.alexey.skoblin.test_task_irbis.mapper;

import com.alexey.skoblin.test_task_irbis.dto.ResourceDto;
import com.alexey.skoblin.test_task_irbis.entity.Resource;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ResourceMapper extends BaseMapper<ResourceDto, Resource> {

    @Override
    Resource toEntity(ResourceDto dto);

    @Override
    ResourceDto toDto(Resource entity);

    @Override
    List<ResourceDto> toDtoList(List<Resource> entities);

    @Override
    List<Resource> toEntityList(List<ResourceDto> dtos);
}
