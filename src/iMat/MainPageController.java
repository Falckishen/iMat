// Hör ihop med MainPage.fxml
// TODO: Fixa scrollen i varukorgen
package iMat;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import se.chalmers.cse.dat216.project.*;

public class MainPageController implements Initializable, ShoppingCartListener {

    private final IMatDataHandler dataHandler = IMat.getIMatDataHandler();
    private final ShoppingCart cart = dataHandler.getShoppingCart();

    private final ClassLoader classLoader = getClass().getClassLoader();

    private ArrayList<ProductItemController> productItemsToShowList = new ArrayList<ProductItemController>();

    private boolean onlyEco = false;

    @FXML private ImageView isEcoImage;
    @FXML private AnchorPane mainPageRootAnchorPane;
    @FXML private TextField searchBar;
    @FXML private GridPane gridPane;
    @FXML private FlowPane cartPanelView;
    @FXML private Label totalPrice;
    @FXML private BorderPane mainborderPane;
    @FXML private AnchorPane registerAnchorPane;
    @FXML private AnchorPane registerstep2AnchorPane;
    @FXML private AnchorPane registerfinalAnchorPane;

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
        updateProductItemsNumText();
    }

/*-------------------------------------------------------------------------------------------------------------------*/

    void openProductDetailView(Product product) {
        AnchorPane detailViewAnchorPane = new DetailViewController(product, this);
        mainPageRootAnchorPane.getChildren().add(detailViewAnchorPane);
        detailViewAnchorPane.toFront();
    }

    void updateProductItemFavoriteButtons(){
        for(ProductItemController productItemController : productItemsToShowList) {
            productItemController.updateFavoriteButton();
        }
    }

    void openMainPageView(){
        mainborderPane.toFront();
    }

    void openPurchaseView(){

    }

    void openRegisterstep2View() {
        registerstep2AnchorPane.toFront();
    }

    void openRegisterfinalstep() {
        registerfinalAnchorPane.toFront();
    }

/*-------------------------------------------------------------------------------------------------------------------*/

    @FXML
    void openRegisterView() {
        registerAnchorPane.toFront();
    }

/*-------------------------------------------------------------------------------------------------------------------*/

    @FXML
    private void ecoButtonPressed() {
        if (onlyEco) {
            onlyEco = false;
            isEcoImage.setImage(null);
        }
        else {
            onlyEco = true;
            isEcoImage.setImage(new Image(Objects.requireNonNull(classLoader.getResourceAsStream("iMat/images/check_mark.png"))));
        }
    }

    /*-----*/

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

    /*-----*/

    @FXML
    private void openAccountWindow() {
        AnchorPane accountWindowPane = new AccountWindowController(this);
        mainPageRootAnchorPane.getChildren().add(accountWindowPane);
        accountWindowPane.toFront();
    }

    @FXML
    private void searchBar() {
        String text = searchBar.getText();
        fillWithFood((ArrayList<Product>) dataHandler.findProducts(text));
    }

    @FXML
    private void fillWithFavorites(ActionEvent event) {
        fillWithFood((ArrayList<Product>) dataHandler.favorites());
    }

/*-------------------------------------------------------------------------------------------------------------------*/

    private void searchForCategory(String category) {
        fillWithFood((ArrayList<Product>) dataHandler.getProducts(ProductCategory.valueOf(category)));
    }

    private void fillWithAllFood() {
        fillWithFood((ArrayList<Product>) dataHandler.getProducts());
    }

    private void fillWithFood(ArrayList<Product> productsToShowList) {
        int colY = 0;
        int rowX = 0;
        gridPane.getChildren().clear();
        productItemsToShowList.clear();
        for(Product product: productsToShowList) {
            if(onlyEco && !product.isEcological()) {
                continue;
            }
            ProductItemController productItem = new ProductItemController(product, this);
            productItemsToShowList.add(productItem);
            gridPane.add(productItem, colY, rowX);
            colY++;
            if(colY == 2 ) {
                colY = 0;
                rowX++;
            }
        }
    }

    /*-----*/

    private void updateProductItemsNumText() {
        for(ProductItemController productItemController : productItemsToShowList) {
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

    /*-----*/

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