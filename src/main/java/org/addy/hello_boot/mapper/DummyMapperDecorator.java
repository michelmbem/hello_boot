package org.addy.hello_boot.mapper;

import org.addy.hello_boot.dto.DummyDto;
import org.addy.hello_boot.dto.DummyItemDto;
import org.addy.hello_boot.model.Dummy;
import org.addy.hello_boot.model.DummyItem;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public abstract class DummyMapperDecorator implements DummyMapper {
    @Autowired
    private DummyMapper dummyMapper;
    @Autowired
    private DummyItemMapper dummyItemMapper;

    @Override
    public Dummy fromDto(DummyDto dummyDto) {
        Dummy dummy = dummyMapper.fromDto(dummyDto);

        for (DummyItem item : dummy.getItems()) {
            item.setParent(dummy);
        }

        return dummy;
    }

    @Override
    public void updateFromDto(DummyDto dummyDto, Dummy dummy) {
        dummyMapper.updateFromDto(dummyDto, dummy);
        syncItems(dummyDto, dummy);
    }

    private void syncItems(DummyDto dummyDto, Dummy dummy) {
        Set<DummyItemDto> givenItems = dummyDto.getItems();
        Set<DummyItem> originalItems = dummy.getItems();
        Set<DummyItem> newItems = new HashSet<>();
        Set<DummyItem> deletedItems = new HashSet<>();

        for (DummyItemDto givenItem : givenItems) {
            DummyItem originalItem = originalItems.stream()
                    .filter(it -> Objects.equals(givenItem.getId(), it.getId()))
                    .findFirst()
                    .orElse(null);

            if (originalItem != null) {
                dummyItemMapper.updateFromDto(givenItem, originalItem);
            } else {
                var newItem = dummyItemMapper.fromDto(givenItem);
                newItem.setParent(dummy);
                newItems.add(newItem);
            }
        }

        for (DummyItem originalItem : originalItems) {
            if (givenItems.stream().noneMatch(givenItem ->
                    Objects.equals(givenItem.getId(), originalItem.getId()))) {
                originalItem.setParent(null);
                deletedItems.add(originalItem);
            }
        }

        originalItems.removeAll(deletedItems);
        originalItems.addAll(newItems);
    }
}
