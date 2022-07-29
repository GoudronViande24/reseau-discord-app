package com.artivain.reseaudiscord;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class ReseauDiscordAPI {
	public ReseauDiscordAPI(Context theContext) {
		context = theContext;
		queue = Volley.newRequestQueue(context);
	}

	String baseUrl = "https://api-rd.artivain.com";

	private Context context;
	private RequestQueue queue;

	private final Response.ErrorListener errorListener = error -> {
		Toast.makeText(context, R.string.error_msg, Toast.LENGTH_SHORT).show();
		Log.e("ReseauDiscordAPI", error.toString());
	};

	public void check(String id, Response.Listener<JSONObject> callback) throws ExecutionException, InterruptedException {
		String URL = baseUrl + "/v1/check?id=" + id;
		JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, callback, errorListener);
		queue.add(request);
	}
}
