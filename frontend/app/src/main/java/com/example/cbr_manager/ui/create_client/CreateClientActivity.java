package com.example.cbr_manager.ui.create_client;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.cbr_manager.R;

public class CreateClientActivity extends AppCompatActivity {

    private ClientCreatePagerAdapter clientCreatePagerAdapter;
    private ViewPager createClientViewPager;

    public enum CreateClientFragments {
      CONSENT,
      VILLAGE_INFO,
      PERSONAL_INFO,
      DISABILITY,
      CAREGIVER_INFO,
      PHOTO
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_client);
        setTitle("Create Client");
        clientCreatePagerAdapter = new ClientCreatePagerAdapter(getSupportFragmentManager(), 0);
        createClientViewPager = findViewById(R.id.container);
        setupViewPager(createClientViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ClientCreatePagerAdapter adapter = new ClientCreatePagerAdapter(getSupportFragmentManager(), 0);

//      Fragment flow order is dependent on the order of these addFragment() functions
//      This MUST match the order of the CreateClientFragments enum above.
        adapter.addFragment(new ConsentFragment());
        adapter.addFragment(new VillageInfoFragment());
        adapter.addFragment(new PersonalInfoFragment());
        adapter.addFragment(new DisabilityFragment());
        adapter.addFragment(new CaregiverInfoFragment());
        adapter.addFragment(new PhotoFragment());

        viewPager.setAdapter(adapter);
    }

    public void setViewPager(final int fragmentNumber) {
        createClientViewPager.setCurrentItem(fragmentNumber);
    }
}
