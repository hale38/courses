package services;

import DAOs.UserDAO;
import shared.models.Converter;
import shared.models.User;
import shared.requests.FillRequest;
import shared.requests.LoginRequest;
import shared.requests.RegisterRequest;
import shared.results.LoginResult;
import shared.results.RegisterResult;

public class RegisterService
{
    UserDAO userD;

    public RegisterService()
    {

    }


    //takes in a request and returns appropriate response
    //Depending on how things go, I might add additional methods for each type of request
    public RegisterResult takeRequest(RegisterRequest request)
    {
        UserDAO userDAO = new UserDAO();
        Converter converter = new Converter();
        User user = converter.toUser(request.getBody());
        RegisterResult registerResult;

        //check to see if user exists
        if(userDAO.checkUserExists(user.getUserName()))
        {
            registerResult = new RegisterResult();
            registerResult.setMesage("User Already Exists");
            return registerResult;
        }


        //if user does not exist

        userDAO.addUser(user);
        LoginRequest loginRequest= new LoginRequest();
        loginRequest.setUserName(user.getUserName());
        loginRequest.setPassword(user.getPassword());
        LoginService loginService = new LoginService();
        LoginResult loginResult = loginService.takeRequest(loginRequest);

        registerResult = new RegisterResult(loginResult.getUserName(),loginResult.getPassWord(),loginResult.getAuth());

        //end of giant else statement

        FillRequest fillRequest = new FillRequest();
        fillRequest.setUserName(user.getUserName());
        FillService fillService = new FillService();
        fillService.takeRequest(fillRequest);



        return registerResult;
    }

}

