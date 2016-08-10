/*
 * Copyright 2016 tantk.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package controller;

import Manager.GameManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author tantk
 */

@WebServlet(name = "Game", urlPatterns = {"/Game/*"})
public class GameController extends HttpServlet {
@Inject  private GameManager gameMgr;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Game</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Game at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        {
            HttpSession session = request.getSession();
            long creationTime = session.getCreationTime();
            String sessionId = session.getId();
            long lastAccessedTime = session.getLastAccessedTime();
            Date createDate = new Date(creationTime);
            Date lastAccessedDate = new Date(lastAccessedTime);
            String userID = (String) session.getAttribute("userID");
            StringBuffer buffer = new StringBuffer();
            String[] pathInfo = request.getPathInfo().split("/");
            String id = pathInfo[1]; // {id}
           gameMgr.addPlayer(id, userID);
            buffer.append(" <HTML> <HEAD> </HEAD> <BODY>");
            buffer.append("<STRONG> Session ID : </STRONG>" + sessionId);
            buffer.append(" <BR/> ");
            buffer.append("<STRONG> Session Creation Time </STRONG>: " + createDate);
            buffer.append(" <BR/> ");
            buffer.append("<STRONG> Last Accessed Time : </STRONG>" + lastAccessedDate);
            buffer.append(" <BR/> ");
            buffer.append("<STRONG> UserID : </STRONG>" + userID);
            buffer.append(" <BR/> ");
            buffer.append("<STRONG> GameID : </STRONG>" + id);
            buffer.append(" <BR/> ");
            buffer.append(" </BODY> </HTML> ");
            PrintWriter writer = response.getWriter();
            writer.print(buffer.toString());
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
