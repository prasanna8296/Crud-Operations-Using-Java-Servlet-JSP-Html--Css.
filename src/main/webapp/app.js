document.addEventListener('DOMContentLoaded', () => {
    const taskList = document.getElementById('task-list');
    const addTaskBtn = document.getElementById('add-task-btn');
    const taskInput = document.getElementById('task-input');
    const descriptionInput = document.getElementById('description-input');
    const isCompletedCheckbox = document.getElementById('is-completed-checkbox');

    // Load tasks
    function loadTasks() {
        const xhr = new XMLHttpRequest();
        xhr.open('GET', 'tasks?action=getAll', true);
        xhr.setRequestHeader('Content-Type', 'application/json');
        xhr.onload = function () {
            if (xhr.status === 200) {
                const tasks = JSON.parse(xhr.responseText);
                taskList.innerHTML = '';
                tasks.forEach(task => {
                    const li = document.createElement('li');
                    li.textContent = `${task.title} - ${task.description} - ${task.isCompleted ? 'Completed' : 'Pending'}`;
                    const deleteBtn = document.createElement('button');
                    deleteBtn.textContent = 'Delete';
                    deleteBtn.classList.add('delete-btn');
                    deleteBtn.onclick = () => deleteTask(task.id);
                    li.appendChild(deleteBtn);
                    taskList.appendChild(li);
                });
            }
        };
        xhr.send();
    }

    // Add task
    addTaskBtn.onclick = () => {
        const title = taskInput.value;
        const description = descriptionInput.value;
        const isCompleted = isCompletedCheckbox.checked;

        const xhr = new XMLHttpRequest();
        xhr.open('POST', 'tasks?action=insert', true);
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.onload = function () {
            if (xhr.status === 201) {
                loadTasks();
                taskInput.value = '';
                descriptionInput.value = '';
                isCompletedCheckbox.checked = false;
            }
        };
        xhr.send(`title=${encodeURIComponent(title)}&description=${encodeURIComponent(description)}&isCompleted=${isCompleted}`);
    };

    // Delete task
    function deleteTask(id) {
        const xhr = new XMLHttpRequest();
        xhr.open('POST', `tasks?action=delete&id=${id}`, true);
        xhr.onload = function () {
            if (xhr.status === 204) {
                loadTasks();
            }
        };
        xhr.send();
    }

    loadTasks(); // Load tasks on page load
});
