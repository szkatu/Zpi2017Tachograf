package harrel.gpsmodule;

/**
 * Created by Olek C on 2017-03-28.
 */
public class Kierowca {

    int interv = 1;            //co sekunde o tyle minut
    public int stan;            //1-jazda, 2 - przerwa, 3 - dzienny_odpoczynek, 4 - przerwa skrocona, 5 - podzial_odpoczynek,
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

    public Kierowca()
    {
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

    public void jazda()
    {
        czasProwadzenia += interv;
        calkCzasProwadzenia += interv;
        czasPracyTyg += interv;
        czasPracy2Tyg += interv;
    }

    public void odpoczynekDzienny()
    {
        czasOdpoczynku += interv;
    }

    public void odpoczynekTygodniowy()
    {
        czasOdpoczynku += interv;
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
        if(czasProwadzenia >= 270*60 && !skroconoPrzerwe)
        {
            czasProwadzenia = 0;
            return true;
        }
        else if(czasProwadzenia >= 270*60 && skroconoPrzerwe)
        {
            czasProwadzenia = 0;
            skroconoPrzerwe = false;
            return true;
        }
        else return false;
    }

    public boolean jazda_skroconaPrzerwa()
    {
        if(czasProwadzenia < 270*60 && !skroconoPrzerwe)
        {
            skroconoPrzerwe = true;
            return true;
        }

        else return false;
    }

    public boolean przerwa_jazda()
    {
        if(czasPrzerwy >= 45*60)
        {
            czasPrzerwy = 0;
            return true;
        }
        else return false;
    }

    public boolean skroconaPrzerwa_jazda()
    {
        if(czasPrzerwy >= 15*60 && skroconoPrzerwe)
        {
            return true;
        }
        else if(czasPrzerwy >= 30*60 && !skroconoPrzerwe)
        {
            return true;
        }
        else return false;
    }

    public boolean jazda_odpoczynekDzienny()
    {
        if(calkCzasProwadzenia >= 540*60)
        {
            calkCzasProwadzenia = 0;
            czasProwadzenia = 0;
            return true;
        }
        else if(czasOdpoczynkuDzien == 24*60 *60|| czasOdpoczynkuTyg == 24*60*60)
        {
            calkCzasProwadzenia = 0;
            czasProwadzenia = 0;
            return true;
        }
        else return false;
    }

    public boolean jazda_skroconyOdpoczynekDzienny()
    {
        if(calkCzasProwadzenia < 540*60)
        {
            podzialOdpoczynku = true;
            return true;
        }
        else return false;
    }

    public boolean skroconyOdpoczynekDzienny_jazda()
    {
        if(czasOdpoczynku >= 180*60 && podzialOdpoczynku)
        {
            podzialOdpoczynku = false;
            odpoczeto_3h = true;
            return true;
        }
        else if(czasOdpoczynku >= 540*60 && odpoczeto_3h)
        {
            odpoczeto_3h = false;
            return true;
        }
        else return false;
    }

    public boolean odpoczynekDzienny_jazda()
    {
        if(czasOdpoczynku >= 660*60)
        {
            czasOdpoczynku = 0;
            return true;
        }

        else return false;
    }

    public boolean jazda_jazdaPrzedluzona()
    {
        if(calkCzasProwadzenia >= 540*60 && liczbaPrzedluzen <= 2)
        {
            liczbaPrzedluzen++;
            return true;
        }
        else return false;
    }

    public boolean jazdaPrzedluzona_odpoczynekDzienny()
    {
        if(calkCzasProwadzenia >= 600*60)
        {
            return true;
        }
        else return false;
    }

    public boolean jazda_odpoczynekTygodniowy()
    {
        if(czasPracyTyg >= 56*60*60 || czasPracy2Tyg >= 90*60*60)
        {
            czasProwadzenia = 0;
            calkCzasProwadzenia = 0;
            return true;
        }
        else if(czasOstatniegoOdpoczynku >= 6*24*60*60)
        {
            czasProwadzenia = 0;
            calkCzasProwadzenia = 0;
            return true;
        }
        else return false;
    }

    public boolean odpoczynekTygodniowy_jazda()
    {
        if(czasOdpoczynku >= 45*60*60)
        {
            czasOdpoczynku = 0;
            skrocenieTyg = true;
            return true;
        }
        else return false;
    }

    public boolean odpoczynekTygodniowy_skroconyOdpoczynekTygodniowy()
    {
        if(czasOdpoczynku >= 24*60*60 && !skrocenieTyg)
        {
            return true;
        }
        else return false;
    }







}
