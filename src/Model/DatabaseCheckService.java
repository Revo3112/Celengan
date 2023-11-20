package Model;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

// Membuat si check database berjalan di background (parallel)
public class DatabaseCheckService extends Service<Integer> {
    // Membuat task untuk melakukan pengecekan database
    @Override
    protected Task<Integer> createTask() {
        return new Task<Integer>() { // Membuat objek task dengan parameter Integer serta mengembalikan nilai Integer
                                     // pada method call()
            @Override // Override method call()
            protected Integer call() throws Exception { // Membuat method call() yang mengembalikan nilai Integer
                // Menunggu selama 10 detik untuk menampilkan splash screen
                Thread.sleep(10000);

                // Melakukan pengecekan database
                LoginModel loginModel = new LoginModel();
                // Mengembalikan nilai dari method checkData()
                return loginModel.checkData();
            }
        };
    }
};
