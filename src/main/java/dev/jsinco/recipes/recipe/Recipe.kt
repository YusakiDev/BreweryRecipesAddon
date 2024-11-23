package dev.jsinco.recipes.recipe

import com.dre.brewery.recipe.PotionColor

// We're not using BreweryX's BRecipe class because it has a bunch of extra stuff that we don't need
data class Recipe (
    val recipeKey: String,

    val name: String,
    val difficulty: Int,
    val cookingTime: Int,
    val distillRuns: Int,
    val distillTime: Int,
    val age: Int,
    val woodType: BarrelWoodTypes,

    val ingredients: Map<String, Int>,

    val potionColor: PotionColor?,
    val customModelData: Int,
    val rarityWeight: Int
) {

    class Builder {
        private var recipeKey: String = ""
        private var name: String = ""
        private var difficulty: Int = 0
        private var cookingTime: Int = 0
        private var distillRuns: Int = 0
        private var distillTime: Int = 0
        private var age: Int = 0
        private var woodType: BarrelWoodTypes = BarrelWoodTypes.ANY
        private var ingredients: Map<String, Int> = emptyMap()
        private var potionColor: PotionColor? = null
        private var customModelData: Int = 0
        private var rarityWeight: Int = 0

        fun recipeKey(recipeKey: String) = apply { this.recipeKey = recipeKey }
        fun name(name: String) = apply { this.name = name }
        fun difficulty(difficulty: Int) = apply { this.difficulty = difficulty }
        fun cookingTime(cookingTime: Int) = apply { this.cookingTime = cookingTime }
        fun distillRuns(distillRuns: Int) = apply { this.distillRuns = distillRuns }
        fun distillTime(distillTime: Int) = apply { this.distillTime = distillTime }
        fun age(age: Int) = apply { this.age = age }
        fun woodType(woodType: BarrelWoodTypes) = apply { this.woodType = woodType }
        fun ingredients(ingredients: Map<String, Int>) = apply { this.ingredients = ingredients }
        fun potionColor(potionColor: PotionColor?) = apply { this.potionColor = potionColor }
        fun customModelData(customModelData: Int) = apply { this.customModelData = customModelData }
        fun rarityWeight(rarityWeight: Int) = apply { this.rarityWeight = rarityWeight }

        fun build() = Recipe(recipeKey, name, difficulty, cookingTime, distillRuns, distillTime, age, woodType, ingredients, potionColor, customModelData, rarityWeight)
    }

    enum class BarrelWoodTypes(val woodNumber: Int) {
        ANY(0),
        BIRCH(1),
        OAK(2),
        JUNGLE(3),
        SPRUCE(4),
        ACACIA(5),
        DARK_OAK(6),
        CRIMSON(7),
        WARPED(8),
        MANGROVE(9),
        CHERRY(10),
        BAMBOO(11);

        companion object {
            fun fromInt(woodNumber: Int): BarrelWoodTypes {
                return entries.firstOrNull { it.woodNumber == woodNumber } ?: ANY
            }
        }
    }
}