package org.groupId.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
//import org.groupId.models.Arrangement;
//import org.groupId.models.Lokale;
import org.groupId.models.*;
import org.groupId.models.exceptions.Feilhaandtering;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {

	//DATAFELT
	Feilhaandtering feilhaandtering = new Feilhaandtering();

	// lokale
	public Lokale LOKALE;
	public ObservableList<Lokale> lokalerObservableList;
	public ObservableList<Arrangement> lokalersArrangement = FXCollections.observableArrayList();

	// arrangement
	public Arrangement arrangement;
	public ObservableList<Lokale> arrangementLokaleObservableList;
	public ObservableList<Arrangement> arrangementObservablelist = FXCollections.observableArrayList();

	// person
	public Person person;
	public ObservableList<Person> arrangementPersonObservableList;
	public ObservableList<Person> personObservableList = FXCollections.observableArrayList();
	public ObservableList<Arrangement> personArrangementObservableList = FXCollections.observableArrayList();

	// Billett
	public ObservableList<Arrangement> billettArrangementObservableList;

	// annet
	public Alert errorAlert;




	//DATAFELT FXML

	//Tabpane
	@FXML
	public TabPane tabPane;

	@FXML
	public Tab tabPaneLokale, tabPaneArrangement, tabPaneBillettsalg;


	//Lokale

	@FXML
	public ListView<Lokale> lstViewLokal;

	@FXML
	public TextField txtLokalNavn, txtLokalAntallPlasser, txtLokalType;

	@FXML
	public TextArea txtFlowLokal, txtFlowLokalOverskrift;

	@FXML
	public HBox hBoxNyttLokale;

	@FXML
	public Button btnFullfoorLokalId, btnFjernLokal;

	@FXML
	public TableView<Arrangement> tableLokale;

	@FXML
	public TableColumn<Arrangement, String> TCNavnLokale, TCAntallLokale, TCPersonLokale;

	@FXML
	public TableColumn<Arrangement, LocalDate> TCDatoLokale;


	//Arrangement

	@FXML
	public ComboBox<Lokale> cbLokal;

	@FXML
	public ComboBox<Person> cbKontaktperson;

	@FXML
	public TableView<Arrangement> tableArrangement;

	@FXML
	public TextField txtArrangementAntPlasser, txtArrangementType, txtArrangementNavn,
			txtArrangementArtist, txtArrangementBillPris, txtArrangementBillSalg;

	@FXML
	public TextArea txtArrangementProgram;

	@FXML
	public TableColumn<Arrangement, String> TCNavn, TCProgram, TCPris, TCAntall, TCPerson;

	@FXML
	public TableColumn<Arrangement, Lokale> TCLokale;

	@FXML
	public TableColumn<Arrangement, Date> TCDato;

	@FXML
	public DatePicker DatePickerArrangement;

	@FXML
	public Button btnRedigerArrangement, btnSlettArrangement;


	//Kontaktperson

	@FXML
	public Button btnPersonSlett, btnPersonRediger, btnPersonLeggTil;

	@FXML
	public TextField txtPersonNavn, txtPersonTlf, txtPersonEpost, txtPersonNettside;

	@FXML
	public TextArea txtPersonOpplysninger, txtPersonOversikt;

	@FXML
	public TableView<Person> tablePerson;

	@FXML
	public TableColumn<Person, String> TCPersonNavn, TCPersonTlf, TCPersonEpost,
			TCPersonNettside, TCPersonAntall;

	@FXML
	public TableView<Arrangement> tablePersonArrangement;

	@FXML
	public TableColumn<Arrangement,String> TCPersonArrangementNavn, TCPersonArrangementLokale;

	@FXML
	public TableColumn<Arrangement, LocalDate> TCPersonArrangementDato;

	// Billett

	@FXML
	public ComboBox<Arrangement> cbBillettArrangement;






	//INITIALIZE
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {

		LOKALE = new Lokale();

		//Kobler observablelist med combobox
		lokalerObservableList = lstViewLokal.getItems();
		arrangementLokaleObservableList = cbLokal.getItems();
		arrangementPersonObservableList = cbKontaktperson.getItems();
		billettArrangementObservableList = cbBillettArrangement.getItems();

		// Kobler objektet med tableview
		lokaleTableViewStruktur();
		arrangementTableViewStruktur();
		personTableViewStruktur();
		personArrangementTableViewStruktur();



		//Testverdier
		DatePickerArrangement.setValue(LocalDate.now());
		TESTDATA();



	}









	//KNAPPER - LOKALE

	public void btnFjernLokal(ActionEvent actionEvent) { //Lokale
		//Hjelpe-Variabler
		int indeks = lstViewLokal.getSelectionModel().getSelectedIndex();
		Lokale lokal = lstViewLokal.getSelectionModel().getSelectedItem();
		Boolean slett = false;
		String arrangement = "Disse arrangementene vil bli slettet: \n\n";

		//Sjekker arrangementer som er linka til Lokale
		for (int i = 0; i < arrangementObservablelist.size(); i++) {
			if(arrangementObservablelist.get(i).getLokale().equals(lokalerObservableList.get(indeks))){
				slett = true;
				arrangement += arrangementObservablelist.get(i).getNavn() + "\n\n";
			}
		}

		//Dersom lokalet har arrangementer
		if(slett){
			errorAlert = new Alert(Alert.AlertType.CONFIRMATION);
			errorAlert.setHeaderText("Er du sikker?");
			errorAlert.setContentText(arrangement);


			//OK-if eller Cancel-else
			Optional<ButtonType> result = errorAlert.showAndWait();
			if (result.get() == ButtonType.OK){
				try{
					fjernLokal(indeks);
					int size = arrangementObservablelist.size() - 1;
					for (int i = size; i >= 0; i--) {
						if(arrangementObservablelist.get(i).getLokale().equals(lokal)){
							fjernArrangement(arrangementObservablelist.get(i));
						}
					}
				}catch (NullPointerException e){

				}
			}

		}else {
			fjernLokal(indeks);
		}

		if(LOKALE.isEmpty()){
			btnFjernLokal.setDisable(true);
		}

		tomTextArea();
		skjulLokalleggTil();
	}


	public void btnLeggTilLokal(ActionEvent actionEvent) { //Lokale
		visLokalleggTil();
	}


	public void btnRedigerLokal(ActionEvent actionEvent) { //Lokale

		//visLokalleggTil();

		Lokale redigerLokale = lstViewLokal.getSelectionModel().getSelectedItem();
		txtLokalNavn.setText(redigerLokale.getNavn());

		txtLokalType.setText(redigerLokale.getType());
		txtLokalAntallPlasser.setText(String.valueOf(redigerLokale.getAntallPlasser()));

		lokalerObservableList.remove(redigerLokale);

		visLokalleggTil();

	}


	public void btnFullfoorLokal(ActionEvent actionEvent) { //Lokale
		if(LokalFeilhaandtering()){
			if(LOKALE.isEmpty()){
				btnFjernLokal.setDisable(false);
			}
			Lokale nyttLokal = new Lokale(txtLokalNavn.getText(),txtLokalType.getText(), Integer.parseInt(txtLokalAntallPlasser.getText()));
			leggTilLokal(nyttLokal);
			skjulLokalleggTil();
		}


	}

	public void btnAvbrytLokal(ActionEvent actionEvent) {
		skjulLokalleggTil();
	}

	public void txtFlowOnMouseClicked(MouseEvent arg0){


		try{
			Lokale info = lstViewLokal.getSelectionModel().getSelectedItem();
			info.getOversikt(txtFlowLokalOverskrift,txtFlowLokal);
			lokalersArrangement.clear();

			for (int i = 0; i < arrangementObservablelist.size() ; i++) {
				if(arrangementObservablelist.get(i).getLokale().equals(info)){
					lokalersArrangement.add(arrangementObservablelist.get(i));
				}

			}
			skjulLokalleggTil();
		} catch (NullPointerException e){
			feilMelding("Velg et lokalet eller vennligst lag et lokalet før du klikker videre. :)");
		}
	}



	// METODER - LOKALE

	public void leggTilLokal(Lokale nyttLokal){
		LOKALE.leggTilLokal(nyttLokal);
		lokalerObservableList.add(nyttLokal);
		arrangementLokaleObservableList.add(nyttLokal);
	}

	public void fjernLokal(int indeks){
		LOKALE.fjernLokal(indeks);
		lokalerObservableList.remove(indeks);
		arrangementLokaleObservableList.remove(indeks);
	}

	public void visLokalleggTil(){
		hBoxNyttLokale.setVisible(true);
		btnFullfoorLokalId.setVisible(true);
	}

	public void skjulLokalleggTil(){
		hBoxNyttLokale.setVisible(false);
		btnFullfoorLokalId.setVisible(false);
		txtLokalNavn.setText("");
		txtLokalType.setText("");
		txtLokalAntallPlasser.setText("");
	}

	public void tomTextArea(){
		txtFlowLokal.clear();
		txtFlowLokalOverskrift.clear();
		txtArrangementAntPlasser.clear();
		txtArrangementType.clear();
	}

	public void lokaleTableViewStruktur(){
		tableLokale.setItems(lokalersArrangement);

		TCNavnLokale.setCellValueFactory(new PropertyValueFactory<>("navn"));
		TCPersonLokale.setCellValueFactory(new PropertyValueFactory<>("kontaktPersonNavn"));
		TCAntallLokale.setCellValueFactory(new PropertyValueFactory<>("antallLedige"));
		TCDatoLokale.setCellValueFactory(new PropertyValueFactory<>("tidspunkt"));
	}



	//KNAPPER - ARRANGEMENT
	public void cbLokalOnAction(ActionEvent actionEvent) {
		try{
			Lokale info = cbLokal.getSelectionModel().getSelectedItem();

			txtArrangementAntPlasser.setText(Integer.toString(info.getAntallPlasser()));
			txtArrangementType.setText(info.getType());


		} catch (NullPointerException e){
			System.out.println("combobox feil");
		}

	}

	public void btnLagArrangement(ActionEvent actionEvent) {
		if(ArrangementFeilhaandtering()){
			Lokale lokale = cbLokal.getSelectionModel().getSelectedItem();
			Person person = cbKontaktperson.getSelectionModel().getSelectedItem();
			//new Person("ole","95959595","hei@Oslomet.no","", "Dette er en test");
			arrangement = new Arrangement(person,lokale , lokale.getType(),
					txtArrangementNavn.getText(),txtArrangementArtist.getText(),
					txtArrangementProgram.getText(), DatePickerArrangement.getValue(),
					Integer.valueOf(txtArrangementBillPris.getText()),Integer.valueOf(txtArrangementBillSalg.getText()));

			leggTilArrangement(arrangement);
			tablePerson.refresh();
			tomLagArrangement();


		}
	}


	public void btnRedigerArrangement(ActionEvent actionEvent) {

		Arrangement redigerArrangement = tableArrangement.getSelectionModel().getSelectedItem();
		txtArrangementNavn.setText(redigerArrangement.getNavn());
		txtArrangementArtist.setText(redigerArrangement.getArtist());
		txtArrangementProgram.setText(redigerArrangement.getProgram());
		DatePickerArrangement.setValue(redigerArrangement.getTidspunkt());
		txtArrangementBillPris.setText(String.valueOf(redigerArrangement.getBillettPris()));
		txtArrangementBillSalg.setText(String.valueOf(redigerArrangement.getBillettSalg()));

		cbLokal.getSelectionModel().select(redigerArrangement.getLokale());

		arrangementObservablelist.remove(redigerArrangement);

		btnRedigerArrangement.setDisable(true);
		btnSlettArrangement.setDisable(true);

	}

	public void btnSlettArrangement(ActionEvent actionEvent) {
		Arrangement arrangement = tableArrangement.getSelectionModel().getSelectedItem();

		fjernArrangement(arrangement);

		tablePersonArrangement.refresh();
		tablePerson.refresh();
		tableLokale.refresh();
		tableArrangement.refresh();

		btnRedigerArrangement.setDisable(true);
		btnSlettArrangement.setDisable(true);
	}

	public void tableArrangementOnMouseClicked(MouseEvent mouseEvent) {
		if(tableArrangement.getSelectionModel().getSelectedItem() != null){
			btnRedigerArrangement.setDisable(false);
			btnSlettArrangement.setDisable(false);
		}else {
			btnRedigerArrangement.setDisable(true);
			btnSlettArrangement.setDisable(true);
		}
	}



	// METODER - ARRANGEMENT
	public void arrangementTableViewStruktur(){
		tableArrangement.setItems(arrangementObservablelist);

		TCNavn.setCellValueFactory(new PropertyValueFactory<>("navn"));
		TCProgram.setCellValueFactory(new PropertyValueFactory<>("programTekst"));
		TCPris.setCellValueFactory(new PropertyValueFactory<>("billettPris"));
		TCLokale.setCellValueFactory(new PropertyValueFactory<>("lokale"));
		TCAntall.setCellValueFactory(new PropertyValueFactory<>("antallLedige"));
		TCPerson.setCellValueFactory(new PropertyValueFactory<>("kontaktPersonNavn"));
		TCDato.setCellValueFactory(new PropertyValueFactory<>("tidspunkt"));

	}

	public void tomLagArrangement(){
		//cbLokal.valueProperty().set(null);
		DatePickerArrangement.setValue(LocalDate.now());
		txtArrangementNavn.clear();
		txtArrangementProgram.clear();
		txtArrangementArtist.clear();
		//cbKontaktperson
		txtArrangementBillPris.clear();
		txtArrangementBillSalg.clear();

	}

	public void leggTilArrangement(Arrangement arrangement){
		arrangementObservablelist.add(arrangement);
		billettArrangementObservableList.add(arrangement);
		arrangement.getKontaktPerson().LeggTilArrangement(arrangement);

	}

	public void fjernArrangement(Arrangement arrangement){
		arrangementObservablelist.remove(arrangement);
		billettArrangementObservableList.remove(arrangement);
		arrangement.getKontaktPerson().FjernArrangement(arrangement);
	}

	//KNAPPER - KONTAKTPERSON
	public void btnPersonRediger(ActionEvent actionEvent) {

		Person redigerPerson  = tablePerson.getSelectionModel().getSelectedItem();
		txtPersonNavn.setText(redigerPerson.getNavn());
		txtPersonTlf.setText(redigerPerson.getTlfNummer());
		txtPersonEpost.setText(redigerPerson.getEpost());
		txtPersonNettside.setText(redigerPerson.getNettside());
		txtPersonOpplysninger.setText(redigerPerson.getOpplysninger());

		//arrangementPersonObservableList.remove(redigerPerson);
		personObservableList.remove(redigerPerson);

		txtPersonOversikt.clear();

		tablePerson.refresh();

		txtPersonOversikt.setVisible(false);
		tablePersonArrangement.setVisible(false);

/*
		Lokale redigerLokale = lstViewLokal.getSelectionModel().getSelectedItem();
		txtLokalNavn.setText(redigerLokale.getNavn());

		txtLokalType.setText(redigerLokale.getType());
		txtLokalAntallPlasser.setText(String.valueOf(redigerLokale.getAntallPlasser()));

		lokalerObservableList.remove(redigerLokale);

		visLokalleggTil();*/
	}

	public void btnPersonLeggTil(ActionEvent actionEvent) {
		if(PersonFeilhaandtering()) {
			person = new Person(txtPersonNavn.getText(), txtPersonTlf.getText(), txtPersonEpost.getText(),
					txtPersonNettside.getText(), txtPersonOpplysninger.getText());

			leggTilPerson(person);
			tomPersonRegistrering();
		}

	}

	public void btnPersonSlett(ActionEvent actionEvent) {
		if(tablePerson.getSelectionModel().getSelectedItem() != null){
			fjernPerson(tablePerson.getSelectionModel().getSelectedItem());

		}else{

		}
	}

	public void tablePersonArrangementOnMouseClicked(MouseEvent mouseEvent) {
		personArrangementObservableList.clear();
		if(tablePerson.getSelectionModel().getSelectedItem() != null){
			txtPersonOversikt.setText(tablePerson.getSelectionModel().getSelectedItem().getOpplysninger());

			ArrayList<Arrangement> test = tablePerson.getSelectionModel().getSelectedItem().getArrangementer();
			for (Arrangement a : test) {
				personArrangementObservableList.add(a);
			}
			btnPersonRediger.setDisable(false);
			btnPersonSlett.setDisable(false);
			txtPersonOversikt.setVisible(true);
			tablePersonArrangement.setVisible(true);
		} else {
			btnPersonRediger.setDisable(true);
			btnPersonSlett.setDisable(true);
			txtPersonOversikt.setVisible(false);
			tablePersonArrangement.setVisible(false);
		}
	}

	// METODER - KONTAKTPERSON
	public void personTableViewStruktur(){
		tablePerson.setItems(personObservableList);

		TCPersonNavn.setCellValueFactory(new PropertyValueFactory<>("navn"));
		TCPersonTlf.setCellValueFactory(new PropertyValueFactory<>("tlfNummer"));
		TCPersonEpost.setCellValueFactory(new PropertyValueFactory<>("Epost"));
		TCPersonNettside.setCellValueFactory(new PropertyValueFactory<>("nettside"));
		TCPersonAntall.setCellValueFactory(new PropertyValueFactory<>("antallArrangementer"));


	}

	public void personArrangementTableViewStruktur(){
		tablePersonArrangement.setItems(personArrangementObservableList);

		TCPersonArrangementNavn.setCellValueFactory(new PropertyValueFactory<>("navn"));
		TCPersonArrangementLokale.setCellValueFactory(new PropertyValueFactory<>("lokale"));
		TCPersonArrangementDato.setCellValueFactory(new PropertyValueFactory<>("tidspunkt"));

	}

	public void leggTilPerson(Person person){
		arrangementPersonObservableList.add(person);
		personObservableList.add(person);
	}

	public void fjernPerson(Person person){

		arrangementPersonObservableList.remove(person);
		personObservableList.remove(person);

		if(personObservableList.isEmpty()){
			btnPersonSlett.setDisable(true);
			btnPersonRediger.setDisable(true);
		}

		tomPersonOversikt();
	}

	public void tomPersonOversikt(){
		txtPersonOversikt.clear();
		txtPersonOversikt.setVisible(false);
		tablePersonArrangement.setVisible(false);
	}

	public void tomPersonRegistrering(){
		txtPersonNavn.clear();
		txtPersonTlf.clear();
		txtPersonEpost.clear();
		txtPersonNettside.clear();
		txtPersonOpplysninger.clear();
	}



//FEILHÅNDTERING


	public boolean LokalFeilhaandtering () {
		String feilmelding = "";

		feilmelding += feilhaandtering.KunBokstaver(txtLokalNavn);
		feilmelding += feilhaandtering.KunBokstaver(txtLokalType);
		feilmelding += feilhaandtering.KunTall(txtLokalAntallPlasser);

		if(feilmelding.isEmpty()){
			return true;
		} else{
			feilMelding(feilmelding);
			return false;
		}
	}

	public boolean ArrangementFeilhaandtering () {
		String feilmelding = "";

		feilmelding += feilhaandtering.IngenObjektValgt(cbLokal);
		feilmelding += feilhaandtering.IngenDatoValgt(DatePickerArrangement);
		feilmelding += feilhaandtering.KunBokstaver(txtArrangementNavn);
		feilmelding += feilhaandtering.KunBokstaverTextArea(txtArrangementProgram);
		feilmelding += feilhaandtering.KunBokstaver(txtArrangementArtist);
		feilmelding += feilhaandtering.IngenObjektValgt(cbKontaktperson);
		feilmelding += feilhaandtering.KunTall(txtArrangementBillPris);
		feilmelding += feilhaandtering.KunTall(txtArrangementBillSalg);
		feilmelding += feilhaandtering.AntallBillett(txtArrangementBillSalg, txtArrangementAntPlasser);

		//Sjekker om vi har feil.
		if(feilmelding.isEmpty()){
			return true;
		} else{
			feilMelding(feilmelding);
			return false;
		}

	}

	public boolean PersonFeilhaandtering () {
		String feilmelding = "";

		feilmelding += feilhaandtering.KunBokstaver(txtPersonNavn);
		feilmelding += feilhaandtering.KunTall(txtPersonTlf); //TELEFON REGEX
		feilmelding += feilhaandtering.KunBokstaver(txtPersonEpost); //EPOST REGEX
		feilmelding += feilhaandtering.KunBokstaver(txtPersonNettside);
		feilmelding += feilhaandtering.KunBokstaverTextArea(txtPersonOpplysninger);

		if(feilmelding.isEmpty()){
			return true;
		} else{
			feilMelding(feilmelding);
			return false;
		}
	}


	public void feilMelding(String melding){
		//SE OM MAN KAN FORANDRE STØRELSE OG FIKSE LAYOUT
		errorAlert = new Alert(Alert.AlertType.ERROR);
		errorAlert.setHeaderText("Feilmelding!");
		errorAlert.setContentText(melding);
		errorAlert.showAndWait();
	}


	public void btnBillettFjern(ActionEvent actionEvent) {
	}

	public void btnBillettRediger(ActionEvent actionEvent) {
	}

	public void btnBillettKjoop(ActionEvent actionEvent) {
	}


	//TESTFELT

	public void TESTDATA(){
		Lokale konsert = new Lokale("Konsert", "Sal", 500);
		Lokale kino = new Lokale("Kino", "Kinosal", 200);
		Lokale teater = new Lokale("Teater", "Sal", 300);
		Lokale foredrag = new Lokale("Foredrag", "Sal",50);

		leggTilLokal(konsert);
		leggTilLokal(kino);
		leggTilLokal(teater);
		leggTilLokal(foredrag);

		Person ole = new Person("Ole","95959595","hei@Oslomet.no","www.test.no","Dette er en test");
		Person abdi = new Person("Abdi","25255555","Petter@Oslomet.no","Ingen","Dette er en test2");
		Person ali = new Person("Ali","23543092","Thomas@Oslomet.no","www.test.no","Dette er en test3");

		leggTilPerson(ole);
		leggTilPerson(abdi);
		leggTilPerson(ali);

		Arrangement arr1 = new Arrangement(ole, konsert, konsert.getType(),"Konsert med Khalid","Khalid","Konsert av Khalid, GAALT!", DatePickerArrangement.getValue(),250,100);
		Arrangement arr2 = new Arrangement(abdi, konsert, konsert.getType(),"Konsert med Drake","Drake","Konsert av Drake, GAALT!", DatePickerArrangement.getValue(),400,50);
		Arrangement arr3 = new Arrangement(ali, foredrag, foredrag.getType(),"Minnestund","","Minnestund for brødre", DatePickerArrangement.getValue(),0,20);


		leggTilArrangement(arr1);
		leggTilArrangement(arr2);
		leggTilArrangement(arr3);







	}
}
