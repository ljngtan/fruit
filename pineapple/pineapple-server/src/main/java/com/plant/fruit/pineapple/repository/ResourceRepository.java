package com.plant.fruit.pineapple.repository;

import com.plant.fruit.pineapple.entity.Resource;
import com.plant.fruit.waxberry.jpa.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends BaseRepository<Resource, Long> {
}
