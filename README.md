# Task Tracker

[My Profile on Roadmap.sh](https://roadmap.sh/u/andrelcnunes)  
[Link to the Task Tracker Challenge](https://roadmap.sh/projects/task-tracker)

The **Task Tracker** is a simple Java application for managing tasks. You can create, view, update, change the status, and delete tasks using an interactive menu.

## Features

1. Create a new task.
2. View tasks:
   - All tasks.
   - "To-Do" tasks.
   - "In Progress" tasks.
   - "Done" tasks.
3. Update task description or status.
4. Change task status to "In Progress."
5. Change task status to "Done."
6. Delete a task.
7. Exit the program.

## Technologies Used

- **Java 21**
- JSON file manipulation for persistent task storage.

## Prerequisites

- JDK 21 installed.
- Text editor or IDE (e.g., IntelliJ IDEA or Eclipse).
- Operating system: Windows, macOS, or Linux.

## Project Structure

- **model/Task.java**: Represents a task.
- **model/enus/StatusTask.java**: Enum for task statuses (`TO_DO`, `IN_PROGRESS`, `DONE`).
- **utils/JsonDb.java**: Utility class for reading and writing tasks to a JSON file.
- **Main.java**: Contains the interactive menu for managing tasks.

## Running the Project

1. Clone or download this repository.

```bash
git clone <REPOSITORY_URL>
```

2. Compile the Java files.

```bash
javac -d bin src/**/*.java
```

3. Run the program.

```bash
java -cp bin Main
```

## How to Use

When running the program, you will see the following menu in the terminal:

```
--- Task Tracker ---
Enter 1 to Create a new task.
Enter 2 to Read a task.
Enter 3 to Update a task.
Enter 4 to change task to in progress.
Enter 5 to change the task to done.
Enter 6 to Delete a task.
Enter 7 to Exit.
Select an option.
```

### Examples

- **Create a new task (option 1):**
  - Enter a description for the task.
  - Choose an initial status or accept the default "TO_DO."

- **View tasks (option 2):**
  - Select one of the filtering options to display tasks.

- **Update a task (option 3):**
  - Enter the task ID.
  - Update the description and/or status.

- **Change task status (options 4 and 5):**
  - Enter the task ID and confirm the status change.

- **Delete a task (option 6):**
  - Enter the task ID to delete it.

- **Exit the program (option 7):**
  - Close the program.

## Data Persistence

Tasks are stored in a JSON file, which is automatically created and managed by the application.

- File: `tasks.json` (created in the project's root directory).

## Contribution

Contributions are welcome! Feel free to open a pull request or submit suggestions.

## License

This project is open-source and licensed under the MIT License. See the `LICENSE` file for details.