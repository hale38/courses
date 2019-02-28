package web.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

import services.EventService;
import shared.requests.EventRequest;
import shared.results.EventResult;

/**
 * Created by hale38 on 2/24/18.
 */

public class EventHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "";


        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();

        String uri= httpExchange.getRequestURI().getPath().toString();
        String auth = httpExchange.getRequestHeaders().getFirst("Authorization");
        Boolean result = null;

        EventService eventService = new EventService();
        EventRequest eventRequest;


        String[] request = uri.split("/");


        if (request.length==2)
        {
            //send eventReuest
            eventRequest = new EventRequest();
            eventRequest.setAuthToken(auth);

            EventResult eventResult = eventService.getAllevents(eventRequest);
            response=eventResult.getBody();





            //return all events for user with auth token
        }
        else if (request.length==3)
        {
            //return the single event with the specified ID
            eventRequest = new EventRequest(request[2].toString(),auth);
            EventResult eventResult = eventService.takeRequest(eventRequest);
            response = eventResult.getBody();
        }
        os.write(response.getBytes());
        os.close();
    }
}
