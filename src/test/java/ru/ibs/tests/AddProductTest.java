package ru.ibs.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;

import ru.ibs.baseTest.BaseTest;
import ru.ibs.dataProvider.AddProductDataProvider;
import ru.ibs.objects.Product;
import ru.ibs.utils.UtilsProducts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class AddProductTest extends BaseTest {

    private static final String TABLE_PRODUCT = "//table[@class='table']//tbody//tr";
    private static final String BUTTON_ADD_PRODUCT = "//button[contains(text(),'Добавить')]";
    private static final String BUTTON_SAVE_PRODUCT = "save";
    private static final String FORM_ADDED_PRODUCT = "modal-content";
    private static final String NAME = "name";
    private static final String TYPE = "type";
    private static final String CHECKBOX_EXOTIC = "exotic";

    @BeforeEach
    public void beforeAddProductTest() {
        driver.get(URL_PRODUCT);
    }

    @ParameterizedTest
    @ArgumentsSource(AddProductDataProvider.class)
    void addProductTest(Product newProduct) {

        // Получаем список существующих продуктов из таблицы.
        List<Product> productsListBefore = UtilsProducts.getParseListProduct(driver.findElements(By.xpath(TABLE_PRODUCT)));

        // Нажимаем на кнопку добавить товар
        driver.findElement(By.xpath(BUTTON_ADD_PRODUCT)).click();

        // Проверяем, что форма добавления товара открылась
        WebElement formAddProduct = driver.findElement(By.className(FORM_ADDED_PRODUCT));
        // Без wait-ов тест крашится на Chromium браузерах
        Assertions.assertTrue(
                wait.until(ExpectedConditions.visibilityOf(formAddProduct)).isDisplayed(),
                "Окно добавления товара не открылось");

        // Добавляем товар (Имя, тип, экзотический)
        WebElement fieldName = driver.findElement(By.name(NAME));
        fieldName.sendKeys(newProduct.getName());

        Select selectType = new Select(driver.findElement(By.id(TYPE)));
        selectType.selectByVisibleText(newProduct.getType());

        WebElement checkbox = driver.findElement(By.id(CHECKBOX_EXOTIC));
        if ((newProduct.isExotic() && !checkbox.isSelected())
                || (!newProduct.isExotic() && checkbox.isSelected())) {
            checkbox.click();
        }

        // Проверяем правильность заполнения формы, проверяя все поля
        Assertions.assertAll(
                () -> Assertions.assertEquals(
                        newProduct.getName(), fieldName.getAttribute("value"), "Неверно указано поле 'Наименование' в форме"),
                () -> Assertions.assertEquals(
                        newProduct.getType(), selectType.getFirstSelectedOption().getText(), "Неверно указано поле 'Тип' в форме"),
                () -> Assertions.assertEquals(newProduct.isExotic(), checkbox.isSelected(), "Неверно указано поле 'Экзотический' в форме")
        );

        // Сохраняем товар
        driver.findElement(By.id(BUTTON_SAVE_PRODUCT)).click();

        // Провеярем, что форма добавления товара закрылась
        // Без wait-ов тест крашится на Chromium браузерах
        Assertions.assertTrue(
                wait.until(ExpectedConditions.invisibilityOf (formAddProduct)),
                "Окно добавления товара не закрылось");

        // Выгружаем список товаров после добавления товара и проверяем, что он изменился
        List<Product> productsListAfter = UtilsProducts.getParseListProduct(driver.findElements(By.xpath(TABLE_PRODUCT)));
        Assertions.assertNotEquals(productsListBefore, productsListAfter, "Список товаров не изменился, после добаваления товара");

        // Проверяем последний товар. Проверяя все поля
        Product productCheck = productsListAfter.get(productsListBefore.size());
        Assertions.assertAll(
                () -> Assertions.assertEquals(newProduct.getName(), productCheck.getName(), "Неверно указано поле 'Наименование'"),
                () -> Assertions.assertEquals(newProduct.getType(), productCheck.getType(), "Неверно указано поле 'Тип'"),
                () -> Assertions.assertEquals(newProduct.isExotic(), productCheck.isExotic(), "Неверно указано поле 'Экзотический'")
        );

        // Проверяем что id у товара уникальный.
        Assertions.assertTrue(UtilsProducts.isUniqueListIndex(productsListAfter), "Товар не получил уникальный ID");
    }
}

