package com.view;

import java.net.*;
import java.util.*;
import java.lang.*;
import java.time.format.*;
import java.sql.*;

import com.db.model.*;

import javafx.beans.value.*;
import javafx.collections.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;
import javafx.application.*;
import javafx.scene.control.Alert.*;
import javafx.scene.input.*;

public class SeatController implements Initializable
{
    @FXML
    private VBox vbox;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        vbox.getChildren().addAll(new ButtonBar("Cut"), new ButtonBar("Copy"), new ButtonBar("Paste"));
    }
}
