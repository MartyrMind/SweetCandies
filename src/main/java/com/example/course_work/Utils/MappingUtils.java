package com.example.course_work.Utils;

import com.example.course_work.Entity.*;
import com.example.course_work.Entity.DTO.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MappingUtils {
    public OrderItemDto mapToOrderItemDto(OrderItem orderItem) {
        if (orderItem == null) return null;
        OrderItemDto dto = new OrderItemDto();
        dto.setGoodName(orderItem.getProduct().getTitle());
        dto.setCost(orderItem.getProduct().getCost());
        dto.setAmount(orderItem.getAmount());
        return dto;
    }

    /**
     * Принимает экземпляр класса Order и возвращает его новое представление
     * @param order - экземпляр оборачиваемого класса класса
     * @return dto - экземпляр класса данных
     */
    public OrderDto mapToOrderDto(Order order) {
        if (order == null) return null;
        OrderDto dto = new OrderDto();
        List<OrderItemDto> items = null;
        //переносим необходимые поля
        dto.setId(order.getId());
        dto.setDate(order.getDate());
        //некоторых полей может не существовать, нужна проверка на null
        if (order.getUser() != null)
            dto.setEmail(order.getUser().getEmail());
        if (order.getOrderItems() != null) //при помощи Stream API преобразуем объекты к новому представлению
           items = order.getOrderItems().stream().map(this::mapToOrderItemDto).toList();
        dto.setItems(items);
        float total = 0F;
        if (dto.getItems() != null) //посчитаем сумму заказа
            for (OrderItemDto item : dto.getItems()) total += item.getAmount() * item.getCost();
        dto.setTotalPrice(total);
        return dto;
    }


    public ProductDto mapToProductDto(Product product) {
        if (product == null) return null;
        ProductDto dto = new ProductDto();
        List<String> images = null;
        dto.setId(product.getId());
        dto.setTitle(product.getTitle());
        dto.setDescription(product.getDescription());
        dto.setRating(product.getRating());
        dto.setCost(product.getCost());
        if (product.getImages() != null)
            images = product.getImages().stream().map(ProductImage::getUrl).collect(Collectors.toList());
        dto.setImages(images);
        return dto;
    }

    public CategoryDto mapToCategoryDto(Category category) {
        if (category == null) return null;
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setTitle(category.getTitle());
        List<String> names = new ArrayList<>();
        if (category.getProducts() != null) {
            names = category.getProducts().stream().map(Product::getTitle).collect(Collectors.toList());
        }
        dto.setProductTitles(names);
        return dto;
    }

    public UserDto mapToUserDto(User user) {
        if (user == null) return null;
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstname(user.getFirstname());
        dto.setSecondname(user.getSecondname());
        dto.setBirthday(user.getBirthday());
        dto.setProfileImage(user.getLink());
        Map<String, Integer> products = new HashMap<>();
        Map<String, Integer> categories = new HashMap<>();
        List<OrderDto> orders = new ArrayList<>();
        if (user.getOrders() != null && !user.getOrders().isEmpty()) {
            for (Order order : user.getOrders()) {
                orders.add(mapToOrderDto(order));
                if (order.getOrderItems() != null) {
                    for (OrderItem item : order.getOrderItems()) {
                        String title = item.getProduct().getTitle();
                        String categoryTitle = "none";
                        products.merge(title, 1, Integer::sum);
                        Category category = item.getProduct().getCategory();
                        if (category != null)
                            categoryTitle = item.getProduct().getCategory().getTitle();
                        categories.merge(categoryTitle, 1, Integer::sum);
                    }
                }
                if (!products.isEmpty())
                    dto.setFavouriteProduct(Collections.max(products.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey());
                if (!categories.isEmpty())
                    dto.setFavouriteCategory(Collections.max(categories.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey());
            }
            dto.setOrders(orders);
        }
        return dto;
    }
}
