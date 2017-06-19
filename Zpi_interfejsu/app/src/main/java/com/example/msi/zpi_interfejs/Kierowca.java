package com.example.msi.zpi_interfejs;

/**
 * Created by Olek C on 2017-06-17.
 */

public class Kierowca {

    int interv = 1;            //co minute o tyle minut
    int stan;            //1-jazda, 2 - przerwa, 3 - dzienny_odpoczynek, 4 - przerwa skrocona, 5 - podzial_odpoczynek,
    //6 - skrocony_odpoczynek
    int czasPracy;                 //czas pracy
    int czasProwadzenia;           //czas ciaglego prowadzenia pojazdu
    int liczbaPrzedluzen;          //liczba przedluzen w tygodniu
    boolean skroconoPrzerwe;       //czy skrocono przerwe
    int czasPrzerwy;               //czas przerwy
    int calkCzasProwadzenia;       //calkowity czas prowadzenia pojazdu
    int czasOdpoczynku;            //czas odpoczynku
    int czasOdpoczynkuDzien;       //czas od odpoczynku dziennego
    int czasOdpoczynkuTyg;         //czas od odpoczynku tygodniowego
    boolean podzialOdpoczynku;     //podzial odpoczynku
    int skrocenieOdpoczynku;       //ilosc skrocen odpoczynku w przeciagu 2 tygodniowych odpoczynkow
    int czasPracyTyg;              //tygodniowy czas pracy
    int czasPracy2Tyg;             //2tygodniowy czas pracy
    int czasOstatniegoOdpoczynku;  //czas od ostatniego tygodniowego odpoczynku
    boolean skrocenieTyg;          //skrocenie w poprzednim tygodniu
    boolean odpoczeto_3h;

    public static final int cz1 = 270;    //ciągły dzienny czas prowadzenia
    public static final int cz2 = 45;     //czas niepodzielonej przerwy
    public static final int cz3 = 15;     //czas pierwszej podzieonej przerwy
    public static final int cz4 = 30;     //czas drugiej podzielonej przerwy
    public static final int cz5 = 540;    //całkowity dzienny czas prowadzenia
    public static final int cz6 = 45*60;  //czas odpoczynku tygodniowego
    public static final int cz7 = 24*60;  //czas odpoczynku dziennego
    public static final int cz8 = 660;    //czas
    public static final int cz9 = 180;    //czas podzielonego odpoczynku
    public static final int cz10 = 56*60; //czas od opoczynku
    public static final int cz11 = 90*60; //czas od odpoczynku tygodniowego
    public static final int cz12 = 6*24*60;//czas od ostatniego odpoczynku

    public Kierowca()
    {
        stan = 0;
        czasPracy = 0;
        czasProwadzenia = 0;
        liczbaPrzedluzen = 0;
        skroconoPrzerwe = false;
        czasPrzerwy = 0;
        calkCzasProwadzenia = 0;
        czasOdpoczynku = 0;
        czasOdpoczynkuDzien = 0;
        czasOdpoczynkuTyg = 0;
        podzialOdpoczynku = false;
        skrocenieOdpoczynku = 0;
        czasPracy2Tyg = 0;
        czasPracyTyg = 0;
        czasOstatniegoOdpoczynku = 0;
        skrocenieTyg = false;
        odpoczeto_3h = false;
    }

    public void restore(int s, int cp, int ccp, int cpr, int cpt, int cp2t, int co, int cot, int time)
    {
        stan = s;
        if(stan == 1) {
            czasProwadzenia = cp + time;
            calkCzasProwadzenia = ccp + time;
            czasPracyTyg = cpt + time;
            czasPracy2Tyg = cp2t + time;
        }
        else if(stan == 2)
        czasPrzerwy = cpr+time;
        else if(stan == 3)
        czasOdpoczynkuDzien = co+time;
        else if(stan ==5)
        czasOdpoczynkuTyg = cot+time;
    }

    public void jazda()
    {
        czasProwadzenia += interv;
        calkCzasProwadzenia += interv;
        czasPracyTyg += interv;
        czasPracy2Tyg += interv;
    }

    public void odpoczynekDzienny()
    {
        czasOdpoczynkuDzien += interv;
    }

