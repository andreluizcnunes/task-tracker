import model.Task;
import model.enus.StatusTask;
import utils.JsonDb;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while(true){
            System.out.println("\n--- Task Tracker ---");
            System.out.println("Enter 1 to Create a new task.");
            System.out.println("Enter 2 to Read a task.");
            System.out.println("Enter 3 to Update a task.");
            System.out.println("Enter 4 to change task to in progress.");
            System.out.println("Enter 5 to change the task to done.");
            System.out.println("Enter 6 to Delete a task.");
            System.out.println("Enter 7 to Exit.");
            System.out.println("Select an option.");

            int option = scanner.nextInt();

            scanner.nextLine();

            try{
                switch(option){
                    case 1 -> createTask(scanner);
                    case 2 -> readTask(scanner);
                    case 3 -> updateTask(scanner);
                    case 4 -> inProgressTask(scanner);
                    case 5 -> doneTask(scanner);
                    case 6 -> deleteTask(scanner);
                    case 7 -> {
                        System.out.println("getting out...");
                        return;
                    }
                    default -> System.out.println("invalid option.");
                }
            }catch (IOException e){
                System.out.println("System error, try again later.");
            }
        }
    }

    private static void createTask(Scanner scanner) throws IOException {

        String id = UUID.randomUUID().toString();

        System.out.println("Enter the task description: ");
        String description = scanner.nextLine();

        System.out.println("The default status when creating a task is to-do, do you want to change the status of the task?");
        System.out.println("Enter 1 to Yes");
        System.out.println("Enter 2 to No");

        StatusTask status = null;

        int option = scanner.nextInt();

        switch(option){
            case 1 -> {
                System.out.println("Select the task status.");
                System.out.println("Enter 1 to to-do.");
                System.out.println("Enter 2 to in progress.");
                System.out.println("Enter 3 to done.");

                int optionStatus = scanner.nextInt();

                switch (optionStatus){
                    case 1 -> status = StatusTask.TODO;
                    case 2 -> status = StatusTask.IN_PROGRESS;
                    case 3 -> status = StatusTask.DONE;
                    default -> System.out.println("invalid option.");
                }
            }
            case 2 -> status = StatusTask.TODO;
            default -> System.out.println("invalid option.");
        }

        Instant createdAt = Instant.now();
        Instant updatedAt = null;

        Task task = new Task(id, description, status, createdAt, updatedAt);

        List<Task> tasks = JsonDb.readTask();
        tasks.add(task);
        JsonDb.writeTasks(tasks);

        System.out.println("Task successfully completed " + task);

    }

    private static void readTask(Scanner scanner) throws IOException {
        List<Task> tasks = JsonDb.readTask();

        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
        } else {
            System.out.println("Enter 1 to list all tasks.");
            System.out.println("Enter 2 to list all to-do tasks.");
            System.out.println("Enter 3 to list all in progress tasks.");
            System.out.println("Enter 4 to list all done tasks.");

            int option = scanner.nextInt();

            switch (option) {
                case 1 -> displayTasks(tasks, null, "No tasks found.");
                case 2 -> displayTasks(tasks, StatusTask.TODO, "No to-do tasks found.");
                case 3 -> displayTasks(tasks, StatusTask.IN_PROGRESS, "No in-progress tasks found.");
                case 4 -> displayTasks(tasks, StatusTask.DONE, "No done tasks found.");
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void displayTasks(List<Task> tasks, StatusTask statusFilter, String noTasksMessage) {
        List<Task> filteredTasks;

        if (statusFilter != null) {
            filteredTasks = tasks.stream()
                    .filter(task -> task.getStatus() == statusFilter)
                    .toList();
        } else {
            filteredTasks = tasks;
        }

        if (!filteredTasks.isEmpty()) {
            filteredTasks.forEach(System.out::println);
        } else {
            System.out.println(noTasksMessage);
        }
    }

    private static void updateTask(Scanner scanner) throws IOException {

        System.out.println("Enter the ID of the user to be updated: ");
        String id = scanner.nextLine();

        List<Task> tasks = JsonDb.readTask();
        for (Task task : tasks) {
            if (task.getId().equals(id)) {
                System.out.println("Enter the task description: ");
                String description = scanner.nextLine();

                System.out.println("The default status when creating a task is to-do, do you want to change the status of the task?");
                System.out.println("Enter 1 to Yes");
                System.out.println("Enter 2 to No");

                StatusTask status = null;

                int option = scanner.nextInt();

                switch(option){
                    case 1 -> {
                        System.out.println("Select the task status.");
                        System.out.println("Enter 1 to to-do.");
                        System.out.println("Enter 2 to in progress.");
                        System.out.println("Enter 3 to done.");

                        int optionStatus = scanner.nextInt();

                        switch (optionStatus){
                            case 1 -> status = StatusTask.TODO;
                            case 2 -> status = StatusTask.IN_PROGRESS;
                            case 3 -> status = StatusTask.DONE;
                            default -> System.out.println("invalid option.");
                        }
                    }
                    case 2 -> status = StatusTask.TODO;
                    default -> System.out.println("invalid option.");
                }

                Instant updatedAt = Instant.now();

                task.setDescription(description);
                task.setStatus(status);
                task.setUpdatedAt(updatedAt);

                JsonDb.writeTasks(tasks);
                System.out.println("updated task.");
                return;
            }
        }
        System.out.println("task with " + id + " not found");
    }

    private static void inProgressTask(Scanner scanner) throws IOException {

        System.out.println("Enter the ID of the user to be updated: ");
        String id = scanner.nextLine();

        List<Task> tasks = JsonDb.readTask();
        for (Task task : tasks) {
            if (task.getId().equals(id)) {
                StatusTask status = StatusTask.IN_PROGRESS;
                Instant updatedAt = Instant.now();

                task.setStatus(status);
                task.setUpdatedAt(updatedAt);

                JsonDb.writeTasks(tasks);
                System.out.println("task changed to in progress.");
                return;
            }
        }
        System.out.println("task with " + id + " not found");
    }

    private static void doneTask(Scanner scanner) throws IOException {

        System.out.println("Enter the ID of the user to be updated: ");
        String id = scanner.nextLine();

        List<Task> tasks = JsonDb.readTask();
        for (Task task : tasks) {
            if (task.getId().equals(id)) {
                StatusTask status = StatusTask.DONE;
                Instant updatedAt = Instant.now();

                task.setStatus(status);
                task.setUpdatedAt(updatedAt);

                JsonDb.writeTasks(tasks);
                System.out.println("task done.");
                return;
            }
        }
        System.out.println("task with " + id + " not found");
    }

    private static void deleteTask(Scanner scanner) throws IOException {
        System.out.println("Enter the id you want to delete: ");
        String id = scanner.nextLine();

        List<Task> tasks = JsonDb.readTask();
        tasks.removeIf(task -> task.getId().equals(id));
        JsonDb.writeTasks(tasks);

        System.out.println("deleted task.");
    }
}