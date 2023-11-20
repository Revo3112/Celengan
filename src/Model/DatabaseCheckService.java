package Model;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

// Membuat si check database berjalan di background (parallel)
public class DatabaseCheckService extends Service<Integer> {
    // Membuat task untuk melakukan pengecekan database
    @Override
    protected Task<Integer> createTask() {
        return new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                // Simulate time consuming operation
                Thread.sleep(10000);

                // Melakukan pengecekan database
                LoginModel loginModel = new LoginModel();
                return loginModel.checkData();
            }
        };
    }
};
