// Hör ihop med MainPage.fxml
package iMat;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.*;

public class MainPageController implements Initializable, ShoppingCartListener {

    private final IMatDataHandler dataHandler = IMat.getIMatDataHandler();
    private final ShoppingCart cart = dataHandler.getShoppingCart();
    private iMatAccount account;

    private final ArrayList<ProductItemController> productItemsList = new ArrayList<ProductItemController>();
    private AnchorPane registerAnchorPane;
    private AnchorPane purchaseAnchorPane;
    private AnchorPane receiptAnchorPane;

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

    // Körs när MainPage.fxml läses in
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupRegisterPage();
        setupAccountPage();
        setupPurchasePage();
        setupReceiptPage();
        fillFood();
        cart.addShoppingCartListener(this);
        updateCart();
    }

    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
        updateCart();
        updateProductItems();
        System.out.println("shoppingCartChanged");
    }

    /*
    public vo{
        cartPanelView.getChildren().clear();
        totalPrice.setText(String.valueOf(cart.getTotal()));
        ArrayList<ShoppingItem> list = (ArrayList<ShoppingItem>) cart.getItems();
        for(ShoppingItem item: list){
            if(item.getAmount() > 0){
                CartItemController cartItem = new CartItemController(item, this);
                cartPanelView.getChildren().add(cartItem);
            }
        }
    }
    */

    public void changeAccount(iMatAccount newAccount) {
        this.account = newAccount;
    }

    public void openProductDetailView(Product product) {
        AnchorPane detailViewAnchorPane = new DetailViewController(product, this);
        this.mainPageRootAnchorPane.getChildren().add(detailViewAnchorPane);
        detailViewAnchorPane.toFront();
    }

    public void updateProductItemFavoriteButtons(){
        for(ProductItemController productItemController : this.productItemsList) {
            productItemController.updateFavoriteButton();
        }
    }

    @FXML
    public void openMainPageView(){
        this.mainborderPane.toFront();
    }

    @FXML
    public void openPurchaseView(){

    }

