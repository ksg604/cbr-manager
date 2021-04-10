package com.example.cbr_manager.ui.statistics;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.baseline_survey.BaselineSurvey;
import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.service.referral.Referral;
import com.example.cbr_manager.ui.VisitViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class StatisticsFragment extends Fragment {

    private static final int HIGH_RISK_THRESHOLD = 20;
    private final APIService apiService = APIService.getInstance();
    private HashMap<String, String> csvFields = new HashMap<>();
    View view;
    private VisitViewModel visitViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        visitViewModel = new ViewModelProvider(this).get(VisitViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_statistics, container, false);
        setupStats(view);

        Button exportButton = view.findViewById(R.id.export_button);
        exportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exportCSV(v);
            }
        });

        return view;
    }


    private void setupStats(View view) {
        if (apiService.isAuthenticated()) {
            setupReferralStats(view);
            setupClientStats(view);
            setupNumberOfVisits(view);
            setupBaselineSurveyStats(view);
        }
    }


    private void setupReferralStats(View view) {
        apiService.referralService.getReferrals().enqueue(new Callback<List<Referral>>() {
            @Override
            public void onResponse(Call<List<Referral>> call, Response<List<Referral>> response) {
                if (response.isSuccessful()) {
                    List<Referral> referrals = response.body();
                    int numReferrals = referrals.size();
                    saveIntToCSV("numReferrals", numReferrals);

                    TextView textView1 = view.findViewById(R.id.statistic4_sub2);
                    textView1.setText(Integer.toString(numReferrals));
                }
            }

            @Override
            public void onFailure(Call<List<Referral>> call, Throwable t) {

            }
        });
    }


    private void setupClientStats(View view) {
        apiService.clientService.getClients().enqueue(new Callback<List<Client>>() {
            @Override
            public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                if (response.isSuccessful()) {
                    List<Client> clients = response.body();

                    int totalClients = clients.size();
                    saveIntToCSV("totalClients", totalClients);

                    double averageAge = getAndSaveAverageAge("averageAge", clients);
                    int totalFemales = getAndSaveTotalFemales("totalFemales", clients);
                    int totalMales = getAndSaveTotalMales("totalMales", clients);
                    int totalHighRisk = getAndSaveHighRiskClients("totalHighRisk", clients);
                    int totalNoCareGiver = getAndSaveTotalNoCareGiver("totalNoCareGiver", clients);
                    double averageHealthRisk = getAndSaveAverageHealthRisk("averageHealthRisk", clients);
                    double averageEducationRisk = getAndSaveAverageEducationRisk("averageEducationRisk", clients);
                    double averageSocialRisk = getAndSaveAverageSocialRisk("averageSocialRisk", clients);
                    double averageRiskScore = getAndSaveAverageRiskScore("averageRiskScore", clients);

                    setTextViewInteger(R.id.statistic1_sub1, totalClients);
                    setTextViewDouble(R.id.statistic1_sub2, averageAge);
                    setTextViewInteger(R.id.statistic1_sub3, totalFemales);
                    setTextViewInteger(R.id.statistic1_sub4, totalMales);
                    setTextViewInteger(R.id.statistic1_sub5, totalHighRisk);
                    setTextViewInteger(R.id.statistic1_sub6, totalNoCareGiver);
                    setTextViewDouble(R.id.statistic2_sub1, averageHealthRisk);
                    setTextViewDouble(R.id.statistic2_sub2, averageEducationRisk);
                    setTextViewDouble(R.id.statistic2_sub3, averageSocialRisk);
                    setTextViewDouble(R.id.statistic2_sub4, averageRiskScore);
                }

            }

            @Override
            public void onFailure(Call<List<Client>> call, Throwable t) {

            }
        });
    }


    private int getAndSaveTotalNoCareGiver(String name, List<Client> clients) {
        int totalNoCareGiver = 0;
        for (Client client : clients) {
            if (client.getCarePresent().toLowerCase().trim().equals("no")) {
                totalNoCareGiver++;
            }
        }

        saveIntToCSV(name, totalNoCareGiver);
        return totalNoCareGiver;
    }


    private int getAndSaveHighRiskClients(String name, List<Client> clients) {
        int numHighRisk = 0;
        for (Client client : clients) {
            if (client.calculateRiskScore() >= HIGH_RISK_THRESHOLD) {
                numHighRisk++;
            }
        }

        saveIntToCSV(name, numHighRisk);
        return numHighRisk;
    }


    private int getAndSaveTotalFemales(String name, List<Client> clients) {
        int numFemales = 0;
        for (Client client : clients) {
            if (client.getGender().toLowerCase().trim().equals("female")) {
                numFemales++;
            }
        }

        saveIntToCSV(name, numFemales);
        return numFemales;
    }


    private int getAndSaveTotalMales(String name, List<Client> clients) {
        int numMales = 0;
        for (Client client : clients) {
            if (client.getGender().toLowerCase().trim().equals("male")) {
                numMales++;
            }
        }

        saveIntToCSV(name, numMales);
        return numMales;
    }


    private double getAndSaveAverageAge(String name, List<Client> clients) {
        double averageAge = 0.0;
        for (Client client : clients) {
            averageAge += client.getAge();
        }

        averageAge = roundPrecision(averageAge / clients.size(), 1);
        saveDoubleToCSV(name, averageAge);
        return averageAge;
    }


    private double getAndSaveAverageHealthRisk(String name, List<Client> clients) {
        double averageHealthRisk = 0.0;
        for (Client client : clients) {
            averageHealthRisk += client.getHealthRisk();
        }

        averageHealthRisk = roundPrecision(averageHealthRisk / clients.size(), 1);
        saveDoubleToCSV(name, averageHealthRisk);
        return averageHealthRisk;
    }


    private double getAndSaveAverageSocialRisk(String name, List<Client> clients) {
        double averageSocialRisk = 0.0;
        for (Client client : clients) {
            averageSocialRisk += client.getSocialRisk();
        }

        averageSocialRisk = roundPrecision(averageSocialRisk / clients.size(), 1);
        saveDoubleToCSV(name, averageSocialRisk);
        return averageSocialRisk;
    }


    private double getAndSaveAverageEducationRisk(String name, List<Client> clients) {
        double averageEducationRisk = 0.0;
        for (Client client : clients) {
            averageEducationRisk += client.getEducationRisk();
        }

        averageEducationRisk = roundPrecision(averageEducationRisk / clients.size(), 1);
        saveDoubleToCSV(name, averageEducationRisk);
        return averageEducationRisk;
    }


    private double getAndSaveAverageRiskScore(String name, List<Client> clients) {
        double averageRiskScore = 0.0;
        for (Client client : clients) {
            averageRiskScore += client.calculateRiskScore();
        }

        averageRiskScore = roundPrecision(averageRiskScore / clients.size(), 1);
        saveDoubleToCSV(name, averageRiskScore);
        return averageRiskScore;
    }


