package com.Mining.app.Money;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.StrictMode;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import com.Mining.app.Money.util.IabHelper;
import com.Mining.app.Money.util.IabResult;
import com.Mining.app.Money.util.Inventory;
import com.Mining.app.Money.util.Purchase;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TimerTask;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity implements RewardedVideoAdListener {
    private RewardedVideoAd mAd;
    private AdView mAdView;
    private static final int START_LEVEL = 1;
    private int mLevel, helios=2, timesEntered, heliosReceived,
            avoidRepetition = 0, d = 18, h = 0, m = 0, s = 15, max = 35, progress = heliosReceived,
            just1 = 0, counter, min = 0, hour = 24, times = 0, anothercounter = 0;
    private InterstitialAd mInterstitialAd;

    String[] permissions = new String[]{
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };
    File loginex = null;
    DataBase dataBase;
    WebView webview;

    GifImageView rocketGif, dogeGif, btcGif;

    Switch lastNews, enableAds;

    TableRow firstWinnerRow, secondWinnerRow, thirdWinnerRow, fourWinnerRow, fifthWinnerRow;

    ImageView arrow1, helioImage, lumberJack, moneyMaker, helio, ticket, theTicket, winners,
            secret, arm, initStart, armTicket, winnerTicket, rateUs, infoStore, cohete, dgbInfo, chest,
            ticketBuy, he, hemed, hemed2, helit, mininghub, validIMG;

    ImageButton homeBtn, messengerBtn, adBtn, profileBtn, infoBtn, editProfile, saveProfile,
            deleteProfile, dice, cashOut, paypal, dgb, send, videoAdd, intersitialAdd, store;

    GridLayout messengerLayout, profileLayout, infoLayout;

    LinearLayout menu;
    ScrollView storeLayout;
    Button login, register, viewWebsite;
    ConstraintLayout mainLayout, withdrawLayout, adLayout, upLayout, lotteryLayout, noInfoLayout;

    TextView titulo, name, email, pass, version, online, gpu, speed, timemining, magia, diff, server,
            newMessageTitulo, messageBody, destinatario, mess, from, money, cashOuText,
            paymentTitle, helioAmount, ticketID, ticketID2, buyHelios, buyDGB, buyMagicChest,
            buyMagicTicket, ticketAmount, countDown, staticCountDown, winnerNumber, firstWinner,
            secondtWinner, thirdWinner, fourWinner, fifthWinner, prizeText;

    String userEmail, firstname, lastname, time = null;

    private static final String path = Environment.getExternalStorageDirectory().getAbsolutePath()
            + "/MiningMoney";

    EditText editName, editPass, editMail, addressInput, promotion;

    boolean DGBtype, valid, goBack, goHome, block = false, guest = true, showTicketID,
            frozenTime = false;


    TableLayout winnerLayout;
    Bundle bundle;
    ProgressBar helioProgress;
    File[] wanted;
    Drawable resImg, img, imga, imgb, pic1, pic2, pic3, pic4;

    private static final String TAG = "InAppBilling";
    IabHelper mHelper;
    static final String ITEM_SKU = "com.mining.app.money";

    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener;

    //------------------------------INITIALISATION----------------------------------------



    private void initialiseElements() {
        viewWebsite = (Button) findViewById(R.id.websitebutton);
        noInfoLayout = (ConstraintLayout) findViewById(R.id.noInfoLayout);
        enableAds = (Switch) findViewById(R.id.activeads);
        login = (Button) findViewById(R.id.loginex);
        register = (Button) findViewById(R.id.registex);
        mininghub = (ImageView) findViewById(R.id.mininghub);
        cohete = (ImageView) findViewById(R.id.cohete);
        dgbInfo = (ImageView) findViewById(R.id.dgbInfo);
        chest = (ImageView) findViewById(R.id.chestInfo);
        ticketBuy = (ImageView) findViewById(R.id.magickTicket);
        he = (ImageView) findViewById(R.id.he);
        validIMG = (ImageView) findViewById(R.id.valid);
        hemed = (ImageView) findViewById(R.id.hemed);
        hemed2 = (ImageView) findViewById(R.id.hemed2);
        helit = (ImageView) findViewById(R.id.helit);
        infoStore = (ImageView) findViewById(R.id.infoStore1);
        prizeText = (TextView) findViewById(R.id.prizeText);
        firstWinnerRow = (TableRow) findViewById(R.id.firstWinnerRow);
        secondWinnerRow = (TableRow) findViewById(R.id.secondWinnerRow);
        thirdWinnerRow = (TableRow) findViewById(R.id.thirdWinnerRow);
        fourWinnerRow = (TableRow) findViewById(R.id.fourWinnerRow);
        fifthWinnerRow = (TableRow) findViewById(R.id.fifthWInnerRow);
        firstWinner = (TextView) findViewById(R.id.firstWinnerName);
        secondtWinner = (TextView) findViewById(R.id.secondWinner);
        thirdWinner = (TextView) findViewById(R.id.thirdWinner);
        fourWinner = (TextView) findViewById(R.id.fourWinner);
        fifthWinner = (TextView) findViewById(R.id.fifthWinner);
        winnerNumber = (TextView) findViewById(R.id.winnerNumber);
        rateUs = (ImageView) findViewById(R.id.rateUs);
        helioProgress = (ProgressBar) findViewById(R.id.helioProgress);
        countDown = (TextView) findViewById(R.id.countdown);
        staticCountDown = (TextView) findViewById(R.id.staticCountDown);
        winnerTicket = (ImageView) findViewById(R.id.winnerTicket);
        winnerLayout = (TableLayout) findViewById(R.id.winnerLayout);
        armTicket = (ImageView) findViewById(R.id.armTicket);
        initStart = (ImageView) findViewById(R.id.initStart);
        arm = (ImageView) findViewById(R.id.arm);
        secret = (ImageView) findViewById(R.id.secret);
        winners = (ImageView) findViewById(R.id.winners);
        theTicket = (ImageView) findViewById(R.id.theTIcket);
        ticketID = (TextView) findViewById(R.id.ticketID);
        ticketID2 = (TextView) findViewById(R.id.ticketID2);
        storeLayout = (ScrollView) findViewById(R.id.storeLayout);
        lumberJack = (ImageView) findViewById(R.id.lumberJack);
        moneyMaker = (ImageView) findViewById(R.id.moneyMaker);
        adLayout = (ConstraintLayout) findViewById(R.id.adLayout);
        paymentTitle = (TextView) findViewById(R.id.paymentTitle);
        arrow1 = (ImageView) findViewById(R.id.arrow1);
        cashOuText = (TextView) findViewById(R.id.cashoutText);
        paypal = (ImageButton) findViewById(R.id.paypal);
        dgb = (ImageButton) findViewById(R.id.dgb);
        send = (ImageButton) findViewById(R.id.send);
        withdrawLayout = (ConstraintLayout) findViewById(R.id.withdrawLayout);
        addressInput = (EditText) findViewById(R.id.addressInput);
        titulo = (TextView) findViewById(R.id.titulo);
        name = (TextView) findViewById(R.id.nametoshow);
        email = (TextView) findViewById(R.id.emailtoshow);
        pass = (TextView) findViewById(R.id.passtoshow);
        webview = (WebView) findViewById(R.id.web);
        rocketGif = (GifImageView) findViewById(R.id.viewGif);
        dogeGif = (GifImageView) findViewById(R.id.viewGif2);
        btcGif = (GifImageView) findViewById(R.id.viewGif3);
        lastNews = (Switch) findViewById(R.id.viewWeb);
        homeBtn = (ImageButton) findViewById(R.id.homeBtn);
        messengerBtn = (ImageButton) findViewById(R.id.messageBtn);
        adBtn = (ImageButton) findViewById(R.id.addBtn);
        profileBtn = (ImageButton) findViewById(R.id.profileBtn);
        infoBtn = (ImageButton) findViewById(R.id.infoBtn);
        mainLayout = (ConstraintLayout) findViewById(R.id.mainLayout);
        upLayout = (ConstraintLayout) findViewById(R.id.upLayout);
        messengerLayout = (GridLayout) findViewById(R.id.MessengerLayout);
        profileLayout = (GridLayout) findViewById(R.id.profileLayout);
        menu = (LinearLayout) findViewById(R.id.MENU);
        infoLayout = (GridLayout) findViewById(R.id.infoLayout);
        editProfile = (ImageButton) findViewById(R.id.edirProfile);
        deleteProfile = (ImageButton) findViewById(R.id.deleteAccount);
        saveProfile = (ImageButton) findViewById(R.id.saveProfile);
        editName = (EditText) findViewById(R.id.editName);
        promotion = (EditText) findViewById(R.id.promotion);
        editMail = (EditText) findViewById(R.id.editEmail);
        editPass = (EditText) findViewById(R.id.editPass);
        version = (TextView) findViewById(R.id.version);
        online = (TextView) findViewById(R.id.online);
        gpu = (TextView) findViewById(R.id.gpu);
        speed = (TextView) findViewById(R.id.speed);
        timemining = (TextView) findViewById(R.id.timemining);
        magia = (TextView) findViewById(R.id.magic);
        diff = (TextView) findViewById(R.id.diff);
        server = (TextView) findViewById(R.id.server);
        newMessageTitulo = (TextView) findViewById(R.id.newMessageTitulo);
        messageBody = (TextView) findViewById(R.id.messageBody);
        destinatario = (TextView) findViewById(R.id.destinatario);
        mess = (TextView) findViewById(R.id.mess);
        from = (TextView) findViewById(R.id.from);
        money = (TextView) findViewById(R.id.money);
        cashOut = (ImageButton) findViewById(R.id.cashOut);
        dice = (ImageButton) findViewById(R.id.dice);
        store = (ImageButton) findViewById(R.id.store);
        helioAmount = (TextView) findViewById(R.id.helioAmount);
        helioImage = (ImageView) findViewById(R.id.helioImage);
        helio = (ImageView) findViewById(R.id.crupier);
        ticket = (ImageView) findViewById(R.id.ticket);
        lotteryLayout = (ConstraintLayout) findViewById(R.id.loterryLayout);
        buyHelios = (TextView) findViewById(R.id.buyHelios);
        buyDGB = (TextView) findViewById(R.id.buyDGB);
        buyMagicChest = (TextView) findViewById(R.id.buyMagicChest);
        buyMagicTicket = (TextView) findViewById(R.id.buyMagicTicket);
        ticketAmount = (TextView) findViewById(R.id.ticketAmount);

    }


    //----------------------------------MAIN---------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sessiondatabasePolicy();//Inicializa Common, Sesion y Base de Datos
        wanted = new File[0];

        initialiseAds();  //inicializa anuncio

        initialiseElements(); //inicializa elementos

        loadPreview();      //inicializa imagenedes de Helio

        Intent intent = getIntent(); //crea intent para pasar la info del mail

        checkPermissions();

        autologin();

        bundle = intent.getExtras();
        if (bundle != null) {
            LoadingInitial(bundle);
        }
        openURL();
        homeSetUp();
        if (guest){
            enterAsAGuest();
        }
        menuAction();
        countDownTimer();
        //HourscountDownTimer();
        staticCountDown.setVisibility(View.INVISIBLE);

        heliosReceived = dataBase.getHeliosReceived();
        helios = dataBase.getHelios();
        helioAmount.setText(String.valueOf(helios));
        DragAndDrop();

        java.util.Timer taskTimer = new java.util.Timer(true); //cada 5 segundos realiza
        TimerTask task = new MyTimerTask(dataBase, this, userEmail); //busqueda de notificaciones
        taskTimer.scheduleAtFixedRate(task, 10000, 5000);
        homeBtn.setBackgroundTintList(this.getResources().getColorStateList(R.color.darkOption));

        in_App_purchase();
    }

    //---------------------------------ACTION LISTENER----------------------------------

    private void menuAction() {

        /*enableAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!enableAds.isChecked()) {
                    frozenTime = true;
                    HourscountDownTimer();
                    staticCountDown.setVisibility(View.VISIBLE);
                    mAdView.setVisibility(View.GONE);

                } else {
                    frozenTime = false;
                    staticCountDown.setVisibility(View.VISIBLE);
                    HourscountDownTimer();
                    mAdView.setVisibility(View.VISIBLE);
                }
            }
        });*/

        viewWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.merlinminer.com"));
                startActivity(browserIntent);
            }
        });

        cohete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createCustomAlert("Hash Speed Boost!",

                        "This item will provide you a Speed Boost on " +
                                "MH/S on your Merlin Miner for 24Hours." +

                                "\n\nIn other words:" +
                                "\nLet's say you are mining at 36mh/s... why not buy this and start mining at " +
                                "62mh/s as mininum?");
            }
        });

        chest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createCustomAlert("Magic Chest!",
                        "You never get to know what a Magic Chest contains. Every time you open one," +
                                " you receive lucky different things... or prizes!");
            }
        });

        ticketBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createCustomAlert("Magic Ticket!",
                        "Probably the best ticket ever made. " +
                                "\n\nNot just because its made of gold, because " +
                                "it make you earn so much money!");
            }
        });

        dgbInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createCustomAlert("5 DIGIBYTES!",
                        "Probably the best crypto currency ever!" +
                                "\n\nIndeed the next BitCoin... Buy now while you can!");
            }
        });

        lastNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuSetUp();
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeSetUp();
            }
        });
        messengerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessengerSetUp();
            }
        });
        adBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adSetUp();
            }
        });
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileSetUp();
            }
        });
        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoSetUp();
                anothercounter++;
                if (anothercounter==1){
                    getInfoVersion();
                }
            }
        });
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editPorfileSetUp();
            }
        });
        saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProfileSettings();
            }
        });
        cashOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cashOutSetUp();
                goHome = true;
            }
        });
        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dataBase.isFirstTimeStore()) {
                    firstTimeStore();
                } else {
                    storeSetUp();
                }
            }
        });

        infoStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoStore.setVisibility(View.INVISIBLE);
            }
        });
        paypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paypalCheckOut();
            }
        });
        dgb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dgbCheckOut();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                withdrawMoney();
            }
        });

        dice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lotterySetUp();
                timesEntered++;
                goHome = true;
            }
        });
        moneyMaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initDiceSetUp();
            }
        });
        lumberJack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lumberJack.setVisibility(View.INVISIBLE);
                storeSetUp();
            }
        });

        winners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                winnersTopSetUp();
            }
        });

        winnerTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                winingFunction();
            }
        });

        rateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAlert();
            }

        });
        buyMagicTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ticketPurchase();
            }
        });

        buyDGB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DGBpurchase();
            }
        });

        buyMagicChest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                magicChestPurchase();
            }
        });

        theTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dataBase.isAnyTicket()) {
                    lotterySetUp();
                } else {
                    say("You do not have any tickets!");
                }
            }
        });

        buyHelios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buyClick(view);
            }
        });

        secret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                secretPreview();
            }
        });

        initStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initStart();
            }
        });

    }

    private void winningMethod() {
        if (winnerNumber.getVisibility() == View.VISIBLE) {
            if (Integer.valueOf(winnerNumber.getText().toString()) ==
                    Integer.valueOf(ticketID.getText().toString())) {
                final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create(); //Read Update
                alertDialog.setTitle("CONGRATULATIONS");
                alertDialog.setMessage("You are the winner of the lottery! Enter this " +
                        "password on our website to get your prize." +
                        "\n\nPassword: " + dataBase.getWinnerPass());

                alertDialog.setButton("Get Prize", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://merlinminer.com/lottery-winner"));
                        startActivity(browserIntent);
                        allocateWinners();
                    }
                });
                alertDialog.show();  //<-- See This!
            } else {
                say("Buy more ticket next time to increase your LUCK!");
            }
        }
    }

    private void createAlert() {
        final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create(); //Read Update
        alertDialog.setTitle("Limited Offer");
        alertDialog.setMessage("Rate us in returnfor 350 extra Helios!");


        alertDialog.setButton("Continue", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Uri uri = Uri.parse("market://details?id=" + getPackageName());
                Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(myAppLinkToMarket);
                } catch (ActivityNotFoundException e) {
                    say("Unable to find App");
                }
                rateUs.setVisibility(View.INVISIBLE);
            }
        });
        alertDialog.show();  //<-- See This!
        say("We need to verify you rate us, It will take less than 1 day!");
    }

    private void say(String message) {
        Toast myToast = Toast.makeText
                (getApplicationContext(), message,
                        Toast.LENGTH_SHORT);
        myToast.show();
    }

    private void winnersTopSetUp() {
        titulo.setVisibility(View.VISIBLE);
        titulo.setText("TOP 10 Winners");
        helioAmount.setVisibility(View.INVISIBLE);
        helioImage.setVisibility(View.INVISIBLE);
        store.setVisibility(View.INVISIBLE);
        messengerLayout.setVisibility(View.INVISIBLE);
        lastNews.setVisibility(View.GONE);
        infoLayout.setVisibility(View.INVISIBLE);
        mainLayout.setVisibility(View.INVISIBLE);
        profileLayout.setVisibility(View.INVISIBLE);
        adLayout.setVisibility(View.INVISIBLE);
        withdrawLayout.setVisibility(View.INVISIBLE);
        storeLayout.setVisibility(View.INVISIBLE);
        lumberJack.setVisibility(View.INVISIBLE);
        moneyMaker.setVisibility(View.INVISIBLE);
        lotteryLayout.setVisibility(View.INVISIBLE);
        secret.setVisibility(View.INVISIBLE);
        arm.setVisibility(View.INVISIBLE);
        initStart.setVisibility(View.INVISIBLE);
        armTicket.setVisibility(View.INVISIBLE);
        winnerLayout.setVisibility(View.VISIBLE);
        goBack = true;
        infoStore.setVisibility(View.INVISIBLE);

    }


    //-----------------------------LAYOUT SETS UP----------------------------------------


    private void infoSetUp() {
        infoBtn.setBackgroundTintList(this.getResources().getColorStateList(R.color.darkOption));
        homeBtn.setBackgroundTintList(this.getResources().getColorStateList(R.color.colorPrimary));
        dice.setBackgroundTintList(this.getResources().getColorStateList(R.color.colorPrimary));
        adBtn.setBackgroundTintList(this.getResources().getColorStateList(R.color.green));
        profileBtn.setBackgroundTintList(this.getResources().getColorStateList(R.color.colorPrimary));

        if (!dataBase.getMagia().equals("") || !dataBase.getUserGPU().equals("")) {
            goBack = false;
            register.setVisibility(View.INVISIBLE);
            login.setVisibility(View.INVISIBLE);
            titulo.setVisibility(View.VISIBLE);
            titulo.setText("Information");
            helioAmount.setVisibility(View.INVISIBLE);
            helioImage.setVisibility(View.INVISIBLE);
            store.setVisibility(View.INVISIBLE);
            infoLayout.setVisibility(View.VISIBLE);
            lastNews.setVisibility(View.GONE);
            messengerLayout.setVisibility(View.INVISIBLE);
            mainLayout.setVisibility(View.INVISIBLE);
            profileLayout.setVisibility(View.INVISIBLE);
            adLayout.setVisibility(View.INVISIBLE);
            withdrawLayout.setVisibility(View.INVISIBLE);
            storeLayout.setVisibility(View.INVISIBLE);
            lumberJack.setVisibility(View.INVISIBLE);
            moneyMaker.setVisibility(View.INVISIBLE);
            lotteryLayout.setVisibility(View.INVISIBLE);
            secret.setVisibility(View.INVISIBLE);
            arm.setVisibility(View.INVISIBLE);
            initStart.setVisibility(View.INVISIBLE);
            armTicket.setVisibility(View.INVISIBLE);
            winnerLayout.setVisibility(View.INVISIBLE);
            goHome = false;
            helioImage.setOnTouchListener(null);
            infoStore.setVisibility(View.INVISIBLE);
        } else {
            goBack = false;
            register.setVisibility(View.INVISIBLE);
            login.setVisibility(View.INVISIBLE);
            titulo.setVisibility(View.VISIBLE);
            titulo.setText("Information");
            helioAmount.setVisibility(View.INVISIBLE);
            helioImage.setVisibility(View.INVISIBLE);
            store.setVisibility(View.INVISIBLE);
            infoLayout.setVisibility(View.INVISIBLE);
            lastNews.setVisibility(View.GONE);
            noInfoLayout.setVisibility(View.VISIBLE);
            messengerLayout.setVisibility(View.INVISIBLE);
            mainLayout.setVisibility(View.INVISIBLE);
            profileLayout.setVisibility(View.INVISIBLE);
            adLayout.setVisibility(View.INVISIBLE);
            withdrawLayout.setVisibility(View.INVISIBLE);
            storeLayout.setVisibility(View.INVISIBLE);
            lumberJack.setVisibility(View.INVISIBLE);
            moneyMaker.setVisibility(View.INVISIBLE);
            lotteryLayout.setVisibility(View.INVISIBLE);
            secret.setVisibility(View.INVISIBLE);
            arm.setVisibility(View.INVISIBLE);
            initStart.setVisibility(View.INVISIBLE);
            armTicket.setVisibility(View.INVISIBLE);
            winnerLayout.setVisibility(View.INVISIBLE);
            goHome = false;
            helioImage.setOnTouchListener(null);
            infoStore.setVisibility(View.INVISIBLE);
        }


    }

    private void profileSetUp() {
        profileBtn.setBackgroundTintList(this.getResources().getColorStateList(R.color.darkOption));
        homeBtn.setBackgroundTintList(this.getResources().getColorStateList(R.color.colorPrimary));
        dice.setBackgroundTintList(this.getResources().getColorStateList(R.color.colorPrimary));
        adBtn.setBackgroundTintList(this.getResources().getColorStateList(R.color.green));
        infoBtn.setBackgroundTintList(this.getResources().getColorStateList(R.color.colorPrimary));


        if (guest) {
            initProfile();

        } else {
            getProfileInfo();
            viewProfileSetUp();
        }
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterSetUp();
                if (promotion.getText().toString().equals(dataBase.getPromotionCode())) {
                    validIMG.setVisibility(View.VISIBLE);
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLogin(view);
            }
        });
    }

    private void initProfile() {
        noInfoLayout.setVisibility(View.INVISIBLE);
        saveProfile.setVisibility(View.INVISIBLE);
        register.setVisibility(View.INVISIBLE);
        login.setVisibility(View.INVISIBLE);
        initStart.setVisibility(View.INVISIBLE);
        armTicket.setVisibility(View.INVISIBLE);
        winnerLayout.setVisibility(View.INVISIBLE);
        goHome = false;
        helioImage.setOnTouchListener(null);
        infoStore.setVisibility(View.INVISIBLE);
        adLayout.setVisibility(View.INVISIBLE);
        withdrawLayout.setVisibility(View.INVISIBLE);
        storeLayout.setVisibility(View.INVISIBLE);
        lumberJack.setVisibility(View.INVISIBLE);
        moneyMaker.setVisibility(View.INVISIBLE);
        lotteryLayout.setVisibility(View.INVISIBLE);
        secret.setVisibility(View.INVISIBLE);
        arm.setVisibility(View.INVISIBLE);
        helioAmount.setVisibility(View.INVISIBLE);
        helioImage.setVisibility(View.INVISIBLE);
        store.setVisibility(View.INVISIBLE);
        profileLayout.setVisibility(View.INVISIBLE);
        lastNews.setVisibility(View.GONE);
        infoLayout.setVisibility(View.INVISIBLE);
        mainLayout.setVisibility(View.INVISIBLE);
        messengerLayout.setVisibility(View.INVISIBLE);
        promotion.setVisibility(View.INVISIBLE);
        goBack = false;
        titulo.setVisibility(View.VISIBLE);
        titulo.setText("ACCESS");
        messengerBtn.setVisibility(View.INVISIBLE);
        if (guest) {
            register.setVisibility(View.VISIBLE);
            login.setVisibility(View.VISIBLE);
        }
    }

    private void RegisterSetUp() {
        noInfoLayout.setVisibility(View.INVISIBLE);

        register.setVisibility(View.INVISIBLE);
        login.setVisibility(View.INVISIBLE);
        initStart.setVisibility(View.INVISIBLE);
        armTicket.setVisibility(View.INVISIBLE);
        winnerLayout.setVisibility(View.INVISIBLE);
        goHome = false;
        helioImage.setOnTouchListener(null);
        infoStore.setVisibility(View.INVISIBLE);
        adLayout.setVisibility(View.INVISIBLE);
        withdrawLayout.setVisibility(View.INVISIBLE);
        storeLayout.setVisibility(View.INVISIBLE);
        lumberJack.setVisibility(View.INVISIBLE);
        moneyMaker.setVisibility(View.INVISIBLE);
        lotteryLayout.setVisibility(View.INVISIBLE);
        secret.setVisibility(View.INVISIBLE);
        arm.setVisibility(View.INVISIBLE);
        editMail.setVisibility(View.INVISIBLE);
        editPass.setVisibility(View.INVISIBLE);
        editName.setVisibility(View.INVISIBLE);
        saveProfile.setVisibility(View.VISIBLE);
        helioAmount.setVisibility(View.INVISIBLE);
        helioImage.setVisibility(View.INVISIBLE);
        store.setVisibility(View.INVISIBLE);
        profileLayout.setVisibility(View.VISIBLE);
        lastNews.setVisibility(View.GONE);
        infoLayout.setVisibility(View.INVISIBLE);
        mainLayout.setVisibility(View.INVISIBLE);
        messengerLayout.setVisibility(View.INVISIBLE);
        goBack = false;
        titulo.setVisibility(View.VISIBLE);
        titulo.setText("Register");
        messengerBtn.setVisibility(View.INVISIBLE);
        editProfile.setVisibility(View.INVISIBLE);
        deleteProfile.setVisibility(View.INVISIBLE);
        editName.setVisibility(View.VISIBLE);
        editMail.setVisibility(View.VISIBLE);
        editPass.setVisibility(View.VISIBLE);
        promotion.setVisibility(View.VISIBLE);
    }

    private void viewProfileSetUp() {
        goBack = false;
        noInfoLayout.setVisibility(View.INVISIBLE);
        register.setVisibility(View.INVISIBLE);
        login.setVisibility(View.INVISIBLE);
        titulo.setVisibility(View.VISIBLE);
        titulo.setText("Profile");
        helioAmount.setVisibility(View.INVISIBLE);
        helioImage.setVisibility(View.INVISIBLE);
        store.setVisibility(View.INVISIBLE);
        profileLayout.setVisibility(View.VISIBLE);
        lastNews.setVisibility(View.GONE);
        infoLayout.setVisibility(View.INVISIBLE);
        mainLayout.setVisibility(View.INVISIBLE);
        messengerLayout.setVisibility(View.INVISIBLE);
        editProfile.setVisibility(View.VISIBLE);
        adLayout.setVisibility(View.INVISIBLE);
        name.setVisibility(View.VISIBLE);
        pass.setVisibility(View.VISIBLE);
        withdrawLayout.setVisibility(View.INVISIBLE);
        storeLayout.setVisibility(View.INVISIBLE);
        lumberJack.setVisibility(View.INVISIBLE);
        moneyMaker.setVisibility(View.INVISIBLE);
        lotteryLayout.setVisibility(View.INVISIBLE);
        secret.setVisibility(View.INVISIBLE);
        arm.setVisibility(View.INVISIBLE);
        editMail.setVisibility(View.INVISIBLE);
        editPass.setVisibility(View.INVISIBLE);
        editName.setVisibility(View.INVISIBLE);
        saveProfile.setVisibility(View.INVISIBLE);
        deleteProfile.setVisibility(View.INVISIBLE);
        initStart.setVisibility(View.INVISIBLE);
        armTicket.setVisibility(View.INVISIBLE);
        winnerLayout.setVisibility(View.INVISIBLE);
        goHome = false;
        promotion.setVisibility(View.INVISIBLE);
        helioImage.setOnTouchListener(null);
        infoStore.setVisibility(View.INVISIBLE);
    }

    private void MessengerSetUp() {
        noInfoLayout.setVisibility(View.INVISIBLE);
        register.setVisibility(View.INVISIBLE);
        login.setVisibility(View.INVISIBLE);
        titulo.setVisibility(View.VISIBLE);
        titulo.setText("Messenger");
        helioAmount.setVisibility(View.INVISIBLE);
        helioImage.setVisibility(View.INVISIBLE);
        store.setVisibility(View.INVISIBLE);
        messengerLayout.setVisibility(View.VISIBLE);
        lastNews.setVisibility(View.GONE);
        infoLayout.setVisibility(View.INVISIBLE);
        mainLayout.setVisibility(View.INVISIBLE);
        profileLayout.setVisibility(View.INVISIBLE);
        adLayout.setVisibility(View.INVISIBLE);
        withdrawLayout.setVisibility(View.INVISIBLE);
        storeLayout.setVisibility(View.INVISIBLE);
        lumberJack.setVisibility(View.INVISIBLE);
        moneyMaker.setVisibility(View.INVISIBLE);
        lotteryLayout.setVisibility(View.INVISIBLE);
        secret.setVisibility(View.INVISIBLE);
        arm.setVisibility(View.INVISIBLE);
        messengerBtn.setVisibility(View.VISIBLE);
        initStart.setVisibility(View.INVISIBLE);
        armTicket.setVisibility(View.INVISIBLE);
        winnerLayout.setVisibility(View.INVISIBLE);
        goBack = false;
        goHome = false;
        helioImage.setOnTouchListener(null);
        infoStore.setVisibility(View.INVISIBLE);

        if (dataBase.isAnyMessage()) {
            messengerBtn.setBackgroundTintList(this.getResources().getColorStateList(R.color.colorPrimary));
            newMessageTitulo.setText("New Message");
            newMessageTitulo.setTextColor(Color.GREEN);
            messageBody.setText(dataBase.getMessage());
            mess.setVisibility(View.VISIBLE);
            from.setVisibility(View.VISIBLE);
        } else {
            messengerBtn.setBackgroundTintList(this.getResources().getColorStateList(R.color.darkOption));
            newMessageTitulo.setText("No New Messages");
            newMessageTitulo.setTextColor(Color.RED);
            messageBody.setVisibility(View.GONE);
            destinatario.setVisibility(View.GONE);
            mess.setVisibility(View.GONE);
            from.setVisibility(View.GONE);
        }
    }

    private void homeSetUp() {
        homeBtn.setBackgroundTintList(this.getResources().getColorStateList(R.color.darkOption));
        infoBtn.setBackgroundTintList(this.getResources().getColorStateList(R.color.colorPrimary));
        dice.setBackgroundTintList(this.getResources().getColorStateList(R.color.colorPrimary));
        adBtn.setBackgroundTintList(this.getResources().getColorStateList(R.color.green));
        profileBtn.setBackgroundTintList(this.getResources().getColorStateList(R.color.colorPrimary));

        goBack = false;
        goHome = false;
        noInfoLayout.setVisibility(View.INVISIBLE);
        register.setVisibility(View.INVISIBLE);
        login.setVisibility(View.INVISIBLE);
        lastNews.setChecked(false);
        webview.setVisibility(View.INVISIBLE);
        webview.setWebViewClient(new MyWebViewClient());
        titulo.setVisibility(View.INVISIBLE);
        titulo.setText("Welcome");
        helioAmount.setVisibility(View.VISIBLE);
        helioImage.setVisibility(View.VISIBLE);
        store.setVisibility(View.VISIBLE);
        money.setText(dataBase.getMoney() + dataBase.getCurrency());
        mainLayout.setVisibility(View.VISIBLE);
        lastNews.setVisibility(View.VISIBLE);
        infoLayout.setVisibility(View.INVISIBLE);
        messengerLayout.setVisibility(View.INVISIBLE);
        profileLayout.setVisibility(View.INVISIBLE);
        withdrawLayout.setVisibility(View.INVISIBLE);
        arrow1.setVisibility(View.VISIBLE);
        dice.setVisibility(View.VISIBLE);
        adLayout.setVisibility(View.INVISIBLE);
        storeLayout.setVisibility(View.INVISIBLE);
        lumberJack.setVisibility(View.INVISIBLE);
        moneyMaker.setVisibility(View.INVISIBLE);
        lotteryLayout.setVisibility(View.INVISIBLE);
        secret.setVisibility(View.INVISIBLE);
        arm.setVisibility(View.INVISIBLE);
        initStart.setVisibility(View.INVISIBLE);
        armTicket.setVisibility(View.INVISIBLE);
        winnerLayout.setVisibility(View.INVISIBLE);
        helioImage.setOnTouchListener(null);
        infoStore.setVisibility(View.INVISIBLE);

    }

    private void storeSetUp() {
        register.setVisibility(View.INVISIBLE);
        login.setVisibility(View.INVISIBLE);
        noInfoLayout.setVisibility(View.INVISIBLE);
        prepareVisibilityForStore();
        avoidRepetition = 0;
        helioImage.setOnTouchListener(null);
        if (dataBase.isFirstTimeStore()) {
            infoStore.setVisibility(View.VISIBLE);
            dataBase.setFirstTimeStore(false);

        } else {
            infoStore.setVisibility(View.INVISIBLE);
        }

    }

    private void prepareVisibilityForStore() {
        titulo.setVisibility(View.VISIBLE);
        register.setVisibility(View.INVISIBLE);
        login.setVisibility(View.INVISIBLE);
        titulo.setText("Store");
        storeLayout.setVisibility(View.VISIBLE);
        lumberJack.setVisibility(View.INVISIBLE);
        helioAmount.setVisibility(View.INVISIBLE);
        helioImage.setVisibility(View.INVISIBLE);
        store.setVisibility(View.INVISIBLE);
        messengerLayout.setVisibility(View.INVISIBLE);
        lastNews.setVisibility(View.GONE);
        infoLayout.setVisibility(View.INVISIBLE);
        mainLayout.setVisibility(View.INVISIBLE);
        profileLayout.setVisibility(View.INVISIBLE);
        adLayout.setVisibility(View.INVISIBLE);
        withdrawLayout.setVisibility(View.INVISIBLE);
        moneyMaker.setVisibility(View.INVISIBLE);
        lotteryLayout.setVisibility(View.INVISIBLE);
        helioAmount.setVisibility(View.VISIBLE);
        helioImage.setVisibility(View.VISIBLE);
        secret.setVisibility(View.INVISIBLE);
        arm.setVisibility(View.INVISIBLE);
        lastNews.setVisibility(View.INVISIBLE);
        initStart.setVisibility(View.INVISIBLE);
        armTicket.setVisibility(View.INVISIBLE);
        winnerLayout.setVisibility(View.INVISIBLE);
    }

    private void menuSetUp() {
        if (!lastNews.isChecked()) {
            webview.setVisibility(View.INVISIBLE);
            cashOut.setVisibility(View.VISIBLE);
            cashOuText.setVisibility(View.VISIBLE);
            arrow1.setVisibility(View.VISIBLE);
            mininghub.setVisibility(View.VISIBLE);
        } else {
            webview.setVisibility(View.VISIBLE);
            cashOut.setVisibility(View.INVISIBLE);
            cashOuText.setVisibility(View.INVISIBLE);
            arrow1.setVisibility(View.INVISIBLE);
            mininghub.setVisibility(View.GONE);
        }
    }

    private void firstTimeStore() {
        titulo.setVisibility(View.VISIBLE);
        register.setVisibility(View.INVISIBLE);
        login.setVisibility(View.INVISIBLE);
        titulo.setText("Store");
        noInfoLayout.setVisibility(View.INVISIBLE);
        storeLayout.setVisibility(View.INVISIBLE);
        helioAmount.setVisibility(View.INVISIBLE);
        helioImage.setVisibility(View.INVISIBLE);
        store.setVisibility(View.INVISIBLE);
        messengerLayout.setVisibility(View.INVISIBLE);
        lastNews.setVisibility(View.GONE);
        infoLayout.setVisibility(View.INVISIBLE);
        mainLayout.setVisibility(View.INVISIBLE);
        profileLayout.setVisibility(View.INVISIBLE);
        adLayout.setVisibility(View.INVISIBLE);
        withdrawLayout.setVisibility(View.INVISIBLE);
        lumberJack.setVisibility(View.VISIBLE);
        moneyMaker.setVisibility(View.INVISIBLE);
        lotteryLayout.setVisibility(View.INVISIBLE);
        secret.setVisibility(View.INVISIBLE);
        arm.setVisibility(View.INVISIBLE);
        initStart.setVisibility(View.INVISIBLE);
        armTicket.setVisibility(View.INVISIBLE);
        winnerLayout.setVisibility(View.INVISIBLE);
        infoStore.setVisibility(View.INVISIBLE);
        helioImage.setOnTouchListener(null);

    }

    private void cashOutSetUp() {
        register.setVisibility(View.INVISIBLE);
        login.setVisibility(View.INVISIBLE);
        withdrawLayout.setVisibility(View.VISIBLE);
        titulo.setVisibility(View.VISIBLE);
        titulo.setText("Cash Out");
        addressInput.setVisibility(View.INVISIBLE);
        send.setVisibility(View.INVISIBLE);
        noInfoLayout.setVisibility(View.INVISIBLE);
        mainLayout.setVisibility(View.INVISIBLE);
        lastNews.setVisibility(View.INVISIBLE);
        dgb.setVisibility(View.VISIBLE);
        paypal.setVisibility(View.VISIBLE);
        lotteryLayout.setVisibility(View.INVISIBLE);
        adLayout.setVisibility(View.INVISIBLE);
        storeLayout.setVisibility(View.INVISIBLE);
        secret.setVisibility(View.INVISIBLE);
        arm.setVisibility(View.INVISIBLE);
        initStart.setVisibility(View.INVISIBLE);
        armTicket.setVisibility(View.INVISIBLE);
        winnerLayout.setVisibility(View.INVISIBLE);
        helioImage.setOnTouchListener(null);

    }

    private void editPorfileSetUp() {
        if (editMail.getVisibility() != View.VISIBLE) {
            editMail.setVisibility(View.VISIBLE);
            editPass.setVisibility(View.VISIBLE);
            editName.setVisibility(View.VISIBLE);
            saveProfile.setVisibility(View.VISIBLE);
            deleteProfile.setVisibility(View.VISIBLE);
        } else {
            editMail.setVisibility(View.INVISIBLE);
            editPass.setVisibility(View.INVISIBLE);
            editName.setVisibility(View.INVISIBLE);
            saveProfile.setVisibility(View.INVISIBLE);
            deleteProfile.setVisibility(View.INVISIBLE);
        }
    }

    private void adSetUp() {
        adBtn.setBackgroundTintList(this.getResources().getColorStateList(R.color.darkOption));
        homeBtn.setBackgroundTintList(this.getResources().getColorStateList(R.color.colorPrimary));
        profileBtn.setBackgroundTintList(this.getResources().getColorStateList(R.color.colorPrimary));
        dice.setBackgroundTintList(this.getResources().getColorStateList(R.color.colorPrimary));
        infoBtn.setBackgroundTintList(this.getResources().getColorStateList(R.color.colorPrimary));

        goBack = false;
        goHome = false;
        noInfoLayout.setVisibility(View.INVISIBLE);
        register.setVisibility(View.INVISIBLE);
        login.setVisibility(View.INVISIBLE);
        titulo.setVisibility(View.VISIBLE);
        titulo.setText("Reward");
        profileLayout.setVisibility(View.INVISIBLE);
        lastNews.setVisibility(View.GONE);
        infoLayout.setVisibility(View.INVISIBLE);
        mainLayout.setVisibility(View.INVISIBLE);
        messengerLayout.setVisibility(View.INVISIBLE);
        editProfile.setVisibility(View.VISIBLE);
        adLayout.setVisibility(View.VISIBLE);
        withdrawLayout.setVisibility(View.INVISIBLE);
        addressInput.setVisibility(View.INVISIBLE);
        send.setVisibility(View.INVISIBLE);
        dgb.setVisibility(View.INVISIBLE);
        paypal.setVisibility(View.INVISIBLE);
        lumberJack.setVisibility(View.INVISIBLE);
        moneyMaker.setVisibility(View.INVISIBLE);
        lotteryLayout.setVisibility(View.INVISIBLE);
        helioAmount.setVisibility(View.VISIBLE);
        helioImage.setVisibility(View.VISIBLE);
        secret.setVisibility(View.INVISIBLE);
        arm.setVisibility(View.INVISIBLE);
        storeLayout.setVisibility(View.INVISIBLE);
        initStart.setVisibility(View.INVISIBLE);
        armTicket.setVisibility(View.INVISIBLE);
        winnerLayout.setVisibility(View.INVISIBLE);
        infoStore.setVisibility(View.INVISIBLE);
        helioImage.setOnTouchListener(null);

    }

    private void withdrawMoney() {
        if (!DGBtype) { //si el booleano es falso
            if (dataBase.getCurrency().contains("$")) { //si la currency del usuario es
                dataBase.setPaymentType("usd");         //poner usd dentro de set Payment Type
            } else if (dataBase.getCurrency().contains("€")) {
                dataBase.setPaymentType("eur");
            } else if (dataBase.getCurrency().contains("£")) {
                dataBase.setPaymentType("gbp");
            }
            dataBase.setAddress(addressInput.getText().toString());//poner el contenido de la address input get text en la base de datos
            Toast myToast = Toast.makeText                          //mero contenido informativo que se display para el usuario
                    (getApplicationContext(), "Paypal Withdraw ordered. It will take within" +
                                    " 5 hours to come into your account;",
                            Toast.LENGTH_LONG);
            myToast.show();
            try {
                dataBase.updatePaymentType();//intenta poner el metodo de pago en la base de datos MySQL
                dataBase.updateAddress();
            } catch (SQLException e) {
                e.printStackTrace();
            }                               //EN ESTE PUNTO YA SE DEBERIA HABER PROCESADO LA ORDEN DE PAGO EN NUESTRA BASE DE DATOS
            try {
                dataBase.updateMoney0(); //establece el balance del usuario a 0 en la base de datos
            } catch (SQLException e) {
                e.printStackTrace();
            }

            dataBase.setMoney("000.0000");//establece el balance de la app del usuario a 0

        } else if (DGBtype) {        //LO MISMO PERO SI ESCOGE DGB
            dataBase.setPaymentType("dgb");
            dataBase.setAddress(addressInput.getText().toString());
            Toast myToast = Toast.makeText
                    (getApplicationContext(), "DGB Withdraw ordered. It will take within" +
                                    " 3 hours to come into your account;",
                            Toast.LENGTH_LONG);
            myToast.show();
            try {
                dataBase.updatePaymentType();
                dataBase.updateAddress();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                dataBase.updateMoney0();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            dataBase.setMoney("000.0000");

        }
        addressInput.setText("");
        addressInput.setHint("");
        money.setText("00.0000");
        homeSetUp();
        lastNews.setVisibility(View.VISIBLE);
    }

    private void dgbCheckOut() {
        if (addressInput.getVisibility() != View.VISIBLE) { // LO MISMO DE ARRIBA PERO PARA DGB
            titulo.setVisibility(View.VISIBLE);
            titulo.setText("DigiByte");
            paypal.setVisibility(View.INVISIBLE);
            dgb.setVisibility(View.VISIBLE);
            addressInput.setVisibility(View.VISIBLE);
            send.setVisibility(View.VISIBLE);
            addressInput.setHint("DGB address...");
            paymentTitle.setText("Amount to withdraw: " + dataBase.getMoney() + dataBase.getCurrency());
            DGBtype = true; //CAMBIO EL BOOLEANO a true para reutilizar el input
        } else {
            withdrawLayout.setVisibility(View.VISIBLE);
            titulo.setVisibility(View.VISIBLE);
            titulo.setText("Cash Out");
            paypal.setVisibility(View.VISIBLE);
            addressInput.setVisibility(View.INVISIBLE);
            send.setVisibility(View.INVISIBLE);
            paymentTitle.setText("Choose between this 2 cash out options");
        }
    }

    private void initDiceSetUp() {
        dice.setBackgroundTintList(this.getResources().getColorStateList(R.color.darkOption));
        homeBtn.setBackgroundTintList(this.getResources().getColorStateList(R.color.colorPrimary));
        profileBtn.setBackgroundTintList(this.getResources().getColorStateList(R.color.colorPrimary));
        adBtn.setBackgroundTintList(this.getResources().getColorStateList(R.color.green));
        infoBtn.setBackgroundTintList(this.getResources().getColorStateList(R.color.colorPrimary));

        moneyMaker.setVisibility(View.INVISIBLE);
        lotteryLayout.setVisibility(View.VISIBLE);
        helio.setVisibility(View.VISIBLE);
        ticketID.setVisibility(View.INVISIBLE);
        ticketID2.setVisibility(View.INVISIBLE);
        ticket.setVisibility(View.INVISIBLE);
        secret.setVisibility(View.INVISIBLE);
        initStart.setVisibility(View.VISIBLE);
        lumberJack.setVisibility(View.INVISIBLE);
        lastNews.setVisibility(View.INVISIBLE);
    }

    private void paypalCheckOut() {
        if (addressInput.getVisibility() != View.VISIBLE) { //Si no se ve el input address
            titulo.setVisibility(View.VISIBLE); //mostrar titulo
            titulo.setText("Paypal"); //poner texto al titulo
            dgb.setVisibility(View.INVISIBLE); //mostrar boton DGB
            paypal.setVisibility(View.VISIBLE); //mostrar boton Paypal
            addressInput.setVisibility(View.VISIBLE); //mostrar address input
            send.setVisibility(View.VISIBLE);//mostrar boton para enviar addres
            addressInput.setHint("PAYPAL address...");//poner texto en modo hint dentro del input
            paymentTitle.setText("Amount to withdraw: " + dataBase.getMoney() + dataBase.getCurrency()); //poner sub titulo informativo
            DGBtype = false; //marcar el boolean a false para reutilizar el contenido del input get text
        } else { //si ya se vel input entonces
            withdrawLayout.setVisibility(View.VISIBLE); //poner el layout de cash out =visible
            titulo.setVisibility(View.VISIBLE); //mostrar el titulo
            titulo.setText("Cash Out");//poner texto al titulo
            addressInput.setVisibility(View.INVISIBLE);//ocultar el address input
            send.setVisibility(View.INVISIBLE);//ocultar boton de enviar
            dgb.setVisibility(View.VISIBLE);//ocultar boton de DGB
            paymentTitle.setText("Choose between this 2 cash out options");
        }
    }

    private void lotterySetUp() {
        dice.setBackgroundTintList(this.getResources().getColorStateList(R.color.darkOption));
        homeBtn.setBackgroundTintList(this.getResources().getColorStateList(R.color.colorPrimary));
        profileBtn.setBackgroundTintList(this.getResources().getColorStateList(R.color.colorPrimary));
        adBtn.setBackgroundTintList(this.getResources().getColorStateList(R.color.green));
        infoBtn.setBackgroundTintList(this.getResources().getColorStateList(R.color.colorPrimary));
        avoidRepetition = 0;

        noInfoLayout.setVisibility(View.INVISIBLE);
        register.setVisibility(View.INVISIBLE);
        login.setVisibility(View.INVISIBLE);
        rocketGif.setVisibility(View.INVISIBLE);
        webview.setVisibility(View.GONE);
        storeLayout.setVisibility(View.INVISIBLE);
        winnerLayout.setVisibility(View.INVISIBLE);
        helioAmount.setVisibility(View.VISIBLE);
        prizeText.setVisibility(View.INVISIBLE);
        infoStore.setVisibility(View.INVISIBLE);
        profileLayout.setVisibility(View.INVISIBLE);
        adLayout.setVisibility(View.INVISIBLE);
        infoLayout.setVisibility(View.INVISIBLE);
        messengerLayout.setVisibility(View.INVISIBLE);
        withdrawLayout.setVisibility(View.INVISIBLE);
        winnerLayout.setVisibility(View.INVISIBLE);
        titulo.setVisibility(View.INVISIBLE);
        helioImage.setVisibility(View.VISIBLE);
        helioAmount.setVisibility(View.VISIBLE);

        if (dataBase.getHelios() == 0) {
            helioImage.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (dataBase.getHelios() == 0) {
                        helioImage.setOnTouchListener(null);
                        avoidRepetition++;
                        say("Insufficient Helios");
                    }
                    return false;
                }
            });
        } else {
            helioImage.setOnTouchListener(new ChoiceTouchListener());
        }

        if (dataBase.isFirstTimeLottery()) {
            if (timesEntered == 1) {
                noFirstTimeLotterySetUp();
                dataBase.setFirstTimeLottery(false);
                secret.setVisibility(View.VISIBLE);
            } else if (timesEntered == 0) {
                firstTimeLotterySetUp();
            } else {
                dataBase.setFirstTimeLottery(false);
                moneyMaker.setVisibility(View.INVISIBLE);
                lotteryLayout.setVisibility(View.VISIBLE);
                helio.setVisibility(View.VISIBLE);
                ticketID.setVisibility(View.INVISIBLE);
                ticketID2.setVisibility(View.INVISIBLE);
                ticket.setVisibility(View.INVISIBLE);
                secret.setVisibility(View.INVISIBLE);
                initStart.setVisibility(View.INVISIBLE);
                lumberJack.setVisibility(View.INVISIBLE);
                lastNews.setVisibility(View.INVISIBLE);
                mainLayout.setVisibility(View.INVISIBLE);
                helio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (valid) {
                            valid = false;
                            storeSetUp();
                        }
                    }
                });
            }

        } else if (!dataBase.isFirstTimeLottery()) {
            dataBase.setFirstTimeLottery(false);
            if (timesEntered == 3) {
                secret.setVisibility(View.INVISIBLE);
                noFirstTimeLotterySetUp();
            } else {
                noFirstTimeLotterySetUp();
            }

        }
    }

    //-------------------------------------ANIMACIÓN-------------------------------------

    private void DragAndDrop() {
        ticket.setOnTouchListener(new ChoiceTouchListener());
        helio.setOnDragListener(new ChoiceDragListener());
    }

    private final class ChoiceTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if ((event.getAction() == MotionEvent.ACTION_DOWN) && ((ImageView) v).getDrawable() != null) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                v.startDrag(data, shadowBuilder, v, 0);
                if (Integer.valueOf(dataBase.getHelios()) == 1) {
                    helioImage.setOnTouchListener(null);
                    avoidRepetition++;
                    say("Inufficient Helios");
                }
                return true;
            } else {
                return false;
            }
        }
    }

    private class ChoiceDragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            helioImage.setEnabled(true);
            boolean ready = false;
            counter = 0;

            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    if (dataBase.isAnyTicket()) {
                        ticket.setVisibility(View.VISIBLE);
                    } else {
                        ticket.setVisibility(View.INVISIBLE);
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:

                    break;
                case DragEvent.ACTION_DRAG_EXITED:

                    break;
                case DragEvent.ACTION_DROP:

                    helioProgress.setProgress(heliosReceived);
                    if (dataBase.isAnyTicket()) {
                        helio.setImageDrawable(getResources().getDrawable(R.mipmap.ticketstart3));
                        ticketID.setVisibility(View.VISIBLE);
                        ticket.setImageDrawable(getResources().getDrawable(R.mipmap.ticketforid));
                        ticketID2.setVisibility(View.VISIBLE);
                        block = true;
                        v.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                homeSetUp();
                            }
                        });
                        ticket.setVisibility(View.VISIBLE);
                        ticket.setOnTouchListener(null);

                        ticket.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                say("Lottery has not finished yet!");
                            }
                        });
                        showTicketID = true;
                        ready = true;
                    }
                    if (ready) {
                        block = false;
                        counter++;
                    }
                    OilehInteraction((ImageView) v);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    break;
            }

            return true;
        }

    }

    //----------------------------FUNCIONES----------------------------------------------

    /**
     * Metodo que muestra el ganador. Coger numero para todos igual.
     * Hacer esto en la app de Control
     */
    private void showWinner() {
        if ((d == 0) && (h == 0) && (m == 0) && (s == 0)) {

            winnerNumber.setText(String.valueOf(getRandomNumberInRange(1, 100)));
            dataBase.setAnyWinner(true);
            dataBase.setWinner(Integer.valueOf(winnerNumber.getText().toString()));
            try {
                dataBase.updateCommon();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            winnerTicket.setImageDrawable(getResources().getDrawable(R.mipmap.ticketforid));
            winnerNumber.setVisibility(View.VISIBLE);
            countDown.setVisibility(View.INVISIBLE);
            say("We have a winner!");

        }
    }

    private void enterAsAGuest() {
        infoBtn.setVisibility(View.GONE);
        name.setText("");
        pass.setText("");
        email.setText("");

    }

    private void autologin() {
        try {

            File root = new File(path);
            if (!root.exists()) {
                root.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        boolean valid = false;

        loginex = new File(path);


        if (loginex.exists()) {
            wanted = loginex.listFiles();
            if (wanted.length==1){
                valid=true;
            }else {
                valid=false;
            }

            if (valid) {

                String usermail = wanted[0].getName();
                dataBase.setUserEmail(usermail);

                guest = false;

            } else {
                guest = true;
            }
        }
        try {
            dataBase.load();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Save(File file, String data, FileOutputStream fos) throws FileNotFoundException {
        try {
            fos.write(data.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void Load(File file, DataBase database) throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(file);

        LineNumberReader r = null;
        try {
            r = new LineNumberReader(new InputStreamReader(new FileInputStream(path + "/login.txt")));
            StringBuilder lineAppended = new StringBuilder();
            String line;
            for (line = null; (line = r.readLine()) != null; ) {
                if (r.getLineNumber() == 1) {
                    database.setUserEmail(lineAppended.append(line).toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void LoadingInitial(Bundle bundle) {



        try {

            //si se loguea mediante facebook
            //registra al usuario
            //y luego hace login
             if (bundle.get("first_name") != null) {
                firstname = (String) bundle.get("first_name");
                userEmail = (String) bundle.get("emailed");
                lastname = (String) bundle.get("last_name");
                dataBase.setUserEmail(userEmail);
                dataBase.setUser(firstname + " " + lastname);
                dataBase.addUser("4");
                dataBase.load();
                say("You received 4 Helios!");
                guest=false;
            }
            //si se logue mediante merlin miner
            else if (bundle.get("miner") != null){
                userEmail = (String) bundle.get("miner");
                dataBase.setUserEmail(userEmail);
                dataBase.load();
                guest=false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        loginex = new File(path + "/" + dataBase.getUserEmail());
        if (!loginex.exists()) {
            loginex.mkdirs();
        }
    }

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 100);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        PrintWriter outfile = null;

        if (requestCode == 100) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }
            return;
        }
    }

    private void OilehInteraction(ImageView v) {
        if (dataBase.isAnyTicket()) {
            ticket.setVisibility(View.VISIBLE);
        } else {
            ticket.setVisibility(View.INVISIBLE);
        }
        if (!block) {
            if (helioImage.isInTouchMode() && helioImage.getVisibility() == View.VISIBLE) {
                helios = dataBase.getHelios();
                helios--;
                heliosReceived++;
                if (helios<=0){
                    helioAmount.setOnDragListener(null);
                    helioAmount.setEnabled(false);
                }
                helioAmount.setText(String.valueOf(helios));
                if (heliosReceived == 1 && counter == 2) {
                    //poner imagen de HELIO CONTENTO
                    v.setImageDrawable(getResources().
                            getDrawable(R.mipmap.helionormal));
                    helioProgress.setVisibility(View.VISIBLE);
                    helioProgress.setMax(max);
                    ticketID.setVisibility(View.INVISIBLE);
                    helioProgress.setProgress(1);
                    heliosReceived = 2;
                }
                if (heliosReceived == 3) {
                    v.setImageDrawable(getResources().
                            getDrawable(R.mipmap.heliothinking));
                    helioProgress.setVisibility(View.VISIBLE);

                }
                if (heliosReceived == 5) {
                    v.setImageDrawable(getResources()
                            .getDrawable(R.mipmap.helioaskingforserver));
                    helioProgress.setVisibility(View.VISIBLE);

                }
                if (heliosReceived == 7) {
                    v.setImageDrawable(getResources().
                            getDrawable(R.mipmap.helioaskingcash));
                    helioProgress.setVisibility(View.VISIBLE);
                }
                if (heliosReceived == 14) {
                    v.setImageDrawable(getResources().
                            getDrawable(R.mipmap.heliothinking));
                    helioProgress.setVisibility(View.VISIBLE);

                }
                if (heliosReceived == 20) {
                    v.setImageDrawable(getResources().
                            getDrawable(R.mipmap.heliothinking));
                    helioProgress.setVisibility(View.VISIBLE);

                }
                if (heliosReceived == 25) {
                    v.setImageDrawable(getResources().
                            getDrawable(R.mipmap.helioaskingcash));
                    helioProgress.setVisibility(View.VISIBLE);

                }
                if (heliosReceived == 31) {
                    v.setImageDrawable(getResources().
                            getDrawable(R.mipmap.heliothinking));
                    helioProgress.setVisibility(View.VISIBLE);

                }

                if (heliosReceived == 36) {
                    v.setImageDrawable(getResources().
                            getDrawable(R.mipmap.helio1serveryeah));
                    helioProgress.setVisibility(View.VISIBLE);

                    helio.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ((ImageView) view).setImageDrawable(getResources().
                                    getDrawable(R.mipmap.helio1servermessage));
                        }
                    });

                }
                if (heliosReceived == 36) {
                    v.setImageDrawable(getResources().
                            getDrawable(R.mipmap.helioaskingfor2servers));
                    helioProgress.setMax(74);
                    progress = 0;
                    helioProgress.setVisibility(View.VISIBLE);


                }
                if (heliosReceived == 60) {
                    v.setImageDrawable(getResources().
                            getDrawable(R.mipmap.helio1servermessage));
                }
                if (heliosReceived == 80) {
                    v.setImageDrawable(getResources().
                            getDrawable(R.mipmap.helio1serveryeah));
                }
                if (heliosReceived == 100) {
                    v.setImageDrawable(getResources().
                            getDrawable(R.mipmap.heliowith1server));
                }

                if (heliosReceived == 120) {
                    v.setImageDrawable(getResources().
                            getDrawable(R.mipmap.helioaskingfor2servers));
                }
                if (heliosReceived == 136) {
                    v.setImageDrawable(getResources().
                            getDrawable(R.mipmap.heliocashmachineyeah));

                    helio.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ((ImageView) view).setImageDrawable(getResources().
                                    getDrawable(R.mipmap.helio2serversmessage));
                        }
                    });
                }

                if (heliosReceived == 137) {
                    v.setImageDrawable(getResources().
                            getDrawable(R.mipmap.helioaskingcashmachine));
                    helioProgress.setMax(125);
                    progress = 0;
                    helioProgress.setVisibility(View.VISIBLE);


                }
                if (heliosReceived == 140) {
                    v.setImageDrawable(getResources().
                            getDrawable(R.mipmap.cashmachinemess1));
                }
                if (heliosReceived == 155) {
                    v.setImageDrawable(getResources().
                            getDrawable(R.mipmap.helio2servers));
                }
                if (heliosReceived == 170) {
                    v.setImageDrawable(getResources().
                            getDrawable(R.mipmap.helio2serversyeah));
                }
                if (heliosReceived == 180) {
                    v.setImageDrawable(getResources().
                            getDrawable(R.mipmap.cashmachinemess1));
                }
                if (heliosReceived == 190) {
                    v.setImageDrawable(getResources().
                            getDrawable(R.mipmap.cashmachinemess2));
                }
                if (heliosReceived == 200) {
                    v.setImageDrawable(getResources().
                            getDrawable(R.mipmap.cashmachinemess3));
                }
                if (heliosReceived == 210) {
                    v.setImageDrawable(getResources().
                            getDrawable(R.mipmap.cashmachinemess5));
                }
                if (heliosReceived == 220) {
                    v.setImageDrawable(getResources().
                            getDrawable(R.mipmap.cashmachinemess4));
                }
                if (heliosReceived == 225) {
                    v.setImageDrawable(getResources().
                            getDrawable(R.mipmap.heliocashmachineyeah));

                    helio.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ((ImageView) view).setImageDrawable(getResources().
                                    getDrawable(R.mipmap.heliocashmachinemessage));
                        }
                    });
                }
                if (heliosReceived == 226) {
                    v.setImageDrawable(getResources().
                            getDrawable(R.mipmap.helioaskingforrobot));
                    helioProgress.setMax(54);
                    progress = 0;
                    helioProgress.setVisibility(View.VISIBLE);
                }
                if (heliosReceived == 230) {
                    v.setImageDrawable(getResources().
                            getDrawable(R.mipmap.heliocashmachinemessage2));
                }
                if (heliosReceived == 240) {
                    v.setImageDrawable(getResources().
                            getDrawable(R.mipmap.heliocashmachineyeah));
                }
                if (heliosReceived == 260) {
                    v.setImageDrawable(getResources().
                            getDrawable(R.mipmap.heliocashmachine));
                }
                if (heliosReceived == 270) {
                    v.setImageDrawable(getResources().
                            getDrawable(R.mipmap.heliocashmachinemessage));
                }
                if (heliosReceived == 280) {

                    v.setImageDrawable(getResources().
                            getDrawable(R.mipmap.heliorobotyeah));

                    helio.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ((ImageView) view).setImageDrawable(getResources().
                                    getDrawable(R.mipmap.robotmessage));
                        }
                    });


                }
                if (heliosReceived >= 290) {
                    helioImage.setOnDragListener(null);
                    v.setImageDrawable(getResources().
                            getDrawable(R.mipmap.helioaway));
                    say("Mr. Oileh is rich for now");

                }
            }
            dataBase.setHelios(helios);
        }
    }

    private void winingFunction() {
        winningMethod();
        prizeText.setText(dataBase.getPrizeText());

        if (helio.getVisibility() == View.VISIBLE) {
            if (showTicketID) {
                ticketID.setVisibility(View.INVISIBLE);
            }

            if (dataBase.getPrizeType() == 1) {
                btcGif.setVisibility(View.VISIBLE);
                prizeText.setVisibility(View.VISIBLE);
                helio.setVisibility(View.INVISIBLE);
                helioProgress.setVisibility(View.INVISIBLE);
            } else if (dataBase.getPrizeType() == 2) {
                dogeGif.setVisibility(View.VISIBLE);
                prizeText.setVisibility(View.VISIBLE);
                helio.setVisibility(View.INVISIBLE);
                helioProgress.setVisibility(View.INVISIBLE);
            } else if (dataBase.getPrizeType() == 3) {
                rocketGif.setVisibility(View.VISIBLE);
                prizeText.setVisibility(View.VISIBLE);
                helio.setVisibility(View.INVISIBLE);
                helioProgress.setVisibility(View.INVISIBLE);
            }

        } else if (helio.getVisibility() != View.VISIBLE) {
            if (showTicketID) {
                ticketID.setVisibility(View.VISIBLE);
            }
            btcGif.setVisibility(View.INVISIBLE);
            dogeGif.setVisibility(View.INVISIBLE);
            rocketGif.setVisibility(View.INVISIBLE);
            winnerTicket.setVisibility(View.VISIBLE);
            helio.setVisibility(View.VISIBLE);
            prizeText.setVisibility(View.INVISIBLE);
            helioProgress.setVisibility(View.VISIBLE);

        }
    }

    public void goToLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void createCustomAlert(String Title, String message) {
        final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create(); //Read Update
        alertDialog.setTitle(Title);
        alertDialog.setMessage(message);

        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.closeOptionsMenu();
            }
        });
        alertDialog.show();  //<-- See This!
    }

    private void allocateWinners() {
        if (dataBase.getLotteryRound() == 1) {
            dataBase.setFirstWinner(dataBase.getUser());
            firstWinner.setText(dataBase.getFirstWinner());
            firstWinnerRow.setVisibility(View.VISIBLE);
            try {
                dataBase.updateCommon();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (dataBase.getLotteryRound() == 2) {
            dataBase.setSecondWinner(dataBase.getUser());
            secondtWinner.setText(dataBase.getSecondWinner());
            secondWinnerRow.setVisibility(View.VISIBLE);
            try {
                dataBase.updateCommon();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (dataBase.getLotteryRound() == 3) {
            dataBase.setThirdWinner(dataBase.getUser());
            thirdWinner.setText(dataBase.getThirdWinner());
            thirdWinnerRow.setVisibility(View.VISIBLE);
            try {
                dataBase.updateCommon();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (dataBase.getLotteryRound() == 4) {
            dataBase.setFourWinner(dataBase.getUser());
            fourWinner.setText(dataBase.getFourWinner());
            fourWinner.setVisibility(View.VISIBLE);
            try {
                dataBase.updateCommon();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (dataBase.getLotteryRound() == 5) {
            dataBase.setFifthWinner(dataBase.getUser());
            fifthWinner.setText(dataBase.getFifthWinner());
            fifthWinner.setVisibility(View.VISIBLE);
            try {
                dataBase.updateCommon();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void getSpeed() {
        if (gpu.getText().toString().toLowerCase().contains("amd")) {
            if (gpu.getText().toString().contains("580")) {
                speed.setText("30 Mh/s");
            } else if (gpu.getText().toString().contains("570")) {
                speed.setText("28 Mh/s");
            } else if (gpu.getText().toString().contains("480")) {
                speed.setText("28 Mh/s");
            } else if (gpu.getText().toString().contains("470")) {
                speed.setText("24 Mh/s");
            } else if (gpu.getText().toString().contains("R9")) {
                speed.setText("18 Mh/s");
            } else {
                speed.setText("7.7 Mh/s");
            }
        } else if ((gpu.getText().toString().toLowerCase().contains("nvidia"))) {
            if (gpu.getText().toString().contains("1080")) {
                speed.setText("79 Mh/s");
            } else if (gpu.getText().toString().contains("1070")) {
                speed.setText("51 Mh/s");
            } else if (gpu.getText().toString().contains("1060")) {
                speed.setText("32 Mh/s");
            } else if (gpu.getText().toString().contains("1050")) {
                speed.setText("18 Mh/s");
            } else {
                speed.setText("7.7 Mh/s");
            }
        } else {
            speed.setText("GPU no reconocida");
        }
    }

    private void getProfileInfo() {
        email.setText(dataBase.getUserEmail());
        StringBuilder passw = new StringBuilder();
        for (int i = 0; i < dataBase.getPass().length(); i++) {
            passw.append("*");
        }
        pass.setText(passw.toString());
        name.setText(dataBase.getUser());
    }

    private void getInfoVersion() {
        version.setText("Garden " + dataBase.getVersion());
        dataBase.checkMerlinAvailability();
        if (dataBase.isMerlinAvailable()) {
            server.setText("ON");
            server.setTextColor(Color.GREEN);
        } else {
            server.setText("OFF");
            server.setTextColor(Color.RED);
        }
        gpu.setText(dataBase.getUserGPU());
        timemining.setText(dataBase.getTime());
        magia.setText(dataBase.getMagia());
        if (dataBase.isOnline()) {
            online.setText("YES");
            online.setTextColor(Color.GREEN);
        } else {
            online.setText("NO");
            online.setTextColor(Color.RED);
        }
        getSpeed();
    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    private void loadPreview() {
        resImg = secret.getResources().getDrawable(R.mipmap.secret1);
        img = secret.getResources().getDrawable(R.mipmap.secret2);
        imga = secret.getResources().getDrawable(R.mipmap.secret3);
        imgb = secret.getResources().getDrawable(R.mipmap.initstart4);

        pic1 = secret.getResources().getDrawable(R.mipmap.inistart);
        pic2 = secret.getResources().getDrawable(R.mipmap.initstart2);
        pic3 = secret.getResources().getDrawable(R.mipmap.initstart3);
        pic4 = secret.getResources().getDrawable(R.mipmap.initstart3and5);


        secret.setImageDrawable(resImg);
        initStart.setImageDrawable(pic1);
    }

    private void sessiondatabasePolicy() {
        if (android.os.Build.VERSION.SDK_INT > 9) { // habilita database
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        dataBase = new DataBase();
        try {
            dataBase.loadCommon();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void noFirstTimeLotterySetUp() {
        if (!dataBase.isAnyTicket()) {
            moneyMaker.setVisibility(View.INVISIBLE);
            lotteryLayout.setVisibility(View.VISIBLE);
            mainLayout.setVisibility(View.INVISIBLE);
            if (heliosReceived >= 35) {
                helio.setImageDrawable(getResources().getDrawable(R.mipmap.buyaticket1server));
            } else if (heliosReceived >= 136) {
                helio.setImageDrawable(getResources().getDrawable(R.mipmap.buyaticket2servers));
            } else if (heliosReceived >= 225) {
                helio.setImageDrawable(getResources().getDrawable(R.mipmap.buyaticketcashmachine));
            } else if (heliosReceived >= 280) {
                helio.setImageDrawable(getResources().getDrawable(R.mipmap.buyaticketrobot));
            } else if (heliosReceived >= 1) {
                helio.setImageDrawable(getResources().getDrawable(R.mipmap.helioaskingcash));
            }
            helioProgress.setVisibility(View.VISIBLE);
            helioProgress.setProgress(heliosReceived);
            ticketID.setVisibility(View.INVISIBLE);
            ticketID2.setVisibility(View.INVISIBLE);
            ticket.setVisibility(View.INVISIBLE);
            secret.setVisibility(View.INVISIBLE);
            valid = true;
            initStart.setVisibility(View.GONE);

        } else {
            moneyMaker.setVisibility(View.INVISIBLE);
            lotteryLayout.setVisibility(View.VISIBLE);
            mainLayout.setVisibility(View.INVISIBLE);
            ticket.setVisibility(View.VISIBLE);
            helioProgress.setVisibility(View.VISIBLE);
            helioProgress.setProgress(heliosReceived);
            if (heliosReceived >= 35) {
                helio.setImageDrawable(getResources().getDrawable(R.mipmap.ticketstart1server));
            } else if (heliosReceived >= 136) {
                helio.setImageDrawable(getResources().getDrawable(R.mipmap.ticketstart2servers));
            } else if (heliosReceived >= 225) {
                helio.setImageDrawable(getResources().getDrawable(R.mipmap.ticketstartcashmachine));
            } else if (heliosReceived >= 280) {
                helio.setImageDrawable(getResources().getDrawable(R.mipmap.ticketstartrobot));
            }

            if (showTicketID) {
                ticketID.setVisibility(View.VISIBLE);
                ticketID2.setVisibility(View.VISIBLE);
            } else {
                ticketID.setVisibility(View.INVISIBLE);
                ticketID2.setVisibility(View.INVISIBLE);
            }
            helio.setVisibility(View.VISIBLE);

            secret.setVisibility(View.INVISIBLE);
            initStart.setVisibility(View.GONE);

        }
    }

    private void firstTimeLotterySetUp() {
        if (!dataBase.isAnyTicket()) {
            moneyMaker.setVisibility(View.VISIBLE);
            if (heliosReceived >= 35) {
                helio.setImageDrawable(getResources().getDrawable(R.mipmap.buyaticket1server));
            } else if (heliosReceived >= 136) {
                helio.setImageDrawable(getResources().getDrawable(R.mipmap.buyaticket2servers));
            } else if (heliosReceived >= 225) {
                helio.setImageDrawable(getResources().getDrawable(R.mipmap.buyaticketcashmachine));
            } else if (heliosReceived >= 280) {
                helio.setImageDrawable(getResources().getDrawable(R.mipmap.buyaticketrobot));
            } else {
                helio.setImageDrawable(getResources().getDrawable(R.mipmap.helionormal));

            }
            mainLayout.setVisibility(View.INVISIBLE);
            lastNews.setVisibility(View.GONE);

        } else {
            moneyMaker.setVisibility(View.INVISIBLE);
            lotteryLayout.setVisibility(View.VISIBLE);
            mainLayout.setVisibility(View.INVISIBLE);
            ticket.setVisibility(View.VISIBLE);
            if (heliosReceived >= 35) {
                helio.setImageDrawable(getResources().getDrawable(R.mipmap.ticketstart1server));
            } else if (heliosReceived >= 136) {
                helio.setImageDrawable(getResources().getDrawable(R.mipmap.ticketstart2servers));
            } else if (heliosReceived >= 225) {
                helio.setImageDrawable(getResources().getDrawable(R.mipmap.ticketstartcashmachine));
            } else if (heliosReceived >= 280) {
                helio.setImageDrawable(getResources().getDrawable(R.mipmap.ticketstartrobot));
            } else {
                helio.setImageDrawable(getResources().getDrawable(R.mipmap.helionormal));
            }

            if (showTicketID) {
                ticketID.setVisibility(View.VISIBLE);
                ticketID2.setVisibility(View.VISIBLE);
            } else {
                ticketID.setVisibility(View.INVISIBLE);
                ticketID2.setVisibility(View.INVISIBLE);
            }
            helio.setVisibility(View.VISIBLE);
            ticketID.setVisibility(View.INVISIBLE);
            ticketID2.setVisibility(View.INVISIBLE);
            secret.setVisibility(View.VISIBLE);
        }
    }

    private void saveProfileSettings() {
        if (!guest) {
            saveNewProfile();
        } else {
            registerUser();
        }
    }

    private void saveNewProfile() {
        if (!editName.getText().toString().equals("")) {
            dataBase.setUser(editName.getText().toString());
            name.setText(editName.getText().toString());
        }
        if (!editMail.getText().toString().equals("")) {
            dataBase.setUserEmail(editMail.getText().toString());
            email.setText(editMail.getText().toString());

        }
        if (!editPass.getText().toString().equals("")) {
            dataBase.setPass(editPass.getText().toString());
            StringBuilder passw = new StringBuilder();
            for (int i = 0; i < editPass.getText().toString().length(); i++) {
                passw.append("*");
            }
            pass.setText(passw.toString());
        }

        try {
            dataBase.update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Toast myToast = Toast.makeText
                (getApplicationContext(), "Profile Updated",
                        Toast.LENGTH_SHORT);
        myToast.show();
        editMail.setText("");
        editPass.setText("");
        editName.setText("");
        editMail.setVisibility(View.GONE);
        editPass.setVisibility(View.GONE);
        editName.setVisibility(View.GONE);

        deleteProfile.setVisibility(View.GONE);
        saveProfile.setVisibility(View.GONE);
        editProfile.setVisibility(View.VISIBLE);
    }

    private void registerUser() {
        guest = false;
        dataBase.setUser(editName.getText().toString());
        userEmail = editMail.getText().toString();
        dataBase.setUserEmail(editMail.getText().toString());
        dataBase.setPass(editPass.getText().toString());
        dataBase.setHelios(7);
        Environment.getExternalStorageState();
        if (promotion.getText().toString().equals(dataBase.getPromotionCode())){
            dataBase.setHelios(915);
            try {
                dataBase.addUser("915");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            say("You gained 915 for the promotion code! Buy 1 MAGIC TICKET before it's too late!!");
        }else {
            try {
                dataBase.addUser("7");
                infoBtn.setVisibility(View.VISIBLE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            say("You gained 7 Helios for Register on the APP!");
        }

        try {
            dataBase.load();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        email.setText(dataBase.getUserEmail());
        name.setText(dataBase.getUser());
        pass.setText(dataBase.getPass());
        editMail.setText("");
        editPass.setText("");
        editName.setText("");
        profileSetUp();
        helioAmount.setText("7");
        helios = Integer.valueOf(dataBase.getHelios());
    }

    private void secretPreview() {
        secret.setVisibility(View.VISIBLE);
        if (secret.getDrawable() == resImg) {

            secret.setImageDrawable(img);
            arm.setRotation(35);
            arm.setVisibility(View.VISIBLE);

        } else if (secret.getDrawable() == img) {
            arm.setRotation(180);
            arm.setVisibility(View.VISIBLE);
            secret.setImageDrawable(imga);

        } else if (secret.getDrawable() == imga) {
            arm.setVisibility(View.INVISIBLE);
            helio.setImageDrawable(getResources().getDrawable(R.mipmap.buyaticket));
            secret.setVisibility(View.GONE);
            valid = true;
            helio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (valid) {
                        valid = false;
                        storeSetUp();
                        goBack = true;
                    }
                }
            });


        }
    }

    private void initStart() {
        if (dataBase.isFirstTimeLottery()) {

            if (initStart.getDrawable() == pic1) {
                initStart.setImageDrawable(pic2);
                armTicket.setVisibility(View.VISIBLE);

            } else if (initStart.getDrawable() == pic2) {
                initStart.setImageDrawable(pic3);
                armTicket.setVisibility(View.GONE);
                arm.setMinimumWidth(50);
                arm.setMinimumHeight(50);
                arm.setRotation(0);
                arm.setVisibility(View.VISIBLE);
            } else if (initStart.getDrawable() == pic3) {
                arm.setRotation(290);
                initStart.setImageDrawable(pic4);
                arm.setVisibility(View.VISIBLE);
            } else if (initStart.getDrawable() == pic4) {
                arm.setVisibility(View.VISIBLE);
                initStart.setImageDrawable(imgb);
            } else if (initStart.getDrawable() == imgb) {
                initStart.setVisibility(View.GONE);
                arm.setVisibility(View.INVISIBLE);
                valid = true;
                helio.setImageDrawable(getResources().getDrawable(R.mipmap.buyaticket));
                helio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (valid) {
                            valid = false;
                            storeSetUp();
                            goBack = true;

                        }
                    }
                });

            }
        }
    }

    private void countDown() {
        s--;
        if (s == -1) {
            s = 59;
            --m;
        }
        if (m == -1) {
            m = 59;
            --h;
        }
        if (h == -1) {
            h = 23;
            --d;
        }
        time = (d <= 9 ? "0" : "") + d + " Days " + (h <= 9 ? "0" : "") + h + ":" + (m <= 9 ? "0" : "") + m + ":" + (s <= 9 ? "0" : "") + s;
        countDown.setText(time);

        showWinner();

    }

    private void countDownTimer() {
        new CountDownTimer(1209600000, 1000) {


            public void onTick(long millisUntilFinished) {
                countDown();
            }

            public void onFinish() {
            }

        }.start();
    }

    //----------------------------ANUNCIOS-----------------------------------------------


    private void initialiseAds() {
        intersitialAdd = ((ImageButton) findViewById(R.id.litMoney));
        intersitialAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mInterstitialAd.isLoaded()) {
                    showInterstitial();
                } else {
                    loadInterstitial();
                    showInterstitial();
                }

            }
        });

        videoAdd = (ImageButton) findViewById(R.id.videoMoney);
        mAdView = (AdView) findViewById(R.id.adView);

        mAd = MobileAds.getRewardedVideoAdInstance(this);
        mAd.setRewardedVideoAdListener(this);

        loadVideoAdd();

        videoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAd.isLoaded()) {
                    mAd.show();
                } else {
                    AdRequest adRequest = new AdRequest.Builder().build();
                    mAdView.loadAd(adRequest);
                    loadVideoAdd();
                    mAd.show();

                }

            }
        });


        // Create the text view to show the level number.
        mLevel = START_LEVEL;
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        // Create the InterstitialAd and set the adUnitId (defined in values/strings.xml).
        mInterstitialAd = newInterstitialAd();
        loadInterstitial();
    }

    private InterstitialAd newInterstitialAd() {
        InterstitialAd interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                intersitialAdd.setEnabled(true);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                intersitialAdd.setEnabled(true);
            }

            @Override
            public void onAdClosed() {
                Toast myToast = Toast.makeText
                        (getApplicationContext(), "You have gain 1 Helio",
                                Toast.LENGTH_LONG);
                myToast.show();
                dataBase.setHelios(dataBase.getHelios()+ 1);
                helioAmount.setText(String.valueOf(dataBase.getHelios()));
                mInterstitialAd = newInterstitialAd();
                loadInterstitial();
            }
        });
        return interstitialAd;
    }

    private void showInterstitial() {
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadInterstitial() {
        // Disable the next level button and load the ad.
        //intersitialAdd.setEnabled(false);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        mInterstitialAd.loadAd(adRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_intersitial, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                createCustomAlert("Version Information",
                        "Current Version: BETA 1.1" +
                        "\n \nFeatures:" +
                                "\n - View ads to gain Helios" +
                                "\n - Multiply your balance with Lottery " +
                                "\n - Manage your Merlin Miner Account" +
                                "\n - Buy crypto on the market store" +
                                "\n - Upgrade Mr. Oileh with Helios");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        videoAdd.setEnabled(true);
    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {

    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        Toast.makeText(this, "You have gain 4 Helios", Toast.LENGTH_LONG).show();
        dataBase.setHelios(dataBase.getHelios()+4);
        helioAmount.setText(String.valueOf(dataBase.getHelios()));
        helioAmount.setText(String.valueOf(helios));
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    private void loadVideoAdd() {
        if (!mAd.isLoaded()) {
            mAd.loadAd("ca-app-pub-8826042662376381/3431771689", new AdRequest.Builder().build());
        }
    }


//-------------------------------------WEB VIEW------------------------------------

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

    }

    private void openURL() {
        webview.loadUrl("https://merlinminer.com/blog");
        webview.requestFocus();
    }

    //------------------------------------IN APP PURCHASES----------------------------

    private void DGBpurchase() {
        int amount = dataBase.getHelios();
        if (amount == 1100) {
            amount = 0;
            dataBase.setHelios(amount);
            purchaseDGB(amount);
        } else if (amount > 110) {
            amount -= 850;
            purchaseDGB(amount);
            dataBase.setHelios(amount);
        } else {
            if (!valid) {
                avoidRepetition++;
                if (avoidRepetition == 1) {
                    Toast myToast = Toast.makeText
                            (getApplicationContext(), "Not enough Helios!",
                                    Toast.LENGTH_LONG);
                    myToast.show();
                    valid = true;
                }
            }
        }
        try {
            dataBase.update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void magicChestPurchase() {
        say("Available soon!");
        /*int amount = dataBase.getHelios();
        if (amount == 550) {
            amount = 0;
               dataBase.setHelios(amount)
            purchaseDGB(amount);
            say("You bought a Magic Chest");

            //
        } else if (amount > 110) {
            amount -= 850;
            purchaseDGB(amount);
   dataBase.setHelios(amount)
            say("You bought a Magic Chest");
        } else {
            if (!valid) {
                avoidRepetition++;
                if (avoidRepetition == 1) {
                    say("You do not have any tickets!");
                    valid = true;
                }
            }
        }*/
    }

    private void purchaseDGB(int amount) {
        helioAmount.setText(String.valueOf(amount));
        cashOutSetUp();
        paypal.setVisibility(View.INVISIBLE);
        dgb.setVisibility(View.INVISIBLE);
        send.setVisibility(View.VISIBLE);
        addressInput.setVisibility(View.VISIBLE);
        titulo.setVisibility(View.VISIBLE);
        titulo.setText("5 DGB Purchase");
        helioImage.setVisibility(View.INVISIBLE);
        helioAmount.setVisibility(View.INVISIBLE);
        addressInput.setHint("DigiByte Address...");
        paymentTitle.setText("add your wallet address to receive your 5 DGB");
        Toast myToast = Toast.makeText
                (getApplicationContext(), "You bought 5DGB for 110 Helios!",
                        Toast.LENGTH_LONG);
        myToast.show();
    }

    private void ticketPurchase() {
        int amount = dataBase.getHelios();
        if (amount == 870) {
            int ticket = +1;
            amount = 0;
            helioAmount.setText(String.valueOf(amount));
            dataBase.setHelios(amount);
            int ticketNumber = getRandomNumberInRange(0, 100);
            dataBase.setTicket(ticketNumber);
            ticketID.setText(String.valueOf(ticketNumber));
            ticketID2.setText(String.valueOf(ticketNumber));
            ticketAmount.setText(String.valueOf(ticket));
            dataBase.setAnyTicket(true);
            Toast myToast = Toast.makeText
                    (getApplicationContext(), "1 Magic Ticket bought!",
                            Toast.LENGTH_LONG);
            myToast.show();
        } else if (amount > 870) {
            amount -= 870;
            int ticket = +1;
            helioAmount.setText(String.valueOf(amount));
            ticketAmount.setText(String.valueOf(ticket));
            int ticketNumber = getRandomNumberInRange(0, 100);
            ticketID.setText(String.valueOf(ticketNumber));
            ticketID2.setText(String.valueOf(ticketNumber));
            dataBase.setAnyTicket(true);
            dataBase.setHelios(amount);
            dataBase.setTicket(getRandomNumberInRange(0, 100));

            Toast myToast = Toast.makeText
                    (getApplicationContext(), "1 Magic Ticket bought!",
                            Toast.LENGTH_LONG);
            myToast.show();
        } else {
            if (!valid) {
                avoidRepetition++;
                if (avoidRepetition == 1) {
                    Toast myToast = Toast.makeText
                            (getApplicationContext(), "Not enough Helios!",
                                    Toast.LENGTH_LONG);
                    myToast.show();
                    valid = false;
                }
            }
        }

        try {
            dataBase.update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void in_App_purchase() {
        mPurchaseFinishedListener
                = new IabHelper.OnIabPurchaseFinishedListener() {
            public void onIabPurchaseFinished(IabResult result,
                                              Purchase purchase) {
                if (result.isFailure()) {
                    // Handle error
                    return;
                } else if (purchase.getSku().equals(ITEM_SKU)) {
                    consumeItem();
                    buyHelios.setEnabled(false);
                }

            }
        };


        String base64EcnodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwVKqqY7G/" +
                "qbbjsC0rvHCYCHYAVyDansuC/+KvuOa5B8O3xLY9Jtr3+GWZ7eZzmQ2RSEWakHl7/g22xyGOzmWe" +
                "krZi1zJxDYeejx3/uXIf+1Z/Y5YjVWKgx+7r99fbIRmMaYwKK9EZvkc5F3oVmnxPODfBvlK4fIt5U" +
                "QKJoaMIFo8ZG9kRLfbA+y8XdFAODieUeMS+txoJmCwwFVKzIUf8Fqwbd7p+xN+tSYTepKmpHc3uXW" +
                "HF/9GkwKsBECzLD8fpBKGZLRaU0pPhCNr+07SCJuMUSCBqmKiY342t5W6S7Ap0GbFxu41Xei9zLqt" +
                "UFMrbWcX/zu9kStUxoAyqxVFlwIDAQAB";

        mHelper = new IabHelper(this, base64EcnodedPublicKey);
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            @Override
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    Log.d(TAG, "In-app Billing setup failed: " +
                            result);
                } else {
                    Log.d(TAG, "In-app Billing is set up OK");
                }
            }
        });
    }

    private void consumeItem() {
        mHelper.queryInventoryAsync(mReceivedInventoryListener);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!mHelper.handleActivityResult(requestCode,
                resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    IabHelper.QueryInventoryFinishedListener mReceivedInventoryListener
            = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {

            if (result.isFailure()) {
                // Handle failure
            } else {
                mHelper.consumeAsync(inventory.getPurchase(ITEM_SKU),
                        mConsumeFinishedListener);
            }
        }

        IabHelper.OnConsumeFinishedListener mConsumeFinishedListener =
                new IabHelper.OnConsumeFinishedListener() {
                    public void onConsumeFinished(Purchase purchase,
                                                  IabResult result) {

                        if (result.isSuccess()) {
                            buyHelios.setEnabled(true);
                        } else {
                            // handle error
                        }
                    }
                };


    };

    private void buyClick(View view) {
        mHelper.launchPurchaseFlow(this, ITEM_SKU, 10001, mPurchaseFinishedListener,
                "1000 Helios");
    }

    @Override
    public void onStop() {
        super.onStop();
        dataBase.setHeliosReceived(heliosReceived);
        dataBase.setHelios(helios);
        dataBase.setFirstTimeStore(false);
        //dataBase.setTicket(Integer.valueOf(ticketID.getText().toString()));
        try {
            dataBase.update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        if (mHelper != null) mHelper.dispose();
        mHelper = null;
        dataBase.setHeliosReceived(heliosReceived);
        dataBase.setHelios(helios);
        dataBase.setFirstTimeStore(false);
        //dataBase.setTicket(Integer.valueOf(ticketID.getText().toString()));
        try {
            dataBase.update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.onDestroy();

    }

    @Override
    public void onBackPressed() {
        if (goBack) {
            lotterySetUp();
            titulo.setVisibility(View.INVISIBLE);
            helioImage.setVisibility(View.VISIBLE);
            store.setVisibility(View.VISIBLE);
        } else if (goHome) {
            homeSetUp();
        }
    }
}


    /*private void HoursCountDown() {
        times++;
        if (frozenTime) {
            say("You stop the 15 Helios reward");
            staticCountDown.setTextColor(R.color.red);
        } else {
            if (times==1){
                min--;
                if (min == -1) {
                    min = 59;
                    --hour;
                }
                String time = (hour <= 9 ? "0" : "") + hour + ":" + (min <= 9 ? "0" : "") + min;
                staticCountDown.setText(time);
                staticCountDown.setTextColor(R.color.green);
            }
        }
    }

    private void HourscountDownTimer() {
        new CountDownTimer(1209600000, 1000) {


            public void onTick(long millisUntilFinished) {
                HoursCountDown();
            }

            public void onFinish() {
                if (mAdView.getVisibility() == View.VISIBLE) {
                    dataBase.setHelios(dataBase.getHelios() + 15);
                    helioAmount.setText(Integer.valueOf(helioAmount.getText().toString()) + 15);
                    try {
                        dataBase.update();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }

        }.start();

    }*/
