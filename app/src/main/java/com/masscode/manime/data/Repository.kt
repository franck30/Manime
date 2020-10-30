package com.masscode.manime.data

import com.masscode.manime.data.source.remote.RemoteDataSource
import com.masscode.manime.data.source.remote.response.AnimeListResponse

class Repository private constructor(private val remoteDataSource: RemoteDataSource) : DataSource {

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(remoteData: RemoteDataSource): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(remoteData)
            }
    }

    override suspend fun getTopAnime(type: String): List<AnimeListResponse> {
        lateinit var animeResult: List<AnimeListResponse>
        remoteDataSource.getTopAnime(type, object : RemoteDataSource.GetAnimeCallback {
            override fun onAnimeReceived(animeList: List<AnimeListResponse>) {
                animeResult = animeList
            }
        })
        return animeResult
    }
}