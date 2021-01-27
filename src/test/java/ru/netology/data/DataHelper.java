package ru.netology.data;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import lombok.Value;

import static io.restassured.RestAssured.given;

public class DataHelper {
    private DataHelper(){}

    public static class User {
        private static final Faker faker = new Faker();
        private static final String login = faker.name().username();
        private static final String password = faker.internet().password();
        private static TestUser tUser = new TestUser(login, password, "active");

        public static void userRegistation(String status){
            given()
                    .baseUri("http://localhost:9999")
                    .contentType(ContentType.JSON)
                    .body(new Gson().toJson(new TestUser(login, password, status)))
                    .when()
                    .post("/api/system/users")
                    .then()
                    .statusCode(200);
        }

        /*public static void setStatus(String status){
            tUser.status = status;
            given()
                    .baseUri("http://localhost:9999")
                    .contentType(ContentType.JSON)
                    .body(new Gson().toJson(tUser))
                    .when()
                    .post("/api/system/users")
                    .then()
                    .statusCode(200);
        }*/

        /*public static void activeUserRegistration(){
            given()
                    .baseUri("http://localhost:9999")
                    .contentType(ContentType.JSON)
                    .body(new Gson().toJson(new testUser(login, password, "active")))
                    .when()
                    .post("/api/system/users")
                    .then()
                    .statusCode(200);
        }

        public static void blockedUserRegistration(){
            given()
                    .baseUri("http://localhost:9999")
                    .contentType(ContentType.JSON)
                    .body(new Gson().toJson(new testUser(login, password, "blocked")))
                    .when()
                    .post("/api/system/users")
                    .then()
                    .statusCode(200);
        }*/

        public static String getLogin() {
            return login;
        }

        public static String getPassword() {
            return password;
        }

        public static String getAnotherLogin() {
            return faker.name().username();
        }

        public static String getAnotherPassword() {
            return faker.internet().password();
        }
    }

    private static class TestUser {

        String login;
        String password;
        String status;

        private TestUser(String login, String password, String status){
            this.login = login;
            this.password = password;
            this.status = status;
        }

        public void setStatus(String status){
            this.status = status;
        }
    }
    
}
