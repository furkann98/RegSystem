package org.groupId.models.exceptions;

import java.util.regex.Pattern;

import javafx.scene.control.*;
import org.groupId.controllers.MainController;
import org.groupId.models.Lokale;
import org.groupId.models.exceptions.*;

public class Feilhaandtering {

    //DATAFELT
    private Pattern bokstavRegex = Pattern.compile("[a-zæøåA-ZÆØÅ ]{1,100}");
    private Pattern tallRegex = Pattern.compile("[0-9]{1,10}");
    private Alert errorAlert;


    //KONTROLL
    public String KunBokstaver(TextField input) {
        try {
            if(!bokstavRegex.matcher(input.getText()).matches()){
                throw new KunTekstException("Du må fylle " + input.getId() + " med bare bokstaver");
            }else{
                textFieldFarge(input, "#FFFFFF");
                return "";
            }

        }catch (KunTekstException e){
            exceptionCatch(input,e.getMessage());
            return e.getMessage() + "\n\n";
        }
    }

    public String KunTall(TextField input) {
        try {
            if(!tallRegex.matcher(input.getText()).matches()){
                throw new KunTallException("Du må fylle " + input.getId() + " med bare tall");
            }else{
                textFieldFarge(input, "#FFFFFF");
                return "";
            }

        }catch (KunTallException e){
            exceptionCatch(input,e.getMessage());
            return e.getMessage() + "\n\n";

        }
    }

    public String KunBokstaverTextArea(TextArea input) {
        try {
            if(!bokstavRegex.matcher(input.getText()).matches()){
                throw new KunTekstException("Du må fylle " + input.getId() + " med bare bokstaver");
            }else{
                input.setStyle("-fx-background-color: #FFFFFF");
                return "";
            }

        }catch (KunTekstException e){
            System.out.print("CATCH: fant feil  --  ");
            //input.setStyle("-fx-background-color: #B4391F");
            //feilMelding(e.getMessage());
            input.setStyle("-fx-background-color: #E74725");
            return e.getMessage() + "\n\n";
        }
    }

    public String IngenObjektValgt(ComboBox input){
        try{
            if(input.getSelectionModel().selectedItemProperty().isNull().get()){
                throw new IngenObjectException("Du må Velge et objekt i: " + input.getId());
            } else{
                input.setStyle("-fx-background-color: #FFFFFF");
                return "";
            }


        }catch (IngenObjectException e){
            //input.setStyle("-fx-background-color: #B4391F");
            //feilMelding(e.getMessage());
            input.setStyle("-fx-background-color: #E74725");
            return e.getMessage() + "\n\n";
        }
    }

    public String IngenDatoValgt(DatePicker input){
        try{
            if(input.getValue() == null){
                throw new IngenObjectException("Du må Velge en dato i " + input.getId());
            } else{
                input.setStyle("-fx-background-color: #FFFFFF");
                return "";
            }


        }catch (IngenObjectException e){
            //input.setStyle("-fx-background-color: #B4391F");
            //feilMelding(e.getMessage());
            input.setStyle("-fx-background-color: #E74725");
            return e.getMessage() + "\n\n";
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
        //textFieldFarge(input, "#B4391F");
        //feilMelding(feilmelding);
        textFieldFarge(input, "#E74725");
    }

    public void feilMelding(String melding){
        errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Feilmelding!");
        errorAlert.setContentText(melding);
        errorAlert.showAndWait();
    }
}
