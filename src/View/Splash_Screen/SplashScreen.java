package View.Splash_Screen;
/* MAIN */
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.Parent;

/* GEOMETRY */

/* COLOR & SHAPE */
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/* FONT & TEXT */
import javafx.scene.text.Text;

/* STAGE */
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/* IMAGE */
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SplashScreen {
    Stage stage;
    Parent root;

    private double xOffset = 0;
    private double yOffset = 0;

    String assetPath = "././Assets/View/Splash_Screen/";
    String imgPath = assetPath + "images/"; // add image/gif/vid name
    String dialogPath = assetPath + "dialog/";

    public SplashScreen(Stage stage) {
        this.stage = stage;
    }

    public void start() {

        /* LOCAL PROPERTIES */
        /* RECTANGLE PROPERTIES */
        double rectArcWidth = 60;
        double rectArcHeight = 60;

        /* OUTER BACKGROUND */
        Rectangle outBackground = new Rectangle(950, 600);
        outBackground.setArcWidth(rectArcWidth);
        outBackground.setArcHeight(rectArcHeight);
        outBackground.setFill(Color.WHITE);

        /* ELEMENTS INSIDE */
        /* IMAGE: Chicken */
        Rectangle imgRect = new Rectangle(550, 580);
        imgRect.setArcWidth(rectArcWidth);
        imgRect.setArcHeight(rectArcHeight);
        imgRect.setTranslateX(40);
        // image declaration
        ImageView imgAyam = new ImageView();
        Image ayamImg = new Image(imgPath + "Chicken-Bank.jpg");
        // image properties
        imgAyam.setImage(ayamImg);
        imgAyam.setFitHeight(600);
        imgAyam.setFitWidth(600);
        imgAyam.setSmooth(true);
        imgAyam.setClip(imgRect);
        imgAyam.setTranslateX(170);
        imgAyam.setTranslateY(10);
        // rect overlay for stroke
        Rectangle imgRectOverlay = new Rectangle(550, 580);
        imgRectOverlay.setArcWidth(rectArcWidth);
        imgRectOverlay.setArcHeight(rectArcHeight);
        imgRectOverlay.setStroke(Color.BLACK);
        imgRectOverlay.setStrokeWidth(2);
        imgRectOverlay.setFill(Color.TRANSPARENT);
        imgRectOverlay.setTranslateX(186);
        imgRectOverlay.setTranslateY(2);

        /* IMAGE: Celengan Logo */
        Rectangle logoRect = new Rectangle(65, 65);
        logoRect.setArcHeight(30);
        logoRect.setArcWidth(30);
        logoRect.setFill(Color.valueOf("#590505"));
        logoRect.setTranslateX(-425);
        logoRect.setTranslateY(-245);
        // image
        ImageView imgLogo = new ImageView();
        Image logoImg = new Image(imgPath + "Celengan-Logo.png");
        imgLogo.setImage(logoImg);
        imgLogo.setFitWidth(80);
        imgLogo.setFitHeight(80);
        imgLogo.setTranslateX(-425);
        imgLogo.setTranslateY(-250);

        /* TEXT: Celengan */
        Text logoText = new Text("Celengan");
        // Load the custom font "Poppins-Bold"
        logoText.setStyle("-fx-font: 36 Poppins;");
        logoText.setFill(Color.valueOf("#590505"));
        logoText.setTranslateX(-285);
        logoText.setTranslateY(-245);

        /* LOGIC */
        /* LOADING: BUBLLE CHAT 1 */
        ImageView chat_1 = new ImageView();
        Image chatImage_1 = new Image(dialogPath + "chat-1.jpg");
        chat_1.setImage(chatImage_1);
        chat_1.setSmooth(true);
        chat_1.setPreserveRatio(true);
        chat_1.setFitWidth(140);
        chat_1.setFitHeight(140);
        chat_1.setTranslateX(380);
        chat_1.setTranslateY(900);
        /* LOADING: BUBBLE CHAT 2 */
        ImageView chat_2 = new ImageView();
        Image chatImage_2 = new Image(dialogPath + "chat-2.jpg");
        chat_2.setImage(chatImage_2);
        chat_2.setSmooth(true);
        chat_2.setPreserveRatio(true);
        chat_2.setFitWidth(220);
        chat_2.setFitHeight(220);
        chat_2.setTranslateX(340);
        chat_2.setTranslateY(900);
        /* LOADING: BUBBLE CHAT 3 */
        ImageView chat_3 = new ImageView();
        Image chatImage_3 = new Image(dialogPath + "chat-3.jpg");
        chat_3.setImage(chatImage_3);
        chat_3.setSmooth(true);
        chat_3.setPreserveRatio(true);
        chat_3.setFitWidth(310);
        chat_3.setFitHeight(310);
        chat_3.setTranslateX(295);
        chat_3.setTranslateY(900);
        /* LOADING: BUBBLE CHAT 4 */
        ImageView chat_4 = new ImageView();
        Image chatImage_4 = new Image(dialogPath + "chat-4.jpg");
        chat_4.setImage(chatImage_4);
        chat_4.setSmooth(true);
        chat_4.setPreserveRatio(true);
        chat_4.setFitWidth(310);
        chat_4.setFitHeight(310);
        chat_4.setTranslateX(295);
        chat_4.setTranslateY(900);
        // animation with audio

        /* ELEMENTS PANE */
        StackPane contentImage = new StackPane(imgAyam, imgRectOverlay);
        StackPane logo = new StackPane(logoRect, imgLogo, logoText);
        StackPane contentLoad = new StackPane(chat_1, chat_2, chat_3, chat_4);
        /* PANE */
        StackPane outerPane = new StackPane(outBackground);
        StackPane innerPane = new StackPane(contentImage, logo, contentLoad);

        /* ROOT */
        StackPane root = new StackPane();
        root.getChildren().addAll(outerPane, innerPane);
        // Make the scene draggable
        root.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown()) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        root.setOnMouseDragged(event -> {
            if (event.isPrimaryButtonDown()) {
                this.stage.setX(event.getScreenX() - xOffset);
                this.stage.setY(event.getScreenY() - yOffset);
            }
        });

        Scene scene = new Scene(root, Color.TRANSPARENT); // Set the scene background to transparent
        // fonts
        scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Poppins:wght@700&display=swap");

        this.stage.initStyle(StageStyle.TRANSPARENT);
        this.stage.setScene(scene);
        this.stage.show();
    }
}
