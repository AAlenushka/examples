package soap;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.SocketException;

public class JsonControler {

    public static HttpResponse sendJSon(String url, String json) throws IOException, JSONException {

        HttpPost request = new HttpPost("https://api.rest-test-app.com/users/add"); // meyhod post

        StringEntity entity = new StringEntity(json);
        entity.setContentType("application/json;charset=UTF-8");
        entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8"));
        request.addHeader("Authorization", "Basic "); // if we need
        // authorization
        request.setEntity(entity);
        HttpResponse response = null;
        HttpClient httpclient = HttpClientBuilder.create().build();
        try {
            response = httpclient.execute(request);
            System.out.println(response);
            return response;
        } catch (SocketException se) {
            throw se;
        }
    }

    @Test
    @Parameters({"url"})
    public void simpleTestSendJson(@Optional String url) throws InterruptedException, IOException, JSONException {

// Create json
        String json = "{\"user1\": {\"name\": \"Jamse\",\"email\": \"main_window@mail.com\",\"age\": 50,}"
                + ",\"user2\": {\"name\": \"Bond\",\"email\":\"aston@mail.com\",\"age\": 49,},"
                + "      \"user3\": {\"name\": \"Weider\"\"email\": \"starwars@mail.com\",\"age\": 500,}}";

// Send json to api
        HttpResponse response = sendJSon(url, json);


//Compare result
        Assert.assertTrue(response.toString().contains("200"));
        System.out.println("DEBUG1:" + response);


    }

}

