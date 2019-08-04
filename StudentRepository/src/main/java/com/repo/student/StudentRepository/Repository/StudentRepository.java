package com.repo.student.StudentRepository.Repository;

import com.repo.student.StudentRepository.Model.Student;
import java.util.List;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * Created by AdarshSrinivasan
 */
@Repository
public interface StudentRepository extends CassandraRepository<Student, Long> {
  
}
