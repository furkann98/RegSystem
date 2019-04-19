package org.groupId.models.exceptions;

import java.util.regex.Pattern;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.groupId.controllers.MainController;
import org.groupId.models.exceptions.*;

public class Feilhaandtering {

    //DATAFELT
    private Pattern bokstavRegex = Pattern.compile("[a-zæøåA-ZÆØÅ]{1,25}+");
    private Alert errorAlert;


    public void KunBokstaver(TextField input) {
        System.out.println("sjekker  -  " + input.getId());
        input.focusedProperty().addListener((arg0,nyV,gammelV) -> {
            if(!gammelV){
                try {
                    System.out.println("TRY: prøver");
                    if(!bokstavRegex.matcher(input.getText()).matches()){
                        throw new KunTallException("Du kan kun skrive tall og ingen, bokstaver");
                    }

                }catch (KunTallException e){
                    System.out.println("CATCH: fant feil");
                    System.out.println(e.getMessage());
                }
            }else {
                System.out.println("ELSE: ingen feil");
            }
        });



    }
}
