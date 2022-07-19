package com.artivain.reseaudiscord;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
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

	public JSONObject check(String id) throws ExecutionException, InterruptedException {
		RequestFuture<JSONObject> future = RequestFuture.newFuture();
		String url = baseUrl + "/v1/check?id=" + id;
		JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(), future, future);
		queue.add(request);

		return future.get();
	}
}
