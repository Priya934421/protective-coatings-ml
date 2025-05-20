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

@WebServlet("/RejectLipid")
public class RejectLipid extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		  try {
	            PrintWriter out = response.getWriter();
	           Connection con = Dbconn.getConectionn();

	            String id = request.getParameter("id");
	            String sql = "DELETE FROM lipidresult WHERE reportid = ?";
	            PreparedStatement ps=con.prepareStatement(sql);
	            ps = con.prepareStatement(sql);
	            ps.setString(1, id);

	            int rowsDeleted = ps.executeUpdate();

	            if (rowsDeleted > 0) {
	                out.print("<html><body><script>alert('Deleted Successfully');</script></body></html>");
	                RequestDispatcher rd = request.getRequestDispatcher("adminhome.html");
	                rd.include(request, response);
	            } else {
	                out.print("<html><body><script>alert('Deletion Failed');</script></body></html>");
	                RequestDispatcher rd = request.getRequestDispatcher("adminhome.html");
	                rd.include(request, response);
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        } 
	}

}
