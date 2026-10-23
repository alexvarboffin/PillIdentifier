package com.walhalla.pillfinder.adapter.obj

class ClassCardItem(
    val classId: String,
    val className: String,
    val classType: String,
    val members: List<ClassMemberItem>,
    // native RxClass relation of this class for the drug (from byRxcui), used by AnalogsActivity -> classMembers
    val relaSource: String = "",
    val rela: String = ""
) : VieModel

class ClassMemberItem(
    val minConceptName: String,
    val minConceptTty: String,
    val rela: String,
    val relaSource: String
) : VieModel 