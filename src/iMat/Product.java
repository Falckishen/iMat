package iMat;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;

public class Product extends AnchorPane {

    public Product(se.chalmers.cse.dat216.project.Product product) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Product.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
    }
}
