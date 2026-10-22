package com.walhalla.pillfinder.adapter.obj

class ClassCardItem(
    val classId: String,
    val className: String,
    val classType: String,
    val members: List<ClassMemberItem>
) : VieModel

class ClassMemberItem(
    val minConceptName: String,
    val minConceptTty: String,
    val rela: String,
    val relaSource: String
) : VieModel 