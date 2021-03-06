package com.example.cbr_manager.ui.referral.create_referral;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.referral.Referral;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReferralCreationFragment extends Fragment {

    private EditText editTextTitle, editTextBody;
    private Button buttonSubmit;
    private static final APIService apiService = APIService.getInstance();
    Referral referralToSend;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_referral_creation, container, false);

        editTextTitle = root.findViewById(R.id.editTextTitle);
        editTextBody = root.findViewById(R.id.editTextBody);
        buttonSubmit = root.findViewById(R.id.buttonBack);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendReferralCreateRequest();
                NavHostFragment.findNavController(ReferralCreationFragment.this)
                        .navigate(R.id.nav_referral_list_to_action_nav_dashboard);
            }
        });
        return root;
    }

    public void sendReferralCreateRequest() {
        Referral referral = new Referral(editTextTitle.getText().toString(),editTextBody.getText().toString());
        Call<Referral> call = apiService.referralService.createReferral(referral);
        call.enqueue(new Callback<Referral>() {
            @Override
            public void onResponse(Call<Referral> call, Response<Referral> response) {
                if(response.isSuccessful()){

                } else{

                }
            }

        @Override
        public void onFailure(Call<Referral> call, Throwable t) {
            Snackbar.make(root, "Failed to create the referral. Please try again", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }); }
}