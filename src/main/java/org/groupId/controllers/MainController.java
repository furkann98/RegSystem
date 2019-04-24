package org.groupId.controllers;

import javafx.application.Platform;
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






	//INITIALIZE
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {

		LOKALE = new Lokale();

		//Kobler observablelist med combobox
		lokalerObservableList = lstViewLokal.getItems();
		arrangementLokaleObservableList = cbLokal.getItems();

		// Kobler objektet med tableview
		lokaleTableViewStruktur();
		arrangementTableViewStruktur();


		//Testverdier

		leggTilLokal(new Lokale("Lindeberg","Kino", 100));
		leggTilLokal(new Lokale("Trosterud","Teater", 150));
		leggTilLokal(new Lokale("Haugerud","Sal", 300));


		Lokale test1 = new Lokale("lokale", "KINO", 124);
		Person test2 = new Person("ole",95959595,"hei@Oslomet.no","", test1,"Dette er en test");
		arrangement = new Arrangement(test2,test1 , test1.getType(),"Fest","Khalid","dette er en konsert", DatePickerArrangement.getValue(),25,10);

		arrangementObservablelist.add(arrangement);

	}



	//KNAPPER - HOVEDSIDE
	public void exitApplication(ActionEvent actionEvent) {
		Platform.exit();
	}

	public void btnSeLokaler(ActionEvent actionEvent) {  //Hovedside
		tabPane.getSelectionModel().select(tabPaneLokale);

	}

	public void btnArrangement(ActionEvent actionEvent) { //Hovedside
		tabPane.getSelectionModel().select(tabPaneArrangement);
	}

	public void btnBillettsalg(ActionEvent actionEvent) { //Hovedside
		tabPane.getSelectionModel().select(tabPaneBillettsalg);
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
					System.out.println("inne i TRY");
					fjernLokal(indeks);
					System.out.println("Forbi fjern");

					int size = arrangementObservablelist.size();
					System.out.println("size: " + size);
					for (int i = 0; i < size; i++) {
						System.out.println("inne i Løkka");
						if(arrangementObservablelist.get(i).getLokale().equals(lokal)){
							System.out.println("inne i for IF");
							System.out.println(arrangementObservablelist.remove(i));
						}
					}
				}catch (NullPointerException e){
					System.out.println("fant exception");

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



	//KNAPPER - ARRANGEMENT
	public void cbLokalOnAction(ActionEvent actionEvent) {
		try{
			Lokale info = cbLokal.getSelectionModel().getSelectedItem();

			txtArrangementAntPlasser.setText(Integer.toString(info.getAntallPlasser()));
			txtArrangementType.setText(info.getType());


		} catch (NullPointerException e){
			System.out.println("combobox feil");
			//feilMelding("Det finnes ingen lokale, dermed er det ikke mulig å se oversikten. Vennligst lag et lokalet før du klikker videre. :)" + "\n" + "TAMAM TAMAM");
		}

	}

	public void btnLagArrangement(ActionEvent actionEvent) {
		if(ArrangementFeilhaandtering()){
				Lokale lokale = cbLokal.getSelectionModel().getSelectedItem();
				Person person = new Person("ole",95959595,"hei@Oslomet.no","", lokale,"Dette er en test");
				arrangement = new Arrangement(person,lokale , lokale.getType(),
						txtArrangementNavn.getText(),txtArrangementArtist.getText(),
						txtArrangementProgram.getText(), DatePickerArrangement.getValue(),
						Integer.valueOf(txtArrangementBillPris.getText()),Integer.valueOf(txtArrangementBillSalg.getText()));

				arrangementObservablelist.add(arrangement);

			tomLagArrangement();
			//cbLokal.;


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

		//MÅ PUTTE I EN SLETTEMETODE
		//
		arrangementObservablelist.remove(redigerArrangement);

		btnRedigerArrangement.setDisable(true);
		btnSlettArrangement.setDisable(true);

	}

	public void btnSlettArrangement(ActionEvent actionEvent) {
		arrangementObservablelist.remove(tableArrangement.getSelectionModel().getSelectedItem());

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



	//METODER

	// Lokal

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





	// Arrangement
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



//Feilhåndetering

/*
	public void feilhaandteringListener(){
		//Lokale - legg til lokal
		feilhaandtering.ListenerKunBokstaver(txtLokalNavn);
		feilhaandtering.ListenerKunBokstaver(txtLokalType);
		feilhaandtering.ListenerKunTall(txtLokalAntallPlasser);

		//Arrangement - legg til arrangment
		feilhaandtering.ListenerKunBokstaver(txtArrangementNavn);
		feilhaandtering.ListenerKunBokstaverTextArea(txtArrangementProgram);
		feilhaandtering.ListenerKunBokstaver(txtArrangementArtist);
		feilhaandtering.ListenerKunTall(txtArrangementBillPris);
		feilhaandtering.ListenerKunTall(txtArrangementBillSalg);

	}
*/

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
		feilmelding += feilhaandtering.KunTall(txtArrangementBillPris);
		feilmelding += feilhaandtering.KunTall(txtArrangementBillSalg);

		//Sjekker om vi har feil.
		if(feilmelding.isEmpty()){
			return true;
		} else{
			feilMelding(feilmelding);
			return false;
		}

	}
/*
	public boolean tomTekst(String tekst){
		if(tekst.isEmpty()){
			return true;
		} else{
			feilMelding(tekst);
			return false;
		}
	}

*/

	public void feilMelding(String melding){
		//SE OM MAN KAN FORANDRE STØRELSE OG FIKSE LAYOUT
		errorAlert = new Alert(Alert.AlertType.ERROR);
		errorAlert.setHeaderText("Feilmelding!");
		errorAlert.setContentText(melding);
		errorAlert.showAndWait();
	}



}
