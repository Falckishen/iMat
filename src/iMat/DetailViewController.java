// Hör ihop med DetailView.fxml
package iMat;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import java.util.Objects;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import se.chalmers.cse.dat216.project.*;

public class DetailViewController extends AnchorPane {

    private final IMatDataHandler dataHandler = IMat.getIMatDataHandler();
    private final ClassLoader classLoader = getClass().getClassLoader();

    private Product product;

    @FXML private ImageView image;
    @FXML private Label productNameLabel;
    @FXML private Label priceLabel;
    @FXML private TextField numOfItems;
    @FXML private Label isEcoLabel;
    @FXML private ImageView closeImageView;

    public DetailViewController(Product product) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DetailView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.product = product;
        populateProductDetailView();
    }

    @FXML
    public void closeProductDetailView() {
        this.toBack();
    }

    @FXML
    public void closeButtonMouseEntered() {
        this.closeImageView.setImage(new Image(Objects.requireNonNull(classLoader.getResourceAsStream("iMat/images/icon_close_hover.png"))));
    }

    @FXML
    public void closeButtonMousePressed() {
        this.closeImageView.setImage(new Image(Objects.requireNonNull(classLoader.getResourceAsStream("iMat/images/icon_close_pressed.png"))));
    }

    @FXML
    public void closeButtonMouseExited(){
        this.closeImageView.setImage(new Image(Objects.requireNonNull(classLoader.getResourceAsStream("iMat/images/icon_close.png"))));
    }

    @FXML
    public void mouseTrap(Event event) {
        event.consume();
    }

    // Fyller upp detalj vyn med rätt produkt
    private void populateProductDetailView() {
        this.image.setImage(dataHandler.getFXImage(this.product));
        this.productNameLabel.setText(this.product.getName());
        this.priceLabel.setText(String.format("Pris: %.2f %s", this.product.getPrice(), this.product.getUnit()));
        if (this.product.isEcological()) {
            this.isEcoLabel.setText("Produkten är ekologisk");
        }
        else {
            this.isEcoLabel.setText("Produkten är ej ekologisk");
        }
        this.numOfItems.setText(String.format("%.1f", IMat.getNumberOfAProductInCart(this.product)));
    }
}
