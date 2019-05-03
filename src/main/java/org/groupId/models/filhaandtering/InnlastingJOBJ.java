package org.groupId.models.filhaandtering;

import javafx.scene.control.Alert;
import org.groupId.models.Arrangement;
import org.groupId.models.Billett;
import org.groupId.models.Lokale;
import org.groupId.models.Person;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

public class InnlastingJOBJ implements Innlasting {

    //DATAFELT
    ArrayList<Lokale> listeLokale = new ArrayList<>();
    ArrayList<Person> listePerson = new ArrayList<>();
    ArrayList<Arrangement> listeArrangement = new ArrayList<>();
    ArrayList<Billett> listeBillett = new ArrayList<>();

    private Alert errorAlert;
    public String mld = "Filen er lastet inn!";
    private boolean JOBJLoad = true;


    List<Object> liste;
    ObjectInputStream ois;
    FileInputStream fis;

    public InnlastingJOBJ(){

    }

    @Override
    public void InnLasting(String kilde, Lokale lokale, Person person, Arrangement arrangement, Billett billett) throws IOException {
        listeLokale.clear();
        listePerson.clear();
        listeArrangement.clear();
        listeBillett.clear();

        try
        {

            try {
                fis = new FileInputStream(kilde);
                ois = new ObjectInputStream(fis);

            }catch (StreamCorruptedException s){
                mld = "Filen kan ikke innlastes, og inneholder feil. Venligst velg en ny fil som støtter java serialisering.";
                JOBJLoad = false;

            }

            liste = (ArrayList<Object>) ois.readObject();

            for (Object obj : liste) {
                if (obj.getClass() == Lokale.class) {
                    Lokale l = (Lokale) obj;
                    listeLokale.add(l);
                }
                if (obj.getClass() == Person.class) {
                    Person p = (Person) obj;
                    listePerson.add(p);
                }
                if (obj.getClass() == Arrangement.class) {
                    Arrangement a = (Arrangement) obj;
                    listeArrangement.add(a);
                }
                if (obj.getClass() == Billett.class) {
                    Billett b = (Billett) obj;
                    listeBillett.add(b);
                }
            }

            ois.close();
            fis.close();
        }
        catch (IOException ioe)
        {
            mld = "Filen kan ikke innlastes, og inneholder feil. Venligst velg en ny fil som støtter java serialisering.";
            JOBJLoad = false;
            return;
        }
        catch (ClassNotFoundException c)
        {
            mld = "Filen kan ikke innlastes, og inneholder feil. Venligst velg en ny fil som støtter java serialisering.";
            JOBJLoad = false;
            return;
        }



        //Verify list data

    }


    //METODER

    public void feilMelding(){
        //SE OM MAN KAN FORANDRE STØRELSE OG FIKSE LAYOUT
        errorAlert = new Alert(Alert.AlertType.INFORMATION);
        if(this.mld.matches("Filen er lastet inn!")){
            errorAlert.setHeaderText("Innlasting!");
        }else{
            errorAlert.setHeaderText("Feilmelding!");
        }
        errorAlert.setContentText(this.mld);
        errorAlert.showAndWait();
        this.mld = "Filen er lastet inn!";
    }

    public Boolean returnJOBJLoad(){
        return JOBJLoad;
    }

    public ArrayList<Lokale> getLokaler() {
        return listeLokale;
    }

    public ArrayList<Person> getPersoner() {
        return listePerson;
    }

    public ArrayList<Arrangement> getArrangementer() {
        return listeArrangement;
    }

    public ArrayList<Billett> getBilletter() {
        return listeBillett;
    }
}
