package com.navulia.audit.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
public class SupplierOutputDTO extends SupplierInputDTO{
    private final long id;
    private final String createdBy;
    private final LocalDateTime createdDate;
    private final String lastModifiedBy;
    private final LocalDateTime lastModifiedDate;

    public SupplierOutputDTO(final long id,final String name, final String address,final String contactDetails,final String specialties,final String createdBy,final LocalDateTime createdDate,final String lastModifiedBy,final LocalDateTime lastModifiedDate) {
        super(name, address, contactDetails,specialties);
        this.id=id;
        this.createdBy=createdBy;
        this.createdDate=createdDate;
        this.lastModifiedBy=lastModifiedBy;
        this.lastModifiedDate=lastModifiedDate;
    }

}
