package org.groupId.models;

import java.util.ArrayList;
import java.util.regex.Pattern;

import javafx.scene.control.*;
import org.groupId.controllers.MainController;
import org.groupId.models.Arrangement;
import org.groupId.models.Lokale;
import org.groupId.models.exceptions.*;

public class Feilhaandtering {

    //DATAFELT
    private Pattern navnRegex = Pattern.compile("[a-zæøåA-ZÆØÅ]{1,30}");
    private Pattern bokstavRegex = Pattern.compile("[a-zæøåA-ZÆØÅ]{1,100}");
    private Pattern tekstRegex = Pattern.compile("^[a-zA-Z0-9_.-]*${1,100}");
    private Pattern tallRegex = Pattern.compile("[0-9]{1,10}");
    private Pattern tlfRegex = Pattern.compile("[0-9]{8}");
    private Pattern ePostRegex = Pattern.compile("[^(.+)@(.+)$]{1,50}");
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

    public String KunTekstTextArea(TextArea input) {
        try {
            if(!tekstRegex.matcher(input.getText()).matches()){
                throw new KunTekstException("Du må fylle " + input.getId() + " med bare tekst og ikke tegn.");
            }else{
                input.setStyle("-fx-background-color: #FFFFFF");
                return "";
            }

        }catch (KunTekstException e){
            //input.setStyle("-fx-background-color: #B4391F");
            //feilMelding(e.getMessage());
            input.setStyle("-fx-background-color: #E74725");
            return e.getMessage() + "\n\n";
        }
    }

    public String kunTlf(TextField input) {
        try {
            if(!tlfRegex.matcher(input.getText()).matches()){
                throw new TelefonNummerException("Du må fylle " + input.getId() + " med et telefonnummer.");
            }else{
                textFieldFarge(input, "#FFFFFF");
                return "";
            }

        }catch (TelefonNummerException e){
            exceptionCatch(input);
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
            if((Integer.valueOf(input.getText()) < 0) || (Integer.valueOf(input.getText()) > Integer.valueOf(inputMax.getText()))){
                throw new AntallBillettException("Antall forhåndssolgte billetter må være i intervallet mellom: [0 , " + inputMax.getText() + "]");
            }else{
                textFieldFarge(input, "#FFFFFF");
                return "";
            }

        }catch (AntallBillettException e){
            exceptionCatch(input);
            return e.getMessage() + "\n\n";

        }
    }

/*
    public boolean navnRegex(String tekst){
        boolean godkjent = false;

        if(navnRegex.matcher(tekst).matches()){
            godkjent = true;
        }

        return godkjent;
    }
*/

    //Feilhåndtering ved loading
    public boolean lokalLoadSjekk(String[] liste){
        boolean godkjent = true;

        if(liste.length != 3){
            godkjent = false;
        }else if(!navnRegex.matcher(liste[0]).matches()){
            godkjent =  false;
        }else if(!navnRegex.matcher(liste[1]).matches()){
            godkjent = false;
        }else if(!tallRegex.matcher(liste[2]).matches()){
            godkjent = false;
        }

        return godkjent;
    }

    public boolean personLoadSjekk(String[] liste){
        boolean godkjent = true;
        /*

        if(liste.length != 5){
            godkjent = false;
            System.out.println("FEIL: 1");
        }else if(!navnRegex.matcher(liste[0]).matches()){
            godkjent =  false;
            System.out.println("FEIL: 2");
        }else if(!tlfRegex.matcher(liste[1]).matches()){
            godkjent = false;
            System.out.println("FEIL: 3");
        }

        //Epost og nettisde og opplysninger

         */
        return godkjent;
    }

    public boolean arrangmentLoadSjekk(String[] liste, int lengdePerson, int lengdeLokaler, ArrayList<Lokale> lokaler){

        boolean godkjent = true;
        /*
        if(liste.length != 8){
            godkjent = false;
            System.out.println("FEIL: 1");
        }else if(Integer.valueOf(liste[0]) >= lengdePerson){
            godkjent =  false;
            System.out.println("FEIL: 2");
        }else if(Integer.valueOf(liste[1]) >= lengdeLokaler){
            godkjent = false;
            System.out.println("FEIL: 3");
        }else if(!navnRegex.matcher(liste[2]).matches()){
            godkjent =  false;
            System.out.println("FEIL: 4");
        }else if(!navnRegex.matcher(liste[3]).matches()){
            godkjent =  false;
            System.out.println("FEIL: 5");
        }else if(Integer.valueOf(liste[7]) > lokaler.get(Integer.valueOf(liste[1])).getAntallPlasser()){
            godkjent =  false;
            System.out.println("FEIL: 6");
        }

        //Epost og nettisde og opplysninger

        */
        return godkjent;
    }


    //Feilhåndetering

    public void textFieldFarge(TextField input, String farge){
        input.setStyle("-fx-background-color: " + farge);
    }

    public void exceptionCatch(TextField input){
        textFieldFarge(input, "#E74725");
    }

}
