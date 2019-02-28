package web.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import services.LoginService;
import services.RegisterService;
import shared.models.Converter;
import shared.requests.LoginRequest;
import shared.requests.RegisterRequest;
import shared.results.LoginResult;
import shared.results.RegisterResult;

/**
 * Created by Riley on 3/7/18.
 */

public class UserHandler implements HttpHandler
{
	public void handle(HttpExchange httpExchange) throws IOException
	{
		String response = "";


		httpExchange.sendResponseHeaders(200, response.length());
		OutputStream os = httpExchange.getResponseBody();
		String uri= httpExchange.getRequestURI().getPath().toString();
		String body = readString(httpExchange.getRequestBody());
		String auth = httpExchange.getRequestHeaders().getFirst("Authorization");
		Converter converter = new Converter();

		String[] url= uri.split("/");

		if(url.length==3)
		{
			if(url[2].equals("register"))
			{
				RegisterRequest registerRequest = new RegisterRequest();
				registerRequest.setAuth(auth);
				registerRequest.setBody(body);
				RegisterService registerService = new RegisterService();
				RegisterResult registerResult = registerService.takeRequest(registerRequest);

				response= converter.toJson(registerResult);
			}

			else if (url[2].equals("login"))
			{

				LoginRequest loginRequest = converter.toLoginReq(body);

				LoginService loginService = new LoginService();
				LoginResult loginResult = loginService.takeRequest(loginRequest);
				response = converter.toJson(loginResult);
			}

		}
		else
		{
			response = "\"Invalid Request\"";
		}
		//Request request = new EventRequest();

		Boolean result = null;


		writeString(response.toString(),os);
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
