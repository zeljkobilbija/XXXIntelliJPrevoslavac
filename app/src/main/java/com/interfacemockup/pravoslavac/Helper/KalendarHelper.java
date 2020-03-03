package com.interfacemockup.pravoslavac.Helper;


import android.graphics.Color;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class KalendarHelper {

    private static final KalendarHelper instance = new KalendarHelper();

    private KalendarHelper() { }


    public static KalendarHelper getInstance(){

        return instance;
    }


    ConstantHelper sharedConstant =  new ConstantHelper();



    public int setIkone(int rb_dana_u_godini){

        Calendar cal = GregorianCalendar.getInstance();
        if (cal.isLenient()){
            return sharedConstant.dravables_prestupna_godina[rb_dana_u_godini];
        }else {
            return sharedConstant.drawables_prosta_godina[rb_dana_u_godini];
        }
    }

    public void setTekstColor(TextView noviDatumLabel, TextView svetiteljLabel, TextView postLabel, int rb_dana_u_nedelji){

        if (rb_dana_u_nedelji == 7){
            noviDatumLabel.setTextColor(Color.parseColor("#CF331F"));
            svetiteljLabel.setTextColor(Color.parseColor("#CF331F"));
        }else {
            noviDatumLabel.setTextColor(Color.parseColor("#CCD4D4"));
            svetiteljLabel.setTextColor(Color.parseColor("#CCD4D4"));
        }
        setPostLabelText(postLabel, rb_dana_u_nedelji);
    }

    private void setPostLabelText(TextView label, int rb_dana_u_nedelji){

        ArrayList<Integer> praznici = new ArrayList<Integer>();
        praznici = sharedConstant.vaskrsnjiArray;

        int rb_dana_u_godini = 0;



        if (rb_dana_u_nedelji == 3 || rb_dana_u_nedelji == 5){
            label.setText("ПОСТ");
        }else {
            label.setText("");
        }
    }


;}
