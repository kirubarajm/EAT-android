package com.tovo.eat.ui.cart;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nhaarman.supertooltips.ToolTip;
import com.nhaarman.supertooltips.ToolTipView;
import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityCartBinding;
import com.tovo.eat.ui.address.select.SelectAddressListActivity;
import com.tovo.eat.ui.base.BaseFragment;
import com.tovo.eat.ui.cart.coupon.CouponListActivity;
import com.tovo.eat.ui.cart.funnel.FunnelActivity;
import com.tovo.eat.ui.cart.refund.RefundListAdapter;
import com.tovo.eat.ui.cart.refund.RefundListResponse;
import com.tovo.eat.ui.cart.refund.alert.DialogRefundAlert;
import com.tovo.eat.ui.cart.suggestion.SuggestionProductAdapter;
import com.tovo.eat.ui.cart.xfactoralert.XFactorFragment;
import com.tovo.eat.ui.home.CartListener;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.ui.home.kitchendish.KitchenDishResponse;
import com.tovo.eat.ui.kitchendetails.KitchenDetailsActivity;
import com.tovo.eat.ui.orderplaced.OrderPlacedActivity;
import com.tovo.eat.ui.payment.PaymentActivity;
import com.tovo.eat.ui.registration.RegistrationActivity;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.analytics.Analytics;
import com.tovo.eat.utilities.fonts.poppins.ButtonTextView;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;
import static com.tovo.eat.utilities.AppConstants.CART_REQUESTCODE;

public class CartActivity extends BaseFragment<ActivityCartBinding, CartViewModel> implements CartNavigator, CartDishAdapter.LiveProductsAdapterListener, RefundListAdapter.LiveProductsAdapterListener, SuggestionProductAdapter.LiveProductsAdapterListener, BillListAdapter.BilldetailsInfoListener {

    public ToolTipView myToolTipView;
    @Inject
    CartDishAdapter adapter;
    @Inject
    SuggestionProductAdapter suggestionProductAdapter;
    @Inject
    CartViewModel mCartViewModel;
    CartListener cartListener;
    @Inject
    RefundListAdapter refundListAdapter;
    @Inject
    BillListAdapter billListAdapter;
    Dialog dialog;
    Analytics analytics;
    String pageName = AppConstants.SCREEN_CART_PAGE;
    boolean infoClicked = false;
    private ActivityCartBinding mActivityCartBinding;

    String screenName = "";

    public static CartActivity newInstance() {
        Bundle args = new Bundle();
        CartActivity fragment = new CartActivity();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        cartListener = (CartListener) context;
        super.onAttach(context);
    }


    @Override
    public int getBindingVariable() {
        return BR.cartViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_cart;
    }

    @Override
    public CartViewModel getViewModel() {

        return mCartViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCartViewModel.setNavigator(this);
        adapter.setListener(this);
        suggestionProductAdapter.setListener(this);
        refundListAdapter.setListener(this);
        billListAdapter.setListener(this);

        Bundle bundle = getArguments();
        if (bundle!=null){
            screenName = bundle.getString("screenName");
            mCartViewModel.previousPage.set(screenName);
        }
        analytics = new Analytics(getActivity(), pageName);
    }

