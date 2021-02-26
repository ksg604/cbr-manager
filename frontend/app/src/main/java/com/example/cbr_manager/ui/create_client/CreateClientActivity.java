package com.example.cbr_manager.ui.create_client;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.client.Client;

public class CreateClientActivity extends AppCompatActivity {

    private ClientCreatePagerAdapter clientCreatePagerAdapter;
    private ViewPager createClientViewPager;

    //Base client object for creating new clients.
    Client client = new Client();

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        clientCreatePagerAdapter = new ClientCreatePagerAdapter(getSupportFragmentManager(), 0);
        createClientViewPager = findViewById(R.id.container);
        setupViewPager(createClientViewPager);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)ev.getRawX(), (int)ev.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
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

    public Client getClient() {
        return client;
    }
    public void setConsentInfo(String consent, String date) {
        client.setConsent(consent);
        client.setDate(date);
    }
    public void setPersonalInfo(String firstName, String lastName, int contactClient, int age, String gender) {
        client.setFirstName(firstName);
        client.setLastName(lastName);
        client.setContactClient(contactClient);
        client.setAge(age);
        client.setGender(gender);
    }
    public void setVillageInfo(String location, int id, int villageNo) {
        client.setLocation(location);
        client.setId(id);
        client.setVillageNo(villageNo);
    }
    public void setDisabilityInfo(String disability) {
        client.setDisability(disability);
    }
    public void setCaregiverInfo(String carePresent, int contactCare) {
        client.setCarePresent(carePresent);
        client.setContactCare(contactCare);
    }
}
