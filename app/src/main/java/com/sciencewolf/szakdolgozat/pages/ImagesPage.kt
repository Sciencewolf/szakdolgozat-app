package com.sciencewolf.szakdolgozat.pages

import androidx.compose.runtime.Composable
import com.sciencewolf.szakdolgozat.components.imagescomponents.GetImagesFromBucketComponent
import io.github.jan.supabase.SupabaseClient

open class ImagesPage {
    private val getImagesFromBucketComponent = GetImagesFromBucketComponent()

    @Composable
    fun LoadImagesPage(supabase: SupabaseClient) {
        getImagesFromBucketComponent.GetImagesAndDisplayFromBucket(supabase = supabase)
    }

}