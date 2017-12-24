package com.technoblaze.traveltrips.traveltrips;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.codemybrainsout.onboarder.AhoyOnboarderActivity;
import com.codemybrainsout.onboarder.AhoyOnboarderCard;

import java.util.ArrayList;
import java.util.List;

public class OnBoarding extends AhoyOnboarderActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AhoyOnboarderCard ahoyOnboarderCard1 = new AhoyOnboarderCard("Scan Barcode", "Label your packages with a barcode before we collect it from you.", R.drawable.barcode);
        AhoyOnboarderCard ahoyOnboarderCard2 = new AhoyOnboarderCard("Shipping", "Our huge network of shipping partners ensures that your packages are always on schedule.", R.drawable.truck);
        AhoyOnboarderCard ahoyOnboarderCard3 = new AhoyOnboarderCard("Payment", "Receive payments immediately after the package is delivered.", R.drawable.wallet);

        ahoyOnboarderCard1.setBackgroundColor(R.color.white);
        ahoyOnboarderCard2.setBackgroundColor(R.color.white);
        ahoyOnboarderCard3.setBackgroundColor(R.color.white);

        List<AhoyOnboarderCard> pages = new ArrayList<>();

        pages.add(ahoyOnboarderCard1);
        pages.add(ahoyOnboarderCard2);
        pages.add(ahoyOnboarderCard3);

        for (AhoyOnboarderCard page : pages) {
            page.setTitleColor(R.color.colorOnBoardTitle);
            page.setDescriptionColor(R.color.colorOnBoardDescription);
        }

        //Set finish button text
        setFinishButtonTitle("Get Started");

        //Show/Hide navigation controls
        showNavigationControls(false);

        //Set pager indicator colors
        setInactiveIndicatorColor(R.color.InActiveIndicatorColor);
        setActiveIndicatorColor(R.color.activeIndicatorColor);

        //Set the finish button style
        setFinishButtonDrawableStyle(ContextCompat.getDrawable(this, R.drawable.rounded_button));

        List<Integer> colorList = new ArrayList<>();
        colorList.add(R.color.white);
        colorList.add(R.color.white);
        colorList.add(R.color.white);

        setColorBackground(colorList);

        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
        setFont(face);

        setOnboardPages(pages);
    }

    @Override
    public void onFinishButtonPressed() {
        finishOnboarding();
        Toast.makeText(this, "Finish Pressed", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(OnBoarding.this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void finishOnboarding() {
        // Get the shared preferences
        SharedPreferences preferences =
                getSharedPreferences("my_preferences", MODE_PRIVATE);

        // Set onboarding_complete to true
        preferences.edit()
                .putBoolean("onboarding_complete",true).apply();

        // Launch the main Activity, called MainActivity
        Intent main = new Intent(this, LoginActivity.class);
        startActivity(main);

        // Close the OnboardingActivity
        finish();
    }

}
