# EmpowerVote User Guide

## Introduction
EmpowerVote is a voting application designed to manage user registration, authentication, and secure voting operations. This guide outlines how to build and run the application.

---

## Prerequisites

To run EmpowerVote, you need the following:

1. **Java Development Kit (JDK)**: JDK 22 or higher is required.
2. **Terminal or Command Line Interface**: To execute the application.
3. **Resource Files**:
    - `UserData.tsv`: Contains user data.
    - `VoteStatus.tsv`: Contains voting status data.

Ensure that these files are in the `resources/` directory relative to the program's location.

---

## How to Build and Run the Application

### Step 1: Verify JDK Installation

Ensure you have JDK 22 or higher installed. You can check the version by running:

```
java -version
```
If the version is lower than 22, download and install JDK 22 from the [official site](https://jdk.java.net/archive/).

### Step 2: Build the Application

The code is built with the IDE and configured to generate artifacts. Upon building, the artifacts are placed in:

- `out/artifacts/Client/Client.jar`
- `out/artifacts/Server/Server.jar`

### Step 3: Run the Application

Navigate to the directory containing the artifact and execute:

For the server:

```bash
cd out/artifacts/Server/ 
java -jar Server.jar <port> <ip>
```

For the client:

```bash
cd out/artifacts/Client/ 
java -jar Client.jar <port> <ip>
```


If no arguments are provided, the client and server default to `localhost` on port `12345`.

---

## Application Flow

Once the program starts, you will interact with a text-based menu that offers several options:

### Initial Menu

```
Enter command (Login, Register, Exit):
```

#### Option 1: Login
1. Enter your username and password.
2. Based on your credentials, you will either:
    - Access **User Menu**.
    - Access **Admin Menu**.
3. If login fails, you will receive an appropriate error message.

#### Option 2: Register User
1. Enter a username and password for the new user.
2. The system will confirm if the registration was successful or if the user already exists.

#### Option 3: Exit
The program disconnects from the server and terminates.

---

## User Menu

Once logged in as a user, you will see the following options:

1. **Vote for Candidates**:
    - Candidates are displayed, grouped by position.
    - Enter the candidate's name to vote.
    - The server confirms your vote.
2. **Logout**:
    - Ends the user session and returns to the main menu.

---

## Admin Menu

If you log in as an admin, you will see the following options:

Admin Menu: 

```
1: **View Votes**

2: Logout 

3. Logout All

4. Exit
```

Enter option:


1. **View Voting Statistics**:
    - Displays the current vote tally.
2. **Logout**:
    - Ends the admin session and returns to the main menu.
3. **Logout All**
    - Logout all logged in users.
4. **Shutdown**
    - Shut down the server.

---

## Notes and Troubleshooting

- **Unsupported Java Version**:
  If you encounter the below error:
```
java.lang.UnsupportedClassVersionError: ... has been compiled by a more recent version of the Java Runtime
```

Ensure you are running the program with JDK 22 or higher.

- **Resource Files**:

Ensure that `UserData.tsv` and `VoteStatus.tsv` are present in the `resources/` folder of the Server and properly formatted.
