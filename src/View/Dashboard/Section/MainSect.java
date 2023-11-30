package View.Dashboard.Section;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainSect {
    Stage stage;
    StackPane mainPane;

    public MainSect(Stage stage) {
        this.mainPane = mainPane;
        this.stage = stage;
    }

    // showing the main section
    private StackPane getMainSect() {
        // membuat text
        Text welcome = createText("Selamat Datang,", "-fx-font: 30 Poppins-Regular;", "#FFFFF", -143, -250);
        // membuat pane sesi welcome
        StackPane welcomePage = new StackPane(welcome);
        // membuat main pane
        mainPane = new StackPane(welcomePage);
        mainPane.setMaxSize(this.stage.getWidth() - 200, this.stage.getHeight() - 100);
        mainPane.setStyle("-fx-background-color: #141F23; -fx-background-radius: 20;");
        mainPane.setPadding(new Insets(10, 10, 10, 10));
        Scene scene = new Scene(mainPane, 750, 500);
        scene.getStylesheets().addAll(
                "https://fonts.googleapis.com/css2?family=Poppins:wght@500;600;700&display=swap");

        // Menanggapi perubahan ukuran layar
        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> {
            double stageWidth = this.stage.getWidth();
            double stageHeight = this.stage.getHeight();

            // Menyesuaikan ukuran dan posisi StackPane
            mainPane.setMaxSize(stageWidth - 200, stageHeight - 100);

            welcome.setTranslateY(-stageHeight / 2 + 107); // Menggunakan proporsi dengan tinggi awal 600
            welcome.setTranslateX(-stageWidth / 2 + 250); // Menggunakan proporsi dengan lebar awal 750

            // // Menyesuaikan posisi tombol
            // btnTanamUang.setFont(Font.font("Verdana", (stageWidth / 30) - 20));
            // btnTanamUang.setTranslateY(stageHeight / 8);
            // System.out.println(stageHeight);
        };

        // Menambahkan listener ke lebar dan tinggi stage
        this.stage.widthProperty().addListener(stageSizeListener);
        this.stage.heightProperty().addListener(stageSizeListener);

        this.stage.setScene(scene);
        this.stage.setMinHeight(500);
        this.stage.setMinWidth(750);

        return mainPane;
    }

    // fungsi untuk membuat text dengan return Text
    public Text createText(String text, String style, String color, double transX, double transY) {
        Text newText = new Text(text);
        newText.setStyle(style); // menetapkan style text
        newText.setFill(Color.valueOf(color)); // menetapkan warna text
        newText.setTranslateX(transX); // menetapkan posisi x pada text
        newText.setTranslateY(transY); // menetapkan posisi y pada text

        return newText;
    }
}