package ru.newdv.repo.targett;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.newdv.entity.targett.TargetTable;

public interface TargetRepository extends JpaRepository<TargetTable, String> {}
