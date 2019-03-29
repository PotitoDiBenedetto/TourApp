package com.dibenedetto.potito.tourapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dibenedetto.potito.tourapp.R;
import com.dibenedetto.potito.tourapp.ViewModels.LocationsViewModel;
import com.dibenedetto.potito.tourapp.db.LocationDAO;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//import androidx.appcompat.app.AppCompatActivity;
import com.dibenedetto.potito.tourapp.ui.AddressTextView;
import com.dibenedetto.potito.tourapp.util.ViewUtility;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

public class AllLocationsFragment extends Fragment {

    /**
     *
     */
    private LocationsViewModel mViewModel;

    /**
     * The Reference of the OnCategoryItemSelectedListener if any
     */
    private WeakReference<OnLocationItemSelectedListener> mOnLocationItemSelectedListenerRef;

    /**
     * The Category Model
     */
    private List<LocationDAO.LocationWithCategory> mModel = new LinkedList<>();

    /**
     * The RecyclerView
     */
    private RecyclerView mRecyclerView;

    /**
     * the adapter
     */
    private LocationAdapter mAdapter;

    /**
     * Number of columns for the grid layout
     */
    //private static final int COLS_NUMBER = 2;

    /**
     * The Adapter for the CategoryItem model
     */
    private LocationAdapter mLocationAdapter;

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
     * This is the ViewHolder to manage item for the CategoryItem
     */
    public final static class ItemViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private ImageView mImage;
        private TextView mText;
        private TextView mCategory;
        private AddressTextView mAddress;

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


            // We register the listener for the onClick
            itemView.setOnClickListener(AllLocationsFragment.ItemViewHolder.this);
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
        }

        public void bindEmpty() {
            mImage.setImageResource(R.mipmap.ic_tourapp_round);
            mText.setText("loading");
            mAddress.setText("loading");
            mCategory.setText("loading");
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
              .inflate(R.layout.location_row, viewGroup, false);
      return new ItemViewHolder(layout);


        }

        @Override
        public void onBindViewHolder(ItemViewHolder itemViewHolder, int position) {

            if(mModel != null) {
                itemViewHolder.bind(mModel.get(position));
            } else {
                itemViewHolder.bindEmpty();
            }
                itemViewHolder.setOnItemClickListener(AllLocationsFragment.LocationAdapter.this);

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


    /**
     * static factory method
     * @return
     */
    public static AllLocationsFragment newInstance() {
        return new AllLocationsFragment();
    }

    /**
     * default constructor
     */
    public AllLocationsFragment() {}

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
        mViewModel = ViewModelProviders.of(this).get(LocationsViewModel.class);
        mViewModel.getAllLocationsWithCategories().observe(this, new Observer<List<LocationDAO.LocationWithCategory>>() {
            @Override
            public void onChanged(@Nullable final List<LocationDAO.LocationWithCategory> locations) {
                // Update the cached copy of the words in the adapter.
                mAdapter.setLocations(locations);
            }
        });

        mModel = mViewModel.getAllLocationsWithCategories().getValue();

        // The adapter
        mAdapter = new LocationAdapter(mModel);
        mAdapter.setOnLocationItemListener(new LocationAdapter.OnLocationItemListener() {
            @Override
            public void onLocationItemClicked(LocationDAO.LocationWithCategory loc, int position) {




                //TODO: call the proper fragment




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

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);

        setRetainInstance(true);

        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
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
