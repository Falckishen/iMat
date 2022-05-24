// Hör ihop med MainPage.fxml
// TODO: Fixa scrollen i varukorgen
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

    private boolean onlyEco = false;

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
        mainPageRootAnchorPane.getChildren().add(detailViewAnchorPane);
        detailViewAnchorPane.toFront();
    }

    void updateProductItemFavoriteButtons(){
        for(ProductItemController productItemController : productItemsList) {
            productItemController.updateFavoriteButton();
        }
    }

/*-------------------------------------------------------------------------------------------------------------------*/

    @FXML
    void openMainPageView(){
        mainborderPane.toFront();
    }

    @FXML
    void openPurchaseView(){

    }

    @FXML
    void openRegisterView() {
        registerAnchorPane.toFront();
    }

    @FXML
    void openRegisterstep2View() {
        registerstep2AnchorPane.toFront();
    }

    @FXML
    void openRegisterfinalstep() {
        registerfinalAnchorPane.toFront();
    }

/*-------------------------------------------------------------------------------------------------------------------*/

    @FXML
    private void searchForColdDrinks() {
        searchForCategory("COLD_DRINKS");
    }

    @FXML
    private void searchForHotDrinks() {
        searchForCategory("HOT_DRINKS");
    }

    /*-----*/

    @FXML
    private void searchForFruit() {
        searchForCategory("FRUIT");
    }

    @FXML
    private void searchForCitrusFruit() {
        searchForCategory("CITRUS_FRUIT");
    }

    @FXML
    private void searchForExoticFruit() {
        searchForCategory("EXOTIC_FRUIT");
    }

    @FXML
    private void searchForMelons() {
        searchForCategory("MELONS");
    }

    @FXML
    private void searchForBerry() {
        searchForCategory("BERRY");
    }

    /*-----*/

    @FXML
    private void searchForVegetableFruit() {
        searchForCategory("VEGETABLE_FRUIT");
    }

    @FXML
    private void searchForCabbage() {
        searchForCategory("CABBAGE");
    }

    @FXML
    private void searchForRootVegetable() {
        searchForCategory("ROOT_VEGETABLE");
    }

    @FXML
    private void searchForPod() {
        searchForCategory("POD");
    }

    /*-----*/

    @FXML
    private void searchForMeat() {
        searchForCategory("MEAT");
    }

    @FXML
    private void searchForFish() {
        searchForCategory("FISH");
    }

    /*-----*/

    @FXML
    private void searchForNutsAndSeeds() {
        searchForCategory("NUTS_AND_SEEDS");
    }

    @FXML
    private void searchForSweet() {
        searchForCategory("SWEET");
    }

    /*-----*/

    @FXML
    private void searchForHerb() {
        searchForCategory("HERB");
    }

    /*-----*/

    @FXML
    private void searchForDairy() {
        searchForCategory("DAIRIES");
    }

    /*-----*/

    @FXML
    private void searchForBread() {
        searchForCategory("BREAD");
    }

    @FXML
    private void searchForFlourSugarSalt() {
        searchForCategory("FLOUR_SUGAR_SALT");
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
    private void openAccountWindow() {
        AnchorPane accountWindowPane = new AccountWindowController(this);
        mainPageRootAnchorPane.getChildren().add(accountWindowPane);
        accountWindowPane.toFront();
    }

    @FXML
    private void searchBar() {
        int colY = 0;
        int rowX = 0;
        gridPane.getChildren().clear();
        String text = searchBar.getText();
        ArrayList<Product> productList = (ArrayList<Product>) dataHandler.findProducts(text);
        for(Product product: productList){
            ProductItemController productItem = new ProductItemController(product, this);
            gridPane.add(productItem, colY, rowX);
            colY++;
            if(colY == 2 ){
                colY = 0;
                rowX++;
            }
        }
    }

    @FXML
    private void favoriteFill(ActionEvent event) {
        ArrayList<Product> favoritesProductList = (ArrayList<Product>) dataHandler.favorites();
        fillWithFood(favoritesProductList);
    }

/*-------------------------------------------------------------------------------------------------------------------*/

    private void fillWithAllFood() {
        ArrayList<Product> productList = (ArrayList<Product>) dataHandler.getProducts();
        if (onlyEco) {
            fillWithOnlyEcoFood(productList);
        }
        else {
            fillWithFood(productList);
        }
    }

    private void searchForCategory(String category) {
        ArrayList<Product> productsInCategoryList = (ArrayList<Product>) dataHandler.getProducts(ProductCategory.valueOf(category));
        if (onlyEco) {
            fillWithOnlyEcoFood(productsInCategoryList);
        }
        else {
            fillWithFood(productsInCategoryList);
        }
    }

    private void fillWithOnlyEcoFood(ArrayList<Product> productsList) {
        productsList.removeIf(Product::isEcological);
        fillWithFood(productsList);
    }

    private void fillWithFood(ArrayList<Product> productsList) {
        int colY = 0;
        int rowX = 0;
        gridPane.getChildren().clear();
        productItemsList.clear();
        for(Product product: productsList) {
            ProductItemController productItem = new ProductItemController(product, this);
            productItemsList.add(productItem);
            gridPane.add(productItem, colY, rowX);
            colY++;
            if(colY == 2 ) {
                colY = 0;
                rowX++;
            }
        }
    }

    private void updateProductItems() {
        for(ProductItemController productItemController : productItemsList) {
            productItemController.updateNumberOfProductsText();
        }
    }

    private void updateCart() {
        cartPanelView.getChildren().clear();
        totalPrice.setText(String.format("Pris: %.2f kr", cart.getTotal()));
        List<ShoppingItem> listOfShoppingItems = cart.getItems();
        for(ShoppingItem item: listOfShoppingItems) {
            CartItemController cartItem = new CartItemController(item, this);
            cartPanelView.getChildren().add(cartItem);
        }
    }

    private void setupPurchasePage() {
        registerstep2AnchorPane = new RegisterStep2controller(this);
        mainPageRootAnchorPane.getChildren().add(registerstep2AnchorPane);
        registerstep2AnchorPane.toBack();

    }

    private void setupReceiptPage() {
        registerfinalAnchorPane = new RegisterFinalStepController(this);
        mainPageRootAnchorPane.getChildren().add(registerfinalAnchorPane);
        registerfinalAnchorPane.toBack();
    }

    private void setupRegisterPage() {
        registerAnchorPane = new RegisterController(this);
        mainPageRootAnchorPane.getChildren().add(registerAnchorPane);
        registerAnchorPane.toBack();
    }
}