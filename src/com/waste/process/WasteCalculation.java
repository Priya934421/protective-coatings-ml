package com.waste.process;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.connect.Dbconn;

@WebServlet("/WasteCalculation")
public class WasteCalculation extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
        	String id=request.getParameter("clientId");
        	PrintWriter out=response.getWriter();
        	float powderWeight=0;
        	int heatingTemperature = 0;
            float dryingRate = 0.0f;
            int dryingTime = 16;
            int min = 10000; 
		    int max = 99999; 
		    String status="Pending";
		    int ss = (int)Math.floor(Math.random() * (max - min + 1) + min);
		    String ordid="WPR"+ss;
		    
            Connection con = Dbconn.getConectionn();
            String sql = "SELECT * FROM wasteprocess";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
          
            String insertSql = "INSERT INTO wasteprocessresult (client_id,reportid,type,totalpomace,initial_moisture,remaining_moisture, powder,status) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement insertPs = con.prepareStatement(insertSql);

            while (rs.next()) {
                String tomatoType = rs.getString("type");
                int seeds = rs.getInt("seeds");
                int pulp = rs.getInt("pulp");
                int skin = rs.getInt("skin");

                int wetWeight = seeds + pulp + skin;

                int dryWeight = 10; 

                float initialMoistureContent = ((float) (wetWeight - dryWeight) / wetWeight) * 100;

                
                if(tomatoType.equalsIgnoreCase("Cherry Tomatoes")) {
                    heatingTemperature = 60;
                    dryingRate = 0.05f;
                } else if(tomatoType.equalsIgnoreCase("Plum Tomatoes")) {
                    heatingTemperature = 70;
                    dryingRate = 0.06f;
                } else if(tomatoType.equalsIgnoreCase("Beefsteak Tomatoes")) {
                    heatingTemperature = 80;
                    dryingRate = 0.07f;
                } else if(tomatoType.equalsIgnoreCase("Roma Tomatoes")) {
                    heatingTemperature = 65;
                    dryingRate = 0.05f;
                } else if(tomatoType.equalsIgnoreCase("Grape Tomatoes")) {
                    heatingTemperature = 60;
                    dryingRate = 0.05f;
                } else if(tomatoType.equalsIgnoreCase("Heirloom Tomatoes")) {
                    heatingTemperature = 75;
                    dryingRate = 0.06f;
                } else if(tomatoType.equalsIgnoreCase("Campari Tomatoes")) {
                    heatingTemperature = 70;
                    dryingRate = 0.06f;
                } else if(tomatoType.equalsIgnoreCase("Cocktail Tomatoes")) {
                    heatingTemperature = 65;
                    dryingRate = 0.05f;
                } else if(tomatoType.equalsIgnoreCase("Beefmaster Tomatoes")) {
                    heatingTemperature = 80;
                    dryingRate = 0.07f;
                } else if(tomatoType.equalsIgnoreCase("Cherry Plum Tomatoes")) {
                    heatingTemperature = 65;
                    dryingRate = 0.05f;
                }
                else
                {
                	heatingTemperature = 60;
                    dryingRate = 0.05f;
                }
                
                float remainingMoistureContent = initialMoistureContent * (float) Math.pow((1 - dryingRate), dryingTime);
                powderWeight = wetWeight * (1 - remainingMoistureContent / 100);
                insertPs.setString(1, id);
                insertPs.setString(2, ordid);
                insertPs.setString(3, tomatoType);
                insertPs.setString(4, String.valueOf(wetWeight));
                insertPs.setString(5, String.valueOf(initialMoistureContent));
                insertPs.setString(6, String.valueOf(remainingMoistureContent));
                insertPs.setString(7, String.valueOf(powderWeight));
                insertPs.setString(8, status);
                insertPs.executeUpdate();
                }
            int i = insertPs.executeUpdate();
            if(i>0) {
				out.print("<head><script>alert('Calculation Done - successfully');</script></head>");
				RequestDispatcher dd=request.getRequestDispatcher("wastehome.html");
				dd.include(request, response);
			}
			else {
				out.print("<head><script>alert('Calculation Done - unsuccessful');</script></head>");
				RequestDispatcher dd=request.getRequestDispatcher("wastehome.html");
				dd.include(request, response);
			}} catch (Exception e) {
            e.printStackTrace();
        }
    }
}
