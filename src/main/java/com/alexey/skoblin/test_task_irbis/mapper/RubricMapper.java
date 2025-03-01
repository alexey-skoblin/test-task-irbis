package com.alexey.skoblin.test_task_irbis.mapper;

import com.alexey.skoblin.test_task_irbis.dto.RubricDto;
import com.alexey.skoblin.test_task_irbis.entity.Rubric;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface RubricMapper extends BaseMapper<RubricDto, Rubric> {

    @Override
    Rubric toEntity(RubricDto dto);

    @Override
    RubricDto toDto(Rubric entity);

    @Override
    List<RubricDto> toDtoList(List<Rubric> entities);

    @Override
    List<Rubric> toEntityList(List<RubricDto> dtos);
}
