package lk.ijse.javaee_test.controller;

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
        //Lastly, remember to handle potential exceptions properly in a real implementation, such as catching and logging them or returning an appropriate HTTP error response.//
    }
}
