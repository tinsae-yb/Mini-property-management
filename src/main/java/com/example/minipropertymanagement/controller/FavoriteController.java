package com.example.minipropertymanagement.controller;


import com.example.minipropertymanagement.dto.response.FavoritesResponse;
import com.example.minipropertymanagement.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/favorites")
@RequiredArgsConstructor
public class FavoriteController {

        private final FavoriteService favoriteService;

        @GetMapping("")
        public FavoritesResponse getFavorites() {
            return favoriteService.getFavorites();
        }

}
