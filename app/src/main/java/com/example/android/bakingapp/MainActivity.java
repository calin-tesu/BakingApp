package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bakingapp.api.GetDataService;
import com.example.android.bakingapp.api.RetrofitClientInstance;
import com.example.android.bakingapp.models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private List<Recipe> recipes;
    private TextView mTestingView;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTestingView = findViewById(R.id.testing);

        //Creating an object of our api interface
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        //Calling JSON
        Call<List<Recipe>> call = service.getAllRecipes();

        //Enqueue Callback will be call when get response
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                recipes = response.body();
                mTestingView.setText(recipes.get(0).getName());
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
