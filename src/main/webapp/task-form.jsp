<%@ page contentType="text/html;charset=UTF-8" language="java" import="crud_operations.Task" %>
<html>
<head>
    <title><%= request.getAttribute("task") == null ? "Add New Task" : "Edit Task" %></title>
</head>
<body>
    <h1><%= request.getAttribute("task") == null ? "Add New Task" : "Edit Task" %></h1>
<form action="tasks" method="post">
    <input type="hidden" name="id" value="<%= request.getAttribute("task") != null ? ((Task)request.getAttribute("task")).getId() : "" %>">
    <label for="title">Title:</label>
    <input type="text" name="title" id="title" value="<%= request.getAttribute("task") != null ? ((Task)request.getAttribute("task")).getTitle() : "" %>" required><br><br>
    <label for="description">Description:</label>
    <input type="text" name="description" id="description" value="<%= request.getAttribute("task") != null ? ((Task)request.getAttribute("task")).getDescription() : "" %>" required><br><br>
    <label for="isCompleted">Completed:</label>
    <input type="checkbox" name="isCompleted" id="isCompleted" <%= request.getAttribute("task") != null && ((Task)request.getAttribute("task")).isCompleted() ? "checked" : "" %>><br><br>
    <input type="submit" value="Update Task">
</form>
<a href="tasks">Back to Task List</a>



</body>
</html>
