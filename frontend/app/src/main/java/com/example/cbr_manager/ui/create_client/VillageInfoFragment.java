package com.example.cbr_manager.ui.create_client;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.example.cbr_manager.R;

public class VillageInfoFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    private Spinner spinner;
    private static final String[] paths = {"BidiBidi Zone 1", "BidiBidi Zone 2", "BidiBidi Zone 3", "BidiBidi Zone 4", "BidiBidi Zone 5",
            "Palorinya Basecamp", "Palorinya Zone 1", "Palorinya Zone 2", "Palorinya Zone 3"};
    private EditText editTextId, editTextVillageNum;

    int id=0, villageNumber=0;
    String location="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.activity_create_client_village_info, container, false);

        editTextId = (EditText) view.findViewById(R.id.editTextFirstName);

        spinner = (Spinner) view.findViewById(R.id.location_dropdown);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,paths);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        editTextVillageNum = (EditText) view.findViewById(R.id.editTextVillageNum);

        Button nextButton = view.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateInfo(getView());
                ((CreateClientActivity) getActivity()).setViewPager(CreateClientActivity.CreateClientFragments.PERSONAL_INFO.ordinal());
            }
        });
        Button prevButton = view.findViewById(R.id.prevButton);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CreateClientActivity) getActivity()).setViewPager(CreateClientActivity.CreateClientFragments.CONSENT.ordinal());
            }
        });

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        location = paths[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        location = paths[0];
    }

    private void updateInfo(View v) {
        String idInString = editTextId.getText().toString();
        String villageNumberInString = editTextVillageNum.getText().toString();
        if(!idInString.equals("")) {
            id = Integer.parseInt(idInString);
        }
        if(!villageNumberInString.equals("")) {
            villageNumber = Integer.parseInt(villageNumberInString);
        }
        ((CreateClientActivity) getActivity()).setVillageInfo(location, id, villageNumber);
    }

}
