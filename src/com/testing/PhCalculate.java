package com.testing;

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
import com.connect.Dbconn;

@WebServlet("/PhCalculate")
public class PhCalculate extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            PrintWriter out = response.getWriter();
            int rowsAffected = 0;
            String type = null;
            double res = 0.0;
            double adjustment = 0.0;
            double bufferCapacity = 0.0;
            double concentration = 0.0;
            double initialPH = 0.30;
            Connection con = Dbconn.getConectionn();

            String id = request.getParameter("id");
            String rep = request.getParameter("rep");

        
            double[] pHMinLimits = {7.3, 7.4, 7.7, 7.8, 7.9, 8.0, 8.1, 8.4}; 
            double[] pHMaxLimits = {7.4, 7.6, 7.8, 7.9, 8.0, 8.1, 8.4, Double.MAX_VALUE}; 
            String[] containers = {
                "Aluminum Food Pack",  
                "Frozen Food Packaging", 
                "Vacuum Sealed Pack",   
                "Ketchup Bottle",      
                "Glass Food Jar",       
                "Plastic Cup",          
                "Plastic Bottle",       
                "Microwave Tray"        
            };

            String qry = "SELECT DISTINCT type FROM ethuylamount WHERE client_id=? AND reportid=?";
            PreparedStatement ps = con.prepareStatement(qry);
            ps.setString(1, id);
            ps.setString(2, rep);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                type = rs.getString("type");

                String qry1 = "SELECT * FROM ph_level WHERE client_id=? AND type=?";
                PreparedStatement ps1 = con.prepareStatement(qry1);
                ps1.setString(1, id);
                ps1.setString(2, type);
                ResultSet rs1 = ps1.executeQuery();

                if (rs1.next()) {
                    bufferCapacity = Double.parseDouble(rs1.getString(4));
                    concentration = Double.parseDouble(rs1.getString(5));
                    initialPH = Double.parseDouble(rs1.getString(3));
                } else {
                    System.out.println("No data found for type: " + type);
                    continue;
                }

                switch (type) {
                    case "Cherry Tomatoes":
                        res = initialPH + adjustment * 1.1;
                        adjustment = Math.log((bufferCapacity + concentration) / bufferCapacity);
                        break;
                    case "Plum Tomatoes":
                        res = initialPH + adjustment * 1.2;
                        adjustment = Math.log((bufferCapacity + concentration) / bufferCapacity);
                        break;
                    case "Beefsteak Tomatoes":
                        res = initialPH + adjustment * 1.3;
                        adjustment = Math.log((bufferCapacity + concentration) / bufferCapacity);
                        break;
                    case "Roma Tomatoes":
                        res = initialPH + adjustment * 1.4;
                        adjustment = Math.log((bufferCapacity + concentration) / bufferCapacity);
                        break;
                    case "Grape Tomatoes":
                        res = initialPH + adjustment * 1.5;
                        adjustment = Math.log((bufferCapacity + concentration) / bufferCapacity);
                        break;
                    case "Heirloom Tomatoes":
                        res = initialPH + adjustment * 1.6;
                        adjustment = Math.log((bufferCapacity + concentration) / bufferCapacity);
                        break;
                    case "Campari Tomatoes":
                        res = initialPH + adjustment * 1.7;
                        adjustment = Math.log((bufferCapacity + concentration) / bufferCapacity);
                        break;
                    case "Cocktail Tomatoes":
                        res = initialPH + adjustment * 1.8;
                        adjustment = Math.log((bufferCapacity + concentration) / bufferCapacity);
                        break;
                    case "Beefmaster Tomatoes":
                        res = initialPH + adjustment * 1.9;
                        adjustment = Math.log((bufferCapacity + concentration) / bufferCapacity);
                        break;
                    case "Cherry Plum Tomatoes":
                        res = initialPH + adjustment * 2.0;
                        adjustment = Math.log((bufferCapacity + concentration) / bufferCapacity);
                        break;
                    default:
                        res = initialPH + adjustment;
                        adjustment = Math.log((bufferCapacity + concentration) / bufferCapacity);
                        break;
                }

                String suitableContainer = determineContainer(pHMinLimits, pHMaxLimits, containers, res);

                String checkQry = "SELECT COUNT(*) FROM ph_result WHERE client_id=? AND reportid=? AND type=?";
                PreparedStatement checkPs = con.prepareStatement(checkQry);
                checkPs.setString(1, id);
                checkPs.setString(2, rep);
                checkPs.setString(3, type);
                ResultSet checkRs = checkPs.executeQuery();
                checkRs.next();
                int count = checkRs.getInt(1);

                if (count == 0) {
                    String insertQry = "INSERT INTO ph_result (client_id, reportid, type, buffer, initialph, concentration, result, container) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement insertPs = con.prepareStatement(insertQry);
                    insertPs.setString(1, id);
                    insertPs.setString(2, rep);
                    insertPs.setString(3, type);
                    insertPs.setString(4, String.valueOf(bufferCapacity));
                    insertPs.setString(5, String.valueOf(initialPH));
                    insertPs.setString(6, String.valueOf(concentration));
                    insertPs.setString(7, String.valueOf(res));
                    insertPs.setString(8, suitableContainer); 
                    rowsAffected += insertPs.executeUpdate();
                }
            }

            if (rowsAffected > 0) {
                out.print("<html><body><script>alert('PH Value Calculated and Suitable Container Determined - Successful');</script></body></html>");
                RequestDispatcher dd = request.getRequestDispatcher("test.html");
                dd.include(request, response);
            } else {
                out.print("<html><body><script>alert('PH Value Calculation or Container Determination Failed');</script></body></html>");
                RequestDispatcher dd = request.getRequestDispatcher("test.html");
                dd.include(request, response);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String determineContainer(double[] minLimits, double[] maxLimits, String[] containers, double pHLevel) {
        for (int i = 0; i < minLimits.length; i++) {
            if (pHLevel >= minLimits[i] && pHLevel < maxLimits[i]) {
                return containers[i];
            }
        }
        return "Any Products"; 
    }
}