    public void odpoczynekTygodniowy()
    {
        czasOdpoczynkuTyg += interv;
    }

    public void skroconyOdpoczynekDzienny()
    {
        czasOdpoczynku += interv;
    }

    public void skroconyOdpoczynekTygodniowy()
    {
        czasOdpoczynku += interv;
    }

    public void przerwa()
    {
        czasPrzerwy += interv;
    }

    public void skroconaPrzerwa()
    {
        czasPrzerwy += interv;
    }

    public boolean jazda_przerwa()
    {
        if(czasProwadzenia >= cz1 && !skroconoPrzerwe)
        {
            czasProwadzenia = 0;
            return true;
        }
        else if(czasProwadzenia >= cz1 && skroconoPrzerwe)
        {
            czasProwadzenia = 0;
            skroconoPrzerwe = false;
            return true;
        }
        else return false;
    }

    public boolean jazda_skroconaPrzerwa()
    {
        if(czasProwadzenia < cz1 && !skroconoPrzerwe)
        {
            skroconoPrzerwe = true;
            return true;
        }

        else return false;
    }

    public boolean przerwa_jazda()
    {
        if(czasPrzerwy >= cz2)
        {
            czasPrzerwy = 0;
            return true;
        }
        else return false;
    }

    public boolean skroconaPrzerwa_jazda()
    {
        if(czasPrzerwy >= cz3 && skroconoPrzerwe)
        {
            return true;
        }
        else if(czasPrzerwy >= cz4 && !skroconoPrzerwe)
        {
            return true;
        }
        else return false;
    }

    public boolean jazda_odpoczynekDzienny()
    {
        if(calkCzasProwadzenia >= cz5)
        {
            calkCzasProwadzenia = 0;
            czasProwadzenia = 0;
            return true;
        }
        else if(czasOdpoczynkuDzien == cz7 || czasOdpoczynkuTyg == cz7)
        {
            calkCzasProwadzenia = 0;
            czasProwadzenia = 0;
            return true;
        }
        else return false;
    }

    public boolean jazda_skroconyOdpoczynekDzienny()
    {
        if(calkCzasProwadzenia < cz5)
        {
            podzialOdpoczynku = true;
            return true;
        }
        else return false;
    }

    public boolean skroconyOdpoczynekDzienny_jazda()
    {
        if(czasOdpoczynku >= cz9 && podzialOdpoczynku)
        {
            podzialOdpoczynku = false;
            odpoczeto_3h = true;
            return true;
        }
        else if(czasOdpoczynku >= cz5 && odpoczeto_3h)
        {
            odpoczeto_3h = false;
            return true;
        }
        else return false;
    }

    public boolean odpoczynekDzienny_jazda()
    {
        if(czasOdpoczynku >= cz8)
        {
            czasOdpoczynku = 0;
            return true;
        }

        else return false;
    }

    public boolean jazda_jazdaPrzedluzona()
    {
        if(calkCzasProwadzenia >= cz5 && liczbaPrzedluzen <= 2)
        {
            liczbaPrzedluzen++;
            return true;
        }
        else return false;
    }

    public boolean jazdaPrzedluzona_odpoczynekDzienny()
    {
        if(calkCzasProwadzenia >= 600)
        {
            return true;
        }
        else return false;
    }

    public boolean jazda_odpoczynekTygodniowy()
    {
        if(czasPracyTyg >= cz10 || czasPracy2Tyg >= cz11)
        {
            czasProwadzenia = 0;
            calkCzasProwadzenia = 0;
            return true;
        }
        else if(czasOstatniegoOdpoczynku >= cz12)
        {
            czasProwadzenia = 0;
            calkCzasProwadzenia = 0;
            return true;
        }
        else return false;
    }

    public boolean odpoczynekTygodniowy_jazda()
    {
        if(czasOdpoczynku >= cz6)
        {
            czasOdpoczynku = 0;
            skrocenieTyg = true;
            return true;
        }
        else return false;
    }

    public boolean odpoczynekTygodniowy_skroconyOdpoczynekTygodniowy()
    {
        if(czasOdpoczynku >= cz7 && !skrocenieTyg)
        {
            return true;
        }
        else return false;
    }







}
