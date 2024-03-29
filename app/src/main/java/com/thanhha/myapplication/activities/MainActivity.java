package com.thanhha.myapplication.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.thanhha.myapplication.R;
import com.thanhha.myapplication.database.adapter.ChatAdapter;
import com.thanhha.myapplication.database.adapter.ProductViewAdapter;
import com.thanhha.myapplication.databinding.ActivityMainBinding;
import com.thanhha.myapplication.listeners.ProductListener;
import com.thanhha.myapplication.models.entity.Product;
import com.thanhha.myapplication.utils.Constants;
import com.thanhha.myapplication.utils.ImageUtils;
import com.thanhha.myapplication.utils.PreferenceManager;
import com.thanhha.myapplication.viewmodels.MostPopularProductViewModel;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ProductListener {
    private ActivityMainBinding activityMainBinding;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private List<Product> products = new ArrayList<>();
    private ProductViewAdapter adapter;
    private MostPopularProductViewModel viewModel;
    private PreferenceManager preferenceManager;
    private StorageReference firebaseStorage;
    private Bitmap productImage;

    private String accountName;
    private String imageProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        preferenceManager = new PreferenceManager(getApplicationContext());
        firebaseStorage = FirebaseStorage.getInstance().getReference("yarn_type.jpg");
        loadPlantImage();
        doInitialization();
    }

    private void loadPlantImage() {
        try {
            File localFile = File.createTempFile("tempFile", ".jpg");
            firebaseStorage.getFile(localFile).addOnSuccessListener(listener -> {
                productImage = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                adapter.setProductImage(productImage);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRestart() {
        super.onRestart();
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

    private void getMostPopularProduct() {
        activityMainBinding.setIsLoading(true);
        viewModel.getPopularProduct().observe(this, products ->
        {
            activityMainBinding.setIsLoading(false);
            if (products == null || products.isEmpty()) {
                System.out.println("No products");
            } else {
                Log.d("Product", "Load All Products");
                this.products.addAll(products);
                adapter.notifyDataSetChanged();
            }
        }
        );
    }

    private void doInitialization() {
        System.out.println("Main Activity: " + this);
        activityMainBinding.productRecyclerView.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this)
                .get(MostPopularProductViewModel.class);
        adapter = new ProductViewAdapter(products, this, productImage);
        activityMainBinding.productRecyclerView.setAdapter(adapter);
        accountName = preferenceManager.getString(Constants.KEY_NAME);
        imageProfile = preferenceManager.getString(Constants.KEY_IMAGE);

        setUpDrawerLayout();
        getMostPopularProduct();
    }

    private void setUpDrawerLayout() {
        setSupportActionBar(activityMainBinding.appBar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, activityMainBinding.mainDrawer, R.string.app_name, R.string.app_name);
        View headerView = activityMainBinding.navView.getHeaderView(0);

        TextView accountName = headerView.findViewById(R.id.nav_header_name);
        accountName.setText(this.accountName);

        ImageView imageProfile = headerView.findViewById(R.id.imageProfile);
        imageProfile.setImageBitmap(ImageUtils.convertStringToBitmap(this.imageProfile));

        activityMainBinding.navView.setNavigationItemSelectedListener(item -> {
            Log.d("Selected Activity", "" + item.getItemId());
            switch (item.getItemId()) {
                case R.id.menu_bill: {
                    startActivity(new Intent(getApplicationContext(), BillHistoryActivity.class));
                    break;
                }
                case R.id.menu_cart: {
                    startActivity(new Intent(getApplicationContext(), CartActivity.class));
                    break;
                }
                case R.id.menu_location: {
                    startActivity(new Intent(getApplicationContext(), MapsMarkerActivity.class));
                    break;
                }
                case R.id.menu_logout: {
                    logout();
                    break;
                }
                case R.id.menu_contact: {
                    Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                    FirebaseFirestore.getInstance().collection(Constants.KEY_COLLECTION_ACCOUNTS)
                            .whereEqualTo(Constants.KEY_EMAIL, "admin@gmail.com")
                            .whereEqualTo(Constants.KEY_PASSWORD, "123456")
                            .get()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful() && task.getResult() != null
                                        && task.getResult().getDocuments().size() >0) {
                                    DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                                    intent.putExtra(Constants.KEY_ADMIN_ACCOUNT_ID, documentSnapshot.getId());
                                    intent.putExtra(Constants.KEY_ADMIN_NAME, documentSnapshot.getString(Constants.KEY_NAME));
                                    intent.putExtra(Constants.KEY_ADMIN_IMAGE, documentSnapshot.getString(Constants.KEY_IMAGE));
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Unable to load admin information", Toast.LENGTH_SHORT).show();
                                }
                            });
                    break;
                }
            }
            return true;
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onProductClicked(Product product) {
        Intent intent = new Intent(getApplicationContext(), ProductDetailActivity.class);
        intent.putExtra("productId", product.getId());
        startActivity(intent);
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String title = (String) item.getTitle();
//        actionBarDrawerToggle.getToolbarNavigationClickListener().onClick(new ViewModelProvider(this,));
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        preferenceManager.clear();
        Intent intent = new Intent(MainActivity.this,
                SignInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}