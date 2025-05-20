package com.lacquer;

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

@WebServlet("/LacquerRegister")
public class LacquerRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try
		{
			PrintWriter out=response.getWriter();
			int min = 10000; 
		    int max = 99999; 
		    int ss = (int)Math.floor(Math.random() * (max - min + 1) + min);
			
			Connection con=Dbconn.getConectionn();
			String name=request.getParameter("lename");
			String email=request.getParameter("leemail");
			String mobile=request.getParameter("lenumber");
			String id=request.getParameter("lepid");
			String status="Pending";
			String pwd=String.valueOf(ss);
			String we="LA-"+pwd;
			
			PreparedStatement st=con.prepareStatement("insert into lacquerregister (empname,empmail,empnumber,empid,empstatus,password) values(?,?,?,?,?,?)");
			
			st.setString(1, name);
			st.setString(2, email);
			st.setString(3, mobile);
			st.setString(4, id);
			st.setString(5, status);
			st.setString(6, we);
			
			int c=st.executeUpdate();
			
			if(c>0)
			{
				out.print("<html><body><script>alert('Lacquer Formation Employee - Registered SucessFul');</script></body></html>");
				RequestDispatcher rs=request.getRequestDispatcher("index.html");
				rs.include(request,response);
			}
			else
			{
				out.print("<html><body><script>alert('Lacquer Formation Employee - Registered UnSucessFul');</script></body></html>");
				RequestDispatcher rs=request.getRequestDispatcher("index.html");
				rs.include(request,response);
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		
	}

}
