package duchess;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import duchess.storage.Storage;
import duchess.task.Task;
import duchess.ui.Ui;

/**
 * Duchess class represents the main class of the Duchess program.
 * It initializes the necessary components and runs the program.
 */
public class Duchess extends Application {
    private static final String FILE_PATH = "./data/duchess.txt";
    private TaskList taskList;
    private Storage storage;
    private Ui ui;

    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;
    private Scene scene;


    /**
     * Constructs a Duchess object.
     * Initializes storage, task list, and user interface components.
     *
     * @throws DuchessException if an error occurs during initialization
     */
    public Duchess() throws DuchessException {
        storage = new Storage(FILE_PATH);
        taskList = new TaskList();
        ArrayList<Task> tasksStored = storage.loadData();
        if (!tasksStored.isEmpty()) {
            taskList = new TaskList(storage.loadData());
        }
        ui = new Ui();
    }


    /**
     * The entry point for the JavaFX application.
     *
     * @param stage The primary stage for this application, onto which the application scene can be set.
     */
    @Override
    public void start(Stage stage) {
        //Step 1. Setting up required components

        //The container for the content of the chat to scroll.
        scrollPane = new ScrollPane();
        dialogContainer = new VBox();
        scrollPane.setContent(dialogContainer);

        userInput = new TextField();
        sendButton = new Button("Send");

        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);

        scene = new Scene(mainLayout);

        stage.setScene(scene);
        stage.show();

        //Step 2. Formatting the window to look as expected
        stage.setTitle("Duke");
        stage.setResizable(false);
        stage.setMinHeight(600.0);
        stage.setMinWidth(400.0);

        mainLayout.setPrefSize(400.0, 600.0);

        scrollPane.setPrefSize(385, 535);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        scrollPane.setVvalue(1.0);
        scrollPane.setFitToWidth(true);

        //You will need to import `javafx.scene.layout.Region` for this.
        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);

        userInput.setPrefWidth(325.0);

        sendButton.setPrefWidth(55.0);

        AnchorPane.setTopAnchor(scrollPane, 1.0);

        AnchorPane.setBottomAnchor(sendButton, 1.0);
        AnchorPane.setRightAnchor(sendButton, 1.0);

        AnchorPane.setLeftAnchor(userInput , 1.0);
        AnchorPane.setBottomAnchor(userInput, 1.0);

        //Step 3. Add functionality to handle user input.
        sendButton.setOnMouseClicked((event) -> {
            dialogContainer.getChildren().add(getDialogLabel(userInput.getText()));
            userInput.clear();
        });

        userInput.setOnAction((event) -> {
            dialogContainer.getChildren().add(getDialogLabel(userInput.getText()));
            userInput.clear();
        });

        //Scroll down to the end every time dialogContainer's height changes.
        dialogContainer.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));
    }

    /**
     * Iteration 1:
     * Creates a label with the specified text and adds it to the dialog container.
     * @param text String containing text to add
     * @return a label with the specified text that has word wrap enabled.
     */
    private Label getDialogLabel(String text) {
        // You will need to import `javafx.scene.control.Label`.
        Label textToAdd = new Label(text);
        textToAdd.setWrapText(true);

        return textToAdd;
    }

    /**
     * Main method to start the Duchess program.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        try {
            new Duchess().run();
        } catch (DuchessException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Runs the Duchess program.
     * Displays opening greeting, interacts with user, and handles exceptions.
     */
    public void run() {
        ui.printOpeningGreeting();
        try {
            ui.printEcho(taskList, storage);
        } catch (DuchessException e) {
            System.out.println(e.getMessage());
        } finally {
            //Close scanner
            ui.closeScanner();
        }
    }


}
