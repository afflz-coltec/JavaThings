/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
@WebServlet("/addContact")
public class AddContactServlet extends HttpServlet {
    
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        
        PrintWriter out = response.getWriter();
        
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        String addr = request.getParameter("addr");
        String email = request.getParameter("email");
        String charge = request.getParameter("charge");
        int workplace = Integer.parseInt(request.getParameter("workplace"));
        Date bdate = Date.valueOf(request.getParameter("bdate"));
        
        Contact c = new Contact();
        
        c.setFirstName(fname);
        c.setLastName(lname);
        c.setAddr(addr);
        c.setEmail(email);
        c.setCharge(charge);
        c.setBirthDate(bdate);
        c.setWorkPlace(workplace);
        
        try {
            
            ContactDAO.addContact(c);
            
            out.println("<html>");
            out.println("<body>");
            out.println("Contact " + fname + " " + lname + " successfully added!");
            out.println("</body>");
            out.println("</html>");
            
        } catch (SQLException ex) {
            out.println(ex.getMessage());
            Logger.getLogger(AddContactServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
