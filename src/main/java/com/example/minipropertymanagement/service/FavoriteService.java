package com.example.minipropertymanagement.service;

import com.example.minipropertymanagement.dto.response.FavoritesResponse;
import com.example.minipropertymanagement.exception.ForbiddenAccess;

public interface FavoriteService {
    FavoritesResponse getFavorites();

    void deleteFavorite(Long favoriteId) throws ForbiddenAccess;
}
