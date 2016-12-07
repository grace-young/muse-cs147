package example.com.gracie.muse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class OnboardingStripIntro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_strip_intro);
    }

    public void toPanel(View view) {
        Intent intent = new Intent(this, OnboardingPanelIntro.class);
        startActivity(intent);
    }
}
