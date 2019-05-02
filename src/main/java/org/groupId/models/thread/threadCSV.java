package org.groupId.models.thread;

import javafx.concurrent.Task;
import org.groupId.models.filhaandtering.InnlastingCSV;

public class threadCSV extends Task<Void> {

    //DATAFELT
    InnlastingCSV csv = new InnlastingCSV();

    @Override
    protected Void call() throws Exception {

        return null;
    }
}
