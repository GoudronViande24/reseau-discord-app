package com.artivain.reseaudiscord;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.artivain.reseaudiscord.databinding.FragmentFirstBinding;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class FirstFragment extends Fragment {

	private FragmentFirstBinding binding;

	@Override
	public View onCreateView(
					@NonNull LayoutInflater inflater, ViewGroup container,
					Bundle savedInstanceState
	) {

		reseauDiscordAPI = new ReseauDiscordAPI(getContext());

		binding = FragmentFirstBinding.inflate(inflater, container, false);
		return binding.getRoot();

	}

	ReseauDiscordAPI reseauDiscordAPI;

	public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		binding.goButton.setOnClickListener(view1 -> {
			try {
				String id = binding.idInput.getText().toString();

				reseauDiscordAPI.check(id, response -> {
					binding.resultData.setVisibility(View.VISIBLE);
					String result = "ID: " + id;

					try {
						boolean suspect = response.getBoolean("suspect");
						if (!suspect) result += "\n\nSuspect: false";
					} catch (JSONException e) {
						try {
							String addedBy = response.getJSONObject("suspect").getString("addedBy");

							long sinceMs = response.getJSONObject("suspect").getLong("since");
							Log.i("Since amongus", String.valueOf(sinceMs));
							String sinceRel = DateUtils.getRelativeTimeSpanString(sinceMs, System.currentTimeMillis(), DateUtils.DAY_IN_MILLIS).toString();
							String since = new SimpleDateFormat("dd/MM/yyy", getResources().getConfiguration().locale).format(new Date(sinceMs));

							result += "\n\nSuspect: true\nAdded by: " + addedBy + "\nOn the list since: " + since + " (" + sinceRel + ")";
						} catch (JSONException ex) {
							Log.e("ResponseHandler", ex.toString());
							Toast.makeText(getContext(), R.string.error_msg, Toast.LENGTH_SHORT).show();
						}
					}

					try {
						boolean blacklisted = response.getBoolean("blacklist");
						if (!blacklisted) result += "\n\nBlacklisted: false";
					} catch (JSONException e) {
						try {
							String addedBy = response.getJSONObject("blacklist").getString("addedBy");

							long sinceMs = response.getJSONObject("blacklist").getLong("since");
							String sinceRel = DateUtils.getRelativeTimeSpanString(sinceMs, System.currentTimeMillis(), DateUtils.DAY_IN_MILLIS).toString();
							String since = new SimpleDateFormat("dd/MM/yyy", getResources().getConfiguration().locale).format(new Date(sinceMs));

							result += "\n\nBlacklisted: true\nAdded by: " + addedBy + "\nOn the list since: " + since + " (" + sinceRel + ")";
						} catch (JSONException ex) {
							Log.e("ResponseHandler", ex.toString());
							Toast.makeText(getContext(), R.string.error_msg, Toast.LENGTH_SHORT).show();
						}
					}

					binding.resultData.setText(result);
				});
			} catch (ExecutionException | InterruptedException e) {
				createErrorToast(e);
			}
		});
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		binding = null;
	}

	void createErrorToast(Exception e) {
		Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
	}

}