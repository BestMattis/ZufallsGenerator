import java.util.ArrayList;
import java.util.Arrays;

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
	boolean usedUp = false;
	ArrayList<Integer> usedNumbers = new ArrayList<Integer>();
	private ObjectProperty<Font> fontTracking = new SimpleObjectProperty<Font>(Font.getDefault());
	private String indexAlphabet[] = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P",
			"Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
	private ArrayList<String> bstn = new ArrayList<String>(Arrays.asList(indexAlphabet));
	boolean standartMin = true;
	boolean standartMax = true;

	public void initialize() {
		outputLabel.fontProperty().bind(fontTracking);
		bg.setBackground(new Background(new BackgroundFill(Color.web("#333333"), CornerRadii.EMPTY, Insets.EMPTY)));
		stack.prefHeightProperty().bind(bg.heightProperty());
		stack.prefWidthProperty().bind(bg.widthProperty());
		bg.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldWidth, Number newWidth) {
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
				StringBuilder parse = new StringBuilder(newValue);
				if (bstn.contains(parse.substring(0, 1))) {
//					System.out.println("axx");
					standartMin = false;
					if (parse.substring(1).length() > 2) {
						min = -2;
					} else {
						if (Integer.parseInt(parse.substring(1)) < 10) {
							parse.insert(1, 0);
						}
						parse.replace(0, 1, String.valueOf(bstn.indexOf(newValue.substring(0, 1))));
						min = Integer.parseInt(parse.toString());
						usedUp = false;
						usedNumbers = new ArrayList<Integer>();
					}
				} else {
//					System.out.println("normal");
					standartMin = true;
					min = Integer.parseInt(parse.toString());
					usedUp = false;
					usedNumbers = new ArrayList<Integer>();
				}
			} catch (Exception e) {
				min = -1;
			}
		});
		minTextfield.setStyle(
				"-fx-text-fill: white; -fx-border-color: white; -fx-border-radius: 4; -fx-background-color: #333333");
		maxTextfield.textProperty().addListener((observable, oldValue, newValue) -> {
			try {
				StringBuilder parse = new StringBuilder(newValue);
				if (bstn.contains(parse.substring(0, 1))) {
					standartMax = false;
					if (parse.substring(1).length() > 2) {
						max = -2;
					} else {
						if (Integer.parseInt(parse.substring(1)) < 10) {
							parse.insert(1, 0);
						}
						parse.replace(0, 1, String.valueOf(bstn.indexOf(newValue.substring(0, 1))));
						max = Integer.parseInt(parse.toString());
						usedUp = false;
						usedNumbers = new ArrayList<Integer>();
					}
				} else {
					standartMax = true;
					max = Integer.parseInt(parse.toString());
					usedUp = false;
					usedNumbers = new ArrayList<Integer>();
				}
			} catch (Exception e) {
				max = -1;
			}
		});

		maxTextfield.setStyle(
				"-fx-text-fill: white; -fx-border-color: white; -fx-border-radius: 4; -fx-background-color: #333333");
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
		if (min != -1 && max != -1 && min <= max && min >= 0 && max >= 0 && !usedUp && standartMin == standartMax) {
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
			result = (int) (Math.random() * ((max - min) + 1)) + min;
			while (usedNumbers.contains(result)) {
				result = (int) (Math.random() * ((max - min) + 1)) + min;
			}
			usedNumbers.add(result);
			outputLabel.setTextFill(Color.WHITE);
			StringBuilder backParse = new StringBuilder(String.valueOf(result));
			if (standartMin == false && standartMax == false) {
				if (result >= 1000) {
					backParse.replace(0, 2, bstn.get(Integer.parseInt(String.valueOf(result).substring(0, 2))));
				} else if (result < 100) {
					backParse.insert(0, 0);
					if (result < 10) {
						backParse.insert(0, 0);
					}
					backParse.replace(0, 1, bstn.get(Integer.parseInt(backParse.substring(0, 1))));
				} else {
					backParse.replace(0, 1, bstn.get(Integer.parseInt(String.valueOf(result).substring(0, 1))));
				}
			}
			outputLabel.setText(String.valueOf(backParse.toString()));
			if (usedNumbers.size() >= max - min + 1) {
				usedUp = true;
			}

		} else if (min == -1 || max == -1) {
			outputLabel.setVisible(false);
			errorLabel.setVisible(true);
			errorLabel.setTextFill(Color.RED);
			errorLabel.setText("Bitte geben Sie eine untere / obere Schranke an.");
		} else if (min > max) {
			errorLabel.setVisible(true);
			outputLabel.setVisible(false);
			errorLabel.setTextFill(Color.RED);
			errorLabel.setText("Bitte die Schranken so wählen sodass : min-Wert < max-Wert.");
		} else if (usedUp) {
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
			errorLabel.setTextFill(Color.RED);
			errorLabel.setText("Keine unbenutzten Zahlen im gewählten Bereich mehr verfügbar.\nBitte wählen Sie eine neue Schranke");
		} else if (min == -2 || max == -2) {
			errorLabel.setVisible(true);
			outputLabel.setVisible(false);
			errorLabel.setTextFill(Color.RED);
			errorLabel.setText("Numerischer Teil der jeweiligen Schranke muss < 100");
		} else if (standartMin != standartMax) {
			errorLabel.setVisible(true);
			outputLabel.setVisible(false);
			errorLabel.setTextFill(Color.RED);
			errorLabel.setText("Maximum und Minimum input müssen vom selben Typ sein");
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
