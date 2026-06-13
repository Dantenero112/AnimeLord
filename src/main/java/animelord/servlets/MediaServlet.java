package animelord.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;

@WebServlet(
        urlPatterns = {
                "/anime/covers/*",
                "/anime/banners/*",
                "/stream/*"
        }
)
public class MediaServlet
        extends HttpServlet {

    private static final String STORAGE_ROOT =
            Paths.get(
                    "D:",
                    "AnimeLordStorage"
            ).toString();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            String requestUri =
                    request.getRequestURI();

            String contextPath =
                    request.getContextPath();

            /*
                REMOVE CONTEXT PATH

                Example:

                /AnimeLord/stream/4/episode_7/480p/index.m3u8

                becomes

                /stream/4/episode_7/480p/index.m3u8
            */
            String relativePath =
                    requestUri.substring(
                            contextPath.length()
                    );

            File mediaFile =
                    Paths.get(
                            STORAGE_ROOT,
                            relativePath.replaceFirst(
                                    "^/",
                                    ""
                            )
                    )
                    .toFile();

            /*
                FILE NOT FOUND
            */
            if(!mediaFile.exists()
                    || !mediaFile.isFile()){

                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND
                );

                return;
            }

            /*
                CONTENT TYPE
            */
            String fileName =
                    mediaFile.getName()
                             .toLowerCase();

            if(fileName.endsWith(
                    ".m3u8"
            )){

                response.setContentType(
                        "application/vnd.apple.mpegurl"
                );

            }
            else if(fileName.endsWith(
                    ".ts"
            )){

                response.setContentType(
                        "video/mp2t"
                );

            }
            else if(fileName.endsWith(
                    ".jpg"
            )
                    || fileName.endsWith(
                            ".jpeg"
                    )){

                response.setContentType(
                        "image/jpeg"
                );

            }
            else if(fileName.endsWith(
                    ".png"
            )){

                response.setContentType(
                        "image/png"
                );

            }
            else if(fileName.endsWith(
                    ".webp"
            )){

                response.setContentType(
                        "image/webp"
                );

            }
            else{

                response.setContentType(
                        "application/octet-stream"
                );

            }

            /*
                HLS HEADERS
            */
            response.setHeader(
                    "Accept-Ranges",
                    "bytes"
            );

            response.setContentLengthLong(
                    mediaFile.length()
            );

            /*
                STREAM FILE
            */
            try(
                    FileInputStream fis =
                            new FileInputStream(
                                    mediaFile
                            );

                    OutputStream os =
                            response.getOutputStream()
            ){

                byte[] buffer =
                        new byte[8192];

                int bytesRead;

                while((bytesRead =
                        fis.read(
                                buffer
                        )) != -1){

                    os.write(
                            buffer,
                            0,
                            bytesRead
                    );

                }

                os.flush();

            }

        }
        catch(Exception e){

            e.printStackTrace();

            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            );
        }
    }
}