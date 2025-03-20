package com.walhalla.pillfinder.adapter.mpc

import com.walhalla.pillfinder.MpcField
import com.walhalla.pillfinder.adapter.obj.VieModel
import java.io.Serializable

class MpcObj(@JvmField val field: MpcField, @JvmField val value: String) : VieModel, Serializable
