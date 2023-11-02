package com.plant.fruit.pineapple.repository;

import com.plant.fruit.pineapple.entity.User;
import com.plant.fruit.waxberry.jpa.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {

    User findByUsername(String username);

}
