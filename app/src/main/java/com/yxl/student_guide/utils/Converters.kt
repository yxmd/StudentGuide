package com.yxl.student_guide.utils

import com.yxl.student_guide.core.data.Institute
import com.yxl.student_guide.core.db.institute.InstituteDBO
import com.yxl.student_guide.core.db.score.ScoreDBO
import com.yxl.student_guide.core.db.subject.SubjectDBO
import com.yxl.student_guide.profile.data.Score
import com.yxl.student_guide.profile.data.Subject

fun ScoreDBO.toScore(): Score {
    return Score(
        id = this.id,
        name = this.name,
        value = this.value
    )
}

fun Score.toScoreDBO(): ScoreDBO {
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
        city = this.city,
        img = this.img,
        logo = this.logo,
        shortName = this.shortName,
        type = this.type,
    )
}

fun InstituteDBO.toInstitute(): Institute {
    return Institute(
        id = this.id,
        name = this.name,
        description = this.description,
        city = this.city,
        img = this.img,
        logo = this.logo,
        shortName = this.shortName,
        type = this.type,
    )
}

fun SubjectDBO.toSubject(): Subject {
    return Subject(
        id = this.id,
        name = this.name
    )
}