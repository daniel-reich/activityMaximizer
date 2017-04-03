package utils;

import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.GenericTypeIndicator;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import model.Event;
import model.User;
import model.UserContact;
import model.UserData;

/**
 * Created by LucianVictor on 4/3/2017.
 */
public class UserTask {

    public interface Listener {
        void onChanged(UserData data);
        void onError(FirebaseError error);
    }

    private String mUID;
    private Firebase mFirebase;
    private Listener mListener;

    private UserData data = new UserData();

    private ValueEventListener mUserListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Log.d("user task", "user " + dataSnapshot);
            data.user = dataSnapshot.getValue(User.class);
            notifyIfNeeded();
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {
            if (mListener != null) mListener.onError(firebaseError);
        }
    };
    private ValueEventListener mEventsListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Map<String, Event> events = dataSnapshot.getValue(new GenericTypeIndicator<Map<String, Event>>(){});
            data.events = events != null ? events.values() : new ArrayList<Event>();
            Log.d("user task", "events " + dataSnapshot);
            notifyIfNeeded();
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {
            if (mListener != null) mListener.onError(firebaseError);
        }
    };
    private ValueEventListener mContactsListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
//            data.user = dataSnapshot.getValue(User.class);
            Map<String, UserContact> contacts = dataSnapshot.getValue(new GenericTypeIndicator<Map<String, UserContact>>(){});
            Log.d("user task", "contacts " + contacts);
            data.contacts = contacts != null ? contacts.values() : new ArrayList<UserContact>();
            notifyIfNeeded();
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {
            if (mListener != null) mListener.onError(firebaseError);
        }
    };

    public UserTask(Firebase firebase, String uid) {
        mUID = uid;
        mFirebase = firebase;
    }

    public UserTask withListener(Listener listener) {
        mListener = listener;
        return this;
    }

    private void notifyIfNeeded() {
        if (data.contacts != null && data.events != null && data.user != null) {
            if (mListener != null) mListener.onChanged(data);
        }
    }

    public void start() {
        Log.d("user task", "start");
        mFirebase.child("users").child(mUID).addValueEventListener(mUserListener);
        mFirebase.child("events").child(mUID).addValueEventListener(mEventsListener);
        mFirebase.child("contacts").child(mUID).addValueEventListener(mContactsListener);
    }

    public void cancel() {
        mFirebase.removeEventListener(mUserListener);
        mFirebase.removeEventListener(mContactsListener);
        mFirebase.removeEventListener(mEventsListener);
        mListener = null;
    }
}
