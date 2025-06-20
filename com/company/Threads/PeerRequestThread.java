package com.company.Threads;

import com.company.Model.Block;
import com.company.ServiceData.BlockchainData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;

public class PeerRequestThread extends Thread{

    private Socket socket;

    public PeerRequestThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream());
            LinkedList<Block> receivedBC = (LinkedList<Block>) objectInput.readObject();
            System.out.println("LedgerId = " + receivedBC.getLast().getLedgerId() + " Size = " + receivedBC.getLast().getTransactionLedger().size());
            objectOutput.writeObject(BlockchainData.getInstance().getBlockchainConsensus(receivedBC));
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
