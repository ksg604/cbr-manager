package com.example.cbr_manager.ui.visitdetails;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.ui.clientdetails.ClientDetailsEditFragment;

public class VisitDetailsEditFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private APIService apiService = APIService.getInstance();
    private View parentLayout;
    private Spinner genderSpinner;
    String gender="";
    Client client;
    private static final String[] paths = {"Male", "Female"};


    public VisitDetailsEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClientDetailsEditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClientDetailsEditFragment newInstance(String param1, String param2) {
        ClientDetailsEditFragment fragment = new ClientDetailsEditFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_visit_details_edit, container, false);
        parentLayout = root.findViewById(android.R.id.content);

        Intent intent = getActivity().getIntent();
        int clientId = intent.getIntExtra("clientId", -1);

        setupVectorImages(root);

        return root;
    }

    private void setupVectorImages(View root) {
        ImageView location = root.findViewById(R.id.profileLocationImageView);
        location.setImageResource(R.drawable.ic_place);
        ImageView date = root.findViewById(R.id.profileDateImageView);
        date.setImageResource(R.drawable.ic_date);
        ImageView additionalInfo = root.findViewById(R.id.profileAdditionalInfoImageView);
        additionalInfo.setImageResource(R.drawable.ic_info);
    }
}
