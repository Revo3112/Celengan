package View.Splash_Screen;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import java.util.ArrayList;
import java.util.List;
import Controller.SceneController;
import Model.DatabaseCheckService;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;

public class SplashScreen {
    private Stage stage; // deklarasi attribute stage
    private String assetPath = "Assets/View/Splash_Screen/"; // deklarasi path location utk kepentingan splash screen
                                                             // page
    private String imgPath = assetPath + "images"; // deklarasi path location utk images
    private List<Image> contents; // deklarasi content images (priority image)
    private List<Image> listChatsLeft; // deklarasi chats images (fill up image)
    private List<Image> listChatsRight; // deklarasi chats images (fill up image)

    private double xOffset = 0;
    private double yOffset = 0;
    private boolean isMousePressed = false;

    private Cursor defaultCursor = Cursor.cursor("DEFAULT");
    private Cursor closedHand = Cursor.cursor("CLOSED_HAND");
    private Cursor waitCursor = Cursor.cursor("WAIT");

    // constructor
    public SplashScreen(Window owner) {
        this.stage = new Stage(); // inisialisasi stage
        this.stage.initOwner(owner); // inisialisasi owner stage
        this.stage.initStyle(StageStyle.TRANSPARENT); // inisialisasi style stage (transparent)
    }

