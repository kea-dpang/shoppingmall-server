package com.example.shoppingmallserver.service;

import com.example.shoppingmallserver.dto.*;
import com.example.shoppingmallserver.entity.Cart;
import com.example.shoppingmallserver.entity.Mileage;
import com.example.shoppingmallserver.entity.User;
import com.example.shoppingmallserver.entity.Wishlist;
import com.example.shoppingmallserver.exception.UserNotFoundException;
import com.example.shoppingmallserver.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public List<Cart> getCartItemByUserId(Long user_id) {
        return null;
    }

    @Override
    public Cart addCartItem(Long user_id, AddCartItemDto addCartItemDto) {
        return null;
    }

    @Override
    public void deleteCartItem(Long user_id) {

    }

    @Override
    public List<Cart> purchaseCartItem(Long id, PurchaseCartItemDto purchaseCartItemDto) {
        return null;
    }

    @Override
    public Mileage requestMileageRecharge(Long user_id, ChargeMileageDto chargeMileageDto) {
        return null;
    }

    @Override
    public Wishlist addWishlistItem(Long user_id, AddWishlistItemDto addWishlistItemDto) {
        return null;
    }

    @Override
    public List<Wishlist> getWishlistItemList(Long user_id) {
        return null;
    }

    @Override
    public void deleteWishlistItem(Long id) {

    }

    @Override
    public User getAdminUserById(Long user_id) {
        return null;
    }

    @Override
    public List<User> getUserList(Optional<String> keyword) {
        return null;
    }

    @Override
    public User updateUser(Long user_id, UpdateUserDto updateUserDto) {
        return null;
    }

    @Override
    public User createUser(CreateUserDto userCreateDto) {
        return null;
    }

    @Override
    public void deleteUser(Long user_id) {

    }

    @Override
    public List<User> getMileageList(Optional<String> keyword) {
        return null;
    }

    @Override
    public Mileage updateMileageStatus(Long user_id, UpdateMileageStatusDto updateMileageStatusDto) {
        return null;
    }
}