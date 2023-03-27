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
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build().relaxedHTTPSValidation();

        return request;
    }
    public static ResponseSpecification response200() {

        ResponseSpecification response = new ResponseSpecBuilder().expectResponseTime(lessThan(5000L))
                .expectStatusCode(200)
                .expectBody(notNullValue())
                .build();
       return response;

    }
    public static ResponseSpecification response201() {

        ResponseSpecification response = new ResponseSpecBuilder().expectResponseTime(lessThan(5000L))
                .expectStatusCode(201)
                .expectBody(notNullValue())
                .build();
        return response;
    }

    public static ResponseSpecification response400() {

        ResponseSpecification response = new ResponseSpecBuilder().expectResponseTime(lessThan(5000L))
                .expectStatusCode(400)
                .expectBody(notNullValue())
                .build();
        return response;

    }

    public static ResponseSpecification response404() {

        ResponseSpecification response = new ResponseSpecBuilder().expectResponseTime(lessThan(5000L))
                .expectStatusCode(404)
                .expectBody(notNullValue())
                .build();
        return response;

    }

}

