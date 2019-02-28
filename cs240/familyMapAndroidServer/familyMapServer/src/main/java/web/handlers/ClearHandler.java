package web.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

import services.ClearService;
import shared.models.Converter;
import shared.requests.ClearRequest;
import shared.results.ClearResult;

/**
 * Created by hale38 on 2/24/18.
 */

public class ClearHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = null;

        Converter converter = new Converter();
        ClearRequest cr = new ClearRequest();
        ClearService clearService = new ClearService();
        String uri= httpExchange.getRequestURI().getPath().toString();
        ClearResult clearResult = new ClearResult();
        Boolean result = null;

        if(uri.equals("/clear/"))
        {
           // cr.sendRequest();

            clearResult=clearService.clearDB();
        }
        else
        {
            clearResult.setMessage("Invalid request");
        }

        response=converter.toJson(clearResult);

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();

        os.write(response.getBytes());
        os.close();
    }
}
