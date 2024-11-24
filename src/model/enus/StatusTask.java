package model.enus;

public enum StatusTask {

    TODO(1, "to do"),
    IN_PROGRESS(2, "in progress"),
    DONE(3, "done");

    private int id;
    private String descricao;

    StatusTask(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public static StatusTask getById(Integer id) {
        if (id == null) return null;

        for (StatusTask task : StatusTask.values()) {
            if (task.equals(task.getId())) {
                return task;
            }
        }

        throw new IllegalArgumentException("invalid id" + id);
    }
}
