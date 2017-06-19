package com.example.msi.zpi_interfejs;


import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Calendar;


public class Temp extends Fragment {

    public TextView stan, jazda_Text, jazda_dzienna_Text, jazda_tygodniowa_Text, jazda_2tygodniowa_Text, przerwa_Text, odpoczynek_Text, odpoczynek_tygodniowy_Text;
    public ProgressBar czas_prowadzenia,czas_prowadzenia_dzienny,czas_prowadzenia_tygodniowy,czas_prowadzenia_2tygodniowy,czas_przerwy,czas_odpoczynku, czas_odpoczynku_tygodniowy;
    public Button jazda, odpoczynek, przerwa;
    public Kierowca kierowca;
    public NotificationCompat.Builder mBuilder;
    SharedPreferences sP;
    Calendar c;
    Runnable runnable;

    public Temp() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_temp, container, false);
        sP = getContext().getSharedPreferences("czasy", Context.MODE_PRIVATE);
        kierowca = new Kierowca();
        long time = c.getInstance().getTimeInMillis() - sP.getLong("time",c.getInstance().getTimeInMillis());
        time = time / DateUtils.MINUTE_IN_MILLIS;
        kierowca.restore(sP.getInt("stan",0),sP.getInt("czasProwadzenia",0),sP.getInt("czasProwadzeniaDzien",0),sP.getInt("czasPrzerwy",0),sP.getInt("czasProwadzeniaTyg",0),sP.getInt("czasProwadzenia2Tyg",0),sP.getInt("czasOdpoczynkuDzien",0),sP.getInt("czasOdpoczynkuTyg",0),(int)time);
        jazda = (Button)view.findViewById(R.id.jazda);
        odpoczynek = (Button)view.findViewById(R.id.odpoczynek);
        przerwa = (Button)view.findViewById(R.id.przerwa);


        mBuilder = new NotificationCompat.Builder(getContext())
                .setSmallIcon(R.drawable.truck)
                .setContentTitle("Wojażer")
                .setContentText("");
        stan = (TextView)view.findViewById(R.id.stan);



        jazda_Text = (TextView)view.findViewById(R.id.jazda_Text);
        jazda_dzienna_Text = (TextView)view.findViewById(R.id.jazda_dzienna_Text);
        jazda_tygodniowa_Text = (TextView)view.findViewById(R.id.jazda_tygodniowa_Text);
        jazda_2tygodniowa_Text = (TextView)view.findViewById(R.id.jazda_2tygodniowa_Text);
        odpoczynek_Text = (TextView)view.findViewById(R.id.odpoczynek_Text);
        przerwa_Text = (TextView)view.findViewById(R.id.przerwa_Text);
        odpoczynek_tygodniowy_Text = (TextView)view.findViewById(R.id.odpoczynek_tygodniowy_Text);
        czas_prowadzenia = (ProgressBar)view.findViewById(R.id.czas_prowadzenia);
        czas_prowadzenia_dzienny = (ProgressBar)view.findViewById(R.id.czas_prowadzenia_dzienny);
        czas_prowadzenia_tygodniowy = (ProgressBar)view.findViewById(R.id.czas_prowadzenia_tygodniowy);
        czas_prowadzenia_2tygodniowy = (ProgressBar)view.findViewById(R.id.czas_prowadzenia_2tygodniowy);
        czas_przerwy = (ProgressBar)view.findViewById(R.id.czas_przerwy);
        czas_odpoczynku = (ProgressBar)view.findViewById(R.id.czas_odpoczynku);
        czas_odpoczynku_tygodniowy = (ProgressBar)view.findViewById(R.id.czas_odpoczynku_tygodniowy);
        setT(czas_prowadzenia, jazda_Text, kierowca.czasProwadzenia);
        setT(czas_prowadzenia_dzienny,jazda_dzienna_Text,kierowca.calkCzasProwadzenia);
        setT(czas_prowadzenia_tygodniowy, jazda_tygodniowa_Text, kierowca.czasPracyTyg);
        setT(czas_prowadzenia_2tygodniowy, jazda_2tygodniowa_Text, kierowca.czasPracy2Tyg);
        setT(czas_przerwy, przerwa_Text, kierowca.czasPrzerwy);
        setT(czas_odpoczynku, odpoczynek_Text, kierowca.czasOdpoczynku);
        setT(czas_odpoczynku_tygodniowy, odpoczynek_tygodniowy_Text, kierowca.czasOdpoczynkuTyg);

        return view;
    }

    public void setT(ProgressBar p, TextView t, int x)
    {
        p.setProgress(x);
        t.setText("" + x / 60 + "h" + x % 60 + "m");
    }

    public void kierowcaMove(Strona strona)
    {
        int x = 0;
        switch(kierowca.stan)
        {
            case 1:
                kierowca.jazda();
                stan.setText("JAZDA");
                setT(czas_prowadzenia, jazda_Text, kierowca.czasProwadzenia);
                setT(czas_prowadzenia_dzienny, jazda_dzienna_Text, kierowca.calkCzasProwadzenia);
                setT(czas_prowadzenia_tygodniowy, jazda_tygodniowa_Text, kierowca.czasPracyTyg);
                setT(czas_prowadzenia_2tygodniowy, jazda_2tygodniowa_Text, kierowca.czasPracy2Tyg);
                strona.mProgressIndicator.setValue(Math.abs(1-(float)kierowca.czasProwadzenia/(float)Kierowca.cz1));
                x = Kierowca.cz1 - kierowca.czasProwadzenia;
                strona.setTekst("" + x / 60 + ":" + x % 60);
                if(x < 0)strona.setTekst2("Więcej o");
                mBuilder.setProgress(Kierowca.cz1, kierowca.czasProwadzenia, false);
                mBuilder.setContentText("Jazda");
            break;
            case 2:
                kierowca.przerwa();
                stan.setText("PRZERWA");
                setT(czas_przerwy, przerwa_Text, kierowca.czasPrzerwy);
                strona.mProgressIndicator.setValue(Math.abs(1-(float)kierowca.czasPrzerwy/(float)Kierowca.cz2));
                x = Kierowca.cz2 - kierowca.czasPrzerwy;
                strona.setTekst("" + x / 60 + ":" + x % 60);
                if(x < 0)strona.setTekst2("Więcej o");
                mBuilder.setProgress(Kierowca.cz2, kierowca.czasPrzerwy, false);
                mBuilder.setContentText("Przerwa");
                break;
            case 3:
                kierowca.odpoczynekDzienny();
                stan.setText("ODPOCZYNEK");
                setT(czas_odpoczynku, odpoczynek_Text, kierowca.czasOdpoczynkuDzien);
                strona.mProgressIndicator.setValue(Math.abs(1-(float)kierowca.czasOdpoczynkuDzien/(float)Kierowca.cz7));
                x = Kierowca.cz7 - kierowca.czasOdpoczynkuDzien;
                strona.setTekst("" + x / 60 + ":" + x % 60);
                if(x < 0)strona.setTekst2("Więcej o");
                mBuilder.setProgress(Kierowca.cz7, kierowca.czasOdpoczynkuDzien, false);
                mBuilder.setContentText("Odpoczynek");
                break;
            case 5:
                kierowca.odpoczynekTygodniowy();
                stan.setText("ODPOCZYNEK");
                setT(czas_odpoczynku_tygodniowy, odpoczynek_tygodniowy_Text, kierowca.czasOdpoczynkuTyg);
                strona.mProgressIndicator.setValue(Math.abs(1-(float)kierowca.czasOdpoczynkuTyg/(float)Kierowca.cz6));
                x = Kierowca.cz6 - kierowca.czasOdpoczynkuTyg;
                strona.setTekst("" + x / 60 + ":" + x % 60);
                if(x < 0)strona.setTekst2("Więcej o");
                mBuilder.setProgress(Kierowca.cz5, kierowca.czasOdpoczynkuTyg, false);
                mBuilder.setContentText("Odpoczynek");
                break;

            default:
                break;
        }
    }



    public void jazda()
    {
        kierowca.stan = 1;
        stan.setText("JAZDA");
        mBuilder.setProgress(Kierowca.cz1, kierowca.czasProwadzenia, false);
        mBuilder.setContentText("Jazda");
    }

    public void przerwa()
    {
        kierowca.stan = 2;
        stan.setText("PRZERWA");
        mBuilder.setProgress(Kierowca.cz2, kierowca.czasPrzerwy, false);
        mBuilder.setContentText("Przerwa");
    }

    public void odpoczynek()
    {
        kierowca.stan = 3;
        stan.setText("ODPOCZYNEK");
        mBuilder.setProgress(Kierowca.cz7, kierowca.czasOdpoczynku, false);
        mBuilder.setContentText("Odpoczynek");
    }

}
