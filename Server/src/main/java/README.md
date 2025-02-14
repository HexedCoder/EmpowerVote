# EmpowerVote Project Server

## Overview

EmpowerVote is an election management system that allows users to register, authenticate, and vote for candidates. The application supports user management, vote handling, and provides administrative capabilities to view and manage votes.

## Features

- **User Management:**
  - User authentication (both regular users and admins).
  - User registration with hashed password storage.
  - User login/logout functionality.
  - Users can be logged out individually or all at once.
  - Persistent login and voting status.

- **Vote Management:**
  - Users can vote for candidates.
  - Admins can view and manage vote counts, including displaying candidates' names and their vote totals.
  - Voting data is persistent across server shutdowns.
  - Admins can prevent voting if the user has already voted.

- **Data Persistence:**
  - User and vote data are loaded from and saved to `.tsv` files.
  - Backup and recovery mechanisms for data during server shutdown.

## Project Structure

- **HandleData.java**: Core class that handles all data-related operations, including user authentication, voting, and file I/O. It also manages server startup and shutdown processes, user and candidate maps, and login functionality.

- **User.java**: Defines the User model, including properties for username, password, user level, and voting status. Contains logic for user authentication and management.

- **Candidate.java**: Defines the Candidate model, including properties for candidate name, position, and vote count. Manages vote tracking and updates for candidates.

## Requirements

- Java 11 or higher
- File I/O permissions for reading and writing `.tsv` files.

## Setup

### 1. Build the project
You can build the project using a standard Java build tool or simply compile it using the `javac` command.

### 2. Running the Server

You need to have two `.tsv` files for the project to load:
- **User Data File** (`UserData.tsv`): Contains registered users with their credentials.
- **Vote Data File** (`VoteData.tsv`): Contains candidates and their positions.

Example format for **UserData.tsv**:
```tsv
user1 e6c3da5b206634d7f3f3586d747ffdb36b5c675757b380c6a5fe5c570c714349  user  false
```

Example format for **VoteData.tsv**:
```tsv
Sophia  Council Member  0
Candidate2  Position2 0
```