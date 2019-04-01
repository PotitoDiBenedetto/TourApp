package com.dibenedetto.potito.temp.exp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.dibenedetto.potito.tourapp.R;
import com.dibenedetto.potito.tourapp.db.CategoriaSecondaria;
import com.dibenedetto.potito.tourapp.db.LocationDAO;
import com.dibenedetto.potito.tourapp.ui.AddressTextView;
import com.dibenedetto.potito.tourapp.util.ViewUtility;
import com.dibenedetto.potito.tourapp.viewmodels.InfopointsViewModel;

import java.lang.ref.WeakReference;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

//import androidx.appcompat.app.AppCompatActivity;

public class InfopointsFragment extends Fragment {

    /**
     *
     */
    private InfopointsViewModel mViewModel;

    /**
     * The Reference of the OnCategoryItemSelectedListener if any
     */
    private WeakReference<OnLocationItemSelectedListener> mOnLocationItemSelectedListenerRef;

    /**
     * The Category Model
     */
    //private List<LocationDAO.LocationWithCategory> mModel = new LinkedList<>();

    /**
     * The RecyclerView
     */
    private RecyclerView mRecyclerView;


    /**
     * the adapter for the category
     */
    private CategoryAdapter mCategoryAdapter;


    /**
     * Number of columns for the grid layout
     */
    //private static final int COLS_NUMBER = 2;

    /**
     * The Adapter for the locations model
     */
    private LocationAdapter mLocationAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    /**
     *
     */
    public interface OnLocationItemSelectedListener {

        /**
         * Invoked when we select a catItem
         *
         * @param locItem The selected catItem
         */
        void onLocationItemSelected(LocationDAO.LocationWithCategory locItem);
    }

    /**
     *
     */
    public interface OnCAtegoryItemSelectedListener {

        /**
         * Invoked when we select a catItem
         *
         * @param catItem The selected catItem
         */
        void onLocationItemSelected(CategoriaSecondaria catItem);
    }


    /*
     ************** ViewHOlder and Adapter for the location items  ******************
     */


    /**
     * This is the ViewHolder to manage item for the CategoryItem
     */
    public final static class ItemViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private ImageView mImage;
        private TextView mText;
        private TextView mCategory;
        private AddressTextView mAddress;
        private TextView mDetail;
        private TextView mTime;
        private TextView mCost;
        private TextView mPhone;
        private TextView mWebsite;
        private TextView mEmail;

        private WeakReference<OnItemClickListener> mOnItemClickListenerRef;

        /**
         * This is the interface to implement to listen to the click on the item
         */
        public interface OnItemClickListener {

            /**
             * Invoked when we select a category item
             *
             * @param position The selected position
             */
            void onItemClicked(int position);

        }

        /**
         *
         * @param itemView
         */
        public ItemViewHolder(View itemView) {
            super(itemView);
            mImage = ViewUtility.findViewById(itemView, R.id.category_icon);
            mText = ViewUtility.findViewById(itemView, R.id.location_name);
            mAddress = ViewUtility.findViewById(itemView, R.id.location_address_view);
            mCategory = ViewUtility.findViewById(itemView, R.id.category);
            mDetail = ViewUtility.findViewById(itemView, R.id.dettaglio);
            mTime = ViewUtility.findViewById(itemView, R.id.orari);
            mCost = ViewUtility.findViewById(itemView, R.id.costo);
            mPhone = ViewUtility.findViewById(itemView, R.id.telefono);
            mWebsite = ViewUtility.findViewById(itemView, R.id.website);
            mEmail = ViewUtility.findViewById(itemView, R.id.email);


            // We register the listener for the onClick
            itemView.setOnClickListener(InfopointsFragment.ItemViewHolder.this);
        }

