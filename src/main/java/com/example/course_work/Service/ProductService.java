package com.example.course_work.Service;

import com.example.course_work.Entity.Category;
import com.example.course_work.Entity.DTO.ProductDto;
import com.example.course_work.Entity.Product;
import com.example.course_work.Entity.ProductImage;
import com.example.course_work.Repository.ProductRepository;
import com.example.course_work.Utils.MappingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductImageService imageService;

    public Product save(Product product) {
        if (product.getImages() != null)
            for (ProductImage img: product.getImages()) imageService.addToProduct(img, product);
        return productRepository.save(product);
    }


    public void delete(Integer id) {
        productRepository.deleteById(id);
    }

    public Product getById(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product getByTitle(String title) {
        return productRepository.findProductByTitle(title);
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    /**
     * Метод получает пользовательскую оценку продукта и обновляет соответствующее поле
     * @param id - идентификатор оцениваемого продукта
     * @param rate - пользовательская оценка
     * @return product - информация о продукте с учетом новой оценки
     */
    public Product rateProduct(Integer id, int rate) {
        Product product = getById(id);
        if (product != null) {
            Integer rates = product.getRates(); //количество гоосов
            int sum = product.getRating() * rates; //сумма баллов
            product.setRating((sum + rate) / (rates + 1)); //сновое среднее арифметическое рейтинга
            product.setRates(rates + 1);
            productRepository.save(product);
        }
        return product;
    }

    public void addToCategory(Product product, Category category) {
        if (product != null) {
            product.setCategory(category);
            save(product);
        }
    }
    public List<Product> getOrderedByCost() {
        return productRepository.findAllByOrderByCostAsc();
    }

    public List<Product> getOrderedByRating() {
        return productRepository.findAllByOrderByRatingDesc();
    }

    public List<Product> getBetween(Integer low, Integer high) {
        return productRepository.findAllByCostBetweenOrderByCostAsc(low, high);
    }
    public List<String> getTitles() {
        return productRepository.getTitles();
    }

    public Product update(Product old, Product nw) {
        old.setTitle(nw.getTitle());
        if (nw.getCategory() != null) old.setCategory(nw.getCategory());
        old.setDescription(nw.getDescription());
        old.setCost(nw.getCost());
        return save(old);
    }
}
