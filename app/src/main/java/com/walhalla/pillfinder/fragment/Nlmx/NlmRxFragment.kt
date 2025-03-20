package com.walhalla.pillfinder.fragment.Nlmx

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.SearchManager
import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.text.Html
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.itextpdf.text.DocumentException
import com.walhalla.FileSystem
import com.walhalla.PDFUtil2
import com.walhalla.Util
import com.walhalla.pillfinder.BuildConfig
import com.walhalla.pillfinder.MyApp
import com.walhalla.pillfinder.PermissionUtil.Companion.on
import com.walhalla.pillfinder.R
import com.walhalla.pillfinder.activity.MainA2
import com.walhalla.pillfinder.adapter.ComplexPresenter
import com.walhalla.pillfinder.adapter.ComplexRecyclerViewAdapter
import com.walhalla.pillfinder.adapter.mpc.MpcObj
import com.walhalla.pillfinder.adapter.obj.RxCuiObjString
import com.walhalla.pillfinder.adapter.obj.SimpleString
import com.walhalla.pillfinder.adapter.obj.VieModel
import com.walhalla.pillfinder.adapter.obj.ingredient.IngredientString
import com.walhalla.pillfinder.databinding.FragmentNlmRxBinding
import com.walhalla.pillfinder.fragment.main.BaseFragment
import com.walhalla.pillfinder.fragment.main.FragmentMain.Companion.newInstance
import com.walhalla.ui.DLog.d
import com.walhalla.ui.DLog.handleException
import com.walhalla.ui.plugins.Launcher.openBrowser
import com.walhalla.ui.plugins.Launcher.rateUs
import com.walhalla.ui.plugins.Module_U.shareText
import gov.fda.api.Main
import gov.nih.nlm.model.NlmRxImage
import okhttp3.PicassoHelper.Companion.getInstance
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.io.UnsupportedEncodingException
import java.net.URLEncoder

class NlmRxFragment : BaseFragment(), ComplexPresenter {
    private var mAdapter: ComplexRecyclerViewAdapter? = null

    private var binding: FragmentNlmRxBinding? = null

    private var imageUrl: String? = null
    private var pname: String? = null


    private var nlmRxImage: NlmRxImage? = null
    private var bitmap: Bitmap? = null

    override fun onItemClicked(v: View, position: Int) {
    }

