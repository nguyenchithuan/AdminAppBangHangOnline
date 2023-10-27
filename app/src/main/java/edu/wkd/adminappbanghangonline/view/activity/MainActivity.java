package edu.wkd.adminappbanghangonline.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.WindowManager;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.firebase.messaging.FirebaseMessaging;

import edu.wkd.adminappbanghangonline.R;
import edu.wkd.adminappbanghangonline.data.api.ApiService;
import edu.wkd.adminappbanghangonline.databinding.ActivityMainBinding;
import edu.wkd.adminappbanghangonline.model.response.UserResponse;
import edu.wkd.adminappbanghangonline.ultil.UserUltil;
import edu.wkd.adminappbanghangonline.view.fragment.HomeFragment;
import edu.wkd.adminappbanghangonline.view.fragment.NotificationFragment;
import edu.wkd.adminappbanghangonline.view.fragment.StatisticalFragment;
import edu.wkd.adminappbanghangonline.view.fragment.UserFragment;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //  transparent Status Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getAndUpdateTokenFcm();
        onClickBottomNav();
    }
    public void getAndUpdateTokenFcm(){
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (!task.isSuccessful()){
                return;
            }
            String token = task.getResult();
            ApiService.apiService.updateTokenAdmin(token,UserUltil.user.getId())
                    .enqueue(new Callback<UserResponse>() {
                        @Override
                        public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                            //nothing
                        }

                        @Override
                        public void onFailure(Call<UserResponse> call, Throwable t) {

                        }
                    });
        });
    }

    private void onClickBottomNav() {
        binding.bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_home));
        binding.bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_notification));
        binding.bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.baseline_bar_chart_24));
        binding.bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.ic_account));
        binding.bottomNavigation.show(1, true);
        chooseFragment(HomeFragment.newInstance());

        binding.bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch (model.getId()){
                    case 1:
                        chooseFragment(HomeFragment.newInstance());
                        break;
                    case 2:
                        chooseFragment(NotificationFragment.newInstance());
                        break;
                    case 3:
                        chooseFragment(StatisticalFragment.newInstance());
                        break;
                    case 4:
                        chooseFragment(UserFragment.newInstance());
                        break;
                }
                return null;
            }
        });
    }

    private void chooseFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(binding.frameLayout.getId(), fragment);
        transaction.commit();
    }
}