package dng.vku.yu.uniform_vku.repository;

import dng.vku.yu.uniform_vku.model.entity.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {
}
