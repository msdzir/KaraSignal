/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.finitx.karasignal.DBAPI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.json.simple.JSONObject;
import com.finitx.karasignal.constant.Queries;
import com.finitx.karasignal.constant.LogConstant;

import java.util.ArrayList;

/**
 * Comments of this class has been wrote on implemented interface 
 * @author alireza
 */
public class UserAssetsSbuscribedDBAPI implements IUserAssetSubscriptionDBAPI {

    private final String url;
    private final String username;
    private final String password;

    public UserAssetsSbuscribedDBAPI(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public ArrayList<JSONObject> getAllAssetsDescription() {
        ArrayList<JSONObject> descriptionsList = null;
        return descriptionsList;
    }

    @Override
    public ArrayList<JSONObject> getAllMarketsDesctiption() {
        ArrayList<JSONObject> descriptionsList = null;
//        String message = this.queryExcecution("select * from markets;");
        return descriptionsList;
    }

    @Override
    public ArrayList<JSONObject> getAllAssetsInfo() {
        ArrayList<JSONObject> subscriptionList = new ArrayList<>();

        try (Connection dbconnect = DriverManager.getConnection(url, username, password)) {
            String query = Queries.getAllAssetsInfo();
            System.out.println("->" + query);
            Statement queryStatement = dbconnect.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = queryStatement.executeQuery(query);

            while (rs.next()) {
                JSONObject jsonTemp = new JSONObject();
                jsonTemp.put("AssetID", rs.getString(1));
                jsonTemp.put("AssetName", rs.getString(2));
                jsonTemp.put("AssetDescription", rs.getString(4));
                jsonTemp.put("MarketID", rs.getString(3));
                jsonTemp.put("MarketName", rs.getString(6));
                jsonTemp.put("MarketDescription", rs.getString(7));
                subscriptionList.add(jsonTemp);
            }

            dbconnect.close();

        } catch (SQLException e) {
            subscriptionList.add((JSONObject) (new JSONObject()).put("Errore", "SQL Exception"));
            System.out.println("SQL Exception :\n" + e.getMessage());
        } catch (Exception e) {
            subscriptionList.add((JSONObject) (new JSONObject()).put("Errore", "Something went wrong"));
            System.out.println("Exception :\n" + e.getMessage());
        }
        return subscriptionList;
    }
    
    @Override
    public ArrayList<JSONObject> getUsersSubscriptionList(String userID) {
        ArrayList<JSONObject> subscriptionList = new ArrayList<>();

        try (Connection dbconnect = DriverManager.getConnection(url, username, password)) {
            String query = Queries.getUserSubscriptionList(userID);
            System.out.println("->" + query);
            Statement queryStatement = dbconnect.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = queryStatement.executeQuery(query);

            while (rs.next()) {
                JSONObject jsonTemp = new JSONObject();
                jsonTemp.put("UserID", rs.getString(1));
                jsonTemp.put("AssetID", rs.getString(2));
                jsonTemp.put("UserSavedName", rs.getString(3));
                jsonTemp.put("TargetPrice", rs.getString(4));
                subscriptionList.add(jsonTemp);
            }

            dbconnect.close();

        } catch (SQLException e) {
            subscriptionList.add((JSONObject) (new JSONObject()).put("Errore", "SQL Exception"));
            System.out.println("SQL Exception :\n" + e.getMessage());
        } catch (Exception e) {
            subscriptionList.add((JSONObject) (new JSONObject()).put("Errore", "Something went wrong"));
            System.out.println("Exception :\n" + e.getMessage());
        }
        return subscriptionList;
    }

    @Override
    public String addNewSignalSubscription(String userID, String assetID, String userSavedName , int targetNumber) {
        String message = "";

        try (Connection dbconnect = DriverManager.getConnection(url, username, password)) {
            String query = Queries.insertNewSubscriptionToUserList(userID, assetID, userSavedName , targetNumber);
            System.out.println("->" + query);
            Statement queryStatement = dbconnect.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            int rs = queryStatement.executeUpdate(query);

            message = LogConstant.signalAdded;

            dbconnect.close();

        } catch (SQLException e) {
            message = "Some problem accured while add new signal\r\n";
            message += "SQLException in addNewSignalSubscription\r\n";
            message += e.getMessage();
            System.out.println("SQL Exception :\n" + e.getMessage());
        } catch (Exception e) {
            message = "Some problem accured while add new signal\n";
            message += "Exception in addNewSignalSubscription";
            message += e.getMessage();
            System.out.println("Exception :\n" + e.getMessage());
        }

        return message;
    }

    @Override
    public String editSubscribedSignal(String userID, String assetID, String userSavedName , int targetPrice) {
        String message = "";

        try (Connection dbconnect = DriverManager.getConnection(url, username, password)) {
            String query = Queries.updateSubscriptionValues(userSavedName, userID, assetID , targetPrice);
            System.out.println("->" + query);
            Statement queryStatement = dbconnect.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            int rs = queryStatement.executeUpdate(query);

            message = LogConstant.signalEdited;

            dbconnect.close();

        } catch (SQLException e) {
            message = "Some problem accured while editting signal\r\n";
            message += "SQLException in addNewSignalSubscription\r\n";
            message += e.getMessage();
            System.out.println("SQL Exception :\n" + e.getMessage());
        } catch (Exception e) {
            message = "Some problem accured while editting signal\n";
            message += "Exception in addNewSignalSubscription";
            message += e.getMessage();
            System.out.println("Exception :\n" + e.getMessage());
        }
        return message;
    }

    @Override
    public String deleteSubscribedSignal(String userID, String assetID) {
        String message = "";

        try (Connection dbconnect = DriverManager.getConnection(url, username, password)) {
            String query = Queries.deleteSubscriptionFromUserList(userID, assetID);
            System.out.println("->" + query);
            Statement queryStatement = dbconnect.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            int rs = queryStatement.executeUpdate(query);

            message = LogConstant.signalDeleted;

            dbconnect.close();

        } catch (SQLException e) {
            message = "Some problem accured while deletting signal\r\n";
            message += "SQLException in deleteSubscribedSignal\r\n";
            message += e.getMessage();
            System.out.println("SQL Exception :\n" + e.getMessage());
        } catch (Exception e) {
            message = "Some problem accured while deleting new signal\n";
            message += "Exception in deleteSubscribedSignal";
            message += e.getMessage();
            System.out.println("Exception :\n" + e.getMessage());
        }

        return message;
    }
}
