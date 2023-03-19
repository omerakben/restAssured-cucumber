package api;

public enum Endpoints {
    BASE_URL("https://simple-books-api.glitch.me"),
    BOOKS("/books"),
    ORDERS("/orders"),
    STATUS("/status"),
    TOKEN("/api-clients/");

    private final String path;

    Endpoints(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}