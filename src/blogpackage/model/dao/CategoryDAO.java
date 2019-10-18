package com.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.controller.CategoryBean;

public class CategoryDAO {
    
	public CategoryDAO() {
	}
	
	/*this method connects to the database and selects all the categories and returns a list*/
	public List<CategoryBean> getAllCatagories() {
		List<CategoryBean> allCat = new ArrayList();
		BasicModel bm = new BasicModel();
		String sql = "SELECT * FROM category"; // TODO  ORDER BY a-z A-Z
		
		if (bm.initDB() == true && bm.startConnection() == true) {
			
			try {
				System.out.println("executing Query");
				System.out.println("attempting to retrieve all category");
				bm.prepStatment = bm.connection.prepareStatement(sql);
				bm.results = bm.prepStatment.executeQuery(); 
				
				System.out.println("retreiving results from query");
				while(bm.results.next()) {

					//insert data from db and add to bean
					CategoryBean catagory = new CategoryBean();
					catagory.setCategoryID(bm.results.getInt("categoryID"));
					catagory.setCategoryTitle(bm.results.getString("categoryTitle"));
					
					//add bean to list
					allCat.add(catagory);
				}
				
				bm.closeDB();
				return allCat;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				bm.closeDB();
				e.printStackTrace();
			}
		}
		
		bm.closeDB();
		return null;
	}
}
