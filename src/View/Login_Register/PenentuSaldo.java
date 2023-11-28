package View.Login_Register;

import Controller.SceneController;
import Model.InputSaldodanMode;
import Model.LoginModel;
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
    private int user_id;

    public PenentuSaldo(Stage stage) {
        this.stage = stage;
        LoginModel user = new LoginModel();
        this.user_id = user.getUserId();
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
        fieldSaldo.setTranslateY(0);
        fieldSaldo.setMaxWidth(200);
        fieldSaldo.setTranslateX(0);

        fieldModeKritis.setPromptText("Masukkan mode kritis");
        fieldModeKritis.setTranslateY(40);
        fieldModeKritis.setMaxWidth(200);
        fieldModeKritis.setTranslateX(0);

        Label labelsaldo = new Label("Masukkan Saldo awal");
        labelsaldo.setTranslateY(0);
        labelsaldo.setTranslateX(-200);

        Label labelModeKritis = new Label("Masukkan Mode Kritis");
        labelModeKritis.setTranslateY(40);
        labelModeKritis.setTranslateX(-200);

        Button inputNilai = new Button("Submit");
        inputNilai.setTranslateY(80);
        inputNilai.setTranslateX(200);

        inputNilai.setOnAction(e -> {
            int saldo = Integer.parseInt(fieldSaldo.getText());
            int modeKritis = Integer.parseInt(fieldModeKritis.getText());
            handleInputSaldodanMode(saldo, modeKritis);
        });

        StackPane root = new StackPane();
        root.setPadding(new Insets(10, 10, 10, 10));
        root.getChildren().addAll(title, fieldSaldo, labelsaldo, fieldModeKritis, labelModeKritis, inputNilai);

        Scene scene = new Scene(root, 500, 500);
        this.stage.setScene(scene);
        this.stage.show();

    }

    private void handleInputSaldodanMode(int saldo, int modeKritis) {
        InputSaldodanMode inputSaldodanMode = new InputSaldodanMode(saldo, modeKritis, this.user_id);
        if (inputSaldodanMode.setSaldo()) {
            SceneController sceneController = new SceneController(this.stage);
            sceneController.switchToDashboard();
        } else {
            System.out.println("Gagal");
        }
    }
}
