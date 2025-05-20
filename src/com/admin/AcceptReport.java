package com.admin;

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


@WebServlet("/AcceptReport")
public class AcceptReport extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try
		{
			String id=request.getParameter("id");
			String rep=request.getParameter("rep");
			PrintWriter out=response.getWriter();
			String str="Accepted";
			Connection con=Dbconn.getConectionn();
			
			String name=request.getParameter("id");
			
			String sql="update wasteprocessresult set status='"+str+"' where client_id='"+id+"' and reportid='"+rep+"'";
			PreparedStatement ps=con.prepareStatement(sql);
			int x=ps.executeUpdate();
			
			if(x>0)
			{
				out.print("<html><body><script>alert('Waste Process Report - Accepted Sucessfully');</script></body></html>");
				RequestDispatcher dd=request.getRequestDispatcher("adminhome.html");
				dd.include(request, response);
			}
			else
			{
				out.print("<html><head><script>alert('Waste Process Report - Not Accepted');</script></body></html>");
				RequestDispatcher dd=request.getRequestDispatcher("adminhome.html");
				dd.include(request, response);
			}
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}

	}

}
