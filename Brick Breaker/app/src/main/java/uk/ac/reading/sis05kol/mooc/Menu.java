package uk.ac.reading.sis05kol.mooc;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.content.Intent;
import android.widget.LinearLayout;
import android.graphics.drawable.AnimationDrawable;
import android.widget.TextView;

public class Menu extends Activity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        LinearLayout linearLayout = findViewById(R.id.layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(500);
        animationDrawable.setExitFadeDuration(500);
        animationDrawable.start();
    }

    public void mainMenu(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}
