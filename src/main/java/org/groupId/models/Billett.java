package org.groupId.models;
import java.io.Serializable;
import java.util.ArrayList;

public class Billett implements Serializable {



    // DATAFELT
    private Arrangement arrangement;
    private String telefonNummer;
    private int antall;

    private ArrayList<Billett> billetter = new ArrayList<>();
    private String arrangementNavn;
    private String sete;





    // KONSTRUKTØR
    public Billett() {
        super();
    }

    public Billett(Arrangement arrangement,String telefonNummer, int antall) {
        super();
        this.arrangement = arrangement;
        this.telefonNummer = telefonNummer;
        this.arrangementNavn = arrangement.getNavn();
        this.antall = antall;
        this.sete = antall + " stk";

        arrangement.leggTilSalg(antall);
    }

    // METODER
    public void lagBillett(Arrangement arrangement, String telefonNummer, int antall){
        billetter.add(new Billett(arrangement,telefonNummer,antall));
    }

    public void fjernBillett(Billett b){
        b.getArrangement().fjernSalg(b.getAntall());
        billetter.remove(b);
    }

    public ArrayList<Billett> getBilletter() {
        return billetter;
    }

    public String getArrangementNavn() {
        return arrangementNavn;
    }

    public String getSete() {
        return sete;
    }

    public int getAntall() {
        return antall;
    }

    public int getPlassNummer() {
        return antall;
    }

    public String getTelefonNummer() {
        return telefonNummer;
    }

    public Arrangement getArrangement() {
        return arrangement;
    }

    public void clear(){
        billetter.clear();
    }
}
