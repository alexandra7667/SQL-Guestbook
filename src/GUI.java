/**
 * This class is the program's GUI.
 *
 * @author Alexandra Härnström
 * @version 1
 */

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class GUI {
    private DBConnection dbConnection;
    private ButtonHandler buttonHandler;
    private Stage primaryStage;
    private Scene scene;
    private BorderPane root;
    private Button btnSign;
    private TextField nameField;
    private TextField emailField;
    private TextField websiteField;
    private TextField commentField;
    private TextArea textArea;

    public GUI(Stage stage) {
        dbConnection = new DBConnection();
        buttonHandler = new ButtonHandler(dbConnection);
        primaryStage = stage;
        showGUI();
    }

    /**
     * This method sets the GUI design and displays it on the screen.
     * It also closes the database connection when the program is exited
     * (when the window is closed).
     */
    private void showGUI() {
        setDesign();

        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            dbConnection.closeConnection();
            event.consume();
            primaryStage.close();
        });
    }

    /**
     * This method sets the design of the GUI
     */
    private void setDesign() {
        primaryStage.setTitle("Guestbook  " + dbConnection.getDBNAME() + "  @" + dbConnection.getHOST());

        root = new BorderPane();
        root.setBackground(Background.fill(Color.LIGHTBLUE));

        setLabels();
        setButton();
        setTextFieldsAndAddButton();
        setTextArea();

        scene = new Scene(root, 800, 800);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
    }

    /**
     * This method sets the Labels
     */
    private void setLabels() {
        Label nameLabel = new Label("Name:");
        Label emailLabel = new Label("Email:");
        Label websiteLabel = new Label("Website:");
        Label commentLabel = new Label("Comment:");

        VBox vBoxLabels = new VBox();
        vBoxLabels.setPadding(new Insets(10));
        vBoxLabels.setSpacing(30);
        vBoxLabels.getChildren().addAll(nameLabel, emailLabel, websiteLabel, commentLabel);

        root.setLeft(vBoxLabels);
    }

    /**
     * This method sets the Button and defines its listener action
     */
    private void setButton() {
        btnSign = new Button("Done!");

        btnSign.setOnAction(e -> {
            buttonHandler.transformValues(getValues());
            updateTextArea();
        });
    }

    /**
     * This method sets the TextFields and adds the Button to the GUI
     */
    private void setTextFieldsAndAddButton() {
        nameField = new TextField();
        nameField.setMinWidth(500);
        emailField = new TextField();
        websiteField = new TextField();
        commentField = new TextField();

        VBox vBoxFields = new VBox();
        vBoxFields.setPadding(new Insets(10));
        vBoxFields.setSpacing(20);
        vBoxFields.getChildren().addAll(nameField, emailField, websiteField, commentField, btnSign);

        root.setRight(vBoxFields);
    }

    /**
     * This method sets the TextArea and ScrollPane.
     * It retrieves all previous entries from the database.
     */
    public void setTextArea() {
        textArea = new TextArea(dbConnection.getGuestbook());
        textArea.setFont(Font.font("Futura", 12));

        ScrollPane scrollPane = new ScrollPane(textArea);
        scrollPane.setPrefHeight(560);
        scrollPane.setPannable(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        root.setBottom(scrollPane);
    }

    /**
     * This method updates the displayed entries in the TextArea.
     */
    private void updateTextArea() {
        textArea.setText(dbConnection.getGuestbook());
    }

    /**
     * This method retrieves all values from the TextFields.
     * @return - A String array of the TextField values
     */
    public String[] getValues() {
        String[] values = new String[5];

        values[0] = nameField.getText();
        values[1] = emailField.getText();
        values[2] = websiteField.getText();
        values[3] = commentField.getText();
        values[4] = null;

        return values;
    }
}