/*-------------------------------------------------------------------------------------------------------------------*/

    @FXML
    private void openRegisterView(){
        this.registerAnchorPane.toFront();
    }

    @FXML
    private void openAccountWindow() {
        AnchorPane accountWindowPane = new AccountWindowController(this, this.account);
        mainPageRootAnchorPane.getChildren().add(accountWindowPane);
        accountWindowPane.toFront();
    }

    /* Knappar below tänker att den fungerar som den ska, fortsätter på allt annat och återvänder till det här när mera
    är färdigt, hoppas det är okej.*/
    @FXML
    private void searchForBread() {
        int colY = 0;
        int rowX = 0;
        this.gridPane.getChildren().clear();
        ArrayList<Product> productList = (ArrayList<Product>) dataHandler.getProducts(ProductCategory.BREAD);
        for(Product product: productList) {
            ProductItemController productItem = new ProductItemController(product, this);
            this.gridPane.add(productItem, colY, rowX);
            colY++;
            if(colY == 2 ) {
                colY = 0;
                rowX++;
            }
        }
        /*
        ObservableList<Node> productItemsList = productItemsFlowpane.getChildren();
        productItemsList.clear();
        ArrayList<Product> productList = (ArrayList<Product>) dataHandler.getProducts(ProductCategory.valueOf("BREAD"));
        for(Product product : productList){
            productItemsList.add(new ProductItemController(product, this));
        }
        */
    }

    @FXML
    private void searchBar() {
        int colY = 0;
        int rowX = 0;
        this.gridPane.getChildren().clear();
        String text = this.searchBar.getText();
        ArrayList<Product> productList = (ArrayList<Product>) dataHandler.findProducts(text);
        for(Product product: productList){
            ProductItemController productItem = new ProductItemController(product, this);
            this.gridPane.add(productItem, colY, rowX);
            colY++;
            if(colY == 2 ){
                colY = 0;
                rowX++;
            }
        }
        /*
        ObservableList<Node> productItemsList = productItemsFlowpane.getChildren();
        productItemsList.clear();
        String text = searchBar.getText();
        ArrayList<Product> productList = (ArrayList<Product>) dataHandler.findProducts(text);
        for(Product product : productList){
            productItemsList.add(new ProductItemController(product, this));
        }
        */
    }

    @FXML
    private void favoriteFill(ActionEvent event){
        int colY = 0;
        int rowX = 0;
        this.gridPane.getChildren().clear();
        ArrayList<Product> productList = (ArrayList<Product>) dataHandler.favorites();
        for(Product product: productList) {
            ProductItemController productItem = new ProductItemController(product, this);
            this.gridPane.add(productItem, colY, rowX);
            colY++;
            if(colY == 2) {
                colY = 0;
                rowX++;
            }
        }
    }

    @FXML
    private void addToFavorite() {
        int colY = 0;
        int rowX = 0;
        this.gridPane.getChildren().clear();
        ArrayList<Product> productList = (ArrayList<Product>) dataHandler.favorites();
        for(Product product: productList) {
            ProductItemController productItem = new ProductItemController(product, this);
            this.gridPane.add(productItem, colY, rowX);
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
        cart.clear();
    }

/*-------------------------------------------------------------------------------------------------------------------*/

    /*
    private void fillStepOneCart(){
        cartFlowPane.getChildren().clear();
        ArrayList<ShoppingItem> list = (ArrayList<ShoppingItem>) cart.getItems();
        for(ShoppingItem item: list){
            CartStepOneController cartitem = new CartStepOneController(item);
            registerGridPane.getChildren().add(cartitem);

        }}

    private void emptyStepOneCart(){
        cartFlowPane.getChildren().clear();
    }
    */

    //TODO: Populera med rätt grejor tex. historiken.
    private void populateAccountWindow(Product product) {

    }

    private void fillFood() {
        int colY = 0;
        int colX = 0;
        this.gridPane.getChildren().clear();
        this.cartPanelView.getChildren().clear();
        ArrayList<Product> productList = (ArrayList<Product>) dataHandler.getProducts();
        for(Product product: productList){
            ProductItemController productItem = new ProductItemController(product, this);
            this.gridPane.add(productItem, colY, colX);
            this.productItemsList.add(productItem);
            colY++;
            if(colY == 2) {
                colX++;
                colY = 0;
            }
        /*
        ObservableList<Node> productItemsList = productItemsFlowpane.getChildren();
        productItemsList.clear();
        ArrayList<Product> productList = (ArrayList<Product>) dataHandler.getProducts();
        for(Product product: productList){
            productItemsList.add(new ProductItemController(product, this));
        }
        */
        }
    }

    private void updateProductItems() {
        for(ProductItemController productItemController : this.productItemsList) {
            productItemController.updateNumberOfProductsText();
        }
    }

    private void updateCart() {
        this.cartPanelView.getChildren().clear();
        this.totalPrice.setText(String.format("Pris: %.2f", cart.getTotal()));
        List<ShoppingItem> listOfShoppingItems = cart.getItems();
        for(ShoppingItem item: listOfShoppingItems) {
            if(item.getAmount() > 0) {
                CartItemController cartItem = new CartItemController(item);
                this.cartPanelView.getChildren().add(cartItem);
            }
        }
    }

    //TODO: Fill in these.
    private void setupAccountPage() {

    }

    private void setupPurchasePage() {

    }

    private void setupReceiptPage() {

    }

    private void setupRegisterPage() {
        this.registerAnchorPane = new RegisterController(this);
        this.mainPageRootAnchorPane.getChildren().add(this.registerAnchorPane);
        this.registerAnchorPane.toBack();
    }

    private void createAccount() {
        this.account = new iMatAccount("", "", "", 0, 0, PaymentType.NONE);
    }
}