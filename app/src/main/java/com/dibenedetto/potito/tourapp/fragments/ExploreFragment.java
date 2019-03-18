package com.dibenedetto.potito.tourapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dibenedetto.potito.tourapp.R;
import com.dibenedetto.potito.tourapp.model.CategoryItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//import androidx.appcompat.app.AppCompatActivity;
import com.dibenedetto.potito.tourapp.util.ViewUtility;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

public class ExploreFragment extends Fragment {

    /**
     *
     */
    private ExploreViewModel mViewModel;

    /**
     * The Reference of the OnCategoryItemSelectedListener if any
     */
    private WeakReference<OnCategoryItemSelectedListener> mOnCategoryItemSelectedListenerRef;

    /**
     * The Model
     */
    private List<CategoryItem> mModel = new LinkedList<>();

    /**
     * The RecyclerView
     */
    private RecyclerView mRecyclerView;

    /**
     * Number of columns for the grid layout
     */
    private static final int COLS_NUMBER = 2;

    /**
     * The Adapter for the CategoryItem model
     */
    private CategoryAdapter mAdapter;

    /**
     *
     */
    public interface OnCategoryItemSelectedListener {

        /**
         * Invoked when we select a catItem
         *
         * @param catItem The selected catItem
         */
        void onCategoryItemSelected(CategoryItem catItem);
    }

    /**
     * This is the ViewHolder to manage item for the CategoryItem
     */
    public final static class ItemViewHolder extends RecyclerView.ViewHolder
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
        public ItemViewHolder(View itemView) {
            super(itemView);
            mImage = ViewUtility.findViewById(itemView, R.id.category_image);
            mText = ViewUtility.findViewById(itemView, R.id.category_text);

            // We register the listener for the onClick
            itemView.setOnClickListener(ExploreFragment.ItemViewHolder.this);
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
         * @param catItem The model to map
         */
        public void bind(CategoryItem catItem) {
            mImage.setImageResource(catItem.mImage);
            mText.setText(catItem.mText);
        }

        @Override
        public void onClick(View v) {

            OnItemClickListener listener;
            if (mOnItemClickListenerRef != null && (listener = mOnItemClickListenerRef.get()) != null) {

                listener.onItemClicked(getLayoutPosition());
            }
        }

    }

    /**
     * This is the Adapter that has the responsibility to access the model, create the ViewHolder
     * and bind data
     */
    public final static class CategoryAdapter extends RecyclerView.Adapter<ItemViewHolder>
            implements ItemViewHolder.OnItemClickListener {

        /**
         * The model for the Adapter
         */
        private final List<CategoryItem> mModel;

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
             * Invoked when we select a category item
             *
             * @param catItem  The selected CategoryItem
             * @param position The selected position
             */
            void onCategoryItemClicked(CategoryItem catItem, int position);

        }

        /**
         * Creates a CategoryItemAdapter for the given model
         *
         * @param model The model for this Adapter
         */
        CategoryAdapter(final List<CategoryItem> model) {
            this.mModel = model;
        }

        /**
         * We use this to register a specific OnCategoryItemListener
         *
         * @param onCategoryItemListener The OnCategoryItemListener
         */
        public void setOnCategoryItemListener(final OnCategoryItemListener onCategoryItemListener) {
            this.mOnCategoryItemListenerRef = new WeakReference<OnCategoryItemListener>(onCategoryItemListener);
            this.mOnCategoryItemListener = onCategoryItemListener;
        }

        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // We inflate the data and return the related ViewHolder

            //for a single type of row
      final View layout = LayoutInflater.from(viewGroup.getContext())
              .inflate(R.layout.card_view_item_layout, viewGroup, false);
      return new ItemViewHolder(layout);


        }

        @Override
        public void onBindViewHolder(ItemViewHolder itemViewHolder, int position) {
            itemViewHolder.bind(mModel.get(position));
            itemViewHolder.setOnItemClickListener(ExploreFragment.CategoryAdapter.this);
        }

        @Override
        public int getItemCount() {
            return mModel.size();
        }


        @Override
        public void onItemClicked(int position) {

            OnCategoryItemListener listener;
            this.mOnCategoryItemListener.onCategoryItemClicked(mModel.get(position), position);
            //}
        }
    }


    /**
     * static factory method
     * @return
     */
    public static ExploreFragment newInstance() {
        return new ExploreFragment();
    }

    /**
     * default constructor
     */
    public ExploreFragment() {}

    /**
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCategoryItemSelectedListener) {
            final OnCategoryItemSelectedListener listener = (OnCategoryItemSelectedListener) context;
            mOnCategoryItemSelectedListenerRef = new WeakReference<OnCategoryItemSelectedListener>(listener);
        }

    }

    /**
     *
     */
    @Override
    public void onDetach() {
        super.onDetach();
        if (mOnCategoryItemSelectedListenerRef != null) {
            mOnCategoryItemSelectedListenerRef.clear();
            mOnCategoryItemSelectedListenerRef = null;
            //mActivity = null;

        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createModel();
        //setHasOptionsMenu(true);
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
        setRetainInstance(true);

        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), COLS_NUMBER,
                GridLayoutManager.HORIZONTAL, false);

        if(savedInstance == null) {
            layoutManager.scrollToPosition(0);
        }

        mRecyclerView.setLayoutManager(layoutManager);


        // The adapter
        mAdapter = new CategoryAdapter(mModel);
        mAdapter.setOnCategoryItemListener(new CategoryAdapter.OnCategoryItemListener() {
            @Override
            public void onCategoryItemClicked(CategoryItem catItem, int position) {




                //TODO: call the proper fragment




            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_main, container, false);

        mRecyclerView = ViewUtility.findViewById(layout, R.id.main_recycler_view);
        return layout;
    }

    /**
     * Utility method that creates the CategoryModel
     */
    private void createModel() {
        mModel.clear();

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
         * @param catItem The selected category item
         */ /*
        void onCardItemSelected(CategoryItem catItem);

    }
    */

}
