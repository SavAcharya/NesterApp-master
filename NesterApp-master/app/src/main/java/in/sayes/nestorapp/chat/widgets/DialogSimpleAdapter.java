package in.sayes.nestorapp.chat.widgets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import in.sayes.nestorapp.R;
import in.sayes.nestorapp.chat.helper.model.GSON_RootLevel;

public class DialogSimpleAdapter extends BaseAdapter {

  private LayoutInflater layoutInflater;
  private boolean isGrid;
    List<GSON_RootLevel.OptionsEntity> mOptions;

  public DialogSimpleAdapter(Context context, boolean isGrid, List<GSON_RootLevel.OptionsEntity> options, String inputFromType) {
    layoutInflater = LayoutInflater.from(context);
    this.isGrid = isGrid;
      this.mOptions=options;
  }

  @Override
  public int getCount() {
    return mOptions.size();
  }

  @Override
  public Object getItem(int position) {
    return position;
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder viewHolder;
    View view = convertView;

    if (view == null) {
      if (isGrid) {
        view = layoutInflater.inflate(R.layout.dialog_simple_grid_item, parent, false);
      } else {
        view = layoutInflater.inflate(R.layout.dialog_simple_list_item, parent, false);
      }

      viewHolder = new ViewHolder();
      viewHolder.textView = (TextView) view.findViewById(R.id.text_view);
      viewHolder.imageView = (ImageView) view.findViewById(R.id.image_view);
      view.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolder) view.getTag();
    }



        viewHolder.textView.setText(mOptions.get(position).getValue());


    return view;
  }

  static class ViewHolder {
    TextView textView;
    ImageView imageView;
  }
}
