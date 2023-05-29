package com.nash.assignment.fakedata;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nash.assignment.constant.AccountStatus;
import com.nash.assignment.constant.ProductStatus;
import com.nash.assignment.constant.UserRole;
import com.nash.assignment.modal.Account;
import com.nash.assignment.modal.Category;
import com.nash.assignment.modal.Product;
import com.nash.assignment.repositories.AccountRepositories;
import com.nash.assignment.repositories.CategoriesRepositories;
import com.nash.assignment.repositories.ProductsRepositories;

@Configuration
public class Fakedata {
    @Bean
    CommandLineRunner initDatabase(AccountRepositories accountRepositories, ProductsRepositories productsRepositories,
            CategoriesRepositories categoriesRepositories) {
        return new CommandLineRunner() {

            @Override
            public void run(String... args) throws Exception {
                List<Account> accounts = new ArrayList<>();
                Account admin = new Account("012345678", "admin", "admin",
                        "$2a$10$3dIANBdA33QEMoWfSwtrx.a.9IkV82m200eswJ7GD20ZK0Q8/0FNC", UserRole.ROLE_ADMIN,
                        AccountStatus.ACTIVE, "admin@gmail.com");
                Account user = new Account("0123456789", "username", "username",
                        "$2a$12$u/ysn8eCWimrcrsu5wqhdOlRZWNji7o0zay7gj0.k3AVqszj2cG8W", UserRole.ROLE_USER,
                        AccountStatus.ACTIVE, "user@gmail.com");
                accounts.add(user);
                accounts.add(admin);
                List<Category> categories = new ArrayList<>();
                Category category1 = new Category("Cherry Blossom");
                Category category2 = new Category("Flowering Shrub");
                categories.add(category1);
                categories.add(category2);
                List<Product> products = new ArrayList<>();
                Product product1 = new Product("Rose", "12000", ProductStatus.AVAILABLE, category1);
                product1.setImage("https://res.cloudinary.com/dyvrvbcxx/image/upload/v1685353200/rose_zonxa9.jpg");
                product1.setDescription("Rose is a most popular flower");
                Product product2 = new Product("Violet", "12000", ProductStatus.AVAILABLE, category2);
                product2.setImage("https://res.cloudinary.com/dyvrvbcxx/image/upload/v1685353206/violet_mtwzng.jpg");
                product2.setDescription("Viola is a genus of flowering plants in the violet family Violaceae");
                Product product3 = new Product("Lavender", "12000", ProductStatus.AVAILABLE, category2);
                product3.setDescription("Lavender is a beautiful and fragrant plant that has many uses.");
                product3.setImage("https://res.cloudinary.com/dyvrvbcxx/image/upload/v1685353198/lavender_gxaazl.jpg");
                Product product4 = new Product("Sakura", "12000", ProductStatus.AVAILABLE, category2);
                product4.setDescription("It is a flower of many trees of genus Prunus or Prunus subgenus Cerasus.");
                product4.setImage("https://res.cloudinary.com/dyvrvbcxx/image/upload/v1685353198/sakura_vcifnv.jpg");
                products.add(product1);
                products.add(product2);
                products.add(product3);
                products.add(product4);
                accountRepositories.saveAll(accounts);
                categoriesRepositories.saveAll(categories);
                productsRepositories.saveAll(products);
            }

        };

    }

}
