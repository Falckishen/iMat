// Hör ihop med MainPage.fxml
package iMat;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.*;

public class MainPageController implements Initializable {

    private final IMatDataHandler dataHandler = IMat.getIMatDataHandler();

    @FXML private AnchorPane mainPageRootAnchorPane;
    @FXML private FlowPane productItemsFlowpane;
    @FXML private Button brodKnapp;
    @FXML private TextField searchBar;
    @FXML private Button btnTestKonto;
    @FXML private GridPane gridPane;
    @FXML private Button favoriterKnapp;
    @FXML private FlowPane cartPanelView;
    @FXML private Button emptyCart;
    @FXML private Label totalPrice;
    @FXML private Button kassa1backButton;
    @FXML private Button tillkassanButton;
    @FXML private BorderPane mainborderPane;
    @FXML private Text currentpriceinCartOne;

    private AnchorPane registerAnchorPane;

    private int rowx = 0;
    private int coly = 0;

    // Körs när MainPage.fxml läses in
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //cart.addShoppingCartListener(this);
        setupRegisterPage();
        fillFood();
        updateCart();
    }

    public void updateCart() {
        cartPanelView.getChildren().clear();
        //registerAnchorPane.getChildren().clear();
        totalPrice.setText(String.valueOf(dataHandler.getShoppingCart().getTotal()));
        ArrayList<ShoppingItem> list = (ArrayList<ShoppingItem>) dataHandler.getShoppingCart().getItems();
        for(ShoppingItem item: list){
            if(item.getAmount() > 0){
                CartItemController cartItem = new CartItemController(item, this);
                CartStepOneController cartFlow = new CartStepOneController(item);
                cartPanelView.getChildren().add(cartItem);
                //cartFlowPane.getChildren().add(cartFlow);
                //currentpriceinCartOne.setText(String.valueOf(dataHandler.getShoppingCart().getTotal()));
            }
        }
    }

    private void setupRegisterPage(){
        registerAnchorPane = new RegisterController(this);
        mainPageRootAnchorPane.getChildren().add(registerAnchorPane);
        registerAnchorPane.toBack();
    }

    @FXML public void openRegisterView(){
        registerAnchorPane.toFront();
    }

    @FXML public void openMainPageView(){
        mainborderPane.toFront();
    }

    @FXML public void openPurchaseView(){
    }

    // Testmetod
    @FXML
    private void openDetailViewButton() {
        openProductDetailView(dataHandler.getProduct(75));
    }

    @FXML
    private void openProductDetailView(Product product) {
        AnchorPane detailViewAnchorPane = new DetailViewController(product, this);
        mainPageRootAnchorPane.getChildren().add(detailViewAnchorPane);
        detailViewAnchorPane.toFront();
    }

    @FXML
    private void openAccountWindow() {
        AnchorPane accountWindowPane = new AccountWindowController();
        mainPageRootAnchorPane.getChildren().add(accountWindowPane);
        accountWindowPane.toFront();
    }

    /* Knappar below tänker att den fungerar som den ska, fortsätter på allt annat och återvänder till det här när mera
    är färdigt, hoppas det är okej.*/
    @FXML
    private void searchForBread(ActionEvent event) {
        int colY = 0;
        int rowX = 0;
        gridPane.getChildren().clear();
        ArrayList<Product> productlist = (ArrayList<Product>) dataHandler.getProducts(ProductCategory.BREAD);
        for(Product product: productlist) {
            ProductItemController productt = new ProductItemController(product, this);
            gridPane.add(productt, colY, rowX);
            colY++;
            if(colY == 2 ) {
                colY = 0;
                rowX++;
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
    private void searchBar(ActionEvent event) {
        int colY = 0;
        int rowX = 0;
        gridPane.getChildren().clear();
        String text = searchBar.getText();
        ArrayList<Product> productlist = (ArrayList<Product>) dataHandler.findProducts(text);
        for(Product product: productlist){
            ProductItemController productt = new ProductItemController(product, this);
            gridPane.add(productt, colY, rowX);
            colY++;
            if(colY == 2 ){
                colY = 0;
                rowX++;
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

    @FXML
    private void favoriteFill(ActionEvent event){
        int colY = 0;
        int rowX = 0;
        gridPane.getChildren().clear();
        ArrayList<Product> productlist = (ArrayList<Product>) dataHandler.favorites();
        for(Product product: productlist){
            ProductItemController productt = new ProductItemController(product, this);
            gridPane.add(productt, colY, rowX);
            colY++;
            if(colY == 2) {
                colY = 0;
                rowX++;
            }
        }
        updateCart();
    }

    @FXML
    private void addToFavorite(ActionEvent event){
        int colY = 0;
        int rowX = 0;
        gridPane.getChildren().clear();
        ArrayList<Product> productlist = (ArrayList<Product>) dataHandler.favorites();
        for(Product product: productlist){
            ProductItemController productt = new ProductItemController(product, this);
            gridPane.add(productt, colY, rowX);
            colY++;
            if(colY == 2) {
                colY = 0;
                rowX++;
            }
        }
    }

    //Använder denna för att fortsätta testa kassan
    @FXML
    private void empty(){
        dataHandler.getShoppingCart().clear();
        updateCart();
    }

    /*private void fillStepOneCart(){
        cartFlowPane.getChildren().clear();
        ArrayList<ShoppingItem> list = (ArrayList<ShoppingItem>) dataHandler.getShoppingCart().getItems();
        for(ShoppingItem item: list){
            CartStepOneController cartitem = new CartStepOneController(item);
            registerGridPane.getChildren().add(cartitem);

        }}

    private void emptyStepOneCart(){
        cartFlowPane.getChildren().clear();
    }*/

    //TODO: Populera med rätt grejor tex. historiken.
    private void populateAccountWindow(Product product) {
    }

    //Fyller på flowpanen för att bygga all funktionalitet runt
    private void fillFood() {
        int colY = 0;
        int colX = 0;
        gridPane.getChildren().clear();
        cartPanelView.getChildren().clear();
        ArrayList<Product> productlist = (ArrayList<Product>) dataHandler.getProducts();
        for(Product product: productlist){
            ProductItemController productt = new ProductItemController(product, this);
            gridPane.add(productt, colY, colX);
            colY++;
            if(colY == 2) {
                colX++;
                colY = 0;
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
