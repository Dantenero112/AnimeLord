/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package animelord.frontcontroller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.util.Properties;
import animelord.models.Model;
/**
 *
 * @author Lenovo
 */
public class FrontController extends HttpServlet {

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
        
        try{
        String uri = request.getRequestURI();
        String context = request.getContextPath();
        String path = uri.substring(context.length());
        
        if (path == null || path.equals("/") || path.equals("") || path.equals("/index")) {
            response.setContentType("text/html;charset=UTF-8");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }
//        System.out.println("path = "+path);
        if(path!=null){
            path = path.substring(1).trim();
//            System.out.println("Inside path!=null path = "+path);
        }
        System.out.println("path = "+path);
        Properties prop = new Properties();
        prop.load(getServletContext().getResourceAsStream("/WEB-INF/pathValues.properties"));
        String pathValue = prop.getProperty(path);
        System.out.println("pathValue="+pathValue);
        
        if(pathValue!=null){
            if(pathValue.endsWith(".jsp")){
                response.setContentType("text/html;charset=UTF-8");
                //Dispatcher Code
//                System.out.println("Inside Dispatcher Code");
                //response.sendRedirect(pathValue);
                request.getRequestDispatcher(pathValue).forward(request, response);
//                System.out.println("After Dispatching");
                }
            else{
                Model lm = (Model) Class.forName(pathValue).getDeclaredConstructor().newInstance();
                lm.businessLogic(request, response);
            }
            }
        else{
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
        catch(Exception e){
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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
