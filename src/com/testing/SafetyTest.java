package com.testing;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.connect.Dbconn;

@WebServlet("/SafetyTest")
public class SafetyTest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DecimalFormat df = new DecimalFormat("#.##########");
		try
		{
			PrintWriter out=response.getWriter();
			  String safetyLevel=null,type2=null;
			int consumption=0,weight=0,x=0;
			String status="pending";
			double safety=0.0,mres=0.0,eres=0.0,sres=0.0,migration=0.0,exposure=0.0,area=0.0,result=0.0;
			String id=request.getParameter("id");
			String rep=request.getParameter("rep");
			Connection con=Dbconn.getConectionn();
			
			String qry="select * from ph_result where client_id='"+id+"' and reportid='"+rep+"'";
			PreparedStatement ps=con.prepareStatement(qry);
			
			ResultSet rs=ps.executeQuery();
			String q="select * from migration_ratio where client_id='"+id+"'";
			PreparedStatement ps1=con.prepareStatement(q);
			
			ResultSet rs1=ps1.executeQuery();
				while(rs1.next())
				{
					type2=rs1.getString(2);
					result=Double.parseDouble(rs1.getString(3));
					area=Double.parseDouble(rs1.getString(4));
					exposure=Double.parseDouble(rs1.getString(5));
					migration=Double.parseDouble(rs1.getString(6));
					consumption=Integer.parseInt(rs1.getString(7));
					weight=Integer.parseInt(rs1.getString(8));
					safety=Double.parseDouble(rs1.getString(9));
					
					 switch (type2) {
	                    case "Cherry Tomatoes":
	                    	mres = result / (area * exposure);
	                    	eres = mres * consumption / weight;
	                        break;
	                    case "Plum Tomatoes":
	                    	mres = result / (area * exposure);
	                    	eres = mres * consumption / weight;     
	                        break;
	                    case "Beefsteak Tomatoes":
	                    	mres = result / (area * exposure);
	                    	eres = mres * consumption / weight;
	                        break;
	                    case "Roma Tomatoes":
	                    	mres = result / (area * exposure);
	                    	eres = mres * consumption / weight;
	                        break;
	                    case "Grape Tomatoes":
	                    	mres = result / (area * exposure);
	                    	eres = mres * consumption / weight;
	                        break;
	                    case "Heirloom Tomatoes":
	                    	mres = result / (area * exposure);
	                    	eres = mres * consumption / weight;
	                        break;
	                    case "Campari Tomatoes":
	                    	mres = result / (area * exposure);
	                    	eres = mres * consumption / weight;
	                        break;
	                    case "Cocktail Tomatoes":
	                    	mres = result / (area * exposure);
	                    	eres = mres * consumption / weight;
	                        break;
	                    case "Beefmaster Tomatoes":
	                    	mres = result / (area * exposure);
	                    	eres = mres * consumption / weight;
	                        break;
	                    case "Cherry Plum Tomatoes":
	                    	mres = result / (area * exposure);
	                    	eres = mres * consumption / weight;
	                        break;
	                    default:
	                    	mres = result / (area * exposure);
	                    	eres = mres * consumption / weight;
	                        break;
	                }
					 if (area != 0 && exposure != 0) {
	                        safety = (result * exposure) / (area * consumption);
	                    } else {
	                        safety = 0.0;
	                    }
	                    String formattedSafety = df.format(safety);
	                    double sf = Double.parseDouble(formattedSafety);
	                  
	                    if (sf >= 0.0003) { 
	                        safetyLevel = "High";
	                    } else if (sf >= 0.0001) {  
	                        safetyLevel = "Moderate";
	                    } else { 
	                        safetyLevel = "Low";
	                    }

	                    String ins="insert into migration_result (client_id,reportid,type,migration,exposure,safetylevel,safety,status) values(?,?,?,?,?,?,?,?)";
	    				PreparedStatement res=con.prepareStatement(ins);
	    				res.setString(1, id);
	    				res.setString(2, rep);
	    				res.setString(3, type2);
	    				res.setString(4, String.valueOf(df.format(mres)));
	    				res.setString(5, String.valueOf(df.format(eres)));
	    				res.setString(6, String.valueOf(df.format(safety)));
	    				res.setString(7, safetyLevel);
	    				res.setString(8, status);
	    				x=res.executeUpdate();}	
				if(x>0)
				{
					out.print("<html><body><script>alert('Safety Level Calculated - Sucessful')</script></body><html>");
					RequestDispatcher dd=request.getRequestDispatcher("test.html");
					dd.include(request, response);
				}
				else {
					out.print("<head><script>alert('Safety Level Calculated - unsuccessful');</script></head>");
					RequestDispatcher dd=request.getRequestDispatcher("test.html");
					dd.include(request, response);
				}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
