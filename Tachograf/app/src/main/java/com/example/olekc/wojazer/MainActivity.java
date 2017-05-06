package com.example.olekc.wojazer;

import android.app.AlertDialog;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private TextView stan, jazda_Text, jazda_dzienna_Text, jazda_tygodniowa_Text, jazda_2tygodniowa_Text, przerwa_Text, odpoczynek_Text, odpoczynek_tygodniowy_Text;
    private ProgressBar czas_prowadzenia,czas_prowadzenia_dzienny,czas_prowadzenia_tygodniowy,czas_prowadzenia_2tygodniowy,czas_przerwy,czas_odpoczynku, czas_odpoczynku_tygodniowy;
    private Button jazda, odpoczynek, przerwa, stopp;
    private Handler handler;
    private Kierowca kierowca;
    int count;
    boolean f,j,p,o;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jazda = (Button)findViewById(R.id.jazda);
        odpoczynek = (Button)findViewById(R.id.odpoczynek);
        przerwa = (Button)findViewById(R.id.przerwa);
        stopp = (Button)findViewById(R.id.stopp);
        stan = (TextView)findViewById(R.id.stan);
        kierowca = new Kierowca();
        handler = new Handler();
        jazda_Text = (TextView)findViewById(R.id.jazda_Text);
        jazda_dzienna_Text = (TextView)findViewById(R.id.jazda_dzienna_Text);
        jazda_tygodniowa_Text = (TextView)findViewById(R.id.jazda_tygodniowa_Text);
        jazda_2tygodniowa_Text = (TextView)findViewById(R.id.jazda_2tygodniowa_Text);
        odpoczynek_Text = (TextView)findViewById(R.id.odpoczynek_Text);
        przerwa_Text = (TextView)findViewById(R.id.przerwa_Text);
        odpoczynek_tygodniowy_Text = (TextView)findViewById(R.id.odpoczynek_tygodniowy_Text);
        czas_prowadzenia = (ProgressBar)findViewById(R.id.czas_prowadzenia);
        czas_prowadzenia_dzienny = (ProgressBar)findViewById(R.id.czas_prowadzenia_dzienny);
        czas_prowadzenia_tygodniowy = (ProgressBar)findViewById(R.id.czas_prowadzenia_tygodniowy);
        czas_prowadzenia_2tygodniowy = (ProgressBar)findViewById(R.id.czas_prowadzenia_2tygodniowy);
        czas_przerwy = (ProgressBar)findViewById(R.id.czas_przerwy);
        czas_odpoczynku = (ProgressBar)findViewById(R.id.czas_odpoczynku);
        czas_odpoczynku_tygodniowy = (ProgressBar)findViewById(R.id.czas_odpoczynku_tygodniowy);

        czas_prowadzenia.setProgress(0);
        czas_przerwy.setProgress(0);
        czas_odpoczynku.setProgress(0);

        count = 0;
        f = j = p = o = false;
        final Runnable runnable = new Runnable() {

            public void run()
            {


                switch(kierowca.stan){
                    case 1: //jazda
                    kierowca.jazda();
                    stan.setText("JAZDA");
                    setT(czas_prowadzenia, jazda_Text, kierowca.czasProwadzenia);
                    setT(czas_prowadzenia_dzienny,jazda_dzienna_Text,kierowca.calkCzasProwadzenia);
                    setT(czas_prowadzenia_tygodniowy,jazda_tygodniowa_Text,kierowca.czasPracyTyg);
                    setT(czas_prowadzenia_2tygodniowy,jazda_2tygodniowa_Text,kierowca.czasPracy2Tyg);
                    if(kierowca.jazda_odpoczynekTygodniowy())
                    {
                        kierowca.stan = 5;
                    }
                    else if(kierowca.jazda_odpoczynekDzienny())
                    {
                        kierowca.stan = 3;
                        j = false;
                        o = true;
                    }
                    else if(kierowca.jazda_przerwa())
                    {
                        kierowca.stan = 2;
                        j = false;
                        p = true;
                    }
                    break;

                    case 2: //przerwa
                    kierowca.przerwa();
                    stan.setText("PRZERWA");
                    setT(czas_przerwy,przerwa_Text,kierowca.czasPrzerwy);
                    if (kierowca.przerwa_jazda())
                    {
                        kierowca.stan = 1;
                        p = false;
                        j = true;
                    }
                    break;

                    case 3: //odpoczynek dzienny
                    kierowca.odpoczynekDzienny();
                    stan.setText("ODPOCZYNEK");
                   setT(czas_odpoczynku,odpoczynek_Text,kierowca.czasOdpoczynku);
                    if(kierowca.odpoczynekDzienny_jazda())
                    {
                        kierowca.stan = 1;
                        o = false;
                        j = true;
                    }
                    break;

                    case 4: //skrocona przerwa
                    kierowca.przerwa();
                        stan.setText("SKRÓCONA PRZERWA");
                        setT(czas_przerwy,przerwa_Text,kierowca.czasPrzerwy);
                        if(kierowca.skroconaPrzerwa_jazda())
                        {
                            kierowca.stan = 1;
                        }
                    break;

                    case 5: //odpoczynek tygodniowy
                    kierowca.odpoczynekTygodniowy();
                        stan.setText("ODPOCZYNEK TYGODNIOWY");
                        setT(czas_odpoczynku_tygodniowy,odpoczynek_tygodniowy_Text,kierowca.czasOdpoczynku);
                        if(kierowca.odpoczynekTygodniowy_jazda())
                        {
                            kierowca.stan = 1;
                        }
                        break;
                    case 6: //skrocony dzienny odpoczynek
                        kierowca.odpoczynekDzienny();
                        stan.setText("SKRÓCONY DZIENNY ODPOCZYNEK");
                        setT(czas_odpoczynku,odpoczynek_Text,kierowca.czasOdpoczynku);
                        if(kierowca.skroconyOdpoczynekDzienny_jazda())
                        {
                            kierowca.stan = 1;
                        }


                    default:
                        break;
                }
                if(!f)handler.postDelayed(this, 1000);
            }
        };



        handler.postDelayed(runnable,1000);
    }

    public void jazda(View view)
    {
        kierowca.stan = 1;
        j = true;
        p = o = false;

    }

    public void przerwa(View view)
    {
        if(kierowca.jazda_skroconaPrzerwa())kierowca.stan=4;
        else kierowca.stan = 2;
        p = true;
        j = o = false;
    }

    public void odpoczynek(View view)
    {
        if(kierowca.jazda_skroconyOdpoczynekDzienny())
        {
            kierowca.stan = 6;
        }
        else kierowca.stan = 3;
        o = true;
        j = p = false;

    }

    public void stopp(View view)
    {
        f = true;
    }

    public void setT(ProgressBar p, TextView t, int x)
    {
        p.setProgress(x);
        t.setText("" + x / 60 + "h" + x % 60 + "m");
    }
}