    override fun onItemClicked(v: View, obj: VieModel) {
        if (activity != null) {
            if (obj is MpcObj) {
                val aa = obj
                val map = HashMap<String, String>()
                map[aa.field.value] = aa.value
                val fragment = newInstance(map)
                callback!!.replaceFragment(fragment)
            } else if (obj is RxCuiObjString) {
                val rxcui = obj.rxcui.toString()

                //            Fragment aa8 = RxNorm.newInstance(rxcui);
//            callback.replaceFragment(aa8);
                val intent = Intent(activity, MainA2::class.java)
                intent.putExtra(MainA2.KEY_RXNORMID, rxcui)
                requireActivity().startActivity(intent)
            } else if (obj is IngredientString) {
                val ingredien = obj.title.toString()

                //            Fragment aa8 = RxNorm.newInstance(rxcui);
//            callback.replaceFragment(aa8);
                val intent = Intent(activity, MainA2::class.java)
                intent.putExtra(MainA2.KEY_INGREDIENT, ingredien)
                requireActivity().startActivity(intent)
            } else if (obj is SimpleString) {
                shareText(
                    requireActivity(), ""
                            + obj.title, getString(R.string.app_name)
                )
            } else {
                Toast.makeText(context, "@@@", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onItemClicked(itemId: Int, obj: VieModel) {
        //none
    }

    private var wrapper: FileWrapper? = null

    protected var callback: NlmxFragmentCallback? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is NlmxFragmentCallback) {
            callback = context
        } else {
            throw RuntimeException("$context must implement Callback")
        }
    }

    override fun onDetach() {
        super.onDetach()
        callback = null
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNlmRxBinding.inflate(inflater, container, false)
        if (mAdapter == null) {
            mAdapter =
                ComplexRecyclerViewAdapter(requireContext() /*, presenter*/) //new AlbumsAdapter(getContext());//
            mAdapter!!.setChildItemClickListener(this)
        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(
            context, 1
        )
        binding!!.recyclerView.layoutManager = layoutManager
        //mBinding.recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, Helpers.dpToPx(getContext(), 2), true));
        binding!!.recyclerView.itemAnimator = DefaultItemAnimator()
        //        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        binding!!.recyclerView.adapter = mAdapter
        binding!!.actionGoogleSearch.setOnClickListener { v: View? ->
            makeGoogleQuery()
        }

        binding!!.actionView.setOnClickListener { v: View? ->
            callback!!.viewProxy(0, nlmRxImage)
        }

        binding!!.actionShare.setOnClickListener { v: View? ->
            callback!!.shareProxy(0, nlmRxImage)
        }

        binding!!.actionQr.setOnClickListener { v: View? ->
            callback!!.makeQRCode(0, nlmRxImage)
        }

        binding!!.actionPdf.setOnClickListener { v: View? ->
            if (on().requestPermission(
                    requireActivity(),
                    REQUEST_CODE_TO_PRINT,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
                saveDocument(requireActivity())
            }
        }

        var supportActionBar: ActionBar? = null
        if (activity != null) {
            supportActionBar = (activity as AppCompatActivity).supportActionBar

            if (supportActionBar != null) {
                supportActionBar.setHomeButtonEnabled(true)
                supportActionBar.setDisplayHomeAsUpEnabled(true)

                val view1 = requireActivity().findViewById<View>(R.id.top_menu)
                if (view1 != null) {
                    view1.visibility = View.GONE
                }
            }
        }


        // String android_id = Secure.getString(this.getContentResolver(),
        // Secure.ANDROID_ID);

        // Log.d("Android","Android ID : "+android_id); //Android ID :
        // 517df663bf6335b2


//        AdvertAdmobRepository mAdmobRepository =
//                AdvertAdmobRepository.getInstance(M.config(this));
//        mAdmobRepository.initialize(this);
//        //mAdmobRepository.attach(findViewById(R.id.bottom_banner), Gravity.TOP);
//
//        AdvertInteractorImpl interactor = new AdvertInteractorImpl(
//                ThreadExecutor.getInstance(),
//                //BackgroundExecutor.getInstance(),
//                MainThreadImpl.getInstance(),
//                mAdmobRepository
//        );
//
//        // Run interactor
//        interactor.selectView(mBinding.bottomBanner, this);
//        getLifecycle().addObserver(mAdmobRepository);


//        AdView banner = AdMobCase.createBanner(this,
//                "ca-app-pub-5111357348858303/8596124522");//bottom-banner-2
//        AdMobCase.interstitialBannerRequest(banner);
//        if (mBinding.nlmBottomBanner != null) {
//            try {
//                //viewGroup.removeView(banner);
//                if (banner.getParent() != null) {
//                    ((ViewGroup) banner.getParent()).removeView(banner);
//                }
//                mBinding.nlmBottomBanner.addView(banner);
//            } catch (Exception e) {
//                M.d(e.getLocalizedMessage());
//            }
//        }


//        AdvertAdmobRepository mAdmobRepository = ((Application) getApplication()).bb();
//
//        //mAdmobRepository.attach(findViewById(R.id.bottom_banner), Gravity.TOP);
//
//        AdvertInteractorImpl interactor = new AdvertInteractorImpl(
//                //ThreadExecutor.getInstance(),
//                BackgroundExecutor.getInstance(),
//                MainThreadImpl.getInstance(),
//                mAdmobRepository
//        );
//
//        // Run interactor
//        interactor.selectView(mBinding.nlmBottomBanner, this);
//        getLifecycle().addObserver(mAdmobRepository);

        /*
         * String orientation = getRotation(this);
         * if(orientation.equals("landscape") && orientation.equals(
         * "reverse landscape")){
         *
         * }
         */
        //MobileAds.initialize(this, getString(R.string.application_id));
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads
        // on this device."

        /*
         * android:autoLink="web" android:linksClickable="true"
         *
         * adb shell am start -a android.intent.action.VIEW -d "http://example.com/hello" com.walhalla.pillidentifier
         */

//        Intent intent = getIntent();
//        String action = intent.getAction();
//        Uri data = intent.getData();
//
//        if (data != null) {
//            data.toString();
//        }
        val helper = getInstance(requireContext())

        if (arguments != null) {
            val json = requireArguments().getString(ARG_IMAGE)
            nlmRxImage = Gson().fromJson(json, NlmRxImage::class.java)
            d("@@$json")

            nlmRxImage?.let {
                imageUrl = it.imageUrl
                pname = it.name

                val fullName = SpannableStringBuilder()
                if (pname != null && !pname!!.trim { it <= ' ' }.isEmpty()) {
                    fullName.append(pname!!.trim { it <= ' ' }).append("\n")
                }
                if (it.getLabeler() != null && !it.getLabeler().trim { it <= ' ' }
                        .isEmpty()) {
                    fullName.append("(")
                    fullName.append(it.getLabeler().trim { it <= ' ' }).append(")").append("\n")
                }

                binding!!.name.text = fullName
                // bar.setDisplayShowTitleEnabled(true); API 4
                if (supportActionBar != null) {
                    callback!!.setMainTitle(it.getLabeler(), SpannableStringBuilder(pname))
                }


                binding!!.actionCopy.setOnClickListener { v: View? ->
                    //Module_U.shareText(getActivity(), fullName.toString(), getActivity().getString(R.string.app_name))
                    val clipboard =
                        requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val tmp0 = fullName.toString()
                    if (clipboard != null) {
                        val clip = ClipData.newPlainText("packageName", tmp0)
                        clipboard.setPrimaryClip(clip)
                    }
                    val tmp =
                        String.format(requireActivity().getString(R.string.data_to_clipboard), tmp0)
                    Toast.makeText(activity, tmp, Toast.LENGTH_SHORT).show()
                }
            }



            binding!!.name.movementMethod = LinkMovementMethod.getInstance() // url

            // text
            // clickable
            // ;)

//            Picasso.with(NlmRxFragment.this).load(imageUrl) // mThumbIds[position]
//                    .placeholder(R.raw.no_photo)
//                    .noFade()
//                    .resize(iSize, iSize).centerCrop()
//                    .error(R.raw.no_photo)
//                    .into(imageView);
            helper!!.loadFullImages(context, imageUrl, binding!!.imageView1)
            aaaaaaaasw33()
            // mBinding.description.setText(value);
        } else {
            helper!!.loadFullImagesErr(context, binding!!.imageView1)
        }

        binding!!.imageView1.setOnClickListener { v: View? ->
            openBrowser(
                requireActivity(), imageUrl
            )
        }
        if (google_market_build()) {
            binding!!.rootView.setOnClickListener((View.OnClickListener { v: View? ->
                rateUs(
                    requireActivity()
                )
            }))
        } else {
            binding!!.rootView.visibility = View.GONE
        }
    }

    private fun aaaaaaaasw33() {
        val activity: Activity? = activity
        if (activity != null && isAdded) {
            val data = Util.wrapper(getActivity(), nlmRxImage)
            //        SpannableString sp = new SpannableString(Html.fromHtml(value.toString()));
//        mBinding.description.setText(sp, TextView.BufferType.SPANNABLE);
            updateData(data)
        }
    }

    private fun google_market_build(): Boolean {
        return BuildConfig.FLAVOR.contains("low21")
    }

    private var currentPDF: FileWrapper?
        get() = wrapper
        private set(fileName) {
            this.wrapper = fileName
        }

    private fun saveDocument(view: View) {
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.RGB_565)
        }
        val c = Canvas(bitmap!!)
        view.draw(c)


        val fileWrapper = FileWrapper()
        fileWrapper.fileName = System.currentTimeMillis().toString() + ".pdf"

        if (bitmap != null) {
            try {
                val fos: OutputStream?


                //                                Uri fileUri = FileProvider.getUriForFile(this, getString(R.string.file_provider_authority), file);
//                                final ParcelFileDescriptor pfd = resolver.openFileDescriptor(fileUri, "w", null);
//                                fos = new FileOutputStream(pfd.getFileDescriptor());
                val fileUri = FileSystem.streamLoader(requireActivity(), fileWrapper.fileName)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)  //Android 10 level 29 (Android 10).
                {
                    val resolver = requireActivity().contentResolver
                    fos = resolver.openOutputStream(fileUri)
                } else {
                    val file = File(
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                        fileWrapper.fileName
                    )

                    fos = FileOutputStream(file)
                    d("=========\t" + Build.VERSION.SDK_INT + " -> " + fileUri + " " + file)
                }

                val stream = ByteArrayOutputStream()
                bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                PDFUtil2.makePDF(activity, fos, stream, nlmRxImage)
                fileWrapper.uri = fileUri

                currentPDF = fileWrapper
                FileSystem.openDocument(activity, fileWrapper)
            } catch (ie: IOException) {
                Toast.makeText(activity, "" + ie.localizedMessage, Toast.LENGTH_SHORT).show()
            } catch (ie: DocumentException) {
                Toast.makeText(activity, "" + ie.localizedMessage, Toast.LENGTH_SHORT).show()
            } catch (ae: ActivityNotFoundException) {
                handleException(ae)
            }
        }
    }

    fun updateData(data: List<VieModel>) {
        val obj: List<VieModel> = ArrayList(data)
        mAdapter!!.onRestoreInstanceState(obj)
    }

    fun saveDocument(context: Activity) {
        if (currentPDF == null) {
            saveDocument(binding!!.imageView1)
        } else {
            FileSystem.openDocument(context, currentPDF)
        }
    }


    //    public static int getWidth(Context mContext) {
    //        int width = 0;
    //        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
    //        Display display = wm.getDefaultDisplay();
    //
    //        if (Build.VERSION.SDK_INT > 12) {
    //            Point size = new Point();
    //            display.getSize(size);
    //            width = size.x;
    //        } else {
    //            width = display.getWidth(); // Deprecated
    //        }
    //        return width;
    //    }
    //    public static int getHeight(Context mContext) {
    //        int height = 0;
    //        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
    //        Display display = wm.getDefaultDisplay();
    //        if (Build.VERSION.SDK_INT > 12) {
    //            Point size = new Point();
    //            display.getSize(size);
    //            height = size.y;
    //        } else {
    //            height = display.getHeight(); // Deprecated
    //        }
    //        return height;
    //    }
    // Orientation
    //    public String getRotation(Context context) {
    //        final int rotation = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
    //                .getOrientation();
    //        switch (rotation) {
    //            case Surface.ROTATION_0:
    //                return "portrait";
    //            case Surface.ROTATION_90:
    //                return "landscape";
    //            case Surface.ROTATION_180:
    //                return "reverse portrait";
    //            default:
    //                return "reverse landscape";
    //        }
    //    }
    // MENU
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.more_info, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    //    @Override
    //    public boolean onCreateOptionsMenu(Menu menu) {
    //        getMenuInflater().inflate(R.menu.more_info, menu);
    //        return true;
    //    }
    @SuppressLint("NonConstantResourceId")
    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        val itemId = menuItem.itemId // item.getTitle());

        // Status bar home button
        if (itemId == R.id.action_img) {
            openBrowser(requireActivity(), imageUrl)
        } else if (itemId == R.id.action_info) {
            makeGoogleQuery()
        }
        //        else if (itemId == android.R.id.home || itemId == R.id.action_back) {
//            Objects.requireNonNull(getActivity()).onBackPressed();
//        }
        return super.onOptionsItemSelected(menuItem)
    }


