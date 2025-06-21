# Blockchain Demo Application

A sample blockchain application that demonstrates peer-to-peer cryptocurrency transactions. Multiple clients can run simultaneously on different machines and send coins to each other.

## About This Code

This code is based on the book **"Introducing Blockchain with Java: Program, Implement, and Extend Blockchains with Java"** by **Spiro Buzharovski**. I am following the tutorial from this book, but have made some modifications:

- Updated some libraries that were outdated in the original code
- Added improved error handling where the original implementation was lacking
- Made minor adjustments for better stability and user experience

The core blockchain concepts and implementation remain faithful to the book's teachings.

## Prerequisites

- Java 21 or higher
- JavaFX SDK 24.0.1
- SQLite JDBC driver (included)

## Setup Instructions

### 1. Download JavaFX SDK

1. Download JavaFX SDK 24.0.1 from [OpenJFX.io](https://openjfx.io/)
2. Extract the downloaded archive
3. Create a `libs` folder in your project root directory
4. Place the extracted JavaFX SDK folder inside the `libs` directory
   - The path should be: `libs/javafx-sdk-24.0.1/`

### 2. Project Structure

Your project should have the following structure:
```
blockchain/
├── DatabaseTest.class
├── blockchain.iml
├── com/
│   └── company/
│       ├── Coin.java
│       ├── Controller/
│       │   ├── AddNewTransactionController.java
│       │   └── MainWindowController.java
│       ├── Model/
│       │   ├── Block.java
│       │   ├── Transaction.java
│       │   └── Wallet.java
│       ├── ServiceData/
│       │   ├── BlockchainData.java
│       │   └── WalletData.java
│       ├── Threads/
│       │   ├── MiningThread.java
│       │   ├── PeerClient.java
│       │   ├── PeerRequestThread.java
│       │   ├── PeerServer.java
│       │   └── UI.java
│       └── View/
│           ├── AddNewTransactionWindow.fxml
│           └── MainWindow.fxml
├── db/
│   ├── blockchain.db
│   └── wallet.db
├── libs/
│   ├── javafx-sdk-24.0.1/
│   │   └── lib/
│   ├── openjfx-24.0.1_windows-x64_bin-sdk.zip
│   └── sqlite-jdbc-3.50.1.0.jar
└── README.md
```

### 3. Port Configuration

**Default Configuration:**
- First machine/client: Port **6001** (default in Coin.java, line 29)
- Additional machines: Port **6002**, **6003**, etc.

**To run multiple clients:**

#### Step 1: Create Separate Folders for Each Client
For each additional client, you need to create a duplicate of the entire project folder:
- Client 1: `blockchain/` (original folder)
- Client 2: `blockchain-client2/` (duplicate folder)
- Client 3: `blockchain-client3/` (duplicate folder)
- And so on...

#### Step 2: Configure Server Port (Coin.java)
In each client folder, open `com/company/Coin.java` and modify line 29:
```java
new PeerServer(6001).start();
```
Change the port number for each client:
- Client 1: `new PeerServer(6001).start();`
- Client 2: `new PeerServer(6002).start();`
- Client 3: `new PeerServer(6003).start;`
- And so on...

#### Step 3: Configure Client Connections (PeerClient.java)
In each client folder, open `com/company/Threads/PeerClient.java` and find the constructor:
```java
public PeerClient() {
    this.queue.add(6001);
    this.queue.add(6002);
}
```
Update to include all ports where other clients are running (this should be THE SAME in all client folders):
```java
public PeerClient() {
    this.queue.add(6001);
    this.queue.add(6002);
    this.queue.add(6003);
    // Add more ports as needed for additional clients
}
```

**Important:** 
- Each client needs its own project folder with unique server port configuration
- All clients must have the same port list in PeerClient.java for proper peer-to-peer communication

**Note:** This application runs on localhost for safety constraints.

## Compilation Instructions

### Step 1: Compile Controller Classes
```bash
javac --module-path .\libs\javafx-sdk-24.0.1\lib --add-modules javafx.controls,javafx.fxml -cp ".;libs/sqlite-jdbc-3.50.1.0.jar" com/company/Controller/*.java
```

### Step 2: Compile Main Application
```bash
javac .\com\company\*.java --module-path .\libs\javafx-sdk-24.0.1\lib --add-modules javafx.controls,javafx.fxml
```

## Running the Application

```bash
java --module-path .\libs\javafx-sdk-24.0.1\lib --add-modules javafx.controls,javafx.fxml --enable-native-access=javafx.graphics --class-path ".;libs/sqlite-jdbc-3.50.1.0.jar" com.company.Coin
```

## Multi-Client Setup Example

### For 3 Clients Setup:

#### Client 1 (blockchain/ folder):
- **Coin.java line 29:** `new PeerServer(6001).start();`
- **PeerClient.java constructor:**
  ```java
  public PeerClient() {
      this.queue.add(6001);
      this.queue.add(6002);
      this.queue.add(6003);
  }
  ```

#### Client 2 (blockchain-client2/ folder):
- **Coin.java line 29:** `new PeerServer(6002).start();`
- **PeerClient.java constructor:** (same as all clients)
  ```java
  public PeerClient() {
      this.queue.add(6001);
      this.queue.add(6002);
      this.queue.add(6003);
  }
  ```

#### Client 3 (blockchain-client3/ folder):
- **Coin.java line 29:** `new PeerServer(6003).start();`
- **PeerClient.java constructor:** (same as all clients)
  ```java
  public PeerClient() {
      this.queue.add(6001);
      this.queue.add(6002);
      this.queue.add(6003);
  }
  ```

### Setup Process:
1. **Create folders**: Duplicate the original blockchain folder for each additional client
2. **Configure ports**: Edit both Coin.java and PeerClient.java in each folder
3. **Compile**: Recompile each client folder separately
4. **Run**: Start each client from its respective folder

## Features

- **Peer-to-peer blockchain network**: Multiple clients can connect and communicate
- **Cryptocurrency transactions**: Send and receive coins between different clients
- **Real-time synchronization**: Blockchain state syncs across all connected peers
- **JavaFX GUI**: User-friendly interface for managing transactions
- **SQLite database**: Local storage for blockchain data

## Troubleshooting

### Network Configuration
- This application runs on **localhost only** for safety constraints
- Each client instance must use a unique port number on the same machine
- All clients will communicate through localhost (127.0.0.1)
- Make sure all clients have the same port list in PeerClient.java for proper communication

### Common Issues
- **Clients not connecting**: Verify that all ports are added to the PeerClient.java queue
- **Port conflicts**: Ensure each client uses a unique port number
- **Database errors**: Check that the db/ directory exists and is writable

## Notes

- This is a demonstration blockchain application for educational purposes
- The application creates a local SQLite database to store blockchain data  
- Each client maintains its own copy of the blockchain and syncs with peers
- **Security Notice:** Application is designed to run on localhost only
- **Important:** All clients must be recompiled after modifying port configurations
- Make sure to update both Coin.java and PeerClient.java for each client setup
