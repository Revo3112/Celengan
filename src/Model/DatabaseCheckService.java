package Model;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class DatabaseCheckService extends Service<Integer> {

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
