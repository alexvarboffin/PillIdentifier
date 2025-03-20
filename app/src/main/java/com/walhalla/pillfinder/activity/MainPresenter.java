//package com.walhalla.pillfinder.activity;
//
//import android.text.SpannableStringBuilder;
//import android.view.View;
//
//import androidx.annotation.NonNull;
//
//import com.walhalla.Util;
//import com.walhalla.domen.PillRequest;
//import com.walhalla.pillfinder.MyApp;
//import com.walhalla.pillfinder.Constants;
//import com.walhalla.pillfinder.R;
//import com.walhalla.pillfinder.fragment.main.FragmentMain;
//import com.walhalla.ui.DLog;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import gov.nih.nlm.model.ModelObject;
//import gov.nih.nlm.model.NlmRxImage;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class MainPresenter implements Constants, IPresenter, Callback<ModelObject> {
//
//
//    private final FragmentMain main;
//    private Call<ModelObject> mTask;
//
//    private QEvent _addOrReplace;
//
//    public MainPresenter(FragmentMain mView) {
//        this.main = mView;
//    }
//
//    @Override
//    public void loadNextPageRequest(int pageNumber, QEvent event) {
//        this._addOrReplace = event;
//
//        if (main != null) {
//            main.hideRefreshLayoutProgress();
//        }
//        if (mTask != null && !mTask.isCanceled()) {
//            mTask.cancel();
//            //return;
//        }
//
//        if (pageNumber > 0) {
//            PillRequest.INSTANCE.put(FIELD_PAGE, String.valueOf(pageNumber));
//        } else {
//            PillRequest.INSTANCE.remove(FIELD_PAGE);
//        }
//
//        if (PillRequest.INSTANCE.optionChanged) {
//            mTask = Application.getService1().searchDrugsNav(PillRequest.INSTANCE.getMap());
//            if (main != null) {
//                main.showProgressBar();
//                mTask.enqueue(this);
//            }
//        }
//    }
//
//    @Override
//    public void onDestroy() {
//        if (mTask != null && !mTask.isCanceled()) {
//            mTask.cancel();
//        }
//    }
//
//    @Override
//    public void onResponse(@NonNull Call<ModelObject> call, @NonNull Response<ModelObject> response) {
//        if (null != main) {
//            main.hideProgressBar();
//        }
//        ModelObject modelObject = response.body();
//
//
//        Integer TOTAL_PAGES = -1;
//        Integer CURRENT_PAGE_NUMBER = -1;
//
//        if (modelObject != null && modelObject.replyStatus != null) {
//            TOTAL_PAGES = modelObject.replyStatus.totalPageCount;
//            CURRENT_PAGE_NUMBER = modelObject.replyStatus.pageNumber;
//        }
//
//        if (TOTAL_PAGES != null && TOTAL_PAGES > -1) {
//            if (main != null) {
//                main.mBinding.pageNavigation.setTOTAL_PAGES(TOTAL_PAGES);
//            }
//        }
//
//        if (CURRENT_PAGE_NUMBER != null && CURRENT_PAGE_NUMBER > -1) {
//            if (main != null) {
//                main.mBinding.pageNavigation.CURRENT_PAGE_NUMBER = CURRENT_PAGE_NUMBER;
//                SpannableStringBuilder a = Util.wrapper(modelObject.replyStatus);
//                main.callback.replyStatus(a);
//                /**
//                 * Generate NavigationView
//                 */
//                main.mNavigationViewFix(TOTAL_PAGES, CURRENT_PAGE_NUMBER);
//            }
//        }
//
//        if (modelObject != null) {
//            DLog.d(String.format("CURRENT_PAGE: %1$s/%2$s, %3$s", CURRENT_PAGE_NUMBER, TOTAL_PAGES, modelObject.replyStatus));
//            if (modelObject.nlmRxImages.isEmpty()) {
//                if (main != null) {
//                    main.callback.mSnackbar(R.string.no_data_found);
//                    main.setState(FragmentMain.State.RESET);
//                }
//
//            } else {
//                List<NlmRxImage> tmp = modelObject.nlmRxImages;
//                if (tmp != null && !tmp.isEmpty()) {
//                    //First page number
//                    //mBinding.progressBar.setVisibility(View.GONE);
//                    main.mBinding.alphSectionIndex.setVisibility(View.VISIBLE);
//
//                    /**
//                     * manage progress view
//                     */
//                    if (CURRENT_PAGE_NUMBER != null && CURRENT_PAGE_NUMBER != main.mBinding.pageNavigation.PAGE_START_INDEX) {
//                        lastNavigationPage();
//                    } else {
//                        main.mViewAdapter.clear();
//                    }
//
//                    fff(tmp);
//
//                    main.mBinding.swipeRefreshLayout.setRefreshing(false);
//
//                    // check weather is last page or not
//                    if ((CURRENT_PAGE_NUMBER != null && TOTAL_PAGES != null) && CURRENT_PAGE_NUMBER < TOTAL_PAGES) {
//                        main.mViewAdapter.addLoadingFooter();
//                    } else {
//                        main.isLastPage = true;
//                    }
//                    main.isLoading = false;
//
//                    DLog.d("Obj: " + modelObject.toString());
//
//
//                    //rr.setItemAnimator(new DefaultItemAnimator());
//                    //rr.setItemAnimator(new SlideInUpAnimator());
//
////                    DLog.d( "onResponse: " + modelObject.getReplyStatus().getCURRENT_PAGE_NUMBER()
////                            + "|" + modelObject.getReplyStatus().getTOTAL_PAGES());
//
//
//                    //Disable
//                    //mBinding.pageNavigation.show();
//
//                } else {
//                    if (main != null) {
//                        main.setState(FragmentMain.State.RESET);
//                    }
//                }
//
//            }
//        }
//
//    }
//
//    private void fff(List<NlmRxImage> tmp) {
//        if (_addOrReplace == QEvent.ADD) {
//            List<Object> objectList = new ArrayList<>(tmp);
//            main.mViewAdapter.addAll(objectList);//pagination
//        } else if (_addOrReplace == QEvent.REPLACE) {
//            List<Object> objectList = new ArrayList<>(tmp);
//            main.mViewAdapter.clear();
//            main.mViewAdapter.addAll(objectList);//new
//        }
//    }
//
//    private void lastNavigationPage() {
//        main.mViewAdapter.removeLoading();
//    }
//
//
//    //JsonSyntaxException
//    @Override
//    public void onFailure(@NonNull Call<ModelObject> call, @NonNull Throwable throwable) {
//        main.handleThrowable(throwable);
//    }
//
//}
