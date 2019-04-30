package org.groupId.models.Lagring;

import org.groupId.models.Arrangement;
import org.groupId.models.Billett;
import org.groupId.models.Lokale;
import org.groupId.models.Person;

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


    ArrayList<Lokale> testLokale = new ArrayList<>();
    ArrayList<Person> testPersoner = new ArrayList<>();
    ArrayList<Arrangement> testArrangement = new ArrayList<>();
    ArrayList<Billett> testBillett = new ArrayList<>();

    //Konstruktør

    public Load(){ }



    @Override
    public void csvOpplasting(String kilde, Lokale lokale, Person person, Arrangement arrangement, Billett billett) throws IOException, FileNotFoundException {
        Boolean lokaleBoolean = false;
        Boolean personBoolean = false;
        Boolean arrangementBoolean = false;
        Boolean billettBoolean = false;


        try {
            br = new BufferedReader(new FileReader(kilde));
            System.out.println("INNE I TRY");

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
                    Lokale ny = new Lokale(innlesing[0],innlesing[1],Integer.valueOf(innlesing[2]));
                    System.out.println(ny.getNavn() + "    " + ny.getType());
                    testLokale.add(ny);

                }

                if(personBoolean){
                    Person ny = new Person(innlesing[0],innlesing[1],innlesing[2],innlesing[3],innlesing[4]);
                    testPersoner.add(ny);
                }


                if(arrangementBoolean){
                    Person p = testPersoner.get(Integer.valueOf(innlesing[0]));
                    Lokale l = testLokale.get(Integer.valueOf(innlesing[1]));
                    String navn = innlesing[2];
                    String artist = innlesing[3];
                    String program = innlesing[4];
                    LocalDate tidspunkt = LocalDate.parse(innlesing[5]);
                    int bPris = Integer.valueOf(innlesing[6]);
                    int bSalg = Integer.valueOf(innlesing[7]);

                    Arrangement ny = new Arrangement(p,l,navn,artist,program,tidspunkt,bPris,bSalg);
                    testArrangement.add(ny);
                }

                if(billettBoolean){

                    if(innlesing[0].matches("-1")){break;}

                    Arrangement a = testArrangement.get(Integer.valueOf(innlesing[0]));
                    String tlf = innlesing[1];
                    int antall = Integer.valueOf(innlesing[2]);

                    Billett ny = new Billett(a,tlf,antall);
                    testBillett.add(ny);
                }
            }

            System.out.println("Lokaler : " + testLokale.size());
            System.out.println("Personer : " + testPersoner.size());
            System.out.println("Arrangementer : " + testArrangement.size());
            System.out.println("Billetter : " + testBillett.size());

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

}
