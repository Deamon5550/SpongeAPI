/*
 * This file is part of SpongeAPI, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered <https://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.spongepowered.api.item.recipe.single;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.item.recipe.Recipe;
import org.spongepowered.api.item.recipe.RecipeType;
import org.spongepowered.api.util.CatalogBuilder;
import org.spongepowered.api.util.ResettableBuilder;

import java.util.function.Predicate;

/**
 * A StoneCutter Recipe.
 */
public interface StoneCutterRecipe extends Recipe {

    static Builder builder() {
        return Sponge.getRegistry().getBuilderRegistry().provideBuilder(Builder.class);
    }

    @Override
    RecipeType<? extends StoneCutterRecipe> getType();

    /**
     * Builds a simple furnace recipe.
     */
    interface Builder extends ResettableBuilder<StoneCutterRecipe, Builder> {

        /**
         * Sets the ingredient and returns this builder.
         *
         * @param ingredient The ingredient
         *
         * @return This builder, for chaining
         */
        ResultStep ingredient(ItemType ingredient);

        /**
         * Sets the ingredient and exemplary ingredient.
         *
         * @param predicate The ingredient predicate
         * @param exemplaryIngredient The exemplary ingredient for the recipe book
         *
         * @return This builder, for chaining
         */
        ResultStep ingredient(Predicate<ItemStackSnapshot> predicate, ItemType exemplaryIngredient);

        interface ResultStep extends Builder {

            /**
             * Changes the result and returns this builder. The result is the
             * {@link ItemStack} created when the recipe is fulfilled.
             *
             * @param result The output of this recipe
             * @return This builder, for chaining
             */
            EndStep result(ItemStackSnapshot result);

            /**
             * Changes the result and returns this builder. The result is the
             * {@link ItemStack} created when the recipe is fulfilled.
             *
             * @param result The output of this recipe
             * @return This builder, for chaining
             */
            default EndStep result(ItemStack result) {
                return this.result(result.createSnapshot());
            }

        }

        interface EndStep extends Builder, CatalogBuilder<StoneCutterRecipe, Builder> {

            @Override
            EndStep key(ResourceKey key);

            /**
             * Builds the {@link StoneCutterRecipe}.
             *
             * @return The built stone cutter recipe
             * @throws IllegalStateException If not all the recipe builder steps are completed
             *                               or the {@link #key(ResourceKey)} isn't set.
             */
            @Override
            StoneCutterRecipe build() throws IllegalStateException;
        }
    }
}
