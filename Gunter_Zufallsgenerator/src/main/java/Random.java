import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;
public class Random extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(RandomNumberController.class.getResource("randomNumber.fxml"));
		try {
			Parent parent = fxmlLoader.load();
			Scene scene = new Scene(parent,400,300);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Zufallsgenerator");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
