package com.googleaccount;

import android.os.AsyncTask;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;

import java.io.ByteArrayInputStream;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.AccessToken;

public class RNGoogleAccountModule extends ReactContextBaseJavaModule {
	private GoogleCredentials credentials;

	public RNGoogleAccountModule(ReactApplicationContext reactContext) {
		super(reactContext);
		credentials = null;
	}

	@ReactMethod
	public void fromString(final String jsonData, final ReadableArray scoped, final Promise promise){
		AsyncTask.execute(new Runnable() {
			@Override
			public void run() {
				try {
					credentials = GoogleCredentials.fromStream(new ByteArrayInputStream(jsonData.getBytes())).createScoped(readableArrayToStringArray(scoped));
					getAccessToken(promise);
				} catch (Exception e) {
					promise.reject(e);
				}
			}
		});
	}
	
	@ReactMethod
	public void getAccessToken(Promise promise) {
		try {
			if (credentials == null)  { promise.reject("Credentials not initialized - use fromString"); return; }
			credentials.refreshIfExpired();
			AccessToken token = credentials.getAccessToken();
			promise.resolve(token.getTokenValue());
		} catch (Exception e) {
			promise.reject(e);
		}
	}

	@Override
	public String getName() {
		return "RNGoogleAccount";
	}
	
	private static String[] readableArrayToStringArray(ReadableArray readableArray) {
		String[] arr = new String[readableArray.size()];
		for (int i = 0; i < readableArray.size(); i++) {
			arr[i] = readableArray.getString(i);
		}

		return arr;
	}
}
