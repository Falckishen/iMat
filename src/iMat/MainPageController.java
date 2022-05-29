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
    private void logoClicked() {
        fillWithAllFood();
    }

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
    private void searchForCarbs() {
        String[] categories = {"BREAD", "FLOUR_SUGAR_SALT", "PASTA", "POTATO_RICE"};
        searchForCategories(categories);
    }

    @FXML
    private void searchForBread() {
        String[] categories = {"BREAD"};
        searchForCategories(categories);
    }

    @FXML
    private void searchForFlourSugarSalt() {
        String[] categories = {"FLOUR_SUGAR_SALT"};
        searchForCategories(categories);
    }

    @FXML
    private void searchForPasta() {
        String[] categories = {"PASTA"};
        searchForCategories(categories);
    }

    @FXML
    private void searchForPotatoRice() {
        String[] categories = {"POTATO_RICE"};
        searchForCategories(categories);
    }

    /*-----*/

    @FXML
    private void searchForDrinks() {
        String[] categories = {"COLD_DRINKS", "HOT_DRINKS"};
        searchForCategories(categories);
    }

    @FXML
    private void searchForColdDrinks() {
        String[] categories = {"COLD_DRINKS"};
        searchForCategories(categories);
    }

    @FXML
    private void searchForHotDrinks() {
        String[] categories = {"HOT_DRINKS"};
        searchForCategories(categories);
    }

    /*-----*/

    @FXML
    private void searchForFruitAndBerrys() {
        String[] categories = {"FRUIT", "CITRUS_FRUIT", "EXOTIC_FRUIT", "MELONS", "BERRY"};
        searchForCategories(categories);
    }

    @FXML
    private void searchForCitrusFruit() {
        String[] categories = {"CITRUS_FRUIT"};
        searchForCategories(categories);
    }

    @FXML
    private void searchForExoticFruit() {
        String[] categories = {"EXOTIC_FRUIT"};
        searchForCategories(categories);
    }

    @FXML
    private void searchForMelons() {
        String[] categories = {"MELONS"};
        searchForCategories(categories);
    }

    @FXML
    private void searchForBerry() {
        String[] categories = {"BERRY"};
        searchForCategories(categories);
    }

    /*-----*/

    @FXML
    private void searchForVegetable() {
        String[] categories = {"VEGETABLE_FRUIT", "CABBAGE", "ROOT_VEGETABLE", "POD"};
        searchForCategories(categories);
    }

    @FXML
    private void searchForVegetableFruit() {
        String[] categories = {"VEGETABLE_FRUIT"};
        searchForCategories(categories);
    }

    @FXML
    private void searchForCabbage() {
        String[] categories = {"CABBAGE"};
        searchForCategories(categories);
    }

    @FXML
    private void searchForRootVegetable() {
        String[] categories = {"ROOT_VEGETABLE"};
        searchForCategories(categories);
    }

    @FXML
    private void searchForPod() {
        String[] categories = {"POD"};
        searchForCategories(categories);
    }

    /*-----*/

    @FXML
    private void searchForMeatAndFish() {
        String[] categories = {"MEAT", "FISH"};
        searchForCategories(categories);
    }

    @FXML
    private void searchForMeat() {
        String[] categories = {"MEAT"};
        searchForCategories(categories);
    }

    @FXML
    private void searchForFish() {
        String[] categories = {"FISH"};
        searchForCategories(categories);
    }

    /*-----*/

    @FXML
    private void searchForSnacks() {
        String[] categories = {"NUTS_AND_SEEDS", "SWEET"};
        searchForCategories(categories);
    }

    @FXML
    private void searchForNutsAndSeeds() {
        String[] categories = {"NUTS_AND_SEEDS"};
        searchForCategories(categories);
    }

    @FXML
    private void searchForSweet() {
        String[] categories = {"SWEET"};
        searchForCategories(categories);
    }

    /*-----*/

    @FXML
    private void searchForHerb() {
        String[] categories = {"HERB"};
        searchForCategories(categories);
    }

    /*-----*/

    @FXML
    private void searchForDairy() {
        String[] categories = {"DAIRIES"};
        searchForCategories(categories);
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

    private void searchForCategories(String[] categories) {
        ArrayList<Product> productsToShowList = new ArrayList<Product>(5);
        for (String category : categories) {
            productsToShowList.addAll(dataHandler.getProducts(ProductCategory.valueOf(category)));
        }
        fillWithFood(productsToShowList);
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
        if(gridPane.getChildren().size() %2 == 1) {
            ProductItemController productItem = new ProductItemController(dataHandler.getProduct(1), this);
            productItem.setVisible(false);
            gridPane.add(productItem, 1, gridPane.getRowCount());
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