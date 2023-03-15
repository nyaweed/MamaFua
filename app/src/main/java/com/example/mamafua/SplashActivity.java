package com.example.mamafua;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        mAuth = FirebaseAuth.getInstance();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // User is signed in. Proceed to main activity.
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        } else {
            // User is not signed in. Proceed to sign-in activity.
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        }
    }
}

