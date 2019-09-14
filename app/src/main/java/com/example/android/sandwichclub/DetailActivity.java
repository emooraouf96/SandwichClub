package com.example.android.sandwichclub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.sandwichclub.model.Sandwich;
import com.example.android.sandwichclub.utils.JsonUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    public static Sandwich sandwich = null;
    TextView mainName;
    TextView origin;
    TextView discription;
    TextView otherNamesDisplay;
    TextView ingredientDisplay;
    ImageView sandwichImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mainName = findViewById(R.id.MainName);
        origin = findViewById(R.id.origin_tv);
        discription = findViewById(R.id.description);
        ingredientDisplay = findViewById(R.id.ingredients);
        otherNamesDisplay = findViewById(R.id.OtherName);
        sandwichImage = findViewById(R.id.image_iv);
        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
            Log.v("SandwitchMainName_log2", sandwich.getMainName());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }
        populateUI();
    }

    private void closeOnError() {
        finish();

        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {


        mainName.setText("- " + sandwich.getMainName());
        discription.setText(sandwich.getDescription());
        Log.v("sandwichImage",sandwich.getImage());
        Picasso.with(this).load(sandwich.getImage()).placeholder(R.drawable.placeholder).into(sandwichImage);
        Log.v("sandwichImage2",sandwich.getImage());
        if(sandwich.getPlaceOfOrigin()!=null) {
           origin.setText(sandwich.getPlaceOfOrigin());
        }
        ArrayList ingredients = (ArrayList) sandwich.getIngredients();
        if (ingredients != null) {
            for (int i = 0; i < ingredients.size(); i++) {
                ingredientDisplay.append("- " + ingredients.get(i).toString());
                ingredientDisplay.append("\n" + '\n');
            }
        }
        ArrayList otherNames = (ArrayList) sandwich.getAlsoKnownAs();
            if (otherNames != null) {
                for (int i = 0; i < otherNames.size(); i++)
                    otherNamesDisplay.append("- " + otherNames.get(i).toString());
                    otherNamesDisplay.append("\n" + '\n');
                }
            }
}
