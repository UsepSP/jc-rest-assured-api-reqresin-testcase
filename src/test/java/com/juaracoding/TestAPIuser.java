package com.juaracoding;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TestAPIuser {

    String endpoint = "https://reqres.in/api/users?page=1";
    @Test
    public void testGetListUsers(){

        Response response = RestAssured.get(endpoint);
        System.out.println(response.getStatusCode());
        System.out.println(response.getTime());
        System.out.println(response.getBody().asString());
        System.out.println(response.getHeaders());
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode,200);
        // validasi pada response body use json path
    }

    @Test
    public void testListUsers(){
        given().get(endpoint)
                .then()
                .statusCode(200)
                .body("data.id[0]", equalTo(1));
    }

    @Test
    public void testAddUser(){
        JSONObject request = new JSONObject();

        given()
                // .header("Authorization", "Bearer token")
                .header("Content-Type", "application/json")
                .body(request.toJSONString())
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .statusCode(201)
                .log().all();
    }

    @Test
    public void testLoginSuccessful() {
        RestAssured.baseURI = "https://reqres.in/api";
        RequestSpecification request = RestAssured.given();
        JSONObject requestBody = new JSONObject();
        requestBody.put("email", "eve.holt@reqres.in");
        requestBody.put("password", "pistol");
        request.header("Content-Type", "application/json");
        request.body(requestBody.toJSONString());

        Response response = request.post("/login");
        Assert.assertEquals(response.getStatusCode(), 200);
        String token = response.getBody().jsonPath().getString("token");
        System.out.println(token);
    }

    @Test
    public void testLoginUnsuccess(){
        RestAssured.baseURI = "https://reqres.in/api";
        RequestSpecification request = RestAssured.given();
        JSONObject requestBody = new JSONObject();
        final Object email = requestBody.put("email", "peter@klaven");
        request.header("Content-Type", "application/json");
        request.body(requestBody.toJSONString());

        Response response = request.post("/login");
        Assert.assertEquals(response.getStatusCode(), 400);
        String message = response.getBody().jsonPath().getString("error");
        System.out.println(message);
    }

    @Test
    public void testRegisterSuccess(){
        RestAssured.baseURI = "https://reqres.in/api";
        RequestSpecification request = RestAssured.given();
        JSONObject requestBody = new JSONObject();
        final Object email = requestBody.put("email", "eve.holt@reqres.in");
        requestBody.put("password", "pistol");
        request.header("Content-Type", "application/json");
        request.body(requestBody.toJSONString());

        Response response = request.post("/register");
        Assert.assertEquals(response.getStatusCode(), 200);
        String id = response.getBody().jsonPath().getString("id");
        String token = response.getBody().jsonPath().getString("token");
        System.out.println(id);
        System.out.println(token);
    }

    @Test
    public void testRegisterUnsuccess(){
        RestAssured.baseURI = "https://reqres.in/api";
        RequestSpecification request = RestAssured.given();
        JSONObject requestBody = new JSONObject();
        final Object email = requestBody.put("email", "sydney@fife");
        request.header("Content-Type", "application/json");
        request.body(requestBody.toJSONString());

        Response response = request.post("/register");
        Assert.assertEquals(response.getStatusCode(), 400);
        String message = response.getBody().jsonPath().getString("error");
        System.out.println(message);

    }


    @Test
    public void testDelay(){
        //request
        RestAssured.baseURI = "https://reqres.in/api";
        RequestSpecification request = RestAssured.given();
        JSONObject requestBody = new JSONObject();
        request.header("Content-Type", "application/json");
        request.body(requestBody.toJSONString());

        //response
        Response response = request.get("/user?delay=3");
        Assert.assertEquals(response.getStatusCode(), 200);
        String data = response.getBody().jsonPath().getString("data");
        System.out.println(response.getStatusCode());
        System.out.println(data);


    }



}
