package com.google.firebase.firestore

import com.google.android.gms.tasks.Task
import com.walhalla.Util
import com.walhalla.domen.PillRequest
import com.walhalla.lib.Export
import com.walhalla.pillfinder.Constants
import com.walhalla.pillfinder.MpcField
import com.walhalla.pillfinder.R
import com.walhalla.pillfinder.activity.IPresenter
import com.walhalla.pillfinder.activity.IPresenter.QEvent
import com.walhalla.pillfinder.fragment.main.FragmentMain
import com.walhalla.ui.DLog.d
import com.walhalla.ui.DLog.handleException
import gov.nih.nlm.model.ModelObject
import gov.nih.nlm.model.NlmRxImage
import gov.nih.nlm.model.ReplyStatus

class MainPresenterNew(private val main: FragmentMain?) : Constants, IPresenter {

    private val firestore = FirebaseFirestore.getInstance()
    private lateinit var c: Array<String>

    private var _addOrReplace: QEvent? = null
    private val mTask: Task<QuerySnapshot>? = null
    private var query: Query? = null //pagination
    private var lastVisible: DocumentSnapshot? = null //pagination

    //    private void test1() {
    //        FRepository r = new FRepository();
    //        List<String> colorFilter = new ArrayList<>();
    //        colorFilter.add("WHITE, GREEN");
    //        colorFilter.add("GREEN, WHITE");
    //        r.test0(firestore, colorFilter, new FRepository.RxCallback() {
    //            @Override
    //            public void success(List<Export> usersList) {
    //                if (usersList.isEmpty()) {
    //                    DLog.d("@@@@@@@@@@@ EMPTY @@@@@@@@@");
    //                }
    //                for (Export export : usersList) {
    //                    DLog.d(export.mpc.color + " " + export.mpc.score);
    //                }
    //            }
    //
    //            @Override
    //            public void error() {
    //
    //            }
    //        });
    //    }
    override fun loadNextPageRequest(pageNumber: Int, event: QEvent) {
        //        for (Map.Entry<String, String> entry : PillRequest.INSTANCE.getMap().entrySet()) {
//            DLog.d("[*]" + entry.getKey() + "->" + entry.getValue());
//        }

        this._addOrReplace = event
        if (mainNoneNull()) {
            main!!.hideRefreshLayoutProgress()
        }
        onDestroy()
        if (_addOrReplace == QEvent.REPLACE) {
            lastVisible = null
        } else {
            //pagination click
            //DLog.d(pageNumber + "@@pagination click@@" + event);//0 true
        }
        if (PillRequest.INSTANCE.optionChanged) {
            val map: HashMap<String, String> = PillRequest.INSTANCE.map
            if (mainNoneNull()) {
                main!!.showProgressBar()
            }
            searchDrugsNav(firestore, map, pageNumber)
        }
    }

    private fun mainNoneNull(): Boolean {
        return main != null
    }


    override fun onDestroy() {
        if (mTask != null && !mTask.isCanceled) {
            //@@@ mTask.cancel();
        }
    }

