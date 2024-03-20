package ui;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ClientCommunicator {
  public <T> T get(String urlString, Class<T> responseClass) throws IOException {
    T response = null;
    URL url = new URL(urlString);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setReadTimeout(5000);
    connection.setRequestMethod("GET");

    connection.connect();
    InputStream responseBody;
    if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
      responseBody = connection.getInputStream();
    }
    else{
      responseBody = connection.getErrorStream();
    }
    InputStreamReader reader = new InputStreamReader(responseBody);
    if (responseClass != null){
      response = new Gson().fromJson(reader, responseClass);
    }
    return response;
  }

  public <T> T post(String urlString, Object request, Class<T> responseClass) throws IOException {
    T response = null;
    URL url = new URL(urlString);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setReadTimeout(5000);
    connection.setRequestMethod("POST");
    connection.setDoOutput(true);
    if (request != null) {
      connection.addRequestProperty("Content-Type", "application/json");
      String reqData = new Gson().toJson(request);
      try (OutputStream reqBody = connection.getOutputStream()) {
        reqBody.write(reqData.getBytes());
      }
    }
    connection.connect();
    InputStream responseBody;
    if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
      responseBody = connection.getInputStream();
    }
    else{
      responseBody = connection.getErrorStream();
    }
    InputStreamReader reader = new InputStreamReader(responseBody);
    if (responseClass != null){
      response = new Gson().fromJson(reader, responseClass);
    }
    return response;
  }
}
