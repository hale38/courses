package web.handlers;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * Created by hale38 on 2/24/18.
 */

public class DefaultHandler implements HttpHandler{


    @Override
    public void handle(HttpExchange httpExchange) throws IOException {



        httpExchange.sendResponseHeaders(200, 0);
        Path path = null;

        String uri= httpExchange.getRequestURI().getPath().toString();


	    if(uri.equals("/"))
        {
	        path  = Paths.get("webFiles/index.html");
        }
        else
        {
            path  = Paths.get("webFiles" + uri);
        }



        Files.copy(path, httpExchange.getResponseBody());
	    httpExchange.close();


    }

    private String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }
}
