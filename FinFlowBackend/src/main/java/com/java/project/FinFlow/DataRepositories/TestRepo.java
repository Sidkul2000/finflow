package com.java.project.FinFlow.DataRepositories;

import com.java.project.FinFlow.Tables.TestTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepo extends JpaRepository<TestTable, Long> {
}
