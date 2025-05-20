package com.lipid;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.connect.Dbconn;

@WebServlet("/UserLogin")
public class UserLogin extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        try {
            Connection con = Dbconn.getConectionn();
            if (con == null) {
                throw new SQLException("Failed to establish connection to the database.");
            }

            String email = request.getParameter("email");
            String pwd = request.getParameter("password");
            
            String status = "Accepted";

            String query = "SELECT * FROM lipidregister WHERE empemail=? AND emppassword=? AND empstatus=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, pwd);
            ps.setString(3, status);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                out.println("<head><script>alert('Lipid Extraction Employee - Login Successful');</script></head>");
                RequestDispatcher dd = request.getRequestDispatcher("sodiumh.html");
                dd.include(request, response);
            } else {
     
                out.println("<head><script>alert('Lipid Extraction Employee - Login unsuccessful');</script></head>");
                RequestDispatcher dd = request.getRequestDispatcher("index.html");
                dd.include(request, response);
            }

            // Close resources
            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace(); // Log the exception
            out.println("<head><script>alert('Error: Failed to process login. Please try again later.');</script>");
            RequestDispatcher dd = request.getRequestDispatcher("index.html");
            dd.include(request, response);
        }
    }
}
