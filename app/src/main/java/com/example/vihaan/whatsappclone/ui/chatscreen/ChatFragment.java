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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vihaan.whatsappclone.R;
import com.example.vihaan.whatsappclone.ui.Database;
import com.example.vihaan.whatsappclone.ui.Util;
import com.example.vihaan.whatsappclone.ui.models.Message;
import com.example.vihaan.whatsappclone.ui.models.User;
import com.example.vihaan.whatsappclone.ui.models.UserChat;
import com.firebase.ui.database.ChangeEventListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private User mReceivingUser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        Bundle bundle = getArguments();
        mReceivingUser = bundle.getParcelable(EXTRAS_USER);
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

    private void initViews() {
        initMessageBar();
        initRecyclerView();
    }


    private FloatingActionButton mFabButton;
    private EditText mEditText;

    private void initMessageBar() {
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
                if (s.length() == 0) {
                    showSendButton();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    showAudioButton();
                }
            }
        });


        mFabButton = (FloatingActionButton) getView().findViewById(R.id.floatingButton);
        mFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tag = (String) mFabButton.getTag();
                Log.d("fab tag", tag);
                if (tag.equalsIgnoreCase(SEND_IMAGE)) {
                    onSendButtonClicked();
                }

            }
        });
    }

    private RecyclerView mRecyclerView;

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.chatsRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }


    private static final String SEND_IMAGE = "send_image";

    private void showSendButton() {
        mFabButton.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.input_send));
        mFabButton.setTag(SEND_IMAGE);
    }

    private static final String MIC_IMAGE = "mic_image";

    private void showAudioButton() {
        mFabButton.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.input_mic_white));
        mFabButton.setTag(MIC_IMAGE);
    }

    private void onSendButtonClicked() {
        String message = mEditText.getText().toString();
        mEditText.setText("");
        Log.d("send msg", message);
        writeTextMessage(message);
    }

    private void writeTextMessage(String data) {

        Message message = new Message();

        message.setSenderUid(mAuth.getCurrentUser().getUid());
        message.setReceiverUid(mReceivingUser.getUid());
        message.setType("text");
        message.setData(data);

        String messagesNode = getMessagesNode();
        String messageNode = mDatabase.getReference().child(Database.NODE_MESSAGES).child(messagesNode).push().getKey();
        mDatabase.getReference().child(Database.NODE_MESSAGES).child(messagesNode).child(messageNode).setValue(message);

        FirebaseUser sendingUser = mAuth.getCurrentUser();

        UserChat userChat = new UserChat();
        userChat.setUid(sendingUser.getUid());
        userChat.setLastMessage(data);

//        String userChatKey = mDatabase.getReference().child(Database.NODE_USER_CHATS).child(mReceivingUser.getUid()).push().getKey();

        // add user chat in sending user
        mDatabase.getReference().child(Database.NODE_USER_CHATS).child(mReceivingUser.getUid()).child(sendingUser.getUid()).setValue(userChat);


        userChat.setUid(mReceivingUser.getUid());
        // add user chat in receiving user
        mDatabase.getReference().child(Database.NODE_USER_CHATS).child(sendingUser.getUid()).child(mReceivingUser.getUid()).setValue(userChat);

    }


    private String getMessagesNode() {
        String messageNode = null;
        if (mAuth.getCurrentUser() != null) {
            FirebaseUser firebaseUser = mAuth.getCurrentUser();
            String sendingUID = firebaseUser.getUid();
            String receivingUID = mReceivingUser.getUid();
            messageNode = Util.getMessageNode(sendingUID, receivingUID);
        }
        return messageNode;
    }

    private ChatAdapter mChatAdapter;

    private void showChats() {
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

        JSONObject jsonObject;
        String json;

        InputStream is = getActivity().getAssets().open("chatmessages.json");

        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        json = new String(buffer, "UTF-8");

        jsonObject = new JSONObject(json);
        JSONArray jsonArray = (JSONArray) jsonObject.get("1");

        Type listType = new TypeToken<List<ChatMessage>>() {
        }.getType();

        chatMessages = new Gson().fromJson(jsonArray.toString(), listType);

        return chatMessages;

    }

    private FirebaseRecyclerAdapter<Message, RecyclerView.ViewHolder> mAdapter;

    private void loadChats() {

        String messageNode = getMessagesNode();
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


        mAdapter = new FirebaseRecyclerAdapter<Message, RecyclerView.ViewHolder>(Message.class, R.layout.item_messsage_outgoing,
                RecyclerView.ViewHolder.class, messageQuery) {

            private final int TYPE_INCOMING = 1;
            private final int TYPE_OUTGOING = 2;

            @Override
            protected void populateViewHolder(final RecyclerView.ViewHolder viewHolder, final Message message, final int position) {

                if (messageFromCurrentUser(message)) {
                    populateOutgoingViewHolder((OutgoingViewHolder) viewHolder, message);
                } else {
                    populateIncomingViewHolder((IncomingViewHolder) viewHolder, message);
                }
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view;
                switch (viewType) {
                    case TYPE_INCOMING:

                        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_messsage_incoming, parent, false);
                        return new IncomingViewHolder(view);

                    case TYPE_OUTGOING:
                        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_messsage_outgoing, parent, false);
                        return new OutgoingViewHolder(view);
                }
                return super.onCreateViewHolder(parent, viewType);
            }

            private void populateIncomingViewHolder(IncomingViewHolder viewHolder, Message message) {
                // Bind Post to ViewHolder, setting OnClickListener for the star button
                viewHolder.bindToMessage(message, new View.OnClickListener() {
                    @Override
                    public void onClick(View starView) {

                    }
                });

            }

            private void populateOutgoingViewHolder(OutgoingViewHolder viewHolder, Message message) {

                // Bind Post to ViewHolder, setting OnClickListener for the star button
                viewHolder.bindToMessage(message, new View.OnClickListener() {
                    @Override
                    public void onClick(View starView) {

                    }
                });

            }

            @Override
            public int getItemViewType(int position) {
                super.getItemViewType(position);
                Message message = getItem(position);

                if (messageFromCurrentUser(message)) {
                    return TYPE_OUTGOING;
                }

                return TYPE_INCOMING;
            }

            private boolean messageFromCurrentUser(Message message) {
                String currentUid = mAuth.getCurrentUser().getUid();
                if (currentUid.equalsIgnoreCase(message.getSenderUid())) {
                    return true;
                }
                return false;
            }

            class IncomingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

                TextView chatTV, timeTV;
                ImageView chatIV;
