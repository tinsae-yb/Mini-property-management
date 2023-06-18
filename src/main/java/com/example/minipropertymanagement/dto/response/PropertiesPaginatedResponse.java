package com.example.minipropertymanagement.dto.response;

import lombok.Data;
import org.springframework.hateoas.PagedModel;

import java.util.List;

@Data
public class PropertiesPaginatedResponse {
    private PagedModel.PageMetadata page;
    private List<PropertySmallResponse> properties;
}
