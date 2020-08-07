package com.koleychik.maindicki.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.koleychik.maindicki.R;
import com.koleychik.maindicki.additions.Keys;
import com.koleychik.maindicki.additions.SheepGoing;
import com.koleychik.maindicki.additions.Style;
import com.koleychik.maindicki.coroutines.WorkWithApple;

import java.util.ArrayList;
import java.util.List;

public class ChooseLevelActivity extends AppCompatActivity {

    Button btn1, btn2, btn3, btn4;
    ImageView sheep, imgInfo;
    TextView apple;
    View mainLL;

    SheepGoing sheepGoing = new SheepGoing(ChooseLevelActivity.this, Keys.CHOOSE_LEVEL_FOR_SPREF, R.string.training);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_level);

        init();

        sheepGoing.checkIsItFirstTime();

        setStyle();
        setApple();
        setOnClick();
    }

    private void setOnClick(){
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseLevelActivity.this, BeforeLevelActivity.class);
                switch (view.getId()){
                    case R.id.button1:
                        intent.putExtra(Keys.BEFORE_LEVEL, 1);
                        startActivity(intent);
                        break;
                    case R.id.button2:
                        intent.putExtra(Keys.BEFORE_LEVEL, 2);
                        startActivity(intent);
                        break;
                    case R.id.button3:
                        intent.putExtra(Keys.BEFORE_LEVEL, 3);
                        startActivity(intent);
                        break;
                    case R.id.button4:
                        intent.putExtra(Keys.BEFORE_LEVEL, 4);
                        startActivity(intent);
                        break;
                }
            }
        };
        btn1.setOnClickListener(onClickListener);
        btn2.setOnClickListener(onClickListener);
        btn3.setOnClickListener(onClickListener);
        btn4.setOnClickListener(onClickListener);

        imgInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sheepGoing.startSheep();
            }
        });
    }

    private void init(){
        btn1 = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);
        btn4 = findViewById(R.id.button4);

        sheep = findViewById(R.id.imageViewSheep);
        apple = findViewById(R.id.textAppleMainActivity);
        imgInfo = findViewById(R.id.img_info);

        mainLL = findViewById(R.id.mainLL);
    }

    private void setApple(){
        WorkWithApple workWithApple = new WorkWithApple(getSharedPreferences(Keys.APP_NAME_FOR_SPREF, MODE_PRIVATE));
        apple.setText(workWithApple.getApple());
    }

    private void setStyle(){
        List<Button> listBtn = new ArrayList<>();
        List<TextView> listTextView = new ArrayList<>();

//      add to listBtn
        listBtn.add(btn1);
        listBtn.add(btn2);
        listBtn.add(btn3);
        listBtn.add(btn4);

//      add to listTextView
        listTextView.add(apple);

        Style style = new Style();
        style.setBg(mainLL);
        style.setBtn(listBtn);
        style.setTV(listTextView, listBtn);
        style.setSheep(sheep);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ChooseLevelActivity.this, MainActivity.class));
    }
}