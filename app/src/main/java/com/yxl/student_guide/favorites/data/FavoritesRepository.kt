package com.yxl.student_guide.favorites.data

import com.yxl.student_guide.core.db.institute.InstituteDBO
import com.yxl.student_guide.core.db.institute.InstituteDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritesRepository @Inject constructor(private val instituteDao: InstituteDao) {

    val institutes: Flow<List<InstituteDBO>> = instituteDao.getInstitutes()

}