    override fun onResume() {
        super.onResume()

        //ddddd();
        //DLog.d("@@@@" + pname);
    }

    private fun ddddd() {
        var tmp = pname
        if (tmp!!.isEmpty()) {
            tmp = nlmRxImage!!.getLabeler()
        }
        //Api server bug fix
        tmp = tmp!!.replace("[", "")
            .replace("]", "")
            .replace("\\", "")
            .replace("/", "")

        val service2 = MyApp.service2
        val call = service2.searchDrag(tmp)
        call.enqueue(object : Callback<Main> {
            override fun onResponse(call: Call<Main>, response: Response<Main>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        makeText(body)
                    }
                    return
                }
                val body = response.errorBody()
                if (body != null) {
                    try {
                        val jObjError = JSONObject(body.string())
                        d(jObjError.toString() + pname)
                        //Toast.makeText(getContext(), jObjError.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
                    } catch (e: Exception) {
                        //Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            override fun onFailure(call: Call<Main>, tr: Throwable) {
                callback!!.handleThrowable(tr)
            }
        })
    }

    private fun makeText(data: Main) {
        val value = Util.wrapper(data)
        val sp = SpannableString(Html.fromHtml(value.toString()))
        binding!!.api2.text = sp
    }

    private fun makeGoogleQuery() {
        // "https://www.google.com.ua/search?q=\"" + pname + "\"" + " \"" +
        // plabeler + "\""
        var tmp = pname
        if (tmp!!.length == 0) {
            tmp = nlmRxImage!!.getLabeler()
        }
        if (activity != null) {
            val intent = Intent(Intent.ACTION_WEB_SEARCH)
            intent.putExtra(SearchManager.QUERY, tmp)
            if (intent.resolveActivity(requireActivity().packageManager) != null) {
                d("QUERY_HANDLER_OK")
                startActivity(intent)
            } else {
                try {
                    tmp = URLEncoder.encode(tmp, "UTF-8")
                } catch (e: UnsupportedEncodingException) {
                    // not going to happen - value came from JDK's own StandardCharsets
                }
                val format = String.format(getString(R.string.google_search_url), tmp)
                openBrowser(requireActivity(), format)
            }
        }
    }

