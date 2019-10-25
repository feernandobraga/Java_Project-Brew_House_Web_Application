package blogpackage.model.dao;

import blogpackage.model.bean.Category;
import org.junit.Test;

public class CategoryDAOTest {

    @Test
    public void selectAllCatagories() {
        CategoryDAO catDAO = new CategoryDAO();

        for (Category tempCat : catDAO.SelectAllCategories()) {
            System.out.println(tempCat.getCategoryID());
            System.out.println(tempCat.getCategoryTitle());
        }
    }
}