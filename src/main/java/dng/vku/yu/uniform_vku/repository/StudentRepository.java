package dng.vku.yu.uniform_vku.repository;

import dng.vku.yu.uniform_vku.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long>, JpaRepository<Student, Long> {
    @Override
    Optional<Student> findById(Long aLong);
    Optional<Student> findByVerificationCode(String aCode);
    @Query("update Student st set st.received=:newReceived where st.id = :id")
    void updateReceivedById(@Param("id") Long id, @Param("newReceived") Boolean received);
}
