package com.interfacemockup.pravoslavac;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.interfacemockup.pravoslavac.Helper.KalendarHelper;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GestureDetectorCompat;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;
//import com.google.android.gms.ads.MobileAds;

import com.google.android.gms.ads.*;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.lang.reflect.Array;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
//import com.google.firebase.crashlytics.CrashlyticsRegistrar;
//import com.google.firebase.crashlytics.core.CrashlyticsCore;


@RequiresApi(api = Build.VERSION_CODES.N)
//TODO 1. ukloniti comment aco ne radi app zbog gestures
//////////////////  START GESTURES   /////////////////////
//////////////////  END GESTURES   /////////////////////
public class MainActivity extends AppCompatActivity  {

    private FirebaseAnalytics       mFirebaseAnalytics;

    private static final String     TAG = "MainActivity";
    private AdView                  mAdView;

    private int                     redniBrojDanauGodini;
    private int                     _counter;
    private KalendarHelper          sharedHelper = KalendarHelper.getInstance();

    private TextView                _post;
    private TextView                _novi_datum;
    private ImageView               ikona;
    private TextView                _svetitelj;
    private TextView                _stari_datum;

    private boolean                 prestupna_je_godina;

    private FrameLayout             pozadinskiFrame;
    private GestureDetectorCompat   mojGesture;
    private ConstraintLayout        _pozadina;

