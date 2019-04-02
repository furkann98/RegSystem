package org.groupId.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import org.groupId.models.Lokale;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable {

	//DATAFELT
	public Lokale LOKALE;
	public ObservableList<Lokale> lokalerListView;

	//DATAFELT FXML
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


	//INITIALIZE
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		//Default
		LOKALE = new Lokale();

		LOKALE.leggTilLokal(new Lokale("Lindeberg","Kino", 100));
		LOKALE.leggTilLokal(new Lokale("Trosterud","Teater", 150));
		LOKALE.leggTilLokal(new Lokale("Haugerud","Sal", 300));

		lokalerListView = lstViewLokal.getItems();

		lokalerListView.add(LOKALE.getArrayList().get(0));
		lokalerListView.add(LOKALE.getArrayList().get(1));
		lokalerListView.add(LOKALE.getArrayList().get(2));

		//lokalerListView = FXCollections.observableArrayList();







	}


	//KNAPPER OG METODER
	public void exitApplication(ActionEvent actionEvent) {
		Platform.exit();
	}

	public void btnSeLokaler(ActionEvent actionEvent) {  //Hovedside
	}

	public void btnLagArrangement(ActionEvent actionEvent) { //Hovedside
	}

	public void btnBillettsalg(ActionEvent actionEvent) { //Hovedside
	}

	public void btnFjernLokal(ActionEvent actionEvent) { //Lokale
		fjernLokal(lstViewLokal.getSelectionModel().getSelectedIndex());

		tomTextArea();
	}

	public void btnLeggTilLokal(ActionEvent actionEvent) { //Lokale
		visLokalleggTil();
	}

	public void btnFullfoorLokal(ActionEvent actionEvent) { //Lokale
		Lokale nyttLokal = new Lokale(txtLokalNavn.getText(),txtLokalType.getText(), 100);
		leggTilLokal(nyttLokal);

		skjulLokalleggTil();
	}

	public void txtFlowOnMouseClicked(MouseEvent arg0){


		try{
			txtFlowLokal.setText(lstViewLokal.getSelectionModel().getSelectedItem().toString());

		} catch (Exception e){
			System.out.println("fant exception");

		}


	}

	public void leggTilLokal(Lokale nyttLokal){
		LOKALE.leggTilLokal(nyttLokal);
		lokalerListView.add(nyttLokal);
	}

	public void fjernLokal(int indeks){
		LOKALE.fjernLokal(indeks);
		lokalerListView.remove(indeks);
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
