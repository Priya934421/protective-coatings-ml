package com.testing;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.connect.Dbconn;

@WebServlet("/TestingRegister")
public class TestingRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try
		{
			PrintWriter out=response.getWriter();
			int min = 10000; 
		    int max = 99999; 
		    int ss = (int)Math.floor(Math.random() * (max - min + 1) + min);
			
			Connection con=Dbconn.getConectionn();
			String name=request.getParameter("tname");
			String email=request.getParameter("temail");
			String mobile=request.getParameter("tnumber");
			String id=request.getParameter("tid");
			String status="Pending";
			String pwd=String.valueOf(ss);
			String we="PT-"+pwd;
			
			PreparedStatement st=con.prepareStatement("insert into protectivetesting (empname,empemail,empnumber,empid,empstatus,password) values(?,?,?,?,?,?)");
			
			st.setString(1, name);
			st.setString(2, email);
			st.setString(3, mobile);
			st.setString(4, id);
			st.setString(5, status);
			st.setString(6, we);
			
			int c=st.executeUpdate();
			
			if(c>0)
			{
				out.print("<html><body><script>alert('Protective Testing Employee - Registerd SucessFul');</script></body></html>");
				RequestDispatcher dd=request.getRequestDispatcher("index.html");
				dd.include(request, response);
			}
			else
			{
				out.print("<html><body><script>alert('Protective Testing Employee - Registered UnSucessFul');</script></body></html>");
				RequestDispatcher dd=request.getRequestDispatcher("index.html");
				dd.include(request, response);
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		
	}

}
