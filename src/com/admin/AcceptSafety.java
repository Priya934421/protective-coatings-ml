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


@WebServlet("/AcceptSafety")
public class AcceptSafety extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try
		{
			PrintWriter out=response.getWriter();
			Connection con=Dbconn.getConectionn();
			String id=request.getParameter("id");
			String rep=request.getParameter("reportId");
			
			String sql="update migration_result set status='Accepted' where client_id='"+id+"' and reportid='"+rep+"'";
			
			PreparedStatement ps=con.prepareStatement(sql);
			
			int y=ps.executeUpdate();
			
			if(y>0)
			{
				out.print("<html><body><script>alert('Protective Testing Report - Accepted Sucessfully');</script></body></html>");
				RequestDispatcher dd=request.getRequestDispatcher("adminhome.html");
				dd.include(request, response);
			}
			else {
				out.print("<head><script>alert('Protective Testing Report - Not Accepted');</script></head>");
				RequestDispatcher dd=request.getRequestDispatcher("adminhome.html");
				dd.include(request, response);
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
