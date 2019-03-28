package org.groupId.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;

public class MainController {
	public void exitApplication(ActionEvent actionEvent) {
		Platform.exit();
	}
}
