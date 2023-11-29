package View.Dashboard;

import View.Dashboard.Section.MainSect;
import Controller.SceneController;
import Model.LoginModel;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

// class DashboardPage digunakan untuk menampilkan halaman dashboard
public class DashboardPage {

    private Stage stage; // Deklarasi property stage
    private String username;

    // Melakukan inisiasi class DashboardPage dengan parameter stage
    public DashboardPage(Stage stage) {
        this.stage = stage; // Instansiasi property stage dengan parameter stage
        this.username = getUsername();
    }

    // Menampilkan halaman dashboard
    public void start() {
        // deklarasi welcome section
        // Text welcomeText = new Text("Selamat Datang,\n " + username + "!");
        // welcomeText.setStyle("-fx-font: 30 Poppins-Regular; -fx-font-weight:
        // regular;");
        // welcomeText.setTranslateY(-143);
        // welcomeText.setTranslateX(-250);
        // welcomeText.setFill(Color.WHITE);

        // Button btnTanamUang = new Button("Tanam Uang Pengeluaran");
        // btnTanamUang.setTranslateY(100);

        // btnTanamUang.setOnMouseClicked(e -> {
        // SceneController sceneController = new SceneController(this.stage);
        // sceneController.switchToTanamUangPengeluaran();
        // });

        // StackPane mainPane = new StackPane();
        // mainPane.setStyle("-fx-background-color: #141F23; -fx-background-radius:
        // 20;");
        // mainPane.setPadding(new Insets(10, 10, 10, 10));
        // mainPane.setMaxSize(this.stage.getWidth() - 200, this.stage.getHeight() -
        // 100);
        // mainPane.getChildren().addAll(welcomeText, btnTanamUang);

        StackPane mainPane;
        mainPane = MainSect(stage);

        StackPane root = new StackPane(mainPane);
        root.setStyle("-fx-background-color: #0D1416;");

        Scene scene = new Scene(root, Color.TRANSPARENT);

        // Menampilkan stage
        this.stage.show();

        // Mouse COORDINATES TRACKER: fungsi untuk mencetak koordinat x dan y dari
        // sebuah mouse yang diklik
        setOnMouseClicked(root);
    }

    // fungsi membuat text

    // Mouse COORDINATES TRACKER: fungsi untuk mencetak koordinat x dan y dari
    // sebuah mouse yang diklik
    private void setOnMouseClicked(StackPane root, Node item) {
        root.setOnMouseClicked(new javafx.event.EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent me) {
                double x = me.getSceneX();
                double y = me.getSceneY();

                // mengkalkulasi posisi translasi x dan y untuk mendapatkan posisi yang ideal
                double itemX = x - (root.getWidth() / 2); // untuk menetapkan pada tengah node root
                double itemY = y - (root.getHeight() / 2); // untuk menetapkan pada tengah node root

                // menetapkan posisi baru untuk item
                item.setTranslateX(itemX); // posisi baru untuk koordinat x
                item.setTranslateY(itemY); // posisi baru untuk kooordinat y

                System.out.println("Item placed at X -> " + itemX);
                System.out.println("Item placed at Y -> " + itemY);
            }
        });

    }

    private String getUsername() {
        LoginModel loginModel = new LoginModel();
        System.out.println(loginModel.getLastActiveUsers());
        return this.username = loginModel.getLastActiveUsers();
    }
}
