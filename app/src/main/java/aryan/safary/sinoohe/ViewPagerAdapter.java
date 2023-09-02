package aryan.safary.sinoohe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>{
// Array of images
// Adding images from drawable folder
private final String[] TexStrings = {"R.drawable.ic_image", "R.drawable.ic_calender", "R.drawable.ic_clock",
        "R.drawable.ic_download", "R.drawable.ic_baseline_person_pin_24"};
private final Context ctx;

        // Constructor of our ViewPager2Adapter class
        public ViewPagerAdapter(Context ctx) {
        this.ctx = ctx;
        }

// This method returns our layout
@NonNull
@Override
public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.message_row, parent, false);
        return new ViewHolder(view);
        }

// This method binds the screen with the view
@Override
public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // This will set the images in imageview
        holder.text.setText(TexStrings[position]);
        }

// This Method returns the size of the Array
@Override
public int getItemCount() {
        return TexStrings.length;
        }

// The ViewHolder class holds the view
public static class ViewHolder extends RecyclerView.ViewHolder {
    TextView text  ;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        text = itemView.findViewById(R.id.row_text);
    }
}
}
