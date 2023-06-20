package com.example.minipropertymanagement.service.impl;


import com.amazonaws.services.kms.model.NotFoundException;
import com.example.minipropertymanagement.domain.Favorite;
import com.example.minipropertymanagement.domain.User;
import com.example.minipropertymanagement.dto.response.FavoriteResponse;
import com.example.minipropertymanagement.dto.response.FavoritesResponse;
import com.example.minipropertymanagement.exception.InvalidCredential;
import com.example.minipropertymanagement.repo.FavoriteRepository;
import com.example.minipropertymanagement.repo.UserRepository;
import com.example.minipropertymanagement.service.FavoriteService;
import com.example.minipropertymanagement.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final AuthUtil authUtil;
    private final ModelMapper modelMapper;
    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;


    @Override
    public FavoritesResponse getFavorites() {
        String username = authUtil.getUsername();
        User user = userRepository.findByEmail(username).orElseThrow(() -> new NotFoundException("User not found"));
        List<Favorite> favorites = favoriteRepository.findAllByUserId(user.getId());
        FavoritesResponse favoritesResponse = new FavoritesResponse();
        favoritesResponse.setFavorites(favorites.stream().map(favorite -> modelMapper.map(favorite, FavoriteResponse.class)).collect(Collectors.toList()));

        return favoritesResponse;

    }

    @Override
    public void deleteFavorite(Long favoriteId) throws InvalidCredential {
        String username = authUtil.getUsername();
        User user = userRepository.findByEmail(username).orElseThrow(() -> new NotFoundException("User not found"));
        Favorite favorite = favoriteRepository.findById(favoriteId).orElseThrow(() -> new NotFoundException("Favorite not found"));
        if (favorite.getUser().getId() == user.getId()) {
            favoriteRepository.delete(favorite);
        } else {

            throw new InvalidCredential("You are not authorized to delete this favorite");
        }


    }
}
