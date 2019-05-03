package org.groupId.models.thread;

import javafx.concurrent.Task;
import org.groupId.models.Arrangement;
import org.groupId.models.Billett;
import org.groupId.models.Lokale;
import org.groupId.models.Person;
import org.groupId.models.filhaandtering.InnlastingCSV;
import org.groupId.models.filhaandtering.InnlastingJOBJ;

import java.io.File;

public class TaskJobj extends Task {

    //DATAFELT
    private Runnable runMeWhenDone;
    File file;
    InnlastingJOBJ innlastingJOBJ;
    String kilde;
    Lokale LOKALE;
    Person PERSON;
    Arrangement ARRANGEMENT;
    Billett BILLETT;


    //KONSTRUKTØR
    public TaskJobj(InnlastingJOBJ innlastingJOBJ, Runnable doneFunc, File file, String kilde, Lokale LOKALE, Person PERSON, Arrangement ARRANGEMENT, Billett BILLETT) {
        this.innlastingJOBJ = innlastingJOBJ;
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
        return null;
    }
}
