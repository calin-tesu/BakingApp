package com.example.android.bakingapp.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapters.RecipeAdapter;
import com.example.android.bakingapp.api.GetDataService;
import com.example.android.bakingapp.api.RetrofitClientInstance;
import com.example.android.bakingapp.models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private List<Recipe> recipes;
    private RecipeAdapter mRecipeAdapter;
    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switch (getResources().getConfiguration().orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                mLayoutManager = new GridLayoutManager(this, 1);
                break;
            case Configuration.ORIENTATION_LANDSCAPE:
                mLayoutManager = new GridLayoutManager(this, 2);
                break;
        }

        mRecyclerView = findViewById(R.id.rv_recipes);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);


        //Creating an object of our api interface
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        //Calling JSON
        Call<List<Recipe>> call = service.getAllRecipes();

        //Enqueue Callback will be call when get response
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                recipes = response.body();
                mRecipeAdapter = new RecipeAdapter(recipes);
                mRecyclerView.setAdapter(mRecipeAdapter);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Toast.makeText(MainActivity.this, getString(R.string.error_loading), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
