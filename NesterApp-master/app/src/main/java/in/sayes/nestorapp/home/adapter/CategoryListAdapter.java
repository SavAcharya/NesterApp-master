package in.sayes.nestorapp.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.typeface.custom.views.RobotoTextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.sayes.nestorapp.NestorApplication;
import in.sayes.nestorapp.R;

/**
 * Adapter to load categories
 *
 *Project : NesterApp , Package Name : in.sayes.nesterapp
 * Copyright : Sourav Bhattacharya eMail: sav.accharya@gmail.com
 */
public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder> {

    private Context context;

    private LayoutInflater inflater;

    private ImageLoader imageLoader;

    public CategoryListAdapter(Context context) {

        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        imageLoader = NestorApplication.getInstance().getImageLoader();
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.row_category, parent, false);

        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {



    }


    @Override
    public int getItemCount() {
        return 0;
    }


    static class CategoryViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.imgvCateory)
        NetworkImageView imgvCateory;

        @Bind(R.id.tvCategory)
        RobotoTextView tvCategory;

        public CategoryViewHolder(View itemView) {
            super(itemView);


            ButterKnife.bind(this, itemView);
        }
    }


}
