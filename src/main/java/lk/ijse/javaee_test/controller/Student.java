package lk.ijse.javaee_test.controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lk.ijse.javaee_test.dto.StudentDTO;
import lk.ijse.javaee_test.model.StudentModel;
import lk.ijse.javaee_test.validation.StudentValidation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Student extends HttpServlet {
    private Connection con;

    @Override
    public void init() throws ServletException {
        try {
            Class.forName(getServletContext().getInitParameter("mysql-driver"));
            con = DriverManager.getConnection(getServletContext().getInitParameter("mysql-url"),
                    getServletContext().getInitParameter("user-name"),
                    getServletContext().getInitParameter("password"));

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String origin = req.getHeader("Origin");
        if (origin.contains("")){
            resp.setHeader("Access-Control-Allow-Origin",origin);
            resp.setHeader("Access-Control-Allow-Method","GET,POST,PUT,DELETE,HEADER");
            resp.setHeader("Access-Control-Allow-Headers","Content-Type");
            //resp.setHeader("Access-Control-Expose-");
        }
        //The provided code seems to be a Java method that handles the HTTP OPTIONS request method, which is used for cross-origin resource sharing (CORS) preflight requests. CORS is a security feature implemented by web browsers to restrict web pages from making requests to a different domain than the one that served the web page.
        //
        //The purpose of this method is to handle the OPTIONS request and set the appropriate CORS headers to allow cross-origin requests from specific origins with certain HTTP methods and headers.
        //
        //Let's break down the code and understand what it does:
        //
        //1. `protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {`: This method is declared as "protected," meaning it can only be accessed within the class it belongs to or its subclasses. It handles the HTTP OPTIONS request method.
        //
        //2. `String origin = req.getHeader("Origin");`: It retrieves the value of the "Origin" header from the incoming HttpServletRequest object. The "Origin" header contains the domain of the requesting client.
        //
        //3. `if (origin.contains("")) {`: This condition checks if the "Origin" header contains an empty string. However, the condition seems to be incomplete and lacks a value to check against, which might result in unexpected behavior. The intention here might have been to check if the "origin" is valid and not null.
        //
        //4. `resp.setHeader("Access-Control-Allow-Origin", origin);`: It sets the "Access-Control-Allow-Origin" response header, allowing requests from the specified origin. In this case, it allows requests from the same origin that was sent in the request.
        //
        //5. `resp.setHeader("Access-Control-Allow-Method", "GET,POST,PUT,DELETE,HEADER");`: It sets the "Access-Control-Allow-Method" response header, indicating the HTTP methods that are allowed for cross-origin requests from the specified origin. The listed methods are GET, POST, PUT, DELETE, and HEADER.
        //
        //6. `resp.setHeader("Access-Control-Allow-Headers", "Content-Type");`: It sets the "Access-Control-Allow-Headers" response header, specifying the allowed request headers for cross-origin requests from the specified origin. In this case, it only allows the "Content-Type" header to be included in the request.
        //
        //7. `//resp.setHeader("Access-Control-Expose-");`: This line appears to be commented out and doesn't have any functionality. The "Access-Control-Expose-Headers" header can be used to specify which response headers should be exposed to the client. However, it is incomplete in the code snippet.
        //
        //It's worth noting that the "Access-Control-Allow-Origin" header with a specific origin allows requests from that origin only. If you want to allow requests from any origin, you can set it to "*" (not recommended unless necessary due to security reasons).
        //
        //Additionally, the "Access-Control-Allow-Credentials" header can be set to "true" if the server needs to allow credentials (e.g., cookies) to be sent with the cross-origin request. In that case, the client-side XMLHttpRequest or Fetch request must set the `withCredentials` flag to `true` as well.
        //
        //Lastly, remember to handle potential exceptions properly in a real implementation, such as catching and logging them or returning an appropriate HTTP error response.

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("found");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getContentType()==null || !req.getContentType().toLowerCase().startsWith("application/json")){
            resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        }
        Jsonb jsonb = JsonbBuilder.create();
        StudentDTO studentObj = jsonb.fromJson(req.getReader(), StudentDTO.class);
        //validation
        boolean b = StudentValidation.studentValidation(studentObj);
        if (b){
            try {
                //dbmangement
                int i = StudentModel.SaveStudent(studentObj, con);
                if (i !=1){
                    throw new RuntimeException("save failed");
                }else {
                    System.out.println("saved sucessfully");
                }
                resp.setStatus(HttpServletResponse.SC_CREATED);
                //the created json is sent to frontend
                resp.setContentType("application/json");
                jsonb.toJson(studentObj,resp.getWriter());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    //
   // The provided code appears to be a Java method that handles the HTTP POST request. It seems to receive JSON data from the request, validate it, and then save it to a database if the validation is successful. Let's go through the code step by step:

     //       if(req.getContentType()==null || !req.getContentType().toLowerCase().startsWith("application/json")): This line checks whether the request has a "Content-Type" header that starts with "application/json". This is to ensure that the incoming data is in JSON format. If the condition is not met, the method sends an HTTP 405 (Method Not Allowed) error response back to the client using resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);. This prevents the server from processing requests with an invalid or missing "Content-Type" header.

       //     Jsonb jsonb = JsonbBuilder.create();: It creates a JSON-B (Java API for JSON Binding) object, which provides functionality for converting Java objects to JSON and vice versa.

  //  StudentDTO studentObj = jsonb.fromJson(req.getReader(), StudentDTO.class);: This line uses JSON-B to deserialize the JSON data from the request's reader stream into a StudentDTO object. It assumes that the JSON data in the request body corresponds to the StudentDTO class, which is likely a data transfer object representing a student.

   // boolean b = StudentValidation.studentValidation(studentObj);: The code calls a static method studentValidation from the StudentValidation class to validate the studentObj. If the validation returns true, it means the validation was successful.

         //   if (b) { ... }: This block of code executes if the validation is successful.

   // int i = StudentModel.SaveStudent(studentObj, con);: It calls the static method SaveStudent from the StudentModel class to save the validated studentObj to the database using a connection object (con). The exact implementation of the database management is not visible here, but it's evident that it is attempting to save the student data.

         //   if (i !=1) { ... } else { ... }: This conditional block checks the result of the database operation. If i is not equal to 1 (indicating that the save operation did not affect one row, as expected), a RuntimeException is thrown with the message "save failed". Otherwise, the code prints "saved successfully".

          //  resp.setStatus(HttpServletResponse.SC_CREATED);: If everything goes well, the response status is set to HTTP 201 (Created) to indicate that the request was successful, and a new resource was created.

         //   resp.setContentType("application/json");: The response's "Content-Type" header is set to "application/json" to indicate that the response contains JSON data.

         //   jsonb.toJson(studentObj, resp.getWriter());: JSON-B is used again to serialize the studentObj back into JSON format and write it to the response's writer stream, so it can be sent back to the client.


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getContentType()==null || !req.getContentType().toLowerCase().startsWith("application/json")){
            resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        }
        Jsonb jsonb = JsonbBuilder.create();
        StudentDTO studentObj = jsonb.fromJson(req.getReader(), StudentDTO.class);
        //validation
        boolean b = StudentValidation.studentValidation(studentObj);
        if (b){
            try {
                //dbmangement
                int i = StudentModel.SaveStudent(studentObj, con);
                if (i !=1){
                    throw new RuntimeException("save failed");
                }else {
                    System.out.println("saved sucessfully");
                }
                resp.setStatus(HttpServletResponse.SC_CREATED);
                //the created json is sent to frontend
                resp.setContentType("application/json");
                jsonb.toJson(studentObj,resp.getWriter());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    //if(req.getContentType()==null || !req.getContentType().toLowerCase().startsWith("application/json")): This line checks whether the request has a "Content-Type" header that starts with "application/json". As in the doPost method, this is to ensure that the incoming data is in JSON format. If the condition is not met, the method sends an HTTP 405 (Method Not Allowed) error response back to the client using resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);. This prevents the server from processing requests with an invalid or missing "Content-Type" header.
    //
    //Jsonb jsonb = JsonbBuilder.create();: It creates a JSON-B (Java API for JSON Binding) object, which provides functionality for converting Java objects to JSON and vice versa.
    //
    //StudentDTO studentObj = jsonb.fromJson(req.getReader(), StudentDTO.class);: This line uses JSON-B to deserialize the JSON data from the request's reader stream into a StudentDTO object. It assumes that the JSON data in the request body corresponds to the StudentDTO class, which is likely a data transfer object representing a student.
    //
    //boolean b = StudentValidation.studentValidation(studentObj);: The code calls a static method studentValidation from the StudentValidation class to validate the studentObj. If the validation returns true, it means the validation was successful.
    //
    //if (b) { ... }: This block of code executes if the validation is successful.
    //
    //int i = StudentModel.SaveStudent(studentObj, con);: It calls the static method SaveStudent from the StudentModel class to save the validated studentObj to the database using a connection object (con). Just like in the doPost method, it seems that this method is intended to save student data.
    //
    //if (i !=1) { ... } else { ... }: This conditional block checks the result of the database operation. If i is not equal to 1 (indicating that the save operation did not affect one row, as expected), a RuntimeException is thrown with the message "save failed". Otherwise, the code prints "saved successfully".
    //
    //resp.setStatus(HttpServletResponse.SC_CREATED);: If everything goes well, the response status is set to HTTP 201 (Created) to indicate that the request was successful, and a new resource was created.
    //
    //resp.setContentType("application/json");: The response's "Content-Type" header is set to "application/json" to indicate that the response contains JSON data.
    //
    //jsonb.toJson(studentObj, resp.getWriter());: JSON-B is used again to serialize the studentObj back into JSON format and write it to the response's writer stream so it can be sent back to the client.
}


