package ru.ibs.utils;

import org.openqa.selenium.WebElement;
import ru.ibs.objects.Product;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class UtilsProducts {

    public static List<Product> getParseListProduct(List<WebElement> listWebElement) {
        List<Product> resultProductList = new ArrayList<>();
        listWebElement.forEach(element -> {
            String[] tmpString = element.getText().split(" ");
            resultProductList.add(new Product(
                    Integer.parseInt(tmpString[0]),
                    tmpString[1],
                    tmpString[2],
                    Boolean.parseBoolean(tmpString[3])));
        });
        return resultProductList;
    }

    public static boolean isUniqueListIndex(List<Product> products) {
        return products.size() == new HashSet<>(products).size();
    }
}
