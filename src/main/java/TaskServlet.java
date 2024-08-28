import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import crud_operations.Task;
import crud_operations.TaskDAO;
import java.io.IOException;
import java.util.List;

@WebServlet("/tasks")
public class TaskServlet extends HttpServlet {
    private TaskDAO taskDAO;

    @Override
    public void init() throws ServletException {
        taskDAO = new TaskDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "new":
                showNewTaskForm(request, response);
                break;
            case "edit":
                showEditTaskForm(request, response);
                break;
            case "delete":
                deleteTask(request, response);
                break;
            default:
                listTasks(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "insert";
        }

        switch (action) {
            case "insert":
                insertTask(request, response);
                break;
            case "update":
                updateTask(request, response);
                break;
            default:
                listTasks(request, response);
                break;
        }
    }

    private void listTasks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Task> tasks = taskDAO.getAllTasks();
        request.setAttribute("tasks", tasks);
        request.getRequestDispatcher("tasks.jsp").forward(request, response);
    }

    private void showNewTaskForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("task-form.jsp").forward(request, response);
    }

    private void showEditTaskForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam != null && !idParam.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(idParam);
                Task existingTask = taskDAO.getTaskById(id);
                if (existingTask != null) {
                    request.setAttribute("task", existingTask);
                    request.getRequestDispatcher("task-form.jsp").forward(request, response);
                } else {
                    response.sendRedirect("tasks");
                }
            } catch (NumberFormatException e) {
                response.sendRedirect("tasks");
            }
        } else {
            response.sendRedirect("tasks");
        }
    }

    private void insertTask(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        boolean isCompleted = "on".equals(request.getParameter("isCompleted"));

        Task newTask = new Task(0, title, description, isCompleted);
        taskDAO.addTask(newTask);
        response.sendRedirect("tasks");
    }

    private void updateTask(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idParam = request.getParameter("id");
        if (idParam != null && !idParam.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(idParam);
                String title = request.getParameter("title");
                String description = request.getParameter("description");
                boolean isCompleted = "on".equals(request.getParameter("isCompleted"));

                Task updatedTask = new Task(id, title, description, isCompleted);
                taskDAO.updateTask(updatedTask);
                response.sendRedirect("tasks");
            } catch (NumberFormatException e) {
                e.printStackTrace(); // Debug statement
                response.sendRedirect("tasks");
            }
        } else {
            response.sendRedirect("tasks");
        }
    }



    protected void deleteTask(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idParam = request.getParameter("id");
        if (idParam != null && !idParam.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(idParam);
                taskDAO.deleteTask(id);
            } catch (NumberFormatException e) {
                // Handle error, log it if necessary
            }
        }
        response.sendRedirect("tasks");
    }

}
