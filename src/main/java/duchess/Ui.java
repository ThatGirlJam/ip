package duchess;

import java.util.Scanner;

public class Ui {

    // Declare the scanner as a static field in the class
    private static Scanner scanner = new Scanner(System.in);

    //Print opening greeting
    public void printOpeningGreeting() {
        String logo = " ____            __\n"
                + "|  _ \\ _   ______| |      ___  ___  ___\n"
                + "| | | | | | |  __| |__  /  _ \\/ __|/ __|\n"
                + "| |_| | |_| | |__| ___ |   __/\\__ \\\\__ \\\n"
                + "|____/ \\__,_|____|_| |_|\\ ___||___/|___/\n";
        printHorizontalLine();
        System.out.println(logo);
        printHorizontalLine();
        System.out.println("Hello! I'm Duchess.Duchess.");
        System.out.println("What can I do for you today?");
        printHorizontalLine();
    }


    //Prints closing greeting
    public void printClosingGreeting() {
        printHorizontalLine();
        System.out.println("Farewell. Hope to see you again soon, my dear!");
        printHorizontalLine();
    }

    //Prints a Horizontal Line of 50 dashes
    public void printHorizontalLine() {
        int lineLength = 50; // Specify the length of the line

        // Print the horizontal line
        for (int i = 0; i < lineLength; i++) {
            System.out.print("_");
        }

        System.out.println();
    }

    //Adds user input to list, exits if user inputs "bye"
    public void printEcho(TaskList taskList, Storage storage) throws DuchessException {
        // Loop to read user input
        while (true) {
            String userInput = scanner.nextLine();

            // Split user input into tokens
            String[] tokens = userInput.split(" ");

            // Based on user input, change output
            switch (tokens[0].toLowerCase()) {
                case "bye":
                    printClosingGreeting();
                    return;

                case "list":
                    printHorizontalLine();
                    taskList.printTaskList();
                    break;

                case "mark":
                    printHorizontalLine();
                    if (tokens.length > 1) {
                        int taskIndexToMark = Integer.parseInt(tokens[1]) - 1; //Minus 1 to match zero-index
                        taskList.markTaskAsDone(taskIndexToMark);
                        storage.saveData(taskList);
                    } else {
                        throw new DuchessException("Oh dear! That is an invalid command. Try: mark <taskIndex>");
                    }
                    break;

                case "unmark":
                    printHorizontalLine();
                    if (tokens.length > 1) {
                        int taskIndexToUnmark = Integer.parseInt(tokens[1]) - 1; //Minus 1 to match zero-index
                        taskList.unmarkTaskAsDone(taskIndexToUnmark);
                        storage.saveData(taskList);
                    } else {
                        throw new DuchessException("Oh dear! That is an invalid command. Try: unmark <taskIndex>");
                    }
                    break;

                case "todo":
                    printHorizontalLine();
                    taskList.addToDo(userInput);
                    storage.saveData(taskList);
                    break;

                case "deadline":
                    printHorizontalLine();
                    taskList.addDeadline(userInput);
                    storage.saveData(taskList);
                    break;

                case "event":
                    printHorizontalLine();
                    taskList.addEvent(userInput);
                    storage.saveData(taskList);
                    break;

                case "delete":
                    printHorizontalLine();
                    if (tokens.length > 1) {
                        int taskIndexToDelete = Integer.parseInt(tokens[1]) - 1; //Minus 1 to match zero-index
                        taskList.deleteTask(taskIndexToDelete);
                        storage.saveData(taskList);
                    } else {
                        throw new DuchessException("Oh dear! That is an invalid command. Try: unmark <taskIndex>");
                    }
                    break;

                default:
                    throw new DuchessException("Oh dear, I can't make out what that is.");

            }
            printHorizontalLine();
        }
    }

    public void closeScanner() {
        scanner.close();
    }
}