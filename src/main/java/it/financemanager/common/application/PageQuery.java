package it.financemanager.common.application;

/** Framework-neutral pagination request used by application ports. */
public record PageQuery(int page, int size, String sortBy, Direction direction) {
    public PageQuery {
        if (page < 0) throw new IllegalArgumentException("page must be non-negative");
        if (size < 1) throw new IllegalArgumentException("size must be positive");
    }

    public enum Direction { ASC, DESC }
}
