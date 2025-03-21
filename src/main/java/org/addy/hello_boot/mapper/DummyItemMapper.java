package org.addy.hello_boot.mapper;

import org.addy.hello_boot.dto.DummyItemDto;
import org.addy.hello_boot.model.DummyItem;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel="spring", unmappedTargetPolicy=IGNORE)
public interface DummyItemMapper {
    DummyItemDto toDto(DummyItem dummyItem);

    @Mapping(target="id", ignore=true)
    DummyItem fromDto(DummyItemDto dummyItemDto);

    @InheritConfiguration
    void updateFromDto(DummyItemDto dummyItemDto, @MappingTarget DummyItem dummyItem);
}
