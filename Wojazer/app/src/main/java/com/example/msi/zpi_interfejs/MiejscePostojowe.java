package com.example.msi.zpi_interfejs;

public class MiejscePostojowe {
    String nazwa;
    String typ;
    String czas;
    String odleglosc;
    int obraz;

    MiejscePostojowe(String nazwa, String typ, String cz, String odleglosc, int obraz){
        this.nazwa = nazwa;
        this.typ = typ;
        this.czas = cz;
        this.odleglosc = odleglosc;
        this.obraz=obraz;
    }
}
