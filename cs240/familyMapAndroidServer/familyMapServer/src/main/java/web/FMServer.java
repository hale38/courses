package web;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

import web.handlers.ClearHandler;
import web.handlers.DefaultHandler;
import web.handlers.EventHandler;
import web.handlers.FillHandler;
import web.handlers.LoadHandler;
import web.handlers.PersonHandler;
import web.handlers.UserHandler;

/**
 * Created by hale38 on 2/24/18.
 */

public class FMServer
{

    public static void main(String [] args)throws Exception
    {
        Integer port = new Integer(args[0].toString());
        System.out.println(port);
        HttpServer server = HttpServer.create(new InetSocketAddress(port),0);

        server.createContext("/", new DefaultHandler());

        server.createContext("/user/", new UserHandler());

        server.createContext("/clear/", new ClearHandler());

        server.createContext("/fill", new FillHandler());

        server.createContext("/load", new LoadHandler());

        server.createContext("/person/", new PersonHandler());

        server.createContext("/event/", new EventHandler());





        server.setExecutor(null);
        server.start();

    }


}