    fun copyIngredientToBuffer(value: IngredientString?) {
        if (activity != null) {
            //            ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
//            if (clipboard != null) {
//                ClipData clip = ClipData.newPlainText("packageName", "" + value.title);
//                clipboard.setPrimaryClip(clip);
//            }
//            String tmp = String.format(getActivity().getString(R.string.data_to_clipboard), value.title);
//            Toast.makeText(getActivity(), tmp, Toast.LENGTH_SHORT).show();
        }
    } //    @Override
    //    public void onMessageRetrieved(int id, View message) {
    //        ViewGroup v = findViewById(R.id.nlm_bottom_banner);
    //        if (v != null && message != null) {
    //            try {
    //                //M.d(message.toString());
    //                //viewGroup.removeView(message);
    //                if (message.getParent() != null) {
    //                    ((ViewGroup) message.getParent()).removeView(message);
    //                }
    //                v.addView(message);
    //            } catch (Exception e) {
    //                //M.d(e.getLocalizedMessage());
    //            }
    //        }
    //    }


    companion object {
        private val ARG_IMAGE = "key_image_" + R.font.opensansregular
        const val REQUEST_CODE_TO_PRINT: Int = 121
        @JvmStatic
        fun getInstance(image: String?): NlmRxFragment {
            val nlmRxFragment = NlmRxFragment()
            val bundle = Bundle()
            bundle.putString(ARG_IMAGE, image)
            nlmRxFragment.arguments = bundle
            return nlmRxFragment
        }
    }
}
