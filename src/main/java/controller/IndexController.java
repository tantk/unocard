/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Manager.GameManager;
import Manager.PlayerManager;
import entity.UNOGame;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author tan
 */
@WebServlet("/index")
public class IndexController extends HttpServlet {

    @EJB
    private PlayerManager playerMgr;
    @EJB
    private GameManager gameMgr;

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
        request.getRequestDispatcher("/MainMenu.jsp").forward(request, response);
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

        processRequest(request, response);
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
//        JsonObject data = new Gson().fromJson(request.getParameter("data"), JsonObject.class);
//        String gameName = data.get("gameName").toString();
//        String playerName = data.get("playerName").toString();
//    
//        System.out.print(gameName);
//        System.out.print(playerName);
        System.out.print("got here");
        
        //String playerName = request.getParameter("playerName");
        String gameName = request.getParameter("gameName");
        UNOGame game=gameMgr.createGame(gameName);
       // playerMgr.createPlayer(playerName);
       request.setAttribute("game", game);
       request.getRequestDispatcher("waitingRoom.jsp").forward(request, response);
        
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
