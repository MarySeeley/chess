package server;

import com.google.gson.Gson;
import exception.ResponseException;
import model.ExceptionData;
import spark.Request;
import spark.Response;
import spark.Route;

public class ExceptionHandler implements spark.ExceptionHandler<Exception> {


  @Override
  public void handle(Exception t, Request request, Response response) {
    response.status(500);
    response.body("Internal Server Error: " + t.getMessage());
  }
}
