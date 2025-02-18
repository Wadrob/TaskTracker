# Task Manager

A simple command-line Task Manager application in Java that allows users to add, update, delete, mark, and list tasks stored in a JSON file.

# Features

Add tasks: Create new tasks with a description.

Update tasks: Modify the description of an existing task.

Delete tasks: Remove tasks by ID.

Mark tasks: Change the status of a task (To Do, In Progress, Done).

List tasks: Display all tasks or filter by status.

# Technologies Used

Java 17+

Jackson (JSON processing)

JUnit 5 (for testing)

# Usage

The application runs in an interactive mode. Available commands:

add <description> – Adds a new task.

update <id> <new description> – Updates an existing task.

delete <id> – Deletes a task.

mark <status> <id> – Changes the status (todo, in-progress, done).

list – Lists all tasks.

list <status> – Lists tasks filtered by status.

quit – Exits the application.

# Example

> add Buy groceries
Task added successfully (ID: 1)

> list
tasks: [{id: 1, description: "Buy groceries", status: "todo"}]

> mark in-progress 1
Task (ID: 1) changed status: in-progress

# Running Tests

To execute unit tests:

mvn test

