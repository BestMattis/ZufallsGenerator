import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
public class RandomNumberController {
	@FXML
	AnchorPane bg;
	@FXML
	StackPane container;
	@FXML
	Label topLabel;	
	@FXML
	Label minLabel;	
	@FXML
	Label maxLabel;	
	@FXML
	Label errorLabel;		
	@FXML
	Label outputLabel;	
	@FXML
	TextField minTextfield;	
	@FXML
	TextField maxTextfield;	
	@FXML
	Button computeButton;
	@FXML
	Button computeButton1;
	@FXML
	Button backButton;
	@FXML
	VBox stack;
	@FXML
	HBox textFields;
	@FXML
	HBox compButtonBox;
	@FXML
	HBox errorLabBox;
	@FXML
	HBox backButtonBox;
	
	private static final String IDLE_BUTTON_STYLE = "-fx-background-color: #333333; -fx-text-fill: white; -fx-border-color: white; -fx-border-radius: 3";
    private static final String HOVERED_BUTTON_STYLE = "-fx-background-color: #555555; -fx-text-fill: white; -fx-border-color: white; -fx-border-radius: 3";	
    int min = -1;
    int max = -1;
    int result;
    private ObjectProperty<Font> fontTracking = new SimpleObjectProperty<Font>(Font.getDefault());
	public void initialize() {
		outputLabel.fontProperty().bind(fontTracking);
		bg.setBackground(new Background(new BackgroundFill(Color.web("#333333"),CornerRadii.EMPTY,Insets.EMPTY)));
		stack.prefHeightProperty().bind(bg.heightProperty());
		stack.prefWidthProperty().bind(bg.widthProperty());
		bg.widthProperty().addListener(new ChangeListener<Number>()
	    {
	        @Override
	        public void changed(ObservableValue<? extends Number> observableValue, Number oldWidth, Number newWidth)
	        {
	            fontTracking.set(Font.font(newWidth.doubleValue() / 4));
	        }
	    });

		errorLabel.setVisible(false);
		outputLabel.setVisible(false);
		backButton.setVisible(false);
		computeButton1.setVisible(false);
		topLabel.setText("Bitte tragen Sie eine untere / obere Schranke ein.");
		minLabel.setText("min Wert :");
		maxLabel.setText("max Wert :");
		minTextfield.textProperty().addListener((observable, oldValue, newValue) -> {
			try {
				min = Integer.parseInt(newValue);
			} catch (Exception e) {
				min = -1;
			}
		});
		minTextfield.setStyle("-fx-text-fill: white; -fx-border-color: white; -fx-border-radius: 4; -fx-background-color: #333333");
		maxTextfield.textProperty().addListener((observable, oldValue, newValue) -> {
			try {
				max = Integer.parseInt(newValue);
			} catch (Exception e) {
				max = -1;
			}
		});
		maxTextfield.setStyle("-fx-text-fill: white; -fx-border-color: white; -fx-border-radius: 4; -fx-background-color: #333333");
		computeButton.setText("Bestimme Zufallszahl");
		computeButton.setOnMouseEntered(e -> computeButton.setStyle(HOVERED_BUTTON_STYLE));
		computeButton.setOnMouseExited(e -> computeButton.setStyle(IDLE_BUTTON_STYLE));
		computeButton.setOnAction(e -> computNumber());
		computeButton1.setText("Nächste Zufallszahl");
		computeButton1.setOnMouseEntered(e -> computeButton.setStyle(HOVERED_BUTTON_STYLE));
		computeButton1.setOnMouseExited(e -> computeButton.setStyle(IDLE_BUTTON_STYLE));
		computeButton1.setOnAction(e -> computNumber());
		backButton.setText("Zurück");
		backButton.setOnMouseEntered(e -> computeButton.setStyle(HOVERED_BUTTON_STYLE));
		backButton.setOnMouseExited(e -> computeButton.setStyle(IDLE_BUTTON_STYLE));
		backButton.setOnAction(e -> nextNumber());
	}
	public void computNumber() {
		if(min != -1 && max != -1 && min <= max && min >= 0 && max >= 0) {
			errorLabel.setVisible(false);
			topLabel.setVisible(false);
			minLabel.setVisible(false);
			maxLabel.setVisible(false);
			minTextfield.setVisible(false);
			maxTextfield.setVisible(false);
			computeButton.setVisible(false);
			computeButton1.setVisible(true);
			backButton.setVisible(true);
			outputLabel.setVisible(true);
			result = (int)(Math.random() * ((max-min)+1))+min;
			outputLabel.setTextFill(Color.WHITE);
			outputLabel.setText(String.valueOf(result));
			
		} else if(min == -1 || max == -1){

			outputLabel.setVisible(false);
			errorLabel.setVisible(true);
			errorLabel.setTextFill(Color.RED);
			errorLabel.setText("Bitte geben Sie eine untere / obere Schranke an.");
		} else if(min > max) {

			errorLabel.setVisible(true);
			outputLabel.setVisible(false);
			errorLabel.setTextFill(Color.RED);
			errorLabel.setText("Bitte die Schranken so wählen sodass : min-Wert < max-Wert.");
		}
	}
	public void nextNumber() {
		errorLabel.setVisible(true);
		topLabel.setVisible(true);
		minLabel.setVisible(true);
		maxLabel.setVisible(true);
		minTextfield.setVisible(true);
		maxTextfield.setVisible(true);
		computeButton.setVisible(true);
		computeButton1.setVisible(false);
		backButton.setVisible(false);
		outputLabel.setVisible(false);
		errorLabel.setVisible(false);
	}
}
