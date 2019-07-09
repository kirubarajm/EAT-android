package com.tovo.eat.utilities.fonts.poppins;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.tovo.eat.R;

public class EatTextView extends android.support.v7.widget.AppCompatTextView {

    private int typefaceType;
    private TypeFactory mFontFactory;

    public EatTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context, attrs);
    }

    public EatTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context, attrs);
    }

    public EatTextView(Context context) {
        super(context);
    }

    private void applyCustomFont(Context context, AttributeSet attrs) {


        TypedArray array = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.Poppins,
                0, 0);
        try {
            typefaceType = array.getInteger(R.styleable.Poppins_font_name, 0);
        } finally {
            array.recycle();
        }
        if (!isInEditMode()) {
            setTypeface(getTypeFace(typefaceType));
        }


    }

    public Typeface getTypeFace(int type) {
        if (mFontFactory == null)
            mFontFactory = new TypeFactory(getContext());

        switch (type) {
            case Constants.BLACK:
                return mFontFactory.poppinsBlack;

            case Constants.BLACKITALIC:
                return mFontFactory.poppinsBlackItalic;

            case Constants.BOLD:
                return mFontFactory.poppinsBold;

            case Constants.BOLDITALIC:
                return mFontFactory.poppinsBoldItalic;

            case Constants.EXTRABOLD:
                return mFontFactory.poppinsExtraBold;


            case Constants.EXTRABOLDITALIC:
                return mFontFactory.poppinsExtraBoldItalic;


            case Constants.EXTRA_LIGHT:
                return mFontFactory.poppinsExtraLight;


            case Constants.EXTRA_LIGHT_ITALIC:
                return mFontFactory.poppinsExtrLightItalic;


            case Constants.ITALIC:
                return mFontFactory.poppinsItalic;


            case Constants.LIGHT:
                return mFontFactory.poppinsLight;


            case Constants.LIGHT_ITALIC:
                return mFontFactory.poppinsLightItalic;


            case Constants.MEDIUM:
                return mFontFactory.poppinsMedium;


            case Constants.MEDIUMT_ITALIC:
                return mFontFactory.poppinsMediumLight;


            case Constants.REGULAR:
                return mFontFactory.poppinsRegular;


            case Constants.SEMI_BOLD:
                return mFontFactory.poppinsSemiBold;


            case Constants.SEMI_BOLD_ITALIC:
                return mFontFactory.poppinsSemiBoldItalic;

            case Constants.THIN:
                return mFontFactory.poppinsThin;


            case Constants.THIN_ITALIC:
                return mFontFactory.poppinsThinItalic;

            default:
                return mFontFactory.poppinsRegular;
        }
    }

    public interface Constants {
        int BLACK = 1,
                BLACKITALIC = 2,
                BOLD = 3,
                BOLDITALIC = 4,
                EXTRABOLD = 5,
                EXTRABOLDITALIC = 6,
                EXTRA_LIGHT = 7,
                EXTRA_LIGHT_ITALIC = 8,
                ITALIC = 9,
                LIGHT = 10,
                LIGHT_ITALIC = 11,
                MEDIUM = 12,
                MEDIUMT_ITALIC = 13,
                REGULAR = 14,
                SEMI_BOLD = 15,
                SEMI_BOLD_ITALIC = 16,
                THIN = 17,
                THIN_ITALIC = 18;
    }



}
