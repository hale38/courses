package com.bignerdranch.android.fmapp.interfaces;

import shared.results.LoginResult;
import shared.results.PersonResult;
import shared.results.RegisterResult;

/**
 * Created by Riley on 3/26/18.
 */

public interface LoginInterface
{
	void loginSuccess(LoginResult result);
	void loginFail(LoginResult result);
	void registerSuccess(RegisterResult result);
	void registerFail(RegisterResult result);
	void dataSyncSuccess(PersonResult result);
	void dataSyncFail(PersonResult result);
	void loadMap();
}
