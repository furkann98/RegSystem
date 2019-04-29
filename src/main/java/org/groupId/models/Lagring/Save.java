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
            //Lokale
            skriver = new PrintWriter(new FileWriter(kilde));
            skriver.append("NAVN;TYPE;ANTALL;");

            for (Lokale l : lokale.getArrayList()) {
                skriverLokale(skriver,l);
            }
            
            //Personer
            skriver.append(";NAVN;TLF;EPOST;NETTSIDE;OPPLYSNINGER;");

            for (Person p : person.getPersoner()) {
                skriverPerson(skriver,p);
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










}
