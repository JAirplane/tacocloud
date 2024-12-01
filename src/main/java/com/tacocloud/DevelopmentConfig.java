package com.tacocloud;

import com.tacocloud.data.*;
import com.tacocloud.domain.*;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@Configuration
@Profile("!prod")
public class DevelopmentConfig {

    @Bean
    public CommandLineRunner dataLoader(IngredientRepository repo, UserRepository userRepository,
                                        TacoRepository tacoRepository, OrderRepository orderRepository,
                                        PasswordEncoder encoder) {
        // user repo for ease of testing with a built-in user
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Ingredient flourTortilla = new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP);
                Ingredient cornTortilla = new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP);
                Ingredient groundBeef = new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN);
                Ingredient carnitas = new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN);
                Ingredient tomatoes = new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES);
                Ingredient lettuce = new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES);
                Ingredient cheddar = new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE);
                Ingredient jack = new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE);
                Ingredient salsa = new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE);
                Ingredient sourCream = new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE);
                repo.save(flourTortilla);
                repo.save(cornTortilla);
                repo.save(groundBeef);
                repo.save(carnitas);
                repo.save(tomatoes);
                repo.save(lettuce);
                repo.save(cheddar);
                repo.save(jack);
                repo.save(salsa);
                repo.save(sourCream);

                TacoUser user = new TacoUser("clruser", encoder.encode("password"),
                        "Craig Walls", "123 North Street", "Cross Roads", "TX",
                        "76227", "123-123-1234");

                Taco taco1 = new Taco();
                taco1.setName("Carnivore");
                taco1.setIngredients(Arrays.asList(flourTortilla, groundBeef, carnitas, sourCream, salsa, cheddar));
                Taco taco2 = new Taco();
                taco2.setName("Bovine Bounty");
                taco2.setIngredients(Arrays.asList(cornTortilla, groundBeef, cheddar, jack, sourCream));
                Taco taco3 = new Taco();
                taco3.setName("Veg-Out");
                taco3.setIngredients(Arrays.asList(flourTortilla, cornTortilla, tomatoes, lettuce, salsa));

                tacoRepository.save(taco1);
                tacoRepository.save(taco2);
                tacoRepository.save(taco3);

                TacoOrder order = new TacoOrder();
                order.setDeliveryName("Default delivery");
                order.setDeliveryStreet("123 North Street");
                order.setDeliveryCity("Cross Roads");
                order.setDeliveryState("TX");
                order.setDeliveryZip("76227");
                order.setCcNumber("4556737586899855");
                order.setCcCVV("111");
                order.setCcExpiration("05/30");
                order.addTaco(taco1);
                order.addTaco(taco2);
                order.addTaco(taco3);
                order.setUser(user);

                userRepository.save(user);
                orderRepository.save(order);

            }
        };
    }
}
