package com.tovo.eat.ui.home.homemenu.kitchen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tovo.eat.databinding.ListItemCollectionsBinding;
import com.tovo.eat.databinding.ListItemEmptyArrayBinding;
import com.tovo.eat.databinding.ListItemEmptyBinding;
import com.tovo.eat.databinding.ListItemHomeOffersBinding;
import com.tovo.eat.databinding.ListItemKitchenCollectionsHolderBinding;
import com.tovo.eat.databinding.ListItemKitchensBinding;
import com.tovo.eat.databinding.ListItemRegionCollectionsBinding;
import com.tovo.eat.databinding.ListItemStoryCollectionsBinding;
import com.tovo.eat.ui.base.BaseViewHolder;
import com.tovo.eat.ui.home.homemenu.OffersAdapter;
import com.tovo.eat.ui.home.homemenu.collection.CollectionAdapter;
import com.tovo.eat.ui.home.infinityadapters.InfinityCollectionKitchensAdapter;
import com.tovo.eat.ui.home.infinityadapters.InfinityRegionAdapter;
import com.tovo.eat.ui.home.infinityadapters.InfinityStoriesAdapter;

import java.util.List;

public class KitchenAdapter extends RecyclerView.Adapter<BaseViewHolder> implements CollectionAdapter.LiveProductsAdapterListener, OffersAdapter.LiveProductsAdapterListener, InfinityStoriesAdapter.LiveProductsAdapterListener,InfinityRegionAdapter.LiveProductsAdapterListener,InfinityCollectionKitchensAdapter.LiveProductsAdapterListener {

    private static final int VIEW_TYPE_NORMAL = 0;
    private static final int VIEW_TYPE_COLLECTION = 1;
    private static final int VIEW_TYPE_STORY = 2;
    private static final int VIEW_TYPE_REGION = 3;
    private static final int VIEW_TYPE_COLLECTION_DETAILS = 4;
    private static final int VIEW_TYPE_COUPON = 5;
    private static final int VIEW_TYPE_EMPTY_ARRAY = 100;
    public boolean serviceablekitchen = true;
    Context context;
    private List<KitchenResponse.Result> item_list;
    private LiveProductsAdapterListener mLiveProductsAdapterListener;

    public KitchenAdapter(List<KitchenResponse.Result> item_list) {

        this.item_list = item_list;

    }

    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        switch (i) {

            case VIEW_TYPE_COLLECTION:
                ListItemCollectionsBinding collectionBinding = ListItemCollectionsBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new CollectionViewHolder(collectionBinding);

            case VIEW_TYPE_STORY:
                ListItemStoryCollectionsBinding storyCollectionsBinding = ListItemStoryCollectionsBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new StoryCollectionViewHolder(storyCollectionsBinding);

            case VIEW_TYPE_COUPON:
                ListItemHomeOffersBinding offersBinding = ListItemHomeOffersBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new OffernViewHolder(offersBinding);

            case VIEW_TYPE_REGION:
                ListItemRegionCollectionsBinding regionCollectionsBinding = ListItemRegionCollectionsBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new RegionCollectionViewHolder(regionCollectionsBinding);

            case VIEW_TYPE_COLLECTION_DETAILS:
                ListItemKitchenCollectionsHolderBinding listItemKitchenCollectionsHolderBinding = ListItemKitchenCollectionsHolderBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new KitchenCollectionViewHolder(listItemKitchenCollectionsHolderBinding);

            case VIEW_TYPE_EMPTY_ARRAY:
                ListItemEmptyArrayBinding listItemEmptyArrayBinding = ListItemEmptyArrayBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new EmptyArrayViewHolder(listItemEmptyArrayBinding);

            case VIEW_TYPE_NORMAL:
            default:
                ListItemKitchensBinding blogViewBinding1 = ListItemKitchensBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new LiveProductsViewHolder(blogViewBinding1);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
        baseViewHolder.onBind(i);
    }

    @Override
    public int getItemCount() {

        return item_list.size();

       /* if (item_list != null && item_list.size() > 0) {
            return item_list.size();
        } else {
            return 1;
        }*/
    }


