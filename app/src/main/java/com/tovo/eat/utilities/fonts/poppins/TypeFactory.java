package com.tovo.eat.utilities.fonts.poppins;

import android.content.Context;
import android.graphics.Typeface;

public class TypeFactory {

    private String BLACK = "Poppins-Black.otf";
    private String BLACKITALIC = "Poppins-BlackItalic.otf";
    private String BOLD = "Poppins-Bold.otf";
    private String BOLDITALIC = "Poppins-BoldItalic.otf";
    private String EXTRABOLD = "Poppins-ExtraBold.otf";
    private String EXTRABOLDITALIC = "Poppins-ExtraBoldItalic.otf";
    private String EXTRA_LIGHT = "Poppins-ExtraLight.otf";
    private String EXTRA_LIGHT_ITALIC = "Poppins-ExtraLightItalic.otf";
    private String ITALIC = "Poppins-Italic.otf";
    private String LIGHT = "Poppins-Light.otf";
    private String LIGHT_ITALIC = "Poppins-LightItalic.otf";
    private String MEDIUM = "Poppins-Medium.otf";
    private String MEDIUMT_ITALIC = "Poppins-MediumItalic.otf";
    private String REGULAR = "Poppins-Regular.otf";
    private String SEMI_BOLD = "Poppins-SemiBold.otf";
    private String SEMI_BOLD_ITALIC = "Poppins-SemiBoldItalic.otf";
    private String THIN = "Poppins-Thin.otf";
    private String THIN_ITALIC = "Poppins-ThinItalic.otf";


    public Typeface poppinsBlack;
    public Typeface poppinsBlackItalic;
    public Typeface poppinsBold;
    public Typeface poppinsBoldItalic;
    public Typeface poppinsExtraBold;
    public Typeface poppinsExtraBoldItalic;
    public Typeface poppinsExtraLight;
    public Typeface poppinsExtrLightItalic;
    public Typeface poppinsItalic;
    public Typeface poppinsLight;
    public Typeface poppinsLightItalic;
    public Typeface poppinsMedium;
    public Typeface poppinsMediumLight;
    public Typeface poppinsRegular;
    public Typeface poppinsSemiBold;
    public Typeface poppinsSemiBoldItalic;
    public Typeface poppinsThin;
    public Typeface poppinsThinItalic;


    public TypeFactory(Context context) {
        poppinsBlack = Typeface.createFromAsset(context.getAssets(), BLACK);
        poppinsBlackItalic = Typeface.createFromAsset(context.getAssets(), BLACKITALIC);
        poppinsBold = Typeface.createFromAsset(context.getAssets(), BOLD);
        poppinsBoldItalic = Typeface.createFromAsset(context.getAssets(), BOLDITALIC);
        poppinsExtraBold = Typeface.createFromAsset(context.getAssets(), EXTRABOLD);
        poppinsExtraBoldItalic = Typeface.createFromAsset(context.getAssets(), EXTRABOLDITALIC);
        poppinsExtraLight = Typeface.createFromAsset(context.getAssets(), EXTRA_LIGHT);
        poppinsExtrLightItalic = Typeface.createFromAsset(context.getAssets(), EXTRA_LIGHT_ITALIC);
        poppinsItalic = Typeface.createFromAsset(context.getAssets(), ITALIC);
        poppinsLight = Typeface.createFromAsset(context.getAssets(), LIGHT);
        poppinsLightItalic = Typeface.createFromAsset(context.getAssets(), LIGHT_ITALIC);
        poppinsMedium = Typeface.createFromAsset(context.getAssets(), MEDIUM);
        poppinsMediumLight = Typeface.createFromAsset(context.getAssets(), MEDIUMT_ITALIC);
        poppinsRegular = Typeface.createFromAsset(context.getAssets(), REGULAR);
        poppinsSemiBold = Typeface.createFromAsset(context.getAssets(), SEMI_BOLD);
        poppinsSemiBoldItalic = Typeface.createFromAsset(context.getAssets(), SEMI_BOLD_ITALIC);
        poppinsThin = Typeface.createFromAsset(context.getAssets(), THIN);
        poppinsThinItalic = Typeface.createFromAsset(context.getAssets(), THIN_ITALIC);
    }

}