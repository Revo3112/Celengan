package View.Login_Register;

import Controller.SceneController;
import Model.*;
import Utils.AlertHelper;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

// membuat class LoginPage
public class LoginPage {
    private Stage stage; // Deklarasi property stage
    private boolean statusCheckbox; // Deklarasi property statusCheckbox
    private TextField fieldUsername; // Deklarasi property fieldUsername
    private PasswordField passwordField; // Deklarasi property passwordField

    private Cursor hand = Cursor.cursor("HAND");
    private Cursor closedHand = Cursor.cursor("CLOSED_HAND");
    private Cursor defaultCursor = Cursor.cursor("DEFAULT");

    private boolean isMousePressed = false;
    private boolean isWindowMax = true;
    private double xOffset = 0;
    private double yOffset = 0;

    StackPane root = new StackPane(); // stackpane root

    // Melakukan inisiasi class LoginPage dengan parameter stage
    public LoginPage(Stage stage) {
        this.stage = stage; // Instansiasi property stage dengan parameter stage

        // Set the application icon
        Image icon = new Image("Assets/View/Splash_Screen/images/logo/celengan_image_logo.png");
        this.stage.getIcons().add(icon); // Menambahkan icon ke dalam stage
        this.stage.setTitle("Celengan");
    }

    public void penentuApakahStartAtauLangsungDashboard() {
        Boolean penentuAkhir = false;
        LoginModel login = new LoginModel(); // Membuat objek login
        penentuAkhir = login.penentuBagianLastUser();
        if (penentuAkhir) {
            SceneController sceneController = new SceneController(this.stage); // Membuat objek sceneController
            sceneController.switchToDashboard(); // Merubah scene menuju dashboard
        } else {
            this.start(); // Menjalankan method start()
        }
    }

