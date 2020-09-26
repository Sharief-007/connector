package com;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

@WebServlet("/Controller")
public class Controller extends HttpServlet {
    RequestDispatcher dispatcher;
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        String psw = req.getParameter("psw");
        String email = req.getParameter("email");
        //String psw2 = req.getParameter("psw-repeat");
        try {
            Sender sender = new Sender();
            System.out.println(email);
            sender.setMailID("shariefmohammad007@gmail.com");
            sender.setPassword("Sharief@007");
            sender.setSubject("OTP for sign-Up");
            int max=999999;
            int min=100000;
            String otp = String.valueOf((int)(Math.random()*((max-min)+1))+min);
            sender.setText(otp);
            sender.sendTo(email);
            out.println("message set successfully");
            req.setAttribute("OTP",otp);
            req.setAttribute("psw",psw);
            req.setAttribute("email",email);
            //req.setAttribute("psw2",psw2);
            dispatcher = req.getRequestDispatcher("confirm.jsp");
            dispatcher.forward(req,res);
        }
        catch (Exception e)
        {
            out.println(e.getMessage());
        }
    }

}
