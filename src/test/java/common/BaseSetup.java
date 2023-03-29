package common;

import constants.Constants;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.notNullValue;

public class BaseSetup {
    public static RequestSpecification request() {

        RequestSpecification request = new RequestSpecBuilder()
                .setBaseUri(Constants.URI)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addPathParam("key","07ec1d16a5acd368e1063a0e347ea112")
                .addPathParam("token","ATTAf866a358b4c7cc509c02dacdf2b9dd99ed583b5893f675c3cc432ab41a1c6d3d84CB3D1C")
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build().relaxedHTTPSValidation();
        return request;
    }

    public static ResponseSpecification response200() {

        ResponseSpecification response = new ResponseSpecBuilder().expectResponseTime(lessThan(15000L))
                .expectStatusCode(200)
                .expectBody(notNullValue())
                .build();
       return response;

    }
    public static ResponseSpecification response201() {

        ResponseSpecification response = new ResponseSpecBuilder().expectResponseTime(lessThan(15000L))
                .expectStatusCode(201)
                .expectBody(notNullValue())
                .build();
        return response;
    }

    public static ResponseSpecification response400() {

        ResponseSpecification response = new ResponseSpecBuilder().expectResponseTime(lessThan(15000L))
                .expectStatusCode(400)
                .expectBody(notNullValue())
                .build();
        return response;

    }

    public static ResponseSpecification response404() {

        ResponseSpecification response = new ResponseSpecBuilder().expectResponseTime(lessThan(15000L))
                .expectStatusCode(404)
                .expectBody(notNullValue())
                .build();
        return response;

    }

}

