package web.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import services.FillService;
import shared.models.Converter;
import shared.requests.FillRequest;
import shared.results.FillResult;

/**
 * Created by hale38 on 2/24/18.
 */

public class FillHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response;
        Converter converter = new Converter();

        httpExchange.sendResponseHeaders(200, 0);
        OutputStream os = httpExchange.getResponseBody();

        String uri= httpExchange.getRequestURI().getPath().toString();
        Boolean result = null;
        String[] url= uri.split("/");
        FillRequest fillRequest = new FillRequest();
        FillService fillService = new FillService();
        FillResult fillResult;

        if(url.length==3)
        {
            fillRequest.setUserName(url[2]);
            System.out.print("/fill/username");
            fillResult = fillService.takeRequest(fillRequest);

        }
        else if (url.length==4)
        {
            int gen = Integer.valueOf(url[3]);
            if(gen<0)
            {
                fillResult=new FillResult();
                fillResult.badRequest();

            }
            else
            {
                fillRequest.setUserName(url[2]);
                fillRequest.setGenerations(gen);
                System.out.print("/fill/username/generations");
                fillResult = fillService.takeRequest(fillRequest);
            }
        }
        else
        {
            fillResult = new FillResult();
            fillResult.badRequest();
        }


        response = converter.toJson(fillResult);
        os.write(response.getBytes());
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
