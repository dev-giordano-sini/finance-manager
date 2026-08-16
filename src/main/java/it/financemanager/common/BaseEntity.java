package it.financemanager.common;

import java.time.Instant;

/**
 * Framework-free identity and audit state shared by domain entities.
 */
public abstract class BaseEntity {
    private final Long id;
    private final long version;
    private final Instant createdAt;
    private final Instant updatedAt;

    protected BaseEntity() {
        this(null, 0, null, null);
    }

    protected BaseEntity(Long id, long version, Instant createdAt,
                         Instant updatedAt) {
        this.id = id;
        this.version = version;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public long getVersion() {
        return version;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
