package com.lipid;

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

@WebServlet("/LipidRegister")
public class LipidRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try
		{
			PrintWriter out=response.getWriter();
			int min = 10000; 
		    int max = 99999; 
		    int ss = (int)Math.floor(Math.random() * (max - min + 1) + min);
			
			Connection con=Dbconn.getConectionn();
			
			String name=request.getParameter("lipname");
			String email=request.getParameter("lipemail");
			String number=request.getParameter("lipnumber");
			String id=request.getParameter("lipid");
			String status="Pending";
			String pwd=String.valueOf(ss);
			String le="LE-"+pwd;
			
			PreparedStatement ps=con.prepareStatement("insert into lipidregister (empname,empemail,empnumber,empid,empstatus,emppassword) values (?,?,?,?,?,?)");
			
			ps.setString(1, name);
			ps.setString(2, email);
			ps.setString(3, number);
			ps.setString(4, id);
			ps.setString(5, status);
			ps.setString(6, le);	
			
			int b=ps.executeUpdate();
			
			if(b>0)
			{
				out.print("<html><body><script>alert('Lipid Extraction Employee - Registered SucessFul');</script></body></html>");
				RequestDispatcher dd = request.getRequestDispatcher("index.html");
                dd.include(request, response);
			}
			else
			{
				out.print("<html><body><script>alert('Lipid Extraction Employee - Registered UnSucessFul');</script></body></html>");
				RequestDispatcher dd = request.getRequestDispatcher("index.html");
                dd.include(request, response);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
