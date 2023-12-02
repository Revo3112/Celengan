package Utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

// Pembuatan class AlertHelper untuk dapat digunakan berkali-kali
public class AlertHelper {
    public static void alert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("oops!");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void info(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
