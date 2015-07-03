package org.sopt.appjam.went;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONObject;
import org.sopt.appjam.went.Depth1.MainActivity;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserInformationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserInformationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserInformationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    CallbackManager callbackManager;
    Button button_logout, button_next;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserInformationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserInformationFragment newInstance(String param1, String param2) {
        UserInformationFragment fragment = new UserInformationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public UserInformationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_information, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){

        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    private void facebookLogin(){

        FacebookSdk.sdkInitialize(getApplicationContext());



        callbackManager = CallbackManager.Factory.create();

        button_logout = (Button) button_logout.findViewById(R.id.button_logout);
        button_next = (Button) button_next.findViewById(R.id.button_next);

        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginManager.getInstance().logOut();

                Intent intent = new Intent(getApplicationContext(), FacebookLoginActivity.class);
                startActivity(intent);
            }
        });

        Bundle parameter = new Bundle();
        parameter.putString("field", "id,name,link");

        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {

                ProfilePictureView profilePictureView = null;
                TextView textView_id = null, textView_name = null, textView_link = null;

                profilePictureView = (ProfilePictureView) profilePictureView.findViewById(R.id.profilePictureView_photo);
                textView_id = (TextView) textView_id.findViewById(R.id.textView_id);
                textView_name = (TextView) textView_id.findViewById(R.id.textView_name);
                textView_link = (TextView) textView_id.findViewById(R.id.textView_link);

                profilePictureView.setProfileId(jsonObject.optString("id"));
                textView_id.setText(jsonObject.optString("id"));
                textView_name.setText(jsonObject.optString("name"));
                textView_link.setText(jsonObject.optString("link"));
            }
        });

        request.setParameters(parameter);
        request.executeAsync();

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }



}
