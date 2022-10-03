package com.cb.week5homeworkfinal.Workers

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import java.io.FileOutputStream

class SepiaFilterWorker(context: Context, workerParameters: WorkerParameters)
        : Worker(context, workerParameters) {

        override fun doWork(): Result {
            val imagePath = inputData.getString("image_path") ?: return Result.failure()
            val bitmap = BitmapFactory.decodeFile(imagePath)
            val Bitmap2 = ImageFilter.applySepiaFilter(bitmap)
            val outputStream = FileOutputStream(imagePath)
            outputStream.use { output ->
                Bitmap2.compress(Bitmap.CompressFormat.PNG, 100, output)
                output.flush()
            }
            val output = workDataOf("image_path" to imagePath )
            return Result.success(output)
        }
    }