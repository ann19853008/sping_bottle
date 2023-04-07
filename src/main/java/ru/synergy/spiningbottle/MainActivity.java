package ru.synergy.spiningbottle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ImageView bottle;
    private int last_dir;
    private boolean spinning;
    private Random random = new Random();
    private Wish[] wishes=new Wish[]{
            new Wish("Отжатся пять раз"),
            new Wish("Похрюкать на улице в лицо прохожим"),
            new Wish("Ходить гуськом 30 секунд"),
            new Wish("Сделать 2 кувырка")

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottle = findViewById(R.id.bottle);
    }

    public void spinBottle( final View view) {
        if (!spinning) {

            int new_dir = random.nextInt(2160);
            float pointWidth = bottle.getWidth() / 2;
            float pointHeight = bottle.getHeight() / 2;
            Animation rotation = new RotateAnimation(last_dir, new_dir, pointWidth, pointHeight);
            rotation.setDuration(2700);
            rotation.setFillAfter(true);
            rotation.setAnimationListener(new Animation.AnimationListener() {

                @Override
                public void onAnimationStart(Animation animation) {

                    spinning = true;
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    spinning = false;
                    popupWishForPlayer(view);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            popupWishForPlayer(view);

                        }
                    },1000);
                }




                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            last_dir = new_dir;
            bottle.startAnimation(rotation);
        }
    }
    private void popupWishForPlayer(View view){
        LayoutInflater inflater = getLayoutInflater();
        View popupView = inflater.inflate(R.layout.popup_wish,null);
        int random_index = random.nextInt(wishes.length);
        TextView wish_massage= popupView.findViewById(R.id.wish_for_player);
        wish_massage.setText(wishes[random_index].getWish());
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView,width,height,focusable);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0,0);

    }
}