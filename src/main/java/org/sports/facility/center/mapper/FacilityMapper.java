package org.sports.facility.center.mapper;

import org.sports.facility.center.dto.FacilityDto;
import org.sports.facility.center.entity.Booking;
import org.sports.facility.center.entity.Facility;

import java.util.List;

public interface FacilityMapper {

    FacilityDto toDto(Facility facility);

    List<FacilityDto> toDto(List<Facility> facilities);


    Facility toEntity(FacilityDto facilityDto);

}
