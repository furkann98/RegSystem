package org.groupId.controllers;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import org.groupId.models.Lokale;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

	//DATAFELT
	public Lokale LOKALE;
	public ObservableList<Lokale> lokalerObservableList;
	public ObservableList<Lokale> arrangementObservableList;
	public Alert errorAlert;

	//DATAFELT FXML
	@FXML
	public TabPane tabPane;

	@FXML
	public Tab tabPaneLokale;

	@FXML
	public Tab tabPaneArrangement;

	@FXML
	public Tab tabPaneBillettsalg;

	@FXML
	public ListView<Lokale> lstViewLokal;

	@FXML
	public TextField txtLokalNavn;

	@FXML
	public TextField txtLokalType;

	@FXML
	public TextField txtLokalAntallPlasser;

	@FXML
	public TextArea txtFlowLokal;

	@FXML
	public TextArea txtFlowLokalOverskrift;

	@FXML
	public HBox hBoxNyttLokale;

	@FXML
	public Button btnFullfoorLokalId;

	@FXML
	public Button btnFjernLokal;

	@FXML
	public ComboBox<Lokale> cbLokal;


	//INITIALIZE
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {

		LOKALE = new Lokale();

		lokalerObservableList = lstViewLokal.getItems();
		arrangementObservableList = cbLokal.getItems();

		leggTilLokal(new Lokale("Lindeberg","Kino", 100));
		leggTilLokal(new Lokale("Trosterud","Teater", 150));
		leggTilLokal(new Lokale("Haugerud","Sal", 300));



	}


	//KNAPPER
	//KNAPPER - HOVEDSIDE
	public void exitApplication(ActionEvent actionEvent) {
		Platform.exit();
	}

	public void btnSeLokaler(ActionEvent actionEvent) {  //Hovedside
		System.out.println("hehehehhe");
		tabPane.getSelectionModel().select(tabPaneLokale);

	}

	public void btnLagArrangement(ActionEvent actionEvent) { //Hovedside
		tabPane.getSelectionModel().select(tabPaneArrangement);
	}

	public void btnBillettsalg(ActionEvent actionEvent) { //Hovedside
		tabPane.getSelectionModel().select(tabPaneBillettsalg);
	}

	public void btnFjernLokal(ActionEvent actionEvent) { //Lokale
		fjernLokal(lstViewLokal.getSelectionModel().getSelectedIndex());

		if(LOKALE.isEmpty()){
			btnFjernLokal.setDisable(true);
		}

		tomTextArea();
	}




	//KNAPPER - LOKALE
	public void btnLeggTilLokal(ActionEvent actionEvent) { //Lokale
		visLokalleggTil();
	}

	public void btnFullfoorLokal(ActionEvent actionEvent) { //Lokale
		if(LOKALE.isEmpty()){
			btnFjernLokal.setDisable(false);
		}
		Lokale nyttLokal = new Lokale(txtLokalNavn.getText(),txtLokalType.getText(), Integer.parseInt(txtLokalAntallPlasser.getText()));
		leggTilLokal(nyttLokal);

		skjulLokalleggTil();

		txtLokalNavn.setText("");
		txtLokalType.setText("");
		txtLokalAntallPlasser.setText("");

	}

	public void txtFlowOnMouseClicked(MouseEvent arg0){

		try{
			Lokale info = lstViewLokal.getSelectionModel().getSelectedItem();
			txtFlowLokalOverskrift.setText(info.getNavn());
			txtFlowLokal.setText("Type: " + info.getType() + "\n" + "Antall Plasser: " + info.getAntallPlasser());

		} catch (NullPointerException e){
			System.out.println("Må velge et lokal for å se oversikt.   ----- > " + e.getMessage());
			feilMelding("Det finnes ingen lokale, dermed er det ikke mulig å se oversikten. Vennligst lag et lokalet før du klikker videre. :)" + "\n" + "TAMAM TAMAM");
		}
	}



	//METODER

	public void leggTilLokal(Lokale nyttLokal){
		LOKALE.leggTilLokal(nyttLokal);
		lokalerObservableList.add(nyttLokal);
		arrangementObservableList.add(nyttLokal);
	}

	public void fjernLokal(int indeks){
		LOKALE.fjernLokal(indeks);
		lokalerObservableList.remove(indeks);
		arrangementObservableList.remove(indeks);
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
	}

	public void feilMelding(String melding){
		errorAlert = new Alert(Alert.AlertType.ERROR);
		errorAlert.setHeaderText("Feilmelding!");
		errorAlert.setContentText(melding);
		errorAlert.showAndWait();
	}

}
