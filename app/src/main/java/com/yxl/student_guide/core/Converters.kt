package com.yxl.student_guide.core

import com.yxl.student_guide.core.db.score.ScoreDBO
import com.yxl.student_guide.profile.data.Score

fun ScoreDBO.toScore() : Score{
    return Score(
        id = this.id,
        name = this.name,
        value = this.value
    )
}

fun Score.toDBO() : ScoreDBO{
    return ScoreDBO(
        id = this.id,
        name = this.name,
        value = this.value
    )
}