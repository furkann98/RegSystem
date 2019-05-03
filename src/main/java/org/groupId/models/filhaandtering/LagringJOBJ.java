package org.groupId.models.filhaandtering;

import org.groupId.models.Arrangement;
import org.groupId.models.Billett;
import org.groupId.models.Lokale;
import org.groupId.models.Person;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LagringJOBJ implements Lagring {

    //DATAFELT
    OutputStream os;

    List<Object> liste = new ArrayList<>();


    public LagringJOBJ(){}

    @Override
    public void Lagring(String kilde, Lokale lokale, Person person, Arrangement arrangement, Billett billett) throws IOException {
        //Path path = Paths.get(kilde);
        //os = Files.newOutputStream(path);

        /*
        liste.add(lokale.getLokaler());
        liste.add(person.getPersoner());
        liste.add(arrangement.getArrangementer());
        liste.add(billett.getBilletter());
        */

        for (Lokale l: lokale.getLokaler()) {
            liste.add(l);
        }

        for (Person l: person.getPersoner()) {
            liste.add(l);
        }

        for (Arrangement l: arrangement.getArrangementer()) {
            liste.add(l);
        }

        for (Billett l: billett.getBilletter()) {
            liste.add(l);
        }

        try{
            FileOutputStream fos = new FileOutputStream(kilde);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(liste);
            oos.close();
            fos.close();
        }catch (IOException e){
            System.out.println(e.getMessage());

        }
    }




}