    // driver's code
    public void start(int fVal) {
        // pembuatan outBackground (outerPane) bagan paling luar sebagai base background
        Rectangle outBackground = createRectangle(900, 550, 60, 60, Color.rgb(20, 31, 35));
        /*
         * menggunakan persegin anjang dikarenakan dibutuhkan rounded corners
         */
        // membuat image untuk logo celengan dengan memanggil fungsi createImage (return
        // imageView type)
        ImageView logo = createImage(imgPath + "/logo/celengan_image_logo.png", 58, 58, -397, -215);
        // membuat text untuk logo celengan dengan font Poppins menggunakan fungsi
        // createText (return Text)
        Text logoText = createText("Celengan", "-fx-font: 36 Poppins;", "#FFFFFF", -268, -209);

        // membuat buttonMasuk menggunakan fungsi createImage
        Rectangle buttonMasuk = createRectangle(125, 46, 50, 50, Color.valueOf("#263940"));
        buttonMasuk.setTranslateX(350);
        buttonMasuk.setTranslateY(-209);
        // membuat text buttonMasuk menggunakan fungsi createText
        Text textButtonMasuk = createText("Masuk", "-fx-font: 24 Poppins;", "#141F23", buttonMasuk.getTranslateX(),
                -209);

        /* BAGIAN CAROUSEL */
        contents = new ArrayList<>(); // mendeklarasikan ArrayList untuk menyimpan contents images
        contents.add(new Image(imgPath + "/carousel/caro_1.png")); // menambahkan image carousel 1
        contents.add(new Image(imgPath + "/carousel/caro_2.png")); // menambahkan image carousel 2
        contents.add(new Image(imgPath + "/carousel/caro_3.png")); // menambahkan image carousel 3
        contents.add(new Image(imgPath + "/carousel/caro_4.png")); // menambahkan image carousel 4
        contents.add(new Image(imgPath + "/carousel/caro_5.png")); // menambahkan image carousel 5

        // menambahkan carousel chat image (tidak diperlukan sementara)
        listChatsLeft = new ArrayList<>(); // deklarasi list untuk chat kiri (1)
        listChatsRight = new ArrayList<>(); // deklarasi list untuk chat kanan (2)

        // menambahkan chat kiri (1)
        listChatsLeft.add(new Image(imgPath + "/carousel/chatCaro1_1.png"));
        listChatsLeft.add(new Image(imgPath + "/carousel/chatCaro1_2.png"));
        listChatsLeft.add(new Image(imgPath + "/carousel/chatCaro1_3.png"));
        listChatsLeft.add(new Image(imgPath + "/carousel/chatCaro1_4.png"));
        listChatsLeft.add(new Image(imgPath + "/carousel/chatCaro1_5.png"));

        // menambahkan chat kanan (2)
        listChatsRight.add(new Image(imgPath + "/carousel/chatCaro2_1.png"));
        listChatsRight.add(new Image(imgPath + "/carousel/chatCaro2_2.png"));
        listChatsRight.add(new Image(imgPath + "/carousel/chatCaro2_3.png"));
        listChatsRight.add(new Image(imgPath + "/carousel/chatCaro2_4.png"));
        listChatsRight.add(new Image(imgPath + "/carousel/chatCaro2_5.png"));

        // deklarasi object carousel untuk membuat carousel berdasarkan contents dari
        // class Carousel
        Carousel carousel = new Carousel(contents, listChatsLeft, listChatsRight);
        // deklarasi object loading sebagai instansiasi class Loading
        Loading loading = new Loading(stage);
        // deklarasi object loadingPane sebagai instansiasi pane dari StackPane
        StackPane loadingPane = loading.getLoadingPane();
        // deklarasi object carouselPane sebagai instansiasi pane dari StackPane
        StackPane carouselPane = carousel.getCarouselPane(); // pane yang digunakan untuk carousel saja
        // deklarasi object outPan sebagai instansiasi pane dari StackPane
        StackPane outPane = new StackPane(outBackground); // pane yang digunakan untuk menampung outBackground
        // deklarasi object mainContent sebagai instansiasi pane daro StackPane
        StackPane mainContent = new StackPane(loadingPane, carouselPane, buttonMasuk, textButtonMasuk,
                logo, logoText);

        loading.updateProgress(mainContent);
        fVal = loading.getSplashValue();

        // deklarasi object root sebagai instansiasi bagian paling dasar dan utama dari
        // pane
        StackPane root = new StackPane();
        // mengambil atau menambahkan seluruh pane yang akan ditambahkan ke dalam root
        root.getChildren().addAll(outPane, mainContent);

        // kondisi dimana roto draggable
        root.setOnMousePressed(e -> {
            if (e.isPrimaryButtonDown() && e.getClickCount() == 2) {
                root.getScene().setCursor(closedHand);
                isMousePressed = true;
                xOffset = e.getSceneX();
                yOffset = e.getSceneY();
            }
        });

        root.setOnMouseReleased(e -> {
            root.getScene().setCursor(defaultCursor);
            isMousePressed = false;
        });

        root.setOnMouseDragged(e -> {
            if (isMousePressed) {
                root.getScene().setCursor(closedHand);
                stage.setX(e.getScreenX() - xOffset);
                stage.setY(e.getScreenY() - yOffset);
            }
        });

        // menggunakan fungsi setOnMouseEntered untuk melakukan aksi saat user menghover
        // mouse
        buttonMasuk.setOnMouseEntered(event -> {
            buttonMasuk.getScene().setCursor(waitCursor);
            ImageView alertButton = createImage(imgPath + "/alert/alert.png", 113, 20, buttonMasuk.getTranslateX() + 30,
                    buttonMasuk.getTranslateY() + 30);
            textButtonMasuk.setOnMouseEntered(eventText -> {
                textButtonMasuk.getScene().setCursor(waitCursor);
                if (!mainContent.getChildren().contains(alertButton)) {
                    mainContent.getChildren().add(alertButton);
                }
            });

            // Check if alertButton is not already added before adding it
            if (!mainContent.getChildren().contains(alertButton)) {
                mainContent.getScene().setCursor(defaultCursor);
                mainContent.getChildren().add(alertButton);
            }
        });

        // menggunakan fungsi setOnMouseExited untuk mengetahui user tidak hover atau
        // belum
        buttonMasuk.setOnMouseExited(event -> {
            mainContent.getChildren().removeIf(node -> node instanceof ImageView
                    && ((ImageView) node).getImage().getUrl().contains("/alert/alert.png"));
        });
        // fungsi untuk mengetahui koordinat x dan y suatu Node
        // setOnMouseClicked(root, listChatsLeft.get(0));

        // deklarasi scene
        Scene scene = new Scene(root, Color.TRANSPARENT);
        // membuat scene untuk memakai font Poppins dari googleapis fonts
        scene.getStylesheets().addAll(
                "https://fonts.googleapis.com/css2?family=Poppins:wght@500;600;700&display=swap");

        // inisiasi style stage
        // stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene); // menetapkan scene dari sebuah stage
        stage.show(); // menampilkan stage

        // memulai animasi carousel
        carousel.startAnimation();

    }

