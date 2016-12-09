package example.com.gracie.muse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    StripDataHolder holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Sign Up");
        holder = StripDataHolder.getInstance();
    }

    public void toOnboarding(View view) {
        // Grab username
        CEditText edit_u = (CEditText) findViewById(R.id.edit_username);
        String entered_username = edit_u.getText().toString();
        if(entered_username.matches("")){
            // it's empty
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.custom_toast,
                    (ViewGroup) findViewById(R.id.custom_toast_container));
            TextView text = (TextView) layout.findViewById(R.id.text);
            text.setText("Please enter a username");
            Toast toast = new Toast(getApplicationContext());
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();
            return;
        }

        CEditText edit_em = (CEditText) findViewById(R.id.edit_email);
        String entered_email = edit_em.getText().toString();
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(edit_em.getText()).matches()){
            // ERROR
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.custom_toast,
                    (ViewGroup) findViewById(R.id.custom_toast_container));
            TextView text = (TextView) layout.findViewById(R.id.text);
            text.setText("Please enter a valid email address");
            Toast toast = new Toast(getApplicationContext());
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();
            return;
        }

        CEditText edit_pass = (CEditText) findViewById(R.id.edit_password);
        String entered_pass = edit_pass.getText().toString();
        if(entered_pass.matches("")){
            // it's empty
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.custom_toast,
                    (ViewGroup) findViewById(R.id.custom_toast_container));
            TextView text = (TextView) layout.findViewById(R.id.text);
            text.setText("Please enter a password");
            Toast toast = new Toast(getApplicationContext());
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();
            return;
        }


        holder.updateNewUsername(entered_username);
        Intent intent = new Intent(this, OnboardingWelcome.class);
        startActivity(intent);
    }
}
