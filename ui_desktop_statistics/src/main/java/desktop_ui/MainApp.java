package desktop_ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Cappoocha on 19.06.2017.
 */
public class MainApp extends Application
{
    private static Stage stage;
    public static Scene commonStatistic, dailyStatistic;

    public static void main(String[] args)
    {
        launch(args);
    }

    /**
     *
     * @param primaryStage
     *
     * @throws Exception
     */
    public void start(Stage primaryStage) throws Exception
    {
        stage = primaryStage;
        primaryStage.setTitle("Statistic");
        primaryStage.setMinWidth(750);
        primaryStage.setMinHeight(600);

        commonStatistic = new Scene((Parent) FXMLLoader.load(getClass().getResource("/fxml/CommonStatistic.fxml")));
        dailyStatistic = new Scene((Parent) FXMLLoader.load(getClass().getResource("/fxml/DailyStatistic.fxml")));

        primaryStage.setScene(commonStatistic);
        primaryStage.show();
    }

    /**
     * Устанавливает окно
     *
     * @param scene
     */
    public static void setScene(Scene scene)
    {
        stage.setScene(scene);
    }
}
