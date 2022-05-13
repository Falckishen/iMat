// Hör ihop med MainPage.fxml
package iMat;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import se.chalmers.cse.dat216.project.*;

public class MainPageController implements Initializable {

    private final IMatDataHandler dataHandler = IMat.getIMatDataHandler();
    private final ShoppingCart cart = IMat.getShoppingCart();

    @FXML private AnchorPane mainPageRootAnchorPane;
    @FXML private FlowPane productItemsFlowpane;
    @FXML private Button brodKnapp;
    @FXML private TextField searchBar;
    @FXML private Button btnTestKonto;
    @FXML private GridPane gridPane;

    private int rowx = 0;
    private int coly = 0;

    // Körs när MainPage.fxml läses in
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillFood();
    }

    // Testmetod, ignorera
    @FXML
    public void openDetailViewButton() throws IOException {
        cart.clear();
        openProductDetailView(dataHandler.getProduct(75));
    }

    @FXML
    public void openProductDetailView(Product product) throws IOException {
        AnchorPane detailViewAnchorPane = new DetailViewController(product);
        mainPageRootAnchorPane.getChildren().add(detailViewAnchorPane);
        detailViewAnchorPane.toFront();
    }

    @FXML
    public void openAccountWindow() throws IOException {
        AnchorPane accountWindowPane = new AccountWindowController();
        mainPageRootAnchorPane.getChildren().add(accountWindowPane);
        accountWindowPane.toFront();
    }

    /* Knappar below tänker att den fungerar som den ska, fortsätter på allt annat och återvänder till det här när mera
    är färdigt, hoppas det är okej.*/
    @FXML
    protected void searchForBread(ActionEvent event) {
        coly = 0;
        rowx = 0;
        gridPane.getChildren().clear();
        ArrayList<Product> productlist = (ArrayList<Product>) dataHandler.getProducts(ProductCategory.BREAD);
        for(Product product: productlist) {
            ProductItemController productt = new ProductItemController(product);
            gridPane.add(productt, coly, rowx);
            coly++;
            if(coly == 2 ) {
                coly = 0;
                rowx++;
            }
        }
        /*ObservableList<Node> productItemsList = productItemsFlowpane.getChildren();
        productItemsList.clear();
        ArrayList<Product> productList = (ArrayList<Product>) dataHandler.getProducts(ProductCategory.valueOf("BREAD"));
        for(Product product : productList){
            productItemsList.add(new ProductItemController(product));
        }*/
    }

    @FXML
    protected void searchBar(ActionEvent event) {
        coly = 0;
        rowx = 0;
        gridPane.getChildren().clear();
        String text = searchBar.getText();
        ArrayList<Product> productlist = (ArrayList<Product>) dataHandler.findProducts(text);
        for(Product product: productlist){
            ProductItemController productt = new ProductItemController(product);
            gridPane.add(productt,coly,rowx);
            coly++;
            if(coly == 2 ){
                coly = 0;
                rowx++;
            }
        }
        /*ObservableList<Node> productItemsList = productItemsFlowpane.getChildren();
        productItemsList.clear();
        String text = searchBar.getText();
        ArrayList<Product> productList = (ArrayList<Product>) dataHandler.findProducts(text);
        for(Product product : productList){
            productItemsList.add(new ProductItemController(product));
        }*/
    }

    //TODO: Populera med rätt grejor tex. historiken.
    private void populateAccountWindow(Product product) {
    }

    //Fyller på flowpanen för att bygga all funktionalitet runt
    private void fillFood() {
        coly = 0;
        rowx = 0;
        gridPane.getChildren().clear();
        ArrayList<Product> productlist = (ArrayList<Product>) dataHandler.getProducts();
        for(Product product: productlist){
            ProductItemController productt = new ProductItemController(product);
            gridPane.add(productt, coly, rowx);
            coly++;
            if(coly == 2) {
                coly = 0;
                rowx++;
            }
        /*ObservableList<Node> productItemsList = productItemsFlowpane.getChildren();
        productItemsList.clear();
        ArrayList<Product> productList = (ArrayList<Product>) dataHandler.getProducts();
        for(Product product: productList){
            productItemsList.add(new ProductItemController(product));
        }*/
        }
    }
}