    public void metricsOpenCartPage() {
        try {
            if (mCartViewModel.getDataManager().getPromotionId() != null && mCartViewModel.getDataManager().getCartDetails() != null
                    && mCartViewModel.getDataManager().getCurrentAddressTitle() != null) {
                String promotionId = String.valueOf(mCartViewModel.getDataManager().getPromotionId());
                String cartDetails = mCartViewModel.getDataManager().getCartDetails();
                String addressType = mCartViewModel.getDataManager().getCurrentAddressTitle();
                new Analytics().openCartPageMetrics(screenName, mCartViewModel.makeitId, mCartViewModel.totalAmount, promotionId,
                        addressType, AppConstants.SCREEN_PAYMENT, cartDetails);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivityCartBinding = getViewDataBinding();
        dialog = new Dialog(getBaseActivity());

        mCartViewModel.toolbarTitle.set(getString(R.string.cart));
        LinearLayoutManager mLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mActivityCartBinding.recyclerviewOrders.setLayoutManager(mLayoutManager);
        mActivityCartBinding.recyclerviewOrders.setAdapter(adapter);


        LinearLayoutManager mLayoutManager2
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        mLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityCartBinding.recyclerviewList.setLayoutManager(mLayoutManager2);
        mActivityCartBinding.recyclerviewList.setAdapter(refundListAdapter);


        LinearLayoutManager billLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mActivityCartBinding.recyclerviewBill.setLayoutManager(billLayoutManager);
        mActivityCartBinding.recyclerviewBill.setAdapter(billListAdapter);


        LinearLayoutManager suggestionLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mActivityCartBinding.recyclerviewProductSuggestion.setLayoutManager(suggestionLayoutManager);
        mActivityCartBinding.recyclerviewProductSuggestion.setAdapter(suggestionProductAdapter);


        subscribeToLiveData();


       /* mActivityCartBinding.cartScroll.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }
        });*/


        mActivityCartBinding.cartScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (myToolTipView != null) {
                    myToolTipView.remove();
                    infoClicked = false;
                }
            }
        });

    }


    @Override
    public void cartLoaded() {
        stopCartLoader();
    }

    @Override
    public void dishListLoaded() {
    }

    @Override
    public void paymentMode(String mode) {


        if (mode.equals(getString(R.string.cash_on_delivery))) {
            mCartViewModel.payment.set(true);
        } else if (mode.equals(getString(R.string.pay_online))) {

            mCartViewModel.payment.set(true);

        } else {
            mCartViewModel.payment.set(false);
        }


    }

    @Override
    public void selectAddress() {

        new Analytics().sendClickData(pageName, AppConstants.CLICK_CHANGE_ADDRESS);

        Intent intent = SelectAddressListActivity.newIntent(getContext());
        startActivityForResult(intent, AppConstants.SELECT_ADDRESS_LIST_CODE);

    }


    @Override
    public void orderCompleted() {
        Intent intent = OrderPlacedActivity.newIntent(getContext());
        startActivity(intent);

    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getBaseActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void otherKitchenDish(Long makeitId, Integer productId, Integer quantity, Integer price) {

    }

    @Override
    public void emptyCart() {
    }

    @Override
    public void postRegistration(String type, String totalAmount) {

        Intent intent = RegistrationActivity.newIntent(getContext());
        intent.putExtra("type", type);
        intent.putExtra("amount", totalAmount);
        startActivityForResult(intent, CART_REQUESTCODE);

    }

    @Override
    public void toastMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void paymentGateway(String totalAmount) {

            Intent intent = PaymentActivity.newIntent(getContext());
            intent.putExtra("amount", totalAmount);
            startActivity(intent);

    }

    @Override
    public void refundList() {
    }

    @Override
    public void checkRefund() {


        if (mActivityCartBinding.refundCheck.isChecked()) {
            mCartViewModel.refundChecked.set(true);

            new Analytics().sendClickData(pageName, AppConstants.CLICK_REFUND_CHECK);
        } else {
            new Analytics().sendClickData(pageName, AppConstants.CLICK_REFUND_UNCHECK);
            mCartViewModel.refundChecked.set(false);
            mCartViewModel.getDataManager().setRefundId(0);
            refundListAdapter.selectedItemClear();

            mCartViewModel.getRefundListItemsLiveData().observe(this,
                    kitchenItemViewModel -> mCartViewModel.addRefundItemsToList(kitchenItemViewModel));

            mCartViewModel.fetchRepos();

        }

    }

    @Override
    public void promoList() {

        new Analytics().sendClickData(pageName, AppConstants.CLICK_ADD_PROMO_CODE);
        Intent intent = CouponListActivity.newIntent(getContext());
        intent.putExtra("clickable", false);
        startActivityForResult(intent, AppConstants.COUPON_LIST_CODE);

    }

    @Override
    public void refundAlert() {

        DialogRefundAlert fragment = new DialogRefundAlert();
        fragment.setTargetFragment(this, 0);
        fragment.setCancelable(false);

        DialogRefundAlert.newInstance(fragment).show(getFragmentManager(), getBaseActivity(), mCartViewModel.grand_total.get());

    }

    @Override
    public void redirectHome() {
        ((MainActivity) getActivity()).openHome();

    }

    @Override
    public void notServicable() {

        if (!dialog.isShowing())
            showDialog();
    }

    @Override
    public void showXFactorALert(String msg, String title) {

        Bundle bundle = new Bundle();
        bundle.putString("message", msg);
        bundle.putString("title", title);
        XFactorFragment bottomSheetFragment = new XFactorFragment();
        bottomSheetFragment.setArguments(bundle);
        bottomSheetFragment.show(getBaseActivity().getSupportFragmentManager(), bottomSheetFragment.getTag());
    }

    @Override
    public void funnelAlert() {

        Intent intent = FunnelActivity.newIntent(getActivity());
        startActivity(intent);

    }

    @Override
    public void gotoKitchen(Long kitchenid) {

        new Analytics().selectKitchen(AppConstants.ANALYTICYS_CART_KITCHEN, kitchenid);

        Intent intent = KitchenDetailsActivity.newIntent(getContext());
        intent.putExtra("kitchenId", kitchenid);
        startActivity(intent);
    }

    @Override
    public void clearToolTips() {
        if (myToolTipView != null) {
            myToolTipView.remove();
            infoClicked = false;
            myToolTipView = null;
        }
    }

    @Override
    public void metricsCartOpen() {
        metricsOpenCartPage();
    }

    private void subscribeToLiveData() {
        mCartViewModel.getDishItemsLiveData().observe(this,
                kitchenItemViewModel -> mCartViewModel.addDishItemsToList(kitchenItemViewModel));

        mCartViewModel.getRefundListItemsLiveData().observe(this,
                kitchenItemViewModel -> mCartViewModel.addRefundItemsToList(kitchenItemViewModel));

        mCartViewModel.getCartBillLiveData().observe(this,
                cartdetails -> mCartViewModel.addBillItemsToList(cartdetails));


        mCartViewModel.getSuggestionViewLiveData().observe(this,
                products -> mCartViewModel.addSuggestionProductItems(products));


    }


    @Override
    public void onResume() {
        super.onResume();
        mCartViewModel.setAddressTitle();

        if (mCartViewModel.getCartPojoDetails() != null) {
            startCartLoader();
            mCartViewModel.fetchRepos();
        }
        if (myToolTipView != null) {
            myToolTipView.remove();
            infoClicked = false;
            myToolTipView = null;
        }
    }

    @Override
    public void onItemClickData(CartDishResponse.Result blogUrl) {

    }

    @Override
    public void onItemClickData(KitchenDishResponse.Productlist blogUrl, View view) {

    }

    @Override
    public void sendCart() {
        cartListener.checkCart();
    }

    @Override
    public void dishRefresh() {
        if (myToolTipView != null) {
            myToolTipView.remove();
            infoClicked = false;
            myToolTipView = null;
        }
        mCartViewModel.xfactorClick.set(false);
        cartListener.checkCart();
        mCartViewModel.fetchRepos();


    }

    @Override
    public void addDishFavourite(Integer dishId, String fav) {

    }

    @Override
    public void productNotAvailable(int quantity, String productname) {

    }

    @Override
    public void removeDishFavourite(Integer favId) {

    }

    @Override
    public void saveToCart(String cart) {
        mCartViewModel.saveToCartPojo(cart);
        cartListener.checkCart();
    }

    @Override
    public String getCartData() {
        return mCartViewModel.getCartPojoDetails();
    }

    @Override
    public void productNotAvailable() {
        Toast.makeText(getContext(), "Entered quantity not available now", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void reloadCart() {
        if (myToolTipView != null) {
            myToolTipView.remove();
            infoClicked = false;
            myToolTipView = null;
        }
        if (mCartViewModel.getCartPojoDetails() != null) {
            mCartViewModel.xfactorClick.set(false);
            mCartViewModel.fetchRepos();
            mCartViewModel.emptyCart.set(false);
        } else {
            mCartViewModel.emptyCart.set(true);
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CART_REQUESTCODE) {
            boolean status = data.getBooleanExtra("status", false);
            if (status) {
                //    mCartViewModel.paymentModeCheck();

            }
        } else if (requestCode == AppConstants.REFUND_LIST_CODE) {

            if (resultCode == RESULT_OK) {
                mCartViewModel.fetchRepos();
            }
        } else if (requestCode == AppConstants.COUPON_LIST_CODE) {
            if (resultCode == RESULT_OK) {
                mCartViewModel.fetchRepos();
            }


        } else if (requestCode == AppConstants.SELECT_ADDRESS_LIST_CODE) {
            if (resultCode == RESULT_OK) {
                mCartViewModel.fetchRepos();
            }
        }
    }

    @Override
    public void onItemClickData(RefundListResponse.Result result, int selected) {

        new Analytics().sendClickData(pageName, AppConstants.CLICK_REFUND_SELECT);

        if (selected == -1) {
            mCartViewModel.saveRefundandCalculate(0);
        } else {
            mCartViewModel.saveRefundandCalculate(result.getRcid());
        }

    }

    public void showDialog() {
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_location_not_servicable);

        ButtonTextView text = dialog.findViewById(R.id.changeAddress);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Analytics().sendClickData(pageName, AppConstants.CLICK_CHANGE_ADDRESS);
                dialog.dismiss();
                selectAddress();
            }
        });

        ButtonTextView dialogButton = dialog.findViewById(R.id.home);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectHome();
                dialog.dismiss();

                new Analytics().sendClickData(pageName, AppConstants.CLICK_GO_HOME);

            }
        });
        dialog.show();
    }

    public void startCartLoader() {
        mActivityCartBinding.cartLoader.setVisibility(View.VISIBLE);
    }

    public void stopCartLoader() {
        mActivityCartBinding.cartLoader.setVisibility(View.GONE);
    }

    @Override
    public void infoClick(CartPageResponse.Cartdetail cartdetail, ImageView imageView) {


        if (!infoClicked) {
            if (cartdetail.getInfostatus()) {
                infoClicked = true;
                ToolTip toolTip = new ToolTip()
                        .withContentView(LayoutInflater.from(MvvmApp.getInstance()).inflate(R.layout.tool_tip_cart_info, null))
                        // .withText("Now delivering to "+mHomeTabViewModel.getDataManager().getCurrentAddress())
                        .withColor(MvvmApp.getInstance().getResources().getColor(R.color.light_gray))
                        .withShadow()
                        .withTextColor(Color.BLACK)
                        .withAnimationType(ToolTip.AnimationType.FROM_MASTER_VIEW);
                myToolTipView = mActivityCartBinding.activityMainTooltipframelayout.showToolTipForView(toolTip, imageView);
                TextView title = myToolTipView.findViewById(R.id.activity_main_redtv);
                StringBuilder sTitle = new StringBuilder();
                for (int i = 0; i < cartdetail.getInfodetails().size(); i++) {
                    sTitle.append("\n").append(cartdetail.getInfodetails().get(i).getName()).append("    ").append("Rs.").append(cartdetail.getInfodetails().get(i).getPrice());
                }
                title.setText(sTitle.toString());

                myToolTipView.setOnToolTipViewClickedListener(new ToolTipView.OnToolTipViewClickedListener() {
                    @Override
                    public void onToolTipViewClicked(ToolTipView toolTipView) {

                        if (myToolTipView != null) {
                            myToolTipView.remove();
                            infoClicked = false;
                        }
                    }
                });
            }
        } else {
            if (myToolTipView != null) {
                myToolTipView.remove();
                infoClicked = false;
                myToolTipView = null;
            }
        }


        mActivityCartBinding.cart.setOnTouchListener((v, event) -> {
            if (myToolTipView != null) {
                myToolTipView.remove();
                infoClicked = false;
                myToolTipView = null;
            }
            return true;
        });

    }


}