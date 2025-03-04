package com.alexey.skoblin.test_task_irbis.mapper;

import com.alexey.skoblin.test_task_irbis.dto.RubricDto;
import com.alexey.skoblin.test_task_irbis.entity.Rubric;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Named("RubricMapper")
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = ComponentModel.SPRING)
public interface RubricSlimMapper {

    @Named("toSlimRubric")
    @Mapping(target = "resource", ignore = true)
    @Mapping(target = "news", ignore = true)
    Rubric toSlimEntry(RubricDto dto);

    @Named("toSlimRubricDto")
    @Mapping(target = "resource", ignore = true)
    @Mapping(target = "news", ignore = true)
    RubricDto toSlimDto(Rubric rubric);

}
