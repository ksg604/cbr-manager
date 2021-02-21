package com.example.cbr_manager.ui.create_client;

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

public class PersonalInfoFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    EditText editTextFirstName, editTextLastName, editTextAge, editTextContactNumber;
    Spinner spinner;
    String firstName="";
    String lastName="";
    int contactNumber=0;
    int age=0;
    String gender="";
    private static final String[] paths = {"Male", "Female"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.activity_create_client_personal_info, container, false);

        editTextFirstName = (EditText) view.findViewById(R.id.editTextFirstName);
        editTextLastName = (EditText) view.findViewById(R.id.editTextLastName);

        spinner = (Spinner) view.findViewById(R.id.gender_dropdown);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        editTextAge = (EditText) view.findViewById(R.id.editTextAge);
        editTextContactNumber = (EditText) view.findViewById(R.id.editTextContactNumber);

        Button nextButton = view.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateInfo(v);
                ((CreateClientActivity) getActivity()).setViewPager(CreateClientActivity.CreateClientFragments.DISABILITY.ordinal());
            }
        });
        Button prevButton = view.findViewById(R.id.prevButton);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CreateClientActivity) getActivity()).setViewPager(CreateClientActivity.CreateClientFragments.VILLAGE_INFO.ordinal());
            }
        });

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        gender = paths[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        gender = paths[0];
    }

    public void updateInfo(View v) {
        firstName = editTextFirstName.getText().toString();
        lastName = editTextLastName.getText().toString();
        String contactNum = editTextContactNumber.getText().toString();
        if(!contactNum.equals("")) {
            contactNumber = Integer.parseInt(contactNum);
        }
        String age_string = editTextAge.getText().toString();
        if(!age_string.equals("")) {
            age = Integer.parseInt(age_string);
        }

        ((CreateClientActivity) getActivity()).setPersonalInfo(firstName, lastName, contactNumber, age, gender);
    }

}