    // fungsi untuk membuat persegi panjang dengan return Node persegi
    private Rectangle createRectangle(double width, double height, double arcWidth, double arcHeight, Color color) {
        Rectangle rect = new Rectangle(width, height);
        rect.setArcWidth(arcWidth);
        rect.setArcHeight(arcHeight);
        rect.setFill(color);

        return rect;
    }

    // fungsi untuk membuat image dengan return ImageView
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

    // fungsi untuk membuat text dengan return Text
    private Text createText(String text, String style, String color, double transX, double transY) {
        Text newText = new Text(text);
        newText.setStyle(style); // menetapkan style text
        newText.setFill(Color.valueOf(color)); // menetapkan warna text
        newText.setTranslateX(transX); // menetapkan posisi x pada text
        newText.setTranslateY(transY); // menetapkan posisi y pada text

        return newText;
    }

    // fungsi untuk menyembunyikan splash screen
    public void hideSplashScreen() {
        Platform.runLater(() -> {
            this.stage.hide();
        });
        // NB: digunakan untuk pergantian page
    }
}

// class Loading/progress bar (berisi tingkatan download yang sedang terjadi)
class Loading {
    private Stage stage;
    private Rectangle progressBar;
    private Rectangle pBarLight;
    private StackPane loadingPane;
    private Text statusText;
    private StackPane loadBar;
    private int splashValue = 0;
    private boolean trigger = false;
    int count = 0;

    public Loading(Stage stage) {
        this.stage = stage;
        progressBar = new Rectangle(0, 20); // 700 20
        pBarLight = new Rectangle(0, 6);
        loadingPane = createLoadingPane();
    }

    public StackPane getLoadingPane() {
        return loadingPane;
    }

