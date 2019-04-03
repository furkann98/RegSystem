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


	//DATAFELT FXML
	@FXML
	public TabPane tabPaneId;

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
	public HBox hBoxNyttLokale;

	@FXML
	public Button btnFullfoorLokalId;

	@FXML
	public ChoiceBox<Lokale> cbLokal;

	@FXML
	public Button btnFjernLokal;


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
	public void exitApplication(ActionEvent actionEvent) {
		Platform.exit();
	}

	public void btnSeLokaler(ActionEvent actionEvent) {  //Hovedside
		tabPaneId.getSelectionModel().select(1);

	}

	public void btnLagArrangement(ActionEvent actionEvent) { //Hovedside
	}

	public void btnBillettsalg(ActionEvent actionEvent) { //Hovedside
	}

	public void btnFjernLokal(ActionEvent actionEvent) { //Lokale
		fjernLokal(lstViewLokal.getSelectionModel().getSelectedIndex());

		if(LOKALE.isEmpty()){
			System.out.println("lengde 0");
			btnFjernLokal.setDisable(true);
		}

		tomTextArea();
	}

	public void btnLeggTilLokal(ActionEvent actionEvent) { //Lokale
		visLokalleggTil();
	}

	public void btnFullfoorLokal(ActionEvent actionEvent) { //Lokale
		Lokale nyttLokal = new Lokale(txtLokalNavn.getText(),txtLokalType.getText(), Integer.parseInt(txtLokalAntallPlasser.getText()));
		leggTilLokal(nyttLokal);

		skjulLokalleggTil();
	}

	public void txtFlowOnMouseClicked(MouseEvent arg0){

		try{
			txtFlowLokal.setText(lstViewLokal.getSelectionModel().getSelectedItem().toString());

		} catch (NullPointerException e){
			System.out.println("Må velge et lokal for å se oversikt.   ----- > " + e.getMessage());

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

}
