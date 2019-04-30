package org.groupId.models;
import java.util.ArrayList;

public class Billett {



    // DATAFELT
    private Arrangement arrangement;
    private String telefonNummer;
    private int antall;

    private ArrayList<Billett> billetter = new ArrayList<>();
    private String arrangementNavn;
    private String sete;





    // KONSTRUKTØR
    public Billett() {
    }

    public Billett(Arrangement arrangement,String telefonNummer, int antall) {
        this.arrangement = arrangement;
        this.telefonNummer = telefonNummer;
        this.arrangementNavn = arrangement.getNavn();
        this.antall = antall;
        this.sete = antall + " stk";

        arrangement.leggTilSalg(antall);

        System.out.println("lager BILLETT til: " + arrangement.getNavn() + "   --  " + sete);
    }

    // METODER
    public void lagBillett(Arrangement arrangement, String telefonNummer, int antall){
        billetter.add(new Billett(arrangement,telefonNummer,antall));
        //ARRANGEMENT.leggTilSalg(antall);
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
}