    // Menampilkan halaman login
    public void start() {
        /* UI/UX : NULLPTR */
        /* BAGIAN MMC (MINIMIZE, MAXIMIZE, CLOSE) */
        // membuat closeButton
        Button closeButton = createButton(35, 35, "X", "FF4646", 23, "Poppins", 30, "0F181B");

        // membuat maximizeButton
        Button maximizeButton = createButton(35, 35, " ", "FFFFFF", 23, "Poppins", 30, "0F181B");
        // set graphic untuk label maximize
        Rectangle maxIcon = new Rectangle(14, 14);
        maxIcon.setFill(Color.TRANSPARENT);
        maxIcon.setStroke(Color.valueOf("#0F181B"));
        maxIcon.setStrokeWidth(4);
        maxIcon.setTranslateX(maximizeButton.getTranslateX() + 5);
        maximizeButton.setGraphic(maxIcon);

        // membuat minimizeButton
        Button minimizeButton = createButton(35, 35, "-", "FFFFFF", 23, "Poppins", 30, "0F181B");

        // BUTTON EVENTS
        // CLOSE BUTTON: saat dipencet maka close button akan menstop aplikasi
        closeButton.setOnMouseClicked(closeEvent -> stage.hide());
        // saat di hover maka cursor berbeda
        closeButton.setOnMouseEntered(closeEvent -> {
            closeButton.getScene().setCursor(hand);
            updateButton(closeButton, 35, 35, "X", "6A1B1B", 23, "Poppins", 30, "0F181B");
        });
        closeButton.setOnMouseExited(closeEvent -> {
            closeButton.getScene().setCursor(defaultCursor);
            updateButton(closeButton, 35, 35, "X", "FF4646", 23, "Poppins", 30, "0F181B");
        });

        // MAXIMIZE BUTTON: saat dipencet maka akan mengecilkan windows
        maximizeButton.setOnMouseClicked(maxEvent -> {
            if (stage.isMaximized()) {
                stage.setMaximized(false);
                isWindowMax = false;
                System.out.println("IsWindMax =" + isWindowMax);
                root.setOnMousePressed(e -> {
                    if (e.isPrimaryButtonDown() && e.getClickCount() == 2 && !isWindowMax) {
                        root.getScene().setCursor(closedHand);
                        isMousePressed = true;
                        xOffset = e.getSceneX();
                        yOffset = e.getSceneY();
                    }
                });
                // saat dilepas
                root.setOnMouseReleased(e -> {
                    root.getScene().setCursor(defaultCursor);
                    isMousePressed = false;
                });
                // saat ditarik
                root.setOnMouseDragged(e -> {
                    if (isMousePressed && !isWindowMax) {
                        root.getScene().setCursor(closedHand);
                        stage.setX(e.getScreenX() - xOffset);
                        stage.setY(e.getScreenY() - yOffset);
                    }
                });
            } else {
                isWindowMax = true;
                System.out.println("IsWindMax =" + isWindowMax);
                stage.setMaximized(true);
            }
        });

        // saat di hover maka cursor berbeda
        maximizeButton.setOnMouseEntered(closeEvent -> {
            maximizeButton.getScene().setCursor(hand);
            updateButton(maximizeButton, 35, 35, "", "959595", 23, "Poppins", 30, "0F181B");
            maxIcon.setTranslateX(maximizeButton.getTranslateX());
            maximizeButton.setGraphic(maxIcon);
        });
        maximizeButton.setOnMouseExited(closeEvent -> {
            maximizeButton.getScene().setCursor(defaultCursor);
            updateButton(maximizeButton, 35, 35, "", "FFFFFF", 23, "Poppins", 30, "0F181B");
            maxIcon.setTranslateX(maximizeButton.getTranslateX());
            maximizeButton.setGraphic(maxIcon);
        });

        // MINIMIZE BUTTON: saat dipencet akan menutup sementara window
        minimizeButton.setOnMouseClicked(maxEvent -> {
            stage.setIconified(true);
        });
        // saat di hover maka cursor berbeda
        minimizeButton.setOnMouseEntered(closeEvent -> {
            minimizeButton.getScene().setCursor(hand);
            updateButton(minimizeButton, 35, 35, "-", "959595", 23, "Poppins", 30, "0F181B");
        });
        minimizeButton.setOnMouseExited(closeEvent -> {
            minimizeButton.getScene().setCursor(defaultCursor);
            updateButton(minimizeButton, 35, 35, "-", "FFFFFF", 23, "Poppins", 30, "0F181B");
        });

        /* BAGIAN DROPDOWN LOGIN */
        // membuat dropdown login
        SplitMenuButton dropDownButton = new SplitMenuButton();
        MenuItem toLoginBtn = new MenuItem("Login Page");
        MenuItem toRegisterBtn = new MenuItem("Register Page");
        MenuItem toRNPBtn = new MenuItem("Request New Password");
        // dropdownButton styling
        dropDownButton.setText("Login Page");
        dropDownButton.getItems().addAll(toRegisterBtn, toRNPBtn);
        dropDownButton.setStyle(
                "-fx-background-radius: 10, 10, 10, 10;" + // Set corner radius for all corners
                        "-fx-color: #111A1E;" +
                        "-fx-background-color: #263940;" +
                        "-fx-font: 15 Poppins;" +
                        "-fx-text-fill: #AB77FF;" +
                        "-fx-pref-width: 140;" + // Set preferred width
                        "-fx-min-width: 140;" + // Set minimum width
                        "-fx-max-width: 140;" + // Set maximum width
                        "-fx-pref-height: 50;" + // Set preferred height
                        "-fx-min-height: 50;" + // Set minimum height
                        "-fx-max-height: 50;" + // Set maximum height
                        "-fx-border-radius: 30;" +
                        "-fx-border-color: #263940;");
        // register button styling

        // BUTTON EVENTS LOGIN DROPDOWN: logic yang terjadi untuk button dropdown
        dropDownButton.setOnAction(event -> {
            // change this
            System.out.println("On Login Page!");
        });
        toRegisterBtn.setOnAction(event -> {
            swapButton(dropDownButton, toRegisterBtn, "Register");
            System.out.println("On Register Page!");
        });

        toRNPBtn.setOnAction(event -> {
            swapButton(dropDownButton, toRNPBtn, "Request New Password");
            System.out.println("On Request New Password Page!");
        });

        toLoginBtn.setOnAction(event -> {
            swapButton(dropDownButton, toLoginBtn, "Login");
            System.out.println("To Login Page!");
        });

        // BUTTON ORGANIZER
        // memasukkan button ke dalam HBox
        HBox mmcButton = new HBox(5); // 5 spacing
        mmcButton.getChildren().addAll(minimizeButton, maximizeButton, closeButton);
        mmcButton.setMaxWidth(150);
        mmcButton.setAlignment(Pos.TOP_RIGHT);
        // memasukkan dropdown ke dalam HBox
        HBox dropDownLogin = new HBox(20);
        dropDownLogin.getChildren().add(dropDownButton);
        dropDownLogin.setAlignment(Pos.TOP_LEFT);
        // menggabungkan kedua HBox ke dalam satu HBox
        HBox topElements = new HBox(500);
        topElements.getChildren().addAll(dropDownLogin, mmcButton);
        topElements.setStyle("-fx-padding: 16, 16, 0, 16;"); // padding: top left bottom right
        HBox.setHgrow(dropDownLogin, Priority.ALWAYS);
        topElements.setAlignment(Pos.TOP_RIGHT);
        // menggabungkan topElements ke section
        VBox topSection = new VBox(topElements);

        /* BAGIAN MAIN CONTENT */
        // membuat base main content
        Rectangle backgroundBase = createRect(550, 360, 60, 60, "0F181B");
        Rectangle mainBase = createRect(backgroundBase.getWidth(), backgroundBase.getHeight(), 60, 60,
                "263940");
        // menambahkan stroke pada mainBase
        mainBase.setStroke(Color.valueOf("#0F181B"));
        mainBase.setStrokeWidth(3);
        // mengubah posisi mainBase agar turun sedikit dari backgroundBase
        mainBase.setTranslateY(backgroundBase.getTranslateY() - 15);
        // menggabungkan backgroundBase dengan mainBase menjadi satu group
        Group base = new Group(backgroundBase, mainBase);
        // MAIN ELEMENTS: elemen di dalam main base
        // USERNAME TEXT: bagian informasi text username dan icon
        Text username = createText("username", 25, "Poppins", "AD7AFF");
        ImageView usr_icon = createImage("/Assets/View/Login_Register/username_icon.png", 40, 40);
        // menambah masing-masing username elements ke hbox
        HBox usernameText = new HBox(5);
        usernameText.getChildren().addAll(usr_icon, username);
        // USERNAME FIELD: bagian yang dapat diinput username
        TextField inputUsername = new TextField();
        inputUsername.setMaxWidth(mainBase.getWidth() - 80);
        inputUsername.setMaxHeight(120);
        inputUsername.setStyle(
                "-fx-background-radius: 30;" +
                        "-fx-background-color: #141F23;" +
                        "-fx-font: 23 Poppins;" +
                        "-fx-text-fill: white;");
        // PASSWORD TEXT: bagian informasi text password dan icon
        Text password = createText("password", 25, "Poppins", "AD7AFF");
        ImageView pwd_icon = createImage("/Assets/View/Login_Register/password_icon.png", 40, 40);
        // menambah masing-masing elemen password elements ke hbox
        HBox passwordText = new HBox(5);
        passwordText.getChildren().addAll(pwd_icon, password);
        // PASSWORD FIELD: bagian yang dapat diinput password
        PasswordField inputPassword = new PasswordField();
        inputPassword.setMaxWidth(mainBase.getWidth() - 80);
        inputPassword.setMaxHeight(120);
        inputPassword.setStyle(
                "-fx-background-radius: 30;" +
                        "-fx-background-color: #141F23;" +
                        "-fx-font: 23 Poppins;" +
                        "-fx-text-fill: white;");
        // ADDITIONAL OPTIONS: berisi lupa password? dan ingatkan saya
        Hyperlink lupaPassword = new Hyperlink("Lupa Password?");
        lupaPassword.setStyle(
                "-fx-text-fill: #FF7474;" +
                        "-fx-font: 14 Poppins;");
        CheckBox ingatkanSaya = new CheckBox("Ingatkan Saya");
        ingatkanSaya.setStyle(
                "-fx-text-fill: #AD7AFF;" +
                        "-fx-font: 14 Poppins;");
        // menggabungkan elemen addoptions
        HBox addOption = new HBox(200);
        addOption.getChildren().addAll(lupaPassword, ingatkanSaya);
        // BUTTON LOGIN: seluruh tombol masuk dan belum punya akun?
        // button login
        Button masukLogin = createButton(100, 50, "Masuk", "AB77FF", 22, "Poppins", 30, "141F23");
        Rectangle masukLoginBG = new Rectangle();

        // button register
        Button registButton = createButton(350, 50, "Belum Punya Akun?", "141F23", 22, "Poppins", 30, "AB77FF");
        Rectangle registButtonBG = new Rectangle();

        // menyatukan elemen login ke satu group
        Group loginButton = new Group(masukLogin, masukLoginBG);
        // menyatukan elemen register ke satu group
        Group registerButton = new Group(registButton, registButtonBG);
        // memasukkan loginButton ke HBox
        HBox loginBtn = new HBox();
        loginBtn.getChildren().add(loginButton);
        // memasukkan registerButton ke HBox
        HBox registBtn = new HBox();
        registBtn.getChildren().add(registerButton);
        // menggabungkan keduanya ke VBox
        HBox loginRegistButton = new HBox();
        loginRegistButton.getChildren().addAll(loginBtn, registBtn);

        // menggabungkan seluruh elements
        VBox contentElements = new VBox();
        contentElements.getChildren().addAll(usernameText, inputUsername, passwordText, inputPassword, addOption,
                loginRegistButton);
        contentElements.setAlignment(Pos.CENTER);
        contentElements.setStyle("-fx-padding: 30;");

        // LOGO: logo celengan
        // inisiasi logo
        ImageView logo = createImage("/Assets/View/Splash_Screen/images/logo/celengan_image_logo.png", 60, 60);
        // inisiasi logo text
        Text logoText = createText("Celengan", 40, "Poppins", "FFFFFF");
        // memasukkan keduanya di HBox
        HBox logoElements = new HBox(5);
        logoElements.getChildren().addAll(logo, logoText);
        logoElements.setAlignment(Pos.BOTTOM_CENTER);
        // menggabungkan semua elemen logo ke stackPane
        StackPane logoCelengan = new StackPane(logoElements);
        logoCelengan.setMaxHeight(50);
        logoCelengan.setTranslateY(logoCelengan.getTranslateY() + 270);

        // memasukkan seluruh elemen ke dalam stackPane mainContent
        StackPane mainContent = new StackPane(base, contentElements, logoCelengan);
        mainContent.setMaxSize(600, 300);
        // mainContent.setStyle("-fx-background-color: red");

        // root stackPane
        root.getChildren().addAll(topSection, mainContent);
        root.setStyle("-fx-background-color: #141F23;");

        // Membuat Scene dengan latar belakang transparent
        Scene scene = new Scene(root);
        scene.getStylesheets()
                .addAll("https://fonts.googleapis.com/css2?family=Poppins:wght@500;600;700&display=swap");
        // Memberikan judul pada stage
        // stage.initStyle(StageStyle.UNDECORATED);
        stage.setMaximized(true);
        stage.setScene(scene); // menetapkan scene dari sebuah stage
        stage.show(); // menampilkan stage

        /* END OF UI/UX */
    }

