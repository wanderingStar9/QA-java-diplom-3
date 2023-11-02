package client;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserClient {

    private static final String BASE_URI = "https://stellarburgers.nomoreparties.site";
    private static final String USER_REGISTRATION_ENDPOINT = "/api/auth/register";
    private static final String USER_LOGIN_ENDPOINT = "/api/auth/login";
    private static final String USER_CHANGE_ENDPOINT = "/api/auth/user";

    @Step("Успешное создание уникального пользователя")
    public static Response postCreateNewUser(User user) {
        return given().log().all()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .post(BASE_URI + USER_REGISTRATION_ENDPOINT);
    }


    @Step("Логин под существующим пользователем")
    public static Response checkRequestUserLogin(User user) {
        return given().log().all()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .post(BASE_URI + USER_LOGIN_ENDPOINT);
    }


    @Step("Получение токена")
    public static String getToken(User user){
        return checkRequestUserLogin(user).then().extract().path("accessToken");
    }

    @Step("Удаление пользователя")
    public static Response deleteUser(String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .when()
                .delete(BASE_URI + USER_CHANGE_ENDPOINT);
    }

}
