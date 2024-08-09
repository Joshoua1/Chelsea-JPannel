# Chelsea FC App

This is a Java-based desktop application designed for Chelsea Football Club fans. The app allows users to register, log in, and access various features such as viewing fixtures, tracking goals, checking match results, and viewing Chelsea's opponents.

## Features

- **Login and Registration:** Users can register with their details and log in to access the main menu.
  
- **Personal Information:** After registration, users can update their username, date of birth, and gender.

- **Main Menu:** Access to different functionalities:
  - **Fixtures:** View Chelsea's UEFA Champions League 2020-21 fixtures.
  - **Track Goals:** View goals scored by Chelsea in the UEFA Champions League.
  - **Match Results:** View the match results of Chelsea in the UEFA Champions League.
  - **Opponents:** View a list of Chelsea's opponents.

## Technologies Used

- **Java:** Core programming language used for building the application.
- **Swing:** Java's GUI toolkit for building the user interface.
- **JDBC:** Java Database Connectivity for interacting with the MySQL database.
- **MySQL:** Database used to store user details, fixtures, goals, match results, and opponents.

## Database Setup

To run this application, you'll need to have a MySQL database set up with the following tables:

- **users:** Stores user registration and login details.
- **chelsea_ucl_2020_21:** Stores Chelsea's UEFA Champions League 2020-21 fixtures.
- **chelsea_ucl_goals:** Stores details about the goals scored by Chelsea.
- **chelsea_champions_league_matches:** Stores the match results of Chelsea.
- **chelsea_opponents:** Stores a list of Chelsea's opponents.

### Sample users Table Structure:

```sql
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) NOT NULL,
  `surname` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(50) NOT NULL,
  `username` varchar(50),
  `dob` date,
  `gender` varchar(10),
  PRIMARY KEY (`id`)
);
 ```
## How to Run

1. **Clone the repository:**

   ```bash
   git clone <repository-url>


2. **Set up the database:**
Install MySQL and create a database named chelsea_fc.
Create the necessary tables using the provided SQL scripts.

3. **Update Database Credentials:**
Open the Java source code.
Replace the database connection credentials in the code with your own MySQL credentials.
4. **Compile and Run the Application:**
Use an IDE like IntelliJ IDEA to open the project.
Compile and run the ChelseaFCApp class.

Contributing
If you'd like to contribute, please fork the repository and use a feature branch. Pull requests are warmly welcome.
Contact
For any questions or inquiries, please contact [Your Name] at [Your Email Address].
text
