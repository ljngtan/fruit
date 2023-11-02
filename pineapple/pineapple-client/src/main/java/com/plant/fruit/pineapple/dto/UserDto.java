package com.plant.fruit.pineapple.dto;

import com.plant.fruit.waxberry.validation.group.Save;
import com.plant.fruit.waxberry.validation.group.Update;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDto {

    @NotNull(groups = {Update.class})
    private Long id;

    @NotBlank(groups = {Save.class, Update.class})
    private String username;

    @NotBlank(groups = {Save.class, Update.class})
    private String password;

    @NotBlank(groups = {Save.class, Update.class})
    private String name;

    @NotBlank(groups = {Save.class, Update.class})
    private String mobile;

    @NotBlank(groups = {Save.class, Update.class})
    private String email;

}
