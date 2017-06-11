package launcher;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    static Stage stage;
    public static Scene allStatistic;
    public static Scene periodStatistic;

    public static void setScene(Scene scene){
        stage.setScene(scene);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        allStatistic = new Scene(FXMLLoader.load(getClass().getResource("../allStatistic/allStatistic.fxml")));
        periodStatistic = new Scene(FXMLLoader.load(getClass().getResource("../periodStatistic/periodStatistic.fxml")));
        stage.setTitle("StatisticGB");
        stage.setScene(allStatistic);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
