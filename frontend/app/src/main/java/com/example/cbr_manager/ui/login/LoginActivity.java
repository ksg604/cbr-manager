package com.example.cbr_manager.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.example.cbr_manager.service.auth.AuthService;
import com.example.cbr_manager.service.auth.LoginUserPass;
import com.example.cbr_manager.ui.AuthViewModel;
import com.example.cbr_manager.utils.ErrorParser;
import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity {

    private static String TAG = "LoginActivity";

    @Inject
    SharedPreferencesHelper sharedPreferencesHelper;
    AuthViewModel authViewModel;
    ProgressBar loadingProgressBar;
    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        loadingProgressBar = findViewById(R.id.loading);

        handleCachedAuthToken();

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

                LoginUserPass loginUserPass = new LoginUserPass(usernameEditText.getText().toString(), passwordEditText.getText().toString());

                authViewModel.login(loginUserPass).subscribe(new SingleObserver<AuthDetail>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        hideKeyBoard(view);
                    }

                    @Override
                    public void onSuccess(@NonNull AuthDetail authDetail) {
                        onLoginSuccess(authDetail, loginUserPass);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        onLoginFailure(e.getMessage());
                    }
                });
            }
        });
    }

    private void handleCachedAuthToken() {
        String cachedToken = getCachedAuthToken();
        if (!cachedToken.isEmpty()) {
            authViewModel.cachedLogin().subscribe(new SingleObserver<AuthDetail>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {
                    loadingProgressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onSuccess(@NonNull AuthDetail authDetail) {
                    Log.d(TAG, "onSuccess: caching token");
                    onLoginSuccess(authDetail, null);
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    onLoginFailure(e.getMessage());
                    Log.d(TAG, "onError: failed to authenticate token. Reason: " + e.getMessage());
                    sharedPreferencesHelper.setAuthToken("");
                }
            });
        }
    }

    private void onLoginSuccess(AuthDetail authDetail, LoginUserPass loginUserPass) {
        loadingProgressBar.setVisibility(View.INVISIBLE);

        // todo: remove these functions
        APIService apiService = APIService.getInstance();
        apiService.initializeServices(authDetail.token);
        apiService.authService = new AuthService(loginUserPass);

        Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
        intent.putExtra(NavigationActivity.KEY_SNACK_BAR_MESSAGE, "Welcome " + authDetail.user.getFirstName());
        startActivity(intent);

//        finish();
    }

    private void onLoginFailure(String errorMessage) {
        loadingProgressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
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
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }


}