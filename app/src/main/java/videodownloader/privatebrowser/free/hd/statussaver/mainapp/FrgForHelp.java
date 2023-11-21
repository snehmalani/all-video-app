package videodownloader.privatebrowser.free.hd.statussaver.mainapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import videodownloader.privatebrowser.free.hd.statussaver.R;

public class FrgForHelp extends Fragment {
    private ImageView imgBackImage;
    private int intId = 0;
    private TextView txtSocialTwo;

    private void AssinnDataToFrag() {
        int i = this.intId;
        if (i == 0) {
            Glide.with(this).load((int) R.drawable.fram1).into(this.imgBackImage);
            this.txtSocialTwo.setVisibility(View.VISIBLE);
            this.txtSocialTwo.setText(getString(R.string.search_or_input_video_URL));
        } else if (i == 1) {
            Glide.with(this).load((int) R.drawable.fram2).into(this.imgBackImage);
            this.txtSocialTwo.setVisibility(View.VISIBLE);
            this.txtSocialTwo.setText(getString(R.string.click_the_video_to_play));
        } else if (i == 2) {
            Glide.with(this).load((int) R.drawable.fram3).into(this.imgBackImage);
            this.txtSocialTwo.setVisibility(View.VISIBLE);
            this.txtSocialTwo.setText(getString(R.string.click_the_detected_button));
        } else if (i == 3) {
            Glide.with(this).load((int) R.drawable.fram4).into(this.imgBackImage);
            this.txtSocialTwo.setVisibility(View.VISIBLE);
            this.txtSocialTwo.setText("Disclaimer");
        } else if (i == 4) {
            Glide.with(this).load((int) R.drawable.twitter_1).into(this.imgBackImage);
            this.txtSocialTwo.setVisibility(View.GONE);
            this.txtSocialTwo.setText(getString(R.string.click_the_detected_button));
        } else if (i == 5) {
            Glide.with(this).load((int) R.drawable.twitter_2).into(this.imgBackImage);
            this.txtSocialTwo.setVisibility(View.GONE);
            this.txtSocialTwo.setText(getString(R.string.click_the_detected_button));
        }
    }

    private void initView(View view) {
        this.imgBackImage = (ImageView) view.findViewById(R.id.imgBackImage);
        this.txtSocialTwo = (TextView) view.findViewById(R.id.txtSocialTwo);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.intId = getArguments() != null ? getArguments().getInt("int_data") : 0;
    }

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_how_to_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        AssinnDataToFrag();
    }
}
