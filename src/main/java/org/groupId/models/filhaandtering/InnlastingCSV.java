package org.groupId.models.filhaandtering;

import javafx.scene.control.Alert;
import org.groupId.models.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class InnlastingCSV implements Innlasting {

    //Datafelt

    private BufferedReader br;
    private String feltGrense = ";";
    private Feilhaandtering feilhaandtering = new Feilhaandtering();
    private Alert errorAlert;
    public String mld = "Filen er lastet inn!";

    public int feilLokaleAnt = 0;
    public int feilPersonAnt = 0;
    public int feilArrangementAnt = 0;
    public int feilBillettAnt = 0;


    ArrayList<Lokale> lokaler = new ArrayList<>();
    ArrayList<Person> personer = new ArrayList<>();
    ArrayList<Arrangement> arrangementer = new ArrayList<>();
    ArrayList<Billett> billetter = new ArrayList<>();

    //Konstruktør

    public InnlastingCSV(){ }


    @Override
    public void InnLasting(String kilde, Lokale lokale, Person person, Arrangement arrangement, Billett billett) throws IOException, FileNotFoundException {
        Boolean lokaleBoolean = false;
        Boolean personBoolean = false;
        Boolean arrangementBoolean = false;
        Boolean billettBoolean = false;

        lokaler.clear();
        personer.clear();
        arrangementer.clear();
        billetter.clear();


        try {
            br = new BufferedReader(new FileReader(kilde));
            String linje;
            while ((linje = br.readLine()) != null){

                //Finner riktig avdelig med data
                if(linje.matches("LOKALE")){
                    lokaleBoolean = true;
                    continue;
                }

                if(linje.matches("PERSONER")){
                    lokaleBoolean = false;
                    personBoolean = true;
                    continue;
                }
                if(linje.matches("ARRANGEMENT")){
                    personBoolean = false;
                    arrangementBoolean = true;
                    continue;
                }
                if(linje.matches("BILLETT")){
                    arrangementBoolean = false;
                    billettBoolean = true;
                    continue;
                }


                String[] innlesing = linje.split(feltGrense);


                if(lokaleBoolean){
                    if(feilhaandtering.lokalLoadSjekk(innlesing)){
                        lokale = new Lokale(innlesing[0],innlesing[1],Integer.valueOf(innlesing[2]));
                        lokaler.add(lokale);
                    }else{
                        System.out.println("feil i Lokale");
                        feilLokaleAnt++;
                    }
                }

                if(personBoolean){
                    if(feilhaandtering.personLoadSjekk(innlesing)){
                        person = new Person(innlesing[0],innlesing[1],innlesing[2],innlesing[3],innlesing[4]);
                        personer.add(person);
                    }else{
                        System.out.println("fant feil i Person");
                        feilPersonAnt++;
                    }
                }


                if(arrangementBoolean){
                    if(feilhaandtering.arrangmentLoadSjekk(innlesing,personer.size(),lokaler.size(),lokaler)){
                        Person p = personer.get(Integer.valueOf(innlesing[0]));
                        Lokale l = lokaler.get(Integer.valueOf(innlesing[1]));
                        String navn = innlesing[2];
                        String artist = innlesing[3];
                        String program = innlesing[4];
                        LocalDate tidspunkt = LocalDate.parse(innlesing[5]);
                        int bPris = Integer.valueOf(innlesing[6]);
                        int bSalg = Integer.valueOf(innlesing[7]);

                        Arrangement a = new Arrangement(p,l,navn,artist,program,tidspunkt,bPris,bSalg);
                        arrangementer.add(a);
                    }else{
                        System.out.println("feil i Arrangement");
                        feilArrangementAnt++;
                    }

                }

                if(billettBoolean){
                    if(feilhaandtering.billettLoadSjekk(innlesing,arrangementer)){
                        if(innlesing[0].matches("-1")){
                            break;
                        }else{
                            Arrangement a = arrangementer.get(Integer.valueOf(innlesing[0]));
                            String tlf = innlesing[1];
                            int antall = Integer.valueOf(innlesing[2]);

                            billett = new Billett(a,tlf,antall);
                            billetter.add(billett);
                        }
                    }else {
                        System.out.println("feil i Billett");
                        feilBillettAnt++;
                    }
                }

                if(feilArrangementAnt+feilBillettAnt+feilLokaleAnt+feilPersonAnt != 0){
                    this.mld = "Det ble fjernet noen objekter under innlastning, med feil format. " +
                            "Alle objekter går gjennom en feilhåndteringsmetode som fanger og fjerner disse fra registreringsystemet." +
                            " \n\n Antallet er listet opp under: " +
                            "\n Lokaler: " + feilLokaleAnt + " feil" +
                            "\n Person: " + feilPersonAnt + " feil" +
                            "\n Arrangment: " + feilArrangementAnt + " feil" +
                            "\n Billett: " + feilBillettAnt + " feil";
                }
            }


        }catch (FileNotFoundException e){
            System.out.println("FileNotFoundException");
        }catch (IOException e){
            System.out.println("IOException");
        }
    }


    //Metoder

    public ArrayList<Lokale> getLokaler() {
        return lokaler;
    }

    public ArrayList<Person> getPersoner() {
        return personer;
    }

    public ArrayList<Arrangement> getArrangementer() {
        return arrangementer;
    }

    public ArrayList<Billett> getBilletter() {
        return billetter;
    }

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
        feilArrangementAnt = feilLokaleAnt = feilPersonAnt = feilBillettAnt = 0;
    }



}
