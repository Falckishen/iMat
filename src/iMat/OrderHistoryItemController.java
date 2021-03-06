package iMat;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

public class OrderHistoryItemController extends AnchorPane {

    private final Order order;
    private double total;

    @FXML private Label labelPurchaseDate;
    @FXML private Label labelPurchaseTotal;
    @FXML private TextArea orderTextArea;
    @FXML private TextArea orderTextArea2;
    @FXML private TextArea orderTextArea3;


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
        StringBuilder textName = new StringBuilder();
        StringBuilder textAmount = new StringBuilder();
        StringBuilder textMoney = new StringBuilder();
        for (ShoppingItem item : order.getItems()) {
            textName.append(item.getProduct().getName()).append("\n");
            textAmount.append((int)item.getAmount()).append(" ").append(item.getProduct().getUnit().substring(3)).append("\n");
            textMoney.append(String.format("%.2f", item.getTotal())).append(" kr\n");
            total = total + item.getTotal();
        }
        orderTextArea.setText(textName.toString());
        orderTextArea2.setText(textAmount.toString());
        orderTextArea3.setText(textMoney.toString());
        labelPurchaseDate.setText(order.getDate().toString().substring(0, order.getDate().toString().length() - 8));
        labelPurchaseTotal.setText(total + "");
    }
}
