package org.groupId.models.Lagring;

import org.groupId.models.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Load implements Opplasting {

    //Datafelt

    private BufferedReader br;
    private String feltGrense = ";";
    private Feilhaandtering feilhaandtering = new Feilhaandtering();


    ArrayList<Lokale> lokaler = new ArrayList<>();
    ArrayList<Person> personer = new ArrayList<>();
    ArrayList<Arrangement> arrangementer = new ArrayList<>();
    ArrayList<Billett> billetter = new ArrayList<>();

    //Konstruktør

    public Load(){ }


    @Override
    public void csvOpplasting(String kilde, Lokale lokale, Person person, Arrangement arrangement, Billett billett) throws IOException, FileNotFoundException {
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
                    }



                }

                if(personBoolean){
                    if(feilhaandtering.personLoadSjekk(innlesing)){
                        person = new Person(innlesing[0],innlesing[1],innlesing[2],innlesing[3],innlesing[4]);
                        personer.add(person);
                        System.out.println("la til Person");

                    }else{
                        System.out.println("fant feil i PErson");

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
                        System.out.println("feil i lokalet");
                    }

                }

                if(billettBoolean){
                    if(innlesing[0].matches("-1")){
                        break;
                    }else{
                        Arrangement a = arrangementer.get(Integer.valueOf(innlesing[0]));
                        String tlf = innlesing[1];
                        int antall = Integer.valueOf(innlesing[2]);

                        billett = new Billett(a,tlf,antall);
                        billetter.add(billett);
                    }
                }
            }
        }catch (FileNotFoundException e){
            System.out.println("FileNotFoundException");
        }catch (IOException e){
            System.out.println("IOException");
        }

    }

    @Override
    public void jobjOpplasting(String kilde) throws IOException {

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



}
