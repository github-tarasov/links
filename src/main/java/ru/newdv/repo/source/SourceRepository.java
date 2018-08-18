package ru.newdv.repo.source;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.newdv.entity.source.SourceTable;

public interface SourceRepository extends JpaRepository<SourceTable, String> {}