    public void updateProgress(StackPane mainContent) {
        double targetWidth = 820;
        Duration initialDelay = Duration.seconds(1);
        Duration animationDuration = Duration.seconds(1);
        Duration pBarLightDelay = Duration.millis(100);

        // Update progressBar width and pBarLight width in a single timeline
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(progressBar.widthProperty(), 0),
                        new KeyValue(pBarLight.widthProperty(), 0)),
                new KeyFrame(initialDelay, // Initial delay before animation starts
                        new KeyValue(progressBar.widthProperty(), 0),
                        new KeyValue(pBarLight.widthProperty(), 0)),
                new KeyFrame(initialDelay.add(animationDuration),
                        new KeyValue(progressBar.widthProperty(), targetWidth),
                        new KeyValue(pBarLight.widthProperty(), targetWidth - 26)),
                new KeyFrame(animationDuration.add(pBarLightDelay),
                        new KeyValue(pBarLight.widthProperty(), 0)));

        // merubah button
        Rectangle buttonBG = new Rectangle(125, 46);
        buttonBG.setFill(Color.valueOf("#93D334"));
        buttonBG.setArcWidth(50);
        buttonBG.setArcHeight(50);
        buttonBG.setTranslateX(350);
        buttonBG.setTranslateY(-209);

        Rectangle buttonLBG = new Rectangle(buttonBG.getWidth(), buttonBG.getHeight() - 6);
        buttonLBG.setFill(Color.valueOf("#AEFD3A"));
        buttonLBG.setArcWidth(45);
        buttonLBG.setArcHeight(45);
        buttonLBG.setTranslateX(350);
        buttonLBG.setTranslateY(-211);

        Text enterText = new Text("Masuk");
        enterText.setStyle("-fx-font: 24 Poppins;");
        enterText.setFill(Color.valueOf("#141F23"));
        enterText.setTranslateX(buttonBG.getTranslateX());
        enterText.setTranslateY(-209);

        timeline.setOnFinished(event -> {
            statusText.setText("Selesai!");
            statusText.setFill(Color.valueOf("#93D334"));

            // Remove the alert button
            mainContent.getChildren().removeIf(node -> node instanceof ImageView
                    && ((ImageView) node).getImage().getUrl().contains("/alert/alert.png"));

            // Add the new button image
            mainContent.getChildren().addAll(buttonBG, buttonLBG, enterText);

        });
        timeline.play();
        DatabaseCheckService databaseCheckService = new DatabaseCheckService();

        databaseCheckService.setOnSucceeded(e -> {
            this.count = databaseCheckService.getValue();
        });
        buttonLBG.addEventHandler(MouseEvent.MOUSE_CLICKED, MouseEvent1 -> {
            handleDatabaseCheck(databaseCheckService, this.count);
        });

        enterText.addEventHandler(MouseEvent.MOUSE_CLICKED, MouseEvent -> {
            handleDatabaseCheck(databaseCheckService, this.count);
        }); // format dah coba
        databaseCheckService.start(); // Menjalankan operasi pengecekan database
    }

    private void handleDatabaseCheck(DatabaseCheckService databaseCheckService, int count) {
        SplashScreen splashScreen = new SplashScreen(this.stage);
        Stage splashStage = new Stage();
        splashStage.initStyle(StageStyle.TRANSPARENT);
        // Instansiasi class SceneController ke dalam variabel mainScene
        SceneController mainScene = new SceneController(splashStage);
        // Menentukan tampilan berikutnya berdasarkan hasil pengecekan
        if (count == 0) {
            mainScene.switchToRegistration(); // Jika database kosong, maka tampilkan halaman registrasi
        } else {
            mainScene.switchToLogin(); // Jika database tidak kosong, maka tampilkan halaman login
        }
        // Menutup splash screen setelah operasi selesai
        splashScreen.hideSplashScreen();
        hideSplashScreen2();

    }

    public int getSplashValue() {
        // System.out.println("value =" + splashValue);
        return splashValue;
    }

    public boolean getBoolSV() {
        // System.out.println("trigger =" + trigger);
        return trigger;
    }

    private StackPane createLoadingPane() {

        Rectangle loadingRect = new Rectangle(820, 20);
        loadingRect.setArcWidth(25);
        loadingRect.setArcHeight(25);
        loadingRect.setFill(Color.valueOf("#263940"));

        progressBar.setArcWidth(25);
        progressBar.setArcHeight(25);
        progressBar.setFill(Color.valueOf("#93D334"));
        progressBar.setTranslateX(-400);

        pBarLight.setArcWidth(15);
        pBarLight.setArcHeight(15);
        pBarLight.setFill(Color.valueOf("#A9DB5D"));
        pBarLight.setTranslateX(progressBar.getTranslateX() + 10);
        pBarLight.setTranslateY(progressBar.getTranslateY() - 3);

        statusText = new Text("Memuat...");
        statusText.setStyle("-fx-font: 22 Poppins;");
        statusText.setFill(Color.valueOf("#263940"));
        statusText.setTranslateX(350);
        statusText.setTranslateY(progressBar.getTranslateY() - 30);

        loadBar = new StackPane(progressBar, pBarLight);
        loadBar.setAlignment(Pos.CENTER_LEFT);
        loadBar.setTranslateX(440);

        StackPane loadingPane = new StackPane(loadingRect, loadBar, statusText);
        loadingPane.setTranslateY(250);
        return loadingPane;
    }

    public void hideSplashScreen2() {
        Platform.runLater(() -> {
            this.stage.hide();
        });
        // NB: digunakan untuk pergantian page
    }
}

