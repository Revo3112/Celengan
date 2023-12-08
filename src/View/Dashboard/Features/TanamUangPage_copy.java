package View.Dashboard.Features;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.Group;
import javafx.stage.Stage;

// class DashboardPage digunakan untuk menampilkan halaman dashboard
public class TanamUangPage_copy {
    private Stage stage; // Deklarasi property stage

    public DecimalFormat formatRibuan;
    // public DecimalFormat formatPuluhanRibu;
    // public DecimalFormat formatRatusanRibu;
    // public DecimalFormat formatJutaan;
    // public DecimalFormat formatPuluhanJuta;
    // public DecimalFormat formatRatusanJuta;
    // public DecimalFormat formatMiliaran;

    public DecimalFormat formatRupiah;

    private StackPane root = new StackPane();

    private ComboBox<String> combobox = new ComboBox<String>();
    private Text title = new Text("Pengeluaran");
    private String tipeTanamUang = "pengeluaran";
    private TextField fieldJumlah = new TextField();
    private TextField fieldKeterangan = new TextField();
    private RadioButton radioButtonCash = new RadioButton("Cash");
    private RadioButton radioButtonTransfer = new RadioButton("Transfer");
    private DatePicker datePickerTanggal = new DatePicker();

    // Melakukan inisiasi class Tanamuang dengan parameter stage
    public TanamUangPage_copy(Stage stage) {
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
        // DatePicker datePickerTanggal = new DatePicker();

        Label labelKategori = new Label("Kategori:");
        labelKategori.setStyle("-fx-font: 14 Poppins, Regular");
        labelKategori.setTextFill(Color.valueOf("#FFFFFF"));
        this.combobox = new ComboBox(FXCollections.observableArrayList(listKategoriPengeluaran));

        Button buttonEdit = new Button("Edit");

        buttonEdit.setOnMouseClicked(e -> {
            double paneWidth = this.stage.getWidth() - 350;
            double paneHeight = this.stage.getHeight() - 200;

            StackPane backgroundMainPane = new StackPane();
            backgroundMainPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8);");

            StackPane mainPane = new StackPane();
            mainPane.setMaxSize(paneWidth - 200, paneHeight - 200);
            mainPane.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 20");

            VBox contentPane = new VBox();
            contentPane.setStyle("-fx-background-radius: 20");
            contentPane.setMaxSize(mainPane.getMaxWidth() - 100, mainPane.getMaxHeight() - 20);
            
            contentPane.setSpacing(20);
            
            HBox topBar = new HBox();
            // topBar.setStyle("-fx-background-color: #3081D0;");

            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setFitToWidth(true);
            scrollPane.setMaxSize(contentPane.getMaxWidth(), contentPane.getMaxHeight() - 100);

            VBox scrollPaneContent;
            if (this.tipeTanamUang.equals("pengeluaran")) {
                scrollPaneContent = createScrollPaneContent(scrollPane, listKategoriPengeluaran, mainPane);
            } else {
                scrollPaneContent = createScrollPaneContent(scrollPane, listKategoriPemasukan, mainPane);
            }

            String firstCapitalLetter = this.tipeTanamUang.substring(0, 1).toUpperCase() + this.tipeTanamUang.substring(1);
            Text titleKategoriPengeluaran = createText("Kategori " + firstCapitalLetter, "-fx-font: 16 Poppins;", "#000000");
            Hyperlink backHyperlink = new Hyperlink();
            backHyperlink.setGraphic(new ImageView(new Image("file:src/Assets/View/Dashboard/Back.png")));
            // HBox.setMargin(titleKategoriPengeluaran,
                        // new Insets(10, contentPane.getMaxWidth() - titleKategoriPengeluaran.getBoundsInLocal().getWidth() - 40, 0, 20));

            backHyperlink.setOnMouseClicked(f -> {
                this.root.getChildren().remove(mainPane);
                this.root.getChildren().remove(backgroundMainPane);
            });

            Hyperlink tambahHyperlink = new Hyperlink();
            tambahHyperlink.setGraphic(new ImageView(new Image("file:src/Assets/View/Dashboard/Tambah.png")));

            StackPane tambahPane = new StackPane(); // agar tidak duplicate
            StackPane backgroundTambahPane = new StackPane();

            tambahHyperlink.setOnMouseClicked(f -> {
                tambahPane.setMaxSize(mainPane.getMaxWidth() - 400, mainPane.getMaxHeight() - 200);
                tambahPane.setStyle("-fx-background-color: #E48F45; -fx-background-radius: 20;");

                backgroundTambahPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8)");

                Text titleTambah = createText("Tambah Kategori", "-fx-font: bold 18 Poppins", "#000000");
                
                Label labelTambah = new Label("Nama:");
                labelTambah.setStyle("-fx-font: 14 'Poppins Regular';");
                labelTambah.setTextFill(Color.valueOf("#000000"));
                TextField fieldTambah = new TextField();
                Button buttonTambah = new Button("Tambah");
                Button buttonBatal = new Button("Batal");

                buttonBatal.setOnMouseClicked(g -> {
                    if (mainPane.getParent() != null) {
                        mainPane.getChildren().remove(tambahPane);
                        mainPane.getChildren().remove(backgroundTambahPane);
                    }
                });

                buttonTambah.setOnMouseClicked(g -> {
                    if (TanamUangModel.tambahKategori(fieldTambah.getText(), tipeTanamUang)) {
                        refreshView(scrollPane, scrollPaneContent, mainPane);

                        if (mainPane.getParent() != null) {
                            mainPane.getChildren().remove(tambahPane);
                            mainPane.getChildren().remove(backgroundTambahPane);
                        }

                        AlertHelper.info("Kategori " + fieldTambah.getText() + " berhasil ditambahkan!");
                    } else {
                        AlertHelper.alert("Kategori gagal ditambahkan!");
                    }
                });

                HBox hboxFormTambah = new HBox(labelTambah, fieldTambah);
                hboxFormTambah.setSpacing(20);
                hboxFormTambah.setAlignment(Pos.CENTER);
                HBox hboxButtonTambah = new HBox(buttonBatal, buttonTambah);
                hboxButtonTambah.setSpacing(20);
                hboxButtonTambah.setAlignment(Pos.CENTER);

                VBox vboxTambah = new VBox(titleTambah, hboxFormTambah, hboxButtonTambah);
                vboxTambah.setMaxSize(tambahPane.getMaxWidth() - 50, tambahPane.getMaxHeight() - 80);
                vboxTambah.setSpacing(20);
                
                tambahPane.getChildren().add(vboxTambah);

                if (!mainPane.getChildren().contains(tambahPane)) {
                    mainPane.getChildren().addAll(backgroundTambahPane, tambahPane);
                }
            });

            scrollPane.setContent(scrollPaneContent);

            topBar.getChildren().addAll(backHyperlink, titleKategoriPengeluaran, tambahHyperlink);
            // HBox.setMargin(titleKategoriPengeluaran,
                    // new Insets(10, mainPane.getWidth() - titleKategoriPengeluaran.getBoundsInLocal().getWidth() - 20, 0, 0));

            // ====== ADD CHILD ======\\
            // topBar.getChildren().addAll(titleKategoriPengeluaran, logoTambah);
            contentPane.getChildren().addAll(topBar, scrollPane);
            mainPane.getChildren().add(contentPane);
            this.root.getChildren().addAll(backgroundMainPane, mainPane);
        });

        Label labelJumlah = new Label("Jumlah:");
        labelJumlah.setStyle("-fx-font: 14 Poppins, Regular");
        labelJumlah.setTextFill(Color.valueOf("#FFFFFF"));
        // TextField fieldJumlah = new TextField();
        this.fieldJumlah.setMaxWidth(200);

        Label labelTipePembayaran = new Label("Tipe Pembayaran:");
        labelTipePembayaran.setStyle("-fx-font: 14 Poppins, Regular");
        labelTipePembayaran.setTextFill(Color.valueOf("#FFFFFF"));
        this.radioButtonCash = new RadioButton("Cash");
        this.radioButtonCash.setTextFill(Color.valueOf("#FFFFFF"));
        this.radioButtonTransfer = new RadioButton("Transfer");
        this.radioButtonTransfer.setTextFill(Color.valueOf("#FFFFFF"));

        ToggleGroup toggleGroup = new ToggleGroup();
        this.radioButtonCash.setToggleGroup(toggleGroup);
        this.radioButtonTransfer.setToggleGroup(toggleGroup);

        Label labelKeterangan = new Label("Keterangan:");
        labelKeterangan.setStyle("-fx-font: 14 Poppins, Regular");
        labelKeterangan.setTextFill(Color.valueOf("#FFFFFF"));
        // TextField fieldKeterangan = new TextField();
        this.fieldKeterangan.setMaxWidth(200);

        Button buttonSimpan = new Button("Simpan");
        buttonSimpan.setOnMouseClicked(e -> {
            if (isFormFilled()) {
                LocalDate selectedTanggal = this.datePickerTanggal.getValue();
                DateTimeFormatter formatTanggal = DateTimeFormatter.ofPattern("YYYY-MM-d");
                String tanggal = selectedTanggal.format(formatTanggal).toString();

                String selectedKategori = this.combobox.getValue().toString();

                int kategoriId = 0;
                
                int jumlah = Integer.parseInt(fieldJumlah.getText().replace(",", ""));
                String keterangan = fieldKeterangan.getText();
                String tipePembayaran = "";

                if (radioButtonCash.isSelected()) {
                    tipePembayaran = radioButtonCash.getText();
                } else if (radioButtonTransfer.isSelected()) {
                    tipePembayaran = radioButtonTransfer.getText();
                }

                boolean isDefault = TanamUangModel.getIsKategoriDefault(selectedKategori);

                if (isDefault) {
                    kategoriId = TanamUangModel.getIdKategoriDefault(selectedKategori);
                } else {
                    kategoriId = TanamUangModel.getIdKategoriUser(selectedKategori);
                }

                System.out.println("Kategori Id: " + kategoriId);
                System.out.println("Is Default: " + isDefault);

                if (TanamUangModel.simpanTanamUang(tanggal, selectedKategori, kategoriId, jumlah, tipePembayaran, keterangan, this.tipeTanamUang, isDefault)) {
                    if (this.tipeTanamUang.toLowerCase().equals("pengeluaran")) {
                        clearSelectionTanamUang("Pengeluaran telah tercatat");
                    } else {
                        clearSelectionTanamUang("Pemasukan telah tercatat");
                    }
                } else {
                    AlertHelper.alert("Mohon isi data Anda.");
                }
            } else {
                System.out.println("FORM GA FILLED BANG");
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

        VBox vbox = new VBox(buttonTU, hboxTitle, formTanggal, formKategori, formJumlah, formTipePembayaran,
                formKeterangan, hboxButtonSimpan);
        vbox.setSpacing(40);
        vbox.setAlignment(Pos.CENTER);
        // vbox.setMaxSize(800, 600);

        this.root.getChildren().addAll(vbox);

        fieldJumlah.textProperty().addListener(new ChangeListener<String>() {
            private String codeFormat = ",##0";
            private int codeLen = 4;

            @Override
            // ObservableValue = StringProperty (Represent an object that has changed its
            // state)
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Hapus listener sementara

                fieldJumlah.textProperty().removeListener(this);
                if (newValue.replace(",", "").matches("\\d*")) { // Check inputan angka atau tidak
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
                double value = Double.parseDouble(text.replace(",", ""));
                System.out.println(value);
                fieldJumlah.setText("");
                fieldJumlah.setText(decimalFormat.format(value));
            }

            private void updateCodeFormat(String text) {
                int newLen = text.replace(",", "").length();
                if (newLen == this.codeLen + 3) {
                    this.codeFormat = "#," + this.codeFormat;
                    codeLen += 3;
                } else if (newLen >= 4) {
                    this.codeFormat = "#" + this.codeFormat;
                }
            }
        });

        this.root.setStyle("-fx-background-color: #0D1416");
        // this.root.setMaxWidth(1366);
        // this.root.setMaxHeight(768);
        System.out.println("ROOT WIDTH: " + this.root.getWidth());
        System.out.println("ROOT HEIGHT: " + this.root.getHeight());

        Scene scene = new Scene(this.root, this.stage.getWidth(), this.stage.getHeight()); // Memasukkan root node ke
                                                                                           // dalam scene
        this.stage.setScene(scene); // Memasukkan scene ke dalam stage
        this.stage.setMaximized(true);
        // this.stage.show(); // Menampilkan stage
    }

    private void refreshView(ScrollPane scrollPane, VBox scrollContentBox, StackPane mainPane) {
        scrollContentBox.getChildren().clear();
        List<String> listKategori = new ArrayList<>();

        if (this.tipeTanamUang.equals("pengeluaran")) {
            String[] kategori = TanamUangModel.getKategoriTanamUang(this.tipeTanamUang);
            for (int i = 0; i < kategori.length; i++) {
                listKategori.add(kategori[i]);
            }
        } else {
            String[] kategori = TanamUangModel.getKategoriTanamUang(this.tipeTanamUang);
            for (int i = 0; i < kategori.length; i++) {
                listKategori.add(kategori[i]);
            }
        }

        String[] kategori = listKategori.toArray(new String[0]);

        VBox contentPane = new VBox();
        contentPane.setStyle("-fx-background-radius: 20");
        contentPane.setMaxSize(mainPane.getMaxWidth(), mainPane.getMaxHeight() - 20);
        contentPane.setSpacing(20);

        VBox scrollPaneContent;
        if (this.tipeTanamUang.equals("pengeluaran")) {
            scrollPaneContent = createScrollPaneContent(scrollPane, kategori, mainPane);
        } else {
            scrollPaneContent = createScrollPaneContent(scrollPane, kategori, mainPane);
        }

        scrollPane.setContent(scrollPaneContent);
    }

    private VBox createScrollPaneContent(ScrollPane scrollPane, String[] listKategori, StackPane mainPane) {
        VBox scrollPaneContent = new VBox();

        for (int i = 0; i < listKategori.length; i++) {
            Text namaKategori = createText(listKategori[i], "-fx-font: 16 Poppins;", "#000000");
            Hyperlink editHyperlink = new Hyperlink();
            editHyperlink.setStyle("-fx-background-color: #3081D0");
            editHyperlink.setGraphic(new ImageView(new Image("file:src/Assets/View/Dashboard/Edit.png")));
            Hyperlink deleteHyperlink = new Hyperlink();
            deleteHyperlink.setStyle("-fx-background-color: #66e5e5");
            deleteHyperlink.setGraphic(new ImageView(new Image("file:src/Assets/View/Dashboard/Delete.png")));

            HBox itemContent = new HBox(namaKategori, editHyperlink, deleteHyperlink);

            int index = i;
            boolean isKategoriDefault = TanamUangModel.getIsKategoriDefault(namaKategori.getText());

            StackPane paneHapus = new StackPane();
            StackPane backgroundPaneHapus = new StackPane();

            deleteHyperlink.setOnMouseClicked(e -> {
                paneHapus.setMaxSize(mainPane.getWidth() - 600, mainPane.getHeight() - 600);
                paneHapus.setStyle("-fx-background-radius: 20; -fx-background-color: #505e63");

                backgroundPaneHapus.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8)");

                Text titleHapus = createText("Yakin ingin menghapus kategori ", "-fx-font: 18 'Poppins Medium';", "#FFFFFF");
                Text titleNamaKategoriHapus = createText(listKategori[index], "-fx-font: bold 18 Poppins;", "#000000");
                Text titleTandaTanya = createText("?", "-fx-font: 18 'Poppins Medium';", "#FFFFFF");

                HBox hboxTitleHapus = new HBox(titleHapus, titleNamaKategoriHapus, titleTandaTanya);

                Button buttonHapus = new Button("Hapus");
                Button buttonHapusBatal = new Button("Batal");

                buttonHapusBatal.setOnAction(f -> {
                    if (mainPane.getParent() != null) {
                        mainPane.getChildren().remove(paneHapus);
                        mainPane.getChildren().remove(backgroundPaneHapus);
                    }
                });

                buttonHapus.setOnAction(f -> {
                    if (isKategoriDefault) {
                        if (TanamUangModel.hapusKategori(listKategori[index], isKategoriDefault)) {
                            refreshView(scrollPane, scrollPaneContent, mainPane);
    
                            if (mainPane.getParent() != null) {
                                    mainPane.getChildren().remove(paneHapus);
                                    mainPane.getChildren().remove(backgroundPaneHapus);
                            }
                            AlertHelper.info("Kategori " + listKategori[index] + " berhasil dihapus!");
                        } else {
                            AlertHelper.alert("Kategori gagal dihapus!");
                        }
                    } else {
                        if (TanamUangModel.hapusKategori(listKategori[index], isKategoriDefault)) {
                            refreshView(scrollPane, scrollPaneContent, mainPane);
    
                            if (mainPane.getParent() != null) {
                                    mainPane.getChildren().remove(paneHapus);
                                    mainPane.getChildren().remove(backgroundPaneHapus);
                                }
                            AlertHelper.info("Kategori " + listKategori[index] + " berhasil dihapus!");
                        } else {
                            AlertHelper.alert("Kategori gagal dihapus!");
                        }
                    }
                });

                HBox hboxButtonHapus = new HBox(buttonHapusBatal, buttonHapus);
                hboxButtonHapus.setSpacing(20);
                hboxButtonHapus.setAlignment(Pos.CENTER);

                VBox itemContentHapus = new VBox(hboxTitleHapus, hboxButtonHapus);
                itemContentHapus.setSpacing(20);
                itemContentHapus.setMaxSize(paneHapus.getMaxWidth() - 100, paneHapus.getMaxHeight() - 30);
                VBox.setMargin(hboxTitleHapus, new Insets(0, 0, 20, 0));

                paneHapus.getChildren().add(itemContentHapus);

                if (!mainPane.getChildren().contains(paneHapus)) {
                    mainPane.getChildren().addAll(backgroundPaneHapus, paneHapus);
                }

            });

            StackPane paneEdit = new StackPane(); // agar tidak duplicate
            StackPane backgroundPaneEdit = new StackPane();
            
            editHyperlink.setOnMouseClicked(e -> {
                paneEdit.setMaxSize(mainPane.getWidth() - 400, mainPane.getHeight() - 200);
                paneEdit.setStyle("-fx-background-radius: 20; -fx-background-color: #505e63");

                backgroundPaneEdit.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8)");

                Text titleEdit = createText("Ubah Kategori", "-fx-font: 20 Poppins;", "#FFFFFF");
                Label labelEditNamaKategori = new Label("Nama:");
                labelEditNamaKategori.setStyle("-fx-font: 16 Poppins");
                labelEditNamaKategori.setTextFill(Color.valueOf("#FFFFFF"));
                TextField fieldEditNamaKategori = new TextField(listKategori[index]);
                Button editButtonSimpanKategori = new Button("Simpan");
                Button editButtonBatalKategori = new Button("Batal");

                editButtonBatalKategori.setOnMouseClicked(g -> {
                    if (mainPane.getParent() != null) {
                        mainPane.getChildren().remove(paneEdit);
                        mainPane.getChildren().remove(backgroundPaneEdit);
                    }
                });

                String nama_kategori = namaKategori.getText();

                editButtonSimpanKategori.setOnMouseClicked(h -> {
                    String valueFieldEditNamaKategori = fieldEditNamaKategori.getText();

                    if (isKategoriDefault) {
                        int idKategoriDefault = TanamUangModel.getIdKategoriDefault(nama_kategori);
                        boolean statusSimpan = TanamUangModel.simpanNamaKategori(valueFieldEditNamaKategori,
                                namaKategori.getText(),
                                isKategoriDefault, this.tipeTanamUang, idKategoriDefault);

                        if (statusSimpan) {
                            refreshView(scrollPane, scrollPaneContent, mainPane);

                            if (mainPane.getParent() != null) {
                                mainPane.getChildren().remove(paneEdit);
                                mainPane.getChildren().remove(backgroundPaneEdit);
                            }

                            AlertHelper.info("Berhasil mengubah nama kategori!");
                        } else {
                            AlertHelper.alert("Gagal mengubah nama kategori!");
                        }
                    } else {
                        boolean statusSimpan = TanamUangModel.simpanNamaKategori(valueFieldEditNamaKategori,
                                namaKategori.getText(),
                                isKategoriDefault, this.tipeTanamUang, 0);
                        if (statusSimpan) {
                            refreshView(scrollPane, scrollPaneContent, mainPane);

                            if (mainPane.getParent() != null) {
                                mainPane.getChildren().remove(paneEdit);
                                mainPane.getChildren().remove(backgroundPaneEdit);
                            }

                            AlertHelper.info("Berhasil mengubah nama kategori!");
                        } else {
                            AlertHelper.alert("Gagal mengubah nama kategori!");
                        }
                    }
                });

                HBox editHBox = new HBox(labelEditNamaKategori, fieldEditNamaKategori);
                editHBox.setSpacing(20);
                editHBox.setAlignment(Pos.CENTER);

                HBox editTitleHBox = new HBox(titleEdit);

                HBox editButtonHBox = new HBox(editButtonBatalKategori, editButtonSimpanKategori);
                editButtonHBox.setSpacing(20);
                editButtonHBox.setAlignment(Pos.CENTER);

                VBox editVBox = new VBox(editTitleHBox, editHBox, editButtonHBox);
                editVBox.setMaxSize(paneEdit.getMaxWidth() - 20, paneEdit.getHeight() - 20);
                editVBox.setSpacing(20);
                editVBox.setAlignment(Pos.CENTER);

                paneEdit.getChildren().add(editVBox);

                if (!mainPane.getChildren().contains(paneEdit)) {
                    mainPane.getChildren().add(backgroundPaneEdit);
                    mainPane.getChildren().add(paneEdit);
                }
            });

            VBox itemBox = new VBox();
            // itemBox.setStyle("-fx-background-color: #EE7214");
            itemBox.setMaxSize(mainPane.getMaxWidth() - 20, 200);
            itemBox.getChildren().add(itemContent);
            itemBox.setAlignment(Pos.CENTER);

            scrollPaneContent.getChildren().add(itemBox);
            
        }

        scrollPaneContent.setSpacing(20);
        // scrollPaneContent.setMaxSize(mainPane.getWidth(), mainPane.getHeight() - 20);
        scrollPaneContent.setAlignment(Pos.CENTER);
        // scrollPaneContent.setStyle("-fx-background-color: #C21292");
        return scrollPaneContent;
    }

    // fungsi untuk membuat text dengan return Text
    private Text createText(String text, String style, String color) {
        Text newText = new Text(text);
        newText.setStyle(style); // menetapkan style text
        newText.setFill(Color.valueOf(color)); // menetapkan warna text
        return newText;
    }

    private void clearSelectionTanamUang(String message) {
        this.datePickerTanggal.setValue(null);
        this.combobox.getSelectionModel().clearSelection();
        this.fieldJumlah.setText("");
        this.fieldKeterangan.setText("");
        this.radioButtonCash.setSelected(false);
        this.radioButtonTransfer.setSelected(false);

        AlertHelper.info(message);
    }

    private boolean isFormFilled() {
        System.out.println("DatePicker: " + (this.datePickerTanggal.getValue() != null));
        System.out.println("ComboBox: " + (this.combobox.getValue() != null));
        System.out.println("Field Jumlah: " + (!this.fieldJumlah.getText().isEmpty()));
        System.out.println("field Keterangan: " + (!this.fieldKeterangan.getText().isEmpty()));
        System.out.println("Radio button: " + (this.radioButtonCash.isSelected() || this.radioButtonTransfer.isSelected()));

        return this.datePickerTanggal.getValue() != null &&
                this.combobox.getValue() != null &&
                !this.fieldJumlah.getText().isEmpty() &&
                !this.fieldKeterangan.getText().isEmpty() &&
                (this.radioButtonCash.isSelected() || this.radioButtonTransfer.isSelected());
    }

    // private int getKategoriId(String[] listKategori, String selectedKategori) {
    //     int kategoriId = 0;

    //     for (int i = 0; i < listKategori.length; i++) {
    //         if (listKategori[i] == selectedKategori) {
    //             kategoriId = i + 1;
    //         }
    //     }
    //     System.out.println("Kategori ID: " + kategoriId);

    //     return kategoriId;
    // }

}
