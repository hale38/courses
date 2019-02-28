package web.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import services.PersonService;
import shared.models.Converter;
import shared.requests.PersonRequest;
import shared.results.PersonResult;

/**
 * Created by hale38 on 2/24/18.
 */

public class PersonHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "person response";


        httpExchange.sendResponseHeaders(200, 0);
        OutputStream os = httpExchange.getResponseBody();

        String uri= httpExchange.getRequestURI().getPath().toString();
        String auth = httpExchange.getRequestHeaders().getFirst("Authorization");
        PersonService personService = new PersonService();
        PersonRequest personRequest = new PersonRequest();
        personRequest.setAuthToken(auth);
        Converter converter = new Converter();

        PersonResult personResult = new PersonResult();

        String[] request = uri.split("/");

        if (request.length==2)
        {
            personRequest.setBody(auth);
            personResult = personService.getAllPersons(personRequest);
            response=personResult.getBody();
            //return ALL people associated with the current user
        }
        else if (request.length==3)
        {
            personRequest.setBody(request[2]);
            personResult = personService.takeRequestSingle(personRequest);
            response = personResult.getBody();
        }



        writeString(response,os);
        os.close();
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

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
