package org.addy.hello_boot.mapper;

import org.addy.hello_boot.dto.DummyItemDto;
import org.addy.hello_boot.model.DummyItem;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel="spring")
public interface DummyItemMapper {
    DummyItemDto toDto(DummyItem dummyItem);
    @Mapping(target="parent", ignore=true)
    DummyItem fromDto(DummyItemDto dummyItemDto);
    @InheritConfiguration
    @Mapping(target="id", ignore=true)
    void updateFromDto(DummyItemDto dummyItemDto, @MappingTarget DummyItem dummyItem);
}
