# EmpowerVote Project Server

## Overview

EmpowerVote is an election management system that allows users to register, authenticate, and vote for candidates. The application supports user management, vote handling, and provides administrative capabilities to view and manage votes.

## Features

- **User Management:**
    - User authentication (both regular users and admins).
    - User registration with hashed password storage.
    - User login/logout functionality.

- **Vote Management:**
    - Users can vote for candidates.
    - Admins can view and manage vote counts.
    - Vote data is persistent across server shutdowns.

- **Data Persistence:**
    - User and vote data are loaded from and saved to `.tsv` files.
    - Backup and recovery mechanisms for data during server shutdown.

## Project Structure

- **HandleData.java**: Core class that handles all data-related operations, including user authentication, voting, and file I/O.
- **User.java**: Defines the User model, including properties for username, password, user level, and voting status.
- **Candidate.java**: Defines the Candidate model, including properties for candidate name, position, and vote count.

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
user1	e6c3da5b206634d7f3f3586d747ffdb36b5c675757b380c6a5fe5c570c714349	user	false

Example format for **VoteData.tsv**:
Sophia	Council Member	0