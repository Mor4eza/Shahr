package com.ariana.shahre_ma.Bazarche;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter {
	private final int VIEW_ITEM = 1;
	private final int VIEW_PROG = 0;

	private List<Product_List_Item> productList;
	public static FieldClass fc=new FieldClass();
	// The minimum amount of items to have below your current scroll position
	// before loading more.
	private int visibleThreshold = 5;
	private int lastVisibleItem, totalItemCount;
	private boolean loading;
	private OnLoadMoreListener onLoadMoreListener;
	public static Context context;
	

	public DataAdapter(Context context, List<Product_List_Item> products, RecyclerView recyclerView) {
		super();
		productList = products;
		this.context=context;
		if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

			final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
					.getLayoutManager();


					recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
						@Override
						public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
							super.onScrolled(recyclerView, dx, dy);

							totalItemCount = linearLayoutManager.getItemCount();
							lastVisibleItem = linearLayoutManager
									.findLastVisibleItemPosition();
							if (!loading
									&& totalItemCount <= (lastVisibleItem + visibleThreshold)) {
								// End has been reached
								// Do something
								if (onLoadMoreListener != null) {
									onLoadMoreListener.onLoadMore();
								}
								loading = true;
							}
						}
					});
		}
	}

	@Override
	public int getItemViewType(int position) {
		return productList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		RecyclerView.ViewHolder vh;
		if (viewType == VIEW_ITEM) {
			View v = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.prudcut_list_card, parent, false);

			vh = new ProductViewHolder(v);
		} else {
			View v = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.progress_item, parent, false);

			vh = new ProgressViewHolder(v);
		}
		return vh;
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		if (holder instanceof ProductViewHolder) {

			Product_List_Item Product= (Product_List_Item) productList.get(position);

			((ProductViewHolder) holder).tvNature.setText(Product.getName());
			((ProductViewHolder) holder).tvDesNature.setText(Product.getmPrice().toString());
			((ProductViewHolder) holder).tvDesNature.setTag(Product.getId());
			((ProductViewHolder) holder).imgThumbnail.setImageResource(Product.getThumbnail());

			String image_url_1;
			image_url_1 = "http://www.shahrma.com/image/business/" + Product.getImageName();
			Picasso.with(context).load(image_url_1).placeholder(R.drawable.img_not_found).into(((ProductViewHolder) holder).imgThumbnail);


			((ProductViewHolder) holder).product= Product;

		} else {
			((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
		}
	}

	public void setLoaded() {
		loading = false;
	}

	@Override
	public int getItemCount() {
		return productList.size();
	}

	public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
		this.onLoadMoreListener = onLoadMoreListener;
	}


	//
	public static class ProductViewHolder extends RecyclerView.ViewHolder {
		public ImageView imgThumbnail;
		public TextView tvNature;
		public TextView tvDesNature;

		public Product_List_Item product;

		public ProductViewHolder(View v) {
			super(v);
			imgThumbnail = (ImageView)itemView.findViewById(R.id.img_product);
			tvNature = (TextView)itemView.findViewById(R.id.tv_title);
			tvDesNature = (TextView)itemView.findViewById(R.id.tv_price);
			itemView.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View v) {
					fc.SetProductId(Integer.valueOf(String.valueOf(tvDesNature.getTag())));
					Intent i=new Intent(context, product_Details.class);
					context.startActivity(i);

				}
			});
			imgThumbnail.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					fc.SetProductId(Integer.valueOf(String.valueOf(tvDesNature.getTag())));
					Intent i=new Intent(context, product_Details.class);
					context.startActivity(i);

				}
			});
		}
	}

	public static class ProgressViewHolder extends RecyclerView.ViewHolder {
		public ProgressBar progressBar;

		public ProgressViewHolder(View v) {
			super(v);
			progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
		}
	}
}