    /* BUTTON METHODS */
    // method untuk membuat background button rectangular

    // method untuk menukar button
    private void swapButton(SplitMenuButton dropDownButton, MenuItem selectedItem, String buttonText) {
        dropDownButton.setText(buttonText);
        dropDownButton.getItems().remove(selectedItem);
        dropDownButton.getItems().add(0, selectedItem);
    }

    // method untuk mengupdate button
    private void updateButton(Button btn, int width, int height, String text, String bgColor,
            int fontSize, String font,
            int radius, String textFill) {
        btn.setPrefSize(width, height);
        btn.setText(text);
        btn.setStyle(
                "-fx-background-color: #" + bgColor + ", transparent; " +
                        "-fx-font: " + fontSize + " " + font + "; " +
                        "-fx-text-fill: #" + textFill + ";" +
                        "-fx-background-radius: " + radius + ";" +
                        "-fx-padding: 0;");
    }

    // method untuk membuat button
    private Button createButton(int width, int height, String text, String bgColor, int fontSize,
            String font,
            int radius, String textFill) {
        Button button = new Button();
        button.setPrefSize(width, height);
        button.setText(text);
        button.setStyle(
                "-fx-background-color: #" + bgColor + ", transparent; " +
                        "-fx-font: " + fontSize + " " + font + "; " +
                        "-fx-text-fill: #" + textFill + ";" +
                        "-fx-background-radius: " + radius + ";" +
                        "-fx-padding: 0;");
        return button;
    }

