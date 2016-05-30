package in.sayes.nestorapp.chat.widgets;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import in.sayes.android.customui.dialogplus.DialogPlus;
import in.sayes.android.customui.dialogplus.GridHolder;
import in.sayes.android.customui.dialogplus.Holder;
import in.sayes.android.customui.dialogplus.ListHolder;
import in.sayes.android.customui.dialogplus.OnCancelListener;
import in.sayes.android.customui.dialogplus.OnClickListener;
import in.sayes.android.customui.dialogplus.OnDismissListener;
import in.sayes.android.customui.dialogplus.OnItemClickListener;
import in.sayes.android.customui.dialogplus.ViewHolder;
import in.sayes.nestorapp.R;
import in.sayes.nestorapp.chat.helper.model.GSON_RootLevel;


public class DialogMainActivity extends ActionBarActivity {

  private RadioGroup radioGroup;
  private CheckBox headerCheckBox;
  private CheckBox footerCheckBox;
  private CheckBox expandedCheckBox;
    private List<GSON_RootLevel.OptionsEntity> mOptions;

    @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.dialog_activity_main);

    Toolbar toolbar = (Toolbar) findViewById(R.id.activity_toolbar);
    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    actionBar.setTitle(getString(R.string.app_name));
        mOptions=new LinkedList<GSON_RootLevel.OptionsEntity>();
for(int i=0 ;i<10;i++){
    GSON_RootLevel.OptionsEntity option= new GSON_RootLevel.OptionsEntity();
    option.setValue(i+"  cjahkjchkjahckjhak");

    mOptions.add(option);
}



    radioGroup = (RadioGroup) findViewById(R.id.radio_group);
    headerCheckBox = (CheckBox) findViewById(R.id.header_check_box);
    footerCheckBox = (CheckBox) findViewById(R.id.footer_check_box);
    expandedCheckBox = (CheckBox) findViewById(R.id.expanded_check_box);

    findViewById(R.id.button_bottom).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        showDialog(
            radioGroup.getCheckedRadioButtonId(),
            Gravity.BOTTOM,
            headerCheckBox.isChecked(),
            footerCheckBox.isChecked(),
            expandedCheckBox.isChecked()
        );
      }
    });

    findViewById(R.id.button_center).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        showDialog(
            radioGroup.getCheckedRadioButtonId(),
            Gravity.CENTER,
            headerCheckBox.isChecked(),
            footerCheckBox.isChecked(),
            expandedCheckBox.isChecked()
        );
      }
    });

    findViewById(R.id.button_top).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        showDialog(
            radioGroup.getCheckedRadioButtonId(),
            Gravity.TOP,
            headerCheckBox.isChecked(),
            footerCheckBox.isChecked(),
            expandedCheckBox.isChecked()
        );
      }
    });

  /*  View contentView = getLayoutInflater().inflate(R.layout.dialog_content2, null);
    DialogPlus dialogPlus = DialogPlus.newDialog(this)
        .setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new String[]{"asdfa"}))
        .setCancelable(true)
        .setOnDismissListener(new OnDismissListener() {
          @Override
          public void onDismiss(DialogPlus dialog) {

          }
        })
        .setOnCancelListener(new OnCancelListener() {
          @Override
          public void onCancel(DialogPlus dialog) {

          }
        })
        .setOnBackPressListener(new OnBackPressListener() {
          @Override
          public void onBackPressed(DialogPlus dialogPlus) {

          }
        })
        .create();

    dialogPlus.show();*/
  }

  private void showDialog(int holderId, int gravity, boolean showHeader, boolean showFooter,
                          boolean expanded) {
    boolean isGrid;
    Holder holder;
    switch (holderId) {
      case R.id.basic_holder_radio_button:
        holder = new ViewHolder(R.layout.dialog_content);
        isGrid = false;
        break;
      case R.id.list_holder_radio_button:
        holder = new ListHolder();
        isGrid = false;
        break;
      default:
        holder = new GridHolder(2);
        isGrid = true;
    }

    OnClickListener clickListener = new OnClickListener() {
      @Override
      public void onClick(DialogPlus dialog, View view) {
        //        switch (view.getId()) {
        //          case R.id.header_container:
        //            Toast.makeText(MainActivity.this, "Header clicked", Toast.LENGTH_LONG).show();
        //            break;
        //          case R.id.like_it_button:
        //            Toast.makeText(MainActivity.this, "We're glad that you like it", Toast.LENGTH_LONG).show();
        //            break;
        //          case R.id.love_it_button:
        //            Toast.makeText(MainActivity.this, "We're glad that you love it", Toast.LENGTH_LONG).show();
        //            break;
        //          case R.id.footer_confirm_button:
        //            Toast.makeText(MainActivity.this, "Confirm button clicked", Toast.LENGTH_LONG).show();
        //            break;
        //          case R.id.footer_close_button:
        //            Toast.makeText(MainActivity.this, "Close button clicked", Toast.LENGTH_LONG).show();
        //            break;
        //        }
        //        dialog.dismiss();
      }
    };

    OnItemClickListener itemClickListener = new OnItemClickListener() {
      @Override
      public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
        TextView textView = (TextView) view.findViewById(R.id.text_view);
        String clickedAppName = textView.getText().toString();
        //        dialog.dismiss();
        //        Toast.makeText(MainActivity.this, clickedAppName + " clicked", Toast.LENGTH_LONG).show();
      }
    };

    OnDismissListener dismissListener = new OnDismissListener() {
      @Override
      public void onDismiss(DialogPlus dialog) {
        //        Toast.makeText(MainActivity.this, "dismiss listener invoked!", Toast.LENGTH_SHORT).show();
      }
    };

    OnCancelListener cancelListener = new OnCancelListener() {
      @Override
      public void onCancel(DialogPlus dialog) {
        //        Toast.makeText(MainActivity.this, "cancel listener invoked!", Toast.LENGTH_SHORT).show();
      }
    };

      String inputFromType="";
      DialogSimpleAdapter adapter = new DialogSimpleAdapter(DialogMainActivity.this, isGrid,mOptions, inputFromType);
    if (showHeader && showFooter) {
      showCompleteDialog(holder, gravity, adapter, clickListener, itemClickListener, dismissListener, cancelListener,
          expanded);
      return;
    }

    if (showHeader && !showFooter) {
      showNoFooterDialog(holder, gravity, adapter, clickListener, itemClickListener, dismissListener, cancelListener,
          expanded);
      return;
    }

    if (!showHeader && showFooter) {
      showNoHeaderDialog(holder, gravity, adapter, clickListener, itemClickListener, dismissListener, cancelListener,
          expanded);
      return;
    }

    showOnlyContentDialog(holder, gravity, adapter, itemClickListener, dismissListener, cancelListener, expanded);
  }

  private void showCompleteDialog(Holder holder, int gravity, BaseAdapter adapter,
                                  OnClickListener clickListener, OnItemClickListener itemClickListener,
                                  OnDismissListener dismissListener, OnCancelListener cancelListener,
                                  boolean expanded) {
    final DialogPlus dialog = DialogPlus.newDialog(this)
        .setContentHolder(holder)
        .setHeader(R.layout.dialog_header)
        .setFooter(R.layout.dialog_footer)
        .setCancelable(true)
        .setGravity(gravity)
        .setAdapter(adapter)
        .setOnClickListener(clickListener)
        .setOnItemClickListener(new OnItemClickListener() {
          @Override public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
            Log.d("DialogPlus", "onItemClick() called with: " + "item = [" +
                item + "], position = [" + position + "]");
              dialog.dismiss();
              showDialog(
                      radioGroup.getCheckedRadioButtonId(),
                      Gravity.BOTTOM,
                      headerCheckBox.isChecked(),
                      footerCheckBox.isChecked(),
                      expandedCheckBox.isChecked()
              );
          }
        })
        .setOnDismissListener(dismissListener)
        .setExpanded(expanded)
