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
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/MainPage.fxml")));
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

/*-------------------------------------------------------------------------------------------------------------------*/

    // Tar en produkt som argument, returnera antalet av denna produkt som finns i varukorgen
    static int getNumberOfAProductInCart(Product product) {
        int numOfProductInCart = 0;
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

    // Tar en produkt som argument, lägger till ett exemplar av denna produkt i varukorgen
    static void addOneToCart(Product product) {
        int amount = getNumberOfAProductInCart(product);
        if (amount < 99) {
            setProductAmount(product, amount+1);
        }
        else {
            setProductAmount(product, 99);
        }
    }

    // Tar en produkt som argument, tar bort ett exemplar av denna produkt från varukorgen
    static void removeOneFromCart(Product product) {
        int amount = getNumberOfAProductInCart(product);
        if (amount >= 1) {
            setProductAmount(product, amount-1);
        }
        else {
            clearCartOfAProduct(product);
        }
    }

    /* Tar en produkt och ett antal i string, ändrar så antalet av denna produkt i varukorgen till antalet som gavs som
    argument */
    static void writeInNumOfProductAmount(Product product, String strAmount) {
        try {
            int amount = Integer.parseInt(strAmount);
            if (amount > 99) {
                setProductAmount(product, 99);
            }
            else if (amount >= 0) {
                setProductAmount(product, amount);
            }
            else {
                clearCartOfAProduct(product);
            }
        }
        catch (Exception e) {
            // Invalid input
        }
    }

/*-------------------------------------------------------------------------------------------------------------------*/

    public static void setProductAmount(Product product, int amount) {
        List<ShoppingItem> listOfShoppingItems = cart.getItems();
        Product productInCart;
        for (ShoppingItem shoppingItem : listOfShoppingItems) {
            productInCart = shoppingItem.getProduct();
            if (productInCart.equals(product)) {
                shoppingItem.setAmount(amount);
                cart.fireShoppingCartChanged(shoppingItem, true);
                return;
            }
        }
        cart.addProduct(product, amount);
    }

    // Tar en produkt som argument, tömmer varukorgen på denna produkt
    public static void clearCartOfAProduct(Product product) {
        while (getNumberOfAProductInCart(product) != 0) {
            List<ShoppingItem> listOfShoppingItems = cart.getItems();
            Product productInCart;
            for (ShoppingItem shoppingItem : listOfShoppingItems) {
                productInCart = shoppingItem.getProduct();
                if (productInCart.equals(product)) {
                    cart.removeItem(shoppingItem);
                    break;
                }
            }
        }
    }
}