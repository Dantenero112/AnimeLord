package animelord.models;

import animelord.dao.UploadQueueDAO;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class EncodingQueueModel
        implements Model {

    @Override
    public void businessLogic(
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        try {

            UploadQueueDAO queueDAO =
                    new UploadQueueDAO();

            String status =
                    request.getParameter(
                            "status"
                    );

            /*
                FILTER BY STATUS
            */
            if(status != null
                    && !status.isBlank()){

                request.setAttribute(
                        "queueList",
                        queueDAO.getQueueByStatus(
                                status
                        )
                );

            }
            else{

                request.setAttribute(
                        "queueList",
                        queueDAO.getAllQueueItems()
                );

            }

            /*
                CURRENT VIEW
            */
            request.setAttribute(
                    "view",
                    "encodingQueue"
            );

            /*
                FORWARD TO ADMIN PAGE
            */
            RequestDispatcher rd =
                    request.getRequestDispatcher(
                            "/WEB-INF/views/admin.jsp"
                    );

            rd.forward(
                    request,
                    response
            );

        }
        catch(Exception e){

            e.printStackTrace();

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin"
            );

        }

    }

}