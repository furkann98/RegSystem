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
import java.util.Date;
import java.util.ResourceBundle;

public class MainController implements Initializable {

	//DATAFELT
	Feilhaandtering feilhaandtering = new Feilhaandtering();

	// lokale
	public Lokale LOKALE;
	public ObservableList<Lokale> lokalerObservableList;

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






	//INITIALIZE
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {

		LOKALE = new Lokale();

		lokalerObservableList = lstViewLokal.getItems();
		arrangementLokaleObservableList = cbLokal.getItems();

		arrangementTableViewStruktur(); // Kobler objektet med tableview
		//feilhaandteringListener(); // kobler alle bokser til en listener


		//Testverdier

		leggTilLokal(new Lokale("Lindeberg","Kino", 100));
		leggTilLokal(new Lokale("Trosterud","Teater", 150));
		leggTilLokal(new Lokale("Haugerud","Sal", 300));


		Lokale test1 = new Lokale("lokale", "KINO", 124);
		Person test2 = new Person("ole",95959595,"hei@Oslomet.no","", test1,"Dette er en test");
		arrangement = new Arrangement(test2,test1 , test1.getType(),"Fest",null,"dette er en konsert", DatePickerArrangement.getValue(),25,10);

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
		fjernLokal(lstViewLokal.getSelectionModel().getSelectedIndex());

		if(LOKALE.isEmpty()){
			btnFjernLokal.setDisable(true);
		}

		tomTextArea();
	}


	public void btnLeggTilLokal(ActionEvent actionEvent) { //Lokale
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
			tomFulfoorLokal();
		}


	}

	public void txtFlowOnMouseClicked(MouseEvent arg0){

		try{
			Lokale info = lstViewLokal.getSelectionModel().getSelectedItem();
			info.getOversikt(txtFlowLokalOverskrift,txtFlowLokal);
			//txtFlowLokalOverskrift.setText(info.getTKNavn());
			//txtFlowLokal.setText("Type: " + info.getType() + "\n" + "Antall Plasser: " + info.getAntallPlasser());

		} catch (NullPointerException e){
			feilMelding("Det finnes ingen lokale, dermed er det ikke mulig å se oversikten. Vennligst lag et lokalet før du klikker videre. :)" + "\n" + "TAMAM TAMAM");
		}
	}

	//KNAPPER - ARRANGEMENT
	public void cbLokalOnAction(ActionEvent actionEvent) {
		try{
			Lokale info = cbLokal.getSelectionModel().getSelectedItem();

			txtArrangementAntPlasser.setText(Integer.toString(info.getAntallPlasser()));
			txtArrangementType.setText(info.getType());


		} catch (NullPointerException e){
			feilMelding("Det finnes ingen lokale, dermed er det ikke mulig å se oversikten. Vennligst lag et lokalet før du klikker videre. :)" + "\n" + "TAMAM TAMAM");
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

			cbLokal.getSelectionModel().clearSelection();


		}



	}



	public void tableArrangementOnMouseClicked(MouseEvent mouseEvent) {

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
	}

	public void tomTextArea(){
		txtFlowLokal.clear();
		txtFlowLokalOverskrift.clear();
		txtArrangementAntPlasser.clear();
		txtArrangementType.clear();
	}

	public void tomFulfoorLokal() {
		txtLokalNavn.setText("");
		txtLokalType.setText("");
		txtLokalAntallPlasser.setText("");
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





/*

	//Feilhåndetering
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

		System.out.println(feilmelding);

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

		System.out.println(feilmelding);

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



}
