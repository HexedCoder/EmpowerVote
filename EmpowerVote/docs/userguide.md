# EmpowerVote User Guide

## Introduction
EmpowerVote is a voting application designed to manage user registration, authentication, and secure voting operations. This guide outlines how to run the binary and interact with the program.

---

## Prerequisites

To run EmpowerVote, you need the following:

1. **Java Development Kit (JDK)**: JDK 22 or higher is required.
2. **Terminal or Command Line Interface**: To execute the application.
3. **Resource Files**:
    - `UserData.tsv`: Contains user data.
    - `VoteStatus.tsv`: Contains voting status data.

Ensure that these files are in the correct `resource` directory relative to the program's location.

---

## How to Run the Application

### Step 1: Verify JDK Installation

Ensure you have JDK 22 or higher installed. You can check the version by running:
```
java -version
```
If the version is lower than 22, download and install JDK 22 from the [official site](https://jdk.java.net/22/).

### Step 2: Compile the Program (Optional)
If you have the source code and need to compile it:

[//]: # (Add compilation instructions)
This generates the required binary files in the `out` directory.

### Step 3: Run the Program
[//]: # (Add run instructions)


---

## Application Flow

Once the program starts, you will interact with a text-based GUI that offers several options:

### Initial GUI
[//]: # (Add GUI Screenshot)
```
Welcome to EmpowerVote!

Select an option:
1: Login
2: Register User
3: Exit
```

#### Option 1: Login
1. Enter your username and password.
2. Based on your credentials, you will either:
    - Access **User GUI**.
    - Access **Admin GUI**.
3. If login fails, you will receive an appropriate error message.

#### Option 2: Register User
1. Enter a username and password for the new user.
2. The system will confirm if the registration was successful or if the user already exists. (TODO: Add screenshots for success and failure)

#### Option 3: Exit
The program saves all data and shuts down gracefully.

---

## User GUI

If you log in as a regular user, you will access the User GUI. The following features are available:

[//]: # (Add GUI Screenshot)
1. **Vote for Candidates**:
    - Candidates are displayed, grouped by position.
    - Use radio buttons to select a candidate for each position.

2. **Logout**:
    - Once you have voted, you can log out to end your session.

---

## Admin GUI

If you log in as an admin, you will access the Admin GUI. The following features are available:

[//]: # (Add GUI Screenshot)
1. **View Voting Statistics**:
    - See the number of votes for each candidate.
    - Candidates are displayed, grouped by position.

2. **Register New Admin**:
    - Add a new admin user by providing a username and password.

3. **Logout All Users**:
    - Log out all active users.

---

## Notes and Troubleshooting

- **Unsupported Java Version**:
  If you encounter an error like:
  ```
  java.lang.UnsupportedClassVersionError: ... has been compiled by a more recent version of the Java Runtime
  ```
  Ensure you are running the program with JDK 22 or higher.

- **Recompiling for Compatibility**:
  If you want the program to run on JDK 21, recompile with the following command:
  
[//]: # (Provide recompilation command)

- **Resource Files**:
  Ensure that `UserData.tsv` and `VoteStatus.tsv` are present in the `resource` folder and properly formatted.

---

## Additional Information

For more details or assistance, contact the program administrator or refer to the source code documentation.
