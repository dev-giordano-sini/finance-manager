package it.financemanager.role;
import it.financemanager.common.BaseEntity;
import java.time.Instant;
public class Role extends BaseEntity {
  private final String code;
  private final String description;
  public Role(String code, String description) {
    this(null, 0, null, null, code, description);
  }
  public Role(Long id, long version, Instant createdAt, Instant updatedAt,
              String code, String description) {
    super(id, version, createdAt, updatedAt);
    this.code = code;
    this.description = description;
  }
  public String getCode() { return code; }
  public String getDescription() { return description; }
}
