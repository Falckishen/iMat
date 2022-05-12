package iMat;

import java.util.List;
import java.util.Objects;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingCart;
import se.chalmers.cse.dat216.project.ShoppingItem;

public class IMat extends Application {

    private static final IMatDataHandler dataHandler = IMatDataHandler.getInstance();
    private static final ShoppingCart cart = dataHandler.getShoppingCart();

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainPage.fxml")));
        Scene scene = new Scene(root);
        stage.setTitle("iMat");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
        
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                dataHandler.shutDown();
            }
        }));
    }

    public static IMatDataHandler getIMatDataHandler() {
        return dataHandler;
    }

    public static ShoppingCart getShoppingCart() {
        return cart;
    }

    // Tar en produkt som argument, retunerar antalet av denna product som finns i varukorgen
    public static double getNumberOfProductInCart(Product product) {
        double numOfProductInCart = 0;
        List<ShoppingItem> listOfShoppingItems = cart.getItems();
        Product productInCart;
        for (ShoppingItem shoppingItem : listOfShoppingItems) {
            productInCart = shoppingItem.getProduct();
            if (productInCart.equals(product)) {
                numOfProductInCart += shoppingItem.getAmount();
            }
        }
        return numOfProductInCart;
    }
}