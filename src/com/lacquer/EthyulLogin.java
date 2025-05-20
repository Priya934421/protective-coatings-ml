package com.lacquer;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.connect.Dbconn;

@WebServlet("/EthyulLogin")
public class EthyulLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 try {
			 Connection con=Dbconn.getConectionn();
			 PrintWriter out=response.getWriter();
	            String email = request.getParameter("email");
	            String pwd = request.getParameter("password");
	            
	            String status = "Accepted";

	            String query = "SELECT * FROM lacquerregister WHERE empmail=? AND password=? AND empstatus=?";
	            PreparedStatement ps = con.prepareStatement(query);
	            ps.setString(1, email);
	            ps.setString(2, pwd);
	            ps.setString(3, status);

	            ResultSet rs = ps.executeQuery();

	            if (rs.next()) {
	                out.println("<head><script>alert('Lacqure Formation Employee - Login Successful');</script></head>");
	                RequestDispatcher dd = request.getRequestDispatcher("lacqure.html");
	                dd.include(request, response);
	            } else {
	     
	                out.println("<head><script>alert('Lacqure Formation Employee - Login unsuccessful');</script></head>");
	                RequestDispatcher dd = request.getRequestDispatcher("index.html");
	                dd.include(request, response);
	            }

	            // Close resources
	            rs.close();
	            ps.close();
	            con.close();
	        } catch (Exception e) {
	            // Handle exceptions
	            e.printStackTrace();
	        }	
	}

}
