package br.edu.unifacisa.ecommerce.sevice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.unifacisa.ecommerce.dto.cart.AddToCartDto;
import br.edu.unifacisa.ecommerce.dto.cart.CartDto;
import br.edu.unifacisa.ecommerce.dto.cart.CartItemDto;
import br.edu.unifacisa.ecommerce.exceptions.CartItemNotExistException;
import br.edu.unifacisa.ecommerce.model.Cart;
import br.edu.unifacisa.ecommerce.model.Product;
import br.edu.unifacisa.ecommerce.repository.CartRepository;

@Service
@Transactional
public class CartService {

    @Autowired
    private  CartRepository cartRepository;

    public CartService(){}

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public void addToCart(AddToCartDto addToCartDto,int userId){
        Cart cart = getAddToCartFromDto(addToCartDto,userId);
        cartRepository.save(cart);
    }

    private Cart getAddToCartFromDto(AddToCartDto addToCartDto, int userId) {
        Cart cart = new Cart(addToCartDto, userId);
        return cart;
    }

    public CartDto listCartItems(int user_id) {
        List<Cart> cartList = cartRepository.findAllByUserIdOrderByCreatedDateDesc(user_id);
        List<CartItemDto> cartItems = new ArrayList<>();
        for (Cart cart:cartList){
            CartItemDto cartItemDto = getDtoFromCart(cart);
            cartItems.add(cartItemDto);
        }
        double totalCost = 0;
        for (CartItemDto cartItemDto :cartItems){
            totalCost += (cartItemDto.getProduct().getPrice()* cartItemDto.getQuantity());
        }
        CartDto cartDto = new CartDto(cartItems,totalCost);
        return cartDto;
    }


    public static CartItemDto getDtoFromCart(Cart cart) {
        CartItemDto cartItemDto = new CartItemDto(cart);
        return cartItemDto;
    }


    public void updateCartItem(AddToCartDto cartDto,int userId,Product product){
        Cart cart = getAddToCartFromDto(cartDto,userId);
        cart.setQuantity(cartDto.getQuantity());
        cart.setUserId(userId);
        cart.setId(cartDto.getId());
        cart.setProductId(product.getId());
        cart.setCreatedDate(new Date());
        cartRepository.save(cart);
    }

    public void deleteCartItem(int id,int userId) throws CartItemNotExistException {
        if (!cartRepository.existsById(id))
            throw new CartItemNotExistException("Cart id is invalid : " + id);
        cartRepository.deleteById(id);

    }

    public void deleteCartItems(int userId) {
        cartRepository.deleteAll();
    }


}
