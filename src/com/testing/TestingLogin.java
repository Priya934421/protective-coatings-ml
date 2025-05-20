package com.testing;

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

@WebServlet("/TestingLogin")
public class TestingLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try
		{
			PrintWriter out=response.getWriter();
			Connection con=Dbconn.getConectionn();
			
			String email=request.getParameter("email");
			String pwd=request.getParameter("password");
			
			String qry="select * from protectivetesting where empemail='"+email+"' and password='"+pwd+"' and empstatus='Accepted'";
			PreparedStatement ps=con.prepareStatement(qry);
			ResultSet rs=ps.executeQuery();
			
			if(rs.next())
			{
				out.print("<html><body><script>alert('Protective Testing Employee - Login SucessFul');</script></body></html>");
				RequestDispatcher dd=request.getRequestDispatcher("test.html");
				dd.include(request, response);
			}
			else
			{
				out.print("<html><body><script>alert('Protective Testing Employee - Login UnSucessFul');</script></body></html>");
				RequestDispatcher dd=request.getRequestDispatcher("index.html");
				dd.include(request, response);
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
