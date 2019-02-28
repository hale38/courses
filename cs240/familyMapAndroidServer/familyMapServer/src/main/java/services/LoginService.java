package services;

import DAOs.AuthDAO;
import DAOs.EventDAO;
import DAOs.PersonDAO;
import DAOs.UserDAO;
import shared.requests.LoginRequest;
import shared.results.LoginResult;

public class LoginService
{
    AuthDAO authD;
    EventDAO eventD;
    PersonDAO personD;
    UserDAO userD;

    public LoginService(){};



    public LoginResult takeRequest (LoginRequest request)
    {

        LoginResult loginResult;

        authD = new AuthDAO();
        userD = new UserDAO();
        //if user is inValid  return error

        if(!userD.checkUserPassword(request.getUserName(), request.getPassword()))
        {
            loginResult = new LoginResult();
            loginResult.setBody("Invalid Username / Password");
            return loginResult;
        };


        String auth = java.util.UUID.randomUUID().toString();
        String pid = userD.getPID(request.getUserName());
        authD.addSingleAuth(request.getUserName(),auth);
        loginResult = new LoginResult(request.getUserName(),auth,pid);









        return loginResult;
    }

}
