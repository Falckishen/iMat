package iMat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class finalcartItem extends AnchorPane {
    private final IMatDataHandler dataHandler = IMat.getIMatDataHandler();

    @FXML
    Label amountLabel;
    @FXML Label priceLabel;
    @FXML
    ImageView bild;


    public finalcartItem(ShoppingItem shoppingitem) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/finalcartitem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        amountLabel.setText(String.valueOf(shoppingitem));
        priceLabel.setText(String.valueOf(shoppingitem));
        bild.setImage(dataHandler.getFXImage(shoppingitem.getProduct()));

    }


}
