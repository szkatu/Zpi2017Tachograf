package com.example.msi.zpi_interfejs;


public class Element {
    int typ;
    String start;
    String stop;
    String czas;

    Element(int typ, String start, String stop, String czas){
        this.typ = typ;
        this.start = start;
        this.stop = stop;
        this.czas = czas;
    }
}
