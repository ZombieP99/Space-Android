package com.example.yousef;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "SpaceEncyclopediaPrefs";
    private static final String KEY_LANGUAGE = "language";
    private static final String KEY_TEXT_SIZE = "text_size";
    private static final String KEY_SELECTED_PLANET = "selected_planet";

    private static final String LANGUAGE_ENGLISH = "en";
    private static final String LANGUAGE_ARABIC = "ar";

    private static final String TEXT_SIZE_SMALL = "small";
    private static final String TEXT_SIZE_MEDIUM = "medium";
    private static final String TEXT_SIZE_LARGE = "large";

    private TextView titleTextView, infoTextView, languageLabel, textSizeLabel;
    private RadioGroup languageRadioGroup, textSizeRadioGroup;
    private RadioButton englishRadio, arabicRadio, smallRadio, mediumRadio, largeRadio;
    private LinearLayout earthContainer, marsContainer, jupiterContainer;
    private ImageView earthImage, marsImage, jupiterImage;
    private TextView earthLabel, marsLabel, jupiterLabel;

    private SharedPreferences sharedPreferences;
    private String currentLanguage = LANGUAGE_ENGLISH;
    private String currentTextSize = TEXT_SIZE_MEDIUM;
    private String selectedPlanet = "earth";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 1. تحميل الإعدادات أولاً
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        loadPreferences();

        // 2. ضبط اللغة في النظام قبل عرض الواجهة لضمان اختيار ملف strings الصحيح
        setAppLanguage(currentLanguage);

        super.onCreate(savedInstanceState);

        // 3. عرض الواجهة
        setContentView(R.layout.activity_main);

        // 4. تعريف العناصر
        initializeViews();

        // 5. إعداد المستمعين والبيانات
        setupClickListeners();
        applySavedPreferences();

        // تحديث النصوص والواجهة فور التشغيل
        updateAllTexts();
        updatePlanetSelection();
    }

    private void initializeViews() {
        titleTextView = findViewById(R.id.titleTextView);
        infoTextView = findViewById(R.id.infoTextView);
        languageLabel = findViewById(R.id.languageLabel);
        textSizeLabel = findViewById(R.id.textSizeLabel);
        languageRadioGroup = findViewById(R.id.languageRadioGroup);
        textSizeRadioGroup = findViewById(R.id.textSizeRadioGroup);
        englishRadio = findViewById(R.id.englishRadio);
        arabicRadio = findViewById(R.id.arabicRadio);
        smallRadio = findViewById(R.id.smallRadio);
        mediumRadio = findViewById(R.id.mediumRadio);
        largeRadio = findViewById(R.id.largeRadio);
        earthContainer = findViewById(R.id.earthContainer);
        marsContainer = findViewById(R.id.marsContainer);
        jupiterContainer = findViewById(R.id.jupiterContainer);
        earthImage = findViewById(R.id.earthImage);
        marsImage = findViewById(R.id.marsImage);
        jupiterImage = findViewById(R.id.jupiterImage);
        earthLabel = findViewById(R.id.earthLabel);
        marsLabel = findViewById(R.id.marsLabel);
        jupiterLabel = findViewById(R.id.jupiterLabel);
    }

    private void setupClickListeners() {
        setupPlanetTouchAnimation(earthContainer, earthImage, "earth");
        setupPlanetTouchAnimation(marsContainer, marsImage, "mars");
        setupPlanetTouchAnimation(jupiterContainer, jupiterImage, "jupiter");

        languageRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.englishRadio) {
                currentLanguage = LANGUAGE_ENGLISH;
            } else if (checkedId == R.id.arabicRadio) {
                currentLanguage = LANGUAGE_ARABIC;
            }

            // حفظ اللغة الجديدة
            savePreferences();

            // تحديث لغة التطبيق وتحديث جميع النصوص فوراً
            setAppLanguage(currentLanguage);
            updateAllTexts();
        });

        textSizeRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.smallRadio) currentTextSize = TEXT_SIZE_SMALL;
            else if (checkedId == R.id.mediumRadio) currentTextSize = TEXT_SIZE_MEDIUM;
            else if (checkedId == R.id.largeRadio) currentTextSize = TEXT_SIZE_LARGE;
            applyTextSize();
            savePreferences();
        });
    }

    private void setupPlanetTouchAnimation(LinearLayout container, ImageView imageView, String planetName) {
        container.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    animatePlanetScale(imageView, 0.9f);
                    return true;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    animatePlanetScale(imageView, 1.0f);
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        selectedPlanet = planetName;
                        updatePlanetInfo(planetName);
                        updatePlanetSelection();
                        savePreferences();
                    }
                    return true;
            }
            return false;
        });
    }

    private void animatePlanetScale(ImageView imageView, float scale) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(imageView, "scaleX", scale);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(imageView, "scaleY", scale);
        scaleX.setDuration(150);
        scaleY.setDuration(150);
        scaleX.setInterpolator(new DecelerateInterpolator());
        scaleY.setInterpolator(new DecelerateInterpolator());
        scaleX.start();
        scaleY.start();
    }

    private void loadPreferences() {
        currentLanguage = sharedPreferences.getString(KEY_LANGUAGE, LANGUAGE_ENGLISH);
        currentTextSize = sharedPreferences.getString(KEY_TEXT_SIZE, TEXT_SIZE_MEDIUM);
        selectedPlanet = sharedPreferences.getString(KEY_SELECTED_PLANET, "earth");
    }

    private void savePreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_LANGUAGE, currentLanguage);
        editor.putString(KEY_TEXT_SIZE, currentTextSize);
        editor.putString(KEY_SELECTED_PLANET, selectedPlanet);
        editor.apply();
    }

    private void applySavedPreferences() {
        if (currentLanguage.equals(LANGUAGE_ENGLISH)) englishRadio.setChecked(true);
        else arabicRadio.setChecked(true);

        switch (currentTextSize) {
            case TEXT_SIZE_SMALL: smallRadio.setChecked(true); break;
            case TEXT_SIZE_MEDIUM: mediumRadio.setChecked(true); break;
            case TEXT_SIZE_LARGE: largeRadio.setChecked(true); break;
        }
        applyTextSize();
    }

    private void setAppLanguage(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = getResources().getConfiguration();
        config.setLocale(locale);

        // تحديث المصادر لتعمل مع ملف strings الصحيح
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        // تحديث اتجاه الشاشة (RTL للعربي و LTR للإنجليزي)
        if (getWindow() != null && getWindow().getDecorView() != null) {
            if (languageCode.equals(LANGUAGE_ARABIC)) {
                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            } else {
                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            }
        }
    }

    private void updateAllTexts() {
        // هذه الدالة ستسحب النصوص من ملف Strings المختار حالياً في النظام
        titleTextView.setText(getString(R.string.app_title));
        languageLabel.setText(getString(R.string.language));
        textSizeLabel.setText(getString(R.string.text_size));
        englishRadio.setText(getString(R.string.english));
        arabicRadio.setText(getString(R.string.arabic));
        smallRadio.setText(getString(R.string.small));
        mediumRadio.setText(getString(R.string.medium));
        largeRadio.setText(getString(R.string.large));
        earthLabel.setText(getString(R.string.earth));
        marsLabel.setText(getString(R.string.mars));
        jupiterLabel.setText(getString(R.string.jupiter));

        // تحديث نص معلومات الكوكب المختار بناءً على اللغة الجديدة
        updatePlanetInfo(selectedPlanet);
    }

    private void applyTextSize() {
        float textSize;
        switch (currentTextSize) {
            case TEXT_SIZE_SMALL: textSize = 14f; break;
            case TEXT_SIZE_LARGE: textSize = 24f; break;
            default: textSize = 18f;
        }
        if (infoTextView != null) infoTextView.setTextSize(textSize);
    }

    private void updatePlanetInfo(String planet) {
        int resId;
        // هنا نستخدم المنطق الذي يضمن اختيار النص العربي من ملف strings-ar
        if (currentLanguage.equals(LANGUAGE_ARABIC)) {
            switch (planet) {
                case "mars": resId = R.string.mars_info_ar; break;
                case "jupiter": resId = R.string.jupiter_info_ar; break;
                default: resId = R.string.earth_info_ar;
            }
        } else {
            switch (planet) {
                case "mars": resId = R.string.mars_info; break;
                case "jupiter": resId = R.string.jupiter_info; break;
                default: resId = R.string.earth_info;
            }
        }
        infoTextView.setText(getString(resId));
    }

    private void updatePlanetSelection() {
        // توضيح الكوكب المختار من خلال الشفافية (Alpha)
        earthContainer.setAlpha(selectedPlanet.equals("earth") ? 1.0f : 0.4f);
        marsContainer.setAlpha(selectedPlanet.equals("mars") ? 1.0f : 0.4f);
        jupiterContainer.setAlpha(selectedPlanet.equals("jupiter") ? 1.0f : 0.4f);
    }
}