//  https://stackoverflow.com/questions/22186778/using-math-round-to-round-to-one-decimal-place
    private double roundPrecision(double num, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(num * scale) / scale;
    }


    private void setupBaselineSurveyStats(View view) {
        apiService.baselineSurveyService.getBaselineSurveys().enqueue(new Callback<List<BaselineSurvey>>() {
            @Override
            public void onResponse(Call<List<BaselineSurvey>> call, Response<List<BaselineSurvey>> response) {
                if (response.isSuccessful()) {
                    List<BaselineSurvey> surveys = response.body();

                    int totalVeryPoor = getAndSaveTotalVeryPoor("totalVeryPoor", surveys);
                    int totalPoor = getAndSaveTotalPoor("totalPoor", surveys);
                    int totalFine = getAndSaveTotalFine("totalFine", surveys);
                    int totalGood = getAndSaveTotalGood("totalGood", surveys);

                    setTextViewInteger(R.id.statistic3_sub1, totalVeryPoor);
                    setTextViewInteger(R.id.statistic3_sub2, totalPoor);
                    setTextViewInteger(R.id.statistic3_sub3, totalFine);
                    setTextViewInteger(R.id.statistic3_sub4, totalGood);
                }

            }

            @Override
            public void onFailure(Call<List<BaselineSurvey>> call, Throwable t) {

            }
        });
    }


    private int getAndSaveTotalVeryPoor(String name, List<BaselineSurvey> surveys) {
        int totalVeryPoor = 0;
        for (BaselineSurvey survey : surveys) {
            if (survey.getGeneralHealth().toLowerCase().trim().equals("very poor")) {
                totalVeryPoor++;
            }
        }

        saveIntToCSV(name, totalVeryPoor);
        return totalVeryPoor;
    }


    private int getAndSaveTotalPoor(String name, List<BaselineSurvey> surveys) {
        int totalPoor = 0;
        for (BaselineSurvey survey : surveys) {
            if (survey.getGeneralHealth().toLowerCase().trim().equals("poor")) {
                totalPoor++;
            }
        }

        saveIntToCSV(name, totalPoor);
        return totalPoor;
    }


    private int getAndSaveTotalFine(String name, List<BaselineSurvey> surveys) {
        int totalFine = 0;
        for (BaselineSurvey survey : surveys) {
            if (survey.getGeneralHealth().toLowerCase().trim().equals("fine")) {
                totalFine++;
            }
        }

        saveIntToCSV(name, totalFine);
        return totalFine;
    }


    private int getAndSaveTotalGood(String name, List<BaselineSurvey> surveys) {
        int totalGood = 0;
        for (BaselineSurvey survey : surveys) {
            if (survey.getGeneralHealth().toLowerCase().trim().equals("good")) {
                totalGood++;
            }
        }

        saveIntToCSV(name, totalGood);
        return totalGood;
    }
    

    private void setupNumberOfVisits(View view) {
        visitViewModel.getVisitsAsLiveData().observe(getViewLifecycleOwner(), visits -> {
            int numVisits = visits.size();
            setTextViewInteger(R.id.statistic4_sub1, numVisits);
        });
    }


    private void setTextViewInteger(int id, int value) {
        TextView integerView = view.findViewById(id);
        integerView.setText(Integer.toString(value));
    }


    private void setTextViewDouble(int id, double value) {
        TextView doubleView = view.findViewById(id);
        doubleView.setText(Double.toString(value));
    }


    private void saveIntToCSV(String name, int value) {
        csvFields.put(name, Integer.toString(value));
    }


    private void saveDoubleToCSV(String name, double value) {
        csvFields.put(name, Double.toString(value));
    }


    private void exportCSV(View view) {
        String fileName = "statistics.csv";
        StringBuilder data = getData(view);

        try {
            FileOutputStream out = getActivity().openFileOutput(fileName, Context.MODE_PRIVATE);
            out.write((data.toString()).getBytes());
            out.close();

            Intent fileIntent = setupFileIntent(fileName);
            startActivity(Intent.createChooser(fileIntent, "Send mail"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    private StringBuilder getData(View view) {

        String separator = ", ";

        String nameFields = "";
        String valueFields = "";

        Iterator<Map.Entry<String, String>> it = csvFields.entrySet().iterator();
        Map.Entry<String, String> firstEntry = it.next();
        nameFields += firstEntry.getKey();
        valueFields += firstEntry.getValue();

        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            nameFields += separator + entry.getKey();
            valueFields += separator + entry.getValue();
        }

        System.out.println(valueFields);

        StringBuilder data = new StringBuilder();
        data.append(nameFields + "\n");
        data.append(valueFields);

        return data;
    }


    private Intent setupFileIntent(String fileName) {
        File fileLocation = new File(getActivity().getFilesDir(), fileName);
        Uri path = FileProvider.getUriForFile(getContext(), "com.example.android.fileprovider", fileLocation);
        Intent fileIntent = new Intent(Intent.ACTION_SEND);
        fileIntent.setType("text/csv");
        fileIntent.putExtra(Intent.EXTRA_SUBJECT, "Export Statistics Data");
        fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        fileIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        fileIntent.putExtra(Intent.EXTRA_STREAM, path);
        return fileIntent;
    }

}
