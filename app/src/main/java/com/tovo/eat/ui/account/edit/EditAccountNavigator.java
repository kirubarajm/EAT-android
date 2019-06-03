package com.tovo.eat.ui.account.edit;

import com.tovo.eat.ui.home.region.RegionSearchModel;

import java.util.List;

public interface EditAccountNavigator {

    void handleError(Throwable throwable);

    void proceedClick();

    void genderSuccess(String strMessage);

    void genderFailure(String strMessage);

    void male();

    void female();

    void regionListLoaded(List<RegionSearchModel.Result> regionList);

    void goBack();


}
