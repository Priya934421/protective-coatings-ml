package com.admin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AdminLogin")
public class AdminLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		try
		{
			PrintWriter out=response.getWriter();
			
			String name=request.getParameter("adminemail");
			String pwd=request.getParameter("adminpassword");
			
			if(name.equalsIgnoreCase("admin") && pwd.equalsIgnoreCase("admin"))
			{
				out.print("<html><body><script>alert('Admin login - sucessful');</script></body></html>");
				RequestDispatcher dd=request.getRequestDispatcher("adminhome.html");
				dd.include(request, response);
			}
			else
			{
				out.print("<html><body><script>alert('Admin login - unsucessFul');</script></body></html>");
				RequestDispatcher dd=request.getRequestDispatcher("index.html");
				dd.include(request, response);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
