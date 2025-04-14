package org.userservice.repo;

import org.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository  extends JpaRepository<User,String> {
    User findByUserid(String userid);
    User findByUseridAndPwd(String userid, String pwd);
}
