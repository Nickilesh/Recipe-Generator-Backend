package com.ai.RecipeGenerator;

import org.springframework.stereotype.Service;

@Service
public class RecipeService {

    private final ChatModel chatModel;

    public RecipeService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String createRecipe(String ingredients, String cuisine, String dietaryRestrictions) {
        // Constructing the prompt manually
        String prompt = "I want to create a recipe using the following ingredients: " + ingredients + ".\n" +
                "The preferred cuisine is: " + cuisine + ".\n" +
                "Consider my dietary restrictions: " + dietaryRestrictions + ".\n" +
                "Provide me a recipe with a title, list of ingredients, and instructions.";

        // Calling the AI model
        return chatModel.call(prompt);
    }
}
