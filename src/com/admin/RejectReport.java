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

@WebServlet("/RejectReport")
public class RejectReport extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try
		{
			PrintWriter out=response.getWriter();
			Connection con=Dbconn.getConectionn();
			
			String name=request.getParameter("productName");
			
			String status="Rejected";
			
			String qry="update wasteprocessresult set status='"+status+"' where type='"+name+"'";
			
			PreparedStatement ps=con.prepareStatement(qry);
			
			int x=ps.executeUpdate();
			
			if(x>0)
			{
				out.print("<html><body><script>alert('Report Rejected');</script></head></body>");
				RequestDispatcher dd=request.getRequestDispatcher("adminhome.html");
				dd.include(request, response);
			}else
			{
				out.print("<html><body><script>alert('Report Not Rejected');</script></head></body>");
				RequestDispatcher dd=request.getRequestDispatcher("adminhome.html");
				dd.include(request, response);
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}

	}

}
