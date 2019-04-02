package org.groupId.controllers;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.TextFlow;
import org.groupId.models.Lokale;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

	//DATAFELT
	public Lokale LOKALE;
	public ObservableList<Lokale> lstViewList;


	@FXML
	public ListView<Lokale> lstViewLokal;

	@FXML
	public TextField txtLokalNavn;

	@FXML
	public TextField txtLokalType;

	@FXML
	public TextField txtLokalAntallPlasser;

	@FXML
	public TextFlow txtFlowLokal;


	//INITIALIZE
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		lstViewList = lstViewLokal.getItems();
		//lstViewList.addAll(LOKALE.getArrayList());
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
		LOKALE.fjernLokal(lstViewLokal.getSelectionModel().getSelectedIndex());
		lstViewList.remove(lstViewLokal.getSelectionModel().getSelectedIndex());
	}

	public void btnLeggTilLokal(ActionEvent actionEvent) { //Lokale

	}

	public void btnFullfoorLokal(ActionEvent actionEvent) { //Lokale
		Lokale nyttLokale = new Lokale(txtLokalNavn.getText(),txtLokalType.getText(), 100);
		//LOKALE.leggTilLokal(nyttLokale);
		lstViewList.add(nyttLokale);
	}

	public void 


}
