package org.sports.facility.center.mapper;

import org.sports.facility.center.dto.FacilityDto;
import org.sports.facility.center.entity.Facility;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacilityMapperImpl implements FacilityMapper{

    private static FacilityDto mapToFacilityDto(Facility facility){
        FacilityDto facilityDto=new FacilityDto();
        facilityDto.setFacilityName(facility.getFacilityName());
        facilityDto.setCapacity(facility.getCapacity());
        facilityDto.setOpeningTime(LocalTime.parse(facility.getOpeningTime()));
        facilityDto.setClosingTime(LocalTime.parse(facility.getClosingTime()));
        return facilityDto;
    }
    @Override
    public FacilityDto toDto(Facility facility) {
        return mapToFacilityDto(facility);
    }

    @Override
    public List<FacilityDto> toDto(List<Facility> facilities) {
        return facilities.stream().map(FacilityMapperImpl::mapToFacilityDto).collect(Collectors.toList());
    }

    @Override
    public Facility toEntity(FacilityDto facilityDto) {
        return Facility.builder()
            .openingTime(facilityDto.getOpeningTime().toString())
            .closingTime(facilityDto.getClosingTime().toString())
            .description(facilityDto.getDescription())
            .capacity(facilityDto.getCapacity())
            .facilityName(facilityDto.getFacilityName())
            .build();
    }
}
