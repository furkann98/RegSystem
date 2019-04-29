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
import org.groupId.models.*;
import org.groupId.models.Feilhaandtering;
import org.groupId.models.Lagring.Save;

import java.io.IOException;
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
	public Lokale LOKALE = new Lokale();
	public ObservableList<Lokale> lokalerObservableList;
	public ObservableList<Arrangement> lokalersArrangement = FXCollections.observableArrayList();

	// ARRANGEMENT
	public Arrangement ARRANGEMENT = new Arrangement();
	public ObservableList<Lokale> arrangementLokaleObservableList;
	public ObservableList<Arrangement> arrangementObservablelist = FXCollections.observableArrayList();

	// PERSON
	public Person PERSON = new Person();
	public ObservableList<Person> arrangementPersonObservableList;
	public ObservableList<Person> personObservableList = FXCollections.observableArrayList();
	public ObservableList<Arrangement> personArrangementObservableList = FXCollections.observableArrayList();

	// Billett
	public Billett BILLETT = new Billett();
	public ObservableList<Arrangement> billettArrangementObservableList;
	public ObservableList<Billett> billettRegisterArrangement = FXCollections.observableArrayList();

	// annet
	public Alert errorAlert;

	// save
	private Save save = new Save();




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
	public Button btnBillettKjop;

	@FXML
	public TableView<Billett> tableBillett;

	@FXML
	public TableColumn<Billett, String> TCBillettArrangement, TCBillettSete, TCBillettTlf;

	@FXML
	public ComboBox<Arrangement> cbBillettArrangement;

	@FXML
	public TextField txtBillettDato, txtBillettLokale, txtBillettTlf; //txtBillettMax - lage textfield

	@FXML
	public Label lblBillettDato, lblBillettLokale, lblBillettMax;

	@FXML
	public Spinner<Integer> spinnerBillettAntall;






	//INITIALIZE
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {

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
		billettTableViewStruktur();



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
		}

	}

	public void btnLagArrangement(ActionEvent actionEvent) {
		if(ArrangementFeilhaandtering()){
			Lokale lokale = cbLokal.getSelectionModel().getSelectedItem();
			Person person = cbKontaktperson.getSelectionModel().getSelectedItem();
			//new Person("ole","95959595","hei@Oslomet.no","", "Dette er en test");
			Arrangement arrangement = new Arrangement(person, lokale, txtArrangementNavn.getText(),
					txtArrangementArtist.getText(), txtArrangementProgram.getText(),
					DatePickerArrangement.getValue(), Integer.valueOf(txtArrangementBillPris.getText())
					,Integer.valueOf(txtArrangementBillSalg.getText())
			);

			leggTilArrangement(arrangement);
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
		//cbKontaktperson.setValue(0);
		txtArrangementBillPris.clear();
		txtArrangementBillSalg.clear();

	}

	public void leggTilArrangement(Arrangement arrangement){
		BILLETT.lagBillett(arrangement, arrangement.getKontaktPerson().getTlfNummer(), Integer.valueOf(arrangement.getBillettSalg()));

		ARRANGEMENT.leggTilArrangement(arrangement);
		arrangementObservablelist.add(arrangement);
		billettArrangementObservableList.add(arrangement);
		arrangement.getKontaktPerson().LeggTilArrangement(arrangement);

	}

	public void fjernArrangement(Arrangement arrangement){
		ARRANGEMENT.fjernArrangement(arrangement);
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
			Person person = new Person(txtPersonNavn.getText(), txtPersonTlf.getText(), txtPersonEpost.getText(),
					txtPersonNettside.getText(), txtPersonOpplysninger.getText());

			leggTilPerson(person);
			tomPersonRegistrering();
		}

	}

	public void btnPersonSlett(ActionEvent actionEvent) {


		//Dersom lokalet har arrangementer
		if(tablePerson.getSelectionModel().getSelectedItem() != null){
			int indeks = tablePerson.getSelectionModel().getSelectedIndex();
			Person person = tablePerson.getSelectionModel().getSelectedItem();
			Boolean slett = false;
			String arrangement = "Disse arrangementene vil bli slettet: \n\n";

			//Sjekker arrangementer som er linka til Person
			for (int i = 0; i < arrangementObservablelist.size(); i++) {
				if(arrangementObservablelist.get(i).getKontaktPerson().equals(personObservableList.get(indeks))){
					slett = true;
					arrangement += arrangementObservablelist.get(i).getNavn() + "\n\n";
				}
			}

			if(slett){
				errorAlert = new Alert(Alert.AlertType.CONFIRMATION);
				errorAlert.setHeaderText("Er du sikker?");
				errorAlert.setContentText(arrangement);

				//OK-if eller Cancel-else
				Optional<ButtonType> result = errorAlert.showAndWait();
				if (result.get() == ButtonType.OK){
					try{
						fjernPerson(person);
						int size = arrangementObservablelist.size() - 1;
						for (int i = size; i >= 0; i--) {
							if(arrangementObservablelist.get(i).getKontaktPerson().equals(person)){
								fjernArrangement(arrangementObservablelist.get(i));
							}
						}
					}catch (NullPointerException e){

					}
				}

			}else {
				fjernPerson(person);
			}
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

		PERSON.leggTilPerson(person);
		arrangementPersonObservableList.add(person);
		personObservableList.add(person);
	}

	public void fjernPerson(Person person){

		PERSON.fjernPerson(person);
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


	// KNAPPER - BILLETT
	public void cbBillettArrangement(ActionEvent actionEvent) {
		if(cbBillettArrangement.getSelectionModel().getSelectedItem() != null){
			txtBillettDato.setText(cbBillettArrangement.getSelectionModel().getSelectedItem().getTidspunkt().toString());
			txtBillettLokale.setText(cbBillettArrangement.getSelectionModel().getSelectedItem().getLokale().getNavn());
			oppdaterBillett();
		} else{
			txtBillettDato.clear();
			txtBillettLokale.clear();
		}
	}

	public void btnBillettFjern(ActionEvent actionEvent) {
		if(tableBillett.getSelectionModel().getSelectedItem() != null){
			BILLETT.fjernBillett(tableBillett.getSelectionModel().getSelectedItem());
			spinnerStruktur(cbBillettArrangement.getSelectionModel().getSelectedItem().getAntallLedigeInt());

			oppdaterBillett();
		}
	}

	public void btnBillettRediger(ActionEvent actionEvent) {
	}

	public void btnBillettKjoop(ActionEvent actionEvent) {
		BILLETT.lagBillett(cbBillettArrangement.getSelectionModel().getSelectedItem(), txtBillettTlf.getText(), spinnerBillettAntall.getValue());
		oppdaterBillett();
		spinnerStruktur(cbBillettArrangement.getSelectionModel().getSelectedItem().getAntallLedigeInt());
	}

	// METODE - BILLETT

	public void billettTableViewStruktur(){
		tableBillett.setItems(billettRegisterArrangement);

		TCBillettArrangement.setCellValueFactory(new PropertyValueFactory<>("arrangementNavn"));
		TCBillettSete.setCellValueFactory(new PropertyValueFactory<>("sete"));
		TCBillettTlf.setCellValueFactory(new PropertyValueFactory<>("telefonNummer"));

	}

	public void spinnerStruktur(int maks){
		System.out.println("spinneren blir satt til maks " + maks);
		if(maks != 0){
			SpinnerValueFactory<Integer> valueFactory =
					new SpinnerValueFactory.IntegerSpinnerValueFactory(1, maks, 1);

			spinnerBillettAntall.setValueFactory(valueFactory);

			lblBillettMax.setText("Max " + maks);
			visBillettUtsolgt();

		}else{
			skjulBillettUtsolgt();

		}

	}

	public void skjulBillettUtsolgt(){
		btnBillettKjop.setDisable(true);
		txtBillettTlf.setDisable(true);
		spinnerBillettAntall.setDisable(true);
		lblBillettMax.setText("UTSOLGT");
	}

	public void visBillettUtsolgt(){
		btnBillettKjop.setDisable(false);
		txtBillettTlf.setDisable(false);
		spinnerBillettAntall.setDisable(false);
	}

	public void oppdaterBillett(){
		if(!BILLETT.getBillettRegister().isEmpty()){

			billettRegisterArrangement.clear();

			for (Billett b : BILLETT.getBillettRegister()) {
				if(b.getArrangement().equals(cbBillettArrangement.getSelectionModel().getSelectedItem())){
					billettRegisterArrangement.add(b);
				}
			}
			spinnerStruktur(cbBillettArrangement.getSelectionModel().getSelectedItem().getAntallLedigeInt());

			refreshTabeller();
		}

	}



	//ANNET
	public void refreshTabeller(){
		tableBillett.refresh();
		tablePerson.refresh();
		tableArrangement.refresh();
		tableLokale.refresh();
		tablePersonArrangement.refresh();

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
		//feilmelding += feilhaandtering.AntallBillett(txtArrangementBillSalg, txtArrangementAntPlasser);

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

		Arrangement arr1 = new Arrangement(ole, konsert,"Konsert med Khalid","Khalid","Konsert av Khalid, GAALT!", DatePickerArrangement.getValue(),250,100);
		Arrangement arr2 = new Arrangement(abdi, konsert,"Konsert med Drake","Drake","Konsert av Drake, GAALT!", DatePickerArrangement.getValue(),400,50);
		Arrangement arr3 = new Arrangement(ali, foredrag,"Minnestund","","Minnestund for brødre", DatePickerArrangement.getValue(),0,20);


		leggTilArrangement(arr1);
		leggTilArrangement(arr2);
		leggTilArrangement(arr3);







	}


	//LAGRING

	public void btnLagring(ActionEvent actionEvent) throws IOException {
		save.csvLagring("src/lagring.csv", LOKALE, PERSON, ARRANGEMENT, BILLETT);
	}
}
