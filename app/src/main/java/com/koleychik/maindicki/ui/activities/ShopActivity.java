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

public class ShopActivity extends AppCompatActivity {

    View mainLL;
    ImageView imgInfo, sheep, bg, btn, textColor, styleSheep;
    TextView apple;
    SheepGoing sheepGoing = new SheepGoing(this, Keys.SHOP_FOR_SPREF, R.string.shop);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        init();

        sheepGoing.checkIsItFirstTime();

        setApple();

        setStyle();

        setOnCLick();

    }

    private void setOnCLick(){
        imgInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sheepGoing.startSheep();
            }
        });

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShopActivity.this, MainActivity.class);
                intent.putExtra(Keys.IS_TEST, true);
                switch (view.getId()){
                    case R.id.imageViewBG:
                        intent.putExtra(Keys.SHOP_ACTIVITY, 0);
                        startActivity(intent);
                        break;
                    case R.id.imageViewBTN:
                        intent.putExtra(Keys.SHOP_ACTIVITY, 1);
                        startActivity(intent);
                        break;
                    case R.id.imageViewTextColor:
                        intent.putExtra(Keys.SHOP_ACTIVITY, 2);
                        startActivity(intent);
                        break;
                    case R.id.imageViewTextShеер:
                        intent.putExtra(Keys.SHOP_ACTIVITY, 3);
                        startActivity(intent);
                        break;
                }
            }
        };
        bg.setOnClickListener(onClickListener);
        btn.setOnClickListener(onClickListener);
        textColor.setOnClickListener(onClickListener);
        styleSheep.setOnClickListener(onClickListener);
    }

    private void setStyle(){
        List<Button> listBtn = new ArrayList<>();
        List<TextView> listTextView = new ArrayList<>();

        Style style = new Style();

        style.setBg(mainLL);
        style.setBtn(listBtn);
        style.setTV(listTextView, listBtn);
        style.setSheep(sheep);
    }

    private void setApple(){
        WorkWithApple workWithApple = new WorkWithApple(getSharedPreferences(Keys.GET_APPLE, MODE_PRIVATE));
        String appleText = workWithApple.getApple();
        if(appleText == null){
            appleText = "0";
        }
        apple.setText(appleText);
    }

    private void init(){
        mainLL = findViewById(R.id.mainLL);
        imgInfo = findViewById(R.id.img_info);
        sheep = findViewById(R.id.imageViewSheep);
        bg = findViewById(R.id.imageViewBG);
        btn = findViewById(R.id.imageViewBTN);
        textColor = findViewById(R.id.imageViewTextColor);
        styleSheep = findViewById(R.id.imageViewTextShеер);

        apple = findViewById(R.id.textAppleMainActivity);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ShopActivity.this, MainActivity.class));
    }
}