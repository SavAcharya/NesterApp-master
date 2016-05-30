package in.sayes.nestorapp.chat.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import in.sayes.android.customui.dialogplus.DialogPlus;
import in.sayes.android.customui.dialogplus.GridHolder;
import in.sayes.android.customui.dialogplus.Holder;
import in.sayes.android.customui.dialogplus.ListHolder;
import in.sayes.android.customui.dialogplus.OnCancelListener;
import in.sayes.android.customui.dialogplus.OnClickListener;
import in.sayes.android.customui.dialogplus.OnDismissListener;
import in.sayes.android.customui.dialogplus.OnItemClickListener;
import in.sayes.nestorapp.NestorApplication;
import in.sayes.nestorapp.R;
import in.sayes.nestorapp.base.fragment.BaseFragment;
import in.sayes.nestorapp.chat.adapter.ChatListAdapter;
import in.sayes.nestorapp.chat.helper.AndroidUtilities;
import in.sayes.nestorapp.chat.helper.MessageEntity;
import in.sayes.nestorapp.chat.helper.MessageType;
import in.sayes.nestorapp.chat.helper.NotificationCenter;
import in.sayes.nestorapp.chat.helper.Status;
import in.sayes.nestorapp.chat.helper.UserType;
import in.sayes.nestorapp.chat.helper.model.GSON_RootLevel;
import in.sayes.nestorapp.chat.helper.model.InputFromType;
import in.sayes.nestorapp.chat.widgets.DialogSimpleAdapter;
import in.sayes.nestorapp.chat.widgets.SizeNotifierRelativeLayout;
import in.sayes.nestorapp.network.utils.NetworkUtils;
import in.sayes.nestorapp.storage.AppPreferences;
import in.sayes.nestorapp.storage.IPreferenceConstants;
import in.sayes.nestorapp.util.APIConstants;


