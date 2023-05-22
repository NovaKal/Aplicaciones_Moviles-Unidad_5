package co.edu.upb.memoryrace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Game extends AppCompatActivity {

    ImageButton game_imageButton_exit;
    Button game_button_restart, game_button_card_menu;
    ImageButton[] cards = new ImageButton[16];
    TextView game_text_points;
    int points, correct;

    int[] images;
    int card_background;

    ArrayList<Integer> cardsShuffled;
    ImageButton first_selection;
    int first_pair, second_pair;
    boolean paired = false;
    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        game_imageButton_exit = findViewById(R.id.game_button_exit);
        game_button_restart = findViewById(R.id.game_button_restart);
        game_button_card_menu = findViewById(R.id.game_button_card_menu);

        game_imageButton_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentHome = new Intent();
                startActivity(intentHome);
                finish();
            }
        });

        game_button_card_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Here goes card menu Intent
            }
        });
        startGame();

        game_button_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });
    }

    private void loadTable() {
        cards[0] = findViewById(R.id.game_card_00);
        cards[1] = findViewById(R.id.game_card_01);
        cards[2] = findViewById(R.id.game_card_02);
        cards[3] = findViewById(R.id.game_card_03);
        cards[4] = findViewById(R.id.game_card_10);
        cards[5] = findViewById(R.id.game_card_11);
        cards[6] = findViewById(R.id.game_card_12);
        cards[7] = findViewById(R.id.game_card_13);
        cards[8] = findViewById(R.id.game_card_20);
        cards[9] = findViewById(R.id.game_card_21);
        cards[10] = findViewById(R.id.game_card_22);
        cards[11] = findViewById(R.id.game_card_23);
        cards[12] = findViewById(R.id.game_card_30);
        cards[13] = findViewById(R.id.game_card_31);
        cards[14] = findViewById(R.id.game_card_32);
        cards[15] = findViewById(R.id.game_card_33);
    }

    private void loadText() {
        game_text_points = findViewById(R.id.game_text_points);
        points = 0;
        correct = 0;
        game_text_points.setText("Points: " + points);
    }

    private void loadImages() {
        images = new int[]{
                R.drawable.elephant,
                R.drawable.fox,
                R.drawable.frog,
                R.drawable.hen,
                R.drawable.panda,
                R.drawable.parrot,
                R.drawable.pig,
                R.drawable.rooster
        };
        card_background = R.drawable.card_background_1;
    }

    private ArrayList<Integer> shuffleCards(int len) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < len * 2; i++) {
            result.add(i % len);
        }
        Collections.shuffle(result);
        return result;
    }

    private void check(int i, final ImageButton imgb){
        if(first_selection == null){
            first_selection = imgb;
            first_selection.setScaleType(ImageView.ScaleType.CENTER_CROP);
            first_selection.setImageResource(images[cardsShuffled.get(i)]);
            first_selection.setEnabled(false);
            first_pair = cardsShuffled.get(i);
        } else {
            paired = true;
            imgb.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imgb.setImageResource(images[cardsShuffled.get(i)]);
            imgb.setEnabled(false);
            second_pair = cardsShuffled.get(i);
            if(first_pair == second_pair){
                first_selection = null;
                paired = false;
                correct++;
                points++;
                game_text_points.setText(String.format("Points: %d", points));
                if(correct == images.length){
                    Toast toast = Toast.makeText(getApplicationContext(), "You Win!", Toast.LENGTH_LONG);
                    toast.show();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intentHome = new Intent(getApplicationContext(), Home.class);
                            startActivity(intentHome);
                            finish();
                        }
                    },1000);
                }
            } else {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        first_selection.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        first_selection.setImageResource(card_background);
                        first_selection.setEnabled(true);
                        imgb.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        imgb.setImageResource(card_background);
                        imgb.setEnabled(true);
                        paired = false;
                        first_selection = null;
                        points--;
                        game_text_points.setText("Points: " + points);
                    }
                }, 1000);
            }
        }
    }

    private void startGame() {
        loadTable();
        loadText();
        loadImages();
        cardsShuffled = shuffleCards(images.length);
        for (int i = 0; i < cards.length; i++) {
            cards[i].setScaleType(ImageView.ScaleType.CENTER_CROP);
            cards[i].setImageResource(images[cardsShuffled.get(i)]);
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < cards.length; i++) {
                    cards[i].setScaleType(ImageView.ScaleType.CENTER_CROP);
                    cards[i].setImageResource(card_background);
                }
            }
        }, 1000);
        for (int i = 0; i < cards.length; i++) {
            final int j = i;
            cards[i].setEnabled(true);
            cards[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!paired)
                        check(j, cards[j]);
                }
            });
        }
    }
}