package com.bignerdranch.android.fmapp.interfaces;

import android.util.Pair;

/**
 * Created by Riley on 4/13/18.
 */

public interface SettingsInterface
{
	void reSyncSuccess(Pair pair);
	void reSyncFail(String message);
}
