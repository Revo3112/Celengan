package View.Login_Register;

import Controller.SceneController;
import Model.*;
import Utils.AlertHelper;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

// membuat class LoginPage
public class LoginPage {
    private Stage stage; // Deklarasi property stage
    private boolean statusCheckbox; // Deklarasi property statusCheckbox
    private TextField fieldUsername; // Deklarasi property fieldUsername
    private PasswordField passwordField; // Deklarasi property passwordField
    private boolean isDragged = false;
    private double xOffset = 0;
    private double yOffset = 0;
    private boolean isMinimized = false;
    private double mmcSectYPos = -370;
    private StackPane root;

    private Cursor closedHand = Cursor.cursor("CLOSED_HAND");
    private Cursor defaultCursor = Cursor.cursor("DEFAULT");

    // Melakukan inisiasi class LoginPage dengan parameter stage
    public LoginPage(Stage stage) {
        this.stage = stage; // Instansiasi property stage dengan parameter stage

        // Set the application icon
        Image icon = new Image("Assets/View/Login_Register/_89099b4b-e95d-49ca-91c4-2a663e06b72a.jpg");
        this.stage.getIcons().add(icon); // Menambahkan icon ke dalam stage
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
        // getting screen resolution
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        // Background for outer pane
        Rectangle background = new Rectangle(bounds.getWidth(), bounds.getHeight());
        background.setFill(Color.valueOf("#141F23"));
        background.setArcHeight(30);
        background.setArcWidth(30);

        // MMC Section
        Rectangle mmcSect = new Rectangle(bounds.getWidth(), 30);
        mmcSect.setArcHeight(30);
        mmcSect.setArcWidth(30);
        mmcSect.setTranslateY(mmcSectYPos);
        mmcSect.setFill(Color.valueOf("#141F23"));
        // Class MaxMinClose
        MaxMinClose maxMinClose = new MaxMinClose(stage, root);
        LoginSystem loginSystem = new LoginSystem(stage, root);
        DropDown dropDown = new DropDown(stage, root);

        Node dropDownElements = dropDown.callDropDown();
        StackPane loginSystemPane = loginSystem.callLogSysPane();
        StackPane mmcElements = maxMinClose.callMMC();

        maxMinClose.listenMMC();
        mmcElements.setTranslateX(640);
        mmcElements.setTranslateY(mmcSect.getTranslateY() + 30);
        // StackPane dropdown
        // StackPane of mmc
        StackPane mmc = new StackPane(mmcSect, mmcElements);
        dropDownElements.setMouseTransparent(true);

        // background declaration
        StackPane outPane = new StackPane(background);
        // inside declaration
        StackPane inPane = new StackPane(mmc, dropDownElements, loginSystemPane);
        // Membuat root container menggunakan StackPane
        StackPane root = new StackPane(outPane, inPane);
        // listening

        // Root condition
        // Draggable root window
        mmcSect.setOnMouseDragged(e -> {
            root.getScene().setCursor(closedHand);
            isDragged = true;
            stage.setX(e.getScreenX() - xOffset);
            stage.setY(e.getScreenY() - yOffset);
        });

        mmcSect.setOnMouseReleased(e -> {
            root.getScene().setCursor(defaultCursor);
            isDragged = false;
        });
        // Minimize by double click
        mmcSect.setOnMousePressed(e -> {
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
            if (e.isPrimaryButtonDown() && e.getClickCount() == 2) {
                if (isMinimized) {
                    // Maximize
                    isMinimized = false;
                    if (isDragged) {
                        stage.setX(0);
                        stage.setY(0);
                    }
                    background.setWidth(bounds.getWidth());
                    background.setHeight(bounds.getHeight());
                    // mmc pref
                    mmcSect.setTranslateY(mmcSectYPos);
                    mmcSect.setWidth(background.getWidth());
                    mmcSect.setHeight(30);
                    // mmcElements
                    mmcElements.setTranslateX(640);
                    mmcElements.setTranslateY(mmcSectYPos + 25);
                    resizeChildren(mmcElements, 10, true);
                } else {
                    // Minimize
                    isMinimized = true;
                    background.setWidth(bounds.getWidth() - 400);
                    background.setHeight(bounds.getHeight() - 200);
                    // mmc pref
                    mmcSect.setTranslateY(mmcSectYPos + 100);
                    mmcSect.setWidth(background.getWidth());
                    mmcSect.setHeight(30);
                    // mmcElements
                    mmcElements.setTranslateY(mmcSectYPos + 120);
                    mmcElements.setTranslateX(440);
                    resizeChildren(mmcElements, -10, false);
                }
            }
        });

        // Membuat Scene dengan latar belakang transparent
        Scene scene = new Scene(root, Color.TRANSPARENT);

        // Memberikan judul pada stage
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setMaximized(true);
        stage.setScene(scene); // menetapkan scene dari sebuah stage
        stage.show(); // menampilkan stage

        /* END OF UI/UX */

        // this.fieldUsername = new TextField(); // Kode ini akan membuat TextField baru
        // this.passwordField = new PasswordField(); // Konstruktor PasswordField untuk
        // membuat PasswordField baru

        // // Components
        // Text title = new Text(); // Membuat objek text
        // title.setText("Please Login"); // Mengatur isi dari text
        // title.setFont(Font.font("Verdana", 20)); // Mengatur font dari text
        // title.setFill(Color.BLACK); // Mengatur warna dari text
        // title.setTranslateY(-40); // Mengatur posisi dari text pada sumbu y

        // fieldUsername.setMaxWidth(200); // Mengatur lebar maximum dari field username
        // fieldUsername.setTranslateX(200); // Mengatur posisi x dari field username

        // // Mengatur lebar maximum dari field password
        // passwordField.setMaxWidth(200);

        // // Mengatur posisi x dari field password
        // passwordField.setTranslateX(200);

        // // Mengatur posisi y dari field password
        // passwordField.setTranslateY(40);

        // // Membuat label "Username:"
        // Label labelUsername = new Label("Username:");
        // labelUsername.setTranslateX(-50); // Mengatur posisi x dari label username

        // // Membuat label "Password:"
        // Label labelPassword = new Label("Password:");
        // labelPassword.setTranslateX(-50); // Mengatur posisi x dari label password
        // labelPassword.setTranslateY(40); // Mengatur posisi y dari label password

        // // Membuat Hyperlink "Register" untuk pindah ke halaman registrasi
        // Hyperlink registerLink = new Hyperlink("Register");
        // registerLink.setTranslateX(280); // Mengatur posisi x dari registerlink
        // registerLink.setTranslateY(80); // Mengatur posisi y dari registerlink
        // registerLink.setStyle( // Mengatur style pada tulisan register link
        // "-fx-underline: true;" +
        // "-fx-font-family: Verdana");
        // registerLink.setOnAction(e -> { // Menginisiasi ketika registet link di tekan
        // SceneController sceneController = new SceneController(this.stage); // Membuat
        // objek scenecontroller
        // sceneController.switchToRegistration(); // Merubah stage menggunakan scene
        // controller menuju registrasi
        // });

        // Hyperlink forgotPasswordLink = new Hyperlink("Forgot Password?");
        // forgotPasswordLink.setTranslateX(280);
        // forgotPasswordLink.setTranslateY(120);
        // forgotPasswordLink.setStyle(
        // "-fx-underline: true;" +
        // "-fx-font-family: Verdana");
        // forgotPasswordLink.setOnAction(e -> {
        // SceneController sceneController = new SceneController(this.stage);
        // sceneController.switchToRequestNewPassword();
        // });

        // // Membuat CheckBox "Remember Me"
        // CheckBox checkbox = new CheckBox("Remember Me");
        // checkbox.setTranslateX(-30); // mengatur posisi x pada checkbox
        // checkbox.setTranslateY(80); // mengatur posisi y pada checkbox
        // checkbox.setOnAction(e -> { // Menjalankan kondisi ketika checkbox ditekan
        // if (checkbox.isSelected()) {
        // this.statusCheckbox = true; // Merubah status checkbox menjadi true
        // } else {
        // this.statusCheckbox = false; // Merubah status checkbox menjadi false
        // }
        // });

        // // Membuat Button "Login"
        // Button btnLogin = new Button("Login");
        // btnLogin.setTranslateY(120); // Mengatur posisi y dari tombol login
        // Mengatur margin dari StackPane
        // Menambahkan semua item kedalam Stackpane
        // root.getChildren().addAll(title, labelUsername, fieldUsername, labelPassword,
        // passwordField, registerLink,
        // forgotPasswordLink,
        // checkbox,
        // btnLogin);

        // Menangani klik tombol "Login"
        // btnLogin.setOnAction(e -> handleLogin(fieldUsername.getText(),
        // passwordField.getText()));

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
    Stage stage;
    StackPane root;
    StackPane logSysPane;

    public LoginSystem(Stage stage, StackPane root) {
        this.stage = stage;
        this.root = root;
        logSysPane = createLoginSystemPane();
    }

    public StackPane callLogSysPane() {
        return logSysPane;
    }

    private StackPane createLoginSystemPane() {
        Rectangle base = new Rectangle(600, 400);
        base.setArcWidth(30);
        base.setArcHeight(30);
        base.setFill(Color.valueOf("#0F181B"));
        base.setTranslateY(base.getTranslateY() - 30);

        Rectangle mainBase = new Rectangle(600, 400);
        mainBase.setArcWidth(30);
        mainBase.setArcHeight(30);
        mainBase.setFill(Color.valueOf("#263940"));
        mainBase.setStroke(Color.valueOf("#0F181B"));
        mainBase.setStrokeWidth(3);
        mainBase.setTranslateY(base.getTranslateY() - 15);

        StackPane logSysElement = new StackPane(base, mainBase);
        logSysPane = new StackPane(logSysElement);
        return logSysPane;
    }
}

class DropDown {
    private Stage stage;
    private StackPane root;
    private Node dropDownPane;

