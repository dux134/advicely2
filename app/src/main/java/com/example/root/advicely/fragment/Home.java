package com.example.root.advicely.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.root.advicely.DatabaseHelper_and_Adds.MyFragment;
import com.example.root.advicely.R;
import com.example.root.advicely.fragment.home.EbookMyAdapter;
import com.example.root.advicely.fragment.home.HomeEbookViewAll;
import com.example.root.advicely.fragment.libraryBooks.LibraryBookDataModel;
import com.example.root.advicely.utils.CheckNetworkConnection;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;


public class Home extends MyFragment implements ViewPagerEx.OnPageChangeListener {
    public static ArrayList<LibraryBookDataModel> sscListitems = new ArrayList<>();
    RecyclerView sscRecyclerView;
    private RecyclerView.Adapter sscAdapter;
    public static ArrayList<LibraryBookDataModel> bankListitems = new ArrayList<>();
    RecyclerView bankRecyclerView;
    private RecyclerView.Adapter bankAdapter;
    public static ArrayList<LibraryBookDataModel> armedforceListitems = new ArrayList<>();
    RecyclerView armedforceRecyclerView;
    private RecyclerView.Adapter armedforceAdapter;
    private SliderLayout mDemoSlider;
    private TextView sscViewAll,bankViewAll,armedforceViewAll;
    private TextView mainWebsite,admitCard,webPortal,sarkariResult,result;
    private ImageView mainWebsiteI,admitCardI,webPortalI,sarkariResultI,resultI;

