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
import javafx.scene.control.TitledPane;
import javafx.scene.layout.*;
import se.chalmers.cse.dat216.project.*;

public class MainPageController implements Initializable, ShoppingCartListener {

    private final IMatDataHandler dataHandler = IMat.getIMatDataHandler();
    private final ShoppingCart cart = dataHandler.getShoppingCart();

    private final ArrayList<ProductItemController> productItemsList = new ArrayList<ProductItemController>();
    private AnchorPane registerAnchorPane;
    private AnchorPane registerstep2AnchorPane;
    private AnchorPane registerfinalAnchorPane;
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

    @FXML private TitledPane dryckerTitledPane;
    @FXML private TitledPane snacksTitledPane;
    @FXML private TitledPane gronsakerTitledPane;
    @FXML private TitledPane kottTitledPane;
    @FXML private TitledPane fruktTitledPane;
    @FXML private TitledPane mejeriTitledPane;
    @FXML private TitledPane kryddorTitledPane;

/*-------------------------------------------------------------------------------------------------------------------*/

    //Använder denna för att fortsätta testa kassan
    @FXML
    private void empty(){
        cart.clear();
    }

/*-------------------------------------------------------------------------------------------------------------------*/

    // Körs när MainPage.fxml läses in
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupRegisterPage();
        setupAccountPage();
        setupPurchasePage();
        setupReceiptPage();
        fillWithAllFood();
        cart.addShoppingCartListener(this);
        updateCart();
    }

    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
        updateCart();
        updateProductItems();
    }

/*-------------------------------------------------------------------------------------------------------------------*/

    void openProductDetailView(Product product) {
        AnchorPane detailViewAnchorPane = new DetailViewController(product, this);
        this.mainPageRootAnchorPane.getChildren().add(detailViewAnchorPane);
        detailViewAnchorPane.toFront();
    }

    void updateProductItemFavoriteButtons(){
        for(ProductItemController productItemController : this.productItemsList) {
            productItemController.updateFavoriteButton();
        }
    }

/*-------------------------------------------------------------------------------------------------------------------*/

    @FXML
    void openMainPageView(){
        this.mainborderPane.toFront();
    }

    @FXML
    void openPurchaseView(){

    }

    @FXML
    void openRegisterView() {
        this.registerAnchorPane.toFront();
    }

    @FXML
    void openRegisterstep2View() {
        this.registerstep2AnchorPane.toFront();
    }

    @FXML
    void openRegisterfinalstep() {
        this.registerfinalAnchorPane.toFront();
    }

/*-------------------------------------------------------------------------------------------------------------------*/

    @FXML
    private void searchForBread() {
        searchForCategory("BREAD");
    }

    @FXML
    private void searchForMelons() {
        searchForCategory("MELONS");
    }

    @FXML
    private void searchForFlourSugarSalt() {
        searchForCategory("FLOUR_SUGAR_SALT");
    }

    @FXML
    private void searchForMeat() {
        searchForCategory("MEAT");
    }

    @FXML
    private void searchForDairy() {
        searchForCategory("DAIRIES");
    }

    @FXML
    private void searchForVegetableFruit() {
        searchForCategory("VEGETABLE_FRUIT");
    }

    @FXML
    private void searchForCabbage() {
        searchForCategory("CABBAGE");
    }

    @FXML
    private void searchForNutsAndSeeds() {
        searchForCategory("NUTS_AND_SEEDS");
    }

    @FXML
    private void searchForPasta() {
        searchForCategory("PASTA");
    }

    @FXML
    private void searchForPotatoRice() {
        searchForCategory("POTATO_RICE");
    }

    @FXML
    private void searchForRootVegetable() {
        searchForCategory("ROOT_VEGETABLE");
    }

    @FXML
    private void searchForFruit() {
        searchForCategory("FRUIT");
    }

    @FXML
    private void searchForSweet() {
        searchForCategory("SWEET");
    }

    @FXML
    private void searchForBerry() {
        searchForCategory("BERRY");
    }

    @FXML
    private void searchForHerb() {
        searchForCategory("HERB");
    }

    @FXML
    private void searchForCitrusFruit() {
        searchForCategory("CITRUS_FRUIT");
    }

    @FXML
    private void searchForHotDrinks() {
        searchForCategory("HOT_DRINKS");
    }

    @FXML
    private void searchForColdDrinks() {
        searchForCategory("COLD_DRINKS");
    }

    @FXML
    private void searchForExoticFruit() {
        searchForCategory("EXOTIC_FRUIT");
    }

    @FXML
    private void searchForFish() {
        searchForCategory("FISH");
    }

    @FXML
    private void openAccountWindow() {
        AnchorPane accountWindowPane = new AccountWindowController(this);
        mainPageRootAnchorPane.getChildren().add(accountWindowPane);
        accountWindowPane.toFront();
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
    }

    @FXML
    private void favoriteFill(ActionEvent event){
        collapsAccordion();
        ArrayList<Product> favoritesProductList = (ArrayList<Product>) dataHandler.favorites();
        fillWithFood(favoritesProductList);
    }

/*-------------------------------------------------------------------------------------------------------------------*/

    private void fillWithAllFood() {
        ArrayList<Product> productList = (ArrayList<Product>) dataHandler.getProducts();
        fillWithFood(productList);
    }

    private void searchForCategory(String category) {
        ArrayList<Product> productsInCategoryList = (ArrayList<Product>) dataHandler.getProducts(ProductCategory.valueOf(category));
        fillWithFood(productsInCategoryList);
    }

    private void fillWithFood(ArrayList<Product> productsList) {
        int colY = 0;
        int rowX = 0;
        this.gridPane.getChildren().clear();
        this.productItemsList.clear();
        for(Product product: productsList) {
            ProductItemController productItem = new ProductItemController(product, this);
            this.productItemsList.add(productItem);
            this.gridPane.add(productItem, colY, rowX);
            colY++;
            if(colY == 2 ) {
                colY = 0;
                rowX++;
            }
        }
    }

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

    private void collapsAccordion(){
        dryckerTitledPane.setExpanded(false);
        fruktTitledPane.setExpanded(false);
        gronsakerTitledPane.setExpanded(false);
        kottTitledPane.setExpanded(false);
        kryddorTitledPane.setExpanded(false);
        mejeriTitledPane.setExpanded(false);
        snacksTitledPane.setExpanded(false);
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
            CartItemController cartItem = new CartItemController(item, this);
            this.cartPanelView.getChildren().add(cartItem);
        }
    }

    //TODO: Fill in these.
    private void setupAccountPage() {

    }

    private void setupPurchasePage() {
        this.registerstep2AnchorPane = new RegisterStep2controller(this);
        this.mainPageRootAnchorPane.getChildren().add(this.registerstep2AnchorPane);
        this.registerstep2AnchorPane.toBack();

    }

    private void setupReceiptPage() {
        this.registerfinalAnchorPane = new RegisterFinalStepController(this);
        this.mainPageRootAnchorPane.getChildren().add(this.registerfinalAnchorPane);
        this.registerfinalAnchorPane.toBack();
    }

    private void setupRegisterPage() {
        this.registerAnchorPane = new RegisterController(this);
        this.mainPageRootAnchorPane.getChildren().add(this.registerAnchorPane);
        this.registerAnchorPane.toBack();
    }
}