    public DropDown(Stage stage, StackPane root) {
        this.stage = stage;
        this.root = root;
        dropDownPane = createDropDown();
    }

    public Node callDropDown() {
        return dropDownPane;
    }

    private ImageView createImage(String imgPath, double width, double height, double transX, double transY) {

        ImageView imageView = new ImageView();
        Image imageImg = new Image(imgPath);

        imageView.setImage(imageImg); // menetapkan gambar
        imageView.setFitWidth(width); // menetapkan panjang gambar
        imageView.setFitHeight(height); // menetapkan tinggi gambar
        imageView.setTranslateX(transX); // menetapkan posisi x gambar
        imageView.setTranslateY(transY); // menetapkan posisi y gambar

        return imageView;
    }

    private Node createDropDown() {
        // base
        Rectangle base = new Rectangle(140, 40);
        base.setFill(Color.valueOf("#111A1E"));
        base.setStroke(Color.valueOf("#263940"));
        base.setStrokeWidth(3);
        base.setArcWidth(50);
        base.setArcHeight(50);
        // text
        Text loginText = new Text("Login");
        loginText.setStyle("-fx-font: 23 Poppins;");
        loginText.setFill(Color.valueOf("#AB77FF"));
        loginText.setTranslateX(loginText.getTranslateX() - 10);

        // triangle
        ImageView triangle = createImage("Assets/View/Login_Register/dropdown_icon.png", 20, 15,
                loginText.getTranslateX() + 52,
                loginText.getTranslateY() + 3);

        // stackPane
        StackPane dropDownElement = new StackPane(base, loginText, triangle);
        dropDownElement.setTranslateX(-575);
        dropDownElement.setTranslateY(-340);
        dropDownPane = new StackPane(dropDownElement);
        return dropDownPane;
    }
}

class MaxMinClose {
    Stage stage;
    StackPane root;

