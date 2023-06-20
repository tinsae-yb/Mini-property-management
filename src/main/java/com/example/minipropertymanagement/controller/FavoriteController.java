package com.example.minipropertymanagement.controller;


import com.example.minipropertymanagement.dto.response.FavoritesResponse;
import com.example.minipropertymanagement.exception.InvalidCredential;
import com.example.minipropertymanagement.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/favorites")
@RequiredArgsConstructor
public class FavoriteController {

        private final FavoriteService favoriteService;

        @GetMapping("")
        @ResponseStatus(HttpStatus.OK)
        public FavoritesResponse getFavorites() {
            return favoriteService.getFavorites();
        }


        @ResponseStatus(HttpStatus.ACCEPTED)
        @DeleteMapping("/{favoriteId}")

        public void deleteFavorite( @PathVariable Long favoriteId) throws InvalidCredential {
            favoriteService.deleteFavorite(favoriteId);
        }

}
