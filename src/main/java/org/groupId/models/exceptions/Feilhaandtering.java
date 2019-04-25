package org.groupId.models.exceptions;

import java.util.regex.Pattern;

import javafx.scene.control.*;
import org.groupId.controllers.MainController;
import org.groupId.models.Arrangement;
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
            exceptionCatch(input);
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
            exceptionCatch(input);
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
            input.setStyle("-fx-background-color: #E74725");
            return e.getMessage() + "\n\n";
        }
    }

    public String AntallBillett(TextField input, TextField inputMax){
        try {
            System.out.println(input.getText());
            System.out.println(inputMax.getText());

            if((Integer.valueOf(input.getText()) < 0) || (Integer.valueOf(input.getText()) > Integer.valueOf(inputMax.getText()))){
                throw new AntallBillettException("Antall billetter må være i intervallet mellom: [0 , " + inputMax.getText() + "]");
            }else{
                textFieldFarge(input, "#FFFFFF");
                return "";
            }

        }catch (AntallBillettException e){
            System.out.println("er inne i catch");
            exceptionCatch(input);
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

    //Feilhåndetering

    public void textFieldFarge(TextField input, String farge){
        input.setStyle("-fx-background-color: " + farge);
    }

    public void exceptionCatch(TextField input){
        textFieldFarge(input, "#E74725");
    }

}
