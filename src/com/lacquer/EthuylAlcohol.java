package com.lacquer;

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

@WebServlet("/EthuylAlcohol")
public class EthuylAlcohol extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
        	int x=0;
        	String status="Pending";
        	PrintWriter out=response.getWriter();
            Connection con = Dbconn.getConectionn();

            String id = request.getParameter("id");
            String rep = request.getParameter("rep");

            String qry = "SELECT * FROM lipidresult WHERE client_id=? AND reportid=?";
            PreparedStatement ps = con.prepareStatement(qry);
            ps.setString(1, id);
            ps.setString(2, rep);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String type = rs.getString(3);
                double lipidAmount = Double.parseDouble(rs.getString(7));


                String qry2 = "SELECT * FROM ethylratio WHERE client_id=? AND type=?";
                PreparedStatement ps1 = con.prepareStatement(qry2);
                ps1.setString(1, id);
                ps1.setString(2, type);
                ResultSet rs1 = ps1.executeQuery();

                if (rs1.next()) {
                    String ratio = rs1.getString(3);
                    String exratio = rs1.getString(4);
                    String ph = rs1.getString(5);

                    double extractionEfficiency = Double.parseDouble(exratio);
                    String[] exactValue = ratio.split(":");
                    double numerator = Double.parseDouble(exactValue[0]);
                    double denominator = Double.parseDouble(exactValue[1]);


                    double ethanolVolumeNeeded = lipidAmount * (numerator / denominator);
         
                    double finalEthanolVolume = ethanolVolumeNeeded;

                    String insertQuery = "INSERT INTO ethuylamount (client_id, reportid, type, lipid, ratio, extractioneffiency, ph, extracted,status) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";
                    PreparedStatement ps3 = con.prepareStatement(insertQuery);
                    ps3.setString(1, id);
                    ps3.setString(2, rep);
                    ps3.setString(3, type);
                    ps3.setString(4, String.valueOf(lipidAmount));
                    ps3.setString(5, ratio);
                    ps3.setString(6, String.valueOf(extractionEfficiency));
                    ps3.setString(7, ph);
                    ps3.setString(8, String.valueOf(finalEthanolVolume));
                    ps3.setString(9, status);
                    x= ps3.executeUpdate();}
                
        }
            if(x>0) {
   				out.print("<head><script>alert('Ethyl Alcohol Calculation - Done successfully');</script></head>");
   				RequestDispatcher dd=request.getRequestDispatcher("lacqure.html");
   				dd.include(request, response);
            }
   			else {
   				out.print("<head><script>alert('Ethyl Alcohol Calculation - Not Done');</script></head>");
   				RequestDispatcher dd=request.getRequestDispatcher("index.html");
   				dd.include(request, response);
   			}}catch (Exception e) {
            e.printStackTrace();
        }
    }
}
