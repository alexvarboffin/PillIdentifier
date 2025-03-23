package com.github.pdfviewer;

import static com.github.pdfviewer.HashUtils.getMD5Hash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuProvider;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.viewpager.widget.ViewPager;


import com.github.pdfviewer.databinding.ActivityPdfBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PDFViewActivity extends androidx.appcompat.app.AppCompatActivity implements IShowPage {

    private static final String STATE_CURRENT_PAGE_INDEX = "current_page_index";
    private ParcelFileDescriptor mFileDescriptor;
    private PdfRenderer mPdfRenderer;
    private PdfRenderer.Page mCurrentPage;
    private int mPageIndex;

    private PDFConfig config;
    private String filePath;
    private ActivityPdfBinding binding;
    private SharedPreferences preferences;
    private String KEY_PDF_CURRENT_PAGE_INDEX;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent == null) {
            finish();
            return;
        }
        preferences = getSharedPreferences("PDFPREF", MODE_PRIVATE);

        if (intent.hasExtra(PDFConfig.EXTRA_CONFIG)) {
            config = getIntent().getParcelableExtra(PDFConfig.EXTRA_CONFIG);
        }

        //intent.setDataAndType(Uri.fromFile(file), mimeType);
        binding = ActivityPdfBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);

        if (config == null) {
            binding.pdfviewfpager.setSwipeOrientation(1);
        } else {
            binding.pdfviewfpager.setSwipeOrientation(config.getSwipeorientation());
            filePath = config.getFilepath();
        }

        binding.pdfviewfpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (mCurrentPage != null) {
                    mPageIndex = mCurrentPage.getIndex();
                    saveToPreferences(filePath, mPageIndex);
                    binding.toolbar.setSubtitle("" + mPageIndex + "/" + mPdfRenderer.getPageCount()

                    );
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


        //render the pdf view
        try {
            Uri pdfUri = null;
            if (filePath != null) {
                openRenderer(this, filePath);
            } else {
                if (intent.getData() != null) {
                    pdfUri = intent.getData();
                    openRenderer(this, pdfUri);
                }
            }


            if (filePath != null) {
                KEY_PDF_CURRENT_PAGE_INDEX = HashUtils.getMD5Hash(filePath);
                setUpViewPager(savedInstanceState);
            } else if (pdfUri != null) {
                KEY_PDF_CURRENT_PAGE_INDEX = HashUtils.getMD5Hash(pdfUri.toString());
                setUpViewPager(savedInstanceState);
            }

        } catch (FileNotFoundException e) {
            Toast.makeText(this, "Error! " + e.getMessage(), Toast.LENGTH_SHORT).show();
            finish();
        } catch (IOException e) {
            //DLog.handleException(e);
            Toast.makeText(this, "Error! " + e.getMessage(), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void saveToPreferences(String filePath, int position) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(KEY_PDF_CURRENT_PAGE_INDEX, mPageIndex);
        editor.apply();
    }

    private int loadFromPreferences() {
        return preferences.getInt(KEY_PDF_CURRENT_PAGE_INDEX, 0);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void setUpViewPager(Bundle savedInstanceState) {
        int total = mPdfRenderer.getPageCount();
        PDFAdapter adapter = new PDFAdapter(PDFViewActivity.this, this, total);

        mPageIndex = 0;
        // If there is a savedInstanceState (screen orientations, etc.), we restore the page index.
        if (null != savedInstanceState && savedInstanceState.containsKey(STATE_CURRENT_PAGE_INDEX)) {
            mPageIndex = savedInstanceState.getInt(STATE_CURRENT_PAGE_INDEX, 0);
        } else {
            int tmp = loadFromPreferences();
            if (tmp <= total) {
                mPageIndex = tmp;
            }
        }
        binding.pdfviewfpager.setAdapter(adapter);
        binding.pdfviewfpager.setCurrentItem(mPageIndex);
    }

    @Override
    protected void onDestroy() {
        try {
            closeRenderer();
        } catch (IOException e) {
            //DLog.handleException(e);
        }
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (null != mCurrentPage) {
            outState.putInt(STATE_CURRENT_PAGE_INDEX, mCurrentPage.getIndex());
        }
    }


    private void openRenderer(Context context, String filepath) throws IOException {
        File file = new File(filepath);
        mFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
        if (mFileDescriptor != null) {
            mPdfRenderer = new PdfRenderer(mFileDescriptor);
        }
    }

    private void openRenderer(Context context, Uri fileUri) throws IOException {
        mFileDescriptor = context.getContentResolver().openFileDescriptor(fileUri, "r");
        if (mFileDescriptor != null) {
            mPdfRenderer = new PdfRenderer(mFileDescriptor);
        }
    }


    private void closeRenderer() throws IOException {
        if (null != mCurrentPage)
            mCurrentPage.close();

        if (null != mPdfRenderer)
            mPdfRenderer.close();

        if (null != mFileDescriptor)
            mFileDescriptor.close();
    }

    @Override
    public Bitmap showPage(int index) {
        if (mPdfRenderer.getPageCount() <= index) {
            return null;
        }
        // Make sure to close the current page before opening another one.
        if (null != mCurrentPage) {
            mCurrentPage.close();
        }
        // Use `openPage` to open a specific page in PDF.
        mCurrentPage = mPdfRenderer.openPage(index);
        // Important: the destination bitmap must be ARGB (not RGB).
        Bitmap bitmap = Bitmap.createBitmap(mCurrentPage.getWidth(), mCurrentPage.getHeight(),
                Bitmap.Config.ARGB_8888);
        mCurrentPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
        return bitmap;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void addMenuProvider(@NonNull MenuProvider provider, @NonNull LifecycleOwner owner, @NonNull Lifecycle.State state) {

    }
}
