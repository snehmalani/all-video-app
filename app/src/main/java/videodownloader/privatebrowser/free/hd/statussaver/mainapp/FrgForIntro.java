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

public class FrgForIntro extends Fragment {
    private ImageView imgBackImage;
    private int intId = 0;
    private TextView txtSocialThree;
    private TextView txtSocialTwo;

    private void AssinnDataToFrag() {
        int i = this.intId;
        if (i == 0) {
            Glide.with(this).load((int) R.drawable.vector1).dontAnimate().into(this.imgBackImage);
            this.txtSocialTwo.setText(getString(R.string.manage_file_on_device));
            this.txtSocialThree.setText(getString(R.string.free_application_for_download));
        } else if (i == 1) {
            Glide.with(this).load((int) R.drawable.vector2).dontAnimate().into(this.imgBackImage);
            this.txtSocialTwo.setText(getString(R.string.one_click_download));
            this.txtSocialThree.setText(getString(R.string.free_application_for_download));
        } else if (i == 2) {
            Glide.with(this).load((int) R.drawable.vector3).dontAnimate().into(this.imgBackImage);
            this.txtSocialTwo.setText(getString(R.string.social_media_video_download));
            this.txtSocialThree.setText(getString(R.string.free_application_for_download));
        }
    }

    private void initView(View view) {
        this.imgBackImage = (ImageView) view.findViewById(R.id.imgBackImage);
        this.txtSocialTwo = (TextView) view.findViewById(R.id.txtSocialTwo);
        this.txtSocialThree = (TextView) view.findViewById(R.id.txtSocialThree);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.intId = getArguments() != null ? getArguments().getInt("int_data") : 0;
    }

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_main_intro, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        AssinnDataToFrag();
    }
}
