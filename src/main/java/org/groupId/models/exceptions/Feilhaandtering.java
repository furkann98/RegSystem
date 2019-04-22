package org.groupId.models.exceptions;

import java.util.regex.Pattern;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.groupId.controllers.MainController;
import org.groupId.models.exceptions.*;

public class Feilhaandtering {

    //DATAFELT
    private Pattern bokstavRegex = Pattern.compile("[a-zæøåA-ZÆØÅ ]{1,100}");
    private Pattern tallRegex = Pattern.compile("[0-9]{1,10}");
    private Alert errorAlert;


    //KONTROLL
    public boolean KunBokstaver(TextField input) {
        System.out.println("sjekker  -  " + input.getId());

        try {
            System.out.println("TRY: prøver");
            if(!bokstavRegex.matcher(input.getText()).matches()){
                throw new KunTekstException("Du må fylle " + input.getId() + " med bare bokstaver");
            }else{
                System.out.println("Riktig REGEX");
                textFieldFarge(input, "#FFFFFF");
                return true;
            }

        }catch (KunTekstException e){
            exceptionCatch(input,e.getMessage());
            return false;
        }
    }

    public boolean KunTall(TextField input) {
        System.out.println("sjekker  -  " + input.getId());

        try {
            System.out.println("TRY: prøver");
            if(!tallRegex.matcher(input.getText()).matches()){
                throw new KunTallException("Du må fylle " + input.getId() + " med bare tall");
            }else{
                textFieldFarge(input, "#FFFFFF");
                return true;
            }

        }catch (KunTallException e){
            exceptionCatch(input,e.getMessage());
            return false;

        }
    }

    public boolean KunBokstaverTextArea(TextArea input) {
        System.out.println("sjekker  -  " + input.getId());

        try {
            System.out.println("TRY: prøver");
            if(!bokstavRegex.matcher(input.getText()).matches()){
                throw new KunTekstException("Du må fylle " + input.getId() + " med bare bokstaver");
            }else{
                System.out.println("Riktig REGEX");
                input.setStyle("-fx-background-color: #FFFFFF");
                return true;
            }

        }catch (KunTekstException e){
            System.out.print("CATCH: fant feil  --  ");
            input.setStyle("-fx-background-color: #B4391F");
            feilMelding(e.getMessage());
            input.setStyle("-fx-background-color: #E74725");
            return false;
        }
    }

    //LISTENER
    public void ListenerKunBokstaver(TextField input){
        input.focusedProperty().addListener((arg0,gammelV,nyV) -> {
            if(!nyV){
                KunBokstaver(input);
            }
        });
    }

    public void ListenerKunTall(TextField input){
        input.focusedProperty().addListener((arg0,gammelV,nyV) -> {
            if(!nyV){
                KunTall(input);
            }
        });
    }
    public void ListenerKunBokstaverTextArea(TextArea input){
        input.focusedProperty().addListener((arg0,gammelV,nyV) -> {
            if(!nyV){
                KunBokstaverTextArea(input);
            }
        });
    }


    //Feilhåndetering
    public void textFieldFarge(TextField input, String farge){
        input.setStyle("-fx-background-color: " + farge);
    }

    public void exceptionCatch(TextField input, String feilmelding){
        System.out.print("CATCH: fant feil  --  ");
        textFieldFarge(input, "#B4391F");
        feilMelding(feilmelding);
        textFieldFarge(input, "#E74725");
    }

    public void feilMelding(String melding){
        errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Feilmelding!");
        errorAlert.setContentText(melding);
        errorAlert.showAndWait();
    }
}