public class ChatFragment extends BaseFragment implements SizeNotifierRelativeLayout.SizeNotifierRelativeLayoutDelegate,
        NotificationCenter.NotificationCenterDelegate, APIConstants {


    private final TextWatcher watcher1 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (chatEditText1.getText().toString().equals("")) {

            } else {
                enterChatView1.setImageResource(R.drawable.ic_chat_send);

            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.length() == 0) {
                enterChatView1.setImageResource(R.drawable.ic_chat_send);
            } else {
                enterChatView1.setImageResource(R.drawable.ic_chat_send_active);
            }
        }
    };

    Gson gson = new Gson();
    GSON_RootLevel mNestorReply;
    HashMap<String, String> userInput;
    private ArrayList<MessageEntity> chatMessages;
    private ImageView enterChatView1, emojiButton;
    private ChatListAdapter listAdapter;
    private SizeNotifierRelativeLayout sizeNotifierRelativeLayout;
    private int keyboardHeight;
    private boolean keyboardVisible;
    private WindowManager.LayoutParams windowLayoutParams;
    private AppPreferences appPreferences;
    private List<String> nestorStatements;
    private long MESSAGE_DELAY = 1000;


    private String TAG = "ChatFragment";
    private boolean isFirst = true;
    private LinearLayout userTextInputLayout;


    private List<GSON_RootLevel.QuestionList> allQuestionsList;
    private GSON_RootLevel.QuestionList currentQuestionsList;
    private GSON_RootLevel.QuestionsEntity currentQustion;

    private int questionsCount = 0;
    private int questionsLevelIndex = 0;

    private ListView chatListView;
    private EditText chatEditText1;
    private EditText.OnKeyListener keyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {

            // If the event is a key-down event on the "enter" button
            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                // Perform action on key press

                EditText editText = (EditText) v;

                if (v == chatEditText1) {
                    // sendMessage(editText.getText().toString(), UserType.NESTOR);
                }

                chatEditText1.setText("");

                return true;
            }
            return false;

        }
    };

    private ImageView.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (v == enterChatView1) {
                handleInput(0, InputFromType.NUMERIC_KEYBOARD);
                userTextInputLayout.setVisibility(View.GONE);
            }

            chatEditText1.setText("");

        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_chat, container, false);
        AndroidUtilities.statusBarHeight = getStatusBarHeight();

        setupView(root);


        // Start chat with initial conversation for first time.
        // get signup data and create Nestor reply object
        initChat(appPreferences.getString(IPreferenceConstants.PREF_SIGNUP_REPLY, ""));


        return root;
    }

    private void initVariables() {

    }

    private void setupView(View rootView) {
        chatMessages = new ArrayList<>();
        appPreferences = new AppPreferences(getActivity());
        userInput = new HashMap<String, String>();
        allQuestionsList = new ArrayList<>();

        chatListView = (ListView) rootView.findViewById(R.id.chat_list_view);

        chatEditText1 = (EditText) rootView.findViewById(R.id.chat_edit_text1);
        enterChatView1 = (ImageView) rootView.findViewById(R.id.send_chat_text);
        userTextInputLayout = (LinearLayout) rootView.findViewById(R.id.bottomlayout);

        listAdapter = new ChatListAdapter(chatMessages, getActivity());

        chatListView.setAdapter(listAdapter);

        chatEditText1.setOnKeyListener(keyListener);

        enterChatView1.setOnClickListener(clickListener);

        chatEditText1.addTextChangedListener(watcher1);
        userTextInputLayout.setVisibility(View.GONE);

        sizeNotifierRelativeLayout = (SizeNotifierRelativeLayout) rootView.findViewById(R.id.chat_layout);
        sizeNotifierRelativeLayout.delegate = this;


    }

    private void initChat(String string) {

        mNestorReply = gson.fromJson(string, GSON_RootLevel.class);

        currentQuestionsList =new GSON_RootLevel.QuestionList(mNestorReply.getQuestions(),0);


        allQuestionsList.add(currentQuestionsList);

        currentQustion= allQuestionsList.get(questionsLevelIndex).getQuestions().get(allQuestionsList.get(questionsLevelIndex).getQustionIndex());
        showNextQuestion();


    }

    private void showNextQuestion() {
    currentQuestionsList=allQuestionsList.get(allQuestionsList.size() - 1);

        /*if (currentQustion != null) {


            if (questionsCount < currentQuestionsList.getQuestions().size()) {
                currentQustion = allQuestionsList.get(questionsLevelIndex).getQuestions().get(questionsCount);

                printStatement(currentQustion);
            } else {
                // TODO make server call to send values and get next question
                requestForNewData();
            }

        } else*/ if (questionsCount < currentQuestionsList.getQuestions().size()) {

                currentQustion = currentQuestionsList.getQuestions().get(questionsCount);
                printStatement();
                questionsCount++;
            }
            else{
                //it's last question on the current list -

                // remove the latest question list
                allQuestionsList.remove(allQuestionsList.size()-1);

                if (!allQuestionsList.isEmpty()){
                    // get new list of questions
                    currentQuestionsList=allQuestionsList.get(allQuestionsList.size()-1);
                    questionsCount=currentQuestionsList.getQustionIndex();
                    // get question index if it was read before
                    if (questionsCount < currentQuestionsList.getQuestions().size()) {
                        currentQustion = currentQuestionsList.getQuestions().get(currentQuestionsList.getQustionIndex());
                        printStatement();
                        questionsCount++;
                    }else{
                        // TODO make server call to send values and get next question
                        requestForNewData();
                    }

                }else{

                    // TODO make server call to send values and get next question
                    requestForNewData();
                }

            }

    }

    private void printStatement() {


        nestorStatements = currentQustion.getStatements();

        for (int statementCount = 0; statementCount < nestorStatements.size(); statementCount++) {

            showChatMessage(nestorStatements.get(statementCount), UserType.NESTOR);

        }
        Runnable mMyRunnable = new Runnable() {
            @Override
            public void run() {
                setInputFrom();
            }
        };
        final Handler handler = new Handler();
        handler.postDelayed(mMyRunnable, 2000);

    }


    private void setInputFrom() {
        boolean isGrid;
        Holder holder;
        String inputFromType = currentQustion.getInput_form().getType();


        switch (inputFromType) {
            case InputFromType.LIST_INPUT:
                holder = new ListHolder();
                isGrid = false;

                showDialog(holder, isGrid, Gravity.BOTTOM, true, true, true);
                break;

            case InputFromType.CUSTOM_KEYBOARD:
                holder = new ListHolder();
                isGrid = false;

                showDialog(holder, isGrid, Gravity.BOTTOM, true, true, true);
                break;

            case InputFromType.FLOATING:
                holder = new ListHolder();
                isGrid = false;

                showDialog(holder, isGrid, Gravity.BOTTOM, true, true, true);
                break;


            case InputFromType.NUMERIC_KEYBOARD:
                userTextInputLayout.setVisibility(View.VISIBLE);
                chatEditText1.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                break;

            case InputFromType.SELECT_VAL:

                break;

            case InputFromType.GRID:
                holder = new GridHolder(2);
                isGrid = true;

                showDialog(holder, isGrid, Gravity.BOTTOM, true, true, true);


                break;

            case InputFromType.VIEWPAGER:
                holder = new GridHolder(2);
                isGrid = true;

                showDialog(holder, isGrid, Gravity.BOTTOM, true, true, true);


            default:

                break;
        }

    }


    private void showChatMessage(final String mMessage, final UserType userType) {
        Log.i("Nestor Message", mMessage);

        if (mMessage.trim().length() == 0)
            return;


        Runnable mMyRunnable = new Runnable() {
            @Override
            public void run() {
                final MessageEntity message = new MessageEntity();
                message.setMsgStatus(Status.DELIVERED);
                message.setMsgText(mMessage);
                message.setUserType(userType);
                if (currentQustion.getInput_form().getType().equalsIgnoreCase(InputFromType.IMAGE)){
                    message.setMessageType(MessageType.IMAGE);
                }else {
                    if (currentQustion.getSelect_type().equals("single")) {
                        message.setMessageType(MessageType.TEXT);
                    } else {
                        message.setMessageType(MessageType.COMBO);
                    }
                }
                message.setMessageTime(new Date().getTime());
                chatMessages.add(message);

                getActivity().runOnUiThread(new Runnable() {
                    public void run() {

                        listAdapter.notifyDataSetChanged();
                    }
                });
            }
        };
        final Handler handler = new Handler();
        handler.postDelayed(mMyRunnable, 2000);


    }

    @Override
    protected void getDataFromBundle() {

    }


    private void sendMessage(final String messageText, final UserType userType) {
        if (messageText.trim().length() == 0)
            return;

        final MessageEntity message = new MessageEntity();
        message.setMsgStatus(Status.SENT);
        message.setMsgText(messageText);
        message.setUserType(userType);
        message.setMessageTime(new Date().getTime());
        chatMessages.add(message);

        if (listAdapter != null)
            listAdapter.notifyDataSetChanged();

        // Mark message as delivered after one second

        final ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);

        exec.schedule(new Runnable() {
            @Override
            public void run() {
                message.setMsgStatus(Status.DELIVERED);

                final MessageEntity message = new MessageEntity();
                message.setMsgStatus(Status.SENT);
                message.setMsgText(messageText);
                message.setUserType(UserType.NESTOR);
                message.setMessageTime(new Date().getTime());
                chatMessages.add(message);

                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        listAdapter.notifyDataSetChanged();
                    }
                });


            }
        }, 2, TimeUnit.SECONDS);

    }


    @Override
    public void onSizeChanged(int height) {

        Rect localRect = new Rect();
        getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);

        WindowManager wm = (WindowManager) NestorApplication.getInstance().getSystemService(Activity.WINDOW_SERVICE);
        if (wm == null || wm.getDefaultDisplay() == null) {
            return;
        }


        if (height > AndroidUtilities.dp(50) && keyboardVisible) {
            keyboardHeight = height;
            NestorApplication.getInstance().getSharedPreferences("emoji", 0).edit().putInt("kbd_height", keyboardHeight).commit();
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();


    }

    /**
     * Get the system status bar height
     *
     * @return
     */
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    @Override
    public void didReceivedNotification(int id, Object... args) {

    }


    private void showDialog(Holder holder, boolean isGrid, int gravity, boolean showHeader,
                            boolean showFooter, boolean expanded) {


        final String inputFromType = currentQustion.getInput_form().getType();

        List<GSON_RootLevel.OptionsEntity> options = currentQustion.getOptions();
        DialogSimpleAdapter adapter = new DialogSimpleAdapter(getContext(), isGrid, options, inputFromType);

        OnClickListener clickListener = new OnClickListener() {
            @Override
            public void onClick(DialogPlus dialog, View view) {
                switch (view.getId()) {
                    //          case R.id.header_container:
                    //            Toast.makeText(MainActivity.this, "Header clicked", Toast.LENGTH_LONG).show();
                    //            break;
                    //          case R.id.like_it_button:
                    //            Toast.makeText(MainActivity.this, "We're glad that you like it", Toast.LENGTH_LONG).show();
                    //            break;
                    //          case R.id.love_it_button:
                    //            Toast.makeText(MainActivity.this, "We're glad that you love it", Toast.LENGTH_LONG).show();
                    //            break;
                    case R.id.footer_confirm_button:
                        Toast.makeText(getContext(), "Confirm button clicked", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.footer_close_button:
                        handleInput(1, inputFromType);
                        dialog.dismiss();
                        break;
                }
                dialog.dismiss();
            }
        };

        OnItemClickListener itemClickListener = new OnItemClickListener() {
            @Override
            public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                TextView textView = (TextView) view.findViewById(R.id.text_view);
                String clickedAppName = textView.getText().toString();
                //       dialog.dismiss();
                Toast.makeText(getContext(), position + "   " + clickedAppName + " clicked", Toast.LENGTH_LONG).show();

            }
        };

        OnDismissListener dismissListener = new OnDismissListener() {
            @Override
            public void onDismiss(DialogPlus dialog) {
                Toast.makeText(getContext(), "dismiss listener invoked!", Toast.LENGTH_SHORT).show();
            }
        };

        OnCancelListener cancelListener = new OnCancelListener() {
            @Override
            public void onCancel(DialogPlus dialog) {
                Toast.makeText(getContext(), "cancel listener invoked!", Toast.LENGTH_SHORT).show();
            }
        };


        if (showHeader && showFooter) {
            showCompleteDialog(holder, gravity, adapter, clickListener, itemClickListener, dismissListener, cancelListener,
                    expanded);
            return;
        }

        if (showHeader && !showFooter) {
            showNoFooterDialog(holder, gravity, adapter, clickListener, itemClickListener, dismissListener, cancelListener,
                    expanded, options);
            return;
        }

        if (!showHeader && showFooter) {
            showNoHeaderDialog(holder, gravity, adapter, clickListener, itemClickListener, dismissListener, cancelListener,
                    expanded, options);
            return;
        }

        showOnlyContentDialog(holder, gravity, adapter, itemClickListener, dismissListener, cancelListener, expanded);


    }


    private void showCompleteDialog(Holder holder, int gravity, BaseAdapter adapter,
                                    OnClickListener clickListener, OnItemClickListener itemClickListener,
                                    OnDismissListener dismissListener, OnCancelListener cancelListener,
                                    boolean expanded) {
        final String inputFromType = currentQustion.getInput_form().getType();
        final DialogPlus dialog = DialogPlus.newDialog(getContext())
                .setContentHolder(holder)
                .setHeader(R.layout.dialog_header)
                .setFooter(R.layout.dialog_footer)
                .setCancelable(true)
                .setGravity(gravity)
                .setAdapter(adapter)
                .setOnClickListener(clickListener)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, final int position) {
                        Log.d("DialogPlus", "onItemClick() called with: " + "item = [" +
                                item + "], position = [" + position + "]");
                        Toast.makeText(getContext(), position + "   " + item + " clicked", Toast.LENGTH_LONG).show();

                        dialog.dismiss();
                        Runnable mMyRunnable = new Runnable() {
                            @Override
                            public void run() {
                                handleInput(position, inputFromType);
                            }
                        };
                        final Handler handler = new Handler();
                        handler.postDelayed(mMyRunnable, 2000);


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
                                    boolean expanded, List<GSON_RootLevel.OptionsEntity> options) {
        final DialogPlus dialog = DialogPlus.newDialog(getContext())
                .setContentHolder(holder)
                .setHeader(R.layout.dialog_header)
                .setCancelable(true)
                .setGravity(gravity)
                .setAdapter(adapter)
                .setOnClickListener(clickListener)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
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
                                    boolean expanded, List<GSON_RootLevel.OptionsEntity> options) {
        final DialogPlus dialog = DialogPlus.newDialog(getContext())
                .setContentHolder(holder)
                .setFooter(R.layout.dialog_footer)
                .setCancelable(true)
                .setGravity(gravity)
                .setAdapter(adapter)
                .setOnClickListener(clickListener)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
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
        final DialogPlus dialog = DialogPlus.newDialog(getContext())
                .setContentHolder(holder)
                .setGravity(gravity)
                .setAdapter(adapter)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
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


    private void handleInput(int selectedInputPOsition, String inputFromType) {

        // TODO get the selected value and add to option selection map.
// TODO set the selected option on map


        boolean isGrid;
        Holder holder;

        switch (inputFromType) {
            case InputFromType.LIST_INPUT:

                showChatMessage(currentQustion.getOptions().get(selectedInputPOsition).getValue(), UserType.SELF);
                // check if name_var exist in response
                if (currentQustion.getName_var() != null) {
                    userInput.put(currentQustion.getName_var(), currentQustion.getOptions().get(selectedInputPOsition).getValue());
                }

                isFollowUp(selectedInputPOsition);

                break;

            case InputFromType.CUSTOM_KEYBOARD:
                showChatMessage(currentQustion.getOptions().get(selectedInputPOsition).getValue(), UserType.SELF);
                // check if name_var exist in response
                if (currentQustion.getName_var() != null) {
                    userInput.put(currentQustion.getName_var(), currentQustion.getOptions().get(selectedInputPOsition).getValue());
                }

                isFollowUp(selectedInputPOsition);
                break;

            case InputFromType.FLOATING:
                showChatMessage(currentQustion.getOptions().get(selectedInputPOsition).getValue(), UserType.SELF);
                // check if name_var exist in response
                if (currentQustion.getName_var() != null) {
                    userInput.put(currentQustion.getName_var(), currentQustion.getOptions().get(selectedInputPOsition).getValue());
                }

                isFollowUp(selectedInputPOsition);
                break;

            case InputFromType.NUMERIC_KEYBOARD:

                String s = chatEditText1.getText().toString();
                showChatMessage(s, UserType.SELF );
                userInput.put(currentQustion.getName_var(),s);
                isFollowUp(selectedInputPOsition);

                break;

            case InputFromType.SELECT_VAL:
                showChatMessage(currentQustion.getOptions().get(selectedInputPOsition).getValue(), UserType.SELF);
                // check if name_var exist in response
                if (currentQustion.getName_var() != null) {
                    userInput.put(currentQustion.getName_var(), currentQustion.getOptions().get(selectedInputPOsition).getValue());
                }

                isFollowUp(selectedInputPOsition);
                break;

            case InputFromType.GRID:
                showChatMessage(currentQustion.getOptions().get(selectedInputPOsition).getValue(), UserType.SELF);
                // check if name_var exist in response
                if (currentQustion.getName_var() != null) {
                    userInput.put(currentQustion.getName_var(), currentQustion.getOptions().get(selectedInputPOsition).getValue());
                }

                isFollowUp(selectedInputPOsition);

                break;

            case InputFromType.VIEWPAGER:

                showChatMessage(currentQustion.getOptions().get(selectedInputPOsition).getValue(), UserType.SELF);
                // check if name_var exist in response
                if (currentQustion.getName_var() != null) {
                    userInput.put(currentQustion.getName_var(), currentQustion.getOptions().get(selectedInputPOsition).getValue());
                }

               isFollowUp(selectedInputPOsition);

                break;

            default:

                break;
        }

    }

    private Boolean isFollowUp(int selectedInputPOsition) {

            if (currentQustion.getOptions().size() > 0 ) {
                if (currentQustion.getOptions().get(selectedInputPOsition).getFollowup() != null) {

                   allQuestionsList.get(allQuestionsList.size() - 1).setQustionIndex(questionsCount);
                    // TODO show followup questions -  set currentQustion to followup question
                    GSON_RootLevel.QuestionList questionList = new GSON_RootLevel.QuestionList(currentQustion.getOptions().get(selectedInputPOsition).getFollowup(), 0);

                    allQuestionsList.add(questionList);
                    // reset question count as new followup questions added
                    questionsCount = 0;
                    showNextQuestion();
                } else {
                    showNextQuestion();
                }
            }else{
                showNextQuestion();
            }
        return true;
    }


    private String requestForNewData() {
        String response = "";

        String url = BASE_URL + mNestorReply.getNext_endpoint();

        final ProgressDialog pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Loading...");
        pDialog.show();
        userInput.put(APIConstants.USERID_URL_PARAM,mNestorReply.getUser_id());
        String urlKeys = NetworkUtils.getURLKeyParameters(userInput);

        if (!urlKeys.isEmpty()) {
            url = url + urlKeys;
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        Gson gson = new Gson();
                        mNestorReply = gson.fromJson(response.toString(), GSON_RootLevel.class);
                        GSON_RootLevel.QuestionList questionList =new GSON_RootLevel.QuestionList( mNestorReply.getQuestions(),0);
                        allQuestionsList.add(questionList);
                        questionsCount = 0;
                        showNextQuestion();
                        // remove all previous data
                        userInput.clear();
                        pDialog.hide();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                // hide the progress dialog
                pDialog.hide();
            }
        });

// Adding request to request queue
        NestorApplication.getInstance().addToRequestQueue(jsonObjReq, "nestorQuery");

        return response;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}



