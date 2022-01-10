package com.zama.example.chatbot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView RvChats;
    private EditText etMessage;
    private FloatingActionButton BtnSend;
    private final String BOT_KEY = "bot";
    private final String USER_KEY = "user";
    private ArrayList<ChatsModel> chatsModelArrayList;
    private ChatRvAdapter chatRvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RvChats = findViewById(R.id.RvChats);
        etMessage = findViewById(R.id.etMessage);
        BtnSend = findViewById(R.id.BtnSend);

        chatsModelArrayList = new ArrayList<>();
        chatRvAdapter = new ChatRvAdapter(chatsModelArrayList, this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        RvChats.setLayoutManager(manager);
        RvChats.setAdapter(chatRvAdapter);

        BtnSend.setOnClickListener(view -> {
            if (etMessage.getText().toString().isEmpty()) {
                Toast.makeText(MainActivity.this, "please enter your message", Toast.LENGTH_SHORT).show();
                return;
            }
            getResources(etMessage.getText().toString());
            etMessage.setText("");

        });
    }


    private void getResources(String message) {
        chatsModelArrayList.add(new ChatsModel(message, USER_KEY));
        chatRvAdapter.notifyDataSetChanged();
        String url = "http://api.brainshop.ai/get?bid=161805&key=xmPc86C2qD3Z4D9o&uid=[uid]&msg=" + message;
        String BASE_URL = "http://api.brainshop.ai/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<MsgModel> call = retrofitAPI.getMessage(url);
        call.enqueue(new Callback<MsgModel>() {

            @Override
            public void onResponse(Call<MsgModel> call, retrofit2.Response<MsgModel> response) {
                if (response.isSuccessful()) {
                    MsgModel model = response.body();
                    chatsModelArrayList.add(new ChatsModel(model.getCnt(), BOT_KEY));
                    chatRvAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MsgModel> call, Throwable t) {
                chatsModelArrayList.add(new ChatsModel("please revert your question", BOT_KEY));
                Log.d("OnFailureBot", "onFailure: " + t.getMessage());
                chatRvAdapter.notifyDataSetChanged();
            }
        });

    }

}






