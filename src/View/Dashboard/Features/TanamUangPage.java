package View.Dashboard.Features;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.UnaryOperator;

import Controller.SceneController;
import Model.TanamUangModel;
import Utils.AlertHelper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.Group; 
import javafx.stage.Stage;

// class DashboardPage digunakan untuk menampilkan halaman dashboard
public class TanamUangPage {
    private Stage stage; // Deklarasi property stage

    public DecimalFormat formatRibuan;
    // public DecimalFormat formatPuluhanRibu;
    public DecimalFormat formatRatusanRibu;
    public DecimalFormat formatJutaan;
    public DecimalFormat formatPuluhanJuta;
    public DecimalFormat formatRatusanJuta;
    public DecimalFormat formatMiliaran;

    public DecimalFormat formatRupiah;

    private StackPane root = new StackPane();

    private ComboBox<String> combobox = new ComboBox<String>();
    private Text title = new Text("Pengeluaran");
    private String tipeTanamUang = "";
    private TextField fieldJumlah = new TextField();
    private TextField fieldKeterangan = new TextField();
    private RadioButton radioBtnCash = new RadioButton("Cash");
    private RadioButton radioBtnTransfer = new RadioButton("Transfer");
    private DatePicker datePickerTanggal = new DatePicker();

    // Melakukan inisiasi class Tanamuang dengan parameter stage
    public TanamUangPage(Stage stage) {
        this.stage = stage; // Instansiasi property stage dengan parameter stage
    }

