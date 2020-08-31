package com.koleychik.maindicki.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.koleychik.maindicki.R;
import com.koleychik.maindicki.additions.Keys;
import com.koleychik.maindicki.additions.SheepGoing;
import com.koleychik.maindicki.additions.Style;
import com.koleychik.maindicki.coroutines.PutToSPrefStyle;
import com.koleychik.maindicki.testShop.TestShop;
import com.koleychik.maindicki.testShop.TestShopBg;
import com.koleychik.maindicki.testShop.TestShopBtn;
import com.koleychik.maindicki.testShop.TestShopSheep;
import com.koleychik.maindicki.testShop.TestShopTextColor;
import com.koleychik.maindicki.ui.viewModel.MainViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class
MainActivity extends AppCompatActivity {

    //   for test Shop
    Button btnBuy, btnBack, btnNext, btnExit;
    TextView textPrice;
    ImageView appleImage;
//  end

    Button btnWrite, btnTraining, btnShowAll, btnShop;
    View llShop;
    ImageView sheep, imgInfo;
    TextView apple;
    View mainLL;
    private List<Button> listBtn = new ArrayList<>();
    private List<TextView> listText = new ArrayList<>();

    MainViewModel viewModel;

    TestShop testShop;

    SheepGoing sheepGoing = new SheepGoing(MainActivity.this, Keys.MAIN_ACTIVITY_FOR_SPREF, R.string.dialog_first_time_main_activity);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        init();
        setStyle();
        getApple();
        setOnClickInfo();

//        viewModel.checkIfFirstTime();

        sheepGoing.checkIsItFirstTime();

        if (isTestShop()) {
            initItemShop();
            checkWhichShopWasClicked();
            setOnClickListenerForBtnExit();
            makeOnClickListenerForBtnTestShop();
        } else {
            setOnClickListener();
        }
    }

    private void setOnClickListener() {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.buttonwrite:
                        startActivity(new Intent(MainActivity.this, WriteActivity.class));
                        break;
                    case R.id.buttontraining:
                        startActivity(new Intent(MainActivity.this, ChooseLevelActivity.class));
                        break;
                    case R.id.buttonallwords:
                        startActivity(new Intent(MainActivity.this, ShowAllWordsActivity.class));
                        break;
                    case R.id.buttonShop:
                        startActivity(new Intent(MainActivity.this, ShopActivity.class));
                        break;
                }
            }
        };
        btnWrite.setOnClickListener(onClickListener);
        btnTraining.setOnClickListener(onClickListener);
        btnShowAll.setOnClickListener(onClickListener);
        btnShop.setOnClickListener(onClickListener);
    }

    private void setOnClickListenerForBtnExit() {
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ShopActivity.class));
            }
        });
    }

    //    @SuppressWarnings("all")
    private boolean isTestShop() {
        Intent getIntent = getIntent();
        try {
            return Objects.requireNonNull(getIntent.getExtras()).getBoolean(Keys.IS_TEST, false);
        } catch (Exception ignore) {
            return false;
        }
    }

    //    @SuppressWarnings("all")
    private void checkWhichShopWasClicked() {
        Intent getIntent = getIntent();
        switch (Objects.requireNonNull(getIntent.getExtras()).getInt(Keys.SHOP_ACTIVITY, 1)) {
            case 0:
                testShop = new TestShopBg(mainLL, btnBuy, apple);
                break;
            case 1:
                testShop = new TestShopBtn(listBtn, btnBuy, apple);
                break;
            case 2:
                testShop = new TestShopTextColor(listText, listBtn, btnBuy, apple);
                break;
            case 3:
                testShop = new TestShopSheep(sheep, btnBuy, apple);
                break;
        }
    }

    private void makeOnClickListenerForBtnTestShop(){
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.btnBackTest:
                        testShop.makeBack();
                        break;
                    case R.id.btnForwardTest:
                        testShop.makeNext();

                        break;
                }
            }
        };
        btnBack.setOnClickListener(onClickListener);
        btnNext.setOnClickListener(onClickListener);
    }


    private void initItemShop() {
        List<Button> listBtn = new ArrayList<>();
        List<TextView> listTV = new ArrayList<>();

        btnBack = findViewById(R.id.btnBackTest);
        btnBuy = findViewById(R.id.btnBuy);
        btnNext = findViewById(R.id.btnForwardTest);
        btnExit = findViewById(R.id.btnExitTest);
        llShop = findViewById(R.id.shopTest);

        listBtn.add(btnBack);
        listBtn.add(btnBuy);
        listBtn.add(btnNext);
        listBtn.add(btnExit);

        textPrice = findViewById(R.id.priceTest);

        listTV.add(textPrice);

        appleImage = findViewById(R.id.imageViewAppleTest);

//      makeVisible
        btnBuy.setVisibility(View.VISIBLE);
        llShop.setVisibility(View.VISIBLE);

        textPrice.setVisibility(View.VISIBLE);
        appleImage.setVisibility(View.VISIBLE);

        setStyleForShopTest(listBtn, listTV);
    }

    private void getApple() {
        apple.setText(viewModel.getApple());
    }

    private void init() {
//      init btn
        btnWrite = findViewById(R.id.buttonwrite);
        btnTraining = findViewById(R.id.buttontraining);
        btnShowAll = findViewById(R.id.buttonallwords);
        btnShop = findViewById(R.id.buttonShop);

//      add to listBtn
        listBtn.add(btnWrite);
        listBtn.add(btnTraining);
        listBtn.add(btnShowAll);
        listBtn.add(btnShop);

//      init ImageView
        sheep = findViewById(R.id.imageViewSheep);
        imgInfo = findViewById(R.id.img_info);

//      init TextView
        apple = findViewById(R.id.textAppleMainActivity);

//      add to listTextView
        listText.add(apple);

//      init layout
        mainLL = findViewById(R.id.mainLL);
    }

    private void setStyleForShopTest(List<Button> listBtn, List<TextView> listText) {
        Style style = new Style();
        style.setBtn(listBtn);
        style.setTV(listText, listBtn);
    }

    private void setStyle() {
        Style style = new Style();
        style.setBg(mainLL);
        style.setBtn(listBtn);
        style.setTV(listText, listBtn);
        style.setSheep(sheep);
    }

    private void setOnClickInfo() {
        imgInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sheepGoing.startSheep();
            }
        });
    }

    @Override
    public void onBackPressed() {}

    @Override
    protected void onResume() {
        super.onResume();

        if (viewModel.getSizeDb() < 3) {
            Log.d("sizeBd", String.valueOf(viewModel.getSizeDb()));
            btnTraining.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainActivity.this, getString(R.string.needWriteMoreThen2Words), Toast.LENGTH_LONG).show();
                }
            });
        } else {
            btnTraining.setEnabled(true);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (testShop != null) {
            testShop.returnToMainStyle();
            PutToSPrefStyle putToSPrefStyle = new PutToSPrefStyle(getSharedPreferences(Keys.APP_NAME_FOR_SPREF, MODE_PRIVATE));
            putToSPrefStyle.putToSPref();
        }
    }
}