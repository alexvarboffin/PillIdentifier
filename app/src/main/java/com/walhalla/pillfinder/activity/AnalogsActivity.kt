package com.walhalla.pillfinder.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
//import com.walhalla.lib.datamodel.common.response.Response8
//import com.walhalla.pillfinder.MyApp
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import com.walhalla.lib.service.RxNormLib
import gov.nih.nlm.rxnorm.models.RelaSource
import com.walhalla.pillfinder.adapter.ComplexRecyclerViewAdapter
import com.walhalla.pillfinder.adapter.obj.HeaderObject
import com.walhalla.pillfinder.adapter.obj.NameValue1_2
import com.walhalla.pillfinder.adapter.obj.VieModel
import com.walhalla.pillfinder.databinding.CategoryListFragmentBinding
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response

class AnalogsActivity : AppCompatActivity() {
    private lateinit var binding: CategoryListFragmentBinding
    private lateinit var mAdapter: ComplexRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CategoryListFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAdapter = ComplexRecyclerViewAdapter(this)
        binding.recyclerView.layoutManager = GridLayoutManager(this, 1)
        binding.recyclerView.itemAnimator = DefaultItemAnimator()
        binding.recyclerView.adapter = mAdapter

        val classId = intent.getStringExtra(EXTRA_CLASS_ID) ?: ""
        val classType = intent.getStringExtra(EXTRA_CLASS_TYPE) ?: ""
        val className = intent.getStringExtra(EXTRA_CLASS_NAME) ?: ""
        val relaSource = intent.getStringExtra(EXTRA_RELA_SOURCE) ?: ""
        val rela = intent.getStringExtra(EXTRA_RELA) ?: ""
        val rxcui = intent.getStringExtra(EXTRA_RXCUI) ?: ""
        title = "Аналоги: $className"

        loadAnalogs(classId, classType, className, relaSource, rela, rxcui)
        binding.swiperefresh.setOnRefreshListener {
            loadAnalogs(classId, classType, className, relaSource, rela, rxcui)
            binding.swiperefresh.isRefreshing = false
        }
    }

    // RxClass via generated DefaultApi (RxNormLib). Fixes vs the old Retrofit code:
    //  - sourcesOfDrugClassRelations.json(classId, classType) is not a documented RxClass call;
    //    the class's own relaSource/rela (from byRxcui in FragmentClass) is used instead and
    //    validated against relaSources.json (getRelaSources)
    //  - classMembers.json requires rela for non-ATC sources (VA has_VAclass, MEDRT may_treat ...)
    //  - classMembers.json returns drugMemberGroup.drugMember, not rxclassDrugInfoList
    //  - fallback byRxcui.json is called with the drug rxcui, not with the classId
    private fun loadAnalogs(
        classId: String, classType: String, className: String,
        relaSourceIn: String, relaIn: String, rxcui: String
    ) {
        lifecycleScope.launch {
            // 1. Доступные источники (relaSources.json)
            val sources = RxNormLib.fetch { getRelaSources() }.getOrNull()
                ?.relaSourceList?.relaSourceName ?: emptyList()
            val relaSourceName = when {
                relaSourceIn.isNotBlank() && (sources.isEmpty() || relaSourceIn in sources) -> relaSourceIn
                else -> PRIORITY_SOURCES.firstOrNull { it in sources } // old behaviour when the class source is unknown
            }
            val relaSource = RelaSource.entries.firstOrNull { it.value.equals(relaSourceName, ignoreCase = true) }
            if (relaSource == null) {
                loadClassInfoFallback(rxcui, className)
                return@launch
            }
            // ATC/ATCPROD rows carry rela = "" -> no rela parameter
            val rela = relaIn.takeIf { it.isNotBlank() && relaSourceName == relaSourceIn }
            // 2. Аналоги через classMembers.json
            RxNormLib.fetch { getClassMembers(classId, relaSource, rela) }.onSuccess { body ->
                val members = body?.drugMemberGroup?.drugMember ?: emptyList()
                if (members.isNotEmpty()) {
                    val data = ArrayList<VieModel>()
                    data.add(HeaderObject("Аналоги класса: $className (источник: ${relaSource.value})"))
                    data.addAll(members.map {
                        NameValue1_2(it.minConcept.name ?: "", it.minConcept.tty ?: "")
                    })
                    mAdapter.onRestoreInstanceState(data)
                } else {
                    // Fallback: показать классы препарата через byRxcui.json
                    loadClassInfoFallback(rxcui, className)
                }
            }.onFailure {
                loadClassInfoFallback(rxcui, className)
            }
        }
    }

    // Fallback: показать классы препарата через byRxcui.json (rxcui препарата)
    private fun loadClassInfoFallback(rxcui: String, className: String) {
        if (rxcui.isBlank()) {
            mAdapter.onRestoreInstanceState(listOf(HeaderObject("Нет информации о классах для: $className")))
            return
        }
        lifecycleScope.launch {
            RxNormLib.fetch { getClassByRxcui(rxcui) }.onSuccess { body ->
                val classes = (body?.rxclassDrugInfoList?.rxclassDrugInfo ?: emptyList())
                    .map { it.rxclassMinConceptItem }
                    .distinctBy { it.classId }
                val data = ArrayList<VieModel>()
                data.add(HeaderObject("Классы для: $className"))
                if (classes.isNotEmpty()) {
                    data.addAll(classes.map { NameValue1_2(it.className, it.classType) })
                } else {
                    data.add(HeaderObject("Нет информации о классах для: $className"))
                }
                mAdapter.onRestoreInstanceState(data)
            }.onFailure {
                mAdapter.onRestoreInstanceState(listOf(HeaderObject("Нет информации о классах для: $className")))
            }
        }
    }

