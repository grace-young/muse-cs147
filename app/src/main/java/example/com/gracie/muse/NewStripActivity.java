package example.com.gracie.muse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewStripActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_strip);

        setTitle("Create New Strip");

        String arrayAsString = getIntent().getExtras().getString("striparray");
        List<Strip> list = Arrays.asList(new Gson().fromJson(arrayAsString, Strip[].class));
        ArrayList<Strip> stripArray = new ArrayList<Strip>(list); //hopefully converts ??????

        Log.d("datas", "Got data back from Gson");
        for(int i = 0; i < stripArray.size(); i++){
            Log.d("datas", stripArray.get(i).toString());
        }
        Log.d("datas", "finished printing");

    }
}