    // method membuat background button
    private Circle backgroundButton(int radius, String valueBgCol, double d, double e) {
        Circle backgroundBtn = new Circle();
        backgroundBtn.setRadius(radius - 10);
        backgroundBtn.setFill(Color.valueOf("#" + valueBgCol));
        backgroundBtn.setTranslateX(d);
        backgroundBtn.setTranslateY(e);
        return backgroundBtn;
    }

    /* MAIN CONTENT METHODS */
    // method membuat Image
    private ImageView createImage(String imgPath, double width, double height) {

        ImageView imageView = new ImageView();
        Image imageImg = new Image(imgPath);

        imageView.setImage(imageImg); // menetapkan gambar
        imageView.setFitWidth(width); // menetapkan panjang gambar
        imageView.setFitHeight(height); // menetapkan tinggi gambar

        return imageView;
    }

    // method membuat text
    private Text createText(String text, int textSize, String textFont, String textFill) {
        Text txt = new Text(text);
        txt.setStyle("-fx-font: " + textSize + " " + textFont + ";");
        txt.setFill(Color.valueOf("#" + textFill));
        return txt;
    }

    // method membuat rectangle
    private Rectangle createRect(double width, double height, double arcWidth, double arcHeight,
            String fill) {
        Rectangle rect = new Rectangle(width, height);
        rect.setArcWidth(arcWidth);
        rect.setArcHeight(arcHeight);
        rect.setFill(Color.valueOf("#" + fill));
        return rect;
    }