    public MainActivity() {

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
/////////////////  AdMob ////////////
        //TODO :aktivirati AdMob na ove dve linije coda ispod
        //MobileAds.initialize(this, "ca-app-pub-7920431183682527~1369121836");
        //loadAdMobRequest();
        /**************
         * ************/

        /*     MESSAGE TOKEN    */
        subskribeMessages();
       // FirebaseMessaging.getInstance().setAutoInitEnabled(true);


        Calendar cale = GregorianCalendar.getInstance();
        redniBrojDanauGodini = cale.get(Calendar.DAY_OF_YEAR) - 1;

        setCalendar();
        setNoviDatumLabel(_counter);



        setSvetiteljLabelText(redniBrojDanauGodini);

        _pozadina = (ConstraintLayout)findViewById(R.id.pozadina);
        pozadinskiFrame = (FrameLayout)findViewById(R.id.bg_frame);
        ikona = (ImageView)findViewById(R.id.id_ikona);
        setIkonu(redniBrojDanauGodini);


        //TODO 2. ukloniti comment aco ne radi app zbog gestures
        //////////////////  START GESTURES   /////////////////////
        //////////////////  END GESTURES   /////////////////////



        _pozadina.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            public void onSwipeTop() {
                //TODO :aktivirati AdMob ispod
                //loadAdMobRequest();
                Calendar cc = Calendar.getInstance();
                redniBrojDanauGodini = cc.get(Calendar.DAY_OF_YEAR) - 1;
                _counter = 0;
                setIkonu(redniBrojDanauGodini);
                setSvetiteljLabelText(redniBrojDanauGodini);
                setNoviDatumLabel(_counter);
                yoyoUporBottom();
            }
            public void onSwipeRight() {
                //TODO :aktivirati AdMob ispod
               // loadAdMobRequest();
                redniBrojDanauGodini = redniBrojDanauGodini - 1;
                _counter = _counter - 1;
                ikona = (ImageView)findViewById(R.id.id_ikona);
                setIkonu(redniBrojDanauGodini);
                setSvetiteljLabelText(redniBrojDanauGodini);
                setNoviDatumLabel(_counter);
                udjiSleva();

            }
            public void onSwipeLeft() {
                //TODO :aktivirati AdMob ispod
                //loadAdMobRequest();
                redniBrojDanauGodini = redniBrojDanauGodini + 1;
                _counter = _counter + 1;
                setIkonu(redniBrojDanauGodini);

                setSvetiteljLabelText(redniBrojDanauGodini);
                setNoviDatumLabel(_counter);
                udjiSdesna();
            }
            public void onSwipeBottom() {
                //TODO :aktivirati AdMob ispod
                //loadAdMobRequest();
                Calendar cc = Calendar.getInstance();
                redniBrojDanauGodini = cc.get(Calendar.DAY_OF_YEAR) - 1;
                _counter = 0;
                setIkonu(redniBrojDanauGodini);
                setSvetiteljLabelText(redniBrojDanauGodini);
                setNoviDatumLabel(_counter);
                yoyoUporBottom();
            }

        });
        

    }

    public void runtimeEnableAutoInit() {
        // [START fcm_runtime_enable_auto_init]
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        // [END fcm_runtime_enable_auto_init]
        subskribeMessages();
    }

    public void loadAdMobRequest(){
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }


    public void setCalendar(){
        Calendar cc = GregorianCalendar.getInstance();
        if(cc.isLenient()){
            prestupna_je_godina = true;
        }else {
            prestupna_je_godina = false;
        }
        redniBrojDanauGodini = cc.get(GregorianCalendar.DAY_OF_YEAR) - 1;

    }

   // @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    public void setNoviDatumLabel(int counter){
        Calendar cal = GregorianCalendar.getInstance();
        String pattern = "d. MMM yyyy.";
        String paternDan = "EEEE ";
        Date danas = cal.getTime();
        cal.add(GregorianCalendar.DATE, counter);
        Date izmenjenidatum = cal.getTime();

        Locale locale = new Locale("sr", "SR");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        SimpleDateFormat danFormat = new SimpleDateFormat(paternDan, locale);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, locale);
        String date = sdf.format(izmenjenidatum);
        String danDate = danFormat.format(izmenjenidatum);

        //Redni broj dana u nedelji 7 = nedelja *******************
        String rbnedeljnogDana = "e";
        SimpleDateFormat rbDanuNedelji = new SimpleDateFormat(rbnedeljnogDana, locale);
        String rbDan = rbDanuNedelji.format(izmenjenidatum);
        int broj = Integer.parseInt(rbDan);
        _novi_datum = findViewById(R.id.novi_datum_label) ;
        _svetitelj = findViewById(R.id.id_svetitelj_text_view);
        _post = findViewById(R.id.id_post_label) ;
        sharedHelper.setTekstColor( _novi_datum, _svetitelj, _post, broj);
       // setTextColors(broj);
        //setPostLabel(broj);
        //*********************************************************


        _novi_datum = findViewById(R.id.novi_datum_label);
        String dd = danDate + date + " god.";
        _novi_datum.setText(dd);
        //_novi_datum.setText(danDate + date + " god.");


        Date stari = setStariDatum(counter);
        String stariDatum = sdf.format(stari);
        _stari_datum = (TextView)findViewById(R.id.stari_datum_label);
        //_stari_datum.setText(danDate + stariDatum + " god.");
        _stari_datum.setText(stariDatum + " god.");

    }

    public void setPostLabel(int broj){
        _post = (TextView)findViewById(R.id.id_post_label);
        if (broj == 3 || broj == 5){
            _post.setText("POST");
        }else{
            _post.setText("Nije Post");
        }
    }

    public void setTextColors(int broj){
        if (broj == 7) {
            setRedColor();
        }else {
            setNormalanColor();
        }
    }

    public void setRedColor(){
        _svetitelj = (TextView)findViewById(R.id.id_svetitelj_text_view);
        //_svetitelj.setTextColor(Color.RED);
        _svetitelj.setTextColor(Color.parseColor("#CF331F"));
        _novi_datum = (TextView)findViewById(R.id.novi_datum_label);
       // _novi_datum.setTextColor(Color.RED);
        _novi_datum.setTextColor(Color.parseColor("#CF331F"));
       // _stari_datum = (TextView)findViewById(R.id.stari_datum_label);
       // _stari_datum.setTextColor(Color.RED);
    }

    public void setNormalanColor(){
        _svetitelj = (TextView)findViewById(R.id.id_svetitelj_text_view);
        _svetitelj.setTextColor(Color.parseColor("#CCD4D4"));
        _novi_datum = (TextView)findViewById(R.id.novi_datum_label);
        _novi_datum.setTextColor(Color.parseColor("#CCD4D4"));
        //_stari_datum = (TextView)findViewById(R.id.stari_datum_label);
        // _stari_datum.setTextColor(Color.parseColor("#ABCDEF"));
    }

    private static Date setStariDatum(int counter) {
        Calendar calendar = GregorianCalendar.getInstance();
        Date danas = calendar.getTime();
        calendar.add(GregorianCalendar.DATE, -13 + counter); // oduzima 13 dana
        Date stariKalendar = calendar.getTime();
        return stariKalendar;
    }

    private static Date umanjiDatum(int counter) {
        Calendar calendar = GregorianCalendar.getInstance();
        Date danas = calendar.getTime();
        calendar.add(GregorianCalendar.DATE, counter);
        Date izmenjeniDatum = calendar.getTime();
        return izmenjeniDatum;
    }

    private static Date uvecajDatum(int counter) {
        Calendar calendar = GregorianCalendar.getInstance();
        Date danas = calendar.getTime();
        calendar.add(GregorianCalendar.DATE, counter);
        Date izmenjeniDatum = calendar.getTime();
        return izmenjeniDatum;
    }



    public void setSvetiteljLabelText(int rb_dana){
        _svetitelj = (TextView)findViewById(R.id.id_svetitelj_text_view);
        _svetitelj.setAllCaps(true); //Ovo sam regulisao u activity_main.xml fajlu
        String[] imeSvetitelja;
        if (prestupna_je_godina)
            imeSvetitelja = getResources().getStringArray(R.array.imena_svetitelja_prestupna_godina);
        else {
            imeSvetitelja = getResources().getStringArray(R.array.imena_svetitelja_prosta_godina);
        }
        _svetitelj.setText(imeSvetitelja[rb_dana]);

    }

    public void setIkonu(int rb_ikone){
        //TODO Ova linija ispode je mozda visak
        ikona.setImageResource(sharedHelper.setIkone(rb_ikone));

        //if (prestupna_je_godina)
            //ikona.setImageResource(dravablesPrestupnaGodina[rb_ikone]);
            //ikona.setImageResource(sharedHelper.setIkone(rb_ikone));
        //else {
            //ikona.setImageResource(drawablesProstaGodina[rb_ikone]);
        //}
    }

    public void udjiSdesna(){
        YoYo.with(Techniques.ZoomInRight)
                .duration(650)
                .repeat(0)
                .playOn(ikona);
    }


    public void udjiSleva(){
        YoYo.with(Techniques.ZoomInLeft)
                .duration(650)
                .repeat(0)
                .playOn(ikona);
    }


    public void yoyoUporBottom(){
        YoYo.with(Techniques.ZoomIn)
                .duration(550)
                .repeat(0)
                .playOn(ikona);

    }
    //TODO 3.ukloniti comment aco ne radi app zbog gestures
    //////////////////  START GESTURES   /////////////////////
    //////////////////  END GESTURES   /////////////////////

    //TODO 4.ukloniti comment aco ne radi app zbog gestures
    //////////////////  OVERIDE !!!!!!   /////////////////////
    //////////////////  OVERIDE !!!!!!   /////////////////////

    public void subskribeMessages(){

        FirebaseMessaging.getInstance().subscribeToTopic("weather")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = getString(R.string.msg_subscribed);
                        if (!task.isSuccessful()) {
                            msg = getString(R.string.msg_subscribe_failed);
                        }
                        Log.d(TAG, msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
                    }
                });

    }




}
