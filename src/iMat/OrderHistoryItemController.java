package iMat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.util.Calendar;

public class OrderHistoryItemController extends AnchorPane {
    private IMatDataHandler dataHandler = IMatDataHandler.getInstance();
    private Order order;
    private double total;

    @FXML
    private Label labelPurchaseDate;
    @FXML
    private Label labelPurchaseTotal;
    @FXML
    private TextArea orderTextArea;

    public OrderHistoryItemController(Order order) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/OrderHistoryItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.order = order;
        fillOrder();
    }

    private void fillOrder() {
        String text = "";
        for (ShoppingItem item : order.getItems()) {
            text = text + item.getProduct().getName() + "   " + (int)item.getAmount() + " " + item.getProduct().getUnit().substring(3) + ",   " + item.getTotal() + " kr\n";
            this.total = this.total + item.getTotal();
        }
        orderTextArea.setText(text);
        labelPurchaseDate.setText(order.getDate().toLocaleString());
        labelPurchaseTotal.setText(this.total + "");
    }
}
