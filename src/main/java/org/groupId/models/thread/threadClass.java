package org.groupId.models.thread;

import javafx.concurrent.Task;
import org.groupId.models.Arrangement;
import org.groupId.models.Billett;
import org.groupId.models.Lokale;
import org.groupId.models.Person;
import org.groupId.models.filhaandtering.InnlastingCSV;
import org.groupId.models.filhaandtering.InnlastingJOBJ;

import java.io.File;
import java.io.IOException;

public class threadClass extends Task {
    private Runnable runCSV;
    private Runnable runJOBJ;
    private File file;
    private InnlastingCSV innlastingCSV;
    private InnlastingJOBJ innlastingJOBJ;
    private String kilde;
    private Lokale LOKALE;
    private Person PERSON;
    private Arrangement ARRANGEMENT;
    private Billett BILLETT;

    private boolean CSV = false;
    private boolean JOBJ = false;
    private boolean JOBJLoad = true;



    public threadClass(InnlastingCSV innlastingCSV, InnlastingJOBJ innlastingJOBJ, Runnable runCsv, Runnable runJobj, File file, String kilde, Lokale LOKALE, Person PERSON, Arrangement ARRANGEMENT, Billett BILLETT) {
        this.innlastingCSV = innlastingCSV;
        this.innlastingJOBJ = innlastingJOBJ;
        this.runCSV = runCsv;
        this.runJOBJ = runJobj;
        this.file = file;
        this.kilde = kilde;
        this.LOKALE = LOKALE;
        this.PERSON = PERSON;
        this.ARRANGEMENT = ARRANGEMENT;
        this.BILLETT = BILLETT;
    }

    @Override
    protected Void call() {
        try {
            String filnavn = file.getPath(); //Valgt filechooser path

            if (filnavn.endsWith(".csv")) {
                    try {
                    CSV = true;
                    innlastingCSV.InnLasting(kilde, LOKALE, PERSON, ARRANGEMENT, BILLETT);

                } catch (IOException e) {
                }

            } else if (filnavn.endsWith(".jobj")) {
                try {
                    JOBJ = true;
                    innlastingJOBJ.InnLasting(kilde, LOKALE,PERSON,ARRANGEMENT,BILLETT);
                    JOBJLoad = innlastingJOBJ.returnJOBJLoad();
                } catch (IOException e) {
                }
            }
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    protected void succeeded() {

        if(CSV){
            runCSV.run();
            innlastingCSV.feilMelding();
        }
        if(JOBJ){
            innlastingJOBJ.feilMelding();
            if(JOBJLoad){
                runJOBJ.run();
            }
        }


    }
}
