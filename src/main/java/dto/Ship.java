package dto;

public class Ship {

    private final String id;
    private final int size;

    public Ship(String id, int size) {
        this.id = id;
        this.size = size;
    }

    public String getId() {
        return id;
    }

    public int getSize() {
        return size;
    }
}
