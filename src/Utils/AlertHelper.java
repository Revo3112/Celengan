package Utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

// Pembuatan class AlertHelper untuk dapat digunakan berkali-kali
public class AlertHelper {
    public static void alert(String message) { // Method alert untuk menampilkan alert
        Alert alert = new Alert(AlertType.ERROR); // Instansiasi class Alert ke dalam variabel alert
        alert.setContentText(message); // Mengatur isi dari alert
        alert.showAndWait(); // Menampilkan alert
    }
}
