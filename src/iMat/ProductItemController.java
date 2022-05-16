// Hör ihop med ProductItem.fxml
package iMat;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

public class ProductItemController extends AnchorPane {

    private final IMatDataHandler dataHandler = IMat.getIMatDataHandler();
    private final MainPageController mainpagecontroller = new MainPageController();
    private final Product product;
    private final MainPageController mainPageController;
    @FXML ImageView image;
    @FXML ImageView favoriteUnselected;
    @FXML ImageView inkopslistaUnselected;
    @FXML Text nameofProduct;
    @FXML Text priceofItem;
    @FXML ImageView addtocartButton;
    @FXML Button addtocart;

    private Image bild;

    public ProductItemController(Product product, MainPageController mainPageController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fmxl/ProductItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.mainPageController = mainPageController;
        this.product = product;
        this.bild = dataHandler.getFXImage(product);
        this.image.setImage(bild);
        this.nameofProduct.setText(product.getName());
        this.priceofItem.setText(String.valueOf(product.getPrice()));
    }

    @FXML public void plusbutton(){
        IMat.addOneToCart(this.product);
        this.mainPageController.updateCart();
    }

    @FXML public void removefromCart(){
        IMat.removeOneFromCart(this.product);
        this.mainPageController.updateCart();

    }

}