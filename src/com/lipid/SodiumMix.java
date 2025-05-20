package com.lipid;

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

@WebServlet("/SodiumMix")
public class SodiumMix extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        Connection con = null;
        PreparedStatement ps = null, ps1 = null, ps3 = null;
        ResultSet rs = null, rs1 = null;
        String status="Pending";

        try {
            String id = request.getParameter("id");
            String rep = request.getParameter("rep");
            con = Dbconn.getConectionn();

            String qry = "SELECT DISTINCT * FROM wasteprocessresult WHERE client_id = ? AND reportid = ?";
            ps = con.prepareStatement(qry);
            ps.setString(1, id);
            ps.setString(2, rep);
            rs = ps.executeQuery();

            boolean success = false;

            while (rs.next()) {
                String type = rs.getString("type");
                String powder = rs.getString("powder");
                double pow = Double.parseDouble(powder);


                String qry1 = "SELECT heat_temo, time, sodium FROM sodiumamount WHERE type = ?";
                ps1 = con.prepareStatement(qry1);
                ps1.setString(1, type);
                rs1 = ps1.executeQuery();

                if (rs1.next()) {
                    int temp = rs1.getInt("heat_temo");
                    int t = rs1.getInt("time");
                    int amount = rs1.getInt("sodium");

                    double sodiumHydroxideAmount = (temp + t) / amount + pow;

             
                    String insertQry = "INSERT INTO sodiumresult(client_id, reportid, type, quantity, heat, sodiumquantity,status) VALUES (?, ?, ?, ?, ?, ?,?)";
                    ps3 = con.prepareStatement(insertQry);
                    ps3.setString(1, id);
                    ps3.setString(2, rep);
                    ps3.setString(3, type);
                    ps3.setString(4, powder);
                    ps3.setString(5, String.valueOf(temp));
                    ps3.setString(6, String.valueOf(sodiumHydroxideAmount));
                    ps3.setString(7, status);
                    int result = ps3.executeUpdate();
                    if (result > 0) {
                        success = true;
                    }
                }
            }

            if (success) {
                out.print("<html><body><script>alert('Sodium Calculated - Successfully');</script></body></html>");
                RequestDispatcher f=request.getRequestDispatcher("sodiumh.html");
				f.include(request, response);
            } else {
                out.print("<html><body><script>alert('Sodium Calculated - Unsuccessfully');</script></body></html>");
                RequestDispatcher f=request.getRequestDispatcher("sodiumh.html");
				f.include(request, response);
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher("sodiumh.html");
            dispatcher.include(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            RequestDispatcher dispatcher = request.getRequestDispatcher("sodiumh.html");
            dispatcher.include(request, response);
        } finally {
            try {
                if (rs != null) rs.close();
                if (rs1 != null) rs1.close();
                if (ps != null) ps.close();
                if (ps1 != null) ps1.close();
                if (ps3 != null) ps3.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
