package co.edu.upb.memoryrace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;
    TextView home_text_title;
    ImageButton home_imageButton_exit, home_imageButton_play, home_imageButton_aboutUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance();
        home_text_title = findViewById(R.id.home_textView_title);
        home_imageButton_exit = findViewById(R.id.home_imageButton_exit);
        home_imageButton_play = findViewById(R.id.home_imageButton_play);
        home_imageButton_aboutUs = findViewById(R.id.home_imageButton_aboutUs);
        user = auth.getCurrentUser();

        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }
        else {
            home_text_title.setText(user.getEmail());
        }

        home_imageButton_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        home_imageButton_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameInit();
            }
        });

        home_imageButton_aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), About.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void gameInit() {
        Intent intentGame = new Intent(getApplicationContext(), Game.class);
        startActivity(intentGame);
        finish();
    }
}