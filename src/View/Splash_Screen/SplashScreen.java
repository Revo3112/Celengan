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

import javafx.event.EventHandler;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.FadeTransition;

public class SplashScreen {
    private Stage stage; // deklarasi attribute stage
    private String assetPath = "Assets/View/Splash_Screen/"; // deklarasi path location utk kepentingan splash screen
                                                             // page
    private String imgPath = assetPath + "images"; // deklarasi path location utk images
    private List<Image> contents; // deklarasi content images (priority image)
    private List<Image> chats; // deklarasi chats images (fill up image)

    public static final double CARO_IMAGE_WIDTH = 100; // tetapan konstan untuk lebar image carousel
    public static final double CARO_IMAGE_HEIGHT = 100; // tetapan konstan untuk tinggi image carousel
    // constructor

    public SplashScreen(Window owner) {
        this.stage = new Stage(); // inisialisasi stage
        this.stage.initOwner(owner); // inisialisasi owner stage
        this.stage.initStyle(StageStyle.TRANSPARENT); // inisialisasi style stage (transparent)
    }

    // driver's code
    public void start() {
        // pembuatan outBackground (outerPane) bagan paling luar sebagai base background
        Rectangle outBackground = createRectangle(900, 550, 60, 60, Color.rgb(20, 31, 35)); // menggunakan persegin
                                                                                            // panjang dikarenakan
                                                                                            // dibutuhkan rounded
                                                                                            // corners

        // membuat image untuk logo celengan dengan memanggil fungsi createImage (return
        // imageView type)
        ImageView logo = createImage(imgPath + "/logo/celengan_image_logo.png", 58, 58, -397, -215);
        // membuat text untuk logo celengan dengan font Poppins menggunakan fungsi
        // createText (return Text)
        Text logoText = createText("Celengan", "-fx-font: 36 Poppins;", "#FFFFFF", -268, -209);

        // membuat buttonMasuk menggunakan fungsi createImage
        ImageView buttonMasuk = createImage(imgPath + "/button/masuk.png", 95, 40, 345, -213);

        /* BAGIAN CAROUSEL */
        contents = new ArrayList<>(); // mendeklarasikan ArrayList untuk menyimpan contents images
        contents.add(new Image(imgPath + "/carousel/caro_1.png")); // menambahkan image carousel 1
        contents.add(new Image(imgPath + "/carousel/caro_2.png")); // menambahkan image carousel 2
        contents.add(new Image(imgPath + "/carousel/caro_3.png")); // menambahkan image carousel 3
        contents.add(new Image(imgPath + "/carousel/caro_4.png")); // menambahkan image carousel 4
        contents.add(new Image(imgPath + "/carousel/caro_5.png")); // menambahkan image carousel 5

        // menambahkan carousel chat image (tidak diperlukan sementara)
        // chats = new ArrayList<>();
        // chats.add(new Image(imgPath + "/carousel/chatCaro1_1.png"));
        // chats.add(new Image(imgPath + "/carousel/chatCaro1_2.png"));
        // chats.add(new Image(imgPath + "/carousel/chatCaro2_1.png"));
        // chats.add(new Image(imgPath + "/carousel/chatCaro2_2.png"));
        // chats.add(new Image(imgPath + "/carousel/chatCaro3_1.png"));
        // chats.add(new Image(imgPath + "/carousel/chatCaro3_2.png"));
        // chats.add(new Image(imgPath + "/carousel/chatCaro4_1.png"));
        // chats.add(new Image(imgPath + "/carousel/chatCaro4_2.png"));
        // chats.add(new Image(imgPath + "/carousel/chatCaro5_1.png"));
        // chats.add(new Image(imgPath + "/carousel/chatCaro5_2.png"));

        // deklarasi object carousel untuk membuat carousel berdasarkan contents dari
        // class Carousel
        Carousel carousel = new Carousel(contents);
        // deklarasi object carouselPane sebagai instansiasi pane dari StackPane
        StackPane carouselPane = carousel.getCarouselPane(); // pane yang digunakan untuk carousel saja
        // deklarasi object outPan sebagai instansiasi pane dari StackPane
        StackPane outPane = new StackPane(outBackground); // pane yang digunakan untuk menampung outBackground
        // deklarasi object mainContent sebagai instansiasi pane daro StackPane
        StackPane mainContent = new StackPane(carouselPane, buttonMasuk, logo, logoText); // pane ini digunakan untuk
                                                                                          // menampung konten - konten
                                                                                          // utama

        // deklarasi object root sebagai instansiasi bagian paling dasar dan utama dari
        // pane
        StackPane root = new StackPane();
        // mengambil atau menambahkan seluruh pane yang akan ditambahkan ke dalam root
        root.getChildren().addAll(outPane, mainContent);
        // menggunakan fungsi setOnMouseEntered untuk melakukan aksi saat user menghover
        // mouse
        buttonMasuk.setOnMouseEntered(event -> {
            ImageView alertButton = createImage(imgPath + "/alert/alert.png", 120, 50, buttonMasuk.getTranslateX(),
                    buttonMasuk.getTranslateY() + 50);
            // menambahkan alertButton
            mainContent.getChildren().add(alertButton);
        });

        // menggunakan fungsi setOnMouseExited untuk mengetahui user tidak hover atau
        // belum
        buttonMasuk.setOnMouseExited(event -> {
            mainContent.getChildren().removeIf(node -> node instanceof ImageView
                    && ((ImageView) node).getImage().getUrl().contains("/alert/alert.png"));
        });
        // fungsi untuk mengetahui koordinat x dan y suatu Node
        // setOnMouseClicked(root, buttonMasuk);

        // deklarasi scene
        Scene scene = new Scene(root, Color.TRANSPARENT);
        // membuat scene untuk memakai font Poppins dari googleapis fonts
        scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Poppins:wght@700&display=swap");

        // inisiasi style stage
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene); // menetapkan scene dari sebuah stage
        stage.show(); // menampilkan stage

