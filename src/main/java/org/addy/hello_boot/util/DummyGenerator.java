package org.addy.hello_boot.util;

import lombok.RequiredArgsConstructor;
import org.addy.hello_boot.model.Dummy;
import org.addy.hello_boot.model.DummyItem;
import org.addy.hello_boot.repository.DummyRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name="management.data.generation.enabled", havingValue="true")
public class DummyGenerator implements CommandLineRunner {
    private final DummyRepository dummyRepository;

    @Override
    public void run(String... args) {
        String[] colors = {"Red", "Green", "Blue", "Yellow", "Orange", "Cyan", "Purple", "Black", "White", "Grey"};
        Random random = ThreadLocalRandom.current();

        for (String color : colors) {
            var dummy = new Dummy();
            dummy.setName(color);
            dummy.setDescription(String.format("A %s dummy entity", color.toLowerCase()));

            int itemCount = random.nextInt(1, 5);
            for (int i = 0; i < itemCount; i++) {
                var item = new DummyItem();
                item.setName(String.format("%s dummy item #%d", color, i + 1));
                item.setPrice(BigDecimal.valueOf(random.nextInt(10, 1000)));
                item.setParent(dummy);
                dummy.getItems().add(item);
            }

            dummyRepository.save(dummy);
        }
    }
}