    @Override
    public int getItemViewType(int position) {

        if (item_list != null &&item_list.get(position).getType()!=null&& item_list.size()>0) {

            if (item_list.get(position).getType()!=null&&item_list.get(position).getType() == 0) {
                return VIEW_TYPE_NORMAL;
            } else if (item_list.get(position).getType() == 1&&item_list.get(position).getCollection()!=null&&item_list.get(position).getCollection().size()>0) {
                return VIEW_TYPE_COLLECTION;
            } else if (item_list.get(position).getType() == 2&&item_list.get(position).getStory()!=null&&item_list.get(position).getStory().size()>0) {
                return VIEW_TYPE_STORY;
            } else if (item_list.get(position).getType() == 3&&item_list.get(position).getRegions()!=null&&item_list.get(position).getRegions().size()>0) {
                return VIEW_TYPE_REGION;
            } else if (item_list.get(position).getType() == 4&&item_list.get(position).getCollectionDetails()!=null&&item_list.get(position).getCollectionDetails().size()>0) {
                return VIEW_TYPE_COLLECTION_DETAILS;
            } else if (item_list.get(position).getType() == 5&&item_list.get(position).getCoupon()!=null&&item_list.get(position).getCoupon() .size()>0) {
                return VIEW_TYPE_COUPON;
            } else {
                return VIEW_TYPE_EMPTY_ARRAY;
            }

        } else  if (item_list != null &&item_list.size()>0&& item_list.get(position).getType()==null){
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY_ARRAY;
        }


       /*
        if (item_list != null && !item_list.isEmpty()) {

            if (item_list.get(position).getCollection() != null && item_list.get(position).getCollection().size() > 0) {

                return VIEW_TYPE_COLLECTION;
            } else if (item_list.get(position).getCoupon() != null && item_list.get(position).getCoupon().size() > 0) {

                return VIEW_TYPE_COUPON;

            } else {
                return VIEW_TYPE_NORMAL;
            }


        } else {
            return VIEW_TYPE_EMPTY;
        }*/

    }

    public void clearItems() {
        item_list.clear();
    }

    public void addItems(List<KitchenResponse.Result> blogList, Context context) {
        this.context = context;
        item_list.addAll(blogList);
        notifyDataSetChanged();
    }

    public void setListener(LiveProductsAdapterListener listener) {
        this.mLiveProductsAdapterListener = listener;
    }

    @Override
    public void collectionItemClick(KitchenResponse.Collection collection) {


        mLiveProductsAdapterListener.collectionItemClick(collection);

    }

    @Override
    public void offerItemClick(KitchenResponse.Coupon offers) {
        mLiveProductsAdapterListener.offersItemClick(offers);
    }

    @Override
    public void infinityStoryItemClick(List<KitchenResponse.Story>  story,int position) {
        mLiveProductsAdapterListener.infinityStoryItemClick(story,position);
    }

    @Override
    public void collectionItemClick(KitchenResponse.Region region) {
        mLiveProductsAdapterListener.regionCollectionItemClick(region);
    }

    @Override
    public void infinityCollectionDetailItemClick(KitchenResponse.CollectionDetail collection) {
        mLiveProductsAdapterListener.infinityCollectionDetailItemClick(collection);
    }

    public interface LiveProductsAdapterListener {

        void collectionItemClick(KitchenResponse.Collection collection);

        void offersItemClick(KitchenResponse.Coupon offers);

        void onItemClickData(Long kitchenId);

        void animateView(View view);

        void removeDishFavourite(Integer favId);

        void addFav(long id, String fav);

        void infinityStoryItemClick(List<KitchenResponse.Story>  story,int position);
        void regionCollectionItemClick(KitchenResponse.Region collection);
        void infinityCollectionDetailItemClick(KitchenResponse.CollectionDetail collection);
    }

    public class EmptyViewHolder extends BaseViewHolder {

        private final ListItemEmptyBinding mBinding;


        EmptyItemViewModel emptyItemViewModel;

        public EmptyViewHolder(ListItemEmptyBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            emptyItemViewModel = new EmptyItemViewModel("No results found for your selection");
            mBinding.setEmptyItemViewModel(emptyItemViewModel);
        }

    }

    public class LiveProductsViewHolder extends BaseViewHolder implements KitchenItemViewModel.KitchenItemViewModelListener {
        ListItemKitchensBinding mListItemLiveProductsBinding;
        KitchenItemViewModel mLiveProductsItemViewModel;

