package com.company.Controller;

import com.company.Model.Transaction;
import com.company.ServiceData.BlockchainData;
import com.company.ServiceData.WalletData;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.security.GeneralSecurityException;
import java.security.Signature;
import java.util.Base64;

public class AddNewTransactionController {

    @FXML
    private TextField toAddress;
    @FXML
    private TextField value;

    @FXML
    public void createNewTransaction() throws GeneralSecurityException {
        Base64.Decoder decoder = Base64.getDecoder();
        Signature signing = Signature.getInstance("SHA256withDSA");

        // Fix: Check if transaction ledger is empty before accessing
        Integer ledgerId;
        if (!BlockchainData.getInstance().getTransactionLedgerFX().isEmpty()) {
            ledgerId = BlockchainData.getInstance().getTransactionLedgerFX().get(0).getLedgerId();
        } else {
            // Handle empty ledger case - you might want to:
            // 1. Get the next ledger ID from the latest block + 1
            // 2. Start with a default value like 1
            // 3. Get it from the blockchain itself

            if (!BlockchainData.getInstance().getCurrentBlockChain().isEmpty()) {
                // Get the next ledger ID from the latest block
                ledgerId = BlockchainData.getInstance().getCurrentBlockChain().getLast().getLedgerId() + 1;
            } else {
                // If no blocks exist either, start with 1
                ledgerId = 1;
            }
        }

        byte[] sendB = decoder.decode(toAddress.getText());
        Transaction transaction = new Transaction(WalletData.getInstance().getWallet(), sendB, Integer.parseInt(value.getText()), ledgerId, signing);
        BlockchainData.getInstance().addTransaction(transaction, false);
        BlockchainData.getInstance().addTransactionState(transaction);
    }
}