package com.waste.process;

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


@WebServlet("/WasteLogin")
public class WasteLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try
		{
			Connection con=Dbconn.getConectionn();
			
			String email=request.getParameter("email");
			String pwd=request.getParameter("password");
			String c = "Accepted";
			PrintWriter out = response.getWriter(); 
			
			   String qry = "SELECT * FROM wasteresigter WHERE emp_mail=? AND password=? AND emp_status=?";
		        PreparedStatement ps = con.prepareStatement(qry);
		        ps.setString(1, email);
		        ps.setString(2, pwd);
		        ps.setString(3, c);

		        ResultSet rs = ps.executeQuery();
			
			if(rs.next())  {
				out.print("<head><script>alert('Waste Process Employee - Login Successful');</script></head>");
				RequestDispatcher dd=request.getRequestDispatcher("wastehome.html");
				dd.include(request, response);
			}else {
				out.print("<head><script>alert('Waste Process Employee - Login unsuccessful');</script></head>");
				RequestDispatcher dd=request.getRequestDispatcher("index.html");
				dd.include(request, response);
			}
			}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
