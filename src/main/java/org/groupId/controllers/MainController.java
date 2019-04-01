package org.groupId.controllers;

import javafx.application.Platform;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.groupId.models.Lokale;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

	//DATAFELT
	Lokale lokale;
	ObservableList<String> items;

	@FXML
	public TextField txtLokalNavn;

	@FXML
	public ListView<String> lstViewLokal;

	//INITIALIZE
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		items = lstViewLokal.getItems();
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
		items.remove(lstViewLokal.getSelectionModel().getSelectedIndex());
	}

	public void btnLeggTilLokal(ActionEvent actionEvent) { //Lokale


	}

	public void btnFullfoorLokal(ActionEvent actionEvent) { //Lokale
		lokale = new Lokale(txtLokalNavn.getText(),"type",100);
		items.add(lokale.getNavn());

	}


}