        // memulai animasi carousel
        carousel.startAnimation();

    }

    // Mouse COORDINATES TRACKER: fungsi untuk mencetak koordinat x dan y dari
    // sebuah mouse yang diklik
    private void setOnMouseClicked(StackPane root, Node item) {
        root.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
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
        System.out.println("IMGPATH: " + imgPath);

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
            stage.hide();
        });
        // NB: digunakan untuk pergantian page
    }
}

// class Carousel (berisi metode pengembangan carousel)
class Carousel {
    private List<Image> contents; // penetapan konten utama
    private StackPane carouselPane; // penetapan bagian panel carousel
    private int currentIndex = 0; // penetapan index sekarang
    private double dragStartX; // penetapan mulainya posisi koordinat x
    private boolean isDragging = false; // penetapan kondisi apakah user mendrag atau tidak

    public static final double CARO_IMAGE_WIDTH = 400; // konstanta panjang gambar carousel
    public static final double CARO_IMAGE_HEIGHT = 400; // konstanta tinggi gambar carousel

    private static final double DRAG_THRESHOLD = 20.0; // konstantan threshold dari drag user

    private HBox dotContainer; // penetapan kontainer untuk dot carousel
    final Color activeDots = Color.valueOf("#AEFD3A"); // kontanta warna dot yang aktif
    final Color passiveDots = Color.valueOf("#263940"); // kontantan warna dot yang pasif (tidak aktif)

    // constructor
    public Carousel(List<Image> contents) {
        this.contents = contents; // inisialisasi konten utama
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
            isDragging = true;
        });

        carouselPane.setOnMouseDragged(event -> {
            carouselPane.setOnMouseReleased(event2 -> {
                double deltaX = event.getSceneX() - dragStartX;
                showImageByDrag(event, deltaX);
                isDragging = false; // mereset kondisi isDragging jika kondisi false
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
        // menambahkan gambar yang sudah diinisiasikan sebagai konten utama tiap halaman
        carouselPane.getChildren().add(initialImage);

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
            } else {
                // mencatat bahwa user menggeser ke kiri
                currentIndex = (currentIndex + 1) % contents.size();
            }

            // membuat image baru dengan opacity 0.0
            ImageView imageView = createResizedImageView(contents.get(currentIndex), CARO_IMAGE_WIDTH,
                    CARO_IMAGE_HEIGHT);
            imageView.setOpacity(0.0);

            // menambahkan transisi fade out sebagai animasi antar gambar carousel
            FadeTransition fadeOut = new FadeTransition(Duration.millis(80), carouselPane.getChildren().get(0));
            fadeOut.setFromValue(1.0); // nilai awal fade
            fadeOut.setToValue(0.0); // nilai akhir fade
            // keyframe yang terjadi saat sudah selesai fadeOut
            fadeOut.setOnFinished(e -> {
                // Mengupdate fadeout dengan image tertentu
                carouselPane.getChildren().set(0, imageView);

                // menambahkan animasi fadeIN (untuk image selanjutnya)
                FadeTransition fadeIn = new FadeTransition(Duration.millis(80), imageView);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();

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