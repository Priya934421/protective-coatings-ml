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

@WebServlet("/AcceptLacqure")
public class AcceptLacqure extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try
		{
			PrintWriter out=response.getWriter();
			Connection con=Dbconn.getConectionn();
			
			String id=request.getParameter("id");
			String rep=request.getParameter("reportid");
			
			String sql="update ethuylamount set status='Accepted' where client_id='"+id+"' and reportid='"+rep+"'";
			PreparedStatement ps=con.prepareStatement(sql);
			int x=ps.executeUpdate();
			
			if(x>0)
			{
				out.println("<html><body><script>alert('Lacqure Formation Report - Accepted Sucessfully');</script></body></html>");
				RequestDispatcher rr=request.getRequestDispatcher("adminhome.html");
				rr.include(request, response);
			}
			else
			{
				out.println("<html><body><script>alert('Lacqure Formation Report - Accepted UnSucessfully');</script></body></html>");
				RequestDispatcher rr=request.getRequestDispatcher("adminhome.html");
				rr.include(request, response);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
