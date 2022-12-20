package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Moon;
import com.revature.utilities.ConnectionUtil;

public class MoonDao {
    
    public List<Moon> getAllMoons() throws SQLException {
		try(Connection connection = ConnectionUtil.createConnection()) {
            String sql = "Select * from moons";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            List<Moon> moons = new ArrayList<>();
            while(rs.next()) {
                Moon moon = new Moon();
                moon.setId(rs.getInt(1));
                moon.setName(rs.getString(2));
                moon.setMyPlanetId(rs.getInt(3));
                moons.add(moon);
            }
            return moons;
        }    
	}

	public Moon getMoonByName(String username, String moonName) {
		try(Connection connection = ConnectionUtil.createConnection()) {
            String sql = "Select * from moons where name = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, moonName);
            ResultSet rs = ps.executeQuery();
            rs.next();
            Moon moon = new Moon();
            moon.setId(rs.getInt("id"));
            moon.setName(rs.getString(2));
            moon.setMyPlanetId(rs.getInt(3));
            return moon;
        } catch(SQLException e) {
            System.out.println(e.getMessage());
            return new Moon();
		}
	}

	public Moon getMoonById(String username, int moonId) {
		try(Connection connection = ConnectionUtil.createConnection()) {
            String sql = "Select * from moons where id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, moonId);
            ResultSet rs = ps.executeQuery();
            rs.next();
            Moon moon = new Moon();
            moon.setId(rs.getInt("id"));
            moon.setName(rs.getString(2));
            moon.setMyPlanetId(rs.getInt(3));
            return moon;
        } catch(SQLException e) {
            System.out.println(e.getMessage());
            return new Moon();
		}
	}

	public Moon createMoon(String username, Moon m) {
		try(Connection connection = ConnectionUtil.createConnection()) {
			String sql = "insert into moons values (default,?,?)"; 
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, m.getName()); 
			String sqlTwo = "Select id from users where username = ?";
			PreparedStatement psTwo = connection.prepareStatement(sqlTwo);
            psTwo.setString(1, username);
            ResultSet rsTwo = psTwo.executeQuery();
            rsTwo.next();
            int ownerId = rsTwo.getInt("id");
            String sqlThree = "Select id from planets where ownerid = ?";
            PreparedStatement psThree = connection.prepareStatement(sqlThree);
            psThree.setInt(1, ownerId);
            ResultSet rsThree = psThree.executeQuery();
            rsThree.next();
			ps.setInt(2, rsThree.getInt("id")); 
			ps.execute(); 
			ResultSet rs = ps.getGeneratedKeys();
			Moon newMoon = new Moon();
			rs.next(); 
			int newId = rs.getInt("id");
			newMoon.setId(newId);
			newMoon.setName(m.getName());
			newMoon.setMyPlanetId(rsThree.getInt("id"));
			return newMoon;
		} catch (SQLException e) {
			System.out.println(e.getMessage()); 
			return new Moon();
		}
	}

	public void deleteMoonById(int moonId) {
		try(Connection connection = ConnectionUtil.createConnection()) {
            String sql = "Delete from moons where id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, moonId);
            int rowsAffected = ps.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
        } catch(SQLException e) {
            System.out.println(e.getMessage());// log spot to catch error
		}
	}

	public List<Moon> getMoonsFromPlanet(int planetId) throws SQLException {
		try(Connection connection = ConnectionUtil.createConnection()) {
            String sql = "Select * from moons where myplanetid = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, planetId);
            ResultSet rs = ps.executeQuery();
            List<Moon> moons = new ArrayList<>();
            while(rs.next()) {
                Moon moon = new Moon();
                moon.setId(rs.getInt(1));
                moon.setName(rs.getString(2));
                moon.setMyPlanetId(rs.getInt(3));
                moons.add(moon);
            }
            return moons;
        }  
	}
}