//        .setContentWidth(800)
        .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
        .setOnCancelListener(cancelListener)
        .setOverlayBackgroundResource(android.R.color.transparent)
//        .setContentBackgroundResource(R.drawable.corner_background)
            //                .setOutMostMargin(0, 100, 0, 0)
        .create();
    dialog.show();
  }

  private void showNoFooterDialog(Holder holder, int gravity, BaseAdapter adapter,
                                  OnClickListener clickListener, OnItemClickListener itemClickListener,
                                  OnDismissListener dismissListener, OnCancelListener cancelListener,
                                  boolean expanded) {
    final DialogPlus dialog = DialogPlus.newDialog(this)
        .setContentHolder(holder)
        .setHeader(R.layout.dialog_header)
        .setCancelable(true)
        .setGravity(gravity)
        .setAdapter(adapter)
        .setOnClickListener(clickListener)
        .setOnItemClickListener(new OnItemClickListener() {
          @Override public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
            Log.d("DialogPlus", "onItemClick() called with: " + "item = [" +
                item + "], position = [" + position + "]");
          }
        })
        .setOnDismissListener(dismissListener)
        .setOnCancelListener(cancelListener)
        .setExpanded(expanded)
        .create();
    dialog.show();
  }

  private void showNoHeaderDialog(Holder holder, int gravity, BaseAdapter adapter,
                                  OnClickListener clickListener, OnItemClickListener itemClickListener,
                                  OnDismissListener dismissListener, OnCancelListener cancelListener,
                                  boolean expanded) {
    final DialogPlus dialog = DialogPlus.newDialog(this)
        .setContentHolder(holder)
        .setFooter(R.layout.dialog_footer)
        .setCancelable(true)
        .setGravity(gravity)
        .setAdapter(adapter)
        .setOnClickListener(clickListener)
        .setOnItemClickListener(new OnItemClickListener() {
          @Override public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
            Log.d("DialogPlus", "onItemClick() called with: " + "item = [" +
                item + "], position = [" + position + "]");
          }
        })
        .setOnDismissListener(dismissListener)
        .setOnCancelListener(cancelListener)
        .setExpanded(expanded)
        .create();
    dialog.show();
  }

  private void showOnlyContentDialog(Holder holder, int gravity, BaseAdapter adapter,
                                     OnItemClickListener itemClickListener, OnDismissListener dismissListener,
                                     OnCancelListener cancelListener, boolean expanded) {
    final DialogPlus dialog = DialogPlus.newDialog(this)
        .setContentHolder(holder)
        .setGravity(gravity)
        .setAdapter(adapter)
        .setOnItemClickListener(new OnItemClickListener() {
          @Override public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
            Log.d("DialogPlus", "onItemClick() called with: " + "item = [" +
                item + "], position = [" + position + "]");
          }
        })
        .setOnDismissListener(dismissListener)
        .setOnCancelListener(cancelListener)
        .setExpanded(expanded)
        .setCancelable(true)
        .create();
    dialog.show();
  }








}
