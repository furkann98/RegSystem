package org.groupId.models.Lagring;

import org.groupId.models.Arrangement;
import org.groupId.models.Billett;
import org.groupId.models.Lokale;
import org.groupId.models.Person;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Save implements Lagring {

    //DATAFELT
    private PrintWriter skriver; // = new PrintWriter(new FileWriter(),);
    private File fil;

    private Lokale lokale = new Lokale();
    private ArrayList<Lokale> lokaleListe;


    //KONSTRUKTØR
    public Save(){

    }

    @Override
    public void csvLagring(String kilde, Lokale lokale, Person person, Arrangement arrangement, Billett billett) throws IOException {
        try {
            skriver = new PrintWriter(new FileWriter(kilde));

            //Lokale
            skriver.append("LOKALE");
            //skriver.append("NAVN;TYPE;ANTALL;");

            for (Lokale l : lokale.getLokaler()) {
                skriverLokale(skriver,l);
            }


            //Personer
            skriver.append("\nPERSONER");
            //skriver.append("NAVN;TLF;EPOST;NETTSIDE;OPPLYSNINGER;");

            for (Person p : person.getPersoner()) {
                skriverPerson(skriver,p);
            }
            //Person kontaktPerson, Lokale lokale, String navn, String artist, String program, LocalDate tidspunkt, int billettPris, int billettSalg


            //Arrangement
            skriver.append("\nARRANGEMENT");
            //skriver.append("PERSON;LOKALE;NAVN;ARTIST;PROGRAM;TIDSPUNKT;BILLETTPRIS;BILLETTSALG;");

            for (Arrangement a : arrangement.getArrangementer()) {
                skriverArrangement(skriver,a, person, lokale);
            }


            //Billett
            skriver.append("\nBILLETT");
            //skriver.append("ARRANGEMENT;TELEFONNUMMER;ANTALL;");

            for (Billett b : billett.getBilletter()) {
                skriverBillett(skriver,b, arrangement);
            }




        } catch (IOException e){
            System.out.println("Fant feil");

        } finally {
            skriver.flush();
            skriver.close();
        }

    }

    @Override
    public void jobjLagring(String kilde) throws IOException {

    }

    //METODER

    public void skriverLokale(PrintWriter skriver, Lokale lokale){
        skriver.append("\n");
        skriver.append(lokale.getNavn()).append(";");
        skriver.append(lokale.getType()).append(";");
        skriver.append(String.valueOf(lokale.getAntallPlasser()));
    }

    public void skriverPerson(PrintWriter skriver, Person person){
        skriver.append("\n");
        skriver.append(person.getNavn()).append(";");
        skriver.append(person.getTlfNummer()).append(";");
        skriver.append(person.getEpost()).append(";");
        skriver.append(person.getNettside()).append(";");
        skriver.append(person.getOpplysninger()).append(";");
    }

    public void skriverArrangement(PrintWriter skriver, Arrangement arrangement, Person person, Lokale lokale){
        skriver.append("\n");
        skriver.append(String.valueOf(person.getPersoner().indexOf(arrangement.getKontaktPerson()))).append(";");
        skriver.append(String.valueOf(lokale.getLokaler().indexOf(arrangement.getLokale()))).append(";");
        skriver.append(arrangement.getNavn()).append(";");
        skriver.append(arrangement.getArtist()).append(";");
        skriver.append(arrangement.getProgram()).append(";");
        skriver.append(String.valueOf(arrangement.getTidspunkt())).append(";");
        skriver.append(String.valueOf(arrangement.getBillettPris())).append(";");
        skriver.append(String.valueOf(arrangement.getBillettSalg())).append(";");



    }

    public void skriverBillett(PrintWriter skriver, Billett billett, Arrangement arrangement){
        skriver.append("\n");
        skriver.append(String.valueOf(arrangement.getArrangementer().indexOf(billett.getArrangement()))).append(";");
        skriver.append(billett.getTelefonNummer()).append(";");
        skriver.append(String.valueOf(billett.getAntall())).append(";");
    }













}
