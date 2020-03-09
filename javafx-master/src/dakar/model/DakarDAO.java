package dakar.model;

import java.sql.*;
import java.util.ArrayList;

public class DakarDAO {
    final static String URL = "jdbc:mysql://localhost:3306/db";
    public String add(Dakar dakar){
        String query = "insert into dakar (team_name, name_surname, sponsors, racing_cars, members, user_id)" +
                "values (?,?,?,?,?,?)";
        try {
            Connection connection = DriverManager.getConnection(URL, "root", "");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, dakar.getTeamName());
            preparedStatement.setString(2, dakar.getNameSurname());
            preparedStatement.setString(3, dakar.getSponsors());
            preparedStatement.setString(4, dakar.getRacingCars());
            preparedStatement.setInt(5, dakar.getMembers());
            preparedStatement.setInt(6, dakar.getUserid());
            preparedStatement.executeUpdate();

            return "Successfully created new entry";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Failure creating new entry";
        }
    }

    public ResultSet searchByTeamName(String teamName, User user){
        String query2 = "";
        if (user.isAdmin()) { // display all results- it's admin after all ;)
            if (teamName.equals("")) {//Admin didn't entered any team_name - displaying all entries
                query2 = "SELECT * FROM dakar";
            } else {// Admin entered team_name displaying specific entries
                query2 = "SELECT * FROM dakar WHERE team_name LIKE '" + teamName + "'";
            }
        } else { // display only specific user results
            if (teamName.equals("")) {//User didn't entered any team_name - displaying all entries created by him
                query2 = "SELECT * FROM dakar WHERE user_id = '" + user.getId() + "'";
            } else {// User entered team_name displaying specific entries created by him
                query2 = "SELECT * FROM dakar WHERE user_id = '" + user.getId() + "' AND team_name LIKE '" + teamName + "'";
            }
        }

        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection(URL, "root", "");
            preparedStatement = connection.prepareStatement(query2);
            resultSet = preparedStatement.executeQuery(query2);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public void editById(Dakar dakar){
        String query = "update dakar set team_name=?, name_surname=?, sponsors=?, racing_cars=?, members=? " +
                " where id=?";
        try {
            Connection connection = DriverManager.getConnection(URL, "root", "");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, dakar.getTeamName());
            preparedStatement.setString(2, dakar.getNameSurname());
            preparedStatement.setString(3, dakar.getSponsors());
            preparedStatement.setString(4, dakar.getRacingCars());
            preparedStatement.setInt(5, dakar.getMembers());
            preparedStatement.setInt(6, dakar.getId());
            preparedStatement.executeUpdate();

            System.out.println("Pavyko paredaguoti esama irasa");
        } catch (SQLException e) {
            System.out.println("Ivyko klaida redaguojant esama irasa");
            e.printStackTrace();
        }
    }
    public void deleteById(int id){
        String query = "delete from dakar where id=?";
        try {
            Connection connection = DriverManager.getConnection(URL, "root", "");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            System.out.println("Pavyko istrinti esama irasa");
        } catch (SQLException e) {
            System.out.println("Ivyko klaida istrinant esama irasa");
            e.printStackTrace();
        }
    }
}