//    old Retrofit loadAnalogs()/loadClassInfoFallback():
//    private fun loadAnalogs(classId: String, classType: String, className: String) {
//        // 1. Получаем доступные источники
//        MyApp.rxClass.getSourcesOfDrugClassRelations(classId, classType)
//            .enqueue(object : Callback<com.walhalla.lib.datamodel.rxclass.response.RxClassSourcesResponse?> {
//                override fun onResponse(
//                    call: Call<com.walhalla.lib.datamodel.rxclass.response.RxClassSourcesResponse?>,
//                    response: Response<com.walhalla.lib.datamodel.rxclass.response.RxClassSourcesResponse?>
//                ) {
//                    val sources = response.body()?.sourcesOfDrugClassRelationsList?.source
//                    if (sources.isNullOrEmpty()) {
//                        // Fallback: показать классы препарата через byRxcui.json
//                        loadClassInfoFallback(classId, className)
//                        return
//                    }
//                    // Выбираем первый источник из приоритетного списка, который есть в ответе
//                    val relaSource = PRIORITY_SOURCES.firstOrNull { it in sources } ?: sources.first()
//                    // 2. Запрашиваем аналоги через classMembers.json
//                    MyApp.rxClass.getClassMembersWithSource(classId, classType, relaSource)
//                        .enqueue(object : Callback<Response8?> {
//                            override fun onResponse(call: Call<Response8?>, response: Response<Response8?>) {
//                                val members = response.body()?.rxclassDrugInfoList?.rxclassDrugInfo ?: emptyList()
//                                val data = ArrayList<VieModel>()
//                                data.add(HeaderObject("Аналоги класса: $className (источник: $relaSource)"))
//                                if (members.isNotEmpty()) {
//                                    data.addAll(members.map {
//                                        NameValue1_2(it.minConcept?.name ?: "", it.minConcept?.tty ?: "")
//                                    })
//                                    mAdapter.onRestoreInstanceState(data)
//                                } else {
//                                    // Fallback: показать классы препарата через byRxcui.json
//                                    loadClassInfoFallback(classId, className)
//                                }
//                            }
//                            override fun onFailure(call: Call<Response8?>, t: Throwable) {
//                                // Fallback: показать классы препарата через byRxcui.json
//                                loadClassInfoFallback(classId, className)
//                            }
//                        })
//                }
//                override fun onFailure(call: Call<com.walhalla.lib.datamodel.rxclass.response.RxClassSourcesResponse?>, t: Throwable) {
//                    val msg = t.message ?: ""
//                    if (msg.contains("404")) {
//                        // Fallback: показать классы препарата через byRxcui.json
//                        loadClassInfoFallback(classId, className)
//                    } else {
//                        mAdapter.onRestoreInstanceState(listOf(HeaderObject("Ошибка загрузки источников: "+msg)))
//                    }
//                }
//            })
//    }
//
//    // Fallback: показать классы препарата через byRxcui.json
//    private fun loadClassInfoFallback(classId: String, className: String) {
//        MyApp.rxClass.byRxcui(classId).enqueue(object : Callback<com.walhalla.lib.datamodel.rxclass.response.RxClassByRxcuiResponse?> {
//            override fun onResponse(
//                call: Call<com.walhalla.lib.datamodel.rxclass.response.RxClassByRxcuiResponse?>,
//                response: Response<com.walhalla.lib.datamodel.rxclass.response.RxClassByRxcuiResponse?>
//            ) {
//                val classes = response.body()?.rxclassMinConceptList?.rxclassMinConcept ?: emptyList()
//                val data = ArrayList<VieModel>()
//                data.add(HeaderObject("Классы для: $className"))
//                if (classes.isNotEmpty()) {
//                    data.addAll(classes.map {
//                        NameValue1_2(it.className ?: "", it.classType ?: "")
//                    })
//                } else {
//                    data.add(HeaderObject("Нет информации о классах для: $className"))
//                }
//                mAdapter.onRestoreInstanceState(data)
//            }
//            override fun onFailure(
//                call: Call<com.walhalla.lib.datamodel.rxclass.response.RxClassByRxcuiResponse?>,
//                t: Throwable
//            ) {
//                mAdapter.onRestoreInstanceState(listOf(HeaderObject("Нет информации о классах для: $className")))
//            }
//        })
//    }

    companion object {
        private const val EXTRA_CLASS_ID = "extra_class_id"
        private const val EXTRA_CLASS_TYPE = "extra_class_type"
        private const val EXTRA_CLASS_NAME = "extra_class_name"
        private const val EXTRA_RELA_SOURCE = "extra_rela_source"
        private const val EXTRA_RELA = "extra_rela"
        private const val EXTRA_RXCUI = "extra_rxcui"
        // Приоритетные источники для поиска аналогов (relaSourceName)
        private val PRIORITY_SOURCES = listOf(
            "ATC", "ATCPROD", "CDC", "DAILYMED", "FDASPL", "FMTSME", "MEDRT", "RXNORM", "SNOMEDCT", "VA"
        )
        fun start(
            context: Context, classId: String, classType: String, className: String,
            relaSource: String = "", rela: String = "", rxcui: String = ""
        ) {
            val intent = Intent(context, AnalogsActivity::class.java)
            intent.putExtra(EXTRA_CLASS_ID, classId)
            intent.putExtra(EXTRA_CLASS_TYPE, classType)
            intent.putExtra(EXTRA_CLASS_NAME, className)
            intent.putExtra(EXTRA_RELA_SOURCE, relaSource)
            intent.putExtra(EXTRA_RELA, rela)
            intent.putExtra(EXTRA_RXCUI, rxcui)
            context.startActivity(intent)
        }
    }
} 