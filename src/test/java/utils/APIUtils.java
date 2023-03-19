package utils;

import api.ApiClientRequest;
import api.Endpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class APIUtils {

    public static String generateToken() {
        ApiClientRequest clientRequest = new ApiClientRequest("some Random name ", "someemail12387928379823@gmail.com");
        Response response = RestAssured.given().contentType(ContentType.JSON).body(clientRequest).post(Endpoints.BASE_URL.getPath() + Endpoints.TOKEN.getPath());
        return response.jsonPath().get("accessToken");
    }

    public static void main(String[] args) {
        System.out.println(generateToken());
    }
}