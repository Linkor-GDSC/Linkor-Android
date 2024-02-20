package com.gdsc.linkor.repository


import android.util.Log
import com.gdsc.linkor.model.TutorDetailResponse
import com.gdsc.linkor.model.TutorListResponse
import com.gdsc.linkor.network.ApiService
import javax.inject.Inject

class TutorRepository @Inject constructor(private val api:ApiService) {
    suspend fun getAllTutor(
        gender: String?,
        locationSido:String?,
        locationGu:String?,
        tutoringMethod:String?,
        time1:String?=null,
        time2:String?=null,
        time3:String?=null,
        time4:String?=null,
        time5:String?=null,
        time6:String?=null,
        time7:String?=null
    ): TutorListResponse? {

        try{
            val response = api.getAllTutor(
                gender=gender,
                locationSido=locationSido,
                locationGu = locationGu,
                tutoringMethod = tutoringMethod,
                time1 = time1,
                time2=time2,
                time3=time3,
                time4=time4,
                time5=time5,
                time6=time6,
                time7=time7
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
