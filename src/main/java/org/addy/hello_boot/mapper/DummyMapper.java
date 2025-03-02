package org.addy.hello_boot.mapper;

import org.addy.hello_boot.dto.DummyDto;
import org.addy.hello_boot.model.Dummy;
import org.mapstruct.*;

@Mapper(componentModel="spring", uses=DummyItemMapper.class)
@DecoratedWith(DummyMapperDecorator.class)
public interface DummyMapper {
    DummyDto toDto(Dummy dummy);
    Dummy fromDto(DummyDto dummyDto);
    @Mapping(target="id", ignore=true)
    @Mapping(target="items", ignore=true)
    void updateFromDto(DummyDto dummyDto, @MappingTarget Dummy dummy);
}
