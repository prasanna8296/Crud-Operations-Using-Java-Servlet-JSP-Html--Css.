<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.List, crud_operations.Task" %>
<html>
<head>
    <title>To-Do List</title>
</head>
<body>
    <h1>To-Do List</h1>
    <a href="tasks?action=new">Add New Task</a>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Title</th>
            <th>Description</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>
        <%
            List<Task> tasks = (List<Task>) request.getAttribute("tasks");
            if (tasks != null && !tasks.isEmpty()) {
                for (Task task : tasks) {
        %>
            <tr>
                <td><%= task.getId() %></td>
                <td><%= task.getTitle() %></td>
                <td><%= task.getDescription() %></td>
                <td><%= task.isCompleted() ? "Completed" : "Pending" %></td>
                <td>
                    <a href="tasks?action=edit&id=<%= task.getId() %>">Edit</a>
                    <a href="tasks?action=delete&id=<%= task.getId() %>">Delete</a>
                </td>
            </tr>
        <%
                }
            } else {
        %>
            <tr>
                <td colspan="5">No tasks available</td>
            </tr>
        <%
            }
        %>
    </table>
</body>
</html>