    StackPane mmcPane;
    Rectangle buttonClose;
    Rectangle buttonClose_S;
    Text closeSign;
    Rectangle buttonMax;
    Rectangle buttonMax_S;
    Rectangle maximizeSign;
    Rectangle buttonMin;
    Rectangle buttonMin_S;
    Rectangle minimizeSign;

    private Cursor defaultCursor = Cursor.cursor("DEFAULT");
    private Cursor handCursor = Cursor.cursor("HAND");

    public MaxMinClose(Stage stage, StackPane root) {
        this.stage = stage;
        this.root = root;
        mmcPane = createMMC();
        listenMMC();
    }

    public StackPane callMMC() {
        return mmcPane;
    }

    private StackPane createMMC() {
        // CLOSE BUTTON ASSET
        buttonClose = new Rectangle(45, 45);
        buttonClose.setArcWidth(90);
        buttonClose.setArcHeight(90);
        buttonClose.setFill(Color.valueOf("#FF4646"));
        // shadow
        buttonClose_S = new Rectangle(buttonClose.getWidth(), buttonClose.getHeight());
        buttonClose_S.setArcWidth(90);
        buttonClose_S.setArcHeight(90);
        buttonClose_S.setTranslateX(buttonClose.getTranslateX());
        buttonClose_S.setTranslateY(buttonClose.getTranslateY() + 5);
        buttonClose_S.setFill(Color.valueOf("#9A2727"));
        // sign
        closeSign = new Text("X");
        closeSign.setStyle("-fx-font: 26 Poppins;");
        closeSign.setFill(Color.valueOf("#141F23"));
        closeSign.setTranslateX(buttonClose.getTranslateX());
        closeSign.setTranslateY(buttonClose.getTranslateY());

        // MAXIMIZE BUTTON ASSET
        buttonMax = new Rectangle(45, 45);
        buttonMax.setArcWidth(90);
        buttonMax.setArcHeight(90);
        buttonMax.setTranslateX(buttonClose.getTranslateX() - 52);
        buttonMax.setFill(Color.WHITE);
        //
        buttonMax_S = new Rectangle(buttonMax.getWidth(), buttonMax.getHeight());
        buttonMax_S.setArcWidth(90);
        buttonMax_S.setArcHeight(90);
        buttonMax_S.setTranslateX(buttonMax.getTranslateX());
        buttonMax_S.setTranslateY(buttonMax.getTranslateY() + 5);
        buttonMax_S.setFill(Color.valueOf("#9B9B9B"));
        //
        maximizeSign = new Rectangle(buttonMax.getWidth() - 30, buttonMax.getHeight() - 30);
        maximizeSign.setFill(Color.TRANSPARENT);
        maximizeSign.setStroke(Color.valueOf("#141F23"));
        maximizeSign.setStrokeWidth(5);
        maximizeSign.setTranslateX(buttonMax.getTranslateX());
        maximizeSign.setTranslateY(buttonMax.getTranslateY());

        // MINIMIZE BUTTON ASSET
        buttonMin = new Rectangle(45, 45);
        buttonMin.setArcWidth(90);
        buttonMin.setArcHeight(90);
        buttonMin.setTranslateX(buttonMax.getTranslateX() - 52);
        buttonMin.setFill(Color.WHITE);
        //
        buttonMin_S = new Rectangle(buttonMin.getWidth(), buttonMin.getHeight());
        buttonMin_S.setArcWidth(90);
        buttonMin_S.setArcHeight(90);
        buttonMin_S.setTranslateX(buttonMin.getTranslateX());
        buttonMin_S.setTranslateY(buttonMin.getTranslateY() + 5);
        buttonMin_S.setFill(Color.valueOf("#9B9B9B"));
        //
        minimizeSign = new Rectangle(buttonMin.getWidth() - 20, 5);
        minimizeSign.setFill(Color.valueOf("#141F23"));
        minimizeSign.setTranslateX(buttonMin.getTranslateX());
        minimizeSign.setTranslateY(buttonMin.getTranslateY());

        // Pane each button

        mmcPane = new StackPane(buttonClose_S, buttonClose, closeSign, buttonMax_S, buttonMax, maximizeSign,
                buttonMin_S, buttonMin, minimizeSign);
        return mmcPane;
    }

    public void listenMMC() {
        // Making sure buttonClose is not mouse-transparent
        buttonClose.setMouseTransparent(false);

        buttonClose.setOnMouseClicked(event -> {
            System.out.println("Closing APP!");
            stage.hide();
        });

        // Setting cursor and handling mouse events for the close button
        buttonClose.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> root.setCursor(handCursor));
        buttonClose.addEventHandler(MouseEvent.MOUSE_EXITED, e -> root.setCursor(defaultCursor));
    }
}
