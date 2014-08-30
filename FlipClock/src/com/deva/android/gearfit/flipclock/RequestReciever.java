/*
 * Copyright (C) 2014, Asterisk* (aka.asterisk@gmail.com)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.deva.android.gearfit.flipclock;

import java.io.IOException;
import java.io.InputStream;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;

/**
 * <b>RequestReciever.java</b>
 * A class that BroadcastReceiver for reacting to <i>com.samsung.android.gearfit.styleclock.REQUEST_DATA</i>
 * @author Asterisk*
 * @version 0.1.0, Build 3, June 29, 2014.
 */
public class RequestReciever extends BroadcastReceiver {
	@SuppressLint("NewApi")
	@Override
	public void onReceive(Context context, Intent intent) {
		AssetManager assetManager = context.getAssets();
		try {
			// clock resources(clock.zip) is in application's asset folder
			InputStream is = assetManager.open("clock/clock.zip");
			byte[] zipData = new byte[is.available()];
			is.read(zipData);
			is.close();

			// send intent <i>com.samsung.android.gearfit.styleclock.SET_CLOCK</i>
			Intent clockIntent = new Intent("com.samsung.android.gearfit.styleclock.SET_CLOCK");
			// the <i>com.samsung.android.wms</i> is <i>Gear fit manager</i>'s package name
			clockIntent.setPackage("com.samsung.android.wms");
			// OMG, clock.zip is transmitted on to the intent
			clockIntent.putExtra("zipData", zipData);
			// set this project's package name
			clockIntent.putExtra("packageName", context.getPackageName());
			// set clock orientation (vertical, or horizontal)
			clockIntent.putExtra("orientation", "horizontal");

			// if the <b>android.os.TransactionTooLargeException</b> occurs here,
			// the <i>/asset/clock/clock.zip</i> file size is <b>too large</b>.
			// intent.putExtras() size limit is 500Kbytes (maybe).
			context.sendBroadcast(clockIntent);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
