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
    ImageButton home_button_exit, home_imageButton_cardSelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance();
        home_text_title = findViewById(R.id.home_textView_title);
        home_button_exit = findViewById(R.id.home_button_exit);
        home_imageButton_cardSelection = findViewById(R.id.home_imageButton_cardSelection);
        user = auth.getCurrentUser();

        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }
        else {
            home_text_title.setText(user.getEmail());
        }

        home_button_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        home_imageButton_cardSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameInit();
            }
        });
    }

    private void gameInit() {
        Intent intentGame = new Intent(getApplicationContext(), Game.class);
        startActivity(intentGame);
        finish();
    }
}