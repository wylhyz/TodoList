/**
 * bean对象
 * @param id  UUID
 * @param title
 * @param description
 * @param completed
 * @constructor
 */
function Task(id, title, description, completed) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.completed = completed;
}

module.exports = Task;