        /**
         * We use this to register a specific OnItemClickListener
         *
         * @param onItemClickListener The OnItemClickListener
         */
        public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
            this.mOnItemClickListenerRef = new WeakReference<OnItemClickListener>(onItemClickListener);
        }

        /**
         * This binds the model to the View
         *
         * @param loc The model to map
         */
        public void bind(LocationDAO.LocationWithCategory loc) {
            mImage.setImageResource(getCategoryIconId(loc));
            mText.setText(loc.location.nome_location);
            mAddress.setText(loc.location.indirizzo);
            mCategory.setText(loc.categoriaSecondaria.nome_categoria_secondaria);
            mDetail.setText(loc.location.dettaglio);
            mTime.setText(loc.location.orari);
            mCost.setText(String.valueOf(loc.location.costo));
            mPhone.setText(String.valueOf(loc.location.telefono));
            mWebsite.setText(loc.location.web_url);
            mEmail.setText(loc.location.email);
        }

        public void bindEmpty() {
            mImage.setImageResource(R.mipmap.ic_tourapp_round);
            mText.setText("loading");
            mAddress.setText("loading");
            mCategory.setText("loading");
            mDetail.setText("loading");
            mTime.setText("loading");
            mCost.setText("loading");
            mPhone.setText("loading");
            mWebsite.setText("loading");
            mEmail.setText("loading");
        }

        @Override
        public void onClick(View v) {

            OnItemClickListener listener;
            if (mOnItemClickListenerRef != null && (listener = mOnItemClickListenerRef.get()) != null) {

                listener.onItemClicked(getLayoutPosition());
            }
        }

        int getCategoryIconId(LocationDAO.LocationWithCategory loc) {
            int id=0;
            switch(loc.categoriaPrimaria._id_categoria_primaria) {
                case 1:
                    id = R.mipmap.ic_resturant;
                    break;
                case 2:
                    id = R.mipmap.ic_hotels;
                    break;
                case 3:
                    id = R.mipmap.ic_walks;
                    break;
                case 4:
                    id = R.mipmap.ic_info;
                    break;
            }
            return id;
        }

    }

    /**
     * This is the Adapter that has the responsibility to access the model, create the ViewHolder
     * and bind data
     */
    public final static class LocationAdapter extends RecyclerView.Adapter<ItemViewHolder>
            implements ItemViewHolder.OnItemClickListener {

        /**
         * The model for the Adapter
         */
        private List<LocationDAO.LocationWithCategory> mModel;

        /**
         * The Reference to the Listener for CategoryItem selection
         */
        private WeakReference<OnLocationItemListener> mOnLocationItemListenerRef;

        private OnLocationItemListener mOnLocationItemListener;

        /**
         * This is the interface to implement to listen to the click on the item
         */
        public interface OnLocationItemListener {

            /**
             * Invoked when we select a Location item
             *
             * @param loc  The selected LocationItem
             * @param position The selected position
             */
            void onLocationItemClicked(LocationDAO.LocationWithCategory loc, int position);

        }

        /**
         * Creates a LocationItemAdapter for the given model
         *
         * @param model The model for this Adapter
         */
        LocationAdapter(final List<LocationDAO.LocationWithCategory> model) {
            this.mModel = model;
        }

        /**
         * We use this to register a specific OnLocationItemListener
         *
         * @param onLocationItemListener The OnLocationItemListener
         */
        public void setOnLocationItemListener(final OnLocationItemListener onLocationItemListener) {
            this.mOnLocationItemListenerRef = new WeakReference<OnLocationItemListener>(onLocationItemListener);
            this.mOnLocationItemListener = onLocationItemListener;
        }

        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // We inflate the data and return the related ViewHolder

            //for a single type of row
            final View layout = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.location_row_2, viewGroup, false);
            return new ItemViewHolder(layout);

        }

        @Override
        public void onBindViewHolder(ItemViewHolder itemViewHolder, int position) {

            if(mModel != null) {
                itemViewHolder.bind(mModel.get(position));
            } else {
                itemViewHolder.bindEmpty();
            }
            itemViewHolder.setOnItemClickListener(InfopointsFragment.LocationAdapter.this);

        }

        @Override
        public int getItemCount() {

            if (mModel != null)
                return mModel.size();
            else return 0;

        }


        @Override
        public void onItemClicked(int position) {

            OnLocationItemListener listener;
            this.mOnLocationItemListener.onLocationItemClicked(mModel.get(position), position);
        }

        void setLocations(List<LocationDAO.LocationWithCategory> locations){
            mModel = locations;
            notifyDataSetChanged();
        }
    }




    /*
     **********************  ADAPTER AND VIEWHOLDER FOR THE SUBCATEGORIES  ******************************
     */



    /**
     * This is the ViewHolder to manage item for the CategoryItem
     */
    public final static class CategoryViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private ImageView mImage;
        private TextView mText;

        private WeakReference<OnItemClickListener> mOnItemClickListenerRef;

        /**
         * This is the interface to implement to listen to the click on the item
         */
        public interface OnItemClickListener {

            /**
             * Invoked when we select a category item
             *
             * @param position The selected position
             */
            void onItemClicked(int position);

        }

        /**
         *
         * @param itemView
         */
        public CategoryViewHolder(View itemView) {
            super(itemView);
            mImage = ViewUtility.findViewById(itemView, R.id.category_icon);
            mText = ViewUtility.findViewById(itemView, R.id.category_name);


            // We register the listener for the onClick
            itemView.setOnClickListener(InfopointsFragment.CategoryViewHolder.this);
        }

        /**
         * We use this to register a specific OnItemClickListener
         *
         * @param onItemClickListener The OnItemClickListener
         */
        public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
            this.mOnItemClickListenerRef = new WeakReference<OnItemClickListener>(onItemClickListener);
        }

        /**
         * This binds the model to the View
         *
         * @param cat The model to map
         */
        public void bind(CategoriaSecondaria cat) {
            mImage.setImageResource(getCategoryIconId(cat));
            mText.setText(cat.nome_categoria_secondaria);

        }

        public void bindEmpty() {
            mImage.setImageResource(R.mipmap.ic_tourapp_round);
            mText.setText("loading");

        }

        @Override
        public void onClick(View v) {

            OnItemClickListener listener;
            if (mOnItemClickListenerRef != null && (listener = mOnItemClickListenerRef.get()) != null) {

                listener.onItemClicked(getLayoutPosition());
            }
        }

        int getCategoryIconId(CategoriaSecondaria cat) {
            int id=0;
            switch(cat.categoria_primaria) {
                case 1:
                    id = R.mipmap.ic_resturant;
                    break;
                case 2:
                    id = R.mipmap.ic_hotels;
                    break;
                case 3:
                    id = R.mipmap.ic_walks;
                    break;
                case 4:
                    id = R.mipmap.ic_info;
                    break;
            }
            return id;
        }

    }

    /**
     * This is the Adapter that has the responsibility to access the model, create the ViewHolder
     * and bind data
     */
    public final static class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder>
            implements CategoryViewHolder.OnItemClickListener {

        /**
         * The model for the Adapter
         */
        private List<CategoriaSecondaria> mModel;

        /**
         * The Reference to the Listener for CategoryItem selection
         */
        private WeakReference<OnCategoryItemListener> mOnCategoryItemListenerRef;

        private OnCategoryItemListener mOnCategoryItemListener;

        /**
         * This is the interface to implement to listen to the click on the item
         */
        public interface OnCategoryItemListener {

            /**
             * Invoked when we select a Location item
             *
             * @param cat The selected LocationItem
             * @param position The selected position
             */
            void onCategoryItemClicked(CategoriaSecondaria cat, int position);

        }

        /**
         * Creates a LocationItemAdapter for the given model
         *
         * @param model The model for this Adapter
         */
        CategoryAdapter(final List<CategoriaSecondaria> model) {
            this.mModel = model;
        }

        /**
         * We use this to register a specific OnLocationItemListener
         *
         * @param onCategoryItemListener The OnLocationItemListener
         */
        public void setOnCategoryItemListener(final OnCategoryItemListener onCategoryItemListener) {
            this.mOnCategoryItemListenerRef = new WeakReference<OnCategoryItemListener>(onCategoryItemListener);
            this.mOnCategoryItemListener = onCategoryItemListener;
        }

        @Override
        public CategoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // We inflate the data and return the related ViewHolder

            //for a single type of row
            final View layout = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.category_row_2, viewGroup, false);

            return new CategoryViewHolder(layout);
        }

        @Override
        public void onBindViewHolder(CategoryViewHolder categoryViewHolder, int position) {

            if(mModel != null) {
                categoryViewHolder.bind(mModel.get(position));
            } else {
                categoryViewHolder.bindEmpty();
            }
            categoryViewHolder.setOnItemClickListener(InfopointsFragment.CategoryAdapter.this);

        }

        @Override
        public int getItemCount() {

            if (mModel != null)
                return mModel.size();
            else return 0;

        }


        @Override
        public void onItemClicked(int position) {

            OnCategoryItemListener listener;
            this.mOnCategoryItemListener.onCategoryItemClicked(mModel.get(position), position);
        }

        void setCategories(List<CategoriaSecondaria> categories){
            mModel = categories;
            notifyDataSetChanged();
        }
    }


    /*
     *++++++++++++++++++++END of ViewHOlder and Adapters+++++++++++++++++++++++++++++++
     */



    /**
     * static factory method
     * @return
     */
    public static InfopointsFragment newInstance() {
        return new InfopointsFragment();
    }

    /**
     * default constructor
     */
    public InfopointsFragment() {}

    /**
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLocationItemSelectedListener) {
            final OnLocationItemSelectedListener listener = (OnLocationItemSelectedListener) context;
            mOnLocationItemSelectedListenerRef = new WeakReference<OnLocationItemSelectedListener>(listener);
        }

    }

    /**
     *
     */
    @Override
    public void onDetach() {
        super.onDetach();
        if (mOnLocationItemSelectedListenerRef != null) {
            mOnLocationItemSelectedListenerRef.clear();
            mOnLocationItemSelectedListenerRef = null;
            //mActivity = null;

        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(InfopointsViewModel.class);

        mViewModel.getInterests().observe(this, new Observer<List<CategoriaSecondaria>>() {
            @Override
            public void onChanged(@Nullable final List<CategoriaSecondaria> categories) {
                // Update the cached copy of the words in the adapter.
                mCategoryAdapter.setCategories(categories);
            }
        });

        // The category adapter
        mCategoryAdapter = new CategoryAdapter(mViewModel.getInterests().getValue());
        mCategoryAdapter.setOnCategoryItemListener(new CategoryAdapter.OnCategoryItemListener() {

            @Override
            public void onCategoryItemClicked(CategoriaSecondaria cat, int position) {

                RecyclerView innerRecyclerView = ViewUtility.findViewById(
                        mRecyclerView.findViewHolderForLayoutPosition(position).itemView, R.id.inner_recycler_view);

                if (!(innerRecyclerView.getVisibility() == View.VISIBLE)) {

                //carica i dati dal DB
                final LiveData<List<LocationDAO.LocationWithCategory>> liveData =
                        mViewModel.getLocationsOfSubCategory(cat._id_categoria_secondaria);
                final List<LocationDAO.LocationWithCategory> model = liveData.getValue();

                //crea l'adapter per le location
                final LocationAdapter innerLocationAdapter = new LocationAdapter(model);

                //registra l'observer per i cambamenti dinamici dei dati
                liveData.observe(InfopointsFragment.this, new Observer<List<LocationDAO.LocationWithCategory>>() {
                    @Override
                    public void onChanged(@Nullable final List<LocationDAO.LocationWithCategory> locations) {
                        // Update the cached copy of the words in the adapter.
                        innerLocationAdapter.setLocations(locations);
                    }
                });

                //cambia-setta l'adapter della recyclerview

                final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                layoutManager.setOrientation(RecyclerView.VERTICAL);
                innerRecyclerView.setLayoutManager(layoutManager);
                innerRecyclerView.setAdapter(innerLocationAdapter);

                //setta il listner per il click
                innerLocationAdapter.setOnLocationItemListener(new LocationAdapter.OnLocationItemListener() {
                    @Override
                    public void onLocationItemClicked(LocationDAO.LocationWithCategory loc, int position) {


                        TableLayout tableView = ViewUtility.findViewById(
                                mRecyclerView.getLayoutManager().findViewByPosition(position), R.id.tablelayout);
                        TextView address = ViewUtility.findViewById(
                                mRecyclerView.findViewHolderForLayoutPosition(position).itemView, R.id.location_address_view);
                        View divider = ViewUtility.findViewById(
                                mRecyclerView.findViewHolderForLayoutPosition(position).itemView, R.id.divider);
                        address.setMaxLines(3);

                        if (tableView.getVisibility() == View.GONE) {
                            tableView.setVisibility(View.VISIBLE);
                            divider.setVisibility(View.VISIBLE);
                        } else {
                            tableView.setVisibility(View.GONE);
                            divider.setVisibility(View.GONE);
                            address.setMaxLines(1);
                        }


                    }
                });

                innerRecyclerView.getAdapter().notifyDataSetChanged();

                innerRecyclerView.setVisibility(View.VISIBLE);

                } else {
                    innerRecyclerView.setVisibility(View.GONE);
                }
            }
        });


        //setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    /*
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_exlorer, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    boolean result;
    switch (item.getItemId()) {
      case android.R.id.home:
        result = openDrawer();
        break;
      case R.id.action_something:
        //result = doSomething();
        break;
      default:
        return super.onOptionsItemSelected(item);
    }
    return result;
  }
*/

    @Override
    public void onActivityCreated(Bundle savedInstance){
        super.onActivityCreated(savedInstance);

        //((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        setRetainInstance(true);

    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_explore, container, false);

        mRecyclerView = ViewUtility.findViewById(layout, R.id.main_recycler_view);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        if(savedInstanceState == null) {
            layoutManager.scrollToPosition(0);
        }

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setAdapter(mCategoryAdapter);
        //mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
                ((TextView)ViewUtility.findViewById(holder.itemView, R.id.location_address_view)).setMaxLines(1);
                ViewUtility.findViewById(holder.itemView, R.id.tablelayout).setVisibility(View.GONE);
                ViewUtility.findViewById(holder.itemView, R.id.divider).setVisibility(View.GONE);
            }
        });

        mSwipeRefreshLayout = ViewUtility.findViewById(layout, R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mRecyclerView.setAdapter(mCategoryAdapter);
                mRecyclerView.getAdapter().notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        setRetainInstance(true);

        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();
        mCategoryAdapter.notifyDataSetChanged();
    }

    /*
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ExploreViewModel.class);
        // TODO: Use the ViewModel
    }
    */

    /**
     * Interface that the Activity should implement to be notified of an item selection
     */
    /*
    public interface OnCardItemSelectedListener {
        /**
         * Invoked when we select an item
         *
         * @param catItem The selected Location item
         */ /*
        void onCardItemSelected(LocationWithCategory loc);
    }
    */

}