package app.watchMe.domain

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import app.watchMe.model.Watch

fun sendWatchBroadcast(context: Context, watch: Watch){
    val textBody = """
        Часы - ${watch.name}
        Производитель компания - ${watch.company}
        Описание - ${watch.featureDescription}
        Цена - ${watch.price} рублей
    """.trimIndent()

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "image/*"
        putExtra(Intent.EXTRA_TEXT, textBody)
        putExtra(Intent.EXTRA_STREAM, watch.presentationImage)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    context.startActivity(Intent.createChooser(intent, "Отправить через"))
}