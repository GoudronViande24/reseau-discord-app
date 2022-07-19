package com.artivain.reseaudiscord;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.artivain.reseaudiscord.databinding.FragmentFirstBinding;

import java.util.concurrent.ExecutionException;

public class FirstFragment extends Fragment {

	private FragmentFirstBinding binding;

	@Override
	public View onCreateView(
					LayoutInflater inflater, ViewGroup container,
					Bundle savedInstanceState
	) {

		reseauDiscordAPI = new ReseauDiscordAPI(getContext());

		binding = FragmentFirstBinding.inflate(inflater, container, false);
		return binding.getRoot();

	}

	ReseauDiscordAPI reseauDiscordAPI;

	public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		binding.goButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				try {
					reseauDiscordAPI.check("382869186042658818");
				} catch (ExecutionException | InterruptedException e) {
					createErrorToast(e);
				}
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