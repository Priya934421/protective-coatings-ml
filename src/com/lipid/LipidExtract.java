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
import javax.servlet.http.HttpSession;
import java.util.Set;
import java.util.HashSet;
import com.connect.Dbconn;

@WebServlet("/LipidExtract")
public class LipidExtract extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try
		{
			PrintWriter out=response.getWriter();
			Connection con=Dbconn.getConectionn();
			String id=request.getParameter("id");
			String cid=request.getParameter("cid");
			String type=request.getParameter("type");
			String p=request.getParameter("powder");
			String h=request.getParameter("heat");
			String s=request.getParameter("sodium");
			
			HttpSession session = request.getSession();
		    Set<String> disabledTomatoTypes = (Set<String>) session.getAttribute("disabledTomatoTypes");
		    if (disabledTomatoTypes == null) {
		    	disabledTomatoTypes = new HashSet<>();
		        session.setAttribute("disabledTomatoTypes", disabledTomatoTypes);
		    }
		
			double sodium=Double.parseDouble(s);
	
			double lipidamount=sodium*0.75;
			
			String sql="insert into lipidresult (client_id,reportid,type,powder,heat,sodium,lipidamount) values (?,?,?,?,?,?,?)";
			
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, cid);
			ps.setString(3, type);
			ps.setString(4, p);
			ps.setString(5, h);
			ps.setString(6, s);
			ps.setString(7, String.valueOf(lipidamount));
			
			int r=ps.executeUpdate();
			
			disabledTomatoTypes.add(type);
			out.println("<html>");
            out.println("<head>");
            out.println("<script>");
            out.println("window.onload = function() {");
            if (r > 0) {
                out.println("    alert('Lipid Extracted - Successfully');");
            } else {
                out.println("    alert('Lipid Extraction Unsuccessful');");
            }
            out.println("    window.location.href = 'sodiummix1.jsp';"); // Redirect after alert
            out.println("};");
            out.println("</script>");
            out.println("</head>");
            out.println("<body>");
            out.println("</body>");
            out.println("</html>");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
