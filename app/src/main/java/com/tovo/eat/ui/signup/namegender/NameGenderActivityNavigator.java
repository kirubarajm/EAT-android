package com.tovo.eat.ui.signup.namegender;

import com.tovo.eat.ui.home.region.RegionSearchModel;

import java.util.List;

public interface NameGenderActivityNavigator {

    void handleError(Throwable throwable);

    void proceedClick();

    void genderSuccess(String strMessage);

    void genderFailure(String strMessage);

    void regionListLoaded(List<RegionSearchModel.Result> regionList);

}
