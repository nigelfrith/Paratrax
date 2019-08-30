package com.altitude.paratrax.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.altitude.paratrax.Classes.Quick_Log;
import com.altitude.paratrax.Models.Full_Logbook_Model;
import com.altitude.paratrax.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class Full_Logbook_Fragment extends Fragment {

    View mMainView;
    DatabaseReference mDatabase;

    EditText userid, fname, lname, email;
    private Button button;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseRecyclerAdapter adapter;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Full_Logbook_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Full_Logbook_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Full_Logbook_Fragment newInstance(String param1, String param2) {
        Full_Logbook_Fragment fragment = new Full_Logbook_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_logbook_full, container, false);
        mMainView = inflater.inflate(R.layout.fragment_logbook_full, container, false);
        recyclerView = (RecyclerView) mMainView.findViewById(R.id.rv_logbook_list);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("User").child(uid);
        mDatabase.keepSynced(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Quick_log").child(uid).orderByKey();

        FirebaseRecyclerOptions<Quick_Log> options = new FirebaseRecyclerOptions.Builder<Quick_Log>()
                .setQuery(query, Quick_Log.class).build();

        adapter = new FirebaseRecyclerAdapter<Full_Logbook_Model, ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Full_Logbook_Model model) {
                holder.settxtEmail("test_rva_nigelfrith@hotmail.com");
                holder.settxtfName("firstname_nigel");
                holder.settxtlName(model.getLname());
            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
               View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.full_logbook_list_item, parent,false);
               return new ViewHolder(view);
            }
        };

        recyclerView.setAdapter(adapter);
        return mMainView;
    }

//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        userid = view.findViewById(R.id.userid);
//        fname = view.findViewById(R.id.fname);
//        lname = view.findViewById(R.id.lname);
//        email = view.findViewById(R.id.email);
//        button = view.findViewById(R.id.btn);
//        recyclerView = view.findViewById(R.id.list);
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("posts").push();
//                Map<String, Object> map = new HashMap<>();
//                map.put("userid", databaseReference.getKey());
//                map.put("First Name", fname.getText().toString());
//                map.put("Last Name", lname.getText().toString());
//
//                databaseReference.setValue(map);
//            }
//
//        });
//
//        linearLayoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setHasFixedSize(true);
//        fetch();
//
//    }
//
//    private void fetch() {
//        Query query = FirebaseDatabase.getInstance()
//                .getReference()
//                .child("posts");
//
//        FirebaseRecyclerOptions<Full_Logbook_Model> options =
//                new FirebaseRecyclerOptions.Builder<Full_Logbook_Model>()
//                        .setQuery(query, new SnapshotParser<Full_Logbook_Model>() {
//                            @NonNull
//                            @Override
//                            public Full_Logbook_Model parseSnapshot(@NonNull DataSnapshot snapshot) {
//                                return new Full_Logbook_Model(snapshot.child("userid").getValue().toString(),
//                                        snapshot.child("fname").getValue().toString(),
//                                        snapshot.child("lname").getValue().toString(),
//                                        snapshot.child("email").getValue().toString());
//                            }
//                        })
//                        .build();
//
//        adapter = new FirebaseRecyclerAdapter<Full_Logbook_Model, ViewHolder>(options) {
//            @Override
//            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.full_logbook_list_item, parent, false);
//
//                return new ViewHolder(view);
//            }
//
//
//            @Override
//            protected void onBindViewHolder(ViewHolder holder, final int position, Full_Logbook_Model model) {
//                holder.settxtfName(model.getFname());
//                holder.settxtlName(model.getLname());
//                holder.settxtEmail(model.getLname());
//
//                holder.root.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Toast.makeText(getContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//
//        };
//        recyclerView.setAdapter(adapter);
//    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout root;
        public TextView txtfName;
        public TextView txtlName;
        public TextView txtEmail;

        public ViewHolder(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.list_root);
            txtfName = itemView.findViewById(R.id.fname);
            txtlName = itemView.findViewById(R.id.lname);
            txtEmail = itemView.findViewById(R.id.email);
        }

        public void settxtfName(String string) {
            txtfName.setText(string);
        }

        public void settxtlName(String string) {txtlName.setText(string);}

        public void settxtEmail(String string) {
            txtEmail.setText(string);
        }
    }


}