    // Menampilkan halaman Tanam Uang
    public void start(String listKategoriPemasukan[], String listKategoriPengeluaran[]) {
        Button buttonPemasukan = new Button("Pemasukan");
        Button buttonPengeluaran = new Button("Pengeluaran");

        buttonPemasukan.setOnMouseClicked(e -> {
            this.title.setText("Pemasukan");
            this.tipeTanamUang = "pemasukan";
            this.combobox.setItems(FXCollections.observableArrayList(listKategoriPemasukan));
        });
        buttonPengeluaran.setOnMouseClicked(e -> {
            this.title.setText("Pengeluaran");
            this.tipeTanamUang = "pengeluaran";
            this.combobox.setItems(FXCollections.observableArrayList(listKategoriPengeluaran));
        });

        this.title = new Text("Pengeluaran");
        this.title.setStyle("-fx-font: 24 Poppins;");
        this.title.setFill(Color.valueOf("#FFFFFF"));

        Label labelTanggal = new Label("Tanggal:");
        labelTanggal.setStyle("-fx-font: 14 Poppins, Regular");
        labelTanggal.setTextFill(Color.valueOf("#FFFFFF"));
        DatePicker datePickerTanggal = new DatePicker();

        Label labelKategori = new Label("Kategori:");
        labelKategori.setStyle("-fx-font: 14 Poppins, Regular");
        labelKategori.setTextFill(Color.valueOf("#FFFFFF"));
        this.combobox = new ComboBox(FXCollections.observableArrayList(listKategoriPengeluaran));
        ImageView editLogo = new ImageView("file:src/Assets/View/Dashboard/Edit.png");
        Button buttonEdit = new Button("Edit");
        buttonEdit.setOnMouseClicked(e -> {
            double paneWidth = this.stage.getWidth() - 350;
            double paneHeight = this.stage.getHeight() - 200;

            StackPane pane = new StackPane();
            pane.setMaxSize(paneWidth, paneHeight);
            pane.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 20");
            
            Text titleKategoriPengeluaran = createText("Kategori Pengeluaran", "-fx-font: 16 Poppins;", "#000000");
            ImageView logoTambah = new ImageView(new Image("file:src/Assets/View/Dashboard/Tambah.png"));
            
            HBox topBar = new HBox(titleKategoriPengeluaran, logoTambah);
            topBar.setMaxSize(paneWidth, paneHeight);
            topBar.setStyle("-fx-background-color: #DF826C");
        
            HBox.setMargin(titleKategoriPengeluaran, new Insets(10, paneWidth - titleKategoriPengeluaran.getBoundsInLocal().getWidth() - 10, 0, 0));

            VBox scrollPaneContent = new VBox();
            scrollPaneContent.setSpacing(30);
        
            ScrollPane scrollPane = new ScrollPane(scrollPaneContent);
            scrollPane.setFitToWidth(true);
        
            scrollPaneContent.setMaxSize(scrollPane.getWidth() - 20, scrollPane.getHeight());
        
            VBox mainBar = new VBox(topBar, scrollPane);
            mainBar.setMaxSize(paneWidth, paneHeight);
            mainBar.setSpacing(20);
            mainBar.setAlignment(Pos.CENTER);
            
            int i = 0;
            while (i < listKategoriPengeluaran.length) {
                Text namaKategori = createText(listKategoriPengeluaran[i], "-fx-font: 16 Poppins;", "#000000");
                ImageView logoEdit = new ImageView(new Image("file:src/Assets/View/Dashboard/Edit.png"));
                Hyperlink editHyperlink = new Hyperlink();
                editHyperlink.setStyle("-fx-background-color: #3081D0");
                editHyperlink.setGraphic(logoEdit);
                
                int index = i;
                boolean isKategoriDefault = TanamUangModel.getIsKategoriDefault(namaKategori.getText());
                
                editHyperlink.setOnMouseClicked(f -> {
                    StackPane paneEdit = new StackPane();
                    paneEdit.setMaxSize(paneWidth - 600, paneHeight - 400);
                    paneEdit.setStyle("-fx-background-radius: 20; -fx-background-color: #505e63");
                    
                    Text editTitle = createText("Ubah Kategori", "-fx-font: 20 Poppins;", "#FFFFFF");
                    Label labelEditNamaKategori = new Label("Nama:");
                    labelEditNamaKategori.setStyle("-fx-font: 16 Poppins");
                    labelEditNamaKategori.setTextFill(Color.valueOf("#FFFFFF"));
                    TextField fieldEditNamaKategori = new TextField(listKategoriPengeluaran[index]);
                    Button editButtonSimpanKategori = new Button("Simpan");
                    Button editButtonBatalKategori = new Button("Batal");

                    editButtonBatalKategori.setOnMouseClicked(g -> {
                        if (pane.getParent() != null) {
                            pane.getChildren().remove(paneEdit);
                        }
                    });

                    String nama_kategori = namaKategori.getText();

                    editButtonSimpanKategori.setOnMouseClicked(h -> {
                        String valueFieldEditNamaKategori = fieldEditNamaKategori.getText();

                        if (isKategoriDefault) {
                            int idKategoriDefault = TanamUangModel.getIdKategoriDefault(nama_kategori);
                            System.out.println("ID: " + idKategoriDefault);
                            boolean statusSimpan = TanamUangModel.simpanNamaKategori(valueFieldEditNamaKategori, isKategoriDefault, "pengeluaran", idKategoriDefault);
                            
                            if (statusSimpan) {
                                AlertHelper.info("Berhasil mengubah nama kategori!");
                            } else {
                                AlertHelper.info("Gagal mengubah nama kategori!");
                            }
                        } else {
                            boolean statusSimpan = TanamUangModel.simpanNamaKategori(valueFieldEditNamaKategori, isKategoriDefault, "pengeluaran", 0);
                            if (statusSimpan) {
                                AlertHelper.info("Berhasil mengubah nama kategori!");
                            } else {
                                AlertHelper.info("Gagal mengubah nama kategori!");
                            }
                        }
                    });
                    
                    HBox editHBox = new HBox(labelEditNamaKategori, fieldEditNamaKategori);
                    editHBox.setSpacing(20);
                    editHBox.setAlignment(Pos.CENTER);

                    HBox editTitleHBox = new HBox(editTitle);
                    editTitleHBox.setAlignment(Pos.TOP_LEFT);
                    HBox.setMargin(editTitle, new Insets(0, 0, 0, 20));

                    HBox editButtonHBox = new HBox(editButtonBatalKategori, editButtonSimpanKategori);
                    editButtonHBox.setSpacing(20);
                    editButtonHBox.setAlignment(Pos.CENTER);

                    VBox editVBox = new VBox(editTitleHBox, editHBox, editButtonHBox);
                    editVBox.setSpacing(20);
                    editVBox.setAlignment(Pos.CENTER);
                    
                    paneEdit.getChildren().add(editVBox);
                    pane.getChildren().add(paneEdit);

                });
    
                HBox itemBox = new HBox(namaKategori, editHyperlink);
                HBox.setMargin(namaKategori, new Insets(10, paneWidth - namaKategori.getBoundsInLocal().getWidth(), 0, 0));
        
                scrollPaneContent.getChildren().add(itemBox);

                i++;
            }

            pane.getChildren().add(mainBar);
        
            this.root.getChildren().add(pane);
        });

        Label labelJumlah = new Label("Jumlah:");
        labelJumlah.setStyle("-fx-font: 14 Poppins, Regular");
        labelJumlah.setTextFill(Color.valueOf("#FFFFFF"));
        TextField fieldJumlah = new TextField();
        fieldJumlah.setMaxWidth(200);

        Label labelTipePembayaran = new Label("Tipe Pembayaran:");
        labelTipePembayaran.setStyle("-fx-font: 14 Poppins, Regular");
        labelTipePembayaran.setTextFill(Color.valueOf("#FFFFFF"));
        RadioButton radioButtonCash = new RadioButton("Cash");
        radioButtonCash.setTextFill(Color.valueOf("#FFFFFF"));
        RadioButton radioButtonTransfer = new RadioButton("Transfer");
        radioButtonTransfer.setTextFill(Color.valueOf("#FFFFFF"));

        ToggleGroup toggleGroup = new ToggleGroup();
        radioButtonCash.setToggleGroup(toggleGroup);
        radioButtonTransfer.setToggleGroup(toggleGroup);

        Label labelKeterangan = new Label("Keterangan:");
        labelKeterangan.setStyle("-fx-font: 14 Poppins, Regular");
        labelKeterangan.setTextFill(Color.valueOf("#FFFFFF"));
        TextField fieldKeterangan = new TextField();
        fieldKeterangan.setMaxWidth(200);

        Button buttonSimpan = new Button("Simpan");
        buttonSimpan.setOnMouseClicked(e -> {
            if (isFormFilled()) {
                LocalDate selectedTanggal = datePickerTanggal.getValue();
                DateTimeFormatter formatTanggal = DateTimeFormatter.ofPattern("YYYY-MM-d");
                String tanggal = selectedTanggal.format(formatTanggal).toString();
                
                String selectedKategori = this.combobox.getValue().toString();

                int kategoriId = 0;
                if (this.tipeTanamUang.equals("pengeluaran")) {
                    kategoriId = getKategoriId(listKategoriPengeluaran, selectedKategori);
                } else {
                    kategoriId = getKategoriId(listKategoriPemasukan, selectedKategori);
                }

                int jumlah = Integer.parseInt(fieldJumlah.getText().replace(".", ""));
                String keterangan = fieldKeterangan.getText();
                String tipePembayaran = "";

                if (radioButtonCash.isSelected()) {
                    tipePembayaran = radioButtonCash.getText();
                } else if (radioButtonTransfer.isSelected()) {
                    tipePembayaran = radioButtonTransfer.getText();
                }

                if (tipeTanamUang.toLowerCase().equals("pengeluaran")) {
                    if (TanamUangModel.simpanPengeluaran(tanggal, selectedKategori, kategoriId, jumlah, tipePembayaran, keterangan)) {
                        clearSelection("Pengeluaran telah tercatat");
                    }
                } else {
                    if (TanamUangModel.simpanPemasukan(tanggal, selectedKategori, kategoriId, jumlah, tipePembayaran, keterangan)) {
                        clearSelection("Pemasukan telah tercatat");
                    }
                }
            } else {
                AlertHelper.alert("Mohon isi data Anda");
            }
        });
        

        // HBOX

        HBox buttonTU = new HBox(buttonPengeluaran, buttonPemasukan);
        buttonTU.setSpacing(60);
        buttonTU.setAlignment(Pos.CENTER);

        HBox hboxTitle = new HBox(title);
        hboxTitle.setAlignment(Pos.CENTER);

        HBox formTanggal = new HBox(labelTanggal, datePickerTanggal);
        formTanggal.setSpacing(40);
        formTanggal.setAlignment(Pos.CENTER);

        HBox formKategori = new HBox(labelKategori, this.combobox, buttonEdit);
        formKategori.setSpacing(40);
        formKategori.setAlignment(Pos.CENTER);

        HBox formJumlah = new HBox(labelJumlah, fieldJumlah);
        formJumlah.setSpacing(20);
        formJumlah.setAlignment(Pos.CENTER);     
        
        HBox formTipePembayaran = new HBox(labelTipePembayaran, radioButtonCash, radioButtonTransfer);
        formTipePembayaran.setSpacing(20);
        formTipePembayaran.setAlignment(Pos.CENTER);     

        HBox formKeterangan = new HBox(labelKeterangan, fieldKeterangan);
        formKeterangan.setSpacing(20);
        formKeterangan.setAlignment(Pos.CENTER);

        HBox hboxButtonSimpan = new HBox(buttonSimpan);
        hboxButtonSimpan.setAlignment(Pos.CENTER);

        VBox vbox = new VBox(buttonTU, hboxTitle, formTanggal, formKategori, formJumlah, formTipePembayaran, formKeterangan, hboxButtonSimpan);
        vbox.setSpacing(40);
        vbox.setAlignment(Pos.CENTER);
        vbox.setMaxSize(800, 600);

        this.root.getChildren().addAll(vbox);

        fieldJumlah.textProperty().addListener(new ChangeListener<String>() {
            private String codeFormat = ".##0";
            private int codeLen = 4;

            @Override
            // ObservableValue = StringProperty (Represent an object that has changed its state)
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Hapus listener sementara

                fieldJumlah.textProperty().removeListener(this);
                if (newValue.replace(".", "").matches("\\d*")) { // Check inputan angka atau tidak
                    if (newValue.length() > oldValue.length()) {
                        if (newValue.length() > 13) {
                            fieldJumlah.setText(oldValue);
                        } else {
                            updateCodeFormat(newValue);
                            formatAndSet(newValue, this.codeFormat);
                        }
                    }
                } else {
                    fieldJumlah.setText(oldValue);
                }

                // Tambahkan kembali listener setelah pembaruan teks
                fieldJumlah.textProperty().addListener(this);
            }

            private void formatAndSet(String text, String format) {
                DecimalFormat decimalFormat = new DecimalFormat(format);
                double value = Double.parseDouble(text.replace(".", ""));
                System.out.println(value);
                fieldJumlah.setText("");
                fieldJumlah.setText(decimalFormat.format(value));            
            }

            private void updateCodeFormat(String text) {
                int newLen = text.replace(".", "").length();
                if (newLen == this.codeLen + 3) {
                    this.codeFormat = "#." + this.codeFormat;
                    codeLen += 3;
                } else if (newLen >= 4) {
                    this.codeFormat = "#" + this.codeFormat;
                }
            }
        });

