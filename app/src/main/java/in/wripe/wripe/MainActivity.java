package in.wripe.wripe;

import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.core.Path;
import com.firebase.client.snapshot.ChildKey;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ListView ulist;
    Firebase mfirebase;
    DatabaseReference mDatabase;
    String resd="We will get back to you soon";
    ArrayList<String> quto= new ArrayList<>();
    ArrayList<String> url = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);
        mfirebase = new Firebase("https://wripe-9ff94.firebaseio.com/Quotation/");
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Quotation");
        ulist= (ListView) findViewById(R.id.Ulist);
        final ArrayAdapter<String> arrayAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,quto);
        ulist.setAdapter(arrayAdapter);

        mfirebase.addChildEventListener(new com.firebase.client.ChildEventListener() {
            @Override
            public void onChildAdded(com.firebase.client.DataSnapshot dataSnapshot, String s) {
               /* Path pth = mfirebase.getPath();
                pth.
                com.firebase.client.DataSnapshot kk = dataSnapshot.child("wripe-9ff94.Quotation");
                //com.firebase.client.DataSnapshot dataSnapshot1= new com.firebase.client.DataSnapshot();
                Quotation value= kk.getValue(Quotation.class);*/
               Quotation QU= dataSnapshot.getValue(Quotation.class);
               quto.add(QU.Name);
               url.add(QU.ImageURL);
               arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(com.firebase.client.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(com.firebase.client.DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(com.firebase.client.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        ulist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                mfirebase.addChildEventListener(new com.firebase.client.ChildEventListener() {
                    @Override
                    public void onChildAdded(com.firebase.client.DataSnapshot dataSnapshot, String s) {

                            final Quotation Qt = dataSnapshot.getValue(Quotation.class);
                            if(quto.get(position).equals(Qt.Name)){
                                    if (url.get(position).equals(Qt.ImageURL)){
                                       final String t = dataSnapshot.getKey();
                                final Dialog dialog = new Dialog(MainActivity.this);
                                dialog.setContentView(R.layout.activity_details);
                                Toast.makeText(MainActivity.this,Qt.Contact,Toast.LENGTH_SHORT).show();
                                TextView contact= (TextView) dialog.findViewById(R.id.ContactNo);
                                contact.append(Qt.Contact);
                                TextView type = (TextView) dialog.findViewById(R.id.Type);
                                type.append(Qt.Type);
                                TextView Quality = (TextView) dialog.findViewById(R.id.Quality);
                                Quality.append(Qt.Quality);
                                TextView Quantity = (TextView) dialog.findViewById(R.id.Quantity);
                                Quantity.append(Qt.Quantity);
                               TextView url = (TextView) dialog.findViewById(R.id.URL);
                               url.setClickable(true);
                               url.setMovementMethod(LinkMovementMethod.getInstance());
                               String text= "<a href='"+Qt.ImageURL+"'>Image</a>";
                               url.setText(Html.fromHtml(text));
                               TextView date= (TextView) dialog.findViewById(R.id.Date);
                               date.append(Qt.Date);
                                final EditText response= (EditText) dialog.findViewById(R.id.Response);
                                final Button submit= (Button) dialog.findViewById(R.id.Submit);
                                if(!Qt.Response.equals(resd)){
                                    submit.setText("SUBMITTED");
                                    submit.setBackgroundColor(Color.RED);
                                }
                                submit.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                         String res= response.getText().toString().trim();
                                        Map<String, Object> userUpdates = new HashMap<String, Object>();
                                        userUpdates.put(t+"/Response",res);
                                       // Toast.makeText(MainActivity.this,res,Toast.LENGTH_SHORT).show();
                                        mfirebase.updateChildren(userUpdates);
                                        dialog.dismiss();
                                    }
                                });
                                dialog.show();
                            }}

                    }

                    @Override
                    public void onChildChanged(com.firebase.client.DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(com.firebase.client.DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(com.firebase.client.DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }
        });
    }
}
