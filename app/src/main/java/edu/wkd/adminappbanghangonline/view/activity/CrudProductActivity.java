package edu.wkd.adminappbanghangonline.view.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.File;

import edu.wkd.adminappbanghangonline.R;
import edu.wkd.adminappbanghangonline.data.api.ApiService;
import edu.wkd.adminappbanghangonline.databinding.ActivityCrudProductBinding;
import edu.wkd.adminappbanghangonline.model.obj.Product;
import edu.wkd.adminappbanghangonline.model.response.ProductResponse;
import edu.wkd.adminappbanghangonline.ultil.ProgressDialogLoading;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrudProductActivity extends AppCompatActivity {
    private ActivityCrudProductBinding binding;
    private int type;
    private Product product;
    private MultipartBody.Part fileToUpload;
    private String strImage; // Nếu là "" thì tức là thêm ảnh mới, còn không là lấy url cũ
    private ProgressDialogLoading dialogLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCrudProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        getData();
        initUI();
        initController();
    }

    private void initController() {
        binding.btnImgProduct.setOnClickListener(view -> {
            ImagePicker.with(CrudProductActivity.this)
                    .crop()	    			//Crop image(Optional), Check Customization for more option
                    .compress(1024)			//Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                    .start();
        });

        binding.btnSave.setOnClickListener(view -> {
            onclickSaveProduct();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            Uri uri = data.getData();
            binding.imgProduct.setImageURI(uri);
            File file = new File(getPath(uri));
            // Parsing any Media type file
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
            fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
            strImage = "";

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    private String getPath(Uri uri){
        String result;
        Cursor cursor = getContentResolver()
                .query(uri, null,null,null,null);
        if (cursor == null){
            result = uri.getPath();
        }else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(index);
            cursor.close();
        }
        return result;
    }

    private void onclickSaveProduct() {
        if(validate() == false) {
            Toast.makeText(this, "Mời nhập dữ liệu đầy đủ", Toast.LENGTH_SHORT).show();
            return;
        }

        String name = binding.edName.getText().toString();
        int price = Integer.parseInt(binding.edPrice.getText().toString());
        String description = binding.edDescription.getText().toString();
        int productType = 1;

        dialogLoading.show();
        if(type == 0) {
            ApiService.apiService.addProduct(fileToUpload, name, price, description, productType).enqueue(new Callback<ProductResponse>() {
                @Override
                public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                    ProductResponse productResponse = response.body();
                    Log.d("zzzzz", "onResponse: " + productResponse);
                    Toast.makeText(CrudProductActivity.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                    if(!productResponse.isSuccess()) {
                        Toast.makeText(CrudProductActivity.this, "Mời bạn chọn ảnh", Toast.LENGTH_SHORT).show();
                    }
                    dialogLoading.cancel();
                }

                @Override
                public void onFailure(Call<ProductResponse> call, Throwable t) {
                    Log.d("zzzzz", "error: " + t.toString());
                    dialogLoading.cancel();
                }
            });
        } else {
            Log.d("zzzzz", "onResponse-image: " + strImage);
            Log.d("zzzzz", "onResponse-image: " + fileToUpload);
            ApiService.apiService.updateProduct(fileToUpload, product.getId(), name, price, description, productType, strImage).enqueue(new Callback<ProductResponse>() {
                @Override
                public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                    ProductResponse productResponse = response.body();
                    Log.d("zzzzz", "onResponse: " + productResponse);
                    Toast.makeText(CrudProductActivity.this, "Sửa thành công!", Toast.LENGTH_SHORT).show();
                    if(!productResponse.isSuccess()) {
                        Toast.makeText(CrudProductActivity.this, "Mời bạn chọn lại ảnh", Toast.LENGTH_SHORT).show();
                    }
                    dialogLoading.cancel();
                }

                @Override
                public void onFailure(Call<ProductResponse> call, Throwable t) {
                    Log.d("zzzzz", "error: " + t.toString());
                    dialogLoading.cancel();
                }
            });
        }
    }

    private boolean validate() {
        if(TextUtils.isEmpty(binding.edPrice.getText().toString())
        || TextUtils.isEmpty(binding.edDescription.getText().toString())
        || TextUtils.isEmpty(binding.edName.getText().toString())) {
            return false;
        }
        return true;
    }

    private void getData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle == null) {
            return;
        }
        type = bundle.getInt("type"); // 0: Thêm, 1: Sửa
        product = (Product) bundle.getSerializable("product");
    }
    private void initUI() {
        dialogLoading = new ProgressDialogLoading(this);
        if(type == 0) {
            binding.tvTitle.setText("Thêm sản phẩm");
            binding.tvIdProduct.setVisibility(View.GONE);
            strImage = "";
        } else {
            binding.tvTitle.setText("Sửa sản phẩm");
            binding.tvIdProduct.setVisibility(View.VISIBLE);
            binding.tvIdProduct.setText("Id sản phẩm: " + product.getId());
            binding.edName.setText(product.getName());
            binding.edPrice.setText(product.getPrice() + "");
            binding.edDescription.setText(product.getDescription());
            strImage = product.getImage();
            if(product.getImage().contains("uploads")) {
                Glide.with(this)
                        .load("https://guyinterns2003.000webhostapp.com/" + product.getImage())
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.error)
                        .into(binding.imgProduct);
            } else {
                Glide.with(this)
                        .load(product.getImage())
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.error)
                        .into(binding.imgProduct);
            }
        }
    }
}