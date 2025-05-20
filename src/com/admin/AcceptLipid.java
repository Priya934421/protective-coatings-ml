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

@WebServlet("/AcceptLipid")
public class AcceptLipid extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try
		{
			PrintWriter out=response.getWriter();
			Connection con=Dbconn.getConectionn();
			
			String id=request.getParameter("id");
			String rep=request.getParameter("rep");
			
			 String sql = "update sodiumresult set status = 'Accepted' where reportid = ? and client_id=?";
			 
			 PreparedStatement ps=con.prepareStatement(sql);
	            ps = con.prepareStatement(sql);
	            ps.setString(1, rep);
	            ps.setString(2, id);
	            int rowsUpdated = ps.executeUpdate();
	            
	            if(rowsUpdated>0)
				{
					out.print("<html><body><script>alert('Lipid Extraction Report - Accepted Sucessfully');</script></body></html>");
					RequestDispatcher dd=request.getRequestDispatcher("adminhome.html");
					dd.include(request, response);
				}
				else
				{
					out.print("<html><head><script>alert('Lipid Extraction Report - Not Accepted');</script></body></html>");
					RequestDispatcher dd=request.getRequestDispatcher("adminhome.html");
					dd.include(request, response);
				}
				
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
