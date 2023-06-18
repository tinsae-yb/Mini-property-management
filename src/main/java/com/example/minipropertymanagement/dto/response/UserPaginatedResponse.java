package com.example.minipropertymanagement.dto.response;

import lombok.Data;
import org.springframework.hateoas.PagedModel;

import java.util.List;

@Data
public class UserPaginatedResponse {
    private PagedModel.PageMetadata page;
    private List<UserSmallResponse> users;
}
