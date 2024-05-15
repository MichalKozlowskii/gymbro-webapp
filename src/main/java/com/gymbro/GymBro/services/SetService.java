package com.gymbro.GymBro.services;

import com.gymbro.GymBro.models.Set;
import com.gymbro.GymBro.web.DTO.SetDto;

public interface SetService {
    SetDto mapToSetDto(Set set);
}
