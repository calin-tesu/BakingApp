package com.example.android.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingapp.Constants;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.models.Ingredient;
import com.example.android.bakingapp.models.Recipe;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Calin Tesu on 11/15/2018.
 */
public class GridWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(this.getApplicationContext());
    }

    class GridRemoteViewsFactory implements RemoteViewsFactory {

        Context mContext;
        Recipe currentRecipe;
        List<Ingredient> ingredientList;

        public GridRemoteViewsFactory(Context applicationContext) {
            mContext = applicationContext;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            //Retrieve the last clicked recipe from SharedPreferences
            SharedPreferences preferences = mContext.getSharedPreferences(Constants.KEY_CURRENT_RECIPE, Context.MODE_PRIVATE);
            //use gson to retrieve class objects from SharedPreferences
            String jsonRecipe = preferences.getString(Constants.KEY_CURRENT_RECIPE, null);
            Gson gson = new Gson();
            currentRecipe = gson.fromJson(jsonRecipe, Recipe.class);
            ingredientList = currentRecipe.getIngredients();
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            if (ingredientList == null) return 0;
            return ingredientList.size();
        }

        @Override
        public RemoteViews getViewAt(int index) {
            if (ingredientList == null || ingredientList.size() == 0) return null;

            Ingredient currentIngredient = ingredientList.get(index);

            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.baking_widget);

            //Update text for ingredient description
            views.setTextViewText(R.id.ingredient_description, currentIngredient.getIngredient());
            //Update and format text for ingredient quantity and measure
            String measure = String.valueOf(currentIngredient.getQuantity()) + currentIngredient.getMeasure();
            views.setTextViewText(R.id.ingredient_measure, measure);

            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1; // Treat all items in the GridView the same
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
