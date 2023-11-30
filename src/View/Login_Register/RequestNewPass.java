package View.Login_Register;

import Controller.SceneController;
import Model.RequestNewPassword;
import Utils.AlertHelper;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RequestNewPass {
    private Stage stage;
    private TextField fieldUsername;
    private TextField fieldPinCode;
    private PasswordField fieldNewPassword;

    public RequestNewPass(Stage stage) {
        this.stage = stage;
    }

    public void start() {
        Text title = new Text();
        title.setText("Request New Password");
        title.setFont(Font.font("Verdana", 20)); // Mengatur font dari text
        title.setFill(Color.BLACK); // Mengatur warna dari text
        title.setTranslateY(-40); // Mengatur koordinat y dari text

        fieldUsername = new TextField();
        fieldUsername.setPromptText("Username");
        fieldUsername.setMaxWidth(200);
        fieldUsername.setTranslateX(90);

        fieldNewPassword = new PasswordField();
        fieldNewPassword.setPromptText("New Password");
        fieldNewPassword.setMaxWidth(200);
        fieldNewPassword.setTranslateX(90);
        fieldNewPassword.setTranslateY(40);

        fieldPinCode = new TextField();
        fieldPinCode.setPromptText("Pin Code");
        fieldPinCode.setMaxWidth(200);
        fieldPinCode.setTranslateX(90);
        fieldPinCode.setTranslateY(80);

        Label labelUsername = new Label("Username");
        labelUsername.setTranslateX(-50);
        Label labelPassword = new Label("New Password");
        labelPassword.setTranslateX(-50);
        labelPassword.setTranslateY(40);
        Label labelPinCode = new Label("Pin Code");
        labelPinCode.setTranslateX(-50);
        labelPinCode.setTranslateY(80);

        Button buttonRequest = new Button("Request");
        buttonRequest.setTranslateX(90);
        buttonRequest.setTranslateY(120);
        buttonRequest.setOnAction(e -> {
            try {
                RequestNewPassword requestNewPassword = new RequestNewPassword();
                boolean status = requestNewPassword.checkData(fieldUsername.getText(), fieldPinCode.getText(),
                        fieldNewPassword.getText());
                System.out.println(status);
                if (status) {
                    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmationAlert.setHeaderText("Password berhasil diubah");
                    confirmationAlert.setContentText("Silahkan login kembali");
                    confirmationAlert.setTitle("Success");
                    confirmationAlert.show();
                    SceneController sceneController = new SceneController(this.stage);
                    sceneController.switchToLogin();
                } else {
                    AlertHelper alert = new AlertHelper();
                    alert.alert("Username atau Pin Code salah");
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        Button buttonBack = new Button("Back");
        buttonBack.setTranslateX(90);
        buttonBack.setTranslateY(160);
        buttonBack.setOnAction(e -> {
            SceneController sceneController = new SceneController(this.stage);
            sceneController.switchToLogin();
        });

        // Membuat root container menggunakan StackPane
        StackPane root = new StackPane();
        StackPane.setMargin(root, new Insets(20, 0, 0, 0)); // Mengatur margin dari StackPane
        root.getChildren().addAll(title, fieldUsername, fieldNewPassword, fieldPinCode, labelUsername, labelPassword,
                labelPinCode, buttonRequest, buttonBack); // Menambahkan semua komponen ke dalam root

        // Membuat scene dan menambahkan root ke dalam scene
        Scene scene = new Scene(root, 500, 500, Color.gray(0.2));
        this.stage.setScene(scene); // Menambahkan scene ke dalam stage
        this.stage.setTitle("Request New Password");
        // this.stage.show(); // Menampilkan stage

    }

}
