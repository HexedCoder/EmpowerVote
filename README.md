# EmpowerVote Project

## Capstone CMSC495
## Team Members

| Role                      | Name                |
|---------------------------|---------------------|
| Project Manager (PM)      | Johnson, Shakenia   |
| Test Director (TD)        | Barnes, Mahogany    |
| Software Designer (SD)    | Friedman, Marc      |
| Software Designer (SD)    | Hitchcox, Jacob     |
| RM / Technical Writer     | Bias, Ashley        |

## Overview
EmpowerVote is a comprehensive voting system application designed to provide a secure and user-friendly experience for voters, administrators, and system users. The project is developed in multiple phases:

- **Phase I (Client-Server):** Focuses on server-client architecture, including backend functionalities for user authentication, vote submission, and session management. This phase ensures the core infrastructure for secure and reliable voting is in place.
- **Phase II (GUI Implementation):** Adds graphical user interfaces (GUIs) for login, voting, and viewing results. This phase enhances the user experience by providing intuitive interfaces for both voters and admins.
- **Final Draft (Encryption):** Adds encryption mechanisms to ensure sensitive data, such as passwords and vote information, are securely handled throughout the system.

## Features
- **Phase I (Client-Server):**
   - **Backend Authentication:** Secure user login and session management.
   - **Vote Submission:** Users can cast votes for candidates, and the backend tracks votes securely.
   - **Data Persistence:** User data and vote data are stored persistently using file I/O.
   - **Admin Privileges:** Admins can view and manage voting data.

- **Phase II (GUI Implementation):**
   - **Login GUI:** User-friendly interface for logging in with username and password.
   - **Admin GUI:** Secure interface for viewing voting results, including a dashboard with voter statistics.
   - **User Voting GUI:** Intuitive interface for casting votes.

- **Final Draft (Encryption):**
   - **Password Hashing:** Secure hashing for user passwords.
   - **Encryption:** Ensures sensitive user and vote data is encrypted during transmission and storage.

- **Additional Features:**
   - **Multilingual Support:** Interface translation into English, Spanish, and Russian.
   - **Testing Framework:** Configured testing framework with unit tests for critical components.
   - **Documentation:** Comprehensive user and admin guides.

## Installation
1. Install Java Development Kit (JDK):
   - Download and install the latest version of JDK from the [Oracle website](https://www.oracle.com/java/technologies/javase-downloads.html).

2. Clone the repository:
   ```
   git clone https://github.com/yourusername/EmpowerVote.git
   cd EmpowerVote
   ```

## Project Structure
- **docs:** Contains the user guide and test guide.
- **src:** All code for the project.
- **test:** Contains the test cases.
- **resource:** Contains the user data and current votes.

## UML Diagram
![img.png](img.png)

## Sequence Diagram
![sequence.png](sequence.png)