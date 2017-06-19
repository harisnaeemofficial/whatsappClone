package com.example.vihaan.whatsappclone.ui.chatscreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.vihaan.whatsappclone.R;
import com.example.vihaan.whatsappclone.ui.Database;
import com.example.vihaan.whatsappclone.ui.Util;
import com.example.vihaan.whatsappclone.ui.models.Message;
import com.example.vihaan.whatsappclone.ui.models.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

import static com.example.vihaan.whatsappclone.ui.chatscreen.ChatActivity.EXTRAS_USER;

/**
 * Created by vihaan on 22/05/17.
 */

public class ChatFragment extends Fragment {

    public static ChatFragment newInstance(Bundle bundle) {
        ChatFragment fragment = new ChatFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private User mUser;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        Bundle bundle = getArguments();
        mUser = bundle.getParcelable(EXTRAS_USER);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        loadChats();
    }

    private void initViews()
    {
        initMessageBar();
        initRecyclerView();
    }


    private FloatingActionButton mFabButton;
    private EditText mEditText;

    private void initMessageBar()
    {
        mEditText = (EditText) getView().findViewById(R.id.editText);
        mEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

            }
        });

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(s.length() == 0)
                {
                    showSendButton();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 0)
                {
                    showAudioButton();
                }
            }
        });


        mFabButton = (FloatingActionButton) getView().findViewById(R.id.floatingButton);
        mFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tag = (String) mFabButton.getTag();
                Log.d("fab tag" , tag);
                if(tag.equalsIgnoreCase(SEND_IMAGE))
                {
                    onSendButtonClicked();
                }

            }
        });
    }

    private RecyclerView mRecyclerView;

    private void initRecyclerView()
    {
        mRecyclerView= (RecyclerView) getView().findViewById(R.id.chatsRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    private static final String SEND_IMAGE = "send_image";
    private void showSendButton()
    {
        mFabButton.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.input_send));
        mFabButton.setTag(SEND_IMAGE);
    }

    private static final String MIC_IMAGE= "mic_image";
    private void showAudioButton()
    {
        mFabButton.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.input_mic_white));
        mFabButton.setTag(MIC_IMAGE);
    }

    private void onSendButtonClicked()
    {
        String message = mEditText.getText().toString();
        mEditText.setText("");
        Log.d("send msg", message);
        writeTextMessage(message);
    }

    private void writeTextMessage(String data)
    {
        Message message = new Message();
        message.setSenderUid(mAuth.getCurrentUser().getUid());
        message.setReceiverUid(mUser.getUid());

        message.setType("text");
        message.setData(data);

        String messageNode = getMessageNode();
       String node =  mDatabase.getReference().child(Database.NODE_MESSAGES).child(messageNode).push().getKey();
        mDatabase.getReference().child(Database.NODE_MESSAGES).child(messageNode).child(node).setValue(message);
    }


    private String getMessageNode()
    {
        String messageNode = null;
        if(mAuth.getCurrentUser() != null)
        {
            FirebaseUser firebaseUser = mAuth.getCurrentUser();
            String sendingUID = firebaseUser.getUid();
            String receivingUID = mUser.getUid();
            messageNode = Util.getMessageNode(sendingUID, receivingUID);
        }
        return messageNode;
    }

    private ChatAdapter mChatAdapter;
    private void showChats()
    {
        try {
            List<ChatMessage> chatMessages = getChatMessages();
            mChatAdapter = new ChatAdapter(getActivity(), chatMessages);
            mRecyclerView.setAdapter(mChatAdapter);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private List<ChatMessage> getChatMessages() throws IOException, JSONException {
        List<ChatMessage> chatMessages = null;

        JSONObject jsonObject ;
        String json;

        InputStream is = getActivity().getAssets().open("chatmessages.json");

        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        json = new String(buffer, "UTF-8");

        jsonObject = new JSONObject(json);
        JSONArray jsonArray = (JSONArray) jsonObject.get("1");

        Type listType = new TypeToken<List<ChatMessage>>() {}.getType();

        chatMessages = new Gson().fromJson(jsonArray.toString(), listType);

        return chatMessages;

    }

   private FirebaseRecyclerAdapter<Message, MessageViewHolder> mAdapter;
   private void loadChats()
   {

       String messageNode = getMessageNode();
       Query messageQuery = mDatabase.getReference().child(Database.NODE_MESSAGES).child(messageNode);

       /*
       messageQuery.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {

               try
               {
                   Message message = dataSnapshot.getValue(Message.class);
                   message.getData();
               }catch (Exception e)
               {
                   e.printStackTrace();

               }

           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });
       */

       mAdapter = new FirebaseRecyclerAdapter<Message, MessageViewHolder>(Message.class, R.layout.item_messsage_outgoing,
               MessageViewHolder.class, messageQuery) {
           @Override
           protected void populateViewHolder(final MessageViewHolder viewHolder, final Message message, final int position) {
               final DatabaseReference postRef = getRef(position);

               // Set click listener for the whole post view
               final String postKey = postRef.getKey();
               viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       // Launch PostDetailActivity
//                       Intent intent = new Intent(getActivity(), ChatActivity.class);
//                       intent.putExtra(ChatActivity.EXTRAS_USER, user);
//                       startActivity(intent);
                   }
               });



               // Bind Post to ViewHolder, setting OnClickListener for the star button
               viewHolder.bindToMessage(message, new View.OnClickListener() {
                   @Override
                   public void onClick(View starView) {

                   }
               });
           }
       };

       mRecyclerView.setAdapter(mAdapter);

   }





























}
