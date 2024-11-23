package dev.jsinco.recipes.recipe

import com.dre.brewery.recipe.BRecipe
import com.dre.brewery.recipe.PotionColor
import dev.jsinco.recipes.Recipes

object RecipeUtil {

    private val loadedRecipes: MutableList<Recipe> = mutableListOf()

    @JvmStatic
    fun loadAllRecipes() {
        if (loadedRecipes.isNotEmpty()) {
            loadedRecipes.clear()
            Recipes.getAddonInstance().addonLogger.info("Refreshing loaded recipes!")
        }

        for (recipe in BRecipe.getRecipes()) {
            loadedRecipes.add(getRecipeFromBRecipe(recipe))
        }
    }

    @JvmStatic
    fun getAllRecipes(): List<Recipe> {
        return loadedRecipes
    }

    @JvmStatic
    fun getRecipeFromBRecipe(bRecipe: BRecipe): Recipe {

        var cmModelData = 0
        if (bRecipe.cmData != null && bRecipe.cmData.isNotEmpty()) {
            cmModelData = bRecipe.cmData[0]
        }

        return Recipe.Builder()
            .name(bRecipe.recipeName)
            .difficulty(bRecipe.difficulty)
            .cookingTime(bRecipe.cookingTime)
            .distillRuns(bRecipe.distillruns.toInt())
            .distillTime(bRecipe.distillTime)
            .age(bRecipe.age)
            .woodType(Recipe.BarrelWoodTypes.fromInt(bRecipe.wood.toInt()))
            .ingredients(parseIngredients(bRecipe.ingredients.map {it.toConfigString()}))
            .potionColor(PotionColor.fromString(bRecipe.color.toString()))
            .customModelData(cmModelData)
            .rarityWeight(bRecipe.difficulty)
            .build()
    }

    fun parseIngredients(ingredientsRaw: List<String>): Map<String, Int> {
        val ingredientsMap: MutableMap<String, Int> = mutableMapOf()
        for (ingredientRaw in ingredientsRaw) {
            ingredientsMap[ingredientRaw.substringBefore("/")] = ingredientRaw.substringAfter("/").toInt()
        }
        return ingredientsMap
    }

    fun getRandomRecipe(): Recipe {
        return getAllRecipes().random()
    }

    @JvmStatic
    fun getRecipeFromKey(recipeKey: String): Recipe? {
        return getAllRecipes().find { it.recipeKey == recipeKey }
    }



    // We need the one in the middle!
    // recipeName/recipeName2/recipeName3
    // recipeName/recipeName2

    fun parseRecipeName(recipeName: String): String {
        if (!recipeName.contains("/")) {
             return recipeName
        }
        val newString = recipeName.substringAfter("/")
        if (newString.contains("/")) {
            return newString.substring(0, newString.indexOf("/"))
        }
        return newString
    }

    fun parseIngredientsName(string: String): String {
        if (string.contains(":")) {
            return string.substringAfter(":")
        }
        return string
    }
}