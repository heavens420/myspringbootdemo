package tk.zlx.redistemplate.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tk.zlx.redistemplate.entities.User;


@Repository
public interface UserRepository extends JpaRepository<User,Integer>, JpaSpecificationExecutor<User> {

//    @Override
//    User findById(Integer id);

    @Modifying
    @Query("update User set tel = 9999 where id = ?1")
    int updatebyId(int id);

    int deleteById(int id);
}
