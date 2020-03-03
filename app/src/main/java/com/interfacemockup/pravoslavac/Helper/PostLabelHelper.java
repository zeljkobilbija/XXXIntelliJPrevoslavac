package com.interfacemockup.pravoslavac.Helper;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.Collection;
import java.util.Date;

public class PostLabelHelper extends TextView {

    private int CRVENI_TEXT  = Color.parseColor("#CF331F");
    private  int NORMALAN_COLOR = Color.parseColor("#CCD4D4");


    public PostLabelHelper(Context context) {
        super(context);
    }

    public PostLabelHelper(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PostLabelHelper(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setCrveniLabel(Date danas, Date izmenjeniDatum, int rb_dana_u_nedelji){





            this.setTextColor(Color.parseColor("#CF331F"));
    }

    public void set_post_text(Date danas, Date sutra, int rb_dan_u_nedelji, int rb_dan_u_godini, int counter){



    }

}