    // includeMpc=true,
    // includeIngredients=true, == ingredientsAvailable
    // rPageSize=20  - REMOVED
    // resolution=300,   - REMOVED
    fun searchDrugsNav(db: FirebaseFirestore, map: HashMap<String, String>, pageNumber: Int) {
        query = coolQueryBuilder(db, map, lastVisible)

        query!!.get()
            .addOnSuccessListener { documentSnapshots: QuerySnapshot ->
                if (mainNoneNull()) {
                    main!!.hideProgressBar()
                }
                wrapp1(db, documentSnapshots, map)
            }.addOnFailureListener { e: Exception ->
                d(
                    "Error getting documents.$e"
                )
            }


        //        db.collection("test0")
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                        if (value != null) {
//                            DLog.d("@@@@@@" + value.getDocuments());
//                        }
//                    }
//                }).add(new String(""));
    }


    private fun coolQueryBuilder(db: FirebaseFirestore, map: HashMap<String, String>,
        lastVisible: DocumentSnapshot?
    ): Query {
        val collection = db.collection(COLLECTION_RX)
        var query: Query

        query = if (lastVisible != null) {
            if (_KKK && map.containsKey(MpcField.IMPRINT.value)) {
                //Reuse Query
                collection //.orderBy(FieldPath.of("mpc", MpcField.IMPRINT))
                    .startAfter(lastVisible)
                    .limit(_LIMIT_)
            } else {
                collection.startAfter(lastVisible)
                    .limit(_LIMIT_) //New Query
            }
        } else {
            if (_KKK && map.containsKey(MpcField.IMPRINT.value)) {
                collection.limit(_LIMIT_)
                //.orderBy(FieldPath.of("mpc", MpcField.IMPRINT))
            } else {
                collection.limit(_LIMIT_) //New Query
            }
        }
        if (map.containsKey(MpcField.COLOR.value)) {
            val value = map[MpcField.COLOR.value]
            if (value != null) {
                c = value.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                if (c.size == 1) {
                    query = query.whereEqualTo(
                        FieldPath.of("mpc", "color"),
                        c[0]
                    )
                } else if (c.size > 1) {
                    val buffer: MutableList<Array<String?>> = ArrayList()
                    permutation(0, buffer)
                    val colorFilter: MutableList<String> = ArrayList()
                    for (array in buffer) {
                        val sb = StringBuilder()
                        if (array.size > 0) {
                            for (i in array.indices) {
                                sb.append(array[i])
                                if (i + 1 < array.size) {
                                    sb.append(", ")
                                }
                            }
                        }
                        colorFilter.add(sb.toString())
                    }
                    query = query.whereIn(FieldPath.of("mpc", "color"), colorFilter)
                }
            }
        }

        if (map.containsKey(MpcField.SCORE.value)) {
            val value = map[MpcField.SCORE.value]
            if (value != null) {
                query = query.whereEqualTo(
                    FieldPath.of("mpc", MpcField.SCORE.value),
                    value.toString().toInt()
                )
            }
        }
        if (map.containsKey(MpcField.SIZE.value)) {
            val value = map[MpcField.SIZE.value]
            if (value != null) {
                query = query.whereEqualTo(
                    FieldPath.of("mpc", MpcField.SIZE.value),
                    value.toString().toInt()
                )
            }
        }
        if (map.containsKey(MpcField.SHAPE.value)) {
            val value = map[MpcField.SHAPE.value]
            if (value != null) {
                query = query.whereEqualTo(FieldPath.of("mpc", MpcField.SHAPE.value), value)
            }
        }
        if (map.containsKey(MpcField.IMPRINT.value)) {
            val value = map[MpcField.IMPRINT.value]
            if (value != null) {
//                List<String> strings = new ArrayList<>();
//                strings.add(value + '\uf8ff');//'\uf8ff'
////                strings.add('~' + value);
////                strings.add('~' + value + 'z');
//                query = query.whereIn(FieldPath.of("mpc", MpcField.IMPRINT), strings);


                //NOT
                //query = query.whereGreaterThanOrEqualTo(FieldPath.of("mpc", MpcField.IMPRINT), value + 'z');
                //query = query.whereGreaterThanOrEqualTo(FieldPath.of("mpc", MpcField.IMPRINT), value + '\uf8ff');


                //55 ==> 554 552
                //query = query.whereGreaterThanOrEqualTo(FieldPath.of("mpc", MpcField.IMPRINT), value);

                //query = query.whereGreaterThanOrEqualTo(FieldPath.of("mpc", MpcField.IMPRINT), value);

                //No impint
                //query = query.whereLessThan(FieldPath.of("mpc", MpcField.IMPRINT), value + 'z');
                //query = query.whereLessThan(FieldPath.of("mpc", MpcField.IMPRINT), String.valueOf(value + '\uf8ff'));

                //55 ==> 554 552
//                query = query
//                        .whereGreaterThanOrEqualTo(FieldPath.of("mpc", MpcField.IMPRINT), value)
//                        .whereLessThan(FieldPath.of("mpc", MpcField.IMPRINT), String.valueOf(value + '\uf8ff'));

                //где меньше или равно
                //query = query.whereLessThanOrEqualTo(FieldPath.of("mpc", MpcField.IMPRINT), value+"z");
                //if (lastVisible == null) {
//                query = query
//                        //.orderBy(FieldPath.of("mpc", MpcField.IMPRINT))
//                        .startAt("" + value)
//                        .endAt("" + value + '\uf8ff');
                //}


                query = query.whereArrayContains("imp", value)
            }
        }


        var key0 = MpcField.IMPRINT_COLOR.value
        if (map.containsKey(key0)) {
            val value = map[key0]
            if (value != null) {
                query = query.whereEqualTo(FieldPath.of("mpc", key0), value)
            }
        }
        key0 = MpcField.IMPRINT_TYPE.value
        if (map.containsKey(key0)) {
            val value = map[key0]
            if (value != null) {
                query = query.whereEqualTo(FieldPath.of("mpc", key0), value)
            }
        }
        key0 = MpcField.SYMBOL.value
        if (map.containsKey(key0)) {
            val value = map[key0]
            if (value != null) {
                query = query.whereEqualTo(FieldPath.of("mpc", key0), value.toBoolean())
            }
        }
        return query
    }

    //    Solution 1:
    //
    //    Use the following query
    //Firestore.instance
    //        .collection('your-collection')
    //        .orderBy('your-document')
    //      .startAt([searchkey])
    //      .endAt([searchkey + '\uf8ff']
    //
    //            Solution 2:
    //
    //            Firestore.instance
    //                    .collection('Col-Name')
    // .where('fieldName', isGreaterThanOrEqualTo: searchKey)
    // .where('fieldName', isLessThan: searchKey +’z’)
    private fun wrapp1(
        db: FirebaseFirestore, documentSnapshots: QuerySnapshot, map: HashMap<String, String>
    ) {
        val obj = ModelObject()
        val nlmRxImages: MutableList<NlmRxImage> = ArrayList()
        val aa = ReplyStatus()

        if (!documentSnapshots.isEmpty) {
            // Get the last visible document

            d("oldValue -> $lastVisible")
            val aaq = documentSnapshots.documents
            val newValue = aaq[documentSnapshots.size() - 1]
            d("newValue -> $newValue")
            if (lastVisible != null && lastVisible!!.id == newValue.id) {
                d("newValue EQUAL oldValue" + newValue.id)
            }
            lastVisible = newValue

            for (document in aaq) {
                val export = document.toObject(Export::class.java)
                try {
                    val aaa = Util.wrapper0(export)
                    nlmRxImages.add(aaa)
                } catch (e: Exception) {
                    handleException(e)
                }
            }

            aa.totalPageCount = 2
            aa.pageNumber = 1
        }


        aa.imageCount = nlmRxImages.size
        obj.replyStatus = aa
        obj.nlmRxImages = nlmRxImages

        if (mainNoneNull()) {
            val var0 = Util.wrapper(obj.replyStatus)
            main!!.replyStatus(var0)
        }
        if (obj.nlmRxImages.isEmpty()) {
            if (mainNoneNull()) {
                if (_addOrReplace == QEvent.ADD) {
                    lastNavigationPage()
                } else if (_addOrReplace == QEvent.REPLACE) {
                    main!!.mSnackbar(R.string.no_data_found)
                    main.setState(FragmentMain.State.RESET)
                }
            }
        } else {
            val tmp = obj.nlmRxImages
            if (tmp != null && !tmp.isEmpty()) {
                //First page number
                //mBinding.progressBar.setVisibility(View.GONE);
                /**
                 * manage progress view
                 */

                if (lastVisible != null && _addOrReplace == QEvent.ADD) {
                    lastNavigationPage()
                } else if (_addOrReplace == QEvent.REPLACE) {
                    if (mainNoneNull()) {
                        main!!.mViewAdapter?.clear() //clear all
                    }
                }

                fff(tmp)

                main!!.mBinding?.swipeRefreshLayout!!.isRefreshing = false

                // check weather is last page or not
                val bb = lastVisible != null
                if (bb) {
                    main.mViewAdapter?.addLoadingFooter()
                } else {
                    main.isLastPage = true
                }
                main.isLoading = false

                //rr.setItemAnimator(new DefaultItemAnimator());
                //rr.setItemAnimator(new SlideInUpAnimator());

//                    DLog.d( "onResponse: " + modelObject.getReplyStatus().getCURRENT_PAGE_NUMBER()
//                            + "|" + modelObject.getReplyStatus().getTOTAL_PAGES());
            } else {
                if (mainNoneNull()) {
                    main!!.setState(FragmentMain.State.RESET)
                }
            }
        }
    }

    private fun fff(tmp: List<NlmRxImage>) {
        if (_addOrReplace == QEvent.ADD) {
            val objectList: List<Any> = ArrayList<Any>(tmp)
            main!!.mViewAdapter?.addAll(objectList) //pagination
        } else if (_addOrReplace == QEvent.REPLACE) {
            val objectList: List<Any> = ArrayList<Any>(tmp)
            main!!.mViewAdapter?.clear()
            main.mViewAdapter?.addAll(objectList) //new
        }
    }

    private fun lastNavigationPage() {
        main!!.mViewAdapter?.removeLoading()
    }


    private fun permutation(start: Int, buffer: MutableList<Array<String?>>) {
        if (start != 0) {
            if (start == c.size) {
                val qq = arrayOfNulls<String>(start)
                for (i in 0 until start) {
                    qq[i] = c[i]
                }
                buffer.add(qq)
            }
        }
        for (i in start until c.size) {
            swap(start, i)
            permutation(start + 1, buffer)
            swap(start, i)
        }
    }

    private fun swap(pos1: Int, pos2: Int) {
        val temp = c[pos1]
        c[pos1] = c[pos2]
        c[pos2] = temp
    }


    companion object {
        private const val COLLECTION_RX = "rximages"
        private const val FIELD_MPC = "mpc"
        private const val _LIMIT_: Long = 25
        private const val _KKK = false
    }
}
