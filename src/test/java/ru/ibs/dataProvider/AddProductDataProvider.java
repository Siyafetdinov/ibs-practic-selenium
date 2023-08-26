package ru.ibs.dataProvider;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import ru.ibs.objects.Product;

import java.util.stream.Stream;

public class AddProductDataProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(new Product("Манго", "Фрукт", true)),
                Arguments.of(new Product("Картошка", "Овощ", false))
        );
    }
}
