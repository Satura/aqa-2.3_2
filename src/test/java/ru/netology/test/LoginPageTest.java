package ru.netology.test;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.data.DataHelper.User.*;

public class LoginPageTest {
    SelenideElement submitButton = $("[data-test-id='action-login'] .button__text");
    SelenideElement errorMessage = $("[data-test-id='error-notification'] .notification__content");

    @BeforeEach
    void setUp() {
        userRegistration("active");
        open("http://localhost:9999/");
        $("[data-test-id='login'] .input__control").setValue(getLogin());
        $("[data-test-id='password'] .input__control").setValue(getPassword());
    }

    @Test
    void shouldSuccessLogin() {
        submitButton.click();
        $(byText("Личный кабинет")).shouldBe(visible);
    }

    @Test
    void shouldNotLoginWrongName() {
        $("[data-test-id='login'] .input__control").setValue(getAnotherLogin());
        submitButton.click();
        errorMessage.shouldBe(visible).shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void shouldNotLoginWrongPass() {
        $("[data-test-id='password'] .input__control").setValue(getAnotherPassword());
        submitButton.click();
        errorMessage.shouldBe(visible).shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void shouldNotLoginBlockedUser() {
        userRegistration("blocked");
        submitButton.click();
        errorMessage.shouldBe(visible).shouldHave(text("Ошибка! Пользователь заблокирован"));
    }

}
