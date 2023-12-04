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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
        createTitle();
        createFormTanggal();

        createFormKategori(listKategoriPengeluaran);
        createButtonSimpan(listKategoriPengeluaran);
        createButtonTanamUang(listKategoriPengeluaran, listKategoriPemasukan);

        if (this.tipeTanamUang.equals("Pemasukan")) {
            createFormKategori(listKategoriPemasukan);
            createButtonSimpan(listKategoriPemasukan);
        }

        createFormJumlah();
        createFormTipePembayaran();
        createFormKeterangan();

        fieldJumlah.textProperty().addListener(new ChangeListener<String>() {
            private String codeFormat = ",##0";
            private int codeLen = 4;

            @Override
            // ObservableValue = StringProperty (Represent an object that has changed its state)
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

        Scene scene = new Scene(this.root); // Memasukkan root node ke dalam scene
        this.stage.setScene(scene); // Memasukkan scene ke dalam stage
        // this.stage.show(); // Menampilkan stage
    }

    private void createTitle() {
        this.title.setFont(Font.font("Verdana", 20)); // Mengatur font dari text
        this.title.setFill(Color.BLACK); // Mengatur warna dari text
        this.title.setTranslateY(-40);

        this.root.getChildren().addAll(this.title);
    }

    private void createFormKategori(String[] listKategori) {
        // Form kategori
        Label labelKategori = new Label("Kategori:");
        labelKategori.setTranslateX(-50);
        labelKategori.setTranslateY(40);

        this.combobox.setItems(FXCollections.observableArrayList(listKategori));
        this.combobox.setTranslateX(60);
        this.combobox.setTranslateY(40);

        this.root.getChildren().addAll(labelKategori, this.combobox);
    }

    private void createFormTanggal() {
        // Form Tanggal
        Label labelTanggal = new Label("Tanggal:");
        labelTanggal.setTranslateX(-50);

        this.datePickerTanggal.setTranslateX(90);

        this.root.getChildren().addAll(labelTanggal, this.datePickerTanggal);
    }

    private void createFormJumlah() {
        // Form jumlah
        Label labelJumlah = new Label("Jumlah:");
        labelJumlah.setTranslateX(-50);
        labelJumlah.setTranslateY(80);

        this.fieldJumlah.setMaxWidth(200);
        this.fieldJumlah.setTranslateX(100);
        this.fieldJumlah.setTranslateY(80);

        this.root.getChildren().addAll(labelJumlah, this.fieldJumlah);
    }

    private void createFormTipePembayaran() {
        // Form tipe pembayaran
        Label labelTipePembayaran = new Label("Tipe Pembayaran:");
        labelTipePembayaran.setTranslateX(-50);
        labelTipePembayaran.setTranslateY(120);

        this.radioBtnCash.setTranslateX(60);
        this.radioBtnCash.setTranslateY(120);

        this.radioBtnTransfer.setTranslateX(120);
        this.radioBtnTransfer.setTranslateY(120);

        ToggleGroup toggleGroup = new ToggleGroup();
        this.radioBtnCash.setToggleGroup(toggleGroup);
        this.radioBtnTransfer.setToggleGroup(toggleGroup);

        this.root.getChildren().addAll(labelTipePembayaran, this.radioBtnCash, this.radioBtnTransfer);
    }

    private void createFormKeterangan() {
        // Form keterangan
        Label labelKeterangan = new Label("Keterangan:");
        labelKeterangan.setTranslateX(-50);
        labelKeterangan.setTranslateY(160);

        this.fieldKeterangan.setMaxWidth(200);
        this.fieldKeterangan.setTranslateX(100);
        this.fieldKeterangan.setTranslateY(160);

        this.root.getChildren().addAll(labelKeterangan, this.fieldKeterangan);
    }

    private void createButtonSimpan(String[] listKategori) {
        // Button Simpan
        Button btnSimpan = new Button("Simpan");
        btnSimpan.setTranslateY(200);

        btnSimpan.setOnMouseClicked(e -> {
            if (isFormFilled()) {
                LocalDate selectedTanggal = datePickerTanggal.getValue();
                DateTimeFormatter formatTanggal = DateTimeFormatter.ofPattern("YYYY-MM-d");
                String tanggal = selectedTanggal.format(formatTanggal).toString();
                
                String selectedKategori = combobox.getValue().toString();
                int kategoriId = getKategoriId(listKategori, selectedKategori);

                int jumlah = Integer.parseInt(fieldJumlah.getText().replace(",", ""));
                String keterangan = fieldKeterangan.getText();
                String tipePembayaran = "";

                if (radioBtnCash.isSelected()) {
                    tipePembayaran = radioBtnCash.getText();
                } else if (radioBtnTransfer.isSelected()) {
                    tipePembayaran = radioBtnTransfer.getText();
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

        this.root.getChildren().addAll(btnSimpan);
    }

    private void createButtonTanamUang(String[] listKategoriPengeluaran, String[] listKategoriPemasukan) {
        Button btnPemasukan = new Button("Pemasukan");
        btnPemasukan.setTranslateX(-50);
        btnPemasukan.setTranslateY(-80);

        btnPemasukan.setOnMouseClicked(e -> {
            this.combobox.setItems(FXCollections.observableArrayList(listKategoriPemasukan));
            this.title.setText("Pemasukan");
            this.tipeTanamUang = "Pemasukan";
        });

        Button btnPengeluaran = new Button("Pengeluaran");
        btnPengeluaran.setTranslateX(50);
        btnPengeluaran.setTranslateY(-80);
        btnPengeluaran.setOnMouseClicked(e -> {
            this.combobox.setItems(FXCollections.observableArrayList(listKategoriPengeluaran));
            this.title.setText("Pengeluaran");
            this.tipeTanamUang = "Pengeluaran";
        });

        this.root.getChildren().addAll(btnPemasukan, btnPengeluaran);
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
