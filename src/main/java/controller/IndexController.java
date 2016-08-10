/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Manager.GameManager;
import Manager.PlayerManager;
import com.google.gson.Gson;
import entity.UNOGame;
import java.io.IOException;
import java.io.PrintWriter;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

/**
 *
 * @author tan
 */
@WebServlet("/index")
public class IndexController extends HttpServlet {

    @Inject
    private PlayerManager playerMgr;
    @Inject
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
        UNOGame game = gameMgr.createGame(gameName);
        // playerMgr.createPlayer(playerName);

        request.setAttribute("game", game);
        request.getRequestDispatcher("waitingRoom.jsp").forward(request, response);
//         Gson gson = new Gson();
//        String jsonInString = gson.toJson(game);
//        
//       response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println(jsonInString);

    }

}


