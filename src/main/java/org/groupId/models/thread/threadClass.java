package org.groupId.models.thread;

import org.groupId.controllers.MainController;

import javafx.concurrent.Task;
import javafx.stage.FileChooser;
import org.groupId.controllers.MainController;
import org.groupId.models.Arrangement;
import org.groupId.models.Billett;
import org.groupId.models.Lokale;
import org.groupId.models.Person;
import org.groupId.models.filhaandtering.Innlasting;
import org.groupId.models.filhaandtering.InnlastingCSV;

import java.io.File;
import java.io.IOException;
import java.util.spi.AbstractResourceBundleProvider;

public class threadClass extends Task {
    private Runnable runMeWhenDone;
    File file;
    InnlastingCSV innlastingCSV;
    String kilde;
    Lokale LOKALE;
    Person PERSON;
    Arrangement ARRANGEMENT;
    Billett BILLETT;


    public threadClass(InnlastingCSV innlastingCSV, Runnable doneFunc, File file, String kilde, Lokale LOKALE, Person PERSON, Arrangement ARRANGEMENT, Billett BILLETT) {
        this.innlastingCSV = innlastingCSV;
        this.runMeWhenDone = doneFunc;
        this.file = file;
        this.kilde = kilde;
        this.LOKALE = LOKALE;
        this.PERSON = PERSON;
        this.ARRANGEMENT = ARRANGEMENT;
        this.BILLETT = BILLETT;
    }

    @Override
    protected Void call() throws Exception {
        // emulerer en "stor" jobb på 3 sekunder...
        try {
            System.out.println("inne i TRY");
            //Thread.sleep(3000);
            String filnavn = file.getPath(); //Valgt filechooser path

            if (filnavn.endsWith(".csv")) {
                System.out.println("Inene i IF");
                try {
                    System.out.println("HEIHEI");
                    innlastingCSV.InnLasting(kilde, LOKALE, PERSON, ARRANGEMENT, BILLETT);
                    System.out.println("HEI");
                } catch (IOException e) {
                    System.out.println("hade");
                    //e.printStackTrace();
                }

            } else if (filnavn.endsWith(".jobj")) {
                try {
                    innlastingCSV.InnLasting(kilde, LOKALE,PERSON,ARRANGEMENT,BILLETT);
                } catch (IOException e) {
                    //e.printStackTrace();
                }
                //	save.jobjLagring("src/lagring.csv", LOKALE, PERSON, ARRANGEMENT, BILLETT);
                //System.out.println("ikke implementert");

            }



        } catch (Exception e) {
            // gjør ikke noe her, men hvis hvis du er i en løkke
            // burde løkken stoppes med break hvis isCancelled() er true...
        }
        System.out.println("thread ferdig");


        return null;
    }



    // succeeded kjører i main-UI tråden, og UI elementer kan manipuleres her
    @Override
    protected void succeeded() {
        runMeWhenDone.run();
    }
}
