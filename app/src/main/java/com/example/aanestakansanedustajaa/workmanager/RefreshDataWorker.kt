package com.example.aanestakansanedustajaa.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.aanestakansanedustajaa.MyApp
import com.example.aanestakansanedustajaa.database.ParliamentDatabase
import com.example.aanestakansanedustajaa.repository.ParliamentRepository

import retrofit2.HttpException

class RefreshDataWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "com.example.aanestakansanedustajaa.workmanager.RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val repository = ParliamentRepository
        try {
            repository.refreshParliamentDataEntry()
        } catch (e: HttpException) {
            return Result.retry()
        }
        return Result.success()
    }
}