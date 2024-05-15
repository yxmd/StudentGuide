package com.yxl.student_guide.utils

import com.yxl.student_guide.core.data.Institute
import com.yxl.student_guide.core.db.institute.InstituteDBO
import com.yxl.student_guide.core.db.score.ScoreDBO
import com.yxl.student_guide.profile.data.Score

fun ScoreDBO.toScore(): Score {
    return Score(
        id = this.id,
        name = this.name,
        value = this.value
    )
}

fun Score.toDBO(): ScoreDBO {
    return ScoreDBO(
        id = this.id,
        name = this.name,
        value = this.value
    )
}

fun Institute.toDBO(): InstituteDBO {
    return InstituteDBO(
        id = this.id,
        name = this.name,
        description = this.description,
        img = this.img,
        logo = this.logo
    )
}

fun InstituteDBO.toInstitute(): Institute {
    return Institute(
        id = this.id,
        name = this.name,
        description = this.description,
        img = this.img,
        logo = this.logo,
    )
}