        this.root.setStyle("-fx-background-color: #0D1416");
        Scene scene = new Scene(this.root); // Memasukkan root node ke dalam scene
        this.stage.setScene(scene); // Memasukkan scene ke dalam stage
        this.stage.setMinHeight(500);
        this.stage.setMinWidth(750);
        this.stage.setFullScreen(true);
        // this.stage.show(); // Menampilkan stage
    }

    // fungsi untuk membuat text dengan return Text
    private Text createText(String text, String style, String color) {
        Text newText = new Text(text);
        newText.setStyle(style); // menetapkan style text
        newText.setFill(Color.valueOf(color)); // menetapkan warna text
        return newText;
    }

    private void clearSelection(String message) {
        this.datePickerTanggal.setValue(null);
        this.combobox.getSelectionModel().clearSelection();
        this.fieldJumlah.setText("");
        this.fieldKeterangan.setText("");
        this.radioBtnCash.setSelected(false);
        this.radioBtnTransfer.setSelected(false);

        AlertHelper.info(message);
    }

    private boolean isFormFilled() {
        return  this.datePickerTanggal.getValue() != null && 
                this.combobox.getValue() != null && 
                !this.fieldJumlah.getText().isEmpty() && 
                !this.fieldKeterangan.getText().isEmpty() &&
                (this.radioBtnCash.isSelected() || this.radioBtnTransfer.isSelected());
    }

    private int getKategoriId(String[] listKategori, String selectedKategori) {
        int kategoriId = 0;

        for (int i = 0; i < listKategori.length; i++) {
            if (listKategori[i] == selectedKategori) {
                kategoriId = i + 1;
            }
        }

        return kategoriId;
    }

}
