package example.com.gracie.muse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Sign Up");
    }

    public void toOnboarding(View view) {
        Intent intent = new Intent(this, OnboardingWelcome.class);
        startActivity(intent);
    }
}
