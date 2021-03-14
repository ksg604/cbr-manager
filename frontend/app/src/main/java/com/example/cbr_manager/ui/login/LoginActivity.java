package com.example.cbr_manager.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.cbr_manager.NavigationActivity;
import com.example.cbr_manager.R;
import com.example.cbr_manager.di.SharedPreferencesHelper;
import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.auth.AuthDetail;
import com.example.cbr_manager.service.auth.LoginUserPass;
import com.example.cbr_manager.service.user.User;
import com.example.cbr_manager.utils.ErrorParser;
import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity {

    private static APIService apiService = APIService.getInstance();
    @Inject
    SharedPreferencesHelper sharedPreferencesHelper;
    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        handleCachedAuthToken();


        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final Button buttonNewUser = findViewById(R.id.buttonNewUser);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

//                //Complete and destroy login activity once successful
//                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingProgressBar.setVisibility(View.VISIBLE);

                LoginUserPass credential = new LoginUserPass(usernameEditText.getText().toString(), passwordEditText.getText().toString());
                apiService.authenticate(credential, new Callback<AuthDetail>() {
                    @Override
                    public void onResponse(Call<AuthDetail> call, Response<AuthDetail> response) {
                        if (apiService.isAuthenticated()) {
                            AuthDetail authResponse = response.body();
                            onLoginSuccess(authResponse);
                        } else {
                            handleAuthError(view, response);
                        }
                        hideKeyBoard(view);
                        loadingProgressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onFailure(Call<AuthDetail> call, Throwable t) {
                        Snackbar.make(view, "Connection failed to server", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        hideKeyBoard(view);
                        loadingProgressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });
    }

    private void handleCachedAuthToken() {
        String cachedToken = getCachedAuthToken();
        if (!cachedToken.isEmpty()) {
            apiService.authenticate(cachedToken, new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        onLoginSuccess(apiService.authService.getAuthDetail());
                    } else {
                        clearCachedToken();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    clearCachedToken();
                }
            });
        }
    }

    private void onLoginSuccess(AuthDetail authResponse) {
        cacheAuthToken(authResponse.token);

        Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
        intent.putExtra(NavigationActivity.KEY_SNACK_BAR_MESSAGE, "Welcome " + authResponse.user.getFirstName());
        startActivity(intent);
    }

    private void clearCachedToken() {
        sharedPreferencesHelper.setAuthToken("");
    }

    private void cacheAuthToken(String token) {
        sharedPreferencesHelper.setAuthToken(token);
    }

    private String getCachedAuthToken() {
        return sharedPreferencesHelper.getAuthToken();
    }

    private void handleAuthError(View view, Response response) {
        ErrorParser errorParser = new ErrorParser(response.errorBody());
        Snackbar.make(view, errorParser.toString(), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    private void hideKeyBoard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }


}