    @NonNull
    public static Home newInstance() {
        return new Home();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        loadAdvertisement();
        mainWebsite = (TextView)rootView.findViewById(R.id.ccsutextView);
        mainWebsiteI = (ImageView)rootView.findViewById(R.id.ccsu);
        admitCard = (TextView)rootView.findViewById(R.id.admitcardtextView);
        admitCardI = (ImageView)rootView.findViewById(R.id.admitcard);
        webPortal = (TextView)rootView.findViewById(R.id.fillformtextView);
        webPortalI = (ImageView)rootView.findViewById(R.id.fillform);
        sarkariResult = (TextView)rootView.findViewById(R.id.sarkariresulttextView);
        sarkariResultI = (ImageView)rootView.findViewById(R.id.sarkariResult);
        result = (TextView)rootView.findViewById(R.id.resulttextView);
        resultI = (ImageView)rootView.findViewById(R.id.result);

        mainWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               OnClickMainWebsite();
            }
        });
        mainWebsiteI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnClickMainWebsite();
            }
        });
        admitCardI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnClickAdmitCard();
            }
        });
        admitCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnClickAdmitCard();
            }
        });
        webPortalI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnClickWebPortal();
            }
        });
        webPortal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnClickWebPortal();
            }
        });
        sarkariResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnClickSarkariResult();
            }
        });
        sarkariResultI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnClickSarkariResult();
            }
        });
        resultI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnClickResult();
            }
        });
        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnClickResult();
            }
        });

        //for ssc recyclerView
        sscRecyclerView = (RecyclerView) rootView.findViewById(R.id.ssc_recyclerView);
        sscRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        sscAdapter = new EbookMyAdapter(sscListitems, new EbookMyAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                showMyAdd();
                sscBookClickAction(view,position);
            }
        });
        sscRecyclerView.setAdapter(sscAdapter);
        sscRecyclerView.setItemAnimator(new DefaultItemAnimator());
        sscViewAll = (TextView)rootView.findViewById(R.id.ssc_textview1);
        sscViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeEbookViewAll.list = sscListitems;
                startActivity(new Intent(getActivity(), HomeEbookViewAll.class));            }
        });

        //for bank recyclerview
        bankRecyclerView = (RecyclerView) rootView.findViewById(R.id.bank_recyclerView);
        bankRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        bankAdapter = new EbookMyAdapter(sscListitems, new EbookMyAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                showMyAdd();
                bankBookClickAction(view,position);
            }
        });
        bankRecyclerView.setAdapter(bankAdapter);
        bankRecyclerView.setItemAnimator(new DefaultItemAnimator());
        bankViewAll = (TextView)rootView.findViewById(R.id.bank_textView1);
        bankViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeEbookViewAll.list = bankListitems;
                startActivity(new Intent(getActivity(), HomeEbookViewAll.class));
            }
        });

        //for armed force recyclerview
        armedforceRecyclerView = (RecyclerView) rootView.findViewById(R.id.armedforce_recyclerView);
        armedforceRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        armedforceAdapter = new EbookMyAdapter(sscListitems, new EbookMyAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                showMyAdd();
                armedForceClickAction(view,position);
            }
        });
        armedforceRecyclerView.setAdapter(armedforceAdapter);
        armedforceRecyclerView.setItemAnimator(new DefaultItemAnimator());
        armedforceViewAll = (TextView)rootView.findViewById(R.id.armedforce_textView1);
        armedforceViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeEbookViewAll.list = armedforceListitems;
                startActivity(new Intent(getActivity(), HomeEbookViewAll.class));            }
        });

        mDemoSlider = (SliderLayout) rootView.findViewById(R.id.slider);
        HashMap<String, String> url_maps = new HashMap<String, String>();
        url_maps.put("Chemistry", "https://www.brainyquote.com/photos_tr/en/n/nelsonmandela/378967/nelsonmandela1-2x.jpg");
        url_maps.put("Defence", "https://www.brainyquote.com/photos_tr/en/s/stjerome/389605/stjerome1-2x.jpg");
        url_maps.put("Physics1", "http://www.powerhumans.com/wp-content/uploads/2017/11/featured.jpg");
        url_maps.put("Physics2", "http://www.dailymoss.com/wp-content/uploads/2014/07/3idiotsquote.jpg");
        url_maps.put("Physics3", "https://www.brainyquote.com/photos_tr/en/u/unknown/159550/unknown1.jpg");
        url_maps.put("Physics4", "https://www.ryrob.com/wp-content/uploads/2017/09/Hustle-Quotes-Motivation_-Don%E2%80%99t-stay-in-bed-unless-you-can.jpg");
        url_maps.put("Mathematics",  "https://quotefancy.com/media/wallpaper/1600x900/18846-Frank-Ocean-Quote-Work-hard-in-silence-let-your-success-be-your.jpg");

        for(String name : url_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(getContext());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            //add your extra information
//            textSliderView.bundle(new Bundle());
//            textSliderView.getBundle()
//                    .putString("extra",name);
            mDemoSlider.setBackgroundColor(Color.TRANSPARENT);
            mDemoSlider.addSlider(textSliderView);
        }

        mDemoSlider.setBackgroundColor(Color.TRANSPARENT);
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(10000);
        mDemoSlider.addOnPageChangeListener(this);

        return rootView;
    }

    @Override
    public void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    private void sscBookClickAction(View view, int position) {
        if(CheckNetworkConnection.isConnectionAvailable(getActivity())) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(sscListitems.get(position).getBookLink().toString()), "application/pdf");
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Log.d("l", "l");
            }
            Toast toast = Toast.makeText(getContext(), "view online", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            Toast.makeText(getActivity(),"Unable to connect to internet, please check your network connection",Toast.LENGTH_SHORT).show();
        }
    }
    private void bankBookClickAction(View view,int position) {
        if(CheckNetworkConnection.isConnectionAvailable(getActivity())) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(bankListitems.get(position).getBookLink().toString()), "application/pdf");
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Log.d("l", "l");
            }
            Toast toast = Toast.makeText(getContext(), "view online", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            Toast.makeText(getActivity(),"Unable to connect to internet, please check your network connection",Toast.LENGTH_SHORT).show();
        }
    }
    private void armedForceClickAction(View view,int position) {
        if(CheckNetworkConnection.isConnectionAvailable(getActivity())) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(armedforceListitems.get(position).getBookLink().toString()), "application/pdf");
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Log.d("l", "l");
            }
            Toast toast = Toast.makeText(getContext(), "view online", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            Toast.makeText(getActivity(),"Unable to connect to internet, please check your network connection",Toast.LENGTH_SHORT).show();
        }
    }

    static {
        sscListitems.add(new LibraryBookDataModel("book name","book description","djvkjdsvhk","https://i.pinimg.com/736x/76/6f/3d/766f3d034ffe6a13acfbe9394553c531--novel-cover-design-book-cover-designs.jpg"));
        sscListitems.add(new LibraryBookDataModel("book name","book description","djvkjdsvhk","https://spark.adobe.com/images/landing/examples/architecture-book-cover.jpg"));
        sscListitems.add(new LibraryBookDataModel("book name","book description","djvkjdsvhk","https://about.canva.com/wp-content/uploads/sites/3/2015/01/notebook_bookcover.png"));
        sscListitems.add(new LibraryBookDataModel("book name","book description","djvkjdsvhk","https://d1csarkz8obe9u.cloudfront.net/posterpreviews/book-cover-flyer-template-8ccc1257ccfd14924d27d1dd5ba441a9.jpg?ts=1456288038"));
        sscListitems.add(new LibraryBookDataModel("book name","book description","djvkjdsvhk","https://marketplace.canva.com/MACSXEOzaeQ/1/0/thumbnail_large/canva-orange-and-dark-purple-triangular-modern-architecture-book-cover-MACSXEOzaeQ.jpg"));
        sscListitems.add(new LibraryBookDataModel("book name","book description","djvkjdsvhk","https://i.pinimg.com/736x/76/6f/3d/766f3d034ffe6a13acfbe9394553c531--novel-cover-design-book-cover-designs.jpg"));
        sscListitems.add(new LibraryBookDataModel("book name","book description","djvkjdsvhk","https://spark.adobe.com/images/landing/examples/architecture-book-cover.jpg"));
        sscListitems.add(new LibraryBookDataModel("book name","book description","djvkjdsvhk","https://about.canva.com/wp-content/uploads/sites/3/2015/01/notebook_bookcover.png"));
        sscListitems.add(new LibraryBookDataModel("book name","book description","djvkjdsvhk","https://d1csarkz8obe9u.cloudfront.net/posterpreviews/book-cover-flyer-template-8ccc1257ccfd14924d27d1dd5ba441a9.jpg?ts=1456288038"));
        sscListitems.add(new LibraryBookDataModel("book name","book description","djvkjdsvhk","https://marketplace.canva.com/MACSXEOzaeQ/1/0/thumbnail_large/canva-orange-and-dark-purple-triangular-modern-architecture-book-cover-MACSXEOzaeQ.jpg"));
        sscListitems.add(new LibraryBookDataModel("book name","book description","djvkjdsvhk","https://spark.adobe.com/images/landing/examples/how-to-book-cover.jpg"));
        sscListitems.add(new LibraryBookDataModel("book name hjjhgj  jhjhgh jhghjhg jhgjhgj jhhjg  jhhjg jhghjhg jhjhghj jhgjhgjhg","book description nhnjhgjh jhgjhg jhgjhgjh vjhghjhgjh jgjhgjhg jhgjhgjg hghjhg hgjj","djvkjdsvhk","https://spark.adobe.com/images/landing/examples/how-to-book-cover.jpg"));

        bankListitems = sscListitems;
        armedforceListitems = sscListitems;

    }

    private void OnClickMainWebsite() {
        showMyAdd();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse("http://www.ccsuniversity.ac.in/default.htm"));
        startActivity(i);
    }

    private void OnClickWebPortal() {
        showMyAdd();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse("http://ccsuweb.in/"));
        startActivity(i);
    }
    private void OnClickAdmitCard() {
        showMyAdd();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse("http://exams.ccsuresults.com/"));
        startActivity(i);
    }
    private void OnClickResult() {
        showMyAdd();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse("http://192.163.211.186/~ccsuresu/"));
        startActivity(i);
    }
    private void OnClickSarkariResult() {
        showMyAdd();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse("http://www.sarkariresult.com/"));
        startActivity(i);
    }
}


