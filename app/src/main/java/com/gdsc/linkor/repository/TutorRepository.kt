package com.gdsc.linkor.repository


import android.util.Log
import com.gdsc.linkor.model.TutorDetailResponse
import com.gdsc.linkor.model.TutorListResponse
import com.gdsc.linkor.network.ApiService
import javax.inject.Inject

class TutorRepository @Inject constructor(private val api:ApiService) {
    suspend fun getAllTutor(
        gender: String? ,
        locationSido:String?,
        locationGu:String?,
        tutoringMethod:String?,
        time:String?
    ): TutorListResponse? {

        try{
            val response = api.getAllTutor(
                gender=gender,
                locationSido=locationSido,
                locationGu = locationGu,
                tutoringMethod = tutoringMethod,
                time1 = time
            )
            if (response.isSuccessful){
                return response.body()
            }else {
                Log.e("MYTAGRepository", "Unsuccessful response: ${response.code()}")
            }

            /*val response = api.getAllTutor()
            Log.d("MYTAGRepository", "성공: ${response}")
            return response*/

        }
        catch (e:Exception){
            Log.e("MYTAGRepository","tutor repository error", e)
        }
        return null
    }

    suspend fun getTutorDetail(email:String):TutorDetailResponse?{
        try {
            val response = api.getTutorDetail(email)
            if (response.isSuccessful){
                return response.body()
            }else {
                Log.e("MYTAGRepository", "Unsuccessful response: ${response.code()}")
            }
        } catch (e:Exception){
            Log.e("MYTAGRepository","tutor repository error, detail", e)
        }
        return null
    }
}