    private void resizeChildren(Pane parent, double deltaSize, boolean isBack) {
        for (Node node : parent.getChildren()) {
            if (node instanceof Rectangle) {
                Rectangle rect = (Rectangle) node;
                if (rect.getArcWidth() != 0) {
                    rect.setWidth(rect.getWidth() + deltaSize);
                    rect.setHeight(rect.getHeight() + deltaSize);
                }
            } else if (node instanceof Text) {
                Text text = (Text) node;
                if (isBack) {
                    text.setStyle("-fx-font: 26 Poppins;");
                } else {
                    text.setStyle("-fx-font: 20 Poppins;");
                }
            }
            // Add more conditions for other node types if needed
        }
    }

    // Metode untuk melakukan pengecekan ke database dan kondisi
    private void handleLogin(String username, String password) {
        LoginModel login = new LoginModel(); // Membuat objek login
        SceneController sceneController = new SceneController(this.stage); // Membuat objek sceneController

        // Mengatur kondisi login antara username dan password baik itu menuju database
        // atau mengecek kondisi field
        if (login.isValidated(username, password, statusCheckbox)) {
            if (login.penentuApakahSudahAdaSaldo()) {
                sceneController.switchToDashboard(); // Merubah scene menuju dashboard
            } else {
                sceneController.switchToBuatSaldoDanModeKritis(); // Merubah scene menuju BuatSaldoDanModeKritis
            }
        } else if (username.isEmpty() && password.isEmpty()) {
            // Memanggil metode untuk mengimplementasikan error style
            applyErrorStyle(fieldUsername, passwordField);
        } else if (username.isEmpty()) {
            // Mereset error style passwordField menjadi normal
            passwordField.setStyle("");
            // Memanggil metode untuk mengimplementasikan error style
            applyErrorStyle(fieldUsername);
        } else if (password.isEmpty()) {
            // Mereset error style fieldUsername menjadi normal
            fieldUsername.setStyle("");
            // Memanggil metode untuk mengimplementasikan error style
            applyErrorStyle(passwordField);
        } else {
            // Mereset error style fieldUsername menjadi normal
            fieldUsername.setStyle("");
            // Mereset error style passwordField menjadi normal
            passwordField.setStyle("");
            // Memunculkan pesan kesalahan
            AlertHelper.alert("Username or password is incorrect.");
        }

    }

    // Membuat metode untuk menerapkan error style
    private void applyErrorStyle(TextField... fields2) { // Menampung berbagai macam field
        for (TextField field : fields2) { // Melakukan loop untuk setiap field yang ada
            // Mengimplementasikan error style ke setiap field
            field.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        }
    }
}

class LoginSystem {
}

class DropDown {

}