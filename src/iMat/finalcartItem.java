package iMat;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingItem;

public class finalcartItem extends AnchorPane {
    private final IMatDataHandler dataHandler = IMat.getIMatDataHandler();

    @FXML private Text price;
    @FXML private Text amount;
    @FXML private ImageView bild;


    public finalcartItem(ShoppingItem shoppingitem) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/finalcartitem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        amount.setText(String.valueOf((int) shoppingitem.getAmount()));
        price.setText(shoppingitem.getProduct().getPrice() +" kr");
        bild.setImage(dataHandler.getFXImage(shoppingitem.getProduct()));
    }
}