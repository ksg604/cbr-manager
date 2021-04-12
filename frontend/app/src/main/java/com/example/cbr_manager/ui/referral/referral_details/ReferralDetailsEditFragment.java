package com.example.cbr_manager.ui.referral.referral_details;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.service.referral.Referral;
import com.example.cbr_manager.ui.ClientViewModel;
import com.example.cbr_manager.ui.ReferralViewModel;
import com.example.cbr_manager.ui.VisitViewModel;
import com.example.cbr_manager.utils.Helper;
import com.google.android.material.snackbar.Snackbar;

import org.threeten.bp.format.FormatStyle;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableCompletableObserver;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class ReferralDetailsEditFragment extends Fragment {

    private Spinner statusSpinner;
    String status="";
    private ReferralViewModel referralViewModel;
    private Referral localReferral;
    private int referralId;


    public ReferralDetailsEditFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_referral_details_edit, container, false);
        referralViewModel = new ViewModelProvider(this).get(ReferralViewModel.class);

        Bundle bundle = this.getArguments();
        this.referralId = bundle.getInt("referralId", -1);

        setupStatusSpinner(root);
        setupTexts(referralId, root);
        setupButtons(root);

        return root;
    }

    private void modifyReferralInfo(Referral referral) {
        referralViewModel.modifyReferral(referral).subscribe(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                Snackbar.make(getView(), "Successfully updated referral", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                getActivity().onBackPressed();
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Snackbar.make(getView(), "Failed to update referral", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void getAndModifyReferral(int referralId, View root) {
        EditText editReferTo = (EditText) root.findViewById(R.id.referralDetailsReferToEditTextView);
        EditText editOutcome = (EditText) root.findViewById(R.id.referralDetailsOutcomeEditTextView);

        localReferral.setStatus(status);
        localReferral.setRefer_to(editReferTo.getText().toString());
        localReferral.setOutcome(editOutcome.getText().toString());
        modifyReferralInfo(localReferral);
    }

    private void setupStatusSpinner(View root) {
        String[] paths = {"CREATED", "RESOLVED"};
        Spinner spinner = (Spinner) root.findViewById(R.id.referralDetailsEditStatusSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                status = paths[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                status = paths[0];
            }
        });
    }

    private void setUpTextView(int textViewId, String text) {
        TextView textView = (TextView)getView().findViewById(textViewId);
        if(text.equals("")){
            text="None";
        }
        textView.setText(text);
    }

    private void setUpEditTextView(int editTextViewId, String text) {
        EditText editTextView = (EditText)getView().findViewById(editTextViewId);
        if(text.equals("")){
            text="None";
        }
        editTextView.setText(text);
    }

    private void setupClientTextView() {
        TextView clientNameTextView = (TextView)getView().findViewById(R.id.referralDetailsClientTextView);
        clientNameTextView.setText(localReferral.getFullName());
    }


    private void setupTexts(int referralId, View root) {
        referralViewModel.getReferral(referralId).observe(getViewLifecycleOwner(), observeReferral -> {
                this.localReferral = observeReferral;
                setupClientTextView();
                setUpTextView(R.id.referralDetailsTypeTextView, localReferral.getServiceType());
                setUpEditTextView(R.id.referralDetailsReferToEditTextView, localReferral.getRefer_to());
                setUpEditTextView(R.id.referralDetailsOutcomeEditTextView, localReferral.getOutcome());
                setUpTextView(R.id.referralDetailsServiceDetailTextView, localReferral.getServiceDetail().getInfo(localReferral.getServiceType()));
                setUpTextView(R.id.referralDetailsDateCreatedTextView, Helper.formatDateTimeToLocalString(localReferral.getCreatedAt(), FormatStyle.SHORT));
        });
    }



    private void setupButtons(View root) {
        setupBackButton(root);
        setupSubmitButton(root);
    }
    private void setupSubmitButton(View root) {
        Intent intent = getActivity().getIntent();
        int referralId = intent.getIntExtra("referralId", -1);

        Button submitButton = root.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAndModifyReferral(referralId, root);
            }
        });
    }

    private void setupBackButton(View root) {
        ImageView backButtonImageView = root.findViewById(R.id.referralDetailsBackImageView);
        backButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }
}