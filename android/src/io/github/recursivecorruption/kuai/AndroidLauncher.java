package io.github.recursivecorruption.kuai;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.badlogic.gdx.backends.android.AndroidFragmentApplication;

import io.github.recursivecorruption.kuai.KuaiApp;
import io.github.recursivecorruption.kuai.R;

public class AndroidLauncher extends FragmentActivity implements AndroidFragmentApplication.Callbacks {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_layout);

        // 6. Finally, replace the AndroidLauncher activity content with the Libgdx Fragment.
        GameFragment fragment = new GameFragment();
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.replace(android.R.id.content, fragment);
        trans.commit();
    }

    // 4. Create a Class that extends AndroidFragmentApplication which is the Fragment implementation for Libgdx.
    public static class GameFragment extends AndroidFragmentApplication {
        // 5. Add the initializeForView() code in the Fragment's onCreateView method.
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) { return initializeForView(new KuaiApp()); }
    }


    @Override
    public void exit() {}
}
