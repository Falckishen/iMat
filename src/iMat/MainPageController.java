// Hör ihop med IMatMainPage.fxml
package iMat;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

public class MainPageController implements Initializable {

    private final IMatDataHandler dataHandler = IMat.getIMatDataHandler();

    @FXML private AnchorPane detailViewAnchorPane;
    @FXML private ImageView detailViewImage;
    @FXML private Label priceLabel;

    // Körs när MainPage.fxml läses in
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateProductDetailView(dataHandler.getProduct(87));
    }

    public void openProductDetailView(Product product) {
        populateProductDetailView(product);
        detailViewAnchorPane.toFront();
    }

    private void populateProductDetailView(Product product) {
        detailViewImage.setImage(dataHandler.getFXImage(product));
        priceLabel.setText(String.format("%f kr", product.getPrice()));
    }
}