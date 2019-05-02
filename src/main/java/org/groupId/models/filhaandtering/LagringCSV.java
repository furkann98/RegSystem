package org.groupId.models.filhaandtering;

import org.groupId.models.Arrangement;
import org.groupId.models.Billett;
import org.groupId.models.Lokale;
import org.groupId.models.Person;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class LagringCSV implements Lagring {

    //DATAFELT
    private PrintWriter skriver;

    //KONSTRUKTØR
    public LagringCSV(){

    }

    @Override
    public void Lagring(String kilde, Lokale lokale, Person person, Arrangement arrangement, Billett billett) throws IOException {
        try {
            skriver = new PrintWriter(new FileWriter(kilde));

            //Lokale
            skriver.append("LOKALE");
            for (Lokale l : lokale.getLokaler()) {
                skriverLokale(skriver,l);
            }


            //Personer
            skriver.append("\nPERSONER");
            for (Person p : person.getPersoner()) {
                skriverPerson(skriver,p);
            }


            //Arrangement
            skriver.append("\nARRANGEMENT");
            for (Arrangement a : arrangement.getArrangementer()) {
                skriverArrangement(skriver,a, person, lokale);
            }


            //Billett
            skriver.append("\nBILLETT");
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
