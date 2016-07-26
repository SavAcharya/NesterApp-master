package in.sayes.nestorapp.chat.fragment;


import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import in.sayes.android.customui.dialogplus.DialogPlus;
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
import in.sayes.nestorapp.chat.widgets.ViewPagerGridFragment;
import in.sayes.nestorapp.chat.widgets.ViewPagerGridItemCategory;
import in.sayes.nestorapp.chat.widgets.ViewPagerGridItems;
import in.sayes.nestorapp.chat.widgets.viewpagerindicator.CirclePageIndicator;
import in.sayes.nestorapp.investment.activity.InvestmentActivity;
import in.sayes.nestorapp.network.utils.NetworkUtils;
import in.sayes.nestorapp.storage.AppPreferences;
import in.sayes.nestorapp.storage.IPreferenceConstants;
import in.sayes.nestorapp.util.APIConstants;
import in.sayes.nestorapp.util.UIUtils;
import in.sayes.nestorapp.util.Utils;


public class ChatFragment extends BaseFragment implements
        NotificationCenter.NotificationCenterDelegate, APIConstants,DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, SizeNotifierRelativeLayout.SizeNotifierRelativeLayoutDelegate {


    private final TextWatcher watcher1 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            chatEditText1.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
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

    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
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

    private String SelectedDate;

    private String FormatedDate;

    private String TAG = "ChatFragment";
    private boolean isFirst = true;
    private LinearLayout userTextInputLayout;


    private List<GSON_RootLevel.QuestionList> allQuestionsList;
    private GSON_RootLevel.QuestionList currentQuestionsList;
    private GSON_RootLevel.QuestionsEntity currentQustion;
    private GSON_RootLevel.QuestionList multiQustionList;

    private FrameLayout rootLayout;
    private int questionsCount = 0;
    private int questionsLevelIndex = 0;
    File photoFile = null;

    //ViewPager
    public CirclePageIndicator mIndicator;
    private ViewPager awesomePager;
    private PagerAdapter pm;
    ArrayList<ViewPagerGridItemCategory> codeViewPagerGridItemCategory;

    final private int CAMERA_PERMISSION_REQUEST_CODE =100123;

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
                UIUtils.hideKeyBoard(getContext(), v);
                handleInput(0, InputFromType.NUMERIC_KEYBOARD);
                userTextInputLayout.setVisibility(View.GONE);
                showOptionsButton.setVisibility(View.VISIBLE);
            }

            chatEditText1.setText("");

        }
    };
    private int REQUEST_IMAGE_CAPTURE = 1;
    private int RESULT_OK = 200;
    private String mCurrentPhotoPath;
    private int REQUEST_TAKE_PHOTO = 10;
    private Bitmap imageBitmap;
    private LinearLayout pagerRoot;
    private Button showOptionsButton;
    private HashMap<String, String> mKeys;
    private String tag_json_obj="PROTFOLIO";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_chat, container, false);
        AndroidUtilities.statusBarHeight = getStatusBarHeight();
        chatMessages = new ArrayList<>();
        appPreferences = new AppPreferences(getActivity());
        userInput = new HashMap<String, String>();
        allQuestionsList = new ArrayList<>();

        List<ViewPagerGridFragment> gridFragments = new ArrayList<ViewPagerGridFragment>();
        // Start chat with initial conversation for first time.
        // get signup data and create Nestor reply object
        initChat(appPreferences.getString(IPreferenceConstants.PREF_SIGNUP_REPLY, ""));
        setupView(root);

        return root;
    }


    private void setupView(View rootView) {

        chatListView = (ListView) rootView.findViewById(R.id.chat_list_view);

        chatEditText1 = (EditText) rootView.findViewById(R.id.chat_edit_text1);
        enterChatView1 = (ImageView) rootView.findViewById(R.id.send_chat_text);
        userTextInputLayout = (LinearLayout) rootView.findViewById(R.id.bottomlayout);
        rootLayout = (FrameLayout) rootView.findViewById(R.id.root_layout);

        listAdapter = new ChatListAdapter(chatMessages, getActivity());

        chatListView.setAdapter(listAdapter);

        chatEditText1.setOnKeyListener(keyListener);

        enterChatView1.setOnClickListener(clickListener);

        chatEditText1.addTextChangedListener(watcher1);
        userTextInputLayout.setVisibility(View.GONE);

        showOptionsButton= (Button) rootView.findViewById(R.id.btnShowOptions);
        showOptionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setInputFrom();
            }
        });
       // showOptionsButton.setVisibility(View.GONE);
        sizeNotifierRelativeLayout = (SizeNotifierRelativeLayout) rootView.findViewById(R.id.chat_layout);
        sizeNotifierRelativeLayout.delegate = this;

        pagerRoot=(LinearLayout)rootView.findViewById(R.id.pagerRoot);
        awesomePager = (ViewPager) rootView.findViewById(R.id.pager);
        awesomePager.setOffscreenPageLimit(3);
        mIndicator = (CirclePageIndicator) rootView.findViewById(R.id.pagerIndicator);
        pagerRoot.setVisibility(View.GONE);

    }

    private void initChat(String string) {

        mNestorReply = gson.fromJson(string, GSON_RootLevel.class);

        currentQuestionsList = new GSON_RootLevel.QuestionList(mNestorReply.getQuestions(), 0);


        allQuestionsList.add(currentQuestionsList);

        currentQustion = allQuestionsList.get(questionsLevelIndex).getQuestions().get(allQuestionsList.get(questionsLevelIndex).getQustionIndex());
        showNextQuestion();


    }

    private void showNextQuestion() {
        currentQuestionsList = allQuestionsList.get(allQuestionsList.size() - 1);

        /*if (currentQustion != null) {


            if (questionsCount < currentQuestionsList.getQuestions().size()) {
                currentQustion = allQuestionsList.get(questionsLevelIndex).getQuestions().get(questionsCount);

                printStatement(currentQustion);
            } else {
                // TODO make server call to send values and get next question
                requestForNewData();
            }

        } else*/
        if (questionsCount < currentQuestionsList.getQuestions().size()) {

            currentQustion = currentQuestionsList.getQuestions().get(questionsCount);
            printStatement();
            questionsCount++;
        } else {
            //it's last question on the current list -

            // remove the latest question list
            allQuestionsList.remove(allQuestionsList.size() - 1);

            if (!allQuestionsList.isEmpty()) {
                // get new list of questions
                currentQuestionsList = allQuestionsList.get(allQuestionsList.size() - 1);
                questionsCount = currentQuestionsList.getQustionIndex();
                // get question index if it was read before
                if (questionsCount < currentQuestionsList.getQuestions().size()) {
                    currentQustion = currentQuestionsList.getQuestions().get(currentQuestionsList.getQustionIndex());
                    printStatement();
                    questionsCount++;
                } else {
                    // TODO make server call to send values and get next question
                    requestForNewData();
                }

            } else {

                // TODO make server call to send values and get next question
                requestForNewData();
            }

        }

    }

    private void printStatement() {


        nestorStatements = currentQustion.getStatements();

        for (int statementCount = 0; statementCount < nestorStatements.size(); statementCount++) {

            showChatMessage(nestorStatements.get(statementCount), UserType.NESTOR, MessageType.TEXT);

        }

        // show options on user selection
       /* Runnable mMyRunnable = new Runnable() {
            @Override
            public void run() {
                setInputFrom();
            }
        };
        final Handler handler = new Handler();
        handler.postDelayed(mMyRunnable, 2000);*/

    }


    private void setInputFrom() {
      //  showOptionsButton.setVisibility(View.GONE);
        boolean isGrid;
        Boolean footer=false;

        Holder holder;
        String inputFromType=" ";
        if (currentQustion.getInput_form()!=null){
            inputFromType = currentQustion.getInput_form().getType();
        }
/*if ((currentQustion.getSelect_type().equalsIgnoreCase(InputFromType.SELECT_TYPE_MULTI))&&currentQustion.getSelect_type()!=null){
            footer=true;
        }*/

        Log.i("setInputFrom-inputType ", inputFromType);

        switch (inputFromType) {
            case InputFromType.LIST_INPUT:


                holder = new ListHolder();
                isGrid = false;

                showDialog(holder, isGrid, Gravity.BOTTOM, true, footer, true);
                break;

            case InputFromType.CUSTOM_KEYBOARD:
                holder = new ListHolder();
                isGrid = false;

                showDialog(holder, isGrid, Gravity.BOTTOM, true, footer, true);
                break;

            case InputFromType.FLOATING:
                holder = new ListHolder();
                isGrid = false;

                showDialog(holder, isGrid, Gravity.BOTTOM, true, footer, true);
                break;


            case InputFromType.NUMERIC_KEYBOARD:
                showOptionsButton.setVisibility(View.GONE);
                userTextInputLayout.setVisibility(View.VISIBLE);
                chatEditText1.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                break;

            case InputFromType.SELECT_VAL:

                break;

            case InputFromType.GRID:
                holder = new ListHolder();
                isGrid = false;

               /* holder = new GridHolder(2);
                isGrid = true;*/

                showDialog(holder, isGrid, Gravity.BOTTOM, true, footer, true);


                break;

            case InputFromType.VIEWPAGER:
                holder = new ListHolder();
                isGrid = false;
                /*holder = new GridHolder(2);
                isGrid = true;*/

                showDialog(holder, isGrid, Gravity.BOTTOM, true, footer, true);
             /* pagerRoot.setVisibility(View.VISIBLE);
                showViewPager(currentQustion.getOptions());
*/

                break;

            case InputFromType.DATE_PICKER:
                showDate();

                break;
            case InputFromType.IMAGE:
                captureImage();

                break;


            case InputFromType.PIE:
                holder = new ListHolder();
                isGrid = false;

                showDialog(holder, isGrid, Gravity.BOTTOM, true, footer, true);


                break;

            default:
                showNextQuestion();
                break;
        }

    }



    private void handleInput(int selectedInputPOsition, String inputFromType) {

        // TODO get the selected value and add to option selection map.
        // TODO set the selected option on map
Log.i("handleInput",inputFromType);
        switch (inputFromType) {
            case InputFromType.LIST_INPUT:

                showChatMessage(currentQustion.getOptions().get(selectedInputPOsition).getValue(), UserType.SELF, MessageType.TEXT);
                // check if name_var exist in response
                if (currentQustion.getName_var() != null) {
                    userInput.put(currentQustion.getName_var(), Integer.toString(selectedInputPOsition));
                }

                isFollowUp(selectedInputPOsition);

                break;

            case InputFromType.CUSTOM_KEYBOARD:
                showChatMessage(currentQustion.getOptions().get(selectedInputPOsition).getValue(), UserType.SELF, MessageType.TEXT);
                // check if name_var exist in response
                if (currentQustion.getName_var() != null) {
                    userInput.put(currentQustion.getName_var(), Integer.toString(selectedInputPOsition));
                }

                isFollowUp(selectedInputPOsition);
                break;

            case InputFromType.FLOATING:
                showChatMessage(currentQustion.getOptions().get(selectedInputPOsition).getValue(), UserType.SELF, MessageType.TEXT);
                // check if name_var exist in response
                if (currentQustion.getName_var() != null) {
                    userInput.put(currentQustion.getName_var(), Integer.toString(selectedInputPOsition));
                }

                isFollowUp(selectedInputPOsition);
                break;

            case InputFromType.NUMERIC_KEYBOARD:

                String s = chatEditText1.getText().toString();
                showChatMessage(s, UserType.SELF, MessageType.TEXT);
                userInput.put(currentQustion.getName_var(), s);
                isFollowUp(selectedInputPOsition);

                break;

            case InputFromType.SELECT_VAL:
                showChatMessage(currentQustion.getOptions().get(selectedInputPOsition).getValue(), UserType.SELF, MessageType.TEXT);
                // check if name_var exist in response
                if (currentQustion.getName_var() != null) {
                    userInput.put(currentQustion.getName_var(), Integer.toString(selectedInputPOsition));
                }

                isFollowUp(selectedInputPOsition);
                break;

            case InputFromType.GRID:
                showChatMessage(currentQustion.getOptions().get(selectedInputPOsition).getValue(), UserType.SELF, MessageType.TEXT);
                // check if name_var exist in response
                if (currentQustion.getName_var() != null) {
                    userInput.put(currentQustion.getName_var(), Integer.toString(selectedInputPOsition));
                }

                isFollowUp(selectedInputPOsition);

                break;

            case InputFromType.VIEWPAGER:

                if (currentQustion.getSelect_type().equalsIgnoreCase(InputFromType.SELECT_TYPE_MULTI)) {
                    showChatMessage(currentQustion.getOptions().get(selectedInputPOsition).getValue(), UserType.SELF, MessageType.COMBO);

                }
                if (currentQustion.getSelect_type().equalsIgnoreCase(InputFromType.SELECT_TYPE_SINGLE)) {
                    showChatMessage(currentQustion.getOptions().get(selectedInputPOsition).getValue(), UserType.SELF, MessageType.TEXT);

                }
                pagerRoot.setVisibility(View.GONE);
                // check if name_var exist in response
                if (currentQustion.getName_var() != null) {
                    userInput.put(currentQustion.getName_var(), Integer.toString(selectedInputPOsition));
                }

                isFollowUp(selectedInputPOsition);

                break;
            case InputFromType.DATE_PICKER:

                showChatMessage(FormatedDate, UserType.SELF, MessageType.TEXT);
                // check if name_var exist in response
                if (currentQustion.getName_var() != null) {
                    userInput.put(currentQustion.getName_var(), SelectedDate);
                }

                isFollowUp(selectedInputPOsition);

                break;

            case InputFromType.IMAGE:
                //Show image and upload data to server ...
                showChatMessage("Uploaded Image", UserType.SELF, MessageType.IMAGE);
                // check if name_var exist in response


                isFollowUp(selectedInputPOsition);

                break;

            case InputFromType.PIE:
                showChatMessage(currentQustion.getOptions().get(selectedInputPOsition).getValue(), UserType.SELF, MessageType.TEXT);
                // check if name_var exist in response
                if (currentQustion.getName_var() != null) {
                    userInput.put(currentQustion.getName_var(), Integer.toString(selectedInputPOsition));
                }

                protfolioRequest();

                //isFollowUp(selectedInputPOsition);

                break;


            default:

                break;
        }

    }

    private Boolean isFollowUp(int selectedInputPOsition) {

        if (currentQustion.getOptions().size() > 0) {
            if (currentQustion.getOptions().get(selectedInputPOsition).getFollowup() != null) {

                allQuestionsList.get(allQuestionsList.size() - 1).setQustionIndex(questionsCount);
                // show followup questions -  set currentQustion to followup question
                GSON_RootLevel.QuestionList questionList = new GSON_RootLevel.QuestionList(currentQustion.getOptions().get(selectedInputPOsition).getFollowup(), 0);

                allQuestionsList.add(questionList);
                // reset question count as new followup questions added
                questionsCount = 0;
                showNextQuestion();
            } else {
                showNextQuestion();
            }
        } else {
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

        if (photoFile!=null){
            userInput.put(APIConstants.USERID_URL_PARAM, mNestorReply.getUser_id());
            userInput.put("user_query","0");
            String urlKeys = NetworkUtils.getURLKeyParameters(userInput);
            Log.i("INPUT",urlKeys);
            if (!urlKeys.isEmpty()) {
                url = url + urlKeys;
            }
            final String finalUrl=url;

                        DownloadWebPageTask task = new DownloadWebPageTask();
                        task.execute(new String[] { finalUrl });

                        pDialog.dismiss();


         /*   userInput = new HashMap<>();
            userInput.put(APIConstants.USERID_URL_PARAM, mNestorReply.getUser_id());
            userInput.put("user_query","0");
            String urlKeys = NetworkUtils.getURLKeyParameters(userInput);
            Log.i("INPUT",urlKeys);
            if (!urlKeys.isEmpty()) {
                url = url + urlKeys;
            }
            Log.i("SAL_IMAGE", "UPLOADING");
            Map<String,String> mStringPart =  new HashMap<>();

            VollyMultipartRequest request = new VollyMultipartRequest(url, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    // hide the progress dialog
                    pDialog.hide();
                }
            },new Response.Listener<String>(){


                @Override
                public void onResponse(String response) {
                    Log.d(TAG, response.toString());
                    Gson gson = new Gson();
                    mNestorReply = gson.fromJson(response.toString(), GSON_RootLevel.class);
                    GSON_RootLevel.QuestionList questionList = new GSON_RootLevel.QuestionList(mNestorReply.getQuestions(), 0);
                    allQuestionsList.add(questionList);
                    questionsCount = 0;
                    showNextQuestion();
                    // remove all previous data
                    userInput.clear();
                    pDialog.hide();
                }
            },photoFile,"sal_image",mStringPart);

            photoFile=null;

            NestorApplication.getInstance().addToRequestQueue(request, "nestorQuery");*/
        }else{
            final String url1 = BASE_URL + mNestorReply.getNext_endpoint();
            userInput.put(APIConstants.USERID_URL_PARAM, mNestorReply.getUser_id());
            String urlKeys = NetworkUtils.getURLKeyParameters(userInput);
            if (!urlKeys.isEmpty()) {
                url = url + urlKeys;
            }
            Log.i("Request", url);

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d(TAG, response.toString());
                            Gson gson = new Gson();
                            mNestorReply = gson.fromJson(response.toString(), GSON_RootLevel.class);
                            GSON_RootLevel.QuestionList questionList = new GSON_RootLevel.QuestionList(mNestorReply.getQuestions(), 0);
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
        }




        return response;
    }


    private void showChatMessage(final String mMessage, final UserType userType, final MessageType msgType) {
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
                switch (msgType) {
                    case IMAGE:
                        message.setMessageType(MessageType.IMAGE);
                        message.setMsgImage(imageBitmap);
                        break;
                    case TEXT:
                        message.setMessageType(MessageType.TEXT);
                        break;

                    case COMBO:
                        message.setMessageType(MessageType.COMBO);
                        break;
                }

                message.setMessageTime(new Date().getTime());
                chatMessages.add(message);

                getActivity().runOnUiThread(new Runnable() {
                    public void run() {

                        listAdapter.notifyDataSetChanged();
                        chatListView.setSelection(listAdapter.getCount());

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

        chatListView.setSelection(listAdapter.getCount());

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

                        chatListView.setSelection(listAdapter.getCount());

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
                       // Toast.makeText(getContext(), "Confirm button clicked", Toast.LENGTH_LONG).show();

                       /* List<GSON_RootLevel.QuestionsEntity> selectedOptions = new ArrayList<>();
                        for (int i=0;i<currentQustion.getOptions().size();i++){
                            if (currentQustion.getOptions().get(i).isSelected()){
                                selectedOptions.add(currentQustion.getOptions().get(i).getFollowup().,i);
                            }
                        }*/

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
               // Toast.makeText(getContext(), position + "   " + clickedAppName + " clicked", Toast.LENGTH_LONG).show();

            }
        };

        OnDismissListener dismissListener = new OnDismissListener() {
            @Override
            public void onDismiss(DialogPlus dialog) {
               // Toast.makeText(getContext(), "dismiss listener invoked!", Toast.LENGTH_SHORT).show();

                showOptionsButton.setVisibility(View.VISIBLE);
            }
        };

        OnCancelListener cancelListener = new OnCancelListener() {
            @Override
            public void onCancel(DialogPlus dialog) {
                showOptionsButton.setVisibility(View.VISIBLE);
              //  Toast.makeText(getContext(), "cancel listener invoked!", Toast.LENGTH_SHORT).show();
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
                      //  Toast.makeText(getContext(), position + "   " + item + " clicked", Toast.LENGTH_LONG).show();
                      //  showOptionsButton.setVisibility(View.GONE);
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

        final String inputFromType = currentQustion.getInput_form().getType();
        final DialogPlus dialog = DialogPlus.newDialog(getContext())
                .setContentHolder(holder)
                .setHeader(R.layout.dialog_header)
                .setCancelable(true)
                .setGravity(gravity)
                .setAdapter(adapter)
                .setOnClickListener(clickListener)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, final int position) {
                        Log.d("DialogPlus", "onItemClick() called with: " + "item = [" +
                                item + "], position = [" + position + "]");
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
                .setOnCancelListener(cancelListener)
                .setExpanded(expanded)
                .create();
        dialog.show();
    }

    private void showNoHeaderDialog(Holder holder, int gravity, BaseAdapter adapter,
                                    OnClickListener clickListener, OnItemClickListener itemClickListener,
                                    OnDismissListener dismissListener, OnCancelListener cancelListener,
                                    boolean expanded, List<GSON_RootLevel.OptionsEntity> options) {
        final String inputFromType = currentQustion.getInput_form().getType();
        final DialogPlus dialog = DialogPlus.newDialog(getContext())
                .setContentHolder(holder)
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
                .setOnCancelListener(cancelListener)
                .setExpanded(expanded)
                .create();
        dialog.show();


    }

    private void showOnlyContentDialog(Holder holder, int gravity, BaseAdapter adapter,
                                       OnItemClickListener itemClickListener, OnDismissListener dismissListener,
                                       OnCancelListener cancelListener, boolean expanded) {
        final String inputFromType = currentQustion.getInput_form().getType();
        final DialogPlus dialog = DialogPlus.newDialog(getContext())
                .setContentHolder(holder)
                .setGravity(gravity)
                .setAdapter(adapter)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, final int position) {
                        Log.d("DialogPlus", "onItemClick() called with: " + "item = [" +
                                item + "], position = [" + position + "]");
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
                .setOnCancelListener(cancelListener)
                .setExpanded(expanded)
                .setCancelable(true)
                .create();
        dialog.show();
    }



    public void showDate() {

        {
           /* Calendar now = Calendar.getInstance();


            DatePickerDialog dpd = DatePickerDialog.newInstance(
                    this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
            dpd.setThemeDark(false);
            dpd.vibrate(true);
            dpd.dismissOnPause(true);
            dpd.showYearPickerFirst(true);
            dpd.setAccentColor(getResources().getColor(R.color.colorPrimary));
            dpd.setTitle(getResources().getString(R.string.date_picker_title));

            dpd.show(getFragmentManager(), "Datepickerdialog");*/
        }

    }

   // @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        String mMonth = "06";
        String mDate = "12";

        if (monthOfYear < 10) {
            mMonth = "0" + monthOfYear;
        }

        if (dayOfMonth < 10) {
            mDate = "0" + dayOfMonth;
        }
        FormatedDate = mDate + "/" + mMonth + "/" + year;
        SelectedDate = mDate + mMonth + "" + year;

        Runnable mMyRunnable = new Runnable() {
            @Override
            public void run() {

                handleInput(0, InputFromType.DATE_PICKER);
            }
        };
        final Handler handler = new Handler();
        handler.postDelayed(mMyRunnable, 2000);
    }

    private void showTimePicker() {

           /* Calendar now = Calendar.getInstance();
            in.sayes.android.customui.datetimepicker.time.TimePickerDialog tpd = in.sayes.android.customui.datetimepicker.time.TimePickerDialog.newInstance(
                    MainActivity.this,
                    now.get(Calendar.HOUR_OF_DAY),
                    now.get(Calendar.MINUTE),
                    mode24Hours.isChecked()
            );
            tpd.setThemeDark(modeDarkTime.isChecked());
            tpd.vibrate(vibrateTime.isChecked());
            tpd.dismissOnPause(dismissTime.isChecked());
            tpd.enableSeconds(enableSeconds.isChecked());
            if (modeCustomAccentTime.isChecked()) {
                tpd.setAccentColor(Color.parseColor("#9C27B0"));
            }
            if (titleTime.isChecked()) {
                tpd.setTitle("TimePicker Title");
            }
            if (limitTimes.isChecked()) {
                tpd.setTimeInterval(2, 5, 10);
            }
            tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    Log.d("TimePicker", "Dialog was cancelled");
                }
            });
            tpd.show(getFragmentManager(), "Timepickerdialog");
        */
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
       /* in.sayes.android.customui.datetimepicker.time.TimePickerDialog tpd = (in.sayes.android.customui.datetimepicker.time.TimePickerDialog) getFragmentManager().findFragmentByTag("Timepickerdialog");
        DatePickerDialog dpd = (DatePickerDialog) getFragmentManager().findFragmentByTag("Datepickerdialog");

        if (tpd != null) tpd.setOnTimeSetListener(this);
        if (dpd != null) dpd.setOnDateSetListener(this);*/
    }


    /*@Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        *//*String hourString = hourOfDay < 10 ? "0"+hourOfDay : ""+hourOfDay;
        String minuteString = minute < 10 ? "0"+minute : ""+minute;
        String secondString = second < 10 ? "0"+second : ""+second;
        String time = "You picked the following time: "+hourString+"h"+minuteString+"m"+secondString+"s";*//*

    }*/


    public void captureImage() {


        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            // Create the File where the photo should go

            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                 startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }


    public File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath =image.getAbsolutePath();
        Log.i("FILE_PATH",mCurrentPhotoPath);
        return image;
    }

    //Get the Thumbnail in image captured
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
           /* Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");*/
            /*File image = new File(mCurrentPhotoPath);
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            imageBitmap = BitmapFactory.decodeFile(image.getAbsolutePath(),bmOptions);*/
            imageBitmap=Utils.resizeBitmap(mCurrentPhotoPath,100 ,100);

            Log.i("Image Capture", "");
            Runnable mMyRunnable = new Runnable() {
                @Override
                public void run() {

                    handleInput(0, InputFromType.IMAGE);
                }
            };
            final Handler handler = new Handler();
            handler.postDelayed(mMyRunnable, 2000);

        }
    }




    private void protfolioRequest(){
        String url = BASE_URL+mNestorReply.getNext_endpoint();
        userInput.put(APIConstants.USERID_URL_PARAM, mNestorReply.getUser_id());
        String urlKeys = NetworkUtils.getURLKeyParameters(userInput);
        if (!urlKeys.isEmpty()) {
            url = url + urlKeys;
        }
        Log.i("Request", url);

        final ProgressDialog pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Loading...");
        pDialog.show();




        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,url,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                       Intent intent = new Intent(getContext(), InvestmentActivity.class);

                        intent.putExtra("res",response.toString());
                        startActivity(intent);


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
        NestorApplication.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }

    public void showViewPager(List<GSON_RootLevel.OptionsEntity> options) {
        ArrayList<String> a = new ArrayList<String>();
        ViewPagerGridItemCategory m = new ViewPagerGridItemCategory();

        for (int i = 0; i < options.size(); i++) {
            a.add(i, options.get(i).getValue());
            m.name = a.get(i);
        }

        codeViewPagerGridItemCategory = new ArrayList<ViewPagerGridItemCategory>();
        codeViewPagerGridItemCategory.add(m);

        Iterator<String> it = a.iterator();

        it = a.iterator();

        int i = 0;
        while (it.hasNext()) {
            ArrayList<ViewPagerGridItems> itmLst = new ArrayList<ViewPagerGridItems>();

            ViewPagerGridItems itm = new ViewPagerGridItems(0, it.next());
            itmLst.add(itm);
            i = i + 1;

            if (it.hasNext()) {
                ViewPagerGridItems itm1 = new ViewPagerGridItems(1, it.next());
                itmLst.add(itm1);
                i = i + 1;
            }

            if (it.hasNext()) {
                ViewPagerGridItems itm2 = new ViewPagerGridItems(2, it.next());
                itmLst.add(itm2);
                i = i + 1;
            }

            if (it.hasNext()) {
                ViewPagerGridItems itm3 = new ViewPagerGridItems(3, it.next());
                itmLst.add(itm3);
                i = i + 1;
            }

        }
    }

    private boolean requestCameraPermission(Context context){
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
        if (result == PackageManager.PERMISSION_GRANTED){

            return true;

        } else {

            return false;

        }
    }

    private boolean requestFilePermission(Context context){
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED){

            return true;

        } else {

            return false;

        }
    }

    private void CameraPermission(Activity activity){

        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,Manifest.permission.CAMERA)){

            //Toast.makeText(activity,"permission allows us to take picture. Please allow in App Settings for additional functionality",Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CAMERA_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(getContext(),"Permission Granted, You cannot take picture.",Toast.LENGTH_SHORT).show();

                } else {
                   // Toast.makeText(getContext(),"Permission Denied, You cannot take picture.",Toast.LENGTH_SHORT).show();

                }
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {

    }

   /* @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.btnShowOptions:
                setInputFrom();
                showOptionsButton.setVisibility(View.GONE);
        }
    }*/




    private class DownloadWebPageTask extends AsyncTask<String, Void, String> {


         ProgressDialog  pDialog1 = new ProgressDialog(getContext());

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog1.setMessage("Loading...");
            pDialog1.show();
        }

        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            try {

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] data = bos.toByteArray();
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost postRequest = new HttpPost(
                        urls[0]);
                FileBody bin = new FileBody(photoFile);
                MultipartEntity reqEntity = new MultipartEntity(
                        HttpMultipartMode.BROWSER_COMPATIBLE);
                reqEntity.addPart("sal_image", bin);
                postRequest.setEntity(reqEntity);
                HttpResponse response1 = httpClient.execute(postRequest);
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        response1.getEntity().getContent(), "UTF-8"));
                StringBuilder s = new StringBuilder();

                while ((response = reader.readLine()) != null) {
                    s = s.append(response);
                }
               response=s.toString();
Log.i("Res",s.toString());
            } catch (Exception e) {
                // handle exception here
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String sResponse) {


            Log.d(TAG, sResponse);
            Gson gson = new Gson();
            mNestorReply = gson.fromJson(sResponse.toString(), GSON_RootLevel.class);
            GSON_RootLevel.QuestionList questionList = new GSON_RootLevel.QuestionList(mNestorReply.getQuestions(), 0);
            allQuestionsList.add(questionList);
            questionsCount = 0;
            showNextQuestion();
            photoFile=null;
            // remove all previous data
            userInput.clear();
            pDialog1.dismiss();
        }
    }

}


