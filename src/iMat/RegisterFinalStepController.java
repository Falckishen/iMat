package iMat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.*;

import java.io.IOException;
import java.util.ArrayList;

public class RegisterFinalStepController extends AnchorPane implements ShoppingCartListener {


    private final ShoppingCart cart = IMat.getShoppingCart();
    private final MainPageController mainPageController;
    private final IMatDataHandler dataHandler = IMat.getIMatDataHandler();

    @FXML
    FlowPane flowpane;
    @FXML
    Text antalkop;


    public RegisterFinalStepController(MainPageController mainPageController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/FinalCart.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        cart.addShoppingCartListener(this);
        this.mainPageController = mainPageController;

    }

    @FXML
    private void openMainPageView() throws IOException {
        cart.clear();
        mainPageController.disableEnableButton();
        mainPageController.openMainPageView();
    }

    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
        flowpane.getChildren().clear();
        ArrayList<ShoppingItem> productList = (ArrayList<ShoppingItem>) cart.getItems();
        for (ShoppingItem product : productList) {
            finalcartItem productItem = new finalcartItem(product);
            flowpane.getChildren().add(productItem);
        }
    }
}
