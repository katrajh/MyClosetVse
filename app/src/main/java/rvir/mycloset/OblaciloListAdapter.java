package rvir.mycloset;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;

public class OblaciloListAdapter extends ArrayAdapter<Oblacilo> {

    private List<CreateList> galleryList;
    ArrayList<Oblacilo> oblacila;

    Context context;
    int resource;

    private static class ViewHolder {
        TextView naziv;
        TextView vrsta;
        ImageView img;
    }

    public OblaciloListAdapter(Context context, int resource, ArrayList<Oblacilo> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.oblacila = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        setupImageLoader();

        // get the oblacilo data
        String naziv = getItem(position).getNaziv();
        String vrsta = getItem(position).getVrsta();
        String imgUrl = getItem(position).getSlika();

        ViewHolder holder;

        holder = new ViewHolder();

        if(convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.single_list_item, parent, false);
            holder.naziv = (TextView)convertView.findViewById(R.id.tv_soNaziv);
            holder.vrsta = (TextView)convertView.findViewById(R.id.tv_soVrsta);
            holder.img = (ImageView) convertView.findViewById(R.id.img_soSlika);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

//        LayoutInflater inflater = LayoutInflater.from(context);
//        convertView = inflater.inflate(resource, parent, false);
//        holder = new ViewHolder();
//        holder.naziv = (TextView) convertView.findViewById(R.id.tv_soNaziv);
//        holder.vrsta = (TextView) convertView.findViewById(R.id.tv_soVrsta);
//        holder.img = (ImageView) convertView.findViewById(R.id.img_soSlika);
//
//        ImageLoader imageLoader = ImageLoader.getInstance();
//
//        int defaultImage = context.getResources().getIdentifier("@drawable/image_failed", null, context.getPackageName());
//
//        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
//                .cacheOnDisk(true).resetViewBeforeLoading(true)
//                .showImageForEmptyUri(defaultImage)
//                .showImageOnFail(defaultImage)
//                .showImageOnLoading(defaultImage).build();
//
//        imageLoader.displayImage(imgUrl, holder.img, options);
        holder.naziv.setText(naziv);
        holder.vrsta.setText(vrsta);
        holder.img.setImageURI(Uri.parse(oblacila.get(position).getSlika()));

        return convertView;
    }

    private void setupImageLoader() {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(context)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .diskCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(configuration);
    }

}
