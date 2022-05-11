/* Hör ihop med ProductItem.fxml */
package iMat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

public class ProductItemController extends AnchorPane {

    private final IMatDataHandler dataHandler = IMat.getIMatDataHandler();
    private final Product product;
    @FXML ImageView image;
    Image bild;

    public ProductItemController(Product product) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProductItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.product = product;
        Image bild = dataHandler.getFXImage(product);
        this.image.setImage(bild);
    }
}