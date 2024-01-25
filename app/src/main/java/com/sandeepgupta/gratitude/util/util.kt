package com.sandeepgupta.gratitude.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.request.ImageRequest
import com.sandeepgupta.gratitude.model.CardModel
import com.sandeepgupta.gratitude.model.SocialMediaModel
import com.sandeepgupta.gratitude.model.socialMediaList
import java.io.File
import java.io.FileOutputStream


@SuppressLint("QueryPermissionsNeeded")
fun getListOfInstalledSocialMediaApps(context: Context): MutableList<SocialMediaModel> {
    val installedApps = context.packageManager.getInstalledApplications(0)
    val newList = mutableListOf<SocialMediaModel>()

    installedApps.forEach { app ->
        socialMediaList.find { it.packageName == app.packageName }?.let {
            newList.add(it)
        }
    }
    return newList
}

fun getImageToShare(context: Context, bitmap: Bitmap): Uri? {
    val imageFolder: File = File(context.cacheDir, "images")
    var uri: Uri? = null
    try {
        imageFolder.mkdirs()
        val file = File(imageFolder, "shared_image.png")
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream)
        outputStream.flush()
        outputStream.close()
        uri = FileProvider.getUriForFile(context, "com.sandeepgupta.gratitude.fileprovider", file)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return uri
}

fun getUriFromImageUrl(
    context: Context,
    imageUrl: String,
    onUriGenerated: (uri: Uri) -> Unit
) {
    val imageLoader = ImageLoader.Builder(context).build()
    val request = ImageRequest.Builder(context)
        .data(imageUrl)
        .allowHardware(true)
        .target { result ->
            val bitmap = result.toBitmap()
            getImageToShare(context, bitmap)?.let { onUriGenerated(it) }
        }
        .build()
    imageLoader.enqueue(request)
}

fun intent(
    imageUri: Uri?,
    cardItem: CardModel
): Intent {
    val shareIntent = Intent(Intent.ACTION_SEND)
    shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri)
    shareIntent.type = "image/*"
    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    shareIntent.putExtra(
        Intent.EXTRA_TEXT,
        "${cardItem.sharePrefix} ${cardItem.articleUrl}"
    )
    return shareIntent
}