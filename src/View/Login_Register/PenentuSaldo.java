package View.Login_Register;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PenentuSaldo {
    private Stage stage;
    private TextField fieldSaldo;
    private TextField fieldModeKritis;

    public PenentuSaldo(Stage stage) {
        this.stage = stage;
    }

    public void penentuSaldo() {
        this.fieldSaldo = new TextField();
        this.fieldModeKritis = new TextField();
        Text title = new Text();
        title.setText("Persiapan sebelum memulai dashboard");
        title.setTranslateY(-40);
        title.setFont(Font.font("Verdana", 20)); // Mengatur font dari text
        title.setFill(Color.BLACK); // Mengatur warna dari text

        fieldSaldo.setPromptText("Masukkan saldo awal");
        fieldSaldo.setTranslateY(-10);
        fieldSaldo.setMaxWidth(200);
        fieldSaldo.setTranslateX(200);

        fieldModeKritis.setPromptText("Masukkan mode kritis");
        fieldModeKritis.setTranslateY(10);
        fieldModeKritis.setMaxWidth(200);
        fieldModeKritis.setTranslateX(200);

        Label labelSaldo = new Label();
        labelSaldo.setText("Masukkan Saldo Kamu");
        labelSaldo.setTranslateY(-10);
        labelSaldo.setTranslateX(-200);

        Label labelModeKritis = new Label();
        labelModeKritis.setText("Masukkan Batasan Mode Kritis Mu");
        labelModeKritis.setTranslateY(10);
        labelModeKritis.setTranslateX(-200);

        Button inputNilai = new Button();
        inputNilai.setText("Masukkan Nilai");
        inputNilai.setTranslateY(40);
        inputNilai.setTranslateX(200);

        StackPane root = new StackPane();
        StackPane.setMargin(root, new Insets(20, 0, 0, 0));
        root.getChildren().addAll(title, fieldSaldo, fieldModeKritis, labelSaldo, labelModeKritis);
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Persiapan sebelum memulai dashboard");
        stage.setScene(scene);
        stage.show();

        // inputNilai.setOnAction(e -> );

    }

    private void handleInputSaldodanMode(int saldo, int modeKritis) {

    }
}
