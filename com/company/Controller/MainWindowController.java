package com.company.Controller;

import com.company.Model.Transaction;
import com.company.ServiceData.BlockchainData;
import com.company.ServiceData.WalletData;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

public class MainWindowController {
    // This is read-only UI table.
    @FXML
    public TableView<Transaction> tableView = new TableView<>();
    @FXML
    private TableColumn<Transaction, String> from;
    @FXML
    private TableColumn<Transaction, String> to;
    @FXML
    private TableColumn<Transaction, Integer> value;
    @FXML
    private TableColumn<Transaction, String> timestamp;
    @FXML
    private TableColumn<Transaction, String> signature;
    @FXML
    private BorderPane borderPane;
    @FXML
    private TextField coins;
    @FXML
    private TextArea publicKey;

    public void initialize() {
        Base64.Encoder encoder = Base64.getEncoder();
        from.setCellValueFactory(
            new PropertyValueFactory<>("fromFX"));
        to.setCellValueFactory(
            new PropertyValueFactory<>("toFX"));
        value.setCellValueFactory(
            new PropertyValueFactory<>("value"));
        signature.setCellValueFactory(
            new PropertyValueFactory<>("signatureFX"));
        timestamp.setCellValueFactory(
            new PropertyValueFactory<>("timestamp"));
        coins.setText(BlockchainData.getInstance().getWalletBalanceFX());
        publicKey.setText(encoder.encodeToString(WalletData.getInstance().getWallet().getPublicKey().getEncoded()));
        tableView.setItems(BlockchainData.getInstance().getTransactionLedgerFX());
        tableView.getSelectionModel().select(0);
    }

    @FXML
    public void toNewTransactionController() {
        Dialog<ButtonType> newTransactionController = new Dialog<>();
        newTransactionController.initOwner(borderPane.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../View/AddNewTransactionWindow.fxml"));
        try {
            newTransactionController.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println("Cant load dialog");
            e.printStackTrace();
        }
        newTransactionController.getDialogPane().getButtonTypes().add(ButtonType.FINISH);
        Optional<ButtonType> result = newTransactionController.showAndWait();
        if (result.isPresent()) {
            tableView.setItems(BlockchainData.getInstance().getTransactionLedgerFX());
            coins.setText(BlockchainData.getInstance().getWalletBalanceFX());
        }
    }

    @FXML
    public void refresh() {
        tableView.setItems(BlockchainData.getInstance().getTransactionLedgerFX());
        tableView.getSelectionModel().select(0);
        coins.setText(BlockchainData.getInstance().getWalletBalanceFX());
    }

    @FXML
    public void handleExit() {
        BlockchainData.getInstance().setExit(true);
        Platform.exit();
    }
}