        public LiveProductsViewHolder(ListItemKitchensBinding binding) {
            super(binding.getRoot());
            this.mListItemLiveProductsBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (item_list.isEmpty()) return;
            final KitchenResponse.Result blog = item_list.get(position);
            mLiveProductsItemViewModel = new KitchenItemViewModel(this, blog);
            mListItemLiveProductsBinding.setKitchenItemViewModel(mLiveProductsItemViewModel);

            if (blog.getMakeituserid() == null)
                mListItemLiveProductsBinding.kitchenTile.setVisibility(View.GONE);

            mListItemLiveProductsBinding.executePendingBindings();


        }


        @Override
        public void onItemClick(Long id) {

            mLiveProductsAdapterListener.onItemClickData(id);


        }

        @Override
        public void addFavourites(Long id, String fav) {
            mLiveProductsAdapterListener.addFav(id, fav);
        }

        @Override
        public void removeFavourites(Integer favId) {

            mLiveProductsAdapterListener.removeDishFavourite(favId);
        }
    }


    /*public class CollectionViewHolder extends BaseViewHolder {
        ListItemCollectionsBinding mListItemLiveProductsBinding;


        public CollectionViewHolder(ListItemCollectionsBinding binding) {
            super(binding.getRoot());
            this.mListItemLiveProductsBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (item_list.get(position).getCollection().isEmpty()) return;

            mListItemLiveProductsBinding.executePendingBindings();


            LinearLayoutManager mLayoutManager = new LinearLayoutManager(mListItemLiveProductsBinding.recyclerCollection.getContext(), LinearLayoutManager.HORIZONTAL, false);

            mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            CollectionAdapter collectionAdapter = new CollectionAdapter(item_list.get(position).getCollection());
            mListItemLiveProductsBinding.recyclerCollection.setLayoutManager(mLayoutManager);
            mListItemLiveProductsBinding.recyclerCollection.setAdapter(collectionAdapter);

            collectionAdapter.setListener(KitchenAdapter.this);


        }


    }*/


    public class CollectionViewHolder extends BaseViewHolder {
        ListItemCollectionsBinding mListItemLiveProductsBinding;


        public CollectionViewHolder(ListItemCollectionsBinding binding) {
            super(binding.getRoot());
            this.mListItemLiveProductsBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (item_list.get(position).getCollection().isEmpty()) return;

            mListItemLiveProductsBinding.executePendingBindings();


            LinearLayoutManager mLayoutManager = new LinearLayoutManager(mListItemLiveProductsBinding.recyclerCollection.getContext(), LinearLayoutManager.HORIZONTAL, false);

            mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            CollectionAdapter collectionAdapter = new CollectionAdapter(item_list.get(position).getCollection());
            mListItemLiveProductsBinding.recyclerCollection.setLayoutManager(mLayoutManager);
            mListItemLiveProductsBinding.recyclerCollection.setAdapter(collectionAdapter);

            collectionAdapter.setListener(KitchenAdapter.this);


        }


    }

    public class StoryCollectionViewHolder extends BaseViewHolder {
        ListItemStoryCollectionsBinding mListItemLiveProductsBinding;


        public StoryCollectionViewHolder(ListItemStoryCollectionsBinding binding) {
            super(binding.getRoot());
            this.mListItemLiveProductsBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (item_list.get(position) != null)
                if (item_list.get(position).getStory() != null)
                    if (item_list.get(position).getStory().isEmpty())
                        return;
            mListItemLiveProductsBinding.executePendingBindings();


            mListItemLiveProductsBinding.title.setText(item_list.get(position).getTitle());
            mListItemLiveProductsBinding.subTitle.setText(item_list.get(position).getSubtitle());



            LinearLayoutManager mLayoutManager = new LinearLayoutManager(mListItemLiveProductsBinding.recyclerStory.getContext(), LinearLayoutManager.HORIZONTAL, false);

            mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            InfinityStoriesAdapter storiesAdapter = new InfinityStoriesAdapter(item_list.get(position).getStory(),position);
            mListItemLiveProductsBinding.recyclerStory.setLayoutManager(mLayoutManager);
            mListItemLiveProductsBinding.recyclerStory.setAdapter(storiesAdapter);
            storiesAdapter.setListener(KitchenAdapter.this);


        }


    }
  public class RegionCollectionViewHolder extends BaseViewHolder {
        ListItemRegionCollectionsBinding mListItemLiveProductsBinding;