//                       messageStatusIV;

                public IncomingViewHolder(View v) {
                    super(v);
                    chatTV = (TextView) v.findViewById(R.id.chatTV);
                    timeTV = (TextView) v.findViewById(R.id.timeTV);
//                   messageStatusIV = (ImageView) v.findViewById(messageStatusIV);
                    chatIV = (ImageView) v.findViewById(R.id.chatIV);
                }

                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                    }
                }

                public void bindToMessage(Message message, View.OnClickListener starClickListener) {

//        if (!TextUtils.isEmpty(user.getProfilePicUrl())) {
//            Picasso.with(userIV.getContext()).load(user.getProfilePicUrl()).into(userIV);
//        }
                    chatTV.setText(message.getData());
                }
            }

            class OutgoingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

                TextView chatTV, timeTV;
                ImageView chatIV, messageStatusIV;

                public OutgoingViewHolder(View v) {
                    super(v);
                    chatTV = (TextView) v.findViewById(R.id.chatTV);
                    timeTV = (TextView) v.findViewById(R.id.timeTV);
                    messageStatusIV = (ImageView) v.findViewById(R.id.messageStatusIV);
                    chatIV = (ImageView) v.findViewById(R.id.chatIV);
                }

                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                    }
                }

                public void bindToMessage(Message message, View.OnClickListener starClickListener) {

//        if (!TextUtils.isEmpty(user.getProfilePicUrl())) {
//            Picasso.with(userIV.getContext()).load(user.getProfilePicUrl()).into(userIV);
//        }
                    chatTV.setText(message.getData());
                }
            }

            @Override
            protected void onChildChanged(ChangeEventListener.EventType type, int index, int oldIndex) {
                super.onChildChanged(type, index, oldIndex);

                int lastPostion;
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                lastPostion = linearLayoutManager.findLastVisibleItemPosition();
                if (lastPostion != -1 && type == ChangeEventListener.EventType.ADDED) {

                    if (index > lastPostion) {
                        onNewMessageReceived();
                    }
                }
            }

            private void onNewMessageReceived() {
                int position = mAdapter.getItemCount() - 1;
                mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount() - 1);
            }
        };

        mRecyclerView.setAdapter(mAdapter);

    }


}
