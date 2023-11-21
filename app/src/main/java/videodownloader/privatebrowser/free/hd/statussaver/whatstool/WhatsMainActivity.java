package videodownloader.privatebrowser.free.hd.statussaver.whatstool;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.util.List;
import java.util.Objects;

import videodownloader.privatebrowser.free.hd.statussaver.DclassApp;
import videodownloader.privatebrowser.free.hd.statussaver.R;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.AdHelper;
import videodownloader.privatebrowser.free.hd.statussaver.mixAds.AllInOneAds;
import videodownloader.privatebrowser.free.hd.statussaver.tool.AdLoaderBase;
import videodownloader.privatebrowser.free.hd.statussaver.tool.ConstantForApp;
import videodownloader.privatebrowser.free.hd.statussaver.tool.UtilsForApp;

public class WhatsMainActivity extends AdLoaderBase {

    private String _type = "wa";
    private PermissionListener permissionListener;
    private SharedPreferences prefs;
    private RelativeLayout realative1;
    private RelativeLayout realative2;

    private void FetchXMLId() {
        this.realative1 = (RelativeLayout) findViewById(R.id.realative1);
        this.realative2 = (RelativeLayout) findViewById(R.id.realative2);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.status_saver));
    }

    private void askForFolderPermission(String pkg, int code) {
        Intent intent;
        if (UtilsForApp.isAppInstalled(pkg)) {
            StorageManager storageManager = (StorageManager) getSystemService(Context.STORAGE_SERVICE);
            String fetchWaOrWaBPath = UtilsForApp.fetchWaOrWaBPath(pkg);
            if (Build.VERSION.SDK_INT >= 29) {
                intent = storageManager.getPrimaryStorageVolume().createOpenDocumentTreeIntent();
                String replace = Objects.requireNonNull(intent.getParcelableExtra("android.provider.extra.INITIAL_URI")).toString().replace("/root/", "/document/");
                intent.putExtra("android.provider.extra.INITIAL_URI", Uri.parse(replace + "%3A" + fetchWaOrWaBPath));
            } else {
                intent = new Intent("android.intent.action.OPEN_DOCUMENT_TREE");
                intent.putExtra("android.provider.extra.INITIAL_URI", Uri.parse("content://com.android.externalstorage.documents/document/primary%3A" + fetchWaOrWaBPath));
            }
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_PREFIX_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            try {
                startActivityForResult(intent, code);
                return;
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        UtilsForApp.showToastMsg(getString(R.string.app_is_not_installed), this, false);
    }

    private void askPermissionFirst() {
        if (Build.VERSION.SDK_INT <= 29) {
            TedPermission.create().setPermissionListener(this.permissionListener).setRationaleTitle(getString(R.string.storage_permission)).setRationaleMessage(getString(R.string.please_allow_storage_permission_to_view_your_saved_status)).setDeniedTitle(getString(R.string.permission_denied)).setDeniedMessage(getString(R.string.if_you_reject_permission)).setGotoSettingButtonText(getString(R.string.settings)).setPermissions("android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE").check();
        } else {
            checkForFolderPermission();
        }
    }

    public void checkForFolderPermission() {
        if (this._type.equalsIgnoreCase("wa")) {
            String string = this.prefs.getString(ConstantForApp.PREF_WA_PATH, "");
            if (!string.isEmpty() && string.contains(UtilsForApp.fetchWAPath())) {
                AllInOneAds.getInstance().showInterWithId(this, AdHelper.whatsappId, () -> {

                    Intent intent = new Intent(this, WaStatusActivity.class);
                    intent.putExtra("request_code", ConstantForApp.REQ_WA);
                    startActivity(intent);
                });


            } else {
                askForFolderPermission("com.whatsapp", ConstantForApp.REQ_WA);
            }
        } else if (this._type.equalsIgnoreCase("wabusiness")) {
            String string2 = this.prefs.getString(ConstantForApp.PREF_WA_BUSINESS_PATH, "");
            if (!string2.isEmpty() && string2.contains(UtilsForApp.fetchWAPath())) {

                AllInOneAds.getInstance().showInterWithId(this, AdHelper.whatsappId, () -> {

                    Intent intent = new Intent(this, WaStatusActivity.class);
                    intent.putExtra("request_code", 497);
                    startActivity(intent);
                });

            } else {
                askForFolderPermission("com.whatsapp.w4b", 497);
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        String fetchWABusinessPath;
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 260 || requestCode == 497) {
            if (resultCode != -1) {
                if (requestCode == 260) {
                    this.prefs.edit().putString(ConstantForApp.PREF_WA_PATH, "").apply();
                } else {
                    this.prefs.edit().putString(ConstantForApp.PREF_WA_BUSINESS_PATH, "").apply();
                }
            } else if (data == null || data.getData() == null) {
            } else {
                Uri data2 = data.getData();
                if (requestCode == 260) {
                    fetchWABusinessPath = UtilsForApp.fetchWAPath();
                } else {
                    fetchWABusinessPath = UtilsForApp.fetchWABusinessPath();
                }
                if (!data2.toString().contains(fetchWABusinessPath)) {
                    UtilsForApp.showToastMsg(getString(R.string.invalid_path_selection_please_try_later), this, false);
                    return;
                }
                grantUriPermission(getPackageName(), data2, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                getContentResolver().takePersistableUriPermission(data2, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                if (requestCode == 260) {
                    this.prefs.edit().putString(ConstantForApp.PREF_WA_PATH, data2.toString()).apply();
                } else {
                    this.prefs.edit().putString(ConstantForApp.PREF_WA_BUSINESS_PATH, data2.toString()).apply();
                }

                AllInOneAds.getInstance().showInterWithId(this, AdHelper.whatsappId, () -> {

                    Intent intent = new Intent(this, WaStatusActivity.class);
                    intent.putExtra("request_code", requestCode);
                    startActivity(intent);
                });
            }
        }
    }

    @Override
    public void onBackPressed() {
        AllInOneAds.getInstance().showBackInter(this, () -> finish());

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whats_status_saver);

        AllInOneAds.getInstance().showBottomNativeWithId(this, AdHelper.whatsappId, findViewById(R.id.banner_container));

        FetchXMLId();
        this.prefs = PreferenceManager.getDefaultSharedPreferences(DclassApp.getInstance());
        this.permissionListener = new PermissionListener() {
            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
            }

            @Override
            public void onPermissionGranted() {
                WhatsMainActivity.this.checkForFolderPermission();
            }
        };
        this.realative1.setOnClickListener(view -> {
            _type = "wa";
            askPermissionFirst();
        });
        this.realative2.setOnClickListener(view -> {
            if (Build.VERSION.SDK_INT <= 29) {
                TedPermission.create().setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        AllInOneAds.getInstance().showInterWithId(WhatsMainActivity.this, AdHelper.whatsappId, () -> {
                            WhatsMainActivity.this.startActivity(new Intent(WhatsMainActivity.this, MyStatuesActivity.class));
                        });
                    }

                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) {

                    }
                }).setRationaleTitle(getString(R.string.storage_permission)).setRationaleMessage(getString(R.string.please_allow_storage_permission_to_view_your_saved_status)).setDeniedTitle(getString(R.string.permission_denied)).setDeniedMessage(getString(R.string.if_you_reject_permission)).setGotoSettingButtonText(getString(R.string.settings)).setPermissions("android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE").check();
            } else {
                AllInOneAds.getInstance().showInterWithId(this, AdHelper.whatsappId, () -> {
                    startActivity(new Intent(WhatsMainActivity.this, MyStatuesActivity.class));
                });
            }
        });
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
