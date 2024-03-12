package test_utils;

import com.github.javafaker.Faker;
import constans.UserAPIConstant;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import models.UserModel;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;

import static io.restassured.RestAssured.given;

public class AllUserMethods implements UserAPIConstant {

    Faker faker = new Faker();

    private String randomName = faker.name().fullName();

    private String randomGender = Math.random() > 0.5 ? "male" : "female";

    public RequestSpecification request;

    public Response response;

    private static String baseUri = "https://gorest.co.in/public/v2/users";

    public static String randomString() {
        return(RandomStringUtils.randomAlphabetic(5));
    }

    public Response getAllUsers() {
        RestAssured.baseURI=baseUri;
        request=RestAssured.given();
        response = request.get();
        return response;
    }

    public List<UserModel> getAllUsersList() {
        RestAssured.baseURI=baseUri;
        request=RestAssured.given();
        Response resp = request.get().then().extract().response();
        return resp.jsonPath().getList("", UserModel.class);
    }

    public Response getSingleUserMethod(int userId) {
        RestAssured.baseURI=baseUri;
        request=RestAssured.given();
        response = request.get("/"+userId);
        return response;
    }

    public Response getUserById(int userId) {
        RequestSpecification reqSpec = new RequestSpecBuilder()
                .addHeader("Authorization", BEARER_DATA)
                .setContentType(ContentType.JSON)
                .setBaseUri(baseUri)
                .build();
        Response response =
                given()
                        .spec(reqSpec)
                        .when()
                        .get("/" + userId)
                        .then()
                        .statusCode(200)
                        .contentType(ContentType.JSON)
                        .extract().response();
        return response;
    }

    public Response createRandomUser() {
        JSONObject jsonObject= new JSONObject();
        String randomEmail= randomString()+"@gmail.com";
        jsonObject.put("name", randomName);
        jsonObject.put("email",randomEmail);
        jsonObject.put("gender", randomGender);
        jsonObject.put("status","Active");

        String payLoad = jsonObject.toString();
        request = RestAssured.given();
        response=request.header("Authorization",BEARER_DATA).header("Content-Type","application/json")
                .body(payLoad)
                .post(baseUri);
        return response;
    }

    public Response createUser(String name,String email,String gender) {
        JSONObject jsonObject= new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("email",email);
        jsonObject.put("gender", gender);
        jsonObject.put("status","Active");

        String payLoad = jsonObject.toString();
        request = RestAssured.given();
        response=request.header("Authorization",BEARER_DATA).header("Content-Type","application/json")
                .body(payLoad)
                .post(baseUri);
        return response;
    }

    public static int createUserAndReturnStatusCode(net.minidev.json.JSONObject userInfo) {
        return RestAssured
                .given()
                .header("Authorization", BEARER_DATA)
                .body(userInfo.toJSONString())
                .contentType(ContentType.JSON)
                .post(baseUri)
                .then()
                .extract().statusCode();
    }

    public Response putMethod(int userId, String userName, String gender) {
        JSONObject jsonObject= new JSONObject();
        jsonObject.put("name", userName);
        jsonObject.put("gender", gender);
        RestAssured.baseURI=baseUri;
        request=RestAssured.given();
        response= request.header("Authorization",BEARER_DATA).header("Content-Type","application/json").body(jsonObject.toString()).put("/"+userId);
        return response;
    }

    public Response deleteMethod(int userId) {
        RestAssured.baseURI=baseUri;
        request=RestAssured.given();
        response = request.header("Authorization",BEARER_DATA).header("Content-Type","application/json").delete("/"+userId);
        return response;
    }
}
