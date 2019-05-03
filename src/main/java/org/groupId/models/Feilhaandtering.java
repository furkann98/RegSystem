package org.groupId.models;

import java.util.ArrayList;
import java.util.regex.Pattern;

import javafx.scene.control.*;
import org.groupId.models.exceptions.*;

public class Feilhaandtering {

    //DATAFELT
    private Pattern navnRegex = Pattern.compile("[a-zæøåA-ZÆØÅ ]{1,30}");
    private Pattern bokstavRegex = Pattern.compile("[a-zæøåA-ZÆØÅ ]{1,100}");
    private Pattern tekstRegex = Pattern.compile("^[a-zæøåA-ZÆØÅ0-9_., ]*${0,200}");
    private Pattern tallRegex = Pattern.compile("[0-9]{1,10}");
    private Pattern tlfRegex = Pattern.compile("[0-9]{8}");
    private Pattern ePostRegex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
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

    public String kunEpost(TextField input) {
        try {
            if(!ePostRegex.matcher(input.getText()).matches()){
                throw new EpostException("Du må fylle " + input.getId() + " med en E-Post");
            }else{
                textFieldFarge(input, "#FFFFFF");
                return "";
            }

        }catch (EpostException e){
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

    public String KunTekst(TextField input) {
        try {
            if(!tekstRegex.matcher(input.getText()).matches()){
                throw new KunTekstException("Du må fylle " + input.getId() + " med bare tekst og ikke tegn.");
            }else{
                input.setStyle("-fx-background-color: #FFFFFF");
                return "";
            }

        }catch (KunTekstException e){
            input.setStyle("-fx-background-color: #E74725");
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
                throw new TelefonNummerException("Du må fylle " + input.getId() + " med et telefonnummer (8 siffer). ");
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

    public String AntallBillett(TextField input, Lokale lokale){
        try {
            if((Integer.valueOf(input.getText()) < 0) || (Integer.valueOf(input.getText()) > lokale.getAntallPlasser())){
                throw new AntallBillettException("Antall forhåndssolgte billetter må være i intervallet mellom: [0 , " + lokale.getAntallPlasser() + "]");
            }else{
                textFieldFarge(input, "#FFFFFF");
                return "";
            }

        }catch (AntallBillettException e){
            exceptionCatch(input);
            return e.getMessage() + "\n\n";

        }
    }

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

        if(liste.length != 5){
            godkjent = false;
        }else if(!bokstavRegex.matcher(liste[0]).matches()){
            godkjent =  false;
        }else if(!tlfRegex.matcher(liste[1]).matches()){
            godkjent = false;
        }else if (!ePostRegex.matcher(liste[2]).matches()){
            godkjent = false;
        }
        else if(!tekstRegex.matcher(liste[3]).matches()){
            godkjent = false;
        }else if(!tekstRegex.matcher(liste[4]).matches()){
            godkjent = false;
        }

        return godkjent;
    }

    public boolean arrangmentLoadSjekk(String[] liste, int lengdePerson, int lengdeLokaler, ArrayList<Lokale> lokaler){

        boolean godkjent = true;

        if(liste.length != 8){
            godkjent = false;
        }else if(Integer.valueOf(liste[0]) >= lengdePerson){
            godkjent =  false;
        }else if(Integer.valueOf(liste[1]) >= lengdeLokaler){
            godkjent = false;
        }else if(!bokstavRegex.matcher(liste[2]).matches()){
            godkjent =  false;
        }else if(!tekstRegex.matcher(liste[3]).matches()){
            godkjent =  false;
        }else if(!tekstRegex.matcher(liste[4]).matches()){
            godkjent =  false;
        }else if(!tallRegex.matcher(liste[6]).matches()){
            godkjent =  false;
        }else if(Integer.valueOf(liste[7]) > lokaler.get(Integer.valueOf(liste[1])).getAntallPlasser()){
            godkjent =  false;
        }

        //DATO??
        return godkjent;
    }

    public boolean billettLoadSjekk(String[] liste, ArrayList<Arrangement> arrangementer){
        boolean godkjent = true;
        if(liste.length != 3){
            System.out.println("feil i lengde");
            godkjent = false;
        }else if(liste[0].matches("-1")){
            System.out.println("feil i -1");
            godkjent =  false;
        }else if(Integer.valueOf(liste[0]) >= arrangementer.size()){
            System.out.println("feil i arr");
            godkjent =  false;
        }else if(!tlfRegex.matcher(liste[1]).matches()){
            System.out.println("feil i tlf");
            godkjent = false;
        }else if(!(tallRegex.matcher(liste[2]).matches()) || Integer.valueOf(liste[2]) > arrangementer.get(Integer.valueOf(0)).getAntallLedigeInt()){
            System.out.println("feil siste");
            godkjent = false;
        }

        return godkjent;
    }


    //Feilhåndetering

    public void textFieldFarge(TextField input, String farge){
        input.setStyle("-fx-background-color: " + farge);
    }

    public void exceptionCatch(TextField input){
        textFieldFarge(input, "#E74725");
    }

    public void feilMelding(String melding){
        //SE OM MAN KAN FORANDRE STØRELSE OG FIKSE LAYOUT
        errorAlert = new Alert(Alert.AlertType.INFORMATION);
        errorAlert.setHeaderText("Feilmelding!");
        errorAlert.setContentText(melding);
        errorAlert.showAndWait();
    }
}
