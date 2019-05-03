package org.groupId.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import org.groupId.models.*;
import org.groupId.models.Feilhaandtering;
import org.groupId.models.filhaandtering.InnlastingCSV;
import org.groupId.models.filhaandtering.InnlastingJOBJ;
import org.groupId.models.filhaandtering.LagringCSV;
import org.groupId.models.filhaandtering.LagringJOBJ;
import org.groupId.models.thread.threadClass;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainController implements Initializable {

	//DATAFELT
	private Feilhaandtering feilhaandtering = new Feilhaandtering();
	private LagringCSV lagringCSV = new LagringCSV();
	private InnlastingCSV innlastingCSV = new InnlastingCSV();
	private LagringJOBJ lagringJOBJ = new LagringJOBJ();
	private InnlastingJOBJ innlastingJOBJ = new InnlastingJOBJ();

	// lokale
	private Lokale LOKALE = new Lokale();
	private ObservableList<Lokale> lokalerObservableList;
	private ObservableList<Arrangement> lokalersArrangement = FXCollections.observableArrayList();

	// ARRANGEMENT
	private Arrangement ARRANGEMENT = new Arrangement();
	private ObservableList<Lokale> arrangementLokaleObservableList;
	private ObservableList<Arrangement> arrangementObservablelist = FXCollections.observableArrayList();

	// PERSON
	private Person PERSON = new Person();
	private ObservableList<Person> arrangementPersonObservableList;
	private ObservableList<Person> personObservableList = FXCollections.observableArrayList();
	private ObservableList<Arrangement> personArrangementObservableList = FXCollections.observableArrayList();

	// Billett
	private Billett BILLETT = new Billett();
	private ObservableList<Arrangement> billettArrangementObservableList;
	private ObservableList<Billett> billettRegisterArrangement = FXCollections.observableArrayList();

	// annet
	private Alert errorAlert;




	//DATAFELT FXML


	//Lokale
	@FXML
	private ListView<Lokale> lstViewLokal;

	@FXML
	private TextField txtLokalNavn, txtLokalAntallPlasser, txtLokalType;

	@FXML
	private TextArea txtFlowLokal;

	@FXML
	private HBox hBoxNyttLokale;

	@FXML
	private Button btnFullfoorLokalId, btnFjernLokal, btnRedigerLokal;

	@FXML
	private TableView<Arrangement> tableLokale;

	@FXML
	private TableColumn<Arrangement, String> TCNavnLokale, TCAntallLokale, TCPersonLokale;

	@FXML
	private TableColumn<Arrangement, LocalDate> TCDatoLokale;


	//Arrangement

	@FXML
	private ComboBox<Lokale> cbLokal;

	@FXML
	private ComboBox<Person> cbKontaktperson;

	@FXML
	private TableView<Arrangement> tableArrangement;

	@FXML
	private TextField txtArrangementAntPlasser, txtArrangementType, txtArrangementNavn,
			txtArrangementArtist, txtArrangementBillPris, txtArrangementBillSalg;

	@FXML
	private TextArea txtArrangementProgram;

	@FXML
	private TableColumn<Arrangement, String> TCNavn, TCProgram, TCPris, TCAntall, TCPerson;

	@FXML
	private TableColumn<Arrangement, Lokale> TCLokale;

	@FXML
	private TableColumn<Arrangement, Date> TCDato;

	@FXML
	private DatePicker DatePickerArrangement;

	@FXML
	private Button btnRedigerArrangement, btnSlettArrangement;


	//Kontaktperson

	@FXML
	private Button btnPersonSlett, btnPersonRediger; //btnPersonLeggTil

	@FXML
	private TextField txtPersonNavn, txtPersonTlf, txtPersonEpost, txtPersonNettside;

	@FXML
	private TextArea txtPersonOpplysninger, txtPersonOversikt;

	@FXML
	private TableView<Person> tablePerson;

	@FXML
	private TableColumn<Person, String> TCPersonNavn, TCPersonTlf, TCPersonEpost,
			TCPersonNettside, TCPersonAntall;

	@FXML
	private TableView<Arrangement> tablePersonArrangement;

	@FXML
	private TableColumn<Arrangement,String> TCPersonArrangementNavn, TCPersonArrangementLokale;

	@FXML
	private TableColumn<Arrangement, LocalDate> TCPersonArrangementDato;


	// Billett

	@FXML
	private Button btnBillettKjop, btnBillettFjern;

	@FXML
	private HBox HBoxBillettKjop;

	@FXML
	private TableView<Billett> tableBillett;

	@FXML
	private TableColumn<Billett, String> TCBillettArrangement, TCBillettSete, TCBillettTlf;

	@FXML
	private ComboBox<Arrangement> cbBillettArrangement;

	@FXML
	private TextField txtBillettDato, txtBillettLokale, txtBillettTlf;

	@FXML
	private Label lblBillettDato, lblBillettLokale, lblBillettMax;

	@FXML
	private Spinner<Integer> spinnerBillettAntall;






	//INITIALIZE
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		//Kobler opp lister og data med GUI
		startOppsett();


	}



	//KNAPPER - LOKALE

	public void btnFjernLokal(ActionEvent actionEvent) { //Lokale
		//Hjelpe-Variabler
		int indeks = lstViewLokal.getSelectionModel().getSelectedIndex();
		Lokale lokal = lstViewLokal.getSelectionModel().getSelectedItem();
		Boolean slett = false;
		StringBuilder slettedeArrangement = new StringBuilder();
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
					e.getMessage();
				}
			}

		}else {
			fjernLokal(indeks);
		}

		if(LOKALE.isEmpty()){
			btnFjernLokal.setDisable(true);
		}

		skjulLokaleOversikt();
		skjulLokalleggTil();
	}


	public void btnLeggTilLokal(ActionEvent actionEvent) { //Lokale
		visLokalleggTil();
		skjulLokaleOversikt();
	}


	public void btnRedigerLokal(ActionEvent actionEvent) { //Lokale

		//visLokalleggTil();

		Lokale redigerLokale = lstViewLokal.getSelectionModel().getSelectedItem();
		txtLokalNavn.setText(redigerLokale.getNavn());

		txtLokalType.setText(redigerLokale.getType());
		txtLokalAntallPlasser.setText(String.valueOf(redigerLokale.getAntallPlasser()));

		lokalerObservableList.remove(redigerLokale);

		visLokalleggTil();
		skjulLokaleOversikt();

	}


	public void btnFullfoorLokal(ActionEvent actionEvent) { //Lokale
		if(LokalFeilhaandtering()){
			if(LOKALE.isEmpty()){
				btnFjernLokal.setDisable(false);
			}
			Lokale nyttLokal = new Lokale(txtLokalNavn.getText(),txtLokalType.getText(), Integer.parseInt(txtLokalAntallPlasser.getText()));
			leggTilLokal(nyttLokal);
			skjulLokalleggTil();
			skjulLokaleOversikt();
		}


	}

	public void btnAvbrytLokal(ActionEvent actionEvent) {
		skjulLokalleggTil();
		skjulLokaleOversikt();
	}

	public void txtFlowOnMouseClicked(MouseEvent arg0){

		if(lstViewLokal.getSelectionModel().getSelectedItem() != null){
			Lokale info = lstViewLokal.getSelectionModel().getSelectedItem();
			info.getOversikt(txtFlowLokal);
			lokalersArrangement.clear();

			for (int i = 0; i < arrangementObservablelist.size() ; i++) {
				if(arrangementObservablelist.get(i).getLokale().equals(info)){
					lokalersArrangement.add(arrangementObservablelist.get(i));
				}

			}
			btnFjernLokal.setDisable(false);
			btnRedigerLokal.setDisable(false);
			skjulLokalleggTil();
			visLokaleOversikt();
		}else {
			btnFjernLokal.setDisable(true);
			btnRedigerLokal.setDisable(true);

		}
	}



	// METODER - LOKALE

	private void leggTilLokal(Lokale nyttLokal){
		LOKALE.leggTilLokal(nyttLokal);
		lokalerObservableList.add(nyttLokal);
		arrangementLokaleObservableList.add(nyttLokal);
	}

	private void fjernLokal(int indeks){
		LOKALE.fjernLokal(indeks);
		lokalerObservableList.remove(indeks);
		arrangementLokaleObservableList.remove(indeks);
	}

	private void visLokalleggTil(){
		hBoxNyttLokale.setVisible(true);
		btnFullfoorLokalId.setVisible(true);
	}

	private void skjulLokalleggTil(){
		hBoxNyttLokale.setVisible(false);
		btnFullfoorLokalId.setVisible(false);
		txtLokalNavn.setText("");
		txtLokalType.setText("");
		txtLokalAntallPlasser.setText("");
	}


	private void skjulLokaleOversikt(){
		txtFlowLokal.clear();
		txtArrangementAntPlasser.clear();
		txtArrangementType.clear();

		txtFlowLokal.setVisible(false);
		tableLokale.setVisible(false);
		txtArrangementAntPlasser.clear();

	}

	private void visLokaleOversikt(){

		txtFlowLokal.setVisible(true);
		tableLokale.setVisible(true);

	}

	private void lokaleTableViewStruktur(){
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
			//new Person("ole","95959595","hei.txt@Oslomet.no","", "Dette er en test");
			Arrangement arrangement = new Arrangement(person, lokale, txtArrangementNavn.getText(),
					txtArrangementArtist.getText(), txtArrangementProgram.getText(),
					DatePickerArrangement.getValue(), Integer.valueOf(txtArrangementBillPris.getText())
					,Integer.valueOf(txtArrangementBillSalg.getText())
			);

			leggTilArrangementOgBillett(arrangement);
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

	public void leggTilArrangementOgBillett(Arrangement arrangement){
		BILLETT.lagBillett(arrangement, arrangement.getKontaktPerson().getTlfNummer(), Integer.valueOf(arrangement.getBillettSalg()));
		leggTilArrangement(arrangement);

	}

	public void leggTilArrangement(Arrangement arrangement){
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
						e.getMessage();
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
			txtPersonOversikt.setText("OPPLYSNINGER: \n" + tablePerson.getSelectionModel().getSelectedItem().getOpplysninger());

			ArrayList<Arrangement> test = tablePerson.getSelectionModel().getSelectedItem().getArrangementer();
			personArrangementObservableList.addAll(test);
			//for (Arrangement a : test) {
			//	personArrangementObservableList.add(a);
			//}
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
	private void personTableViewStruktur(){
		tablePerson.setItems(personObservableList);

		TCPersonNavn.setCellValueFactory(new PropertyValueFactory<>("navn"));
		TCPersonTlf.setCellValueFactory(new PropertyValueFactory<>("tlfNummer"));
		TCPersonEpost.setCellValueFactory(new PropertyValueFactory<>("Epost"));
		TCPersonNettside.setCellValueFactory(new PropertyValueFactory<>("nettside"));
		TCPersonAntall.setCellValueFactory(new PropertyValueFactory<>("antallArrangementer"));


	}

	private void personArrangementTableViewStruktur(){
		tablePersonArrangement.setItems(personArrangementObservableList);

		TCPersonArrangementNavn.setCellValueFactory(new PropertyValueFactory<>("navn"));
		TCPersonArrangementLokale.setCellValueFactory(new PropertyValueFactory<>("lokale"));
		TCPersonArrangementDato.setCellValueFactory(new PropertyValueFactory<>("tidspunkt"));

	}

	private void leggTilPerson(Person person){

		PERSON.leggTilPerson(person);
		arrangementPersonObservableList.add(person);
		personObservableList.add(person);
	}

	private void fjernPerson(Person person){

		PERSON.fjernPerson(person);
		arrangementPersonObservableList.remove(person);
		personObservableList.remove(person);

		if(personObservableList.isEmpty()){
			btnPersonSlett.setDisable(true);
			btnPersonRediger.setDisable(true);
		}

		tomPersonOversikt();
	}

	private void tomPersonOversikt(){
		txtPersonOversikt.clear();
		txtPersonOversikt.setVisible(false);
		tablePersonArrangement.setVisible(false);
	}

	private void tomPersonRegistrering(){
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
			btnBillettFjern.setDisable(false);
			oppdaterBillett();
			visBillettKjop();
			visBillett();
		} else{
			txtBillettDato.clear();
			txtBillettLokale.clear();
			skjulBillett();
		}
	}

	public void btnBillettFjern(ActionEvent actionEvent) {
		if(tableBillett.getSelectionModel().getSelectedItem() != null){
			BILLETT.fjernBillett(tableBillett.getSelectionModel().getSelectedItem());
			spinnerStruktur(cbBillettArrangement.getSelectionModel().getSelectedItem().getAntallLedigeInt());

			oppdaterBillett();

			if(billettRegisterArrangement.isEmpty()){
				btnBillettFjern.setDisable(true);
			}else{
				btnBillettFjern.setDisable(false);
			}
		}
	}

	public void btnBillettKjoop(ActionEvent actionEvent) {

		if(BillettFeilhaandtering()){
			BILLETT.lagBillett(cbBillettArrangement.getSelectionModel().getSelectedItem(), txtBillettTlf.getText(), spinnerBillettAntall.getValue());
			oppdaterBillett();
			spinnerStruktur(cbBillettArrangement.getSelectionModel().getSelectedItem().getAntallLedigeInt());
			btnBillettFjern.setDisable(false);
		}

	}

	// METODE - BILLETT

	private void visBillettKjop(){
		HBoxBillettKjop.setVisible(true);
	}

	private void billettTableViewStruktur(){
		tableBillett.setItems(billettRegisterArrangement);

		TCBillettArrangement.setCellValueFactory(new PropertyValueFactory<>("arrangementNavn"));
		TCBillettSete.setCellValueFactory(new PropertyValueFactory<>("sete"));
		TCBillettTlf.setCellValueFactory(new PropertyValueFactory<>("telefonNummer"));

	}

	private void spinnerStruktur(int maks){
		if(maks != 0 ){
			SpinnerValueFactory<Integer> valueFactory =
					new SpinnerValueFactory.IntegerSpinnerValueFactory(1, maks, 1);

			spinnerBillettAntall.setValueFactory(valueFactory);

			lblBillettMax.setText("Max " + maks);
			visBillettUtsolgt();

		}else{
			lblBillettMax.setText("UTSOLGT");
			skjulBillettUtsolgt();

		}

	}

	private void visBillett(){
		HBoxBillettKjop.setVisible(true);
		lblBillettMax.setVisible(true);
		lblBillettDato.setVisible(true);
		lblBillettLokale.setVisible(true);
		txtBillettLokale.setVisible(true);
		txtBillettDato.setVisible(true);
		tableBillett.setVisible(true);
		btnBillettFjern.setVisible(true);


	}

	private void skjulBillett(){
		HBoxBillettKjop.setVisible(false);
		lblBillettMax.setVisible(false);
		lblBillettDato.setVisible(false);
		lblBillettLokale.setVisible(false);
		txtBillettLokale.setVisible(false);
		txtBillettDato.setVisible(false);
		tableBillett.setVisible(false);
		btnBillettFjern.setVisible(false);

	}


	private void skjulBillettUtsolgt(){
		btnBillettKjop.setDisable(true);
		txtBillettTlf.setDisable(true);
		spinnerBillettAntall.setDisable(true);
	}

	private void visBillettUtsolgt(){
		btnBillettKjop.setDisable(false);
		txtBillettTlf.setDisable(false);
		spinnerBillettAntall.setDisable(false);
	}

	private void oppdaterBillett(){
		if(!BILLETT.getBilletter().isEmpty()){

			billettRegisterArrangement.clear();

			for (Billett b : BILLETT.getBilletter()) {
				if(b.getArrangement().equals(cbBillettArrangement.getSelectionModel().getSelectedItem())){
					billettRegisterArrangement.add(b);
				}
			}
			spinnerStruktur(cbBillettArrangement.getSelectionModel().getSelectedItem().getAntallLedigeInt());

			refreshTabeller();
		}

	}

	//ANNET
	private void startOppsett(){
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

		//TESTDATA
		TESTDATA();
	}

	private void refreshTabeller(){
		tableBillett.refresh();
		tablePerson.refresh();
		tableArrangement.refresh();
		tableLokale.refresh();
		tablePersonArrangement.refresh();

	}
	public void clearRegSystem(){
		//Lokale
		LOKALE.clear();
		lokalerObservableList.clear();
		lokalersArrangement.clear();
		skjulLokaleOversikt();
		skjulLokalleggTil();
		btnFjernLokal.setDisable(true);
		btnRedigerLokal.setDisable(true);

		//Arrangement
		ARRANGEMENT.clear();
		arrangementLokaleObservableList.clear();
		arrangementObservablelist.clear();
		arrangementPersonObservableList.clear();
		tomLagArrangement();

		//Person
		PERSON.clear();
		personObservableList.clear();
		personArrangementObservableList.clear();
		btnPersonSlett.setDisable(true);
		btnPersonRediger.setDisable(true);
		tomPersonOversikt();
		tomPersonRegistrering();

		//Billett
		BILLETT.clear();
		billettArrangementObservableList.clear();
		billettRegisterArrangement.clear();
		skjulBillettUtsolgt();
		HBoxBillettKjop.setVisible(false);
	}



	//FEILHÅNDTERING
	private boolean LokalFeilhaandtering () {
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

	private boolean ArrangementFeilhaandtering () {
		String feilmelding = "";

		feilmelding += feilhaandtering.IngenObjektValgt(cbLokal);
		feilmelding += feilhaandtering.IngenDatoValgt(DatePickerArrangement);
		feilmelding += feilhaandtering.KunBokstaver(txtArrangementNavn);
		feilmelding += feilhaandtering.KunTekstTextArea(txtArrangementProgram);
		feilmelding += feilhaandtering.KunTekst(txtArrangementArtist);
		feilmelding += feilhaandtering.IngenObjektValgt(cbKontaktperson);
		feilmelding += feilhaandtering.KunTall(txtArrangementBillPris);
		feilmelding += feilhaandtering.KunTall(txtArrangementBillSalg);
		if(cbLokal.getSelectionModel().getSelectedItem() != null){
			feilmelding += feilhaandtering.AntallBillett(txtArrangementBillSalg, cbLokal.getSelectionModel().getSelectedItem());
		}

		if(feilmelding.isEmpty()){
			return true;
		} else{
			feilMelding(feilmelding);
			return false;
		}

	}

	private boolean PersonFeilhaandtering () {
		String feilmelding = "";

		feilmelding += feilhaandtering.KunBokstaver(txtPersonNavn);
		feilmelding += feilhaandtering.kunTlf(txtPersonTlf); //TELEFON REGEX
		feilmelding += feilhaandtering.kunEpost(txtPersonEpost); //EPOST REGEX
		feilmelding += feilhaandtering.KunTekst(txtPersonNettside);
		feilmelding += feilhaandtering.KunTekstTextArea(txtPersonOpplysninger);

		if(feilmelding.isEmpty()){
			return true;
		} else{
			feilMelding(feilmelding);
			return false;
		}
	}

	private boolean BillettFeilhaandtering () {
		String feilmelding = "";

		feilmelding += feilhaandtering.kunTlf(txtBillettTlf);

		if(feilmelding.isEmpty()){
			return true;
		} else{
			feilMelding(feilmelding);
			return false;
		}
	}


	private void feilMelding(String melding){
		//SE OM MAN KAN FORANDRE STØRELSE OG FIKSE LAYOUT
		errorAlert = new Alert(Alert.AlertType.ERROR);
		errorAlert.setHeaderText("Feilmelding!");
		errorAlert.setContentText(melding);
		errorAlert.showAndWait();
	}




	//TESTFELT

	private void TESTDATA(){
		Lokale konsert = new Lokale("Konsert", "Sal", 500);
		Lokale kino = new Lokale("Kino", "Kinosal", 200);
		Lokale teater = new Lokale("Teater", "Sal", 300);
		Lokale foredrag = new Lokale("Foredrag", "Sal",50);

		leggTilLokal(konsert);
		leggTilLokal(kino);
		leggTilLokal(teater);
		leggTilLokal(foredrag);

		Person ole = new Person("Ole","95959595","hei.txt@Oslomet.no","www.test.no","Opplysninger");
		Person petter = new Person("Petter","25255555","Petter@Oslomet.no","Ingen","Opplysninger");
		Person thomas = new Person("Thomas","23543092","Thomas@Oslomet.no","www.test.no","Opplysninger");

		leggTilPerson(ole);
		leggTilPerson(petter);
		leggTilPerson(thomas);

		Arrangement arr1 = new Arrangement(ole, konsert,"Konsert med Khalid","Khalid","Konsert", DatePickerArrangement.getValue(),250,100);
		Arrangement arr2 = new Arrangement(petter, konsert,"Konsert med Drake","Drake","Konsert", DatePickerArrangement.getValue(),400,50);
		Arrangement arr3 = new Arrangement(thomas, foredrag,"Minnestund","","Minnestund for familie", DatePickerArrangement.getValue(),0,20);


		leggTilArrangementOgBillett(arr1);
		leggTilArrangementOgBillett(arr2);
		leggTilArrangementOgBillett(arr3);


	}


	//LAGRING
	public void btnLagring(ActionEvent actionEvent) throws IOException {
		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Lagre");
			fileChooser.getExtensionFilters().addAll(
					new FileChooser.ExtensionFilter("csv", "*.csv"),
					new FileChooser.ExtensionFilter("jobj", "*.jobj")
			);
			File fil = fileChooser.showSaveDialog(null);

			if(fil != null){
				String filnavn = fil.getPath(); //Valgt filechooser path

				if (filnavn.endsWith(".csv")) {
					try {
						lagringCSV.Lagring(filnavn, LOKALE, PERSON, ARRANGEMENT, BILLETT);
					} catch (IOException e) {
						e.printStackTrace();
					}

				} else if (filnavn.endsWith(".jobj")) {
						lagringJOBJ.Lagring(filnavn, LOKALE, PERSON, ARRANGEMENT, BILLETT);
				} else {
					System.out.println("cancel lagringCSV");
				}
			}
		}catch (NullPointerException e){

		}
	}

	public void btnInnlasting(ActionEvent actionEvent) throws IOException {

		FileChooser fileChooser = new FileChooser();
		File fil = fileChooser.showOpenDialog(null);

		if (fil != null) {
		String filnavn = fil.getPath(); //Valgt filechooser path

		Task task = new threadClass(innlastingCSV, innlastingJOBJ,this::ThreadDoneCSV,this::ThreadDoneJOBJ, fil, filnavn, LOKALE, PERSON, ARRANGEMENT, BILLETT);

		ExecutorService service = Executors.newSingleThreadExecutor();
		service.execute(task);


		} else {
			//Cancel innlasting
		}
	}


	private void ThreadDoneCSV() {
		clearRegSystem();
		//	load.csvOpplasting(kilde, LOKALE, PERSON, ARRANGEMENT, BILLETT);

		for (Lokale l : innlastingCSV.getLokaler()) {
			leggTilLokal(l);
		}
		for (Person p : innlastingCSV.getPersoner()) {
			leggTilPerson(p);
		}
		for (Arrangement a : innlastingCSV.getArrangementer()) {
			leggTilArrangement(a);
			a.nullstillSalg();
		}
		for (Billett b : innlastingCSV.getBilletter()) {
			BILLETT.lagBillett(b.getArrangement(), b.getTelefonNummer(), b.getAntall());
		}
	}

	private void ThreadDoneJOBJ() {
		clearRegSystem();

		for (Lokale l : innlastingJOBJ.getLokaler()) {
			leggTilLokal(l);
		}
		for (Person p : innlastingJOBJ.getPersoner()) {
			leggTilPerson(p);
		}
		for (Arrangement a : innlastingJOBJ.getArrangementer()) {
			leggTilArrangement(a);
			a.nullstillSalg();
		}
		for (Billett b : innlastingJOBJ.getBilletter()) {
			BILLETT.lagBillett(b.getArrangement(), b.getTelefonNummer(), b.getAntall());
		}

	}

}
