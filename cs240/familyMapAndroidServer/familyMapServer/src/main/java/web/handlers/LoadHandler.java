package web.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import services.ClearService;
import services.LoadService;
import shared.models.Converter;
import shared.requests.LoadRequest;
import shared.results.LoadResult;

/**
 * Created by hale38 on 2/24/18.
 */

public class LoadHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "Load Response";


        httpExchange.sendResponseHeaders(200, 0);
        OutputStream os = httpExchange.getResponseBody();

        String uri= httpExchange.getRequestURI().getPath().toString();
        String body = readString(httpExchange.getRequestBody());
        Converter converter = new Converter();
        Boolean result = null;

        if(uri.equals("/load/"))
        {
            ClearService clearService = new ClearService();
            clearService.clearDB();
            LoadRequest loadRequest = new LoadRequest();
            loadRequest.setBody(body);
            LoadService loadService = new LoadService();
            LoadResult loadResult = new LoadResult();
            loadResult = loadService.takeRequest(loadRequest);
            response = converter.toJson(loadResult);
        }


        writeString(response,os);

        os.close();
    }


    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
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
