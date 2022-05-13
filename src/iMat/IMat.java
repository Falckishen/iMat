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

    public static void main(String[] args) {
        launch(args);
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                dataHandler.shutDown();
            }
        }));
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainPage.fxml")));
        Scene scene = new Scene(root);
        stage.setTitle("iMat");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static IMatDataHandler getIMatDataHandler() {
        return dataHandler;
    }

    public static ShoppingCart getShoppingCart() {
        return cart;
    }

    // Tar en produkt som argument, retunerar antalet av denna produkt som finns i varukorgen
    public static double getNumberOfAProductInCart(Product product) {
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

    // OBS EJ HELT KLAR! Tar en produkt som argument, tar bort ett exemplar av denna produkt från varukorgen
    public static void removeOneFromCart(Product product) {
            List<ShoppingItem> listOfShoppingItems = cart.getItems();
            Product productInCart;
            for (ShoppingItem shoppingItem : listOfShoppingItems) {
                productInCart = shoppingItem.getProduct();
                if (productInCart.equals(product)) {
                    double amount = shoppingItem.getAmount();
                    cart.removeItem(shoppingItem);
                    if (IMat.getNumberOfAProductInCart(product) >= 1) {
                        cart.addItem(new ShoppingItem(product, amount-1));
                    }
                    break;
                }
            }
        }

    // OBS EJ HELT KLAR!
    public static void writeInNumOfProductAmount(Product product, String strAmount) {
        double amount = 0;
        try {
            amount = roundToOneDecimal(Double.parseDouble(strAmount));
        }
        catch (Exception e) {
            System.out.println("Fel");
            // Invalid input
        }
        clearCartOfProduct(product);
        cart.addProduct(product, amount);
    }

    private static double roundToOneDecimal(double value) {
        return (double) Math.round(value * 10) / 10;
    }

    // OBS EJ HELT KLAR!
    private static void clearCartOfProduct(Product product) {
        while (getNumberOfAProductInCart(product) > 0) {
            removeOneFromCart(product);
        }
    }
}