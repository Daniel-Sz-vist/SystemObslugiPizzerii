public class Table {
    private int number;
    private TableStatus status;


    public Table(int number) {
        this.number = number;
        this.status = TableStatus.FREE;
    }


    public int getNumber() {
        return number;
    }


    public TableStatus getStatus() {
        return status;
    }


    public void setStatus(TableStatus status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "Table{" + number + ":" + status + "}";
    }
}