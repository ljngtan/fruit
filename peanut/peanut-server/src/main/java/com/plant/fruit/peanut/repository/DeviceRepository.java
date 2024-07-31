package com.plant.fruit.peanut.repository;

import com.plant.fruit.peanut.entity.Device;
import com.plant.fruit.waxberry.jpa.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends BaseRepository<Device, String> {
}
