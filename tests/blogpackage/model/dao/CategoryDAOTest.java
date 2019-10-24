package blogpackage.model.dao;

import blogpackage.model.bean.Category;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryDAOTest {

    @Test
    public void selectAllCatagories() {
        CategoryDAO catDAO = new CategoryDAO();

        for (Category tempCat : catDAO.SelectAllCatagories()) {
            System.out.println(tempCat.getCategoryID());
            System.out.println(tempCat.getCategoryTitle());
        }
    }
}