// class Carousel (berisi metode pengembangan carousel)
class Carousel {
    private List<Image> contents; // penetapan konten utama
    private List<Image> chatsLeft; // penetapan konten chats kiri (1)
    private List<Image> chatsRight; // penetapan konten chats kanan (2)
    private StackPane carouselPane; // penetapan bagian panel carousel
    private int currentIndex = 0; // penetapan index sekarang
    private int chatIdx_1 = 0; // penetapan index chat kiri (1)
    private int chatIdx_2 = 0; // penetapan index chat kanan (2)
    private int[] chatPosIdxX_1 = { -188, -170, -130, -200, -160 }; // koordinat x chat kiri (1)
    private int[] chatPosIdxY_1 = { -60, 100, 110, -90, 100 }; // koordinat y chat kiri (1)
    private int[] chatPosIdxX_2 = { 150, 180, 100, 110, 210 }; // koordinat x chat kanan (2)
    private int[] chatPosIdxY_2 = { 110, -80, -110, -80, -110 }; // koordinat y chat kanan (2)
    private double dragStartX; // penetapan mulainya posisi koordinat x
    public static final double CARO_IMAGE_WIDTH = 400; // konstanta panjang gambar carousel
    public static final double CARO_IMAGE_HEIGHT = 400; // konstanta tinggi gambar carousel
    public static final double CHAT_IMAGE_WIDTH = 176; // tetapan konstan untuk lebar chat carousel
    public static final double CHAT_IMAGE_HEIGHT = 72; // tetapan konstan untuk tinggi chat carousel

    private static final double DRAG_THRESHOLD = 20.0; // konstantan threshold dari drag user

    private HBox dotContainer; // penetapan kontainer untuk dot carousel
    final Color activeDots = Color.valueOf("#AEFD3A"); // kontanta warna dot yang aktif
    final Color passiveDots = Color.valueOf("#263940"); // kontantan warna dot yang pasif (tidak aktif)

    // constructor
    public Carousel(List<Image> contents, List<Image> chatsLeft, List<Image> chatsRight) {
        this.contents = contents; // inisialisasi konten utama
        this.chatsLeft = chatsLeft; // inisialisasi konten chats kiri (1)
        this.chatsRight = chatsRight; // inisialiasi konten chats kanan (2)
        initializeCarousel(); // memanggil fungsi untuk menginisialisasi Carousel
    }

    // fungsi untuk memanggil panel carousel
    public StackPane getCarouselPane() {
        return carouselPane;
    }

    // fungsi untuk memulai animasi carousel
    public void startAnimation() {
        // Menambahkan handler drag event
        carouselPane.setOnMousePressed(event -> {
            dragStartX = event.getSceneX();
        });

        carouselPane.setOnMouseDragged(event -> {
            carouselPane.setOnMouseReleased(event2 -> {
                double deltaX = event.getSceneX() - dragStartX;
                showImageByDrag(event, deltaX);
                dragStartX = 0; // me reset value dragStartX jika mouse sudah lepas klik
            });
        });
    }

    // fungsi untuk menginisiasi isi dari panel carousel
    private void initializeCarousel() {
        carouselPane = new StackPane(); // membuat object baru carouselPane
        carouselPane.setPrefSize(CARO_IMAGE_WIDTH, CARO_IMAGE_HEIGHT); // menetapkan ukuran preferensi

        // menginisiasi gambar konten utama
        ImageView initialImage = createResizedImageView(contents.get(currentIndex), CARO_IMAGE_WIDTH,
                CARO_IMAGE_HEIGHT);
        // menginisiasi gambar konten chats kiri (1)
        ImageView initialChatsLeft = createResizedImageView(chatsLeft.get(chatIdx_1), CHAT_IMAGE_WIDTH,
                CHAT_IMAGE_HEIGHT);
        // menginisiasi gambar konten chats kanan (2)
        ImageView initialChatsRight = createResizedImageView(chatsRight.get(chatIdx_2), CHAT_IMAGE_WIDTH,
                CHAT_IMAGE_HEIGHT);
        // ImageView initialChatsRight =
        // createResizedImageView(chatsRight.get(currentIndex), CARO_IMAGE_WIDTH,
        // CARO_IMAGE_HEIGHT);
        /*
         * TABEL INDEX & TX,TY
         * idx -> (tx, ty)
         * 0 -> (-188, -60)
         */
        // left
        initialChatsLeft.setTranslateX(chatPosIdxX_1[chatIdx_1]);
        initialChatsLeft.setTranslateY(chatPosIdxY_1[chatIdx_1]);
        // right
        initialChatsRight.setTranslateX(chatPosIdxX_2[chatIdx_2]);
        initialChatsRight.setTranslateY(chatPosIdxY_2[chatIdx_2]);

        // transisi scale untuk chat kiri (1)
        ScaleTransition chatsLeftTransition = new ScaleTransition(Duration.millis(750), initialChatsLeft);
        chatsLeftTransition.setToX(1.1);
        chatsLeftTransition.setToY(1.1);
        chatsLeftTransition.setAutoReverse(true);
        chatsLeftTransition.setCycleCount(ScaleTransition.INDEFINITE);
        chatsLeftTransition.play();

        // transisi scale untuk chat kanan (2)
        ScaleTransition chatsRightTransition = new ScaleTransition(Duration.millis(750), initialChatsRight);
        chatsRightTransition.setToX(1.1);
        chatsRightTransition.setToY(1.1);
        chatsRightTransition.setAutoReverse(true);
        chatsRightTransition.setCycleCount(ScaleTransition.INDEFINITE);
        chatsRightTransition.play();

        // inisiasi stackpane ketiga elemen
        StackPane initContent = new StackPane(initialImage, initialChatsLeft, initialChatsRight);
        // menambahkan gambar yang sudah diinisiasikan sebagai konten utama tiap halaman
        carouselPane.getChildren().add(initContent);

        // membuat dot indikator
        dotContainer = createDotIndicators(contents.size());
        StackPane.setAlignment(dotContainer, Pos.BOTTOM_CENTER); // preferensi dot indikator harus ditengah
        carouselPane.getChildren().add(dotContainer); // menambahkan dotContainer ke dalam panel Carousel
    }

    // fungsi untuk membuat dot indikator berdasarkan jumlah dot
    private HBox createDotIndicators(int numDots) {
        HBox dots = new HBox(20); // Spasi antara dot satu dengan lainnya
        dots.setAlignment(Pos.CENTER); // membuat dot agar tetap di tengah
        dots.setTranslateY(200); // menetapkan posisi koordinat Y dari dot

        for (int i = 0; i < numDots; i++) {
            Circle dot = new Circle(5); // menetapkan jumlah dot (5)
            // melakukan ternary kondisi untuk menentukan warna dot aktif atau pasif
            dot.setFill(i == 0 ? activeDots : passiveDots); // menghighlight dot yang utama (yang sedang dilihat user)
            dots.getChildren().add(dot); // menambahkan dot
        }

        return dots;
    }

    // fungsi untuk mengupdate indikator dot
    private void updateDotIndicators() {
        for (Node dot : dotContainer.getChildren()) {
            ((Circle) dot).setFill(passiveDots); // mengubah warna dot menjadi warna pasif
        }

        // mengubah warna dot menjadi aktif sesuai dengan index sekarang
        ((Circle) dotContainer.getChildren().get(currentIndex)).setFill(activeDots);
    }

