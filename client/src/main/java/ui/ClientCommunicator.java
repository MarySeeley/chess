package ui;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class ClientCommunicator {
  public <T> T get(String urlString, Class<T> responseClass, String headerName, String headerValue) throws IOException {
    T response = null;
    URL url = new URL(urlString);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setReadTimeout(5000);
    connection.setRequestMethod("GET");
    if(headerName != null && headerValue != null){
      connection.setRequestProperty(headerName, headerValue);
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

  public <T> T post(String urlString, Object request, Class<T> responseClass, String headerName, String headerValue) throws IOException {
    T response = null;
    URL url = new URL(urlString);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setReadTimeout(5000);
    connection.setRequestMethod("POST");
    connection.setDoOutput(true);
    if(headerName != null && headerValue != null){
      connection.setRequestProperty(headerName, headerValue);
    }
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

  public void delete(String urlString, String headerName, String headerValue) throws IOException{
    URL url = new URL(urlString);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("DELETE");
    if(headerName != null && headerValue != null){
      connection.setRequestProperty(headerName, headerValue);
    }
    connection.connect();

    int responseCode = connection.getResponseCode();
    if (responseCode != HttpURLConnection.HTTP_OK) {
      throw new IOException("Unexpected response code: " + responseCode +"\n"+"Error: " + connection.getInputStream());
    }
  }

  public void put(String urlString, Object request, String headerName, String headerValue) throws IOException{
    Object response = null;
    URL url = new URL(urlString);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setReadTimeout(5000);
    connection.setRequestMethod("PUT");
    connection.setDoOutput(true);
    if(headerName != null && headerValue != null){
      connection.setRequestProperty(headerName, headerValue);
    }
    if (request != null) {
      connection.addRequestProperty("Content-Type", "application/json");
      String reqData = new Gson().toJson(request);
      try (OutputStream reqBody = connection.getOutputStream()) {
        reqBody.write(reqData.getBytes());
      }
    }
    connection.connect();
    InputStream responseBody;
    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
      responseBody = connection.getInputStream();
    } else {
      throw new IOException(connection.getErrorStream().toString());
    }
  }
}
