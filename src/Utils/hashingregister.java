package Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class hashingregister {
    // Deklarasi dan instansiasi String variabel key variable ini nantinya akan
    // digunakan sebagai salt
    private String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public String setkey() {
        // Objek untuk memnyatukan char menjadi string
        StringBuilder sb = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            // Membuat random index untuk mengambil char dari variabel alphabet
            int index = random.nextInt(alphabet.length());
            // Mengambil char dari variabel alphabet berdasarkan index yang sudah dibuat
            char randomChar = alphabet.charAt(index);
            // Menyatukan char menjadi string
            sb.append(randomChar);
        }
        // Mengubah sb menjadi string
        String randomString = sb.toString();
        return randomString;
    }

    public String hash(String password, String salt) {
        // Deklarasi dan instansiasi objek dari class MessageDigest
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
        }
        // Mengubah string menjadi byte
        byte[] hash = md.digest(password.getBytes());
        // Deklarasi dan instansiasi objek dari class StringBuilder
        StringBuilder sb = new StringBuilder();
        // Mengubah byte menjadi hex
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        // Mengubah sb menjadi string
        String hashedPassword = sb.toString();
        // Menggabungkan hashedPassword dengan salt
        String saltedPassword = hashedPassword + salt;
        // Mengubah saltedPassword menjadi byte
        byte[] saltedHash = md.digest(saltedPassword.getBytes());
        // Mengubah byte menjadi hex
        StringBuilder sb2 = new StringBuilder();
        for (byte b : saltedHash) {
            sb2.append(String.format("%02x", b));
        }
        // Mengubah sb2 menjadi string
        String saltedHashedPassword = sb2.toString();
        return saltedHashedPassword;
    }
}
