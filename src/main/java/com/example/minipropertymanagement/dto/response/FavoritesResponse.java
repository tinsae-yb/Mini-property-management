package com.example.minipropertymanagement.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class FavoritesResponse {

    List<FavoriteResponse> favorites;
}