        public RegionCollectionViewHolder(ListItemRegionCollectionsBinding binding) {
            super(binding.getRoot());
            this.mListItemLiveProductsBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (item_list.get(position) != null)
                if (item_list.get(position).getCollectionDetails() != null)
                    if (item_list.get(position).getCollectionDetails().isEmpty())
                        return;
            mListItemLiveProductsBinding.executePendingBindings();


            mListItemLiveProductsBinding.title.setText(item_list.get(position).getTitle());
            mListItemLiveProductsBinding.subTitle.setText(item_list.get(position).getSubtitle());

            LinearLayoutManager mLayoutManager = new LinearLayoutManager(mListItemLiveProductsBinding.recyclerRegion.getContext(), LinearLayoutManager.HORIZONTAL, false);

            mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            InfinityRegionAdapter storiesAdapter = new InfinityRegionAdapter(item_list.get(position).getRegions());
            mListItemLiveProductsBinding.recyclerRegion.setLayoutManager(mLayoutManager);
            mListItemLiveProductsBinding.recyclerRegion.setAdapter(storiesAdapter);
            storiesAdapter.setListener(KitchenAdapter.this);

        }

    }

 public class KitchenCollectionViewHolder extends BaseViewHolder {
        ListItemKitchenCollectionsHolderBinding mListItemLiveProductsBinding;


        public KitchenCollectionViewHolder(ListItemKitchenCollectionsHolderBinding binding) {
            super(binding.getRoot());
            this.mListItemLiveProductsBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (item_list.get(position) != null)
                if (item_list.get(position).getCollectionDetails() != null)
                    if (item_list.get(position).getCollectionDetails().isEmpty())
                        return;
            mListItemLiveProductsBinding.executePendingBindings();


            mListItemLiveProductsBinding.title.setText(item_list.get(position).getTitle());
            mListItemLiveProductsBinding.subTitle.setText(item_list.get(position).getSubtitle());

            LinearLayoutManager mLayoutManager = new LinearLayoutManager(mListItemLiveProductsBinding.recyclerviewKitchens.getContext(), LinearLayoutManager.HORIZONTAL, false);

            mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            InfinityCollectionKitchensAdapter storiesAdapter = new InfinityCollectionKitchensAdapter(item_list.get(position).getCollectionDetails());
            mListItemLiveProductsBinding.recyclerviewKitchens.setLayoutManager(mLayoutManager);
            mListItemLiveProductsBinding.recyclerviewKitchens.setAdapter(storiesAdapter);
            storiesAdapter.setListener(KitchenAdapter.this);

        }

    }


    public class OffernViewHolder extends BaseViewHolder {
        ListItemHomeOffersBinding mListItemLiveProductsBinding;

        public OffernViewHolder(ListItemHomeOffersBinding binding) {
            super(binding.getRoot());
            this.mListItemLiveProductsBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (item_list.get(position).getCoupon().isEmpty()) return;
            mListItemLiveProductsBinding.executePendingBindings();

            LinearLayoutManager offerLayputManager = new LinearLayoutManager(mListItemLiveProductsBinding.recyclerCollection.getContext(), LinearLayoutManager.HORIZONTAL, false);
            offerLayputManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            OffersAdapter offersAdapter = new OffersAdapter(item_list.get(position).getCoupon());
            mListItemLiveProductsBinding.recyclerCollection.setLayoutManager(offerLayputManager);
            mListItemLiveProductsBinding.recyclerCollection.setAdapter(offersAdapter);

            offersAdapter.setListener(KitchenAdapter.this);


        }

    }

    public class EmptyArrayViewHolder extends BaseViewHolder {
        ListItemEmptyArrayBinding mListItemLiveProductsBinding;

        public EmptyArrayViewHolder(ListItemEmptyArrayBinding binding) {
            super(binding.getRoot());
            this.mListItemLiveProductsBinding = binding;
        }

        @Override
        public void onBind(int position) {
            mListItemLiveProductsBinding.executePendingBindings();
        }

    }


}
