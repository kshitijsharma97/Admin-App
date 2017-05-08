package in.wripe.wripe;

import android.media.Image;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by KSHITIJ SHARMA on 19-04-2017.
 */

@IgnoreExtraProperties
public class Quotation{

    public  String UserId;
    public  String Type;
    public  String Response;
    public  String Quantity;
    public  String Quality;
    public  String Name;
    public  String ImageURL;
    public String Date;
    public  String Contact;
    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();

    public Quotation() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Quotation(String UserId,String Type,String Response,String Quantity,String Quality,String Name,String ImageURL,String Date,String Contact) {
        this.UserId= UserId;
        this.Type= Type;
        this.Response=Response;
        this.Quantity=Quantity;
        this.Quality=Quality;
        this.Name=Name;
        this.ImageURL = ImageURL;
        this.Date=Date;
        this.Contact=Contact;
    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("UserId",UserId);
        result.put("Type",Type);
        result.put("Response",Response);
        result.put("Quantity",Quantity);
        result.put("Quality",Quality);
        result.put("Name",Name);
        result.put("ImageURL",ImageURL);
        result.put("Date",Date);
        result.put("Contact",Contact);
        //result.put("stars", stars);

        return result;
    }
    // [END post_to_map]

}
