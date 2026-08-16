package it.financemanager.common.application;

import java.util.List;

/** Framework-neutral page returned by application ports. */
public record PageResult<T>(List<T> content, int page, int size,
                            long totalElements, int totalPages) {
  public PageResult { content = List.copyOf(content); }
}