    // fungsi untuk menampilkan gambar berdasarkan drag user
    private void showImageByDrag(MouseEvent event, double deltaX) {
        double totalDeltaX = Math.abs(deltaX); // mengubah deltaX (perubaha posisi kooordinat x user menjadi nilai yang
                                               // absolut (positif)) agar dapat diproses

        // kondisi jika value dari totalDeltaX lebih besar dari value DRAG_THRESHOLD
        if (totalDeltaX > DRAG_THRESHOLD) {
            // jika deltaX positif
            if (deltaX > 0) {
                // mencatat bahwa user menggeser ke kanan
                currentIndex = (currentIndex - 1 + contents.size()) % contents.size();
                chatIdx_1 = (chatIdx_1 - 1 + chatsLeft.size()) % chatsLeft.size();
                chatIdx_2 = (chatIdx_2 - 1 + chatsRight.size()) % chatsRight.size();
            } else {
                // mencatat bahwa user menggeser ke kiri
                currentIndex = (currentIndex + 1) % contents.size();
                chatIdx_1 = (chatIdx_1 + 1) % chatsLeft.size();
                chatIdx_2 = (chatIdx_2 + 1) % chatsRight.size();
            }

            // membuat image untuk konten utama dengan opacity 0.0
            ImageView mainContentImage = createResizedImageView(contents.get(currentIndex), CARO_IMAGE_WIDTH,
                    CARO_IMAGE_HEIGHT);
            mainContentImage.setOpacity(0.0);
            // membuat image chat kiri (1) dengan opacity 0.0
            ImageView chatLeftImage = createResizedImageView(chatsLeft.get(chatIdx_1), CHAT_IMAGE_WIDTH,
                    CHAT_IMAGE_HEIGHT);
            // membuat image chat kanan (2) dengan opacity 0.0
            ImageView chatRightImage = createResizedImageView(chatsRight.get(chatIdx_2), CHAT_IMAGE_WIDTH,
                    CHAT_IMAGE_HEIGHT);

            // menetapkan posisi koordinat x dan y chatLeftImage (1)
            chatLeftImage.setTranslateX(chatPosIdxX_1[chatIdx_1]);
            chatLeftImage.setTranslateY(chatPosIdxY_1[chatIdx_1]);
            // menetapkan posisi koordinat x dan y chatRightImage (2)
            chatRightImage.setTranslateX(chatPosIdxX_2[chatIdx_2]);
            chatRightImage.setTranslateY(chatPosIdxY_2[chatIdx_2]);

            StackPane contentFull = new StackPane(mainContentImage, chatLeftImage, chatRightImage);
            // menambahkan transisi fade out sebagai animasi antar gambar carousel
            FadeTransition fadeOut = new FadeTransition(Duration.millis(80),
                    carouselPane.getChildren().get(0));
            fadeOut.setFromValue(1.0); // nilai awal fade
            fadeOut.setToValue(0.0); // nilai akhir fade
            // keyframe yang terjadi saat sudah selesai fadeOut
            fadeOut.setOnFinished(e -> {
                // Mengupdate fadeout dengan image tertentu
                carouselPane.getChildren().set(0, contentFull);

                // menambahkan animasi fadeIN (untuk image selanjutnya)
                FadeTransition fadeIn = new FadeTransition(Duration.millis(80), mainContentImage);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();

                // transisi chat kiri (1)
                ScaleTransition leftChatTransition = new ScaleTransition(Duration.millis(750), chatLeftImage);
                leftChatTransition.setToX(1.1);
                leftChatTransition.setToY(1.1);
                leftChatTransition.setAutoReverse(true);
                leftChatTransition.setCycleCount(ScaleTransition.INDEFINITE);
                leftChatTransition.play();

                // transisi chat kanan (2)
                ScaleTransition rightChatTransition = new ScaleTransition(Duration.millis(750), chatRightImage);
                rightChatTransition.setToX(1.1);
                rightChatTransition.setToY(1.1);
                rightChatTransition.setAutoReverse(true);
                rightChatTransition.setCycleCount(ScaleTransition.INDEFINITE);
                rightChatTransition.play();

                // mengupdate dot indikator
                updateDotIndicators();
            });
            fadeOut.play();

            // mereset dragStartX dari awal
            dragStartX = event.getSceneX();
        }
    }

    // fungsi untuk membuat gambar yang di resize
    private ImageView createResizedImageView(Image image, double width, double height) {
        ImageView imageView = new ImageView(image); // mendeklarasikan gambar
        imageView.setFitWidth(width); // menetapkan panjang image
        imageView.setFitHeight(height); // menetapkan tinggi image
        imageView.setPreserveRatio(true); // menetapkan ratio yang ideal
        return imageView; // mengembalikan image yang sudah dimodifikasi.
    }
}