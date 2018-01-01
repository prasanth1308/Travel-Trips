package com.technoblaze.traveltrips.traveltrips;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {


    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView, mFullNameView, mMobileNuberView;

    // [START declare_auth]
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();;
    // [END declare_auth]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Set up the sign up form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email_sign_up);
        mPasswordView = (EditText) findViewById(R.id.password_sign_up);
        mFullNameView = (EditText) findViewById(R.id.fullname_sign_up);
        mMobileNuberView = (EditText) findViewById(R.id.mobile_sign_up);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_up_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSignUp();
            }
        });
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private void attemptSignUp() {

        boolean cancel = false;
        View focusView = null;

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String name = mFullNameView.getText().toString();
        String phone = mMobileNuberView.getText().toString();

        TextInputLayout emailLayout = (TextInputLayout) findViewById(R.id.email_sign_up_input_layout);
        TextInputLayout passwordLayout = (TextInputLayout) findViewById(R.id.password_sign_up_input_layout);
        TextInputLayout fullNameLayout = (TextInputLayout) findViewById(R.id.fullname_sign_up_input_layout);
        TextInputLayout mobileNumberLayout = (TextInputLayout) findViewById(R.id.mobile_sign_up_input_layout);

        emailLayout.setError(null);
        passwordLayout.setError(null);
        fullNameLayout.setError(null);
        mobileNumberLayout.setError(null);

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(phone)) {
            //mPasswordView.setError(getString(R.string.error_invalid_password));
            mobileNumberLayout.setError(getString(R.string.phone_field_required));
            focusView = mPasswordView;
            cancel = true;
        } else if (!isPhoneValid(phone)) {
            //mEmailView.setError(getString(R.string.error_invalid_email));
            mobileNumberLayout.setError(getString(R.string.error_invalid_phone));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(name)) {
            //mPasswordView.setError(getString(R.string.error_invalid_password));
            fullNameLayout.setError(getString(R.string.fullname_field_required));
            focusView = mFullNameView;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            //mPasswordView.setError(getString(R.string.error_invalid_password));
            passwordLayout.setError(getString(R.string.password_field_required));
            focusView = mPasswordView;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            //mEmailView.setError(getString(R.string.error_invalid_email));
            passwordLayout.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            //mEmailView.setError(getString(R.string.error_field_required));
            emailLayout.setError(getString(R.string.email_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            //mEmailView.setError(getString(R.string.error_invalid_email));
            emailLayout.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user sign up attempt.
            sendSignUpRequest(email, password);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }


    private boolean isPhoneValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() == 10;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private void sendSignUpRequest(String email, String password) {
        //create user
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(SignUpActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            FirebaseUser user = mAuth.getCurrentUser();
                            user.sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(SignUpActivity.this, "Verification Email sent..",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }
                             });
                            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                            finish();
                        }
                    }
                });

    }


    public void click(View view) {
        Intent intent = null;
        switch(view.getId()) {
            case R.id.signUpLogin:
                intent = new Intent(this, LoginActivity.class);
                break;
            case R.id.terms_conditions_label:
                intent = new Intent(this, TermsConditionsActivity.class);
                break;
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }



}
