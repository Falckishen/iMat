// Hör ihop med MainPage.fxml
package iMat;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.*;

public class MainPageController implements Initializable, ShoppingCartListener {

    private final IMatDataHandler dataHandler = IMat.getIMatDataHandler();
    private final ShoppingCart cart = IMat.getShoppingCart();

    @FXML private AnchorPane mainPageRootAnchorPane;
    @FXML private AnchorPane cartstepOne;
    @FXML private FlowPane productItemsFlowpane;
    @FXML private Button brodKnapp;
    @FXML private TextField searchBar;
    @FXML private Button btnTestKonto;
    @FXML private GridPane gridPane;
    @FXML private Button favoriterKnapp;
    @FXML private FlowPane cartPanelView;
    @FXML private Button emptyCart;
    @FXML private Label totalPrice;
    @FXML private FlowPane cartFlowPane;
    @FXML private Button kassa1backButton;
    @FXML private Button tillkassanButton;
    @FXML private BorderPane mainborderPane;
    @FXML private Text currentpriceinCartOne;

    private int rowx = 0;
    private int coly = 0;

    // Körs när MainPage.fxml läses in
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cart.addShoppingCartListener(this);
        fillFood();
    }

    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
        cartPanelView.getChildren().clear();
        cartFlowPane.getChildren().clear();
        totalPrice.setText(String.valueOf(dataHandler.getShoppingCart().getTotal()));
        ArrayList<ShoppingItem> list = (ArrayList<ShoppingItem>) dataHandler.getShoppingCart().getItems();
        for(ShoppingItem item: list){
            if(item.getAmount() > 0){
                CartItemController cartitem = new CartItemController(item);
                CartStepOneController cartflow = new CartStepOneController(item);
                cartPanelView.getChildren().add(cartitem);
                cartFlowPane.getChildren().add(cartflow);
                currentpriceinCartOne.setText(String.valueOf(dataHandler.getShoppingCart().getTotal()));
            }
        }
    }


    @FXML public void toCartView(){
        fillsteponeCart();
        cartstepOne.toFront();
    }

    @FXML public void backtoMainPageView(){
        mainborderPane.toFront();
        emptysteponeCart();
    }

    // Testmetod
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
        cartPanelView.getChildren().clear();
        ArrayList<Product> productlist = (ArrayList<Product>) dataHandler.getProducts();
        for(Product product: productlist){
            ProductItemController productt = new ProductItemController(product);
            gridPane.add(productt, coly, rowx);
            coly++;
            if(coly == 2) {
                rowx++;
                coly = 0;
            }
        /*ObservableList<Node> productItemsList = productItemsFlowpane.getChildren();
        productItemsList.clear();
        ArrayList<Product> productList = (ArrayList<Product>) dataHandler.getProducts();
        for(Product product: productList){
            productItemsList.add(new ProductItemController(product));
        }*/
        }
    }

    /* ---------------------------------------------------------------------------   */

    private void fillsteponeCart(){
        cartFlowPane.getChildren().clear();
        ArrayList<ShoppingItem> list = (ArrayList<ShoppingItem>) dataHandler.getShoppingCart().getItems();
        for(ShoppingItem item: list){
            CartStepOneController cartitem = new CartStepOneController(item);
            cartFlowPane.getChildren().add(cartitem);

    }}

    private void emptysteponeCart(){
        cartFlowPane.getChildren().clear();
        }

    @FXML protected void favoriteFill(ActionEvent event){
        updateCart();
        coly = 0;
        rowx = 0;
        gridPane.getChildren().clear();
        ArrayList<Product> productlist = (ArrayList<Product>) dataHandler.favorites();
        for(Product product: productlist){
            ProductItemController productt = new ProductItemController(product);
            gridPane.add(productt, coly, rowx);
            coly++;
            if(coly == 2) {
                coly = 0;
                rowx++;
            }
        }
    }

     void updateCart(){
        cartPanelView.getChildren().clear();
        ArrayList<ShoppingItem> list = (ArrayList<ShoppingItem>) dataHandler.getShoppingCart().getItems();
        for(ShoppingItem item: list){
            CartItemController cartitem = new CartItemController(item);
            cartPanelView.getChildren().add(cartitem);
        }
                /*ObservableList<Node> productItemsList = productItemsFlowpane.getChildren();
        productItemsList.clear();
        ArrayList<Product> productList = (ArrayList<Product>) dataHandler.getProducts();
        for(Product product: productList){
            productItemsList.add(new ProductItemController(product));
        }*/
    }

    @FXML protected void addtoFavorite(ActionEvent event){
        gridPane.getChildren().clear();
        ArrayList<Product> productlist = (ArrayList<Product>) dataHandler.favorites();
        for(Product product: productlist){
            ProductItemController productt = new ProductItemController(product);
            gridPane.add(productt, coly, rowx);
            coly++;
            if(coly == 2) {
                coly = 0;
                rowx++;
            }
        }
    }

    //Använder denna för att fortsätta testa kassan
    @FXML private void empty(){
        dataHandler.getShoppingCart().clear();
        updateCart();